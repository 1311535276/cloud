package com.accp.server.service;

import com.accp.server.domain.User;
import com.accp.server.domain.UserExample;
import com.accp.server.dto.LoginUserDto;
import com.accp.server.dto.ResourceDto;
import com.accp.server.dto.UserDto;
import com.accp.server.dto.PageDto;
import com.accp.server.exception.BusinessException;
import com.accp.server.exception.BusinessExceptionCode;
import com.accp.server.mapper.UserMapper;
import com.accp.server.mapper.my.MyUserMapper;
import com.accp.server.util.CopyUtil;
import com.accp.server.util.UuidUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Mr.黄
 */
@Service
public class UserService {

  private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

  @Resource
  public UserMapper userMapper;
  @Resource
  private MyUserMapper myUserMapper;

  /**
   * 列表查询
   */
  public void list(PageDto pageDto) {
    PageHelper.startPage(pageDto.getPage(), pageDto.getSize());
    UserExample userExample = new UserExample();
    //查询
    List<User> userList = userMapper.selectByExample(userExample);
    //PageHelper内置的方法 pageinfo
    PageInfo<User> pageInfo = new PageInfo<>(userList);
    pageDto.setTotal(pageInfo.getTotal());
    List<UserDto> userDtoList = new ArrayList<UserDto>();
    for (int i = 0, l = userList.size(); i < l; i++) {
      User user = userList.get(i);
      UserDto userDto = new UserDto();
      BeanUtils.copyProperties(user, userDto);
      userDtoList.add(userDto);
    }
    pageDto.setList(userDtoList);
  }

  /**
   * save:增 改
   */
  public void save(UserDto userDto) {
    User user = CopyUtil.copy(userDto, User.class);
    //判断id是否为空 为空就新增
    if (StringUtil.isEmpty(userDto.getId())) {
      LOG.info("进入新增:{}", userDto);
      this.insert(user);
    } else {
      LOG.info("进入修改:{}", userDto);
      //不为空id 就是修改 进入修改方法
      this.update(user);
    }
  }

  /**
   * 新增
   */
  private void insert(User user) {
    //循环mysql里面有没有时间这个字段,如果有的话就要insert||update;
    //判断是否有时间(createdAt)这个字段
    user.setId(UuidUtil.getShortUuid());
    //判断用户名称是否在数据库有 没有即可新增 有就要报异常给前端
    User userDb = this.selectByLoginName(user.getLoginName());
    if (userDb != null) {
      throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
    }
    userMapper.insert(user);
  }

  /**
   * 修改
   */
  private void update(User user) {
    //修改时间
    user.setPassword(null);
    //userMapper.updateByPrimaryKey(user);
      userMapper.updateByPrimaryKeySelective(user);
  }

  /**
   * 删除
   */
  public void delete(String id) {
    userMapper.deleteByPrimaryKey(id);
  }

  /**
   * 根据登录名查询用户信息
   *
   * @param loginName
   * @return
   */
  public User selectByLoginName(String loginName) {
    UserExample userExample = new UserExample();
    userExample.createCriteria().andLoginNameEqualTo(loginName);
    List<User> userList = userMapper.selectByExample(userExample);
    if (CollectionUtils.isEmpty(userList)) {
      return null;
    } else {
      return userList.get(0);
    }
  }

  /**
   * 重置密码
   *
   * @param userDto
   */
  public void savePassword(UserDto userDto) {
    User user = new User();
    user.setId(userDto.getId());
    user.setPassword(userDto.getPassword());
    userMapper.updateByPrimaryKeySelective(user);
  }

  /**
   * 登录
   *
   * @param userDto
   */
  public LoginUserDto login(UserDto userDto) {
    User user = selectByLoginName(userDto.getLoginName());
    if (user == null) {
      LOG.info("用户名不存在, {}", userDto.getLoginName());
      throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
    } else {
      if (user.getPassword().equals(userDto.getPassword())) {
        // 登录成功
        LoginUserDto loginUserDto = CopyUtil.copy(user, LoginUserDto.class);
        // 为登录用户读取权限
        setAuth(loginUserDto);
        return loginUserDto;
      } else {
        LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", userDto.getPassword(), user.getPassword());
        throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
      }
    }
  }


  /**
   * 为登录用户读取权限
   */
  private void setAuth(LoginUserDto loginUserDto) {
    List<ResourceDto> resourceDtoList = myUserMapper.findResources(loginUserDto.getId());
    loginUserDto.setResources(resourceDtoList);

    // 整理所有有权限的请求，用于接口拦截
    HashSet<String> requestSet = new HashSet<>();
    if (!CollectionUtils.isEmpty(resourceDtoList)) {
      for (int i = 0, l = resourceDtoList.size(); i < l; i++) {
        ResourceDto resourceDto = resourceDtoList.get(i);
        String arrayString = resourceDto.getRequest();
        List<String> requestList = JSON.parseArray(arrayString, String.class);
        if (!CollectionUtils.isEmpty(requestList)) {
          requestSet.addAll(requestList);
        }
      }
    }
    LOG.info("有权限的请求：{}", requestSet);
    loginUserDto.setRequests(requestSet);
  }
}
