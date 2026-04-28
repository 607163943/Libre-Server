package com.libre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.libre.pojo.doc.BookDoc;
import com.libre.pojo.dto.admin.BookPageDTO;
import com.libre.pojo.po.Book;
import com.libre.pojo.vo.admin.BookPageVO;
import com.libre.pojo.vo.app.BookDetailVO;
import com.libre.pojo.vo.app.HomeTopLatestBookItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookMapper extends BaseMapper<Book> {
    /**
     * 分页查询图书信息
     * @param page 分页条件
     * @param bookPageDTO 查询参数
     * @return 分页结果
     */
    IPage<BookPageVO> pageQueryBook(@Param("page") IPage<BookPageVO> page,@Param("bookPageDTO") BookPageDTO bookPageDTO);

    /**
     * 获取用户首页最新图书
     * @return 用户首页最新图书
     */
    List<HomeTopLatestBookItem> getHomeTopLatestBookList();

    /**
     * 根据id获取图书文档
     * @param id 图书id
     * @return Book文档
     */
    BookDoc findBookDockById(Long id);

    // 用于全量同步数据到 ES

    /**
     * 获取所有图书文档
     * @return 图书文档列表
     */
    List<BookDoc> findAllBookDocs();

    /**
     * 获取图书详情
     * @param bookId 图书id
     * @return 图书详情
     */
    BookDetailVO getBookDetail(@Param("bookId") Long bookId);
}
