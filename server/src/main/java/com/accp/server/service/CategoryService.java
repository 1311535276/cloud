package com.accp.server.service;

import com.accp.server.domain.Category;
import com.accp.server.domain.CategoryExample;
import com.accp.server.dto.CategoryDto;
import com.accp.server.dto.PageDto;
import com.accp.server.mapper.CategoryMapper;
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
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    public CategoryMapper categoryMapper;

    /**
     * 列表查询
     */
    public List<CategoryDto> all() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        //查询
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categoryList, CategoryDto.class);

        return categoryDtoList;
    }


    /**
     * 列表查询
     */
    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        //查询
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        //PageHelper内置的方法 pageinfo
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        pageDto.setTotal(pageInfo.getTotal());
        List<CategoryDto> categoryDtoList = new ArrayList<CategoryDto>();
        for (int i = 0, l = categoryList.size(); i < l; i++) {
            Category category = categoryList.get(i);
            CategoryDto categoryDto = new CategoryDto();
            BeanUtils.copyProperties(category, categoryDto);
            categoryDtoList.add(categoryDto);
        }
        pageDto.setList(categoryDtoList);
    }

    /**
     * save:增 改
     */
    public void save(CategoryDto categoryDto) {
        Category category = CopyUtil.copy(categoryDto, Category.class);
        //判断id是否为空 为空就新增
        if (StringUtil.isEmpty(categoryDto.getId())) {
            LOG.info("进入新增:{}", categoryDto);
            this.insert(category);
        } else {
            LOG.info("进入修改:{}", categoryDto);
            //不为空id 就是修改 进入修改方法
            this.update(category);
        }
    }

    /**
     * 新增
     */
    private void insert(Category category) {

        //循环mysql里面有没有时间这个字段,如果有的话就要insert||update;
        //判断是否有时间(createdAt)这个字段
        category.setId(UuidUtil.getShortUuid());
//        Category category = new Category();
//        BeanUtils.copyProperties(categoryDto,category);
        categoryMapper.insert(category);
    }

    /**
     * 修改
     */
    private void update(Category category) {
        //修改时间
        categoryMapper.updateByPrimaryKey(category);

    }

    /**
     * 删除
     */
    @Transactional
    public void delete(String id) {
        //删除了一级分类的话,就一定也要跟着删除二级分类
        deleteChildren(id);
        categoryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 删除子分类
     *
     * @param id
     */
    public void deleteChildren(String id) {
        Category category = categoryMapper.selectByPrimaryKey(id);
        //判断是否一级分类
        if ("00000000".equals(category.getParent())) {
            //如果是一级分类,需要删除旗下的二级分类
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentEqualTo(category.getId());
            categoryMapper.deleteByExample(example);
        }
    }
}
