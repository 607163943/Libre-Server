package com.libre.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.ModuleDTO;
import com.libre.pojo.dto.ModulePageDTO;
import com.libre.pojo.po.Module;
import com.libre.pojo.vo.ModulePageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface AdminModuleService extends IService<Module> {
    /**
     * 分页查询模块信息
     * @param modulePageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<ModulePageVO>> pageQueryModule(ModulePageDTO modulePageDTO);

    /**
     * 添加模块
     * @param moduleDTO 模块信息
     */
    void addModule(ModuleDTO moduleDTO);

    /**
     * 修改模块
     * @param moduleDTO 模块信息
     */
    void modifyModule(ModuleDTO moduleDTO);

    /**
     * 删除模块
     * @param moduleId 模块id
     */
    void deleteModule(Long moduleId);

    /**
     * 批量删除模块
     * @param ids 模块id集合
     */
    void deleteBatchModule(List<Long> ids);

    /**
     * 获取所有模块列表（带缓存）
     * @return 模块列表
     */
    List<Module> getAllModule();
}
