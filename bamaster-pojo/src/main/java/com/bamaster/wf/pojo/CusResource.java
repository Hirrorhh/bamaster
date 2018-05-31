package com.bamaster.wf.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-19
 *
 * @create 2017-12-19 16:38
 *
 * @desc 上传资料的实体类
 */

@ApiModel(description = "资料保存实例")
@Table(name="cus_resource")
public class CusResource implements Serializable {
    /**
     * 资料id
     */
    @Column(name = "id")
    @Id
    @ApiModelProperty(hidden = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 生成uuid名称
     */
    @ApiModelProperty("生成uuid名称")
    @Column(name = "uuidname")
    private String uuidname;

    /**
     * 资料真实名称
     */
    @ApiModelProperty("资料真实名称")
    @Column(name = "realname")
    private String realname;

    /**
     * 资料保存路径
     */
    @ApiModelProperty("资料保存路径")
    @Column(name = "savepath")
    private String savepath;

    /**
     * 节点id
     */
    @ApiModelProperty("节点id")
    @Column(name = "nodeid")
    private Integer nodeid;

    /**
     * 上传资料用户ID
     */
    @ApiModelProperty("上传资料用户ID")
    @Column(name = "userid")
    private Integer userid;

    /**
     * 上传时间
     */
    @ApiModelProperty("上传时间")
    @Column(name = "uploadtime")
    private Date uploadtime;

    /**
     * 资料描述
     */
    @ApiModelProperty("资料描述")
    @Column(name = "description")
    private String description;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuidname() {
        return uuidname;
    }

    public void setUuidname(String uuidname) {
        this.uuidname = uuidname == null ? null : uuidname.trim();
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    public String getSavepath() {
        return savepath;
    }

    public void setSavepath(String savepath) {
        this.savepath = savepath == null ? null : savepath.trim();
    }

    public Integer getNodeid() {
        return nodeid;
    }

    public void setNodeid(Integer nodeid) {
        this.nodeid = nodeid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Date getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(Date uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uuidname=").append(uuidname);
        sb.append(", realname=").append(realname);
        sb.append(", savepath=").append(savepath);
        sb.append(", nodeid=").append(nodeid);
        sb.append(", userid=").append(userid);
        sb.append(", uploadtime=").append(uploadtime);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}