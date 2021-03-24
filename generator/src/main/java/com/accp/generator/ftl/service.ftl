package com.accp.server.service;

import com.accp.server.domain.${Domain};
import com.accp.server.domain.${Domain}Example;
import com.accp.server.dto.${Domain}Dto;
import com.accp.server.dto.PageDto;
import com.accp.server.mapper.${Domain}Mapper;
import com.accp.server.util.CopyUtil;
import com.accp.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
<#list typeSet as type>
    <#if type =='Date'>
import java.util.Date;
    </#if>
</#list>
@Service
public class ${Domain}Service {

private static final Logger LOG = LoggerFactory.getLogger(${Domain}Service.class);

    @Resource
    public ${Domain}Mapper ${domain}Mapper;
    /**
    *列表查询
    */
    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        <#list fieldList as field>
            <#if field.nameHump=='sort'>
                ${domain}Example.setOrderByClause("sort asc");
            </#if>
        </#list>
        //查询
        List<${Domain}> ${domain}List=${domain}Mapper.selectByExample(${domain}Example);
       //PageHelper内置的方法 pageinfo
        PageInfo<${Domain}> pageInfo=new PageInfo<>(${domain}List);
        pageDto.setTotal(pageInfo.getTotal());
        List<${Domain}Dto> ${domain}DtoList=new ArrayList<${Domain}Dto>();
        for (int i = 0,l =${domain}List.size();i<l; i++) {
            ${Domain} ${domain} = ${domain}List.get(i);
            ${Domain}Dto ${domain}Dto = new ${Domain}Dto();
            BeanUtils.copyProperties(${domain},${domain}Dto);
            ${domain}DtoList.add(${domain}Dto);
        }
        pageDto.setList(${domain}DtoList);
    }

    /**
     * save:增 改
     */
    public void save(${Domain}Dto ${domain}Dto){
        ${Domain} ${domain}= CopyUtil.copy(${domain}Dto,${Domain}.class);
        //判断id是否为空 为空就新增
     if(StringUtil.isEmpty(${domain}Dto.getId())){
         LOG.info("进入新增:{}",${domain}Dto);
         this.insert(${domain});
     }else{
         LOG.info("进入修改:{}",${domain}Dto);
         //不为空id 就是修改 进入修改方法
        this.update(${domain});
     }
    }
    /**
     * 新增
     */
    private void insert(${Domain}  ${domain} ){
        <#list typeSet as type>
            <#if type =='Date'>
                Date now =new Date();
            </#if>
        </#list>

        //循环mysql里面有没有时间这个字段,如果有的话就要insert||update;
        //判断是否有时间(createdAt)这个字段
        <#list fieldList as field>
            <#if field.nameHump=='createdAt'>
                ${domain}.setCreatedAt(now);
            </#if>
            <#if field.nameHump=='updatedAt'>
                ${domain}.setUpdatedAt(now);
            </#if>
        </#list>
        ${domain}.setId(UuidUtil.getShortUuid());
//        ${Domain} ${domain} = new ${Domain}();
//        BeanUtils.copyProperties(${domain}Dto,${domain});
        ${domain}Mapper.insert(${domain});
    }
    /**
     *  修改
     */
    private void update(${Domain}  ${domain} ){
        //修改时间
        <#list fieldList as field>
        <#if field.nameHump=='updatedAt'>
            ${domain}.setUpdatedAt(new Date());
        </#if>
        </#list>
        ${domain}Mapper.updateByPrimaryKey(${domain});

    }

    /**
     * 删除
     */
    public void delete(String id){
    ${domain}Mapper.deleteByPrimaryKey(id);
    }

}
