package com.libre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.libre.pojo.dto.UserRolePageDTO;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.UserRolePageVO;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 分页查询用户角色信息
     * @param page 分页条件
     * @param userRolePageDTO 查询参数
     * @return 分页结果
     */
    IPage<UserRolePageVO> pageQueryUserRole(@Param("page") IPage<UserRolePageVO> page,@Param("userRolePageDTO") UserRolePageDTO userRolePageDTO);
}
