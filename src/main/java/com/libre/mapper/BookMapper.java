package com.libre.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.libre.pojo.dto.BookPageDTO;
import com.libre.pojo.po.Book;
import com.libre.pojo.vo.BookPageVO;
import com.libre.pojo.vo.user.HomeTopLatestBookItem;
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
}
