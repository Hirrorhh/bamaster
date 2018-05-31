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
 * @desc 角色权限关联表实体类
 */

@ApiModel(description = "角色权限关联表实体类")
public class URolePermission  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * {@link URole.id}
     *
     **/
    @ApiModelProperty("角色ID")
    private Integer rid;
    /**
     * {@link UPermission.id}
     *
     */
    @ApiModelProperty("权限ID")
    private Integer pid;

    public URolePermission() {
    }

    public URolePermission(Integer rid, Integer pid) {
        this.rid = rid;
        this.pid = pid;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
    public String toString(){
        return JSONObject.fromObject(this).toString();
    }

}
