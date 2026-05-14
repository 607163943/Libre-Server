package com.libre.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.common.UserPasswordDTO;
import com.libre.pojo.dto.common.UserProfileDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.common.UserProfileVO;

public interface CommonUserService extends IService<User> {
    /**
     * 初始化超管账号
     */
    void initSuperAdmin();

    /**
     * 获取当前用户个人信息
     * @return 用户个人信息
     */
    UserProfileVO getUserProfile();

    /**
     * 修改当前用户个人信息
     * @param userProfileDTO 用户个人信息
     */
    void modifyUserProfile(UserProfileDTO userProfileDTO);

    /**
     * 修改当前用户密码
     * @param userPasswordDTO 用户密码信息
     */
    void modifyUserPassword(UserPasswordDTO userPasswordDTO);
}
