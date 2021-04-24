package com.accp.file.controller.admin;

import com.accp.server.dto.ResponseDto;
import com.accp.server.util.UuidUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@RequestMapping("/admin")
@RestController
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    public static final String BUSINESS_NAME = "文件上传";

    @RequestMapping("/upload")
    public ResponseDto upload(@RequestParam MultipartFile file) throws IOException {
        LOG.info("上传文件开始:{}",file);
        LOG.info(file.getOriginalFilename());
        LOG.info(String.valueOf(file.getSize()));

        //保存文件到本地
        String fileName = file.getOriginalFilename();
        String key = UuidUtil.getShortUuid();
        String fullPath ="E:/newCodeProject/videoCloud/file1/teacher/"+ key +"_"+ fileName;
//        E:\newCodeProject\videoCloud\file1\teacher
        File dest = new File(fullPath);
        //transferTo才是写入 把传进来的资源:file给写进去 路径:dest的路径
        file.transferTo(dest);
        //输出全路径
        LOG.info(dest.getAbsolutePath());

        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }
    }
