package com.libre.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.libre.mapper.UploadFileRefMapper;
import com.libre.pojo.po.UploadFileRef;
import com.libre.service.common.CommonUploadFileRefService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CommonUploadFileRefServiceImpl extends ServiceImpl<UploadFileRefMapper, UploadFileRef> implements CommonUploadFileRefService {
}
