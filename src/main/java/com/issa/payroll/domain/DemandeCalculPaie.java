package com.issa.payroll.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DemandeCalculPaie.
 */
@Entity
@Table(name = "pr_dmd_calc")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DemandeCalculPaie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "lib")
    private String lib;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "mois")
    private Integer mois;

    @Column(name = "date_calcul")
    private LocalDate dateCalcul;

    @Column(name = "date_valid")
    private LocalDate dateValid;

    @Column(name = "date_payroll")
    private LocalDate datePayroll;

    @Column(name = "total_net")
    private Double totalNet;

    @Column(name = "total_net_devise")
    private Double totalNetDevise;

    @Column(name = "taux_change")
    private Double tauxChange;

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

    public DemandeCalculPaie id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public DemandeCalculPaie code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLib() {
        return this.lib;
    }

    public DemandeCalculPaie lib(String lib) {
        this.setLib(lib);
        return this;
    }

    public void setLib(String lib) {
        this.lib = lib;
    }

    public Integer getAnnee() {
        return this.annee;
    }

    public DemandeCalculPaie annee(Integer annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getMois() {
        return this.mois;
    }

    public DemandeCalculPaie mois(Integer mois) {
        this.setMois(mois);
        return this;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public LocalDate getDateCalcul() {
        return this.dateCalcul;
    }

    public DemandeCalculPaie dateCalcul(LocalDate dateCalcul) {
        this.setDateCalcul(dateCalcul);
        return this;
    }

    public void setDateCalcul(LocalDate dateCalcul) {
        this.dateCalcul = dateCalcul;
    }

    public LocalDate getDateValid() {
        return this.dateValid;
    }

    public DemandeCalculPaie dateValid(LocalDate dateValid) {
        this.setDateValid(dateValid);
        return this;
    }

    public void setDateValid(LocalDate dateValid) {
        this.dateValid = dateValid;
    }

    public LocalDate getDatePayroll() {
        return this.datePayroll;
    }

    public DemandeCalculPaie datePayroll(LocalDate datePayroll) {
        this.setDatePayroll(datePayroll);
        return this;
    }

    public void setDatePayroll(LocalDate datePayroll) {
        this.datePayroll = datePayroll;
    }

    public Double getTotalNet() {
        return this.totalNet;
    }

    public DemandeCalculPaie totalNet(Double totalNet) {
        this.setTotalNet(totalNet);
        return this;
    }

    public void setTotalNet(Double totalNet) {
        this.totalNet = totalNet;
    }

    public Double getTotalNetDevise() {
        return this.totalNetDevise;
    }

    public DemandeCalculPaie totalNetDevise(Double totalNetDevise) {
        this.setTotalNetDevise(totalNetDevise);
        return this;
    }

    public void setTotalNetDevise(Double totalNetDevise) {
        this.totalNetDevise = totalNetDevise;
    }

    public Double getTauxChange() {
        return this.tauxChange;
    }

    public DemandeCalculPaie tauxChange(Double tauxChange) {
        this.setTauxChange(tauxChange);
        return this;
    }

    public void setTauxChange(Double tauxChange) {
        this.tauxChange = tauxChange;
    }

    public String getCalculBy() {
        return this.calculBy;
    }

    public DemandeCalculPaie calculBy(String calculBy) {
        this.setCalculBy(calculBy);
        return this;
    }

    public void setCalculBy(String calculBy) {
        this.calculBy = calculBy;
    }

    public String getEffectBy() {
        return this.effectBy;
    }

    public DemandeCalculPaie effectBy(String effectBy) {
        this.setEffectBy(effectBy);
        return this;
    }

    public void setEffectBy(String effectBy) {
        this.effectBy = effectBy;
    }

    public LocalDate getDateSituation() {
        return this.dateSituation;
    }

    public DemandeCalculPaie dateSituation(LocalDate dateSituation) {
        this.setDateSituation(dateSituation);
        return this;
    }

    public void setDateSituation(LocalDate dateSituation) {
        this.dateSituation = dateSituation;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public DemandeCalculPaie dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public DemandeCalculPaie modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public DemandeCalculPaie createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getOp() {
        return this.op;
    }

    public DemandeCalculPaie op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getUtil() {
        return this.util;
    }

    public DemandeCalculPaie util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public DemandeCalculPaie isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public DemandeCalculPaie createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public DemandeCalculPaie modifiedDate(ZonedDateTime modifiedDate) {
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
        if (!(o instanceof DemandeCalculPaie)) {
            return false;
        }
        return id != null && id.equals(((DemandeCalculPaie) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DemandeCalculPaie{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", lib='" + getLib() + "'" +
            ", annee=" + getAnnee() +
            ", mois=" + getMois() +
            ", dateCalcul='" + getDateCalcul() + "'" +
            ", dateValid='" + getDateValid() + "'" +
            ", datePayroll='" + getDatePayroll() + "'" +
            ", totalNet=" + getTotalNet() +
            ", totalNetDevise=" + getTotalNetDevise() +
            ", tauxChange=" + getTauxChange() +
            ", calculBy='" + getCalculBy() + "'" +
            ", effectBy='" + getEffectBy() + "'" +
            ", dateSituation='" + getDateSituation() + "'" +
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
