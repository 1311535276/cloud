package com.accp.file.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

/**
 * @author Mr.黄
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.accp")
@MapperScan("com.accp.server.mapper")
public class FileApplication {

	private static final Logger LOG = LoggerFactory.getLogger(FileApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FileApplication.class);
		Environment env = app.run(args).getEnvironment();
		LOG.info("启动成功！！");
		LOG.info("File地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
	}

}
