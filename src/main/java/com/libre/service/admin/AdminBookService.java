package com.libre.service.admin;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.BookDTO;
import com.libre.pojo.dto.BookPageDTO;
import com.libre.pojo.po.Book;
import com.libre.pojo.vo.BookPageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface AdminBookService extends IService<Book> {
    /**
     * 分页查询图书信息
     * @param bookPageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<BookPageVO>> pageQueryBook(BookPageDTO bookPageDTO);

    /**
     * 添加图书
     * @param bookDTO 图书信息
     */
    void addBook(BookDTO bookDTO);

    /**
     * 修改图书
     * @param bookDTO 图书信息
     */
    void modifyBook(BookDTO bookDTO);

    /**
     * 删除图书
     * @param bookId 图书id
     */
    void deleteBook(Long bookId);

    /**
     * 批量删除图书
     * @param ids 图书id列表
     */
    void deleteBatchBook(List<Long> ids);
}
