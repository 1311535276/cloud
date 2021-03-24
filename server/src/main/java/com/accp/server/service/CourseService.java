package com.accp.server.service;

import com.accp.server.domain.Course;
import com.accp.server.domain.CourseExample;
import com.accp.server.dto.CourseDto;
import com.accp.server.dto.PageDto;
import com.accp.server.mapper.CourseMapper;
import com.accp.server.mapper.my.MyCourseMapper;
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
public class CourseService {

private static final Logger LOG = LoggerFactory.getLogger(CourseService.class);

    @Resource
    public CourseMapper courseMapper;

    @Resource
    public MyCourseMapper myCourseMapper;
    /**
    *列表查询
    */
    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CourseExample courseExample = new CourseExample();
                courseExample.setOrderByClause("sort asc");
        //查询
        List<Course> courseList=courseMapper.selectByExample(courseExample);
       //PageHelper内置的方法 pageinfo
        PageInfo<Course> pageInfo=new PageInfo<>(courseList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseDto> courseDtoList=new ArrayList<CourseDto>();
        for (int i = 0,l =courseList.size();i<l; i++) {
            Course course = courseList.get(i);
            CourseDto courseDto = new CourseDto();
            BeanUtils.copyProperties(course,courseDto);
            courseDtoList.add(courseDto);
        }
        pageDto.setList(courseDtoList);
    }

    /**
     * save:增 改
     */
    public void save(CourseDto courseDto){
        Course course= CopyUtil.copy(courseDto,Course.class);
        //判断id是否为空 为空就新增
     if(StringUtil.isEmpty(courseDto.getId())){
         LOG.info("进入新增:{}",courseDto);
         this.insert(course);
     }else{
         LOG.info("进入修改:{}",courseDto);
         //不为空id 就是修改 进入修改方法
        this.update(course);
     }
    }
    /**
     * 新增
     */
    private void insert(Course  course ){
        Date now =new Date();
        //循环mysql里面有没有时间这个字段,如果有的话就要insert||update;
        //判断是否有时间(createdAt)这个字段
                course.setCreatedAt(now);
                course.setUpdatedAt(now);
        course.setId(UuidUtil.getShortUuid());
//        Course course = new Course();
//        BeanUtils.copyProperties(courseDto,course);
        courseMapper.insert(course);
    }
    /**
     *  修改
     */
    private void update(Course  course ){
        //修改时间
            course.setUpdatedAt(new Date());
        courseMapper.updateByPrimaryKey(course);

    }

    /**
     * 删除
     */
    public void delete(String id){
    courseMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新课程时长
     * @param courseId
     */
   public void updateTime(String courseId){
        LOG.info("更新课程时长:{}",courseId);
       myCourseMapper.updateTime(courseId);
   }

}
