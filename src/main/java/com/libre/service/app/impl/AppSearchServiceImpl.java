package com.libre.service.app.impl;

import com.libre.pojo.dto.app.SearchDTO;
import com.libre.pojo.vo.app.SearchBookVO;
import com.libre.result.PageResult;
import com.libre.service.app.AppBookService;
import com.libre.service.app.AppSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AppSearchServiceImpl implements AppSearchService {
    private final AppBookService appBookService;

    /**
     * 搜索图书
     * @param searchDTO 搜索参数
     * @return 搜索结果
     */
    @Override
    public PageResult<List<SearchBookVO>> search(SearchDTO searchDTO) {
        return appBookService.search(searchDTO);
    }
}
