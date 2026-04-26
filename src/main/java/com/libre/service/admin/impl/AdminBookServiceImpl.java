package com.libre.service.admin.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.CommonExceptionEnums;
import com.libre.exception.BookException;
import com.libre.mapper.BookMapper;
import com.libre.pojo.dto.BookDTO;
import com.libre.pojo.dto.BookPageDTO;
import com.libre.pojo.po.Book;
import com.libre.pojo.vo.BookPageVO;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminBookService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminBookServiceImpl extends ServiceImpl<BookMapper, Book> implements AdminBookService {
    private final StringRedisTemplate stringRedisTemplate;
    /**
     * 清除首页缓存
     */
    private void clearHomeCache() {
        stringRedisTemplate.delete("admin:home:total-card");
        stringRedisTemplate.delete("admin:home:recent-lend-trend");
        stringRedisTemplate.delete("admin:home:top-book");
    }

    /**
     * 分页查询图书信息
     *
     * @param bookPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<BookPageVO>> pageQueryBook(BookPageDTO bookPageDTO) {
        // 构建分页条件
        IPage<BookPageVO> page = PageUtil.createPage(bookPageDTO);
        // 查询
        page = baseMapper.pageQueryBook(page, bookPageDTO);

        return PageResult.<List<BookPageVO>>builder()
                .total(page.getTotal())
                .data(page.getRecords())
                .build();
    }

    /**
     * 添加图书信息
     *
     * @param bookDTO 图书信息
     */
    @Override
    public void addBook(BookDTO bookDTO) {
        // 判断是否已存在相同ISBN的图书
        Long bookCount = lambdaQuery()
                .eq(Book::getIsbn, bookDTO.getIsbn())
                .count();

        if (bookCount > 0) {
            throw new BookException(CommonExceptionEnums.BOOK_EXIST);
        }

        // 判断名称/作者/出版社/封面/简介/语言/出版日期是否已存在同时重复图书
        bookCount = lambdaQuery()
                .eq(Book::getBookName, bookDTO.getBookName())
                .eq(Book::getAuthorId, bookDTO.getAuthorId())
                .eq(Book::getPublisherId, bookDTO.getPublisherId())
                .eq(Book::getLanguage, bookDTO.getLanguage())
                .eq(Book::getPublishDate, bookDTO.getPublishDate())
                .count();
        if (bookCount > 0) {
            throw new BookException(CommonExceptionEnums.BOOK_EXIST);
        }

        Book book = BeanUtil.copyProperties(bookDTO, Book.class);
        // 避免前端id残留数据影响
        if (book.getId() != null) book.setId(null);
        save(book);

        // 清除首页缓存
        clearHomeCache();
    }

    /**
     * 修改图书信息
     *
     * @param bookDTO 图书信息
     */
    @Override
    public void modifyBook(BookDTO bookDTO) {
        // 判断是否已存在不是当前修改图书的同ISBN的图书
        Long count = lambdaQuery()
                .eq(Book::getIsbn, bookDTO.getIsbn())
                .ne(Book::getId, bookDTO.getId())
                .count();
        if (count > 0) {
            throw new BookException(CommonExceptionEnums.BOOK_EXIST);
        }

        // 判断名称/作者/出版社/封面/简介/语言/出版日期是否已存在同时重复图书
        count = lambdaQuery()
                .eq(Book::getBookName, bookDTO.getBookName())
                .eq(Book::getAuthorId, bookDTO.getAuthorId())
                .eq(Book::getPublisherId, bookDTO.getPublisherId())
                .eq(Book::getLanguage, bookDTO.getLanguage())
                .eq(Book::getPublishDate, bookDTO.getPublishDate())
                .ne(Book::getId, bookDTO.getId())
                .count();
        if (count > 0) {
            throw new BookException(CommonExceptionEnums.BOOK_EXIST);
        }

        Book book = BeanUtil.copyProperties(bookDTO, Book.class);
        updateById(book);

        // 清除首页缓存
        clearHomeCache();
    }

    /**
     * 删除图书信息
     *
     * @param bookId 图书id
     */
    @Override
    public void deleteBook(Long bookId) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Book::getIsDelete, System.currentTimeMillis())
                .eq(Book::getId, bookId)
                .update();

        // 清除首页缓存
        clearHomeCache();
    }

    /**
     * 批量删除图书信息
     *
     * @param ids 图书id列表
     */
    @Override
    public void deleteBatchBook(List<Long> ids) {
        lambdaUpdate()
                .set(Book::getIsDelete, System.currentTimeMillis())
                .in(Book::getId, ids)
                .update();

        // 清除首页缓存
        clearHomeCache();
    }
}
