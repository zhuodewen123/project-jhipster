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
@Table(name = "t_sys_work_process")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TSysWorkProcess implements Serializable {

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

    public TSysWorkProcess title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReceiveDate() {
        return receiveDate;
    }

    public TSysWorkProcess receiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
        return this;
    }

    public void setReceiveDate(LocalDate receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getReceiveOrg() {
        return receiveOrg;
    }

    public TSysWorkProcess receiveOrg(String receiveOrg) {
        this.receiveOrg = receiveOrg;
        return this;
    }

    public void setReceiveOrg(String receiveOrg) {
        this.receiveOrg = receiveOrg;
    }

    public String getCurrentProcess() {
        return currentProcess;
    }

    public TSysWorkProcess currentProcess(String currentProcess) {
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
        if (!(o instanceof TSysWorkProcess)) {
            return false;
        }
        return id != null && id.equals(((TSysWorkProcess) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TSysWorkProcess{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", receiveDate='" + getReceiveDate() + "'" +
            ", receiveOrg='" + getReceiveOrg() + "'" +
            ", currentProcess='" + getCurrentProcess() + "'" +
            "}";
    }
}
