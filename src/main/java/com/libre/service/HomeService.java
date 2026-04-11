package com.libre.service;

import com.libre.pojo.vo.HomeRecentLendTrendVO;
import com.libre.pojo.vo.HomeTopBookVO;
import com.libre.pojo.vo.HomeTotalCardVO;

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
}
