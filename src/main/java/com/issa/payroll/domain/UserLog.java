package com.issa.payroll.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserLog.
 */
@Entity
@Table(name = "sys_user_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "op")
    private String op;

    @Column(name = "util")
    private String util;

    @Column(name = "date_op")
    private ZonedDateTime dateOp;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserLog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOp() {
        return this.op;
    }

    public UserLog op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getUtil() {
        return this.util;
    }

    public UserLog util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public ZonedDateTime getDateOp() {
        return this.dateOp;
    }

    public UserLog dateOp(ZonedDateTime dateOp) {
        this.setDateOp(dateOp);
        return this;
    }

    public void setDateOp(ZonedDateTime dateOp) {
        this.dateOp = dateOp;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserLog)) {
            return false;
        }
        return id != null && id.equals(((UserLog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserLog{" +
            "id=" + getId() +
            ", op='" + getOp() + "'" +
            ", util='" + getUtil() + "'" +
            ", dateOp='" + getDateOp() + "'" +
            "}";
    }
}
