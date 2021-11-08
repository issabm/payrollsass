package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PayrollInfo.
 */
@Entity
@Table(name = "pr_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PayrollInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "util")
    private String util;

    @Column(name = "code")
    private String code;

    @Column(name = "lib_ar")
    private String libAr;

    @Column(name = "lib_fr")
    private String libFr;

    @Column(name = "lib_er")
    private String libEr;

    @Column(name = "value_rebrique")
    private Double valueRebrique;

    @Column(name = "value_reb_devise")
    private Double valueRebDevise;

    @Column(name = "taux_change")
    private Double tauxChange;

    @Column(name = "date_calcul")
    private LocalDate dateCalcul;

    @Column(name = "date_effect")
    private LocalDate dateEffect;

    @Column(name = "calcul_by")
    private String calculBy;

    @Column(name = "effect_by")
    private String effectBy;

    @Column(name = "date_situation")
    private LocalDate dateSituation;

    @Column(name = "dateop")
    private ZonedDateTime dateop;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "op")
    private String op;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "devise", "mvt", "situation" }, allowSetters = true)
    private Payroll payroll;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PayrollInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUtil() {
        return this.util;
    }

    public PayrollInfo util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public String getCode() {
        return this.code;
    }

    public PayrollInfo code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibAr() {
        return this.libAr;
    }

    public PayrollInfo libAr(String libAr) {
        this.setLibAr(libAr);
        return this;
    }

    public void setLibAr(String libAr) {
        this.libAr = libAr;
    }

    public String getLibFr() {
        return this.libFr;
    }

    public PayrollInfo libFr(String libFr) {
        this.setLibFr(libFr);
        return this;
    }

    public void setLibFr(String libFr) {
        this.libFr = libFr;
    }

    public String getLibEr() {
        return this.libEr;
    }

    public PayrollInfo libEr(String libEr) {
        this.setLibEr(libEr);
        return this;
    }

    public void setLibEr(String libEr) {
        this.libEr = libEr;
    }

    public Double getValueRebrique() {
        return this.valueRebrique;
    }

    public PayrollInfo valueRebrique(Double valueRebrique) {
        this.setValueRebrique(valueRebrique);
        return this;
    }

    public void setValueRebrique(Double valueRebrique) {
        this.valueRebrique = valueRebrique;
    }

    public Double getValueRebDevise() {
        return this.valueRebDevise;
    }

    public PayrollInfo valueRebDevise(Double valueRebDevise) {
        this.setValueRebDevise(valueRebDevise);
        return this;
    }

    public void setValueRebDevise(Double valueRebDevise) {
        this.valueRebDevise = valueRebDevise;
    }

    public Double getTauxChange() {
        return this.tauxChange;
    }

    public PayrollInfo tauxChange(Double tauxChange) {
        this.setTauxChange(tauxChange);
        return this;
    }

    public void setTauxChange(Double tauxChange) {
        this.tauxChange = tauxChange;
    }

    public LocalDate getDateCalcul() {
        return this.dateCalcul;
    }

    public PayrollInfo dateCalcul(LocalDate dateCalcul) {
        this.setDateCalcul(dateCalcul);
        return this;
    }

    public void setDateCalcul(LocalDate dateCalcul) {
        this.dateCalcul = dateCalcul;
    }

    public LocalDate getDateEffect() {
        return this.dateEffect;
    }

    public PayrollInfo dateEffect(LocalDate dateEffect) {
        this.setDateEffect(dateEffect);
        return this;
    }

    public void setDateEffect(LocalDate dateEffect) {
        this.dateEffect = dateEffect;
    }

    public String getCalculBy() {
        return this.calculBy;
    }

    public PayrollInfo calculBy(String calculBy) {
        this.setCalculBy(calculBy);
        return this;
    }

    public void setCalculBy(String calculBy) {
        this.calculBy = calculBy;
    }

    public String getEffectBy() {
        return this.effectBy;
    }

    public PayrollInfo effectBy(String effectBy) {
        this.setEffectBy(effectBy);
        return this;
    }

    public void setEffectBy(String effectBy) {
        this.effectBy = effectBy;
    }

    public LocalDate getDateSituation() {
        return this.dateSituation;
    }

    public PayrollInfo dateSituation(LocalDate dateSituation) {
        this.setDateSituation(dateSituation);
        return this;
    }

    public void setDateSituation(LocalDate dateSituation) {
        this.dateSituation = dateSituation;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public PayrollInfo dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public PayrollInfo modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public PayrollInfo createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getOp() {
        return this.op;
    }

    public PayrollInfo op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public PayrollInfo isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public PayrollInfo createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public PayrollInfo modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Payroll getPayroll() {
        return this.payroll;
    }

    public void setPayroll(Payroll payroll) {
        this.payroll = payroll;
    }

    public PayrollInfo payroll(Payroll payroll) {
        this.setPayroll(payroll);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PayrollInfo)) {
            return false;
        }
        return id != null && id.equals(((PayrollInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PayrollInfo{" +
            "id=" + getId() +
            ", util='" + getUtil() + "'" +
            ", code='" + getCode() + "'" +
            ", libAr='" + getLibAr() + "'" +
            ", libFr='" + getLibFr() + "'" +
            ", libEr='" + getLibEr() + "'" +
            ", valueRebrique=" + getValueRebrique() +
            ", valueRebDevise=" + getValueRebDevise() +
            ", tauxChange=" + getTauxChange() +
            ", dateCalcul='" + getDateCalcul() + "'" +
            ", dateEffect='" + getDateEffect() + "'" +
            ", calculBy='" + getCalculBy() + "'" +
            ", effectBy='" + getEffectBy() + "'" +
            ", dateSituation='" + getDateSituation() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", op='" + getOp() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
