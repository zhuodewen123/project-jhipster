package cn.dgd.zdw.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link cn.dgd.zdw.domain.WorkProcess} entity.
 */
@ApiModel(description = "办事进度表")
public class WorkProcessDTO implements Serializable {
    
    private Long id;

    /**
     * 事项名称
     */
    @ApiModelProperty(value = "事项名称")
    private String title;

    /**
     * 接收日期
     */
    @ApiModelProperty(value = "接收日期")
    private LocalDate receiveDate;

    /**
     * 接收单位
     */
    @ApiModelProperty(value = "接收单位")
    private String receiveOrg;

    /**
     * 当前进度
     */
    @ApiModelProperty(value = "当前进度")
    private String currentProcess;

    
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

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getReceiveOrg() {
        return receiveOrg;
    }

    public void setReceiveOrg(String receiveOrg) {
        this.receiveOrg = receiveOrg;
    }

    public String getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(String currentProcess) {
        this.currentProcess = currentProcess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkProcessDTO)) {
            return false;
        }

        return id != null && id.equals(((WorkProcessDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkProcessDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", receiveDate='" + getReceiveDate() + "'" +
            ", receiveOrg='" + getReceiveOrg() + "'" +
            ", currentProcess='" + getCurrentProcess() + "'" +
            "}";
    }
}
