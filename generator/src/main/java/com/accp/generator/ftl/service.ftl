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

@Service
public class ${Domain}Service {

    @Resource
    public ${Domain}Mapper ${domain}Mapper;
    public void list(PageDto pageDto){
//        分页 (第几页,每页第几条);
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        //new 实体类
        ${Domain}Example ${domain}Example = new ${Domain}Example();
//        ${domain}Example.createCriteria().andIdEqualTo("1");
//        ${domain}Example.setOrderByClause("id desc");
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
    private static final Logger LOG = LoggerFactory.getLogger(${Domain}Service.class);
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
        ${domain}.setId(UuidUtil.getShortUuid());
//        ${Domain} ${domain} = new ${Domain}();
//        BeanUtils.copyProperties(${domain}Dto,${domain});
        ${domain}Mapper.insert(${domain});
    }
    /**
     *  修改
     */
    private void update(${Domain}  ${domain} ){
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }

    /**
     * 删除
     */
    public void delete(String id){
    ${domain}Mapper.deleteByPrimaryKey(id);
    }

}
