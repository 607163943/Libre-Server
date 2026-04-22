package com.libre.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONUtil;
import com.libre.constant.LendStatus;
import com.libre.constant.Role;
import com.libre.pojo.po.Lend;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.admin.RecentLendTrendItem;
import com.libre.pojo.vo.admin.HomeRecentLendTrendVO;
import com.libre.pojo.vo.admin.HomeTopBookItem;
import com.libre.pojo.vo.admin.HomeTopBookVO;
import com.libre.pojo.vo.admin.HomeTotalCardVO;
import com.libre.pojo.vo.user.*;
import com.libre.service.BookService;
import com.libre.service.HomeService;
import com.libre.service.LendService;
import com.libre.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class HomeServiceImpl implements HomeService {
    private final BookService bookService;

    private final UserRoleService userRoleService;

    private final LendService lendService;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 获取首页卡片数据
     *
     * @return 首页卡片数据
     */
    @Override
    public HomeTotalCardVO getHomeTotalCard() {
        String cacheKey = "admin:home:total-card";
        
        // 尝试从缓存中获取
        String cachedData = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toBean(cachedData, HomeTotalCardVO.class);
        }
        
        // 缓存未命中，查询数据库
        long bookCount = bookService.count();
        Long readerCount = userRoleService.lambdaQuery().eq(UserRole::getRoleId, Role.READER).count();

        // 获取当天借阅数量
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Long todayLendCount = lendService.lambdaQuery()
                .ge(Lend::getCreateTime, startOfDay)
                .count();

        HomeTotalCardVO result = HomeTotalCardVO.builder()
                .bookCount(bookCount)
                .readerCount(readerCount)
                .todayLendCount(todayLendCount)
                .build();
        
        // 存入缓存，过期时间5分钟
        stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(result), 5, TimeUnit.MINUTES);
        
        return result;
    }

    /**
     * 获取最近借阅趋势
     *
     * @return 最近借阅趋势
     */
    @Override
    public HomeRecentLendTrendVO getHomeRecentLendTrend() {
        String cacheKey = "admin:home:recent-lend-trend";
        
        // 尝试从缓存中获取
        String cachedData = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toBean(cachedData, HomeRecentLendTrendVO.class);
        }
        
        // 缓存未命中，查询数据库
        List<RecentLendTrendItem> recentLendTrendItemList = lendService.getRecentLendTrend();
        HomeRecentLendTrendVO result = HomeRecentLendTrendVO.builder()
                .recentLendTrendItemList(recentLendTrendItemList)
                .build();
        
        // 存入缓存，过期时间10分钟
        stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(result), 10, TimeUnit.MINUTES);
        
        return result;
    }

    /**
     * 获取图书排行top
     *
     * @return 图书排行top
     */
    @Override
    public HomeTopBookVO getHomeTopBook() {
        String cacheKey = "admin:home:top-book";
        
        // 尝试从缓存中获取
        String cachedData = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toBean(cachedData, HomeTopBookVO.class);
        }
        
        // 缓存未命中，查询数据库
        List<HomeTopBookItem> homeTopBookItemList = lendService.getHomeTopBook();
        HomeTopBookVO result = HomeTopBookVO.builder()
                .homeTopBookItemList(homeTopBookItemList)
                .build();
        
        // 存入缓存，过期时间15分钟
        stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(result), 15, TimeUnit.MINUTES);
        
        return result;
    }

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
