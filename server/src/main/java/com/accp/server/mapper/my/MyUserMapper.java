package com.accp.server.mapper.my;

import com.accp.server.dto.ResourceDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Mr.黄
 */
public interface MyUserMapper {
    /**
     * 一个用户可以属于多个角色，配置的资源可能重复，所以用distinct去重
     * @param userId
     * @return
     */
    List<ResourceDto> findResources(@Param("userId") String userId);

}
