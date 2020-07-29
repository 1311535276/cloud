package com.accp.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableEurekaServer
@Component("com.accp")
@MapperScan("com.accp.server.mapper")
@ComponentScan("com.accp")
public class systemApplication {

	private static final Logger LOG = LoggerFactory.getLogger(systemApplication.class);

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(systemApplication.class);
		Environment env = app.run(args).getEnvironment();
		LOG.info("启动成功！！");
		LOG.info("System地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
	}

}
