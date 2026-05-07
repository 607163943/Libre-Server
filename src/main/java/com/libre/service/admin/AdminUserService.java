package com.libre.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.admin.UserDTO;
import com.libre.pojo.dto.admin.UserPageDTO;
import com.libre.pojo.dto.admin.UserProfileDTO;
import com.libre.pojo.dto.admin.UserPasswordDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.admin.UserPageVO;
import com.libre.pojo.vo.admin.UserProfileVO;
import com.libre.result.PageResult;

import java.util.List;

public interface AdminUserService extends IService<User> {
    /**
     * 分页查询用户信息
     * @param userPageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<UserPageVO>> pageQueryUser(UserPageDTO userPageDTO);

    /**
     * 添加用户
     * @param userDTO 用户信息
     */
    void addUser(UserDTO userDTO);

    /**
     * 修改用户
     * @param userDTO 用户信息
     */
    void modifyUser(UserDTO userDTO);

    /**
     * 删除用户
     * @param userId 用户id
     */
    void deleteUser(Long userId);

    /**
     * 批量删除用户
     * @param ids 用户id列表
     */
    void deleteBatchUser(List<Long> ids);

    /**
     * 修改用户状态
     * @param userId 用户id
     * @param state 状态
     */
    void modifyUserState(Long userId, Integer state);

    /**
     * 获取指定用户个人信息
     * @return 用户个人信息
     */
    UserProfileVO getUserProfile();

    /**
     * 修改指定用户个人信息
     * @param userProfileDTO 用户个人信息
     */
    void modifyUserProfile(UserProfileDTO userProfileDTO);

    /**
     * 修改指定用户密码
     * @param userPasswordDTO 用户密码信息
     */
    void modifyUserPassword(UserPasswordDTO userPasswordDTO);
}
