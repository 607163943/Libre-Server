package com.libre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.libre.pojo.dto.LendPageDTO;
import com.libre.pojo.po.Lend;
import com.libre.pojo.vo.LendPageVO;
import org.apache.ibatis.annotations.Param;

public interface LendMapper extends BaseMapper<Lend> {
    /**
     * 分页查询借阅信息
     * @param page 分页条件
     * @param lendPageDTO 查询参数
     * @return 分页结果
     */
    IPage<LendPageVO> pageQueryLend(@Param("page") IPage<LendPageVO> page,@Param("lendPageDTO") LendPageDTO lendPageDTO);
}
