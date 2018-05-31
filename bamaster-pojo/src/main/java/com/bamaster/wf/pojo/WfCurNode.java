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
 * @desc 当前节点的实体类
 */

@ApiModel(description = "当前节点")
@Table(name="wf_cur_node")
public class WfCurNode implements Serializable {

    /**
     * 节点ID
     */
    @Id
    @ApiModelProperty(hidden = true)
    @Column(name = "nodeid")
    private Integer nodeid;

    /**
     * 操作人员id
     */
    @ApiModelProperty("当前节点的操作人员id")
    @Column(name = "opreateid")
    private Integer opreateid;

    /**
     * 审核人员id
     */
    @ApiModelProperty("当前节点的审核人员id")
    @Column(name = "examineid")
    private Integer examineid;

    /**
     * 下一节点id
     */
    @ApiModelProperty("下一节点的id,结束为999")
    @Column(name = "nextnodeid")
    private Integer nextnodeid;

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
     * 节点状态（1未分配、2进行中、3已完成）
     */
    @ApiModelProperty("当前节点状态")
    @Column(name = "status")
    private Integer status;

    private static final long serialVersionUID = 1L;

    public Integer getNodeid() {
        return nodeid;
    }

    public void setNodeid(Integer nodeid) {
        this.nodeid = nodeid;
    }

    public Integer getOpreateid() {
        return opreateid;
    }

    public void setOpreateid(Integer opreateid) {
        this.opreateid = opreateid;
    }

    public Integer getExamineid() {
        return examineid;
    }

    public void setExamineid(Integer examineid) {
        this.examineid = examineid;
    }

    public Integer getNextnodeid() {
        return nextnodeid;
    }

    public void setNextnodeid(Integer nextnodeid) {
        this.nextnodeid = nextnodeid;
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
        sb.append(", nodeid=").append(nodeid);
        sb.append(", opreateid=").append(opreateid);
        sb.append(", examineid=").append(examineid);
        sb.append(", nextnodeid=").append(nextnodeid);
        sb.append(", createtime=").append(createtime);
        sb.append(", expiretime=").append(expiretime);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}