package com.libre.service;

import com.libre.pojo.vo.admin.HomeRecentLendTrendVO;
import com.libre.pojo.vo.admin.HomeTopBookVO;
import com.libre.pojo.vo.admin.HomeTotalCardVO;
import com.libre.pojo.vo.user.HomeTopLatestBookVO;
import com.libre.pojo.vo.user.HomeTopLendBookVO;
import com.libre.pojo.vo.user.HomeUserTotalVO;

public interface HomeService {
    /**
     * 获取首页统计数据
     * @return HomeTotalCardVO
     */
    HomeTotalCardVO getHomeTotalCard();

    /**
     * 获取最近一周的借阅趋势
     * @return HomeRecentLendTrendVO
     */
    HomeRecentLendTrendVO getHomeRecentLendTrend();

    /**
     * 获取首页热门图书
     * @return HomeTopBookVO
     */
    HomeTopBookVO getHomeTopBook();

    /**
     * 获取用户首页统计数据
     * @return HomeUserTotalVO
     */
    HomeUserTotalVO getHomeUserTotal();

    /**
     * 获取用户首页热门借阅图书
     * @return HomeTopLendBookVO
     */
    HomeTopLendBookVO getHomeTopLendBook();

    /**
     * 获取用户首页最新图书
     * @return HomeTopLatestBookVO
     */
    HomeTopLatestBookVO getHomeTopLatestBook();
}
