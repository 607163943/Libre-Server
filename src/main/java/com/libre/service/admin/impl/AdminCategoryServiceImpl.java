package com.libre.service.admin.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.CategoryException;
import com.libre.mapper.CategoryMapper;
import com.libre.pojo.dto.admin.CategoryDTO;
import com.libre.pojo.dto.admin.CategoryPageDTO;
import com.libre.pojo.po.Category;
import com.libre.pojo.vo.admin.CategoryPageVO;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminCategoryService;
import com.libre.util.CacheUtil;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AdminCategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements AdminCategoryService {
    private final CacheUtil cacheUtil;

    private static final String CATEGORY_CACHE_KEY = "admin:class:all";

    /**
     * 分页查询分类信息
     *
     * @param categoryPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<CategoryPageVO>> pageQueryClass(CategoryPageDTO categoryPageDTO) {
        // 构建分页条件
        IPage<Category> page = PageUtil.createPage(categoryPageDTO);
        // 查询
        page = lambdaQuery()
                .like(StrUtil.isNotBlank(categoryPageDTO.getCategoryName())
                        , Category::getCategoryName, categoryPageDTO.getCategoryName())
                .page(page);
        // 构建VO数据
        List<CategoryPageVO> categoryPageVOS = BeanUtil.copyToList(page.getRecords(), CategoryPageVO.class);

        return PageResult.<List<CategoryPageVO>>builder()
                .total(page.getTotal())
                .data(categoryPageVOS)
                .build();
    }

    /**
     * 添加分类信息
     *
     * @param categoryDTO 分类信息
     */
    @Override
    public void addClass(CategoryDTO categoryDTO) {
        // 判断是否已存在同名分类
        Long classCount = lambdaQuery()
                .eq(Category::getCategoryName, categoryDTO.getCategoryName())
                .count();

        if (classCount > 0) {
            throw new CategoryException(ExceptionEnums.CATEGORY_EXIST);
        }

        Category clazz = BeanUtil.copyProperties(categoryDTO, Category.class);
        // 避免前端id残留数据影响
        if (clazz.getId() != null) clazz.setId(null);
        save(clazz);

        // 清除缓存
        cacheUtil.delete(CATEGORY_CACHE_KEY);
    }

    /**
     * 修改分类信息
     *
     * @param categoryDTO 分类信息
     */
    @Override
    public void modifyClass(CategoryDTO categoryDTO) {
        // 判断是否已存在不是当前修改分类的同名分类
        Long count = lambdaQuery()
                .eq(Category::getCategoryName, categoryDTO.getCategoryName())
                .ne(Category::getId, categoryDTO.getId())
                .count();
        if (count > 0) {
            throw new CategoryException(ExceptionEnums.CATEGORY_EXIST);
        }

        Category clazz = BeanUtil.copyProperties(categoryDTO, Category.class);
        updateById(clazz);

        // 清除缓存
        cacheUtil.delete(CATEGORY_CACHE_KEY);
    }

    /**
     * 删除分类信息
     *
     * @param classId 分类id
     */
    @Override
    public void deleteClass(Long classId) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Category::getIsDelete, System.currentTimeMillis())
                .eq(Category::getId, classId)
                .update();

        // 清除缓存
        cacheUtil.delete(CATEGORY_CACHE_KEY);
    }

    /**
     * 批量删除分类信息
     *
     * @param ids 分类id集合
     */
    @Override
    public void deleteBatchClass(List<Long> ids) {
        lambdaUpdate()
                .set(Category::getIsDelete, System.currentTimeMillis())
                .in(Category::getId, ids)
                .update();

        // 清除缓存
        cacheUtil.delete(CATEGORY_CACHE_KEY);
    }

    /**
     * 获取所有分类列表（带缓存）
     *
     * @return 分类列表
     */
    @Override
    public List<Category> getAllClass() {
        // 尝试从缓存中获取
        String cachedData = cacheUtil.get(CATEGORY_CACHE_KEY);
        if (cachedData != null) {
            return JSONUtil.toList(cachedData, Category.class);
        }

        // 缓存未命中，查询数据库
        List<Category> categoryList = list();

        // 存入缓存，过期时间30分钟
        cacheUtil.set(CATEGORY_CACHE_KEY, JSONUtil.toJsonStr(categoryList), 30, TimeUnit.MINUTES);

        return categoryList;
    }
}
