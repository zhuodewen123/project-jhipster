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
 * Criteria class for the {@link cn.dgd.zdw.domain.SysWorkProcessdetail} entity. This class is used
 * in {@link cn.dgd.zdw.web.rest.SysWorkProcessdetailResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sys-work-processdetails?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SysWorkProcessdetailCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fkWorkProcessId;

    private StringFilter todoOrg;

    private StringFilter result;

    private StringFilter approvalComments;

    private LocalDateFilter receiveTime;

    private LocalDateFilter approvalTime;

    public SysWorkProcessdetailCriteria() {
    }

    public SysWorkProcessdetailCriteria(SysWorkProcessdetailCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fkWorkProcessId = other.fkWorkProcessId == null ? null : other.fkWorkProcessId.copy();
        this.todoOrg = other.todoOrg == null ? null : other.todoOrg.copy();
        this.result = other.result == null ? null : other.result.copy();
        this.approvalComments = other.approvalComments == null ? null : other.approvalComments.copy();
        this.receiveTime = other.receiveTime == null ? null : other.receiveTime.copy();
        this.approvalTime = other.approvalTime == null ? null : other.approvalTime.copy();
    }

    @Override
    public SysWorkProcessdetailCriteria copy() {
        return new SysWorkProcessdetailCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFkWorkProcessId() {
        return fkWorkProcessId;
    }

    public void setFkWorkProcessId(StringFilter fkWorkProcessId) {
        this.fkWorkProcessId = fkWorkProcessId;
    }

    public StringFilter getTodoOrg() {
        return todoOrg;
    }

    public void setTodoOrg(StringFilter todoOrg) {
        this.todoOrg = todoOrg;
    }

    public StringFilter getResult() {
        return result;
    }

    public void setResult(StringFilter result) {
        this.result = result;
    }

    public StringFilter getApprovalComments() {
        return approvalComments;
    }

    public void setApprovalComments(StringFilter approvalComments) {
        this.approvalComments = approvalComments;
    }

    public LocalDateFilter getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateFilter receiveTime) {
        this.receiveTime = receiveTime;
    }

    public LocalDateFilter getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(LocalDateFilter approvalTime) {
        this.approvalTime = approvalTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SysWorkProcessdetailCriteria that = (SysWorkProcessdetailCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fkWorkProcessId, that.fkWorkProcessId) &&
            Objects.equals(todoOrg, that.todoOrg) &&
            Objects.equals(result, that.result) &&
            Objects.equals(approvalComments, that.approvalComments) &&
            Objects.equals(receiveTime, that.receiveTime) &&
            Objects.equals(approvalTime, that.approvalTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fkWorkProcessId,
        todoOrg,
        result,
        approvalComments,
        receiveTime,
        approvalTime
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysWorkProcessdetailCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fkWorkProcessId != null ? "fkWorkProcessId=" + fkWorkProcessId + ", " : "") +
                (todoOrg != null ? "todoOrg=" + todoOrg + ", " : "") +
                (result != null ? "result=" + result + ", " : "") +
                (approvalComments != null ? "approvalComments=" + approvalComments + ", " : "") +
                (receiveTime != null ? "receiveTime=" + receiveTime + ", " : "") +
                (approvalTime != null ? "approvalTime=" + approvalTime + ", " : "") +
            "}";
    }

}
