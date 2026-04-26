package com.libre.service.admin.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.AuthorException;
import com.libre.mapper.AuthorMapper;
import com.libre.pojo.dto.admin.AuthorDTO;
import com.libre.pojo.dto.admin.AuthorPageDTO;
import com.libre.pojo.po.Author;
import com.libre.pojo.po.Book;
import com.libre.pojo.vo.admin.AuthorPageVO;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminAuthorService;
import com.libre.service.admin.AdminBookService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AdminAuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements AdminAuthorService {
    private final StringRedisTemplate stringRedisTemplate;
    private final AdminBookService adminBookService;

    /**
     * 分页查询作者信息
     *
     * @param authorPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<AuthorPageVO>> pageQueryAuthor(AuthorPageDTO authorPageDTO) {
        // 构建分页条件
        IPage<Author> page = PageUtil.createPage(authorPageDTO);
        // 查询
        page = lambdaQuery()
                .like(StrUtil.isNotBlank(authorPageDTO.getAuthorName())
                        , Author::getAuthorName, authorPageDTO.getAuthorName())
                .page(page);
        // 构建VO数据
        List<AuthorPageVO> authorPageVOS = BeanUtil.copyToList(page.getRecords(), AuthorPageVO.class);

        return PageResult.<List<AuthorPageVO>>builder()
                .total(page.getTotal())
                .data(authorPageVOS)
                .build();
    }

    /**
     * 添加作者信息
     *
     * @param authorDTO 作者信息
     */
    @Override
    public void addAuthor(AuthorDTO authorDTO) {
        // 判断是否已存在同名作者
        Long authorCount = lambdaQuery()
                .eq(Author::getAuthorName, authorDTO.getAuthorName())
                .count();

        if (authorCount > 0) {
            throw new AuthorException(ExceptionEnums.AUTHOR_EXIST);
        }

        Author author = BeanUtil.copyProperties(authorDTO, Author.class);
        // 避免前端id残留数据影响
        if (author.getId() != null) author.setId(null);
        save(author);

        // 清除缓存
        stringRedisTemplate.delete("admin:author:all");
    }

    /**
     * 修改作者信息
     *
     * @param authorDTO 作者信息
     */
    @Override
    public void modifyAuthor(AuthorDTO authorDTO) {
        // 判断是否已存在不是当前修改作者的同名作者
        Long count = lambdaQuery()
                .eq(Author::getAuthorName, authorDTO.getAuthorName())
                .ne(Author::getId, authorDTO.getId())
                .count();
        if (count > 0) {
            throw new AuthorException(ExceptionEnums.AUTHOR_EXIST);
        }

        Author author = BeanUtil.copyProperties(authorDTO, Author.class);
        updateById(author);

        // 清除缓存
        stringRedisTemplate.delete("admin:author:all");
    }

    /**
     * 删除作者信息
     *
     * @param authorId 作者id
     */
    @Override
    public void deleteAuthor(Long authorId) {
        // 判断是否存在该作者的图书
        Long bookCount = adminBookService.lambdaQuery()
                .eq(Book::getAuthorId, authorId)
                .count();

        if(bookCount>0) {
            throw new AuthorException(ExceptionEnums.AUTHOR_HAS_BOOK);
        }

        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Author::getIsDelete, System.currentTimeMillis())
                .eq(Author::getId, authorId)
                .update();

        // 清除缓存
        stringRedisTemplate.delete("admin:author:all");
    }

    /**
     * 批量删除作者信息
     * @param ids 作者id集合
     */
    @Override
    public void deleteBatchAuthor(List<Long> ids) {
        Long bookCount = adminBookService.lambdaQuery()
                .in(Book::getAuthorId, ids)
                .count();
        if(bookCount>0) {
            throw new AuthorException(ExceptionEnums.AUTHOR_HAS_BOOK);
        }

        lambdaUpdate()
                .set(Author::getIsDelete, System.currentTimeMillis())
                .in(Author::getId, ids)
                .update();

        // 清除缓存
        stringRedisTemplate.delete("admin:author:all");
    }

    /**
     * 获取所有作者列表（带缓存）
     * @return 作者列表
     */
    @Override
    public List<Author> getAllAuthor() {
        String cacheKey = "admin:author:all";

        // 尝试从缓存中获取
        String cachedData = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toList(cachedData, Author.class);
        }

        // 缓存未命中，查询数据库
        List<Author> authorList = list();

        // 存入缓存，过期时间30分钟
        stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(authorList), 30, TimeUnit.MINUTES);

        return authorList;
    }
}
