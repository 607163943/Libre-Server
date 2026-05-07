package com.libre.service.common.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.enums.ExceptionEnums;
import com.libre.exception.UploadException;
import com.libre.mapper.FileUploadMapper;
import com.libre.pojo.po.FileUpload;
import com.libre.pojo.vo.common.UploadFileVO;
import com.libre.service.common.CommonFileUploadService;
import lombok.RequiredArgsConstructor;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CommonFileUploadServiceImpl extends ServiceImpl<FileUploadMapper, FileUpload> implements CommonFileUploadService {

    private final FileStorageService fileStorageService;//注入实列

    /**
     * 上传文件
     *
     * @param file 文件实例
     * @return 文件路径
     */
    @Override
    public UploadFileVO uploadFile(MultipartFile file) {
        // 将file文件转为md5
        String fileMd5 = getFileMd5(file);

        // 判断资源是否存在，数据库事实依据
        FileUpload fileUpload = lambdaQuery()
                .eq(FileUpload::getFileMd5, fileMd5)
                .one();
        if (fileUpload != null) {
            return UploadFileVO.builder()
                    .fileId(fileUpload.getId())
                    .fileUrl(fileUpload.getFileUrl())
                    .build();
        }

        // 使用当前年月日作为路径前缀
        String pathPrefix = DateUtil.format(LocalDateTime.now(), "yyyy/MM/dd/");

        // 获取文件后缀名
        String fileSuffix = FileUtil.getSuffix(file.getOriginalFilename());

        // 上传文件
        FileInfo fileInfo = fileStorageService.of(file)
                .setPath(pathPrefix)
                .setSaveFilename(fileMd5 + "." + fileSuffix)
                .setContentType(file.getContentType())
                .upload();
        // 记录上传资源
        fileUpload = FileUpload.builder()
                .fileMd5(fileMd5)
                .fileName(file.getOriginalFilename())
                .ossPath(fileInfo.getPath())
                .fileUrl(fileInfo.getUrl())
                .fileSize(fileInfo.getSize())
                .mimeType(file.getContentType())
                .build();

        save(fileUpload);

        return UploadFileVO.builder()
                .fileId(fileUpload.getId())
                .fileUrl(fileUpload.getFileUrl())
                .build();
    }

    private String getFileMd5(MultipartFile file) {
        try {
            return DigestUtil.md5Hex(file.getInputStream());
        } catch (IOException e) {
            throw new UploadException(ExceptionEnums.FILE_MD5_ERROR);
        }
    }
}
