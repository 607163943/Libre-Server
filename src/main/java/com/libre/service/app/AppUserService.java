package com.libre.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.app.UserPasswordDTO;
import com.libre.pojo.dto.app.UserProfileDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.app.UserProfileVO;

public interface AppUserService extends IService<User> {
    /**
     * 获取当前用户个人信息
     * @param userId 用户id
     * @return 用户个人信息
     */
    UserProfileVO getUserProfile(Long userId);

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
