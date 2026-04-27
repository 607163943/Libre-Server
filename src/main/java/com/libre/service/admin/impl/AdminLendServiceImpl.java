package com.libre.service.admin.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.libre.constant.LendStatus;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LendException;
import com.libre.mapper.LendMapper;
import com.libre.pojo.dto.admin.LendDTO;
import com.libre.pojo.dto.admin.LendPageDTO;
import com.libre.pojo.po.Book;
import com.libre.pojo.po.Lend;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.admin.HomeTopBookItem;
import com.libre.pojo.vo.admin.LendPageVO;
import com.libre.pojo.vo.admin.RecentLendTrendItem;
import com.libre.pojo.vo.app.HomeTopLendBookItem;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminLendService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminLendServiceImpl extends ServiceImpl<LendMapper, Lend> implements AdminLendService {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 分页查询借阅信息
     *
     * @param lendPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<LendPageVO>> pageQueryLend(LendPageDTO lendPageDTO) {
        // 构建分页条件
        IPage<LendPageVO> page = PageUtil.createPage(lendPageDTO);
        // 查询
        page = baseMapper.pageQueryLend(page, lendPageDTO);

        return PageResult.<List<LendPageVO>>builder()
                .total(page.getTotal())
                .data(page.getRecords())
                .build();
    }

    /**
     * 添加借阅记录
     *
     * @param lendDTO 借阅信息
     */
    @Override
    public void addLend(LendDTO lendDTO) {
        // 判断用户是否已经借阅过该书籍
        Long lendCount = lambdaQuery()
                .eq(Lend::getUserId, lendDTO.getUserId())
                .eq(Lend::getBookId, lendDTO.getBookId())
                .eq(Lend::getState, LendStatus.LEND)
                .count();

        if (lendCount > 0) {
            throw new LendException(ExceptionEnums.LEND_USER_LEND_BOOK_EXIST);
        }

        // 检查书籍是否存在
        Book book = checkBookIsNullAndReturnBook(lendDTO.getBookId());

        // 检查用户是否存在
        boolean userExists = Db.lambdaQuery(User.class)
                .eq(User::getId, lendDTO.getUserId())
                .exists();

        if (!userExists) {
            throw new LendException(ExceptionEnums.LEND_USER_NOT_EXIST);
        }

        // 判断库存是否为空
        Long lendBookNumber = lambdaQuery()
                .eq(Lend::getBookId, lendDTO.getBookId())
                .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                .count();

        // 相等说明库存为空
        if (lendBookNumber.equals(book.getNumber())) {
            throw new LendException(ExceptionEnums.LEND_BOOK_EMPTY);
        }

        Lend lend = BeanUtil.copyProperties(lendDTO, Lend.class);
        // 初始化借阅次数为0
        lend.setRenewCount(0);
        // 初始化为借阅状态
        lend.setState(LendStatus.LEND);
        // 默认借阅时间为7天
        lend.setDueTime(lendDTO.getLendTime().plusDays(7));
        save(lend);

        // 清除首页缓存
        clearHomeCache();
    }

    /**
     * 修改借阅记录
     *
     * @param lendDTO 借阅信息
     */
    @Override
    public void modifyLend(LendDTO lendDTO) {
        // 检查书籍是否存在
        Book book = checkBookIsNullAndReturnBook(lendDTO.getBookId());

        // 检查用户是否存在
        boolean userExists = Db.lambdaQuery(User.class)
                .eq(User::getId, lendDTO.getUserId())
                .exists();

        if (!userExists) {
            throw new LendException(ExceptionEnums.LEND_USER_NOT_EXIST);
        }

        // 非归还状态
        if (!lendDTO.getState().equals(LendStatus.RETURN)) {
            // 判断用户是否已经借阅过该书籍
            Long lendCount = lambdaQuery()
                    .eq(Lend::getUserId, lendDTO.getUserId())
                    .eq(Lend::getBookId, lendDTO.getBookId())
                    .eq(Lend::getState, LendStatus.LEND)
                    .ne(Lend::getId, lendDTO.getId())
                    .count();

            if (lendCount > 0) {
                throw new LendException(ExceptionEnums.LEND_USER_LEND_BOOK_EXIST);
            }

            // 判断库存是否为空
            Long lendBookNumber = lambdaQuery()
                    // 排除自己
                    .ne(Lend::getId, lendDTO.getId())
                    .eq(Lend::getBookId, lendDTO.getBookId())
                    .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                    .count();

            // 相等说明库存为空
            if (lendBookNumber.equals(book.getNumber())) {
                throw new LendException(ExceptionEnums.LEND_BOOK_EMPTY);
            }
        }

        Lend lend = BeanUtil.copyProperties(lendDTO, Lend.class);

        // 更新了借阅时间则逾期时间同步更新
        if (lend.getLendTime() != null) {
            lend.setDueTime(lendDTO.getLendTime().plusDays(7));
        }
        updateById(lend);

        // 清除首页缓存
        clearHomeCache();
    }

    /**
     * 检查书籍是否存在
     *
     * @param bookId 书籍id
     * @return 书籍
     */
    private Book checkBookIsNullAndReturnBook(Long bookId) {
        // 判断该书籍是否存在
        Book book = Db.lambdaQuery(Book.class)
                .eq(Book::getId, bookId)
                .one();
        if (book == null) {
            throw new LendException(ExceptionEnums.LEND_BOOK_NOT_EXIST);
        }

        return book;
    }

    /**
     * 删除借阅记录
     *
     * @param lendId 借阅记录id
     */
    @Override
    public void deleteLend(Long lendId) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Lend::getIsDelete, System.currentTimeMillis())
                .eq(Lend::getId, lendId)
                .update();

        // 清除首页缓存
        clearHomeCache();
    }

    /**
     * 批量删除借阅记录
     *
     * @param ids 借阅记录id列表
     */
    @Override
    public void deleteBatchLend(List<Long> ids) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Lend::getIsDelete, System.currentTimeMillis())
                .in(Lend::getId, ids)
                .update();

        // 清除首页缓存
        clearHomeCache();
    }

    /**
     * 清除首页缓存
     */
    private void clearHomeCache() {
        stringRedisTemplate.delete("admin:home:total-card");
        stringRedisTemplate.delete("admin:home:recent-lend-trend");
        stringRedisTemplate.delete("admin:home:top-book");
    }

    /**
     * 获取最近借阅趋势
     *
     * @return 最近借阅趋势
     */
    @Override
    public List<RecentLendTrendItem> getRecentLendTrend() {
        return baseMapper.getRecentLendTrend();
    }

    /**
     * 获取首页图书排行
     *
     * @return 首页图书排行
     */
    @Override
    public List<HomeTopBookItem> getHomeTopBook() {
        return baseMapper.getHomeTopBook();
    }

    @Override
    public List<HomeTopLendBookItem> getHomeTopLendBookList() {
        return baseMapper.getHomeTopLendBookList();
    }
}
