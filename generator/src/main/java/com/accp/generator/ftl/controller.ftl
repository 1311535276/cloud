package com.accp.${module}.controller.admin;

import com.accp.server.dto.${Domain}Dto;
import com.accp.server.dto.PageDto;
import com.accp.server.dto.ResponseDto;
import com.accp.server.service.${Domain}Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/admin/${domain}")
public class ${Domain}Controller {

    @Resource
    private ${Domain}Service ${domain}Service;
public static final String BUSINESS_NAME="${tableNameCn}";
@PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
    //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
    ResponseDto responseDto=new ResponseDto();
    //存储结果: 可看底层代码
    responseDto.setContent(pageDto);
    ${domain}Service.list(pageDto);
    return responseDto;
    }
    private static final Logger LOG = LoggerFactory.getLogger(${Domain}Controller.class);
    /**
     * 增+修，id有值时更新，无值时新增
     */
    @PostMapping("/save")
    public ResponseDto save(@RequestBody ${Domain}Dto ${domain}Dto){
    //保存校验
<#list fieldList as field>
    <#if !field.nullAble>
        ValidatorUtil.require(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}");
    </#if>
    <#if (field.length > 0)>
        ValidatorUtil.length(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}", 1, ${field.length});
    </#if>
</#list>
        //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
        ResponseDto responseDto=new ResponseDto();
        //给${domain}表的id 存储Uuid(特殊处理的id,64位8位数字符)
        LOG.info("${domain}Dto:{}",${domain}Dto);

        ${domain}Service.save(${domain}Dto);
        //存储结果: 可看底层代码
        responseDto.setContent(${domain}Dto);
        return responseDto;
    }

    /**
     * 删 传入id
     * @PathVariable 接收请求路径中占位符的值
     */
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        //ResponseDto: 业务接口数据回调(是否接口调用成功等一系列处理)
        ResponseDto responseDto=new ResponseDto();
        //给${domain}表的id 存储Uuid(特殊处理的id,64位8位数字符)
        LOG.info("id:{}",id);

        ${domain}Service.delete(id);
        return responseDto;
    }
}
