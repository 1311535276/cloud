//package com.accp.business.config.swagger;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@Profile("swagger")
//@EnableWebMvc
//@EnableSwagger2
//@ComponentScan(basePackages="com.accp.business.config.swagger")
//@ComponentScan({"com.accp.business.controller"})
//public class SwaggerConfig implements WebMvcConfigurer  {
//  private static final Logger LOG = LoggerFactory.getLogger(SwaggerConfig.class);
//
//  @Bean
//  public Docket api() {
//    return new Docket(DocumentationType.SWAGGER_2)
//            .select()
//            .apis(RequestHandlerSelectors.any())
//            .paths(PathSelectors.any())
//            .build();
//  }
//}
