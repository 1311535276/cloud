package com.accp.server.dto;

import io.swagger.annotations.ApiModelProperty;


/**
 * @author Mr.黄
 */
public class UserDto {

    /**
     * id
     */
  @ApiModelProperty("id")
    private String id;

    /**
     * 登陆名
     */
  @ApiModelProperty("登陆名")
    private String loginName;

    /**
     * 昵称
     */
  @ApiModelProperty("昵称")
    private String name;

    /**
     * 密码
     */
  @ApiModelProperty("密码")
    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", loginName=").append(loginName);
        sb.append(", name=").append(name);
        sb.append(", password=").append(password);
        sb.append("]");
        return sb.toString();
    }

}
