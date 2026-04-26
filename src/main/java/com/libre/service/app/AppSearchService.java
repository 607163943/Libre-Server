package com.libre.service.app;

import com.libre.pojo.dto.app.SearchDTO;
import com.libre.pojo.vo.app.SearchBookVO;
import com.libre.result.PageResult;

import java.util.List;

public interface AppSearchService {
    /**
     * 搜索
     * @param searchDTO 搜索参数
     * @return 搜索结果
     */
    PageResult<List<SearchBookVO>> search(SearchDTO searchDTO);
}
