package com.accp.server.service;

import com.accp.server.domain.Chapter;
import com.accp.server.domain.ChapterExample;
import com.accp.server.mapper.ChapterMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChapterService {

@Resource
    public ChapterMapper chapterMapper;
    public List<Chapter> list(){
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.createCriteria().andIdEqualTo("1");
        //查询 where  id==1 IdEqualTo
        //多个查询 需要在表达式后面继续and xxx
        chapterExample.setOrderByClause("id desc");
        //chapterExample   好用啊 条件筛选
        return chapterMapper.selectByExample(chapterExample);
    }
}
