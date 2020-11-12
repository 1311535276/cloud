package com.accp.generator.server;

import com.accp.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 启动类
 */
public class ServerGenerator {
    static String MODULE="business";
    //业务层项目路径
    static String toServicePath= "server\\src\\main\\java\\com\\accp\\server\\service\\";
    //控制层项目路径
    static String toControllerPath =MODULE+"\\src\\main\\java\\com\\accp\\"+MODULE+"\\controller\\admin\\";



    public static void main(String[] args) throws IOException,TemplateException
    {
        String Domain="Section";
        String domain="section";
        String tableNameCn="小节";
        String module=MODULE;

        Map<String,Object> map=new HashMap<>();
        map.put("Domain",Domain);
        map.put("domain",domain);
        map.put("tableNameCn",tableNameCn);
        map.put("module",module);

        //service的路径 ::业务层生成路径
        FreemarkerUtil.initConfig("service.ftl");
        FreemarkerUtil.generator(toServicePath+Domain+"Service.java",map);

        //controller的路径 ::控制层路径
        FreemarkerUtil.initConfig("controller.ftl");
        FreemarkerUtil.generator(toControllerPath+Domain+"Controller.java",map);

    }

}
