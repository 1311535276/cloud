package com.accp.server.service;

import com.accp.server.domain.Category;
import com.accp.server.domain.CourseCategory;
import com.accp.server.domain.CourseCategoryExample;
import com.accp.server.dto.CategoryDto;
import com.accp.server.dto.CourseCategoryDto;
import com.accp.server.dto.PageDto;
import com.accp.server.mapper.CourseCategoryMapper;
import com.accp.server.util.CopyUtil;
import com.accp.server.util.UuidUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class CourseCategoryService {

private static final Logger LOG = LoggerFactory.getLogger(CourseCategoryService.class);

    @Resource
    public CourseCategoryMapper courseCategoryMapper;
    /**
    *列表查询
    */
    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CourseCategoryExample courseCategoryExample = new CourseCategoryExample();
        //查询
        List<CourseCategory> courseCategoryList=courseCategoryMapper.selectByExample(courseCategoryExample);
       //PageHelper内置的方法 pageinfo
        PageInfo<CourseCategory> pageInfo=new PageInfo<>(courseCategoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseCategoryDto> courseCategoryDtoList=new ArrayList<CourseCategoryDto>();
        for (int i = 0,l =courseCategoryList.size();i<l; i++) {
            CourseCategory courseCategory = courseCategoryList.get(i);
            CourseCategoryDto courseCategoryDto = new CourseCategoryDto();
            BeanUtils.copyProperties(courseCategory,courseCategoryDto);
            courseCategoryDtoList.add(courseCategoryDto);
        }
        pageDto.setList(courseCategoryDtoList);
    }

    /**
     * save:增 改
     */
    public void save(CourseCategoryDto courseCategoryDto){
        CourseCategory courseCategory= CopyUtil.copy(courseCategoryDto,CourseCategory.class);
        //判断id是否为空 为空就新增
     if(StringUtil.isEmpty(courseCategoryDto.getId())){
         LOG.info("进入新增:{}",courseCategoryDto);
         this.insert(courseCategory);
     }else{
         LOG.info("进入修改:{}",courseCategoryDto);
         //不为空id 就是修改 进入修改方法
        this.update(courseCategory);
     }
    }
    /**
     * 新增
     */
    private void insert(CourseCategory  courseCategory ){

        //循环mysql里面有没有时间这个字段,如果有的话就要insert||update;
        //判断是否有时间(createdAt)这个字段
        courseCategory.setId(UuidUtil.getShortUuid());
//        CourseCategory courseCategory = new CourseCategory();
//        BeanUtils.copyProperties(courseCategoryDto,courseCategory);
        courseCategoryMapper.insert(courseCategory);
    }
    /**
     *  修改
     */
    private void update(CourseCategory  courseCategory ){
        //修改时间
        courseCategoryMapper.updateByPrimaryKey(courseCategory);

    }

    /**
     * 删除
     */
    public void delete(String id){
    courseCategoryMapper.deleteByPrimaryKey(id);
    }


    /**
     * 根据某一课程，先湾空课程分类，再保存课程分类
     * @param courseId
     * @param dtoList
     * 外层save增加了事务，saveBatch按理可以不加事务。
     * 但是由于本身也是多个sql操作，且以后可能被多个地方调用，
     * 为了防止外层save忘记加事务，所以在saveBatch加事务，以防万一。
     */
    @Transactional
    public void saveBatch (String courseId, List<CategoryDto> dtoList){
      CourseCategoryExample example=new CourseCategoryExample();
    example.createCriteria().andCourseIdEqualTo(courseId);
      courseCategoryMapper.deleteByExample(example);

    for (int i = 0; i < dtoList.size(); i++) {
    CategoryDto categoryDto =dtoList.get(i);
    CourseCategory courseCategory =new CourseCategory();
    courseCategory.setId(UuidUtil.getShortUuid());
    courseCategory.setCourseId(courseId);
    courseCategory.setCategoryId(categoryDto.getId());
    insert(courseCategory);
    }
        }
    /**
     * 查找课程下所有分类
     * @param courseId
     */
    public List<CourseCategoryDto> listByCourse(String courseId) {
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseCategory> courseCategoryList = courseCategoryMapper.selectByExample(example);
        return CopyUtil.copyList(courseCategoryList, CourseCategoryDto.class);
    }
}
