package com.libre.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.LibreException;
import com.libre.mapper.UserRoleMapper;
import com.libre.pojo.dto.UserRoleDTO;
import com.libre.pojo.dto.UserRolePageDTO;
import com.libre.pojo.dto.admin.AddUserRoleDTO;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.RoleVO;
import com.libre.pojo.vo.UserRolePageVO;
import com.libre.result.PageResult;
import com.libre.service.UserRoleService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    // 解决自盗用导致的事务失效问题
    @Lazy
    @Resource
    private UserRoleService userRoleService;

    /**
     * 分页查询用户角色信息
     *
     * @param userRolePageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<UserRolePageVO>> pageQueryUserRole(UserRolePageDTO userRolePageDTO) {
        // 构建分页条件
        IPage<UserRolePageVO> page = PageUtil.createPage(userRolePageDTO);
        // 查询
        page = baseMapper.pageQueryUserRole(page,userRolePageDTO);

        return PageResult.<List<UserRolePageVO>>builder()
                .total(page.getTotal())
                .data(page.getRecords())
                .build();
    }

    /**
     * 添加用户角色信息
     *
     * @param userRoleDTO 用户角色信息
     */
    @Override
    public void addUserRole(UserRoleDTO userRoleDTO) {
        // 判断是否已存在相同的用户角色关系
        Long count = lambdaQuery()
                .eq(UserRole::getUserId, userRoleDTO.getUserId())
                .eq(UserRole::getRoleId, userRoleDTO.getRoleId())
                .count();

        if (count > 0) {
            throw new LibreException(ExceptionEnums.USER_ROLE_EXIST);
        }

        UserRole userRole = BeanUtil.copyProperties(userRoleDTO, UserRole.class);
        // 避免前端id残留数据影响
        if (userRole.getId() != null) userRole.setId(null);
        save(userRole);
    }

    /**
     * 修改用户角色信息
     *
     * @param userRoleDTO 用户角色信息
     */
    @Override
    public void modifyUserRole(UserRoleDTO userRoleDTO) {
        // 判断是否已存在不是当前修改记录的相同用户角色关系
        Long count = lambdaQuery()
                .eq(UserRole::getUserId, userRoleDTO.getUserId())
                .eq(UserRole::getRoleId, userRoleDTO.getRoleId())
                .ne(UserRole::getId, userRoleDTO.getId())
                .count();
        if (count > 0) {
            throw new LibreException(ExceptionEnums.USER_ROLE_EXIST);
        }

        UserRole userRole = BeanUtil.copyProperties(userRoleDTO, UserRole.class);
        updateById(userRole);
    }

    /**
     * 删除用户角色信息
     *
     * @param userRoleId 用户角色id
     */
    @Override
    public void deleteUserRole(Long userRoleId) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(UserRole::getIsDelete, System.currentTimeMillis())
                .eq(UserRole::getId, userRoleId)
                .update();
    }

    /**
     * 批量删除用户角色信息
     * @param ids 用户角色id列表
     */
    @Override
    public void deleteBatchUserRole(List<Long> ids) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(UserRole::getIsDelete, System.currentTimeMillis())
                .in(UserRole::getId, ids)
                .update();
    }

    /**
     * 获取用户角色信息
     * @param userId 用户id
     * @return 用户角色信息
     */
    @Override
    public List<RoleVO> getUserRoles(Long userId) {
        return baseMapper.getUserRoles(userId);
    }

    /**
     * 添加用户角色信息
     * @param addUserRoleDTO 添加用户角色信息
     */
    @Transactional
    @Override
    public void assignUserRoles(AddUserRoleDTO addUserRoleDTO) {
        // 删除用户旧角色信息
        userRoleService.lambdaUpdate()
                .set(UserRole::getIsDelete, System.currentTimeMillis())
                .eq(UserRole::getUserId, addUserRoleDTO.getUserId())
                .update();
        // 构建UserRole实体集合
        List<UserRole> userRoleList=new ArrayList<>(addUserRoleDTO.getRoleIds().size());
        for (Long roleId : addUserRoleDTO.getRoleIds()) {
            userRoleList.add(new UserRole(addUserRoleDTO.getUserId(), roleId));
        }

        // 批量添加
        userRoleService.saveBatch(userRoleList);
    }
}
