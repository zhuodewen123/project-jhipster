package cn.dgd.zdw.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link cn.dgd.zdw.domain.SysNotice} entity.
 */
@ApiModel(description = "通知公告表")
public class SysNoticeDTO implements Serializable {
    
    private Long id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 通知类型
     */
    @ApiModelProperty(value = "通知类型")
    private String sender;

    /**
     * 发件日期
     */
    @ApiModelProperty(value = "发件日期")
    private String sendDate;

    /**
     * 接收人
     */
    @ApiModelProperty(value = "接收人")
    private LocalDate receiver;

    /**
     * 审核机关
     */
    @ApiModelProperty(value = "审核机关")
    private LocalDate auditOrg;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private String delFlag;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public LocalDate getReceiver() {
        return receiver;
    }

    public void setReceiver(LocalDate receiver) {
        this.receiver = receiver;
    }

    public LocalDate getAuditOrg() {
        return auditOrg;
    }

    public void setAuditOrg(LocalDate auditOrg) {
        this.auditOrg = auditOrg;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysNoticeDTO)) {
            return false;
        }

        return id != null && id.equals(((SysNoticeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysNoticeDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", sender='" + getSender() + "'" +
            ", sendDate='" + getSendDate() + "'" +
            ", receiver='" + getReceiver() + "'" +
            ", auditOrg='" + getAuditOrg() + "'" +
            ", delFlag='" + getDelFlag() + "'" +
            "}";
    }
}
