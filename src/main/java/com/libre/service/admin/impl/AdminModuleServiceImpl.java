package com.libre.service.admin.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.AdminExceptionEnums;
import com.libre.exception.ModuleException;
import com.libre.mapper.ModuleMapper;
import com.libre.pojo.dto.ModuleDTO;
import com.libre.pojo.dto.ModulePageDTO;
import com.libre.pojo.po.Module;
import com.libre.pojo.vo.ModulePageVO;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminModuleService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AdminModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements AdminModuleService {
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 分页查询模块信息
     *
     * @param modulePageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<ModulePageVO>> pageQueryModule(ModulePageDTO modulePageDTO) {
        // 构建分页条件
        IPage<Module> page = PageUtil.createPage(modulePageDTO);
        // 查询
        page = lambdaQuery()
                .like(StrUtil.isNotBlank(modulePageDTO.getModuleName())
                        , Module::getModuleName, modulePageDTO.getModuleName())
                .eq(modulePageDTO.getClientType() != null
                        , Module::getClientType, modulePageDTO.getClientType())
                .page(page);
        // 构建VO数据
        List<ModulePageVO> modulePageVOS = BeanUtil.copyToList(page.getRecords(), ModulePageVO.class);

        return PageResult.<List<ModulePageVO>>builder()
                .total(page.getTotal())
                .data(modulePageVOS)
                .build();
    }

    /**
     * 添加模块信息
     *
     * @param moduleDTO 模块信息
     */
    @Override
    public void addModule(ModuleDTO moduleDTO) {
        // 判断是否已存在同名模块
        Long moduleCount = lambdaQuery()
                // 同客户端下同名、同编码模块均不允许
                .eq(Module::getModuleKey, moduleDTO.getModuleKey())
                .eq(Module::getClientType,moduleDTO.getClientType())
                .or()
                .eq(Module::getModuleName, moduleDTO.getModuleName())
                .eq(Module::getClientType, moduleDTO.getClientType())
                .count();

        if (moduleCount > 0) {
            throw new ModuleException(AdminExceptionEnums.MODULE_EXIST);
        }

        Module module = BeanUtil.copyProperties(moduleDTO, Module.class);
        // 避免前端id残留数据影响
        if (module.getId() != null) module.setId(null);
        save(module);

        // 清除缓存
        stringRedisTemplate.delete("admin:module:all");
    }

    /**
     * 修改模块信息
     *
     * @param moduleDTO 模块信息
     */
    @Override
    public void modifyModule(ModuleDTO moduleDTO) {
        // 判断是否已存在不是当前修改模块的同名模块
        Long count = lambdaQuery()
                // 同客户端下同名、同编码模块均不允许
                .eq(Module::getModuleKey, moduleDTO.getModuleKey())
                .eq(Module::getClientType,moduleDTO.getClientType())
                .ne(Module::getId, moduleDTO.getId())
                .or()
                .eq(Module::getModuleName, moduleDTO.getModuleName())
                .eq(Module::getClientType, moduleDTO.getClientType())
                .ne(Module::getId, moduleDTO.getId())
                .count();
        if (count > 0) {
            throw new ModuleException(AdminExceptionEnums.MODULE_EXIST);
        }

        Module module = BeanUtil.copyProperties(moduleDTO, Module.class);
        updateById(module);

        // 清除缓存
        stringRedisTemplate.delete("admin:module:all");
    }

    /**
     * 删除模块信息
     *
     * @param moduleId 模块id
     */
    @Override
    public void deleteModule(Long moduleId) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Module::getIsDelete, System.currentTimeMillis())
                .eq(Module::getId, moduleId)
                .update();

        // 清除缓存
        stringRedisTemplate.delete("admin:module:all");
    }

    /**
     * 批量删除模块信息
     * @param ids 模块id集合
     */
    @Override
    public void deleteBatchModule(List<Long> ids) {
        lambdaUpdate()
                .set(Module::getIsDelete, System.currentTimeMillis())
                .in(Module::getId, ids)
                .update();

        // 清除缓存
        stringRedisTemplate.delete("admin:module:all");
    }

    /**
     * 获取所有模块列表（带缓存）
     * @return 模块列表
     */
    @Override
    public List<Module> getAllModule() {
        String cacheKey = "admin:module:all";

        // 尝试从缓存中获取
        String cachedData = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toList(cachedData, Module.class);
        }

        // 缓存未命中，查询数据库
        List<Module> moduleList = list();

        // 存入缓存，过期时间30分钟
        stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(moduleList), 30, TimeUnit.MINUTES);

        return moduleList;
    }
}
