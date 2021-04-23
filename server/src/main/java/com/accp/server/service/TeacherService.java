package com.accp.server.service;

import com.accp.server.domain.Category;
import com.accp.server.domain.CategoryExample;
import com.accp.server.domain.Teacher;
import com.accp.server.domain.TeacherExample;
import com.accp.server.dto.CategoryDto;
import com.accp.server.dto.TeacherDto;
import com.accp.server.dto.PageDto;
import com.accp.server.mapper.TeacherMapper;
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
public class TeacherService {

private static final Logger LOG = LoggerFactory.getLogger(TeacherService.class);

    @Resource
    public TeacherMapper teacherMapper;
    /**
    *列表查询
    */
    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        TeacherExample teacherExample = new TeacherExample();
        //查询
        List<Teacher> teacherList=teacherMapper.selectByExample(teacherExample);
       //PageHelper内置的方法 pageinfo
        PageInfo<Teacher> pageInfo=new PageInfo<>(teacherList);
        pageDto.setTotal(pageInfo.getTotal());
        List<TeacherDto> teacherDtoList=new ArrayList<TeacherDto>();
        for (int i = 0,l =teacherList.size();i<l; i++) {
            Teacher teacher = teacherList.get(i);
            TeacherDto teacherDto = new TeacherDto();
            BeanUtils.copyProperties(teacher,teacherDto);
            teacherDtoList.add(teacherDto);
        }
        pageDto.setList(teacherDtoList);
    }

    /**
     * save:增 改
     */
    public void save(TeacherDto teacherDto){
        Teacher teacher= CopyUtil.copy(teacherDto,Teacher.class);
        //判断id是否为空 为空就新增
     if(StringUtil.isEmpty(teacherDto.getId())){
         LOG.info("进入新增:{}",teacherDto);
         this.insert(teacher);
     }else{
         LOG.info("进入修改:{}",teacherDto);
         //不为空id 就是修改 进入修改方法
        this.update(teacher);
     }
    }
    /**
     * 新增
     */
    private void insert(Teacher  teacher ){

        //循环mysql里面有没有时间这个字段,如果有的话就要insert||update;
        //判断是否有时间(createdAt)这个字段
        teacher.setId(UuidUtil.getShortUuid());
//       Teacher teacher = new Teacher();
//        BeanUtils.copyProperties(teacherDto,teacher);
        teacherMapper.insert(teacher);
    }
    /**
     *  修改
     */
    private void update(Teacher  teacher ){
        //修改时间
        teacherMapper.updateByPrimaryKey(teacher);

    }

    /**
     * 删除
     */
    public void delete(String id){
    teacherMapper.deleteByPrimaryKey(id);
    }

    /**
     * 列表查询
     */
    public List<TeacherDto> all() {
        TeacherExample teacherExample = new TeacherExample();
        //查询
        List<Teacher> teacherList = teacherMapper.selectByExample(teacherExample);
        List<TeacherDto> teacherDtoList = CopyUtil.copyList(teacherList, TeacherDto.class);
        return teacherDtoList;
    }
}
