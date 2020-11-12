package com.accp.generator.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FreemarkerUtil {

    static String ftlPath = "generator\\src\\main\\java\\com\\accp\\generator\\ftl\\";
    /**
     * 读取模板具体所在的 就是哪一个模板 ::变量
     * 我们会用代码生成器生成controller service dto vue的代码，这些代
     */
    static Template temp;

    public static void initConfig(String ftlName) throws IOException {
        //读取模板
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        //读取模板所在的路径
        cfg.setDirectoryForTemplateLoading(new File(ftlPath));
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_29));
        //读取模板具体所在的 就是哪一个模板
        temp = cfg.getTemplate(ftlName);
    }

    //根据模板生成模板文件
    public static void generator(String fileName, Map<String, Object> map) throws IOException, TemplateException {
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        temp.process(map, bw);
        bw.flush();
        fw.close();
    }
}
