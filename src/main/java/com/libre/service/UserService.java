package com.libre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.UserDTO;
import com.libre.pojo.dto.UserPageDTO;
import com.libre.pojo.po.User;
import com.libre.pojo.vo.UserPageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface UserService extends IService<User> {
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
}
