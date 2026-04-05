package com.libre.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.libre.pojo.dto.BasePageDTO;

public class PageUtil {
    public static <T> IPage<T> createPage(BasePageDTO basePageDTO) {
        Long page = basePageDTO.getPage();
        // 判断基础分页参数是否非法，非法则采用默认值
        if (page == null || page < 1) page = 1L;
        Long pageSize = basePageDTO.getPageSize();
        if (pageSize == null || pageSize < 1) pageSize = 10L;

        return new Page<>(page, pageSize);
    }
}
