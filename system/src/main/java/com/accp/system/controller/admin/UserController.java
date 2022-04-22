package com.accp.system.controller.admin;

import com.accp.server.dto.UserDto;
import com.accp.server.dto.PageDto;
import com.accp.server.dto.ResponseDto;
import com.accp.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;
import com.accp.server.util.ValidatorUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/admin/user")
public class UserController {

  @Resource
  private UserService userService;
  public static final String BUSINESS_NAME = "用户";

  @PostMapping("/list")
  public ResponseDto list(@RequestBody PageDto pageDto) {
    //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
    ResponseDto responseDto = new ResponseDto();
    //存储结果: 可看底层代码
    responseDto.setContent(pageDto);
    userService.list(pageDto);
    return responseDto;
  }

  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

  /**
   * 增+修，id有值时更新，无值时新增
   */
  @PostMapping("/save")
  public ResponseDto save(@RequestBody UserDto userDto) {
    //保存校验
    ValidatorUtil.require(userDto.getLoginName(), "登陆名");
    ValidatorUtil.length(userDto.getLoginName(), "登陆名", 1, 50);
    ValidatorUtil.length(userDto.getName(), "昵称", 1, 50);
    ValidatorUtil.require(userDto.getPassword(), "密码");
    //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
    ResponseDto responseDto = new ResponseDto();
    //给user表的id 存储Uuid(特殊处理的id,64位8位数字符)
    LOG.info("userDto:{}", userDto);
    userService.save(userDto);
    //存储结果: 可看底层代码
    responseDto.setContent(userDto);
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
    //给user表的id 存储Uuid(特殊处理的id,64位8位数字符)
    LOG.info("id:{}", id);
    userService.delete(id);
    return responseDto;
  }
  ///**
  // *全局异常扑获的处理
  // */
  //@ExceptionHandler(value = Exception.class)
  //@ResponseBody
  //public ResponseDto exceptionHandle(Exception e, HttpServletRequest request) {
  //  ResponseDto responseDto=new ResponseDto();
  //  LOG.info(e.getMessage(), e);
  //  String errorMsg = e.getMessage();
  //  if (e instanceof DataIntegrityViolationException){
  //    errorMsg = "其他地方已使用,无法操作";
  //    responseDto.setSuccess(false);
  //    responseDto.setMessage(errorMsg);
  //    return responseDto;
  //  }
  //  return responseDto;
  //}
}
