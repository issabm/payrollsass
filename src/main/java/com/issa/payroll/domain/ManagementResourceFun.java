package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ManagementResourceFun.
 */
@Entity
@Table(name = "rh_manage_rule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ManagementResourceFun implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "lib_en")
    private String libEn;

    @Column(name = "profile")
    private String profile;

    @Column(name = "enable_add")
    private Boolean enableAdd;

    @Column(name = "enable_cnst")
    private Boolean enableCnst;

    @Column(name = "enable_del")
    private Boolean enableDel;

    @Column(name = "enable_ed")
    private Boolean enableEd;

    @Column(name = "dateop")
    private ZonedDateTime dateop;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "op")
    private String op;

    @Column(name = "util")
    private String util;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "situation", "employe", "entreprise", "groupe", "affiliation" }, allowSetters = true)
    private ManagementResource ressourceManage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ManagementResourceFun id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibEn() {
        return this.libEn;
    }

    public ManagementResourceFun libEn(String libEn) {
        this.setLibEn(libEn);
        return this;
    }

    public void setLibEn(String libEn) {
        this.libEn = libEn;
    }

    public String getProfile() {
        return this.profile;
    }

    public ManagementResourceFun profile(String profile) {
        this.setProfile(profile);
        return this;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Boolean getEnableAdd() {
        return this.enableAdd;
    }

    public ManagementResourceFun enableAdd(Boolean enableAdd) {
        this.setEnableAdd(enableAdd);
        return this;
    }

    public void setEnableAdd(Boolean enableAdd) {
        this.enableAdd = enableAdd;
    }

    public Boolean getEnableCnst() {
        return this.enableCnst;
    }

    public ManagementResourceFun enableCnst(Boolean enableCnst) {
        this.setEnableCnst(enableCnst);
        return this;
    }

    public void setEnableCnst(Boolean enableCnst) {
        this.enableCnst = enableCnst;
    }

    public Boolean getEnableDel() {
        return this.enableDel;
    }

    public ManagementResourceFun enableDel(Boolean enableDel) {
        this.setEnableDel(enableDel);
        return this;
    }

    public void setEnableDel(Boolean enableDel) {
        this.enableDel = enableDel;
    }

    public Boolean getEnableEd() {
        return this.enableEd;
    }

    public ManagementResourceFun enableEd(Boolean enableEd) {
        this.setEnableEd(enableEd);
        return this;
    }

    public void setEnableEd(Boolean enableEd) {
        this.enableEd = enableEd;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public ManagementResourceFun dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public ManagementResourceFun modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ManagementResourceFun createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getOp() {
        return this.op;
    }

    public ManagementResourceFun op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getUtil() {
        return this.util;
    }

    public ManagementResourceFun util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public ManagementResourceFun isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public ManagementResourceFun createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public ManagementResourceFun modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public ManagementResource getRessourceManage() {
        return this.ressourceManage;
    }

    public void setRessourceManage(ManagementResource managementResource) {
        this.ressourceManage = managementResource;
    }

    public ManagementResourceFun ressourceManage(ManagementResource managementResource) {
        this.setRessourceManage(managementResource);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ManagementResourceFun)) {
            return false;
        }
        return id != null && id.equals(((ManagementResourceFun) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ManagementResourceFun{" +
            "id=" + getId() +
            ", libEn='" + getLibEn() + "'" +
            ", profile='" + getProfile() + "'" +
            ", enableAdd='" + getEnableAdd() + "'" +
            ", enableCnst='" + getEnableCnst() + "'" +
            ", enableDel='" + getEnableDel() + "'" +
            ", enableEd='" + getEnableEd() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", op='" + getOp() + "'" +
            ", util='" + getUtil() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
