package com.libre.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.UserException;
import com.libre.mapper.UserMapper;
import com.libre.pojo.dto.UserDTO;
import com.libre.pojo.dto.UserPageDTO;
import com.libre.pojo.dto.user.UserPasswordDTO;
import com.libre.pojo.dto.user.UserProfileDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.UserPageVO;
import com.libre.pojo.vo.user.UserProfileVO;
import com.libre.result.PageResult;
import com.libre.service.UserService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

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
                        , User::getName, userPageDTO.getName())
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
        if(StrUtil.isBlank(user.getName())) {
            user.setName(user.getUsername());
        }
        // 避免前端id残留数据影响
        if (user.getId() != null) user.setId(null);
        save(user);
    }

    /**
     * 修改用户信息
     *
     * @param userDTO 用户信息
     */
    @Override
    public void modifyUser(UserDTO userDTO) {
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
        if(StrUtil.isBlank(user.getName())) {
            user.setName(user.getUsername());
        }
        updateById(user);
    }

    /**
     * 删除用户信息
     *
     * @param userId 用户id
     */
    @Override
    public void deleteUser(Long userId) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(User::getIsDelete, System.currentTimeMillis())
                .eq(User::getId, userId)
                .update();
    }

    /**
     * 批量删除用户信息
     * @param ids 用户id列表
     */
    @Override
    public void deleteBatchUser(List<Long> ids) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(User::getIsDelete, System.currentTimeMillis())
                .in(User::getId, ids)
                .update();
    }

    /**
     * 获取当前用户个人信息
     *
     * @param userId 用户id
     * @return 用户个人信息
     */
    @Override
    public UserProfileVO getUserProfile(Long userId) {
        User user = getById(userId);
        if (user == null) {
            throw new UserException(ExceptionEnums.USER_NOT_EXIST);
        }
        return BeanUtil.copyProperties(user, UserProfileVO.class);
    }

    /**
     * 修改当前用户个人信息
     * @param userProfileDTO 用户个人信息
     */
    @Override
    public void modifyUserProfile(UserProfileDTO userProfileDTO) {
        User user = getById(StpUtil.getLoginIdAsLong());
        if (user == null) {
            throw new UserException(ExceptionEnums.USER_NOT_EXIST);
        }

        // 更新姓名
        if (StrUtil.isNotBlank(userProfileDTO.getName())) {
            user.setName(userProfileDTO.getName());
        }
        updateById(user);
    }

    /**
     * 修改当前用户密码
     * @param userPasswordDTO 用户密码信息
     */
    @Override
    public void modifyUserPassword(UserPasswordDTO userPasswordDTO) {
        Long userId = StpUtil.getLoginIdAsLong();
        User user = getById(userId);
        if (user == null) {
            throw new UserException(ExceptionEnums.USER_NOT_EXIST);
        }

        // 校验旧密码
        if (!BCrypt.checkpw(userPasswordDTO.getOldPassword(), user.getPassword())) {
            throw new UserException(ExceptionEnums.USER_PASSWORD_ERROR);
        }

        // 更新新密码
        user.setPassword(BCrypt.hashpw(userPasswordDTO.getNewPassword()));
        updateById(user);
    }
}
