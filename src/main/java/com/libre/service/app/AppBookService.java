package com.libre.service.app;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.app.SearchDTO;
import com.libre.pojo.po.Book;
import com.libre.pojo.vo.app.BookDetailVO;
import com.libre.pojo.vo.app.HomeTopLatestBookItem;
import com.libre.pojo.vo.app.SearchBookVO;
import com.libre.result.PageResult;

import java.util.List;

public interface AppBookService extends IService<Book> {
    /**
     * 获取图书详情
     * @param bookId 图书id
     * @return 图书详情
     */
    BookDetailVO getBookDetail(Long bookId);

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
