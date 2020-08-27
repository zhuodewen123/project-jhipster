package cn.dgd.zdw.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 办事明细表
 */
@Entity
@Table(name = "work_processdetail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WorkProcessdetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 办事进度ID
     */
    @Column(name = "fk_work_process_id")
    private String fkWorkProcessId;

    /**
     * 处理单位
     */
    @Column(name = "todo_org")
    private String todoOrg;

    /**
     * 处理结果
     */
    @Column(name = "result")
    private String result;

    /**
     * 审批意见
     */
    @Column(name = "approval_comments")
    private String approvalComments;

    /**
     * 接收时间
     */
    @Column(name = "receive_time")
    private LocalDate receiveTime;

    /**
     * 审核时间
     */
    @Column(name = "approval_time")
    private LocalDate approvalTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFkWorkProcessId() {
        return fkWorkProcessId;
    }

    public WorkProcessdetail fkWorkProcessId(String fkWorkProcessId) {
        this.fkWorkProcessId = fkWorkProcessId;
        return this;
    }

    public void setFkWorkProcessId(String fkWorkProcessId) {
        this.fkWorkProcessId = fkWorkProcessId;
    }

    public String getTodoOrg() {
        return todoOrg;
    }

    public WorkProcessdetail todoOrg(String todoOrg) {
        this.todoOrg = todoOrg;
        return this;
    }

    public void setTodoOrg(String todoOrg) {
        this.todoOrg = todoOrg;
    }

    public String getResult() {
        return result;
    }

    public WorkProcessdetail result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getApprovalComments() {
        return approvalComments;
    }

    public WorkProcessdetail approvalComments(String approvalComments) {
        this.approvalComments = approvalComments;
        return this;
    }

    public void setApprovalComments(String approvalComments) {
        this.approvalComments = approvalComments;
    }

    public LocalDate getReceiveTime() {
        return receiveTime;
    }

    public WorkProcessdetail receiveTime(LocalDate receiveTime) {
        this.receiveTime = receiveTime;
        return this;
    }

    public void setReceiveTime(LocalDate receiveTime) {
        this.receiveTime = receiveTime;
    }

    public LocalDate getApprovalTime() {
        return approvalTime;
    }

    public WorkProcessdetail approvalTime(LocalDate approvalTime) {
        this.approvalTime = approvalTime;
        return this;
    }

    public void setApprovalTime(LocalDate approvalTime) {
        this.approvalTime = approvalTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkProcessdetail)) {
            return false;
        }
        return id != null && id.equals(((WorkProcessdetail) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkProcessdetail{" +
            "id=" + getId() +
            ", fkWorkProcessId='" + getFkWorkProcessId() + "'" +
            ", todoOrg='" + getTodoOrg() + "'" +
            ", result='" + getResult() + "'" +
            ", approvalComments='" + getApprovalComments() + "'" +
            ", receiveTime='" + getReceiveTime() + "'" +
            ", approvalTime='" + getApprovalTime() + "'" +
            "}";
    }
}
