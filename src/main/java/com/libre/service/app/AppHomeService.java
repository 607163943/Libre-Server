package com.libre.service.app;

import com.libre.pojo.vo.app.HomeTopLatestBookVO;
import com.libre.pojo.vo.app.HomeTopLendBookVO;
import com.libre.pojo.vo.app.HomeUserTotalVO;

public interface AppHomeService {
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
