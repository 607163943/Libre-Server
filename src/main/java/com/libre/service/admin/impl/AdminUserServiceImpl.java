package com.libre.service.admin.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.RoleCode;
import com.libre.constant.UserState;
import com.libre.enums.ExceptionEnums;
import com.libre.enums.UserRoleEnums;
import com.libre.exception.UserException;
import com.libre.mapper.LendMapper;
import com.libre.mapper.UserMapper;
import com.libre.pojo.dto.admin.UserDTO;
import com.libre.pojo.dto.admin.UserPageDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.po.UserRole;
import com.libre.pojo.vo.admin.RoleVO;
import com.libre.pojo.vo.admin.UserPageVO;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminUserRoleService;
import com.libre.service.admin.AdminUserService;
import com.libre.util.PageUtil;
import com.libre.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl extends ServiceImpl<UserMapper, User> implements AdminUserService {
    private final SecurityUtil securityUtil;
    private final AdminUserRoleService adminUserRoleService;

    private final LendMapper lendMapper;

    private final AdminUserRoleService userRoleService;

    // 处理自调用事务
    @Lazy
    @Resource
    private AdminUserService userService;

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
                .like(StrUtil.isNotBlank(userPageDTO.getNickName())
                        , User::getNickName, userPageDTO.getNickName())
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
    @Transactional
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
        if (StrUtil.isNotBlank(user.getEmail())) {
            Long emailCount = lambdaQuery()
                    .eq(User::getEmail, user.getEmail())
                    .count();
            if (emailCount > 0) {
                throw new UserException(ExceptionEnums.USER_EMAIL_ALREADY_EXIST);
            }
        }

        // 判断手机号是否绑定
        if (StrUtil.isNotBlank(user.getPhone())) {
            Long phoneCount = lambdaQuery()
                    .eq(User::getPhone, user.getPhone())
                    .count();
            if (phoneCount > 0) {
                throw new UserException(ExceptionEnums.USER_PHONE_ALREADY_EXIST);
            }
        }

        user.setPassword(securityUtil.generatePassword(user.getPassword()));
        userService.save(user);

        // 新用户默认添加读者角色
        UserRole userRole = UserRole.builder()
                .userId(user.getId())
                .roleId(UserRoleEnums.READER.getId())
                .build();
        userRoleService.save(userRole);
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
        if (StrUtil.isNotBlank(user.getEmail())) {
            Long emailCount = lambdaQuery()
                    .eq(User::getEmail, user.getEmail())
                    .ne(User::getId, userDTO.getId())
                    .count();
            if (emailCount > 0) {
                throw new UserException(ExceptionEnums.USER_EMAIL_ALREADY_EXIST);
            }
        }

        // 判断手机号是否绑定
        if (StrUtil.isNotBlank(user.getPhone())) {
            Long phoneCount = lambdaQuery()
                    .eq(User::getPhone, user.getPhone())
                    .ne(User::getId, userDTO.getId())
                    .count();
            if (phoneCount > 0) {
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
    @Transactional
    @Override
    public void deleteUser(Long userId) {
        // 权限校验
        checkDeletePermission(userId);
        userService.lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(User::getIsDelete, System.currentTimeMillis())
                .eq(User::getId, userId)
                .update();

        // 删除用户角色关系
        userRoleService.lambdaUpdate()
                .set(UserRole::getIsDelete, System.currentTimeMillis())
                .eq(UserRole::getUserId, userId)
                .update();
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 用户id列表
     */
    @Transactional
    @Override
    public void deleteBatchUser(List<Long> ids) {
        // 所有id进行一次校验
        ids.forEach(this::checkDeletePermission);
        userService.lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(User::getIsDelete, System.currentTimeMillis())
                .in(User::getId, ids)
                .update();

        // 删除用户角色关系
        userRoleService.lambdaUpdate()
                .set(UserRole::getIsDelete, System.currentTimeMillis())
                .in(UserRole::getUserId, ids)
                .update();
    }

    /**
     * 修改用户状态
     *
     * @param userId 用户id
     * @param state  状态
     */
    @Override
    public void modifyUserState(Long userId, Integer state) {
        // 权限校验
        checkModifyPermission(userId);

        // 用户状态从逾期借阅禁用到启用时
        Long userCount = lambdaQuery()
                .eq(User::getId, userId)
                .eq(User::getState, UserState.OVERTIME_DISABLE)
                .count();

        // 重新启用逾期借阅用户需要检查该用户是否还存在超时逾期借阅
        if (userCount > 0) {
            // 查询该用户是否存在逾期截止借阅+10天的未还借阅记录
            int countOverduePlusTenDays = lendMapper.countOverduePlusTenDays(userId);
            if (countOverduePlusTenDays > 0 && state.equals(UserState.NORMAL)) {
                throw new UserException(ExceptionEnums.USER_HAS_OVERDUE_PLUS_TEN_DAYS_LEND);
            }
        }

        lambdaUpdate()
                .set(User::getState, state)
                .eq(User::getId, userId)
                .update();
    }

    /**
     * 校验修改用户信息的权限
     * 规则：超级管理员能修改管理员、读者；管理员能修改读者
     *
     * @param targetUserId 目标用户ID
     */
    private void checkModifyPermission(Long targetUserId) {
        // 获取当前登录用户ID
        Long currentUserId = StpUtil.getLoginIdAsLong();

        // 如果是修改自己的信息，走另一个接口
        if (currentUserId.equals(targetUserId)) {
            throw new UserException(ExceptionEnums.USER_MODIFY_SELF);
        }

        checkPermission(currentUserId, targetUserId);
    }

    /**
     * 判断是否具备权限
     *
     * @param currentUserId 当前登录用户ID
     * @param targetUserId  目标用户ID
     */
    private void checkPermission(Long currentUserId, Long targetUserId) {
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
        boolean hasPermission = isHasPermission(currentUserRoleIds, targetUserRoleIds);

        if (!hasPermission) {
            throw new UserException(ExceptionEnums.USER_PERMISSION_DENIED);
        }
    }

    /**
     * 判断当前用户是否具备权限
     *
     * @param currentUserRoleIds 当前用户的角色ID列表
     * @param targetUserRoleIds  目标用户的角色ID列表
     * @return 是否具备权限
     */
    private boolean isHasPermission(List<Long> currentUserRoleIds, List<Long> targetUserRoleIds) {
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
        return hasPermission;
    }

    /**
     * 校验删除用户信息的权限
     * 规则：超级管理员能删除管理员、读者；管理员能删除读者；不能删除自己
     *
     * @param targetUserId 目标用户ID
     */
    private void checkDeletePermission(Long targetUserId) {
        // 获取当前登录用户ID
        Long currentUserId = StpUtil.getLoginIdAsLong();

        // 不能删除自己
        if (currentUserId.equals(targetUserId)) {
            throw new UserException(ExceptionEnums.USER_PERMISSION_DENIED);
        }

        checkPermission(currentUserId, targetUserId);
    }
}
