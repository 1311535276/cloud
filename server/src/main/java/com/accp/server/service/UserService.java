package com.accp.server.service;

import com.accp.server.domain.User;
import com.accp.server.domain.UserExample;
import com.accp.server.dto.UserDto;
import com.accp.server.dto.PageDto;
import com.accp.server.mapper.UserMapper;
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
public class UserService {

private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    public UserMapper userMapper;
    /**
    *列表查询
    */
    public void list(PageDto pageDto){
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        UserExample userExample = new UserExample();
        //查询
        List<User> userList=userMapper.selectByExample(userExample);
       //PageHelper内置的方法 pageinfo
        PageInfo<User> pageInfo=new PageInfo<>(userList);
        pageDto.setTotal(pageInfo.getTotal());
        List<UserDto> userDtoList=new ArrayList<UserDto>();
        for (int i = 0,l =userList.size();i<l; i++) {
            User user = userList.get(i);
            UserDto userDto = new UserDto();
            BeanUtils.copyProperties(user,userDto);
            userDtoList.add(userDto);
        }
        pageDto.setList(userDtoList);
    }

    /**
     * save:增 改
     */
    public void save(UserDto userDto){
        User user= CopyUtil.copy(userDto,User.class);
        //判断id是否为空 为空就新增
     if(StringUtil.isEmpty(userDto.getId())){
         LOG.info("进入新增:{}",userDto);
         this.insert(user);
     }else{
         LOG.info("进入修改:{}",userDto);
         //不为空id 就是修改 进入修改方法
        this.update(user);
     }
    }
    /**
     * 新增
     */
    private void insert(User  user ){

        //循环mysql里面有没有时间这个字段,如果有的话就要insert||update;
        //判断是否有时间(createdAt)这个字段
        user.setId(UuidUtil.getShortUuid());
//        User user = new User();
//        BeanUtils.copyProperties(userDto,user);
        userMapper.insert(user);
    }
    /**
     *  修改
     */
    private void update(User  user ){
        //修改时间
        userMapper.updateByPrimaryKey(user);

    }

    /**
     * 删除
     */
    public void delete(String id){
    userMapper.deleteByPrimaryKey(id);
    }

}
