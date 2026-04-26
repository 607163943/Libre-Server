package com.libre.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.libre.constant.LendStatus;
import com.libre.pojo.po.Lend;
import com.libre.pojo.vo.user.*;
import com.libre.service.BookService;
import com.libre.service.HomeService;
import com.libre.service.LendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class HomeServiceImpl implements HomeService {
    private final BookService bookService;

    private final LendService lendService;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 获取用户借阅数据
     *
     * @return 用户借阅数据
     */
    @Override
    public HomeUserTotalVO getHomeUserTotal() {
        Long userId = StpUtil.getLoginIdAsLong();

        // 借阅统计
        Long lendCount = lendService.lambdaQuery()
                .eq(Lend::getUserId, userId)
                .eq(Lend::getState, LendStatus.LEND)
                .count();
        // 即将逾期统计(3天内属于即将逾期)
        Long soonOverdueCount = lendService.lambdaQuery()
                .eq(Lend::getUserId, userId)
                .eq(Lend::getState, LendStatus.LEND)
                .ge(Lend::getReturnTime, LocalDateTime.now().plusDays(3))
                .count();
        // 逾期统计
        Long overdueCount = lendService.lambdaQuery()
                .eq(Lend::getUserId, userId)
                .eq(Lend::getState, LendStatus.OVERDUE)
                .count();
        return HomeUserTotalVO.builder()
                .lendCount(lendCount)
                .soonOverdueCount(soonOverdueCount)
                .overdueCount(overdueCount)
                .build();
    }

    /**
     * 获取用户借阅排行top
     *
     * @return 用户借阅排行top
     */
    @Override
    public HomeTopLendBookVO getHomeTopLendBook() {
        String cacheKey = "user:home:top-lend-book";
        
        // 尝试从缓存中获取
        String cachedData = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toBean(cachedData, HomeTopLendBookVO.class);
        }
        
        // 缓存未命中，查询数据库
        List<HomeTopLendBookItem> homeTopLendBookItemList = lendService.getHomeTopLendBookList();
        HomeTopLendBookVO result = HomeTopLendBookVO.builder()
                .homeTopLendBookItemList(homeTopLendBookItemList)
                .build();
        
        // 存入缓存，过期时间30分钟
        stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(result), 30, TimeUnit.MINUTES);
        
        return result;
    }

    /**
     * 获取用户借阅最新top
     *
     * @return 用户借阅最新top
     */
    @Override
    public HomeTopLatestBookVO getHomeTopLatestBook() {
        String cacheKey = "user:home:top-latest-book";
        
        // 尝试从缓存中获取
        String cachedData = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toBean(cachedData, HomeTopLatestBookVO.class);
        }
        
        // 缓存未命中，查询数据库
        List<HomeTopLatestBookItem> homeTopLatestBookItemList = bookService.getHomeTopLatestBookList();
        HomeTopLatestBookVO result = HomeTopLatestBookVO.builder()
                .homeTopLatestBookItemList(homeTopLatestBookItemList)
                .build();
        
        // 存入缓存，过期时间30分钟
        stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(result), 30, TimeUnit.MINUTES);
        
        return result;
    }
}
