package com.libre.service.admin.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.UserException;
import com.libre.mapper.UserMapper;
import com.libre.pojo.dto.UserDTO;
import com.libre.pojo.dto.UserPageDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.UserPageVO;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminUserService;
import com.libre.util.PageUtil;
import com.libre.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminUserServiceImpl extends ServiceImpl<UserMapper, User> implements AdminUserService {
    private final SecurityUtil securityUtil;

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
        if (StrUtil.isBlank(user.getName())) {
            user.setName(user.getUsername());
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
        if (StrUtil.isBlank(user.getName())) {
            user.setName(user.getUsername());
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
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(User::getIsDelete, System.currentTimeMillis())
                .in(User::getId, ids)
                .update();
    }
}
