package com.bamaster.shiro.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;

import java.io.Serializable;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-27 14:38
 *
 * @desc 用户角色关联表实体类
 */

@ApiModel(description = "用户角色关联表实体类")
public class UUserRole  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**{@link UUser.id}*/
    @ApiModelProperty("用户ID")
    private Integer uid;
    /**{@link URole.id}*/
    @ApiModelProperty("角色ID")
    private Integer rid;

    public UUserRole(Integer uid, Integer rid) {
        this.uid = uid;
        this.rid = rid;
    }

    public UUserRole() {
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }
    public String toString(){
        return JSONObject.fromObject(this).toString();
    }

}
