package com.libre.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
}
