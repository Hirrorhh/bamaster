package com.bamaster.shiro.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-27 14:38
 *
 * @desc 用户实体类
 */

@ApiModel(description = "用户实体类")
public class UUser implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    private Integer id;
    /**昵称*/
    @ApiModelProperty("用户昵称")
    private String nickname;
    /**邮箱 | 登录帐号*/
    @ApiModelProperty("用户登录名")
    private String username;
    /**密码*/
    @ApiModelProperty("用户密码")
    private transient String pswd;
    /**创建时间*/
    @ApiModelProperty("用户创建时间(系统自动填充)")
    private Date createTime;
    @ApiModelProperty("用户openid(微信接入预留)")
    private String openid;
    /**1:有效，0:禁止登录*/
    @ApiModelProperty("用户登录状态(系统初始化分配有效)")
    private Integer status;

    public UUser() {
    }

    public UUser(UUser user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.username = user.getUsername();
        this.pswd = user.getPswd();
        this.createTime = user.getCreateTime();
        this.openid = user.getOpenId();
        this.status = user.getStatus();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getOpenId() {
        return openid;
    }

    public void setOpenId(String openId) {
        this.openid = openId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String toString(){
        return JSONObject.fromObject(this).toString();
    }
}
