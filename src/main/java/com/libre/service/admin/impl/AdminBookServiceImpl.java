package com.libre.service.admin.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.libre.constant.ServiceType;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.BookException;
import com.libre.mapper.BookMapper;
import com.libre.pojo.doc.BookDoc;
import com.libre.pojo.dto.admin.BookDTO;
import com.libre.pojo.dto.admin.BookPageDTO;
import com.libre.pojo.po.Book;
import com.libre.pojo.po.Lend;
import com.libre.pojo.po.UploadFileRef;
import com.libre.pojo.vo.admin.BookPageVO;
import com.libre.pojo.vo.admin.BookVO;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminBookService;
import com.libre.service.common.CommonUploadFileRefService;
import com.libre.util.CacheUtil;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AdminBookServiceImpl extends ServiceImpl<BookMapper, Book> implements AdminBookService {
    private final CacheUtil cacheUtil;

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    private final CommonUploadFileRefService uploadFileRefService;

    // 解决事务失效
    @Lazy
    @Resource
    private AdminBookService bookService;

    /**
     * 清除首页缓存
     */
    private void clearHomeCache() {
        cacheUtil.delete("admin:home:total-card");
        cacheUtil.delete("admin:home:recent-lend-trend");
        cacheUtil.delete("admin:home:top-book");

        cacheUtil.delete("user:home:top-latest-book");
        cacheUtil.delete("user:home:top-lend-book");
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
    @Transactional
    @Override
    public void addBook(BookDTO bookDTO) {
        // 判断是否已存在相同ISBN的图书
        Long bookCount = lambdaQuery()
                .eq(Book::getIsbn, bookDTO.getIsbn())
                .count();

        if (bookCount > 0) {
            throw new BookException(ExceptionEnums.BOOK_EXIST);
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
            throw new BookException(ExceptionEnums.BOOK_EXIST);
        }

        Book book = BeanUtil.copyProperties(bookDTO, Book.class);
        // 避免前端id残留数据影响
        if (book.getId() != null) book.setId(null);

        bookService.save(book);

        if(bookDTO.getFileId()!=null) {
            // 记录文件引用关系
            UploadFileRef uploadFileRef = UploadFileRef.builder()
                    .serviceId(book.getId())
                    .serviceType(ServiceType.BOOK_COVER)
                    .fileId(bookDTO.getFileId())
                    .build();
            uploadFileRefService.save(uploadFileRef);
        }

        // 清除首页缓存
        clearHomeCache();

        // 同步到es
        toEs(book);
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
            throw new BookException(ExceptionEnums.BOOK_EXIST);
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
            throw new BookException(ExceptionEnums.BOOK_EXIST);
        }

        Book book = BeanUtil.copyProperties(bookDTO, Book.class);
        bookService.updateById(book);

        // 更新文件引用关系
        bookDTO.setId(bookDTO.getId());
        updateUploadFileRef(bookDTO);


        // 清除首页缓存
        clearHomeCache();

        // 同步到es
        toEs(book);
    }

    private void updateUploadFileRef(BookDTO bookDTO) {
        // 删除旧关系
        uploadFileRefService.lambdaUpdate()
                .set(UploadFileRef::getIsDelete, System.currentTimeMillis())
                .eq(UploadFileRef::getServiceType, ServiceType.BOOK_COVER)
                .eq(UploadFileRef::getServiceId, bookDTO.getId())
                .update();

        // 添加新关系
        if(bookDTO.getFileId()!=null) {
            UploadFileRef uploadFileRef = UploadFileRef.builder()
                    .serviceType(ServiceType.BOOK_COVER)
                    .serviceId(bookDTO.getId())
                    .fileId(bookDTO.getFileId())
                    .build();
            uploadFileRefService.save(uploadFileRef);
        }
    }

    /**
     * 同步图书信息到es
     *
     * @param book 图书信息
     */
    private void toEs(Book book) {
        BookDoc bookDoc = baseMapper.findBookDockById(book.getId());
        elasticsearchRestTemplate.save(bookDoc);
    }

    /**
     * 删除图书信息
     *
     * @param bookId 图书id
     */
    @Override
    public void deleteBook(Long bookId) {
        // 存在该图书的借阅则无法删除
        Long lendCount = Db.lambdaQuery(Lend.class)
                .eq(Lend::getBookId, bookId)
                .count();

        if(lendCount>0) {
            throw new BookException(ExceptionEnums.BOOK_HAS_LEND);
        }
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Book::getIsDelete, System.currentTimeMillis())
                .eq(Book::getId, bookId)
                .update();

        // 清除首页缓存
        clearHomeCache();
        // 删除文件引用关系
        uploadFileRefService.lambdaUpdate()
                .set(UploadFileRef::getIsDelete, System.currentTimeMillis())
                .eq(UploadFileRef::getServiceId, bookId)
                .eq(UploadFileRef::getServiceType, ServiceType.BOOK_COVER)
                .update();
        elasticsearchRestTemplate.delete(bookId.toString(), BookDoc.class);
    }

    /**
     * 批量删除图书信息
     *
     * @param ids 图书id列表
     */
    @Override
    public void deleteBatchBook(List<Long> ids) {
        // 存在该图书的借阅则无法删除
        Long lendCount = Db.lambdaQuery(Lend.class)
                .in(Lend::getBookId, ids)
                .count();

        if(lendCount>0) {
            throw new BookException(ExceptionEnums.BOOK_HAS_LEND);
        }

        lambdaUpdate()
                .set(Book::getIsDelete, System.currentTimeMillis())
                .in(Book::getId, ids)
                .update();

        // 清除首页缓存
        clearHomeCache();
        // 删除文件引用关系
        uploadFileRefService.lambdaUpdate()
                .set(UploadFileRef::getIsDelete, System.currentTimeMillis())
                .in(UploadFileRef::getServiceId, ids)
                .eq(UploadFileRef::getServiceType, ServiceType.BOOK_COVER)
                .update();
        ids.forEach(id -> elasticsearchRestTemplate.delete(id.toString(), BookDoc.class));
    }

    /**
     * 获取图书信息
     *
     * @param bookId 图书id
     * @return 图书信息
     */
    @Override
    public BookVO getBook(Long bookId) {
        Book Book = getById(bookId);
        BookVO bookVO = BeanUtil.copyProperties(Book, BookVO.class);
        UploadFileRef uploadFileRef = uploadFileRefService.lambdaQuery()
                .eq(UploadFileRef::getServiceId, Book.getId())
                .eq(UploadFileRef::getServiceType, ServiceType.BOOK_COVER)
                .one();
        if(uploadFileRef!=null) {
            bookVO.setFileId(uploadFileRef.getFileId());
        }
        return bookVO;
    }
}
