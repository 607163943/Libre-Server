package com.libre.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.AuthorMapper;
import com.libre.pojo.dto.AuthorPageDTO;
import com.libre.pojo.po.Author;
import com.libre.pojo.vo.AuthorPageVO;
import com.libre.result.PageResult;
import com.libre.service.AuthorService;
import com.libre.util.PageUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements AuthorService {
    /**
     * 分页查询作者信息
     * @param authorPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<AuthorPageVO>> pageQueryAuthor(AuthorPageDTO authorPageDTO) {
        // 构建分页条件
        IPage<Author> page=PageUtil.createPage(authorPageDTO);
        // 查询
        page=lambdaQuery()
                .like(StrUtil.isNotBlank(authorPageDTO.getAuthorName())
                        ,Author::getAuthorName, authorPageDTO.getAuthorName())
                .page(page);
        // 构建VO数据
        List<AuthorPageVO> authorPageVOS = BeanUtil.copyToList(page.getRecords(), AuthorPageVO.class);

        return PageResult.<List<AuthorPageVO>>builder()
                .total(page.getTotal())
                .data(authorPageVOS)
                .build();
    }
}
