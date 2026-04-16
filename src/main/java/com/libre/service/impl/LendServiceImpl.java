package com.libre.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.LendStatus;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LendException;
import com.libre.mapper.BookMapper;
import com.libre.mapper.LendMapper;
import com.libre.pojo.dto.LendDTO;
import com.libre.pojo.dto.LendPageDTO;
import com.libre.pojo.po.Lend;
import com.libre.pojo.vo.LendPageVO;
import com.libre.pojo.vo.user.BookDetailVO;
import com.libre.pojo.vo.admin.HomeTopBookItem;
import com.libre.pojo.vo.admin.RecentLendTrendItem;
import com.libre.pojo.vo.user.HomeTopLendBookItem;
import com.libre.result.PageResult;
import com.libre.service.LendService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LendServiceImpl extends ServiceImpl<LendMapper, Lend> implements LendService {
    private final BookMapper bookMapper;
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
            throw new LendException(ExceptionEnums.USER_LEND_BOOK_EXIST);
        }
        Lend lend = BeanUtil.copyProperties(lendDTO, Lend.class);
        // 避免前端id残留数据影响
        if (lend.getId() != null) lend.setId(null);
        // 初始化借阅次数为0
        lend.setRenewCount(0);
        // 初始化为借阅状态
        lend.setState(LendStatus.LEND);
        // 默认借阅时间为7天
        lend.setDueTime(LocalDateTime.now().plusDays(7));
        save(lend);
    }

    /**
     * 修改借阅记录
     *
     * @param lendDTO 借阅信息
     */
    @Override
    public void modifyLend(LendDTO lendDTO) {
        // 判断用户是否已经借阅过该书籍
        Long lendCount = lambdaQuery()
                .eq(Lend::getUserId, lendDTO.getUserId())
                .eq(Lend::getBookId, lendDTO.getBookId())
                .eq(Lend::getState, LendStatus.LEND)
                .ne(Lend::getId, lendDTO.getId())
                .count();

        if (lendCount > 0) {
            throw new LendException(ExceptionEnums.USER_LEND_BOOK_EXIST);
        }

        Lend lend = BeanUtil.copyProperties(lendDTO, Lend.class);
        updateById(lend);
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

    /**
     * 修改借阅状态
     *
     * @param lendDTO 借阅信息
     */
    @Override
    public void modifyLendStatus(LendDTO lendDTO) {
        // 验证借阅状态是否合法
        if (lendDTO.getState() == null ||
                (!lendDTO.getState().equals(LendStatus.LEND) &&
                        !lendDTO.getState().equals(LendStatus.RETURN) &&
                        !lendDTO.getState().equals(LendStatus.OVERDUE))) {
            throw new LendException(ExceptionEnums.LEND_STATUS_ILLEGAL);
        }

        lambdaUpdate()
                .set(Lend::getState, lendDTO.getState())
                .eq(Lend::getBookId, lendDTO.getBookId())
                .eq(Lend::getUserId, StpUtil.getLoginIdAsLong())
                .update();
    }

    /**
     * 用户借阅图书
     *
     * @param bookId 借阅的图书id
     */
    @Override
    public void userLendBook(Long bookId) {
        // 检查该书籍是否已借阅
        Long lendCount = lambdaQuery()
                .eq(Lend::getBookId, bookId)
                .eq(Lend::getUserId, StpUtil.getLoginIdAsLong())
                .ne(Lend::getState, LendStatus.RETURN)
                .count();
        if (lendCount > 0) {
            throw new LendException(ExceptionEnums.USER_LEND_BOOK_EXIST);
        }

        Lend lend = new Lend();
        lend.setBookId(bookId);
        lend.setUserId(StpUtil.getLoginIdAsLong());
        // 初始化借阅次数为0
        lend.setRenewCount(0);
        // 初始化为借阅状态
        lend.setState(LendStatus.LEND);
        // 默认借阅时间为7天
        lend.setDueTime(LocalDateTime.now().plusDays(7));
        save(lend);
    }

    /**
     * 用户归还图书
     *
     * @param bookId 归还的图书id
     */
    @Override
    public void userReturnBook(Long bookId) {
        // 检查该书籍是否已借阅
        Long lendCount = lambdaQuery()
                .eq(Lend::getBookId, bookId)
                .eq(Lend::getUserId, StpUtil.getLoginIdAsLong())
                .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                .count();
        if (lendCount == 0) {
            throw new LendException(ExceptionEnums.LEND_USER_RETURN_BOOK_NOT_EXIST);
        }

        lambdaUpdate()
                .set(Lend::getState, LendStatus.RETURN)
                .eq(Lend::getBookId, bookId)
                .eq(Lend::getUserId, StpUtil.getLoginIdAsLong())
                .update();
    }

    /**
     * 获取图书详情
     *
     * @param bookId 图书id
     * @return 图书详情
     */
    @Override
    public BookDetailVO getBookDetail(Long bookId) {
        BookDetailVO bookDetail = bookMapper.getBookDetail(bookId);
        if(!StpUtil.isLogin()) {
            return bookDetail;
        }

        Lend lend = lambdaQuery()
                .eq(Lend::getBookId, bookId)
                .eq(Lend::getUserId, StpUtil.getLoginIdAsLong())
                .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                .one();
        if(lend==null) {
            return bookDetail;
        }

        bookDetail.setState(lend.getState());

        return bookDetail;
    }
}
