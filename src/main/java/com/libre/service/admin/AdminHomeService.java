package com.libre.service.admin;

import com.libre.pojo.vo.admin.HomeRecentLendTrendVO;
import com.libre.pojo.vo.admin.HomeTopBookVO;
import com.libre.pojo.vo.admin.HomeTotalCardVO;

public interface AdminHomeService {
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
