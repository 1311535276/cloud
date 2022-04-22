package com.accp.server.dto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

public class ResponseDto<T> {

    private static final Logger LOG = LoggerFactory.getLogger(ResponseDto.class);

    /**
     * 业务上的成功或失败
     */
    private boolean success = true;

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回泛型数据，自定义类型
     */
    private T content;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResponseDto{");
        sb.append("success=").append(success);
        sb.append(", code='").append(code).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", content=").append(content);
        sb.append('}');
        return sb.toString();
    }
    /**
     *全局异常扑获的处理
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object exceptionHandle(Exception e, HttpServletRequest request) {
        LOG.info(e.getMessage(), e);
        String errorMsg = e.getMessage();
        if (e instanceof DataIntegrityViolationException){
            errorMsg = "其他地方已使用,无法操作";
            //setMessage(errorMsg);
            return errorMsg;
        }
        return errorMsg;
    }
}
