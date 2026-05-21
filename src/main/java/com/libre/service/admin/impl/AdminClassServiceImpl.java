package com.libre.service.admin.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.ClassException;
import com.libre.mapper.ClassMapper;
import com.libre.pojo.dto.admin.ClassDTO;
import com.libre.pojo.dto.admin.ClassPageDTO;
import com.libre.pojo.po.Class;
import com.libre.pojo.vo.admin.ClassPageVO;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminClassService;
import com.libre.util.CacheUtil;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AdminClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements AdminClassService {
    private final CacheUtil cacheUtil;

    /**
     * 分页查询分类信息
     *
     * @param classPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<ClassPageVO>> pageQueryClass(ClassPageDTO classPageDTO) {
        // 构建分页条件
        IPage<Class> page = PageUtil.createPage(classPageDTO);
        // 查询
        page = lambdaQuery()
                .like(StrUtil.isNotBlank(classPageDTO.getClassName())
                        , Class::getClassName, classPageDTO.getClassName())
                .page(page);
        // 构建VO数据
        List<ClassPageVO> classPageVOS = BeanUtil.copyToList(page.getRecords(), ClassPageVO.class);

        return PageResult.<List<ClassPageVO>>builder()
                .total(page.getTotal())
                .data(classPageVOS)
                .build();
    }

    /**
     * 添加分类信息
     *
     * @param classDTO 分类信息
     */
    @Override
    public void addClass(ClassDTO classDTO) {
        // 判断是否已存在同名分类
        Long classCount = lambdaQuery()
                .eq(Class::getClassName, classDTO.getClassName())
                .count();

        if (classCount > 0) {
            throw new ClassException(ExceptionEnums.CLASS_EXIST);
        }

        Class clazz = BeanUtil.copyProperties(classDTO, Class.class);
        // 避免前端id残留数据影响
        if (clazz.getId() != null) clazz.setId(null);
        save(clazz);

        // 清除缓存
        cacheUtil.delete("admin:class:all");
    }

    /**
     * 修改分类信息
     *
     * @param classDTO 分类信息
     */
    @Override
    public void modifyClass(ClassDTO classDTO) {
        // 判断是否已存在不是当前修改分类的同名分类
        Long count = lambdaQuery()
                .eq(Class::getClassName, classDTO.getClassName())
                .ne(Class::getId, classDTO.getId())
                .count();
        if (count > 0) {
            throw new ClassException(ExceptionEnums.CLASS_EXIST);
        }

        Class clazz = BeanUtil.copyProperties(classDTO, Class.class);
        updateById(clazz);

        // 清除缓存
        cacheUtil.delete("admin:class:all");
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
                .set(Class::getIsDelete, System.currentTimeMillis())
                .eq(Class::getId, classId)
                .update();

        // 清除缓存
        cacheUtil.delete("admin:class:all");
    }

    /**
     * 批量删除分类信息
     * @param ids 分类id集合
     */
    @Override
    public void deleteBatchClass(List<Long> ids) {
        lambdaUpdate()
                .set(Class::getIsDelete, System.currentTimeMillis())
                .in(Class::getId, ids)
                .update();

        // 清除缓存
        cacheUtil.delete("admin:class:all");
    }

    /**
     * 获取所有分类列表（带缓存）
     * @return 分类列表
     */
    @Override
    public List<Class> getAllClass() {
        String cacheKey = "admin:class:all";

        // 尝试从缓存中获取
        String cachedData = cacheUtil.get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toList(cachedData, Class.class);
        }

        // 缓存未命中，查询数据库
        List<Class> classList = list();

        // 存入缓存，过期时间30分钟
        cacheUtil.set(cacheKey, JSONUtil.toJsonStr(classList), 30, TimeUnit.MINUTES);

        return classList;
    }
}
