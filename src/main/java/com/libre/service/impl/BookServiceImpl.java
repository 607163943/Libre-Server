package com.libre.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.LendStatus;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.BookException;
import com.libre.mapper.BookMapper;
import com.libre.pojo.dto.BookDTO;
import com.libre.pojo.dto.BookPageDTO;
import com.libre.pojo.dto.user.SearchDTO;
import com.libre.pojo.po.Book;
import com.libre.pojo.po.Lend;
import com.libre.pojo.vo.BookPageVO;
import com.libre.pojo.vo.user.HomeTopLatestBookItem;
import com.libre.pojo.vo.user.SearchBookVO;
import com.libre.result.PageResult;
import com.libre.service.BookService;
import com.libre.service.LendService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    private final LendService lendService;

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
        save(book);
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
        updateById(book);
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
    }

    /**
     * 获取用户借阅最新top
     *
     * @return 用户借阅最新top
     */
    @Override
    public List<HomeTopLatestBookItem> getHomeTopLatestBookList() {
        return baseMapper.getHomeTopLatestBookList();
    }

    /**
     * 搜索图书
     *
     * @param searchDTO 搜索参数
     * @return 搜索结果
     */
    @Override
    public PageResult<List<SearchBookVO>> search(SearchDTO searchDTO) {
        IPage<SearchBookVO> page = PageUtil.createPage(searchDTO);
        page = baseMapper.search(page, searchDTO);

        List<SearchBookVO> searchBookVOS = page.getRecords();
        long total = page.getTotal();
        // 登录状态下额外提供借阅书籍标注、借阅状态查询功能
        if (StpUtil.isLogin()) {
            LambdaQueryChainWrapper<Lend> chainWrapper = lendService.lambdaQuery()
                    .eq(Lend::getUserId, StpUtil.getLoginIdAsLong());

            // 借阅书籍查询
            List<Lend> lendList;
            if (searchDTO.getStatus() != null) {
                lendList = chainWrapper
                        .eq(Lend::getState, searchDTO.getStatus())
                        .list();
                // 获取借阅书籍id
                List<Long> bookIds = lendList.stream().map(Lend::getBookId).collect(Collectors.toList());
                searchBookVOS = searchBookVOS.stream()
                        .filter(searchBookVO -> bookIds.contains(searchBookVO.getId()))
                        .collect(Collectors.toList());
                // 带状态的借阅查询下，总条数根有效借阅总数一致
                total = lendList.size();
            } else {
                // 借阅书籍标记
                lendList = chainWrapper
                        .in(Lend::getState, LendStatus.LEND, LendStatus.OVERDUE)
                        .list();
                // 借阅书籍标注
                Map<Long, Lend> lendBookStatusMap = lendList.stream()
                        .collect(Collectors.toMap(Lend::getBookId, lend -> lend));
                searchBookVOS.forEach(searchBookVO -> {
                    Lend lend = lendBookStatusMap.get(searchBookVO.getId());
                    if (lend != null) {
                        searchBookVO.setStatus(lend.getState());
                        searchBookVO.setDueTime(lend.getDueTime());
                    }
                });
            }
        }


        return PageResult.<List<SearchBookVO>>builder()
                .total(total)
                .data(searchBookVOS)
                .build();
    }
}
