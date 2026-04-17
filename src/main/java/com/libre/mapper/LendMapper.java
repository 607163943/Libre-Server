package com.libre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.libre.pojo.dto.LendPageDTO;
import com.libre.pojo.dto.user.MyLendPageDTO;
import com.libre.pojo.po.Lend;
import com.libre.pojo.vo.LendPageVO;
import com.libre.pojo.vo.admin.HomeTopBookItem;
import com.libre.pojo.vo.admin.RecentLendTrendItem;
import com.libre.pojo.vo.user.HomeTopLendBookItem;
import com.libre.pojo.vo.user.MyLendBookVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LendMapper extends BaseMapper<Lend> {
    /**
     * 分页查询借阅信息
     * @param page 分页条件
     * @param lendPageDTO 查询参数
     * @return 分页结果
     */
    IPage<LendPageVO> pageQueryLend(@Param("page") IPage<LendPageVO> page,@Param("lendPageDTO") LendPageDTO lendPageDTO);

    /**
     * 获取最近借阅趋势
     * @return 最近借阅趋势
     */
    List<RecentLendTrendItem> getRecentLendTrend();

    /**
     * 获取图书排行
     * @return 图书排行
     */
    List<HomeTopBookItem> getHomeTopBook();

    /**
     * 获取用户借阅排行
     * @return 用户借阅排行
     */
    List<HomeTopLendBookItem> getHomeTopLendBookList();

    /**
     * 分页查询用户借阅书籍
     * @param page 分页条件
     * @param myLendPageDTO 查询参数
     * @return 分页结果
     */
    IPage<MyLendBookVO> pageQueryMyLend(@Param("page") IPage<MyLendBookVO> page, @Param("myLendPageDTO") MyLendPageDTO myLendPageDTO);
}
