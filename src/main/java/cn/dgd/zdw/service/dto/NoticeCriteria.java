package cn.dgd.zdw.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link cn.dgd.zdw.domain.Notice} entity. This class is used
 * in {@link cn.dgd.zdw.web.rest.NoticeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /notices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NoticeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter content;

    private StringFilter sender;

    private StringFilter sendDate;

    private LocalDateFilter receiver;

    private LocalDateFilter auditOrg;

    private StringFilter delFlag;

    public NoticeCriteria() {
    }

    public NoticeCriteria(NoticeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.content = other.content == null ? null : other.content.copy();
        this.sender = other.sender == null ? null : other.sender.copy();
        this.sendDate = other.sendDate == null ? null : other.sendDate.copy();
        this.receiver = other.receiver == null ? null : other.receiver.copy();
        this.auditOrg = other.auditOrg == null ? null : other.auditOrg.copy();
        this.delFlag = other.delFlag == null ? null : other.delFlag.copy();
    }

    @Override
    public NoticeCriteria copy() {
        return new NoticeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getContent() {
        return content;
    }

    public void setContent(StringFilter content) {
        this.content = content;
    }

    public StringFilter getSender() {
        return sender;
    }

    public void setSender(StringFilter sender) {
        this.sender = sender;
    }

    public StringFilter getSendDate() {
        return sendDate;
    }

    public void setSendDate(StringFilter sendDate) {
        this.sendDate = sendDate;
    }

    public LocalDateFilter getReceiver() {
        return receiver;
    }

    public void setReceiver(LocalDateFilter receiver) {
        this.receiver = receiver;
    }

    public LocalDateFilter getAuditOrg() {
        return auditOrg;
    }

    public void setAuditOrg(LocalDateFilter auditOrg) {
        this.auditOrg = auditOrg;
    }

    public StringFilter getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(StringFilter delFlag) {
        this.delFlag = delFlag;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NoticeCriteria that = (NoticeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(content, that.content) &&
            Objects.equals(sender, that.sender) &&
            Objects.equals(sendDate, that.sendDate) &&
            Objects.equals(receiver, that.receiver) &&
            Objects.equals(auditOrg, that.auditOrg) &&
            Objects.equals(delFlag, that.delFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        content,
        sender,
        sendDate,
        receiver,
        auditOrg,
        delFlag
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoticeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (content != null ? "content=" + content + ", " : "") +
                (sender != null ? "sender=" + sender + ", " : "") +
                (sendDate != null ? "sendDate=" + sendDate + ", " : "") +
                (receiver != null ? "receiver=" + receiver + ", " : "") +
                (auditOrg != null ? "auditOrg=" + auditOrg + ", " : "") +
                (delFlag != null ? "delFlag=" + delFlag + ", " : "") +
            "}";
    }

}
