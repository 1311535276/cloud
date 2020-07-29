package com.accp.server.service;

import com.accp.server.domain.Test;
import com.accp.server.domain.TestExample;
import com.accp.server.mapper.TestMapper;

import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;

@Service
public class TestService {

@Resource
    public TestMapper testMapper;
    public List<Test> list(){

        TestExample testExample = new TestExample();

        testExample.createCriteria().andIdEqualTo("1");
        //查询 where  id==1 IdEqualTo
        //多个查询 需要在表达式后面继续and xxx
        testExample.setOrderByClause("id desc");
        //testExample   好用啊 条件筛选
        return testMapper.selectByExample(testExample);
    }
}
