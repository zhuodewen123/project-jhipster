package cn.dgd.zdw.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 通知公告表
 */
@Entity
@Table(name = "notice")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 通知类型
     */
    @Column(name = "sender")
    private String sender;

    /**
     * 发件日期
     */
    @Column(name = "send_date")
    private String sendDate;

    /**
     * 接收人
     */
    @Column(name = "receiver")
    private LocalDate receiver;

    /**
     * 审核机关
     */
    @Column(name = "audit_org")
    private LocalDate auditOrg;

    /**
     * 删除标识
     */
    @Column(name = "del_flag")
    private String delFlag;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Notice title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public Notice content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public Notice sender(String sender) {
        this.sender = sender;
        return this;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSendDate() {
        return sendDate;
    }

    public Notice sendDate(String sendDate) {
        this.sendDate = sendDate;
        return this;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public LocalDate getReceiver() {
        return receiver;
    }

    public Notice receiver(LocalDate receiver) {
        this.receiver = receiver;
        return this;
    }

    public void setReceiver(LocalDate receiver) {
        this.receiver = receiver;
    }

    public LocalDate getAuditOrg() {
        return auditOrg;
    }

    public Notice auditOrg(LocalDate auditOrg) {
        this.auditOrg = auditOrg;
        return this;
    }

    public void setAuditOrg(LocalDate auditOrg) {
        this.auditOrg = auditOrg;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public Notice delFlag(String delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notice)) {
            return false;
        }
        return id != null && id.equals(((Notice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notice{" +
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
