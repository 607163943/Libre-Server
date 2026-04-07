package com.libre.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.dto.PublisherDTO;
import com.libre.pojo.dto.PublisherPageDTO;
import com.libre.pojo.po.Publisher;
import com.libre.pojo.vo.PublisherPageVO;
import com.libre.result.PageResult;

import java.util.List;

public interface PublisherService extends IService<Publisher> {
    /**
     * 分页查询出版社信息
     * @param publisherPageDTO 查询参数
     * @return 查询结果
     */
    PageResult<List<PublisherPageVO>> pageQueryPublisher(PublisherPageDTO publisherPageDTO);

    /**
     * 添加出版社
     * @param publisherDTO 出版社信息
     */
    void addPublisher(PublisherDTO publisherDTO);

    /**
     * 修改出版社
     * @param publisherDTO 出版社信息
     */
    void modifyPublisher(PublisherDTO publisherDTO);

    /**
     * 删除出版社
     * @param publisherId 出版社id
     */
    void deletePublisher(Long publisherId);
}
