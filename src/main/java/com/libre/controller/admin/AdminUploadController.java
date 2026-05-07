package com.libre.controller.admin;

import com.libre.pojo.vo.common.UploadFileVO;
import com.libre.result.Result;
import com.libre.service.common.CommonFileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "上传接口")
@RequiredArgsConstructor
@RequestMapping("/admin/upload")
@RestController
public class AdminUploadController {
    private final CommonFileUploadService fileUploadService;

    @ApiOperation("上传文件")
    @PostMapping
    public Result<UploadFileVO> uploadFile(@RequestParam("file") MultipartFile file) {
        UploadFileVO uploadFileVO=fileUploadService.uploadFile(file);
        return Result.success(uploadFileVO);
    }
}
