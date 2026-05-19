package com.libre.service;

import com.libre.mapper.BookMapper;
import com.libre.pojo.doc.BookDoc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;

import java.util.List;

@SpringBootTest
class EsDataSyncTest {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private BookMapper bookMapper; // 引入你的 Mapper

    @Test
    void syncAllBooksToEs() {
        int page = 1;
        int size = 1000; // 每批次从MySQL捞取1000条，兼顾内存与速度
        long totalSynced = 0;

        System.out.println("====== 开始同步 MySQL 图书数据至 Elasticsearch ======");
        long startTime = System.currentTimeMillis();

        while (true) {
            // 1. 分页从数据库中查出对应的 BookDoc 列表
            // 注意：这里建议在你的 Mapper 映射或 SQL 里，只查出 `is_delete = 0` 的未删除数据
            int offset = (page - 1) * size;
            List<BookDoc> bookDocList = bookMapper.findBookDocPage(offset, size);

            // 如果这一页没数据了，说明全部同步完成，跳出循环
            if (bookDocList == null || bookDocList.isEmpty()) {
                break;
            }

            // 2. 调用 elasticsearchRestTemplate 的 save 方法（传入集合即为批量插入）
            elasticsearchRestTemplate.save(bookDocList);

            totalSynced += bookDocList.size();
            System.out.println("进度提示：已成功同步 " + totalSynced + " 条数据...");

            // 3. 准备下一页
            page++;
        }

        long endTime = System.currentTimeMillis();
        System.out.println("====== 同步完成 ======");
        System.out.println("共计同步数据：" + totalSynced + " 条");
        System.out.println("总计耗时：" + (endTime - startTime) / 1000.0 + " 秒");
    }
}