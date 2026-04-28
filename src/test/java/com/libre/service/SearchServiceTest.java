package com.libre.service;

import cn.hutool.core.collection.CollUtil;
import com.libre.mapper.BookMapper;
import com.libre.pojo.doc.BookDoc;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;

import java.util.List;

@Slf4j
@SpringBootTest
public class SearchServiceTest {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private ElasticsearchRestTemplate esTemplate;

    /**
     * 全量同步 MySQL 数据到 ES
     */
    @Test
    public void syncAllBooks() {
        // 1. 检查索引是否存在，如果修改了 Mapping，建议先删除再重建
        IndexOperations indexOps = esTemplate.indexOps(BookDoc.class);
        if (indexOps.exists()) {
            indexOps.delete();
            log.info("旧索引已删除");
        }
        indexOps.create();
        indexOps.putMapping();
        log.info("新索引与 Mapping 已初始化");

        // 2. 从数据库查询所有数据
        List<BookDoc> allBooks = bookMapper.findAllBookDocs();

        if (CollUtil.isEmpty(allBooks)) {
            log.warn("数据库中没有可同步的书籍数据");
            return;
        }

        // 3. 批量写入 ES (Spring Data ES 会自动处理分批推送)
        try {
            esTemplate.save(allBooks);
            log.info("同步成功！共导入 {} 条书籍数据", allBooks.size());
        } catch (Exception e) {
            log.error("同步过程中发生异常", e);
        }
    }
}
