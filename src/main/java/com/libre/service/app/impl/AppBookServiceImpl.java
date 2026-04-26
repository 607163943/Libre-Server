package com.libre.service.app.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.libre.constant.LendStatus;
import com.libre.mapper.BookMapper;
import com.libre.pojo.dto.app.SearchDTO;
import com.libre.pojo.po.Book;
import com.libre.pojo.po.Lend;
import com.libre.pojo.vo.app.BookDetailVO;
import com.libre.pojo.vo.app.HomeTopLatestBookItem;
import com.libre.pojo.vo.app.SearchBookVO;
import com.libre.result.PageResult;
import com.libre.service.app.AppBookService;
import com.libre.service.app.AppLendService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AppBookServiceImpl extends ServiceImpl<BookMapper, Book> implements AppBookService {
    private final AppLendService appLendService;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 获取图书详情
     *
     * @param bookId 图书id
     * @return 图书详情
     */
    @Override
    public BookDetailVO getBookDetail(Long bookId) {
        String cacheKey = "user:book:detail:" + bookId;

        // 尝试从缓存中获取
        String cachedData = stringRedisTemplate.opsForValue().get(cacheKey);

        BookDetailVO bookDetail;
        if(cachedData!=null) {
            // 已登录状态，查询数据库获取实时信息
            bookDetail = JSONUtil.toBean(cachedData, BookDetailVO.class);
        }else {
            // 查询数据库，建立缓存
            bookDetail = baseMapper.getBookDetail(bookId);
            stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(bookDetail),1, TimeUnit.HOURS);
        }

        if(!StpUtil.isLogin()) {
            return bookDetail;
        }

        Lend lend = Db.lambdaQuery(Lend.class)
                .eq(Lend::getBookId, bookId)
                .eq(Lend::getUserId, StpUtil.getLoginIdAsLong())
                .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                .one();

        if (lend == null) {
            return bookDetail;
        }

        bookDetail.setState(lend.getState());

        // 已登录且有借阅记录，不缓存个性化数据（因为每个用户的借阅状态不同）
        return bookDetail;
    }

    /**
     * 获取用户借阅最新top
     *
     * @return 用户借阅最新top
     */
    @Override
    public List<HomeTopLatestBookItem> getHomeTopLatestBookList() {
        return baseMapper.getHomeTopLatestBookList();
    }

    /**
     * 搜索图书
     *
     * @param searchDTO 搜索参数
     * @return 搜索结果
     */
    @Override
    public PageResult<List<SearchBookVO>> search(SearchDTO searchDTO) {
        IPage<SearchBookVO> page = PageUtil.createPage(searchDTO);
        page = baseMapper.search(page, searchDTO);

        List<SearchBookVO> searchBookVOS = page.getRecords();
        long total = page.getTotal();
        // 登录状态下额外提供借阅书籍标注、借阅状态查询功能
        if (StpUtil.isLogin()) {
            LambdaQueryChainWrapper<Lend> chainWrapper = appLendService.lambdaQuery()
                    .eq(Lend::getUserId, StpUtil.getLoginIdAsLong());

            // 借阅书籍查询
            List<Lend> lendList;
            if (searchDTO.getState() != null) {
                lendList = chainWrapper
                        .eq(Lend::getState, searchDTO.getState())
                        .list();
                // 获取借阅书籍id
                List<Long> bookIds = lendList.stream().map(Lend::getBookId).collect(Collectors.toList());
                searchBookVOS = searchBookVOS.stream()
                        .filter(searchBookVO -> bookIds.contains(searchBookVO.getId()))
                        .peek(searchBookVO -> searchBookVO.setState(searchDTO.getState()))
                        .collect(Collectors.toList());
                // 带状态的借阅查询下，总条数根有效借阅总数一致
                total = lendList.size();
            } else {
                // 借阅书籍标记
                lendList = chainWrapper
                        .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                        .list();
                // 借阅书籍标注
                Map<Long, Lend> lendBookStatusMap = lendList.stream()
                        .collect(Collectors.toMap(Lend::getBookId, lend -> lend));
                searchBookVOS.forEach(searchBookVO -> {
                    Lend lend = lendBookStatusMap.get(searchBookVO.getId());
                    if (lend != null) {
                        searchBookVO.setState(lend.getState());
                        searchBookVO.setDueTime(lend.getDueTime());
                    }
                });
            }
        }


        return PageResult.<List<SearchBookVO>>builder()
                .total(total)
                .data(searchBookVOS)
                .build();
    }
}
