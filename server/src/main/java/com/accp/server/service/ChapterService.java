package com.accp.server.service;

import com.accp.server.domain.Chapter;
import com.accp.server.domain.ChapterExample;
import com.accp.server.dto.ChapterDto;
import com.accp.server.dto.PageDto;
import com.accp.server.mapper.ChapterMapper;
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
public class ChapterService {

    @Resource
    public ChapterMapper chapterMapper;
    public void list(PageDto pageDto){
//        分页 (第几页,每页第几条);
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        //new 实体类
        ChapterExample chapterExample = new ChapterExample();
//        chapterExample.createCriteria().andIdEqualTo("1");
//        chapterExample.setOrderByClause("id desc");
        //查询
        List<Chapter> chapterList=chapterMapper.selectByExample(chapterExample);
       //PageHelper内置的方法 pageinfo
        PageInfo<Chapter> pageInfo=new PageInfo<>(chapterList);
        pageDto.setTotal(pageInfo.getTotal());

        List<ChapterDto> chapterDtoList=new ArrayList<ChapterDto>();
        for (int i = 0,l =chapterList.size();i<l; i++) {
            Chapter chapter = chapterList.get(i);
            ChapterDto chapterDto = new ChapterDto();
            BeanUtils.copyProperties(chapter,chapterDto);
            chapterDtoList.add(chapterDto);
        }
        pageDto.setList(chapterDtoList);
    }
    private static final Logger LOG = LoggerFactory.getLogger(ChapterService.class);
    /**
     * save:增 改
     */
    public void save(ChapterDto chapterDto){
        Chapter chapter= CopyUtil.copy(chapterDto,Chapter.class);
        //判断id是否为空 为空就新增
     if(StringUtil.isEmpty(chapterDto.getId())){
         LOG.info("进入新增:{}",chapterDto);
         this.insert(chapter);
     }else{
         LOG.info("进入修改:{}",chapterDto);
         //不为空id 就是修改 进入修改方法
        this.update(chapter);
     }
    }
    /**
     * 新增
     */
    private void insert(Chapter  chapter ){
        chapter.setId(UuidUtil.getShortUuid());
//        Chapter chapter = new Chapter();
//        BeanUtils.copyProperties(chapterDto,chapter);
        chapterMapper.insert(chapter);
    }
    /**
     *  修改
     */
    private void update(Chapter  chapter ){
        chapterMapper.updateByPrimaryKey(chapter);
    }

    /**
     * 删除
     */
    public void delete(String id){
    chapterMapper.deleteByPrimaryKey(id);
    }

}
