package com.accp.file.controller.admin;

import com.accp.server.dto.FileDto;
import com.accp.server.dto.PageDto;
import com.accp.server.dto.ResponseDto;
import com.accp.server.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import com.accp.server.util.ValidatorUtil;

import javax.annotation.Resource;


/**
 * @author Mr.黄
 */
@RestController
@RequestMapping("/admin/file")
public class FileController {

  @Resource
  private FileService fileService;
  public static final String BUSINESS_NAME = "文件";

  @PostMapping("/list")
  public ResponseDto list(@RequestBody PageDto pageDto) {
    //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
    ResponseDto responseDto = new ResponseDto();
    //存储结果: 可看底层代码
    responseDto.setContent(pageDto);
    fileService.list(pageDto);
    return responseDto;
  }

  private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

  /**
   * 增+修，id有值时更新，无值时新增
   */
  @PostMapping("/save")
  public ResponseDto save(@RequestBody FileDto fileDto) {
    //保存校验
    ValidatorUtil.require(fileDto.getPath(), "相对路径");
    ValidatorUtil.length(fileDto.getPath(), "相对路径", 1, 100);
    ValidatorUtil.length(fileDto.getName(), "文件名", 1, 100);
    ValidatorUtil.length(fileDto.getSuffix(), "后缀", 1, 10);
    ValidatorUtil.length(fileDto.getKey(), "文件标识", 1, 32);
    //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
    ResponseDto responseDto = new ResponseDto();
    //给file表的id 存储Uuid(特殊处理的id,64位8位数字符)
    LOG.info("fileDto:{}", fileDto);

    fileService.save(fileDto);
    //存储结果: 可看底层代码
    responseDto.setContent(fileDto);
    return responseDto;
  }

  /**
   * 删 传入id
   *
   * @PathVariable 接收请求路径中占位符的值
   */
  @DeleteMapping("/delete/{id}")
  public ResponseDto delete(@PathVariable String id) {
    //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
    ResponseDto responseDto = new ResponseDto();
    //给file表的id 存储Uuid(特殊处理的id,64位8位数字符)
    LOG.info("id:{}", id);

    fileService.delete(id);
    return responseDto;
  }
}
