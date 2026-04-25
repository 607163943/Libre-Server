package com.libre.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.constant.LendStatus;
import com.libre.mapper.BookMapper;
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
            if (searchDTO.getState() != null) {
                lendList = chainWrapper
                        .eq(Lend::getState, searchDTO.getState())
                        .list();
                // 获取借阅书籍id
                List<Long> bookIds = lendList.stream().map(Lend::getBookId).collect(Collectors.toList());
                searchBookVOS = searchBookVOS.stream()
                        .filter(searchBookVO -> bookIds.contains(searchBookVO.getId()))
                        .peek(searchBookVO -> searchBookVO.setState(searchDTO.getState()))
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
                        searchBookVO.setState(lend.getState());
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
