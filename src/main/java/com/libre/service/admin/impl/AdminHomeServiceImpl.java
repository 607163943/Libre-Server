package com.libre.service.admin.impl;

import cn.hutool.json.JSONUtil;
import com.libre.constant.Role;
import com.libre.pojo.po.Lend;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.admin.*;
import com.libre.service.admin.AdminBookService;
import com.libre.service.admin.AdminHomeService;
import com.libre.service.admin.AdminLendService;
import com.libre.service.admin.AdminUserRoleService;
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
public class AdminHomeServiceImpl implements AdminHomeService {
    private final AdminBookService adminBookService;

    private final AdminUserRoleService adminUserRoleService;

    private final AdminLendService adminLendService;

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
        long bookCount = adminBookService.count();
        Long readerCount = adminUserRoleService.lambdaQuery().eq(UserRole::getRoleId, Role.READER).count();

        // 获取当天借阅数量
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Long todayLendCount = adminLendService.lambdaQuery()
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
        List<RecentLendTrendItem> recentLendTrendItemList = adminLendService.getRecentLendTrend();
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
        List<HomeTopBookItem> homeTopBookItemList = adminLendService.getHomeTopBook();
        HomeTopBookVO result = HomeTopBookVO.builder()
                .homeTopBookItemList(homeTopBookItemList)
                .build();

        // 存入缓存，过期时间15分钟
        stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(result), 15, TimeUnit.MINUTES);

        return result;
    }
}
