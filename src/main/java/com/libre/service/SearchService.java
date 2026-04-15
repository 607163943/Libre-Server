package com.libre.service;

import com.libre.pojo.dto.user.SearchDTO;
import com.libre.pojo.vo.user.SearchBookVO;
import com.libre.result.PageResult;

import java.util.List;

public interface SearchService {
    /**
     * 搜索
     * @param searchDTO 搜索参数
     * @return 搜索结果
     */
    PageResult<List<SearchBookVO>> search(SearchDTO searchDTO);
}
