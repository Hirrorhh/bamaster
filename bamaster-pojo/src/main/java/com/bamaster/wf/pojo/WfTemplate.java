package com.bamaster.wf.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-15
 *
 * @create 2017-12-15 14:38
 *
 * @desc 模板的实体类(公司提供的服务)
 */

@ApiModel(description = "模板")
@Table(name="wf_template")
public class WfTemplate implements Serializable {
    /**
     * 模版id
     */
    @Id
    @ApiModelProperty(hidden = true)
    @Column(name = "templateid")
    private Integer templateid;

    /**
     * 模版描述
     */
    @ApiModelProperty("模板描述")
    @Column(name = "description")
    private String description;

    /**
     * 模版操作步骤值
     */
    @ApiModelProperty("模版操作步骤值")
    @Column(name = "complete_size")
    private Integer completeSize;

    /**
     * 模板状态（1有效、2无效）
     */
    @ApiModelProperty("模版状态")
    @Column(name = "deleted")
    private Integer deleted;

    private static final long serialVersionUID = 1L;

    public Integer getTemplateid() {
        return templateid;
    }

    public void setTemplateid(Integer templateid) {
        this.templateid = templateid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getCompleteSize() {
        return completeSize;
    }

    public void setCompleteSize(Integer completeSize) {
        this.completeSize = completeSize;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", templateid=").append(templateid);
        sb.append(", description=").append(description);
        sb.append(", completeSize=").append(completeSize);
        sb.append(", deleted=").append(deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}