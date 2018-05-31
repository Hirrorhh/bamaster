package com.bamaster.shiro.pojo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-27 14:38
 *
 * @desc 角色实体类
 */

@ApiModel(description = "角色")
public class URole  implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(hidden = true)
    private Integer id;
    /**角色名称*/
    @ApiModelProperty("角色名称")
    private String name;
    /**角色类型*/
    @ApiModelProperty("角色类型")
    private String type;
    //***做 role --> permission 一对多处理
    @ApiModelProperty(hidden = true)
    private List<UPermission> permissions = new LinkedList<UPermission>();



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<UPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<UPermission> permissions) {
        this.permissions = permissions;
    }
    public String toString(){
        return JSONObject.fromObject(this).toString();
    }
}
