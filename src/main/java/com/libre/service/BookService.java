package com.libre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.BookPageDTO;
import com.libre.pojo.dto.user.SearchDTO;
import com.libre.pojo.po.Book;
import com.libre.pojo.vo.BookPageVO;
import com.libre.pojo.vo.user.HomeTopLatestBookItem;
import com.libre.pojo.vo.user.SearchBookVO;
import com.libre.result.PageResult;

import java.util.List;

public interface BookService extends IService<Book> {
    /**
     * 分页查询图书信息
     * @param bookPageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<BookPageVO>> pageQueryBook(BookPageDTO bookPageDTO);


    /**
     * 获取首页最新图书
     * @return 首页最新图书
     */
    List<HomeTopLatestBookItem> getHomeTopLatestBookList();

    /**
     * 搜索图书
     * @param searchDTO 搜索参数
     * @return 搜索结果
     */
    PageResult<List<SearchBookVO>> search(SearchDTO searchDTO);
}
