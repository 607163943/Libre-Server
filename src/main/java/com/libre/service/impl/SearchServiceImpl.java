package com.libre.service.impl;

import com.libre.pojo.dto.user.SearchDTO;
import com.libre.pojo.vo.user.SearchBookVO;
import com.libre.result.PageResult;
import com.libre.service.BookService;
import com.libre.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchServiceImpl implements SearchService {
    private final BookService bookService;

    /**
     * 搜索图书
     * @param searchDTO 搜索参数
     * @return 搜索结果
     */
    @Override
    public PageResult<List<SearchBookVO>> search(SearchDTO searchDTO) {
        return bookService.search(searchDTO);
    }
}
