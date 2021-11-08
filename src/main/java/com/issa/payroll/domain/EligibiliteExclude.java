package com.issa.payroll.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EligibiliteExclude.
 */
@Entity
@Table(name = "pr_eligible_exclude")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EligibiliteExclude implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "annee_begin")
    private Integer anneeBegin;

    @Column(name = "mois_begin")
    private Integer moisBegin;

    @Column(name = "annee_end")
    private Integer anneeEnd;

    @Column(name = "mois_end")
    private Integer moisEnd;

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "code")
    private String code;

    @Column(name = "lib_en")
    private String libEn;

    @Column(name = "lib_ar")
    private String libAr;

    @Column(name = "lib_fr")
    private String libFr;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "val_payroll")
    private Double valPayroll;

    @Column(name = "nb_days_leave")
    private Integer nbDaysLeave;

    @Column(name = "pour_val_payroll")
    private Double pourValPayroll;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EligibiliteExclude id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnneeBegin() {
        return this.anneeBegin;
    }

    public EligibiliteExclude anneeBegin(Integer anneeBegin) {
        this.setAnneeBegin(anneeBegin);
        return this;
    }

    public void setAnneeBegin(Integer anneeBegin) {
        this.anneeBegin = anneeBegin;
    }

    public Integer getMoisBegin() {
        return this.moisBegin;
    }

    public EligibiliteExclude moisBegin(Integer moisBegin) {
        this.setMoisBegin(moisBegin);
        return this;
    }

    public void setMoisBegin(Integer moisBegin) {
        this.moisBegin = moisBegin;
    }

    public Integer getAnneeEnd() {
        return this.anneeEnd;
    }

    public EligibiliteExclude anneeEnd(Integer anneeEnd) {
        this.setAnneeEnd(anneeEnd);
        return this;
    }

    public void setAnneeEnd(Integer anneeEnd) {
        this.anneeEnd = anneeEnd;
    }

    public Integer getMoisEnd() {
        return this.moisEnd;
    }

    public EligibiliteExclude moisEnd(Integer moisEnd) {
        this.setMoisEnd(moisEnd);
        return this;
    }

    public void setMoisEnd(Integer moisEnd) {
        this.moisEnd = moisEnd;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public EligibiliteExclude matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getCode() {
        return this.code;
    }

    public EligibiliteExclude code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibEn() {
        return this.libEn;
    }

    public EligibiliteExclude libEn(String libEn) {
        this.setLibEn(libEn);
        return this;
    }

    public void setLibEn(String libEn) {
        this.libEn = libEn;
    }

    public String getLibAr() {
        return this.libAr;
    }

    public EligibiliteExclude libAr(String libAr) {
        this.setLibAr(libAr);
        return this;
    }

    public void setLibAr(String libAr) {
        this.libAr = libAr;
    }

    public String getLibFr() {
        return this.libFr;
    }

    public EligibiliteExclude libFr(String libFr) {
        this.setLibFr(libFr);
        return this;
    }

    public void setLibFr(String libFr) {
        this.libFr = libFr;
    }

    public Integer getAnnee() {
        return this.annee;
    }

    public EligibiliteExclude annee(Integer annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Double getValPayroll() {
        return this.valPayroll;
    }

    public EligibiliteExclude valPayroll(Double valPayroll) {
        this.setValPayroll(valPayroll);
        return this;
    }

    public void setValPayroll(Double valPayroll) {
        this.valPayroll = valPayroll;
    }

    public Integer getNbDaysLeave() {
        return this.nbDaysLeave;
    }

    public EligibiliteExclude nbDaysLeave(Integer nbDaysLeave) {
        this.setNbDaysLeave(nbDaysLeave);
        return this;
    }

    public void setNbDaysLeave(Integer nbDaysLeave) {
        this.nbDaysLeave = nbDaysLeave;
    }

    public Double getPourValPayroll() {
        return this.pourValPayroll;
    }

    public EligibiliteExclude pourValPayroll(Double pourValPayroll) {
        this.setPourValPayroll(pourValPayroll);
        return this;
    }

    public void setPourValPayroll(Double pourValPayroll) {
        this.pourValPayroll = pourValPayroll;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public EligibiliteExclude dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public EligibiliteExclude modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public EligibiliteExclude createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getOp() {
        return this.op;
    }

    public EligibiliteExclude op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getUtil() {
        return this.util;
    }

    public EligibiliteExclude util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public EligibiliteExclude isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public EligibiliteExclude createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public EligibiliteExclude modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EligibiliteExclude)) {
            return false;
        }
        return id != null && id.equals(((EligibiliteExclude) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EligibiliteExclude{" +
            "id=" + getId() +
            ", anneeBegin=" + getAnneeBegin() +
            ", moisBegin=" + getMoisBegin() +
            ", anneeEnd=" + getAnneeEnd() +
            ", moisEnd=" + getMoisEnd() +
            ", matricule='" + getMatricule() + "'" +
            ", code='" + getCode() + "'" +
            ", libEn='" + getLibEn() + "'" +
            ", libAr='" + getLibAr() + "'" +
            ", libFr='" + getLibFr() + "'" +
            ", annee=" + getAnnee() +
            ", valPayroll=" + getValPayroll() +
            ", nbDaysLeave=" + getNbDaysLeave() +
            ", pourValPayroll=" + getPourValPayroll() +
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
