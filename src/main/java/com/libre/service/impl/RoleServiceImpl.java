package com.libre.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.RoleException;
import com.libre.mapper.RoleMapper;
import com.libre.pojo.dto.RoleDTO;
import com.libre.pojo.dto.RolePageDTO;
import com.libre.pojo.po.Role;
import com.libre.pojo.vo.RolePageVO;
import com.libre.result.PageResult;
import com.libre.service.RoleService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 分页查询角色信息
     *
     * @param rolePageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<RolePageVO>> pageQueryRole(RolePageDTO rolePageDTO) {
        // 构建分页条件
        IPage<Role> page = PageUtil.createPage(rolePageDTO);
        // 查询
        page = lambdaQuery()
                .like(StrUtil.isNotBlank(rolePageDTO.getRoleName())
                        , Role::getRoleName, rolePageDTO.getRoleName())
                .page(page);
        // 构建VO数据
        List<RolePageVO> rolePageVOS = BeanUtil.copyToList(page.getRecords(), RolePageVO.class);

        return PageResult.<List<RolePageVO>>builder()
                .total(page.getTotal())
                .data(rolePageVOS)
                .build();
    }

    /**
     * 添加角色信息
     *
     * @param roleDTO 角色信息
     */
    @Override
    public void addRole(RoleDTO roleDTO) {
        // 判断是否已存在同名角色
        Long roleCount = lambdaQuery()
                .eq(Role::getRoleName, roleDTO.getRoleName())
                .count();

        if (roleCount > 0) {
            throw new RoleException(ExceptionEnums.ROLE_EXIST);
        }

        Role role = BeanUtil.copyProperties(roleDTO, Role.class);
        // 避免前端id残留数据影响
        if (role.getId() != null) role.setId(null);
        save(role);
        
        // 清除缓存
        stringRedisTemplate.delete("admin:role:all");
    }

    /**
     * 修改角色信息
     *
     * @param roleDTO 角色信息
     */
    @Override
    public void modifyRole(RoleDTO roleDTO) {
        // 判断是否已存在不是当前修改角色的同名角色
        Long count = lambdaQuery()
                .eq(Role::getRoleName, roleDTO.getRoleName())
                .ne(Role::getId, roleDTO.getId())
                .count();
        if (count > 0) {
            throw new RoleException(ExceptionEnums.ROLE_EXIST);
        }

        Role role = BeanUtil.copyProperties(roleDTO, Role.class);
        updateById(role);
        
        // 清除缓存
        stringRedisTemplate.delete("admin:role:all");
    }

    /**
     * 删除角色信息
     *
     * @param roleId 角色id
     */
    @Override
    public void deleteRole(Long roleId) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Role::getIsDelete, System.currentTimeMillis())
                .eq(Role::getId, roleId)
                .update();
        
        // 清除缓存
        stringRedisTemplate.delete("admin:role:all");
    }

    /**
     * 批量删除角色信息
     * @param ids 角色id列表
     */
    @Override
    public void deleteBatchRole(List<Long> ids) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Role::getIsDelete, System.currentTimeMillis())
                .in(Role::getId, ids)
                .update();
        
        // 清除缓存
        stringRedisTemplate.delete("admin:role:all");
    }

    /**
     * 获取所有角色列表（带缓存）
     * @return 角色列表
     */
    @Override
    public List<Role> getAllRole() {
        String cacheKey = "admin:role:all";
        
        // 尝试从缓存中获取
        String cachedData = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toList(cachedData, Role.class);
        }
        
        // 缓存未命中，查询数据库
        List<Role> roleList = list();
        
        // 存入缓存，过期时间30分钟
        stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(roleList), 30, TimeUnit.MINUTES);
        
        return roleList;
    }
}
