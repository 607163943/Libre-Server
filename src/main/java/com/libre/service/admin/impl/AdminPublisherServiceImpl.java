package com.libre.service.admin.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.CommonExceptionEnums;
import com.libre.exception.PublisherException;
import com.libre.mapper.PublisherMapper;
import com.libre.pojo.dto.PublisherDTO;
import com.libre.pojo.dto.PublisherPageDTO;
import com.libre.pojo.po.Book;
import com.libre.pojo.po.Publisher;
import com.libre.pojo.vo.PublisherPageVO;
import com.libre.result.PageResult;
import com.libre.service.admin.AdminBookService;
import com.libre.service.admin.AdminPublisherService;
import com.libre.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class AdminPublisherServiceImpl extends ServiceImpl<PublisherMapper, Publisher> implements AdminPublisherService {
    private final AdminBookService adminBookService;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 分页查询出版社信息
     * @param publisherPageDTO 查询参数
     * @return 分页结果
     */
    @Override
    public PageResult<List<PublisherPageVO>> pageQueryPublisher(PublisherPageDTO publisherPageDTO) {
        // 构建分页条件
        IPage<Publisher> page= PageUtil.createPage(publisherPageDTO);
        // 查询
        page=lambdaQuery()
                .like(StrUtil.isNotBlank(publisherPageDTO.getPublisherName())
                        ,Publisher::getPublisherName, publisherPageDTO.getPublisherName())
                .page(page);
        // 构建VO数据
        List<PublisherPageVO> publisherPageVOS = BeanUtil.copyToList(page.getRecords(), PublisherPageVO.class);

        return PageResult.<List<PublisherPageVO>>builder()
                .total(page.getTotal())
                .data(publisherPageVOS)
                .build();
    }

    /**
     * 添加出版社信息
     * @param publisherDTO 出版社信息
     */
    @Override
    public void addPublisher(PublisherDTO publisherDTO) {
        // 判断是否已存在同名出版社
        Long publisherCount = lambdaQuery()
                .eq(Publisher::getPublisherName, publisherDTO.getPublisherName())
                .count();

        if(publisherCount>0) {
            throw new PublisherException(CommonExceptionEnums.PUBLISHER_EXIST);
        }

        Publisher publisher = BeanUtil.copyProperties(publisherDTO, Publisher.class);
        // 避免前端id残留数据影响
        if(publisher.getId()!=null) publisher.setId(null);
        save(publisher);

        // 清除缓存
        stringRedisTemplate.delete("admin:publisher:all");
    }

    /**
     * 修改出版社信息
     * @param publisherDTO 出版社信息
     */
    @Override
    public void modifyPublisher(PublisherDTO publisherDTO) {
        // 判断是否已存在不是当前修改出版社的同名出版社
        Long count = lambdaQuery()
                .eq(Publisher::getPublisherName, publisherDTO.getPublisherName())
                .ne(Publisher::getId, publisherDTO.getId())
                .count();
        if(count>0) {
            throw new PublisherException(CommonExceptionEnums.PUBLISHER_EXIST);
        }

        Publisher publisher = BeanUtil.copyProperties(publisherDTO, Publisher.class);
        updateById(publisher);

        // 清除缓存
        stringRedisTemplate.delete("admin:publisher:all");
    }

    /**
     * 删除出版社信息
     * @param publisherId 出版社id
     */
    @Override
    public void deletePublisher(Long publisherId) {
        // 校验是否存在该出版社发布的图书
        Long bookCount = adminBookService.lambdaQuery()
                .eq(Book::getPublisherId, publisherId)
                .count();

        if(bookCount>0) {
            throw new PublisherException(CommonExceptionEnums.PUBLISHER_HAS_BOOK);
        }

        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Publisher::getIsDelete,System.currentTimeMillis())
                .eq(Publisher::getId, publisherId)
                .update();

        // 清除缓存
        stringRedisTemplate.delete("admin:publisher:all");
    }

    /**
     * 批量删除出版社信息
     * @param ids 出版社id列表
     */
    @Override
    public void deleteBatchPublisher(List<Long> ids) {
        lambdaUpdate()
                // 使用时间戳标记逻辑删除，避免唯一键冲突
                .set(Publisher::getIsDelete, System.currentTimeMillis())
                .in(Publisher::getId, ids)
                .update();

        // 清除缓存
        stringRedisTemplate.delete("admin:publisher:all");
    }

    /**
     * 获取所有出版社列表（带缓存）
     * @return 出版社列表
     */
    @Override
    public List<Publisher> getAllPublisher() {
        String cacheKey = "admin:publisher:all";

        // 尝试从缓存中获取
        String cachedData = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cachedData != null) {
            return JSONUtil.toList(cachedData, Publisher.class);
        }

        // 缓存未命中，查询数据库
        List<Publisher> publisherList = list();

        // 存入缓存，过期时间30分钟
        stringRedisTemplate.opsForValue().set(cacheKey, JSONUtil.toJsonStr(publisherList), 30, TimeUnit.MINUTES);

        return publisherList;
    }
}
