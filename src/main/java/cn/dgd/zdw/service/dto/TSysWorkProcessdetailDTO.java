package cn.dgd.zdw.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link cn.dgd.zdw.domain.TSysWorkProcessdetail} entity.
 */
@ApiModel(description = "办事明细表")
public class TSysWorkProcessdetailDTO implements Serializable {
    
    private Long id;

    /**
     * 办事进度ID
     */
    @ApiModelProperty(value = "办事进度ID")
    private String fkWorkProcessId;

    /**
     * 处理单位
     */
    @ApiModelProperty(value = "处理单位")
    private String todoOrg;

    /**
     * 处理结果
     */
    @ApiModelProperty(value = "处理结果")
    private String result;

    /**
     * 审批意见
     */
    @ApiModelProperty(value = "审批意见")
    private String approvalComments;

    /**
     * 接收时间
     */
    @ApiModelProperty(value = "接收时间")
    private LocalDate receiveTime;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    private LocalDate approvalTime;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFkWorkProcessId() {
        return fkWorkProcessId;
    }

    public void setFkWorkProcessId(String fkWorkProcessId) {
        this.fkWorkProcessId = fkWorkProcessId;
    }

    public String getTodoOrg() {
        return todoOrg;
    }

    public void setTodoOrg(String todoOrg) {
        this.todoOrg = todoOrg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getApprovalComments() {
        return approvalComments;
    }

    public void setApprovalComments(String approvalComments) {
        this.approvalComments = approvalComments;
    }

    public LocalDate getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDate receiveTime) {
        this.receiveTime = receiveTime;
    }

    public LocalDate getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(LocalDate approvalTime) {
        this.approvalTime = approvalTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TSysWorkProcessdetailDTO)) {
            return false;
        }

        return id != null && id.equals(((TSysWorkProcessdetailDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TSysWorkProcessdetailDTO{" +
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
