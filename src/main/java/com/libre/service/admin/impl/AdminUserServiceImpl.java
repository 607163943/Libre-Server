package com.libre.service.admin.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.RoleCode;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.UserException;
import com.libre.mapper.UserMapper;
import com.libre.pojo.dto.admin.UserDTO;
import com.libre.pojo.dto.admin.UserPageDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.admin.RoleVO;
import com.libre.pojo.vo.admin.UserPageVO;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminUserRoleService;
import com.libre.service.admin.AdminUserService;
import com.libre.util.PageUtil;
import com.libre.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl extends ServiceImpl<UserMapper, User> implements AdminUserService {
    private final SecurityUtil securityUtil;
    private final AdminUserRoleService adminUserRoleService;

    /**
     * 分页查询用户信息
     *
     * @param userPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<UserPageVO>> pageQueryUser(UserPageDTO userPageDTO) {
        // 构建分页条件
        IPage<User> page = PageUtil.createPage(userPageDTO);
        // 查询
        page = lambdaQuery()
                .like(StrUtil.isNotBlank(userPageDTO.getUsername())
                        , User::getUsername, userPageDTO.getUsername())
                .like(StrUtil.isNotBlank(userPageDTO.getName())
                        , User::getNickName, userPageDTO.getName())
                .page(page);
        // 构建VO数据
        List<UserPageVO> userPageVOS = BeanUtil.copyToList(page.getRecords(), UserPageVO.class);

        return PageResult.<List<UserPageVO>>builder()
                .total(page.getTotal())
                .data(userPageVOS)
                .build();
    }

    /**
     * 添加用户信息
     *
     * @param userDTO 用户信息
     */
    @Override
    public void addUser(UserDTO userDTO) {
        // 判断是否已存在同名用户
        Long userCount = lambdaQuery()
                .eq(User::getUsername, userDTO.getUsername())
                .count();

        if (userCount > 0) {
            throw new UserException(ExceptionEnums.USER_EXIST);
        }

        User user = BeanUtil.copyProperties(userDTO, User.class);
        // 没设置姓名则默认使用用户名
        if (StrUtil.isBlank(user.getNickName())) {
            user.setNickName(user.getUsername());
        }

        // 判断邮箱是否绑定
        if(StrUtil.isNotBlank(user.getEmail())) {
            Long emailCount = lambdaQuery()
                    .eq(User::getEmail, user.getEmail())
                    .count();
            if(emailCount>0) {
                throw new UserException(ExceptionEnums.USER_EMAIL_ALREADY_EXIST);
            }
        }

        // 判断手机号是否绑定
        if(StrUtil.isNotBlank(user.getPhone())) {
            Long phoneCount = lambdaQuery()
                    .eq(User::getPhone, user.getPhone())
                    .count();
            if(phoneCount>0) {
                throw new UserException(ExceptionEnums.USER_PHONE_ALREADY_EXIST);
            }
        }

        user.setPassword(securityUtil.generatePassword(user.getPassword()));
        save(user);
    }

    /**
     * 修改用户信息
     *
     * @param userDTO 用户信息
     */
    @Override
    public void modifyUser(UserDTO userDTO) {
        // 权限校验
        checkModifyPermission(userDTO.getId());

        // 判断是否已存在不是当前修改用户的同名用户
        Long count = lambdaQuery()
                .eq(User::getUsername, userDTO.getUsername())
                .ne(User::getId, userDTO.getId())
                .count();
        if (count > 0) {
            throw new UserException(ExceptionEnums.USER_EXIST);
        }

        User user = BeanUtil.copyProperties(userDTO, User.class);
        // 没设置姓名则默认使用用户名
        if (StrUtil.isBlank(user.getNickName())) {
            user.setNickName(user.getUsername());
        }

        // 判断邮箱是否绑定
        if(StrUtil.isNotBlank(user.getEmail())) {
            Long emailCount = lambdaQuery()
                    .eq(User::getEmail, user.getEmail())
                    .ne(User::getId, userDTO.getId())
                    .count();
            if(emailCount>0) {
                throw new UserException(ExceptionEnums.USER_EMAIL_ALREADY_EXIST);
            }
        }

        // 判断手机号是否绑定
        if(StrUtil.isNotBlank(user.getPhone())) {
            Long phoneCount = lambdaQuery()
                    .eq(User::getPhone, user.getPhone())
                    .ne(User::getId, userDTO.getId())
                    .count();
            if(phoneCount>0) {
                throw new UserException(ExceptionEnums.USER_PHONE_ALREADY_EXIST);
            }
        }

        user.setPassword(securityUtil.generatePassword(user.getPassword()));
        updateById(user);
    }

    /**
     * 删除用户信息
     *
     * @param userId 用户id
     */
    @Override
    public void deleteUser(Long userId) {
        // 权限校验
        checkDeletePermission(userId);
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(User::getIsDelete, System.currentTimeMillis())
                .eq(User::getId, userId)
                .update();
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 用户id列表
     */
    @Override
    public void deleteBatchUser(List<Long> ids) {
        // 所有id进行一次校验
        ids.forEach(this::checkDeletePermission);
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(User::getIsDelete, System.currentTimeMillis())
                .in(User::getId, ids)
                .update();
    }

    /**
     * 修改用户状态
     * @param userId 用户id
     * @param state 状态
     */
    @Override
    public void modifyUserState(Long userId, Integer state) {
        // 权限校验
        checkModifyPermission(userId);

        lambdaUpdate()
                .set(User::getState, state)
                .eq(User::getId, userId)
                .update();
    }

    /**
     * 校验修改用户信息的权限
     * 规则：超级管理员能修改管理员、读者；管理员能修改读者
     * @param targetUserId 目标用户ID
     */
    private void checkModifyPermission(Long targetUserId) {
        // 获取当前登录用户ID
        Long currentUserId = StpUtil.getLoginIdAsLong();
        
        // 如果是修改自己的信息，不需要校验（个人信息修改走另一个接口）
        if (currentUserId.equals(targetUserId)) {
            return;
        }

        // 获取当前登录用户的角色列表
        List<RoleVO> currentUserRoles = adminUserRoleService.getUserRoles(currentUserId);
        List<Long> currentUserRoleIds = currentUserRoles.stream()
                .map(RoleVO::getId)
                .collect(Collectors.toList());

        // 获取目标用户的角色列表
        List<RoleVO> targetUserRoles = adminUserRoleService.getUserRoles(targetUserId);
        List<Long> targetUserRoleIds = targetUserRoles.stream()
                .map(RoleVO::getId)
                .collect(Collectors.toList());

        // 判断当前用户是否有权限修改目标用户
        boolean hasPermission = false;

        // 超级管理员(1)可以修改管理员(2)和读者(3)
        if (currentUserRoleIds.contains(RoleCode.SUPER_ADMIN.longValue())) {
            hasPermission = true;
        }
        // 管理员(2)可以修改读者(3)
        else if (currentUserRoleIds.contains(RoleCode.ADMIN.longValue())) {
            // 检查目标用户是否为读者（不包含管理员或超级管理员角色）
            boolean isReader = !targetUserRoleIds.contains(RoleCode.SUPER_ADMIN.longValue())
                    && !targetUserRoleIds.contains(RoleCode.ADMIN.longValue());
            if (isReader) {
                hasPermission = true;
            }
        }

        if (!hasPermission) {
            throw new UserException(ExceptionEnums.USER_PERMISSION_DENIED);
        }
    }

    /**
     * 校验删除用户信息的权限
     * 规则：超级管理员能删除管理员、读者；管理员能删除读者；不能删除自己
     * @param targetUserId 目标用户ID
     */
    private void checkDeletePermission(Long targetUserId) {
        // 获取当前登录用户ID
        Long currentUserId = StpUtil.getLoginIdAsLong();
        
        // 不能删除自己
        if (currentUserId.equals(targetUserId)) {
            throw new UserException(ExceptionEnums.USER_PERMISSION_DENIED);
        }

        // 获取当前登录用户的角色列表
        List<RoleVO> currentUserRoles = adminUserRoleService.getUserRoles(currentUserId);
        List<Long> currentUserRoleIds = currentUserRoles.stream()
                .map(RoleVO::getId)
                .collect(Collectors.toList());

        // 获取目标用户的角色列表
        List<RoleVO> targetUserRoles = adminUserRoleService.getUserRoles(targetUserId);
        List<Long> targetUserRoleIds = targetUserRoles.stream()
                .map(RoleVO::getId)
                .collect(Collectors.toList());

        // 判断当前用户是否有权限删除目标用户
        boolean hasPermission = false;

        // 超级管理员(1)可以删除管理员(2)和读者(3)
        if (currentUserRoleIds.contains(RoleCode.SUPER_ADMIN.longValue())) {
            hasPermission = true;
        }
        // 管理员(2)可以删除读者(3)
        else if (currentUserRoleIds.contains(RoleCode.ADMIN.longValue())) {
            // 检查目标用户是否为读者（不包含管理员或超级管理员角色）
            boolean isReader = !targetUserRoleIds.contains(RoleCode.SUPER_ADMIN.longValue())
                    && !targetUserRoleIds.contains(RoleCode.ADMIN.longValue());
            if (isReader) {
                hasPermission = true;
            }
        }

        if (!hasPermission) {
            throw new UserException(ExceptionEnums.USER_PERMISSION_DENIED);
        }
    }
}
