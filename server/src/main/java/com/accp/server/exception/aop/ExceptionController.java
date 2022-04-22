package com.accp.server.exception.aop;

import com.accp.server.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName ExceptionController
 * @Description 全局异常扑获
 * @Author Administrator
 * @Date 2019/3/12 8:58
 * 扑获  全局的异常  没有这个注解的话   只能扑获本类的
 */
@ControllerAdvice
public class ExceptionController {

  private static final Logger LOG = LoggerFactory.getLogger(ExceptionController.class);

  /**
   * 全局异常扑获的处理
   */
  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public ResponseDto exceptionHandle(Exception e, HttpServletRequest request) {
    ResponseDto responseDto = new ResponseDto();
    LOG.warn(e.getMessage(), e);
    String errorMsg = e.getMessage();
    if (e instanceof DataIntegrityViolationException) {
      errorMsg = "其他地方已使用,无法操作";
      responseDto.setSuccess(false);
      responseDto.setMessage(errorMsg);
      return responseDto;
    }
    return responseDto;
  }

  //异常扑获的处理
  //@ExceptionHandler(value = {ConstraintViolationException.class, BindException.class, MethodArgumentNotValidException.class})
  //@ResponseBody
  //public Object constraintViolationExceptionHandle(Exception e, HttpServletRequest request) {
  //  String message;
  //  if (e instanceof ConstraintViolationException) {
  //    ConstraintViolationException ce = (ConstraintViolationException) e;
  //    Iterator<ConstraintViolation<?>> iterator =
  //            ce.getConstraintViolations().iterator();
  //    StringBuilder sb = new StringBuilder();
  //    while (iterator.hasNext()) {
  //      sb.append(iterator.next().getMessage());
  //    }
  //    message = sb.toString();
  //  } else if (e instanceof MethodArgumentNotValidException) {
  //    MethodArgumentNotValidException me = (MethodArgumentNotValidException) e;
  //    List<ObjectError> allErrors = me.getBindingResult().getAllErrors();
  //    StringBuilder sb = new StringBuilder();
  //    for (ObjectError err : allErrors) {
  //      sb.append(err.getDefaultMessage());
  //    }
  //    message = sb.toString();
  //  } else if (e instanceof BindException) {
  //    BindException ce = (BindException) e;
  //    List<ObjectError> allErrors = ce.getAllErrors();
  //    StringBuilder sb = new StringBuilder();
  //
  //    for (ObjectError err : allErrors) {
  //      sb.append(err.getDefaultMessage());
  //    }
  //    message = sb.toString();
  //  } else {
  //    message = e.getLocalizedMessage();
  //  }
  //  //LOG.info(e.getMessage(), e);
  //  return failure(message);
  //}

  //异常扑获的处理
  //@ExceptionHandler(value = {AuthenticationException.class})
  //@ResponseBody
  //public Object shiroExceptionHandle(Exception e, HttpServletRequest request) {
  //  String message = e.getMessage();
  //  LOG.error(e.getMessage(), e);
  //  //LOG.info(e.getMessage(), e);
  //  return failure("账号或密码不正确");
  //}
}
