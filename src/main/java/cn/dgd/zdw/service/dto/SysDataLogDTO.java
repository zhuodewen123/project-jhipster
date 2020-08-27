package cn.dgd.zdw.service.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDate;
import java.io.Serializable;

/**
 * A DTO for the {@link cn.dgd.zdw.domain.SysDataLog} entity.
 */
@ApiModel(description = "审计日志表")
public class SysDataLogDTO implements Serializable {
    
    private Long id;

    /**
     * 表名
     */
    @ApiModelProperty(value = "表名")
    private String tableEngName;

    /**
     * 数据ID
     */
    @ApiModelProperty(value = "数据ID")
    private String dataId;

    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private String version;

    /**
     * 数据内容
     */
    @ApiModelProperty(value = "数据内容")
    private String dataContent;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String username;

    /**
     * 创建人姓名
     */
    @ApiModelProperty(value = "创建人姓名")
    private String realname;

    /**
     * 描述信息
     */
    @ApiModelProperty(value = "描述信息")
    private String desc;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDate createTime;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableEngName() {
        return tableEngName;
    }

    public void setTableEngName(String tableEngName) {
        this.tableEngName = tableEngName;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysDataLogDTO)) {
            return false;
        }

        return id != null && id.equals(((SysDataLogDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysDataLogDTO{" +
            "id=" + getId() +
            ", tableEngName='" + getTableEngName() + "'" +
            ", dataId='" + getDataId() + "'" +
            ", version='" + getVersion() + "'" +
            ", dataContent='" + getDataContent() + "'" +
            ", username='" + getUsername() + "'" +
            ", realname='" + getRealname() + "'" +
            ", desc='" + getDesc() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            "}";
    }
}
