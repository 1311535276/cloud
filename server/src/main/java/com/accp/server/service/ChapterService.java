package com.accp.server.service;

import com.accp.server.domain.Chapter;
import com.accp.server.domain.ChapterExample;
import com.accp.server.dto.ChapterDto;
import com.accp.server.dto.PageDto;
import com.accp.server.mapper.ChapterMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    public void save(ChapterDto chapterDto){
        Chapter chapter = new Chapter();
        BeanUtils.copyProperties(chapterDto,chapter);
    chapterMapper.insert(chapter);
    }
}
