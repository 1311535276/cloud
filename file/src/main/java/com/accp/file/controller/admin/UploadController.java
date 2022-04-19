package com.accp.file.controller.admin;

import com.accp.server.dto.FileDto;
import com.accp.server.dto.ResponseDto;
import com.accp.server.enums.FileUseEnum;
import com.accp.server.service.FileService;
import com.accp.server.util.Base64ToMultipartFile;
import com.accp.server.util.UuidUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author Mr.黄
 */
@RequestMapping("/admin")
@RestController
public class UploadController {

  private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

  public static final String BUSINESS_NAME = "文件上传";

  @Value("${file.domain}")
  private String FILE_DOMAIN;
  @Value("${file.path}")
  private String FILE_PATH;
  //@Value("${vod.accessKeyId}")
  //private String accessKeyId;
  //@Value("${vod.accessKeySecret}")
  //private String accessKeySecret;

  @Resource
  private FileService fileService;

  @RequestMapping("/upload")
  public ResponseDto upload(@RequestParam MultipartFile file, String use) throws IOException {
    //输出list等一系列集合 要用到通配符{}
    //LOG.info("上传文件开始:{}", file);
    LOG.info("上传文件开始");
    LOG.info(file.getOriginalFilename());
    LOG.info(String.valueOf(file.getSize()));

    // 保存文件到本地
    FileUseEnum useEnum = FileUseEnum.getByCode(use);
    String key = UuidUtil.getShortUuid();
    String fileName = file.getOriginalFilename();
    //  E:\newCodeProject\videoCloud\file1\teacher
    String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

    //如果文件夹不存在则创建
    //判断枚举类型 T是teacher C是课程
    String dir = useEnum.name().toLowerCase();
    File fullDir = new File(FILE_PATH + dir);
    if (!fullDir.exists()) {
      fullDir.mkdir();
    }

    //String path="teacher/"+key+"."+suffix;
    String path = dir + File.separator + key + "." + suffix;

    //String fullPath = FILE_PATH + "teacher/" + key + "_" + fileName;
    String fullPath = FILE_PATH + path;
    File dest = new File(fullPath);
    //transferTo才是写入 把传进来的资源:file给写进去 路径:dest的路径
    file.transferTo(dest);
    //输出全路径
    LOG.info(dest.getAbsolutePath());
    LOG.info("保存文件记录开始");
    FileDto fileDto = new FileDto();
    fileDto.setPath(path);
    fileDto.setName(fileName);
    fileDto.setSize(Math.toIntExact(file.getSize()));
    fileDto.setSuffix(suffix);
    fileDto.setUse(use);
    fileService.save(fileDto);
    ResponseDto responseDto = new ResponseDto();
    //输出给前端 把图片(文件)资源地址
    //responseDto.setContent(FILE_DOMAIN + "teacher/" + key + "_" + fileName);
    //返回前端全路径
    fileDto. setPath(FILE_DOMAIN + path);
    responseDto. setContent(fileDto);
    //responseDto.setContent(FILE_DOMAIN + path);
    return responseDto;
  }
}
