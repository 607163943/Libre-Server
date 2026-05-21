package com.libre.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.admin.CategoryDTO;
import com.libre.pojo.dto.admin.CategoryPageDTO;
import com.libre.pojo.po.Category;
import com.libre.pojo.vo.admin.CategoryPageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface AdminCategoryService extends IService<Category> {
    /**
     * 分页查询分类信息
     * @param categoryPageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<CategoryPageVO>> pageQueryClass(CategoryPageDTO categoryPageDTO);

    /**
     * 添加分类
     * @param categoryDTO 分类信息
     */
    void addClass(CategoryDTO categoryDTO);

    /**
     * 修改分类
     * @param categoryDTO 分类信息
     */
    void modifyClass(CategoryDTO categoryDTO);

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
    List<Category> getAllClass();
}
