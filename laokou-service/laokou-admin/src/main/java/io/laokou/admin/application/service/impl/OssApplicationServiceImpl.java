package io.laokou.admin.application.service.impl;

import io.laokou.admin.application.service.OssApplicationService;
import io.laokou.admin.infrastructure.common.cloud.CloudFactory;
import io.laokou.admin.interfaces.vo.UploadVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Slf4j
public class OssApplicationServiceImpl implements OssApplicationService {

    @Autowired
    private CloudFactory cloudFactory;

    @Override
    public UploadVO upload(InputStream inputStream, String fileName, Long fileSize) throws Exception {
        UploadVO vo = new UploadVO();
        String url = cloudFactory.build().upload(inputStream, fileName, fileSize);
        log.info("上传文件地址：{}",url);
        vo.setUrl(url);
        return vo;
    }
}