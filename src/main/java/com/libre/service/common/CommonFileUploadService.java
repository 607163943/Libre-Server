package com.libre.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.libre.pojo.po.FileUpload;
import com.libre.pojo.vo.common.UploadFileVO;
import org.springframework.web.multipart.MultipartFile;

public interface CommonFileUploadService extends IService<FileUpload> {
    /**
     * 上传文件
     * @param file 文件实例
     * @return OSS请求路径
     */
    UploadFileVO uploadFile(MultipartFile file);
}
