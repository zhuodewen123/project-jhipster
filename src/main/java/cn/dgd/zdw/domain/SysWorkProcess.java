package cn.dgd.zdw.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 办事进度表
 */
@Entity
@Table(name = "sys_work_process")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SysWorkProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 事项名称
     */
    @Column(name = "title")
    private String title;

    /**
     * 接收日期
     */
    @Column(name = "receive_date")
    private LocalDate receiveDate;

    /**
     * 接收单位
     */
    @Column(name = "receive_org")
    private String receiveOrg;

    /**
     * 当前进度
     */
    @Column(name = "current_process")
    private String currentProcess;

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

    public SysWorkProcess title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public SysWorkProcess receiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
        return this;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getReceiveOrg() {
        return receiveOrg;
    }

    public SysWorkProcess receiveOrg(String receiveOrg) {
        this.receiveOrg = receiveOrg;
        return this;
    }

    public void setReceiveOrg(String receiveOrg) {
        this.receiveOrg = receiveOrg;
    }

    public String getCurrentProcess() {
        return currentProcess;
    }

    public SysWorkProcess currentProcess(String currentProcess) {
        this.currentProcess = currentProcess;
        return this;
    }

    public void setCurrentProcess(String currentProcess) {
        this.currentProcess = currentProcess;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SysWorkProcess)) {
            return false;
        }
        return id != null && id.equals(((SysWorkProcess) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SysWorkProcess{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", receiveDate='" + getReceiveDate() + "'" +
            ", receiveOrg='" + getReceiveOrg() + "'" +
            ", currentProcess='" + getCurrentProcess() + "'" +
            "}";
    }
}
