package com.bamaster.wf.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-15
 *
 * @create 2017-12-15 14:38
 *
 * @desc 当前实例的实体类
 */

@ApiModel(description = "当前实例")
@Table(name="wf_cur_instance")
public class WfCurInstance implements Serializable {
    /**
     * 新创建的某个模版下的实例ID
     */
    @ApiModelProperty(hidden = true)
    @Id
    @Column(name = "instanceid")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer instanceid;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @Column(name = "createtime")
    private Date createtime;

    /**
     * 过期时间
     */
    @ApiModelProperty("过期时间")
    @Column(name = "expiretime")
    private Date expiretime;

    /**
     * 创建人id
     */
    @ApiModelProperty("创建人员id")
    @Column(name = "createuserid")
    private Integer createuserid;

    /**
     * 模板ID
     */
    @ApiModelProperty("模板id")
    @Column(name = "templateid")
    private Integer templateid;

    /**
     * 实例状态（1待确定、2确定进行中、3已完成）
     */
    @ApiModelProperty("当前实例状态")
    @Column(name = "status")
    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getInstanceid() {
        return instanceid;
    }

    public void setInstanceid(Integer instanceid) {
        this.instanceid = instanceid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(Date expiretime) {
        this.expiretime = expiretime;
    }

    public Integer getCreateuserid() {
        return createuserid;
    }

    public void setCreateuserid(Integer createuserid) {
        this.createuserid = createuserid;
    }

    public Integer getTemplateid() {
        return templateid;
    }

    public void setTemplateid(Integer templateid) {
        this.templateid = templateid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", instanceid=").append(instanceid);
        sb.append(", createtime=").append(createtime);
        sb.append(", expiretime=").append(expiretime);
        sb.append(", createuserid=").append(createuserid);
        sb.append(", templateid=").append(templateid);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}