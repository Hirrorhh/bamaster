package com.bamaster.wf.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-15
 *
 * @create 2017-12-15 14:38
 *
 * @desc 历史实例的实体类
 */

@ApiModel(description = "历史实例")
@Table(name="wf_hist_instance")
public class WfHistInstance implements Serializable {
    /**
     * 实例ID
     */
    @Id
    @ApiModelProperty(hidden = true)
    @Column(name = "instanceid")
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
    @ApiModelProperty("实例状态 默认是3表示 完成")
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