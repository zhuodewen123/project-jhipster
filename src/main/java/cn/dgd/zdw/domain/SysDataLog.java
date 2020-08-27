package cn.dgd.zdw.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 审计日志表
 */
@Entity
@Table(name = "sys_data_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysDataLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 表名
     */
    @Column(name = "table_eng_name")
    private String tableEngName;

    /**
     * 数据ID
     */
    @Column(name = "data_id")
    private String dataId;

    /**
     * 版本号
     */
    @Column(name = "version")
    private String version;

    /**
     * 数据内容
     */
    @Column(name = "data_content")
    private String dataContent;

    /**
     * 创建人
     */
    @Column(name = "username")
    private String username;

    /**
     * 创建人姓名
     */
    @Column(name = "realname")
    private String realname;

    /**
     * 描述信息
     */
    @Column(name = "jhi_desc")
    private String desc;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private LocalDate createTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableEngName() {
        return tableEngName;
    }

    public SysDataLog tableEngName(String tableEngName) {
        this.tableEngName = tableEngName;
        return this;
    }

    public void setTableEngName(String tableEngName) {
        this.tableEngName = tableEngName;
    }

    public String getDataId() {
        return dataId;
    }

    public SysDataLog dataId(String dataId) {
        this.dataId = dataId;
        return this;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getVersion() {
        return version;
    }

    public SysDataLog version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDataContent() {
        return dataContent;
    }

    public SysDataLog dataContent(String dataContent) {
        this.dataContent = dataContent;
        return this;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public String getUsername() {
        return username;
    }

    public SysDataLog username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public SysDataLog realname(String realname) {
        this.realname = realname;
        return this;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getDesc() {
        return desc;
    }

    public SysDataLog desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public SysDataLog createTime(LocalDate createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysDataLog)) {
            return false;
        }
        return id != null && id.equals(((SysDataLog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysDataLog{" +
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
