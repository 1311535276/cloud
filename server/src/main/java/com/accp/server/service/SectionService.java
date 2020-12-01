package com.accp.server.service;

import com.accp.server.domain.Section;
import com.accp.server.domain.SectionExample;
import com.accp.server.dto.SectionDto;
import com.accp.server.dto.PageDto;
import com.accp.server.mapper.SectionMapper;
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
import java.util.Date;
@Service
public class SectionService {
    private static final Logger LOG = LoggerFactory.getLogger(SectionService.class);
    @Resource
    public SectionMapper sectionMapper;
    /**
    *列表查询
    */
    public void list(PageDto pageDto){
//        分页 (第几页,每页第几条);
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        //new 实体类
        SectionExample sectionExample = new SectionExample();
        sectionExample.setOrderByClause("sort asc");
//        sectionExample.createCriteria().andIdEqualTo("1");
//        sectionExample.setOrderByClause("id desc");
        //查询
        List<Section> sectionList=sectionMapper.selectByExample(sectionExample);
       //PageHelper内置的方法 pageinfo
        PageInfo<Section> pageInfo=new PageInfo<>(sectionList);
        pageDto.setTotal(pageInfo.getTotal());

        List<SectionDto> sectionDtoList=new ArrayList<SectionDto>();
        for (int i = 0,l =sectionList.size();i<l; i++) {
            Section section = sectionList.get(i);
            SectionDto sectionDto = new SectionDto();
            BeanUtils.copyProperties(section,sectionDto);
            sectionDtoList.add(sectionDto);
        }
        pageDto.setList(sectionDtoList);
    }

    /**
     * save:增 改
     */
    public void save(SectionDto sectionDto){
        Section section= CopyUtil.copy(sectionDto,Section.class);
        //判断id是否为空 为空就新增
     if(StringUtil.isEmpty(sectionDto.getId())){
         LOG.info("进入新增:{}",sectionDto);
         this.insert(section);
     }else{
         LOG.info("进入修改:{}",sectionDto);
         //不为空id 就是修改 进入修改方法
        this.update(section);
     }
    }
    /**
     * 新增
     */
    private void insert(Section  section ){
        Date now =new Date();
        //循环mysql里面有没有时间这个字段,如果有的话就要insert||update;
        //判断是否有时间(createdAt)这个字段
                section.setCreatedAt(now);
                section.setUpdatedAt(now);
        section.setId(UuidUtil.getShortUuid());
//        Section section = new Section();
//        BeanUtils.copyProperties(sectionDto,section);
        sectionMapper.insert(section);
    }
    /**
     *  修改
     */
    private void update(Section  section ){
        //修改时间
            section.setUpdatedAt(new Date());
        sectionMapper.updateByPrimaryKey(section);

    }

    /**
     * 删除
     */
    public void delete(String id){
    sectionMapper.deleteByPrimaryKey(id);
    }

}
