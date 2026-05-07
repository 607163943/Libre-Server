package com.libre.scheduler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.libre.pojo.po.FileUpload;
import com.libre.pojo.po.UploadFileRef;
import com.libre.service.common.CommonFileUploadService;
import com.libre.service.common.CommonUploadFileRefService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class ClearUnRefOSSFileScheduler {
    private final FileStorageService fileStorageService;
    private final CommonUploadFileRefService uploadFileRefService;
    private final CommonFileUploadService fileUploadService;

    @Value("${oss.platform_name}")
    private String platformName;

    // 每天凌晨4点执行
    //@Scheduled(cron = "0 0 4 * * ?")
    // 临时改成10分钟
    @Scheduled(cron = "0 0/10 * * * ?")
    public void clearUnRefOSSFile() {
        log.info("开始清理未使用的OSS文件");
        // 获取引用文件id
        List<UploadFileRef> uploadFileRefs = uploadFileRefService.lambdaQuery()
                .list();
        List<Long> refFileIds = uploadFileRefs.stream().map(UploadFileRef::getFileId).collect(Collectors.toList());

        // 获取未使用文件集合
        List<FileUpload> fileUploads = fileUploadService.lambdaQuery()
                .notIn(CollUtil.isNotEmpty(refFileIds),FileUpload::getId, refFileIds)
                .list();
        for (FileUpload fileUpload : fileUploads) {
            log.info("开始清理文件：{},id:{}", fileUpload.getFileName(),fileUpload.getId());
            // 获取文件拓展名
            String fileSuffix = FileUtil.getSuffix(fileUpload.getFileName());
            FileInfo fileInfo = new FileInfo()
                    .setPlatform(platformName)
                    .setPath(fileUpload.getOssPath())
                    // OSS使用文件md5作为文件名
                    .setFilename(fileUpload.getFileMd5() + "." + fileSuffix);

            // 删除OSS文件
            fileStorageService.delete(fileInfo);
            // 删除文件资源
            fileUploadService.lambdaUpdate()
                    .set(FileUpload::getIsDelete,System.currentTimeMillis())
                    .eq(FileUpload::getId, fileUpload.getId())
                    .update();
        }
        log.info("清理未使用的OSS文件完成");
    }
}
