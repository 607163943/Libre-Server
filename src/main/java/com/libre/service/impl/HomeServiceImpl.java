package com.libre.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.libre.constant.LendStatus;
import com.libre.constant.Role;
import com.libre.pojo.po.Lend;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.RecentLendTrendItem;
import com.libre.pojo.vo.admin.HomeRecentLendTrendVO;
import com.libre.pojo.vo.admin.HomeTopBookItem;
import com.libre.pojo.vo.admin.HomeTopBookVO;
import com.libre.pojo.vo.admin.HomeTotalCardVO;
import com.libre.pojo.vo.user.HomeUserTotalVO;
import com.libre.service.BookService;
import com.libre.service.HomeService;
import com.libre.service.LendService;
import com.libre.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HomeServiceImpl implements HomeService {
    private final BookService bookService;

    private final UserRoleService userRoleService;

    private final LendService lendService;

    /**
     * 获取首页卡片数据
     *
     * @return 首页卡片数据
     */
    @Override
    public HomeTotalCardVO getHomeTotalCard() {
        long bookCount = bookService.count();
        Long readerCount = userRoleService.lambdaQuery().eq(UserRole::getRoleId, Role.READER).count();

        // 获取当天借阅数量
        LocalDateTime startOfDay = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        Long todayLendCount = lendService.lambdaQuery()
                .ge(Lend::getCreateTime, startOfDay)
                .count();

        return HomeTotalCardVO.builder()
                .bookCount(bookCount)
                .readerCount(readerCount)
                .todayLendCount(todayLendCount)
                .build();
    }

    /**
     * 获取最近借阅趋势
     *
     * @return 最近借阅趋势
     */
    @Override
    public HomeRecentLendTrendVO getHomeRecentLendTrend() {
        List<RecentLendTrendItem> recentLendTrendItemList = lendService.getRecentLendTrend();
        return HomeRecentLendTrendVO.builder()
                .recentLendTrendItemList(recentLendTrendItemList)
                .build();
    }

    /**
     * 获取图书排行top
     * @return 图书排行top
     */
    @Override
    public HomeTopBookVO getHomeTopBook() {
        List<HomeTopBookItem> homeTopBookItemList= lendService.getHomeTopBook();
        return HomeTopBookVO.builder()
                .homeTopBookItemList(homeTopBookItemList)
                .build();
    }

    /**
     * 获取用户借阅数据
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
}
