package com.accp.file.config;

import com.accp.file.controller.admin.UploadController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

//    @Value("${file.path}")
//    private String FILE_PATH;
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/f/**").addResourceLocations("file:E:/newCodeProject/videoCloud/file1/");
//       http://127.0.0.1:9003/file/f/teacher/Gojh6nDp_6.jpg               ("file:D:/file/imooc/course");
//                                                                           E:\newCodeProject\videoCloud\file1\teacher
}
}