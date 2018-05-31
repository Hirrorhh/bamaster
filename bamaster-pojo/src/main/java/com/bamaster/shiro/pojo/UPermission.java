package com.bamaster.shiro.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import net.sf.json.JSONObject;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-27 14:38
 *
 * @desc 权限的实体类
 */

@ApiModel(description = "用户")
public class UPermission {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(hidden = true)
    private Integer id;
    /** 操作的url */
    @ApiModelProperty("操作权限的url")
    private String url;
    /** 操作的名称 */
    @ApiModelProperty("操作权限的名称")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return JSONObject.fromObject(this).toString();
    }
}

