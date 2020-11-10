package com.accp.generator.test;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TestUtil {
    //生成路径变量
    static String ftlPath = "generator\\src\\main\\java\\com\\accp\\generator\\test\\";
    static String toPath = "generator\\src\\main\\java\\com\\accp\\generator\\test\\";

    public static void main(String[] args) throws IOException, TemplateException {

        //读取模板
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        //读取模板所在的路径
        cfg.setDirectoryForTemplateLoading(new File(ftlPath));
        cfg.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_29));
        //读取模板具体所在的 就是哪一个模板
        Template temp = cfg.getTemplate("test.ftl");

        //根据模板生成模板文件
        FileWriter fw = new FileWriter(toPath + "Test.java");
        BufferedWriter bw = new BufferedWriter(fw);
        temp.process(null, bw);
        bw.flush();
        fw.close();
    }
}
