package com.libre.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.admin.ClassDTO;
import com.libre.pojo.dto.admin.ClassPageDTO;
import com.libre.pojo.po.Class;
import com.libre.pojo.vo.admin.ClassPageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface AdminClassService extends IService<Class> {
    /**
     * 分页查询分类信息
     * @param classPageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<ClassPageVO>> pageQueryClass(ClassPageDTO classPageDTO);

    /**
     * 添加分类
     * @param classDTO 分类信息
     */
    void addClass(ClassDTO classDTO);

    /**
     * 修改分类
     * @param classDTO 分类信息
     */
    void modifyClass(ClassDTO classDTO);

    /**
     * 删除分类
     * @param classId 分类id
     */
    void deleteClass(Long classId);

    /**
     * 批量删除分类
     * @param ids 分类id集合
     */
    void deleteBatchClass(List<Long> ids);

    /**
     * 获取所有分类列表（带缓存）
     * @return 分类列表
     */
    List<Class> getAllClass();
}
