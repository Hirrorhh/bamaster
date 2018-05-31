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
 * @desc 当前消息的实体类
 */

@ApiModel(description = "当前消息")
@Table(name="wf_cur_message")
public class WfCurMessage implements Serializable {
    /**
     * 消息ID
     */
    @Id
    @ApiModelProperty(hidden = true)
    @Column(name = "msgid")
    private Integer msgid;

    /**
     * 通知用户id
     */
    @ApiModelProperty("通知用户id")
    @Column(name = "userid")
    private Integer userid;

    /**
     * 节点id
     */
    @ApiModelProperty("当前消息的节点id")
    @Column(name = "nodeid")
    private Integer nodeid;

    /**
     * 消息内容
     */
    @ApiModelProperty("消息内容")
    @Column(name = "msg")
    private String msg;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @Column(name = "createtime")
    private Date createtime;

    /**
     * 阅读时间
     */
    @ApiModelProperty("阅读时间")
    @Column(name = "readtime")
    private Date readtime;

    private static final long serialVersionUID = 1L;

    public Integer getMsgid() {
        return msgid;
    }

    public void setMsgid(Integer msgid) {
        this.msgid = msgid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getNodeid() {
        return nodeid;
    }

    public void setNodeid(Integer nodeid) {
        this.nodeid = nodeid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getReadtime() {
        return readtime;
    }

    public void setReadtime(Date readtime) {
        this.readtime = readtime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", msgid=").append(msgid);
        sb.append(", userid=").append(userid);
        sb.append(", nodeid=").append(nodeid);
        sb.append(", msg=").append(msg);
        sb.append(", createtime=").append(createtime);
        sb.append(", readtime=").append(readtime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}