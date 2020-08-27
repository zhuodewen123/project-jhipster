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
 * Criteria class for the {@link cn.dgd.zdw.domain.SysWorkProcess} entity. This class is used
 * in {@link cn.dgd.zdw.web.rest.SysWorkProcessResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /sys-work-processes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class SysWorkProcessCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private LocalDateFilter receiveDate;

    private StringFilter receiveOrg;

    private StringFilter currentProcess;

    public SysWorkProcessCriteria() {
    }

    public SysWorkProcessCriteria(SysWorkProcessCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.receiveDate = other.receiveDate == null ? null : other.receiveDate.copy();
        this.receiveOrg = other.receiveOrg == null ? null : other.receiveOrg.copy();
        this.currentProcess = other.currentProcess == null ? null : other.currentProcess.copy();
    }

    @Override
    public SysWorkProcessCriteria copy() {
        return new SysWorkProcessCriteria(this);
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

    public LocalDateFilter getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDateFilter receiveDate) {
        this.receiveDate = receiveDate;
    }

    public StringFilter getReceiveOrg() {
        return receiveOrg;
    }

    public void setReceiveOrg(StringFilter receiveOrg) {
        this.receiveOrg = receiveOrg;
    }

    public StringFilter getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(StringFilter currentProcess) {
        this.currentProcess = currentProcess;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SysWorkProcessCriteria that = (SysWorkProcessCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(receiveDate, that.receiveDate) &&
            Objects.equals(receiveOrg, that.receiveOrg) &&
            Objects.equals(currentProcess, that.currentProcess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        receiveDate,
        receiveOrg,
        currentProcess
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysWorkProcessCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (receiveDate != null ? "receiveDate=" + receiveDate + ", " : "") +
                (receiveOrg != null ? "receiveOrg=" + receiveOrg + ", " : "") +
                (currentProcess != null ? "currentProcess=" + currentProcess + ", " : "") +
            "}";
    }

}
