package com.libre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.AuthorDTO;
import com.libre.pojo.dto.AuthorPageDTO;
import com.libre.pojo.po.Author;
import com.libre.pojo.vo.AuthorPageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface AuthorService extends IService<Author> {
    /**
     * 分页查询作者信息
     * @param authorPageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<AuthorPageVO>> pageQueryAuthor(AuthorPageDTO authorPageDTO);

    /**
     * 添加作者
     * @param authorDTO 作者信息
     */
    void addAuthor(AuthorDTO authorDTO);

    /**
     * 修改作者
     * @param authorDTO 作者信息
     */
    void modifyAuthor(AuthorDTO authorDTO);

    /**
     * 删除作者
     * @param authorId 作者id
     */
    void deleteAuthor(Long authorId);

    /**
     * 批量删除作者
     * @param ids 作者id集合
     */
    void deleteBatchAuthor(List<Long> ids);
}
