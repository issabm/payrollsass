package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SoldeAbsence.
 */
@Entity
@Table(name = "rh_solde_abs")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SoldeAbsence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "nb_days_right")
    private Integer nbDaysRight;

    @Column(name = "nb_days_consumed")
    private Integer nbDaysConsumed;

    @Column(name = "nb_days_unavailble")
    private Integer nbDaysUnavailble;

    @Column(name = "nb_days_availble")
    private Integer nbDaysAvailble;

    @Column(name = "nb_days_left")
    private Integer nbDaysLeft;

    @Column(name = "util")
    private String util;

    @Column(name = "dateop")
    private ZonedDateTime dateop;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "op")
    private String op;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "situation", "nationalite", "typeHandicap" }, allowSetters = true)
    private Employe employe;

    @ManyToOne
    @JsonIgnoreProperties(value = { "natureConfig", "affilication", "entreprise", "groupe", "sexe" }, allowSetters = true)
    private NatureAbsence natureAbsence;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SoldeAbsence id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAnnee() {
        return this.annee;
    }

    public SoldeAbsence annee(Integer annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getNbDaysRight() {
        return this.nbDaysRight;
    }

    public SoldeAbsence nbDaysRight(Integer nbDaysRight) {
        this.setNbDaysRight(nbDaysRight);
        return this;
    }

    public void setNbDaysRight(Integer nbDaysRight) {
        this.nbDaysRight = nbDaysRight;
    }

    public Integer getNbDaysConsumed() {
        return this.nbDaysConsumed;
    }

    public SoldeAbsence nbDaysConsumed(Integer nbDaysConsumed) {
        this.setNbDaysConsumed(nbDaysConsumed);
        return this;
    }

    public void setNbDaysConsumed(Integer nbDaysConsumed) {
        this.nbDaysConsumed = nbDaysConsumed;
    }

    public Integer getNbDaysUnavailble() {
        return this.nbDaysUnavailble;
    }

    public SoldeAbsence nbDaysUnavailble(Integer nbDaysUnavailble) {
        this.setNbDaysUnavailble(nbDaysUnavailble);
        return this;
    }

    public void setNbDaysUnavailble(Integer nbDaysUnavailble) {
        this.nbDaysUnavailble = nbDaysUnavailble;
    }

    public Integer getNbDaysAvailble() {
        return this.nbDaysAvailble;
    }

    public SoldeAbsence nbDaysAvailble(Integer nbDaysAvailble) {
        this.setNbDaysAvailble(nbDaysAvailble);
        return this;
    }

    public void setNbDaysAvailble(Integer nbDaysAvailble) {
        this.nbDaysAvailble = nbDaysAvailble;
    }

    public Integer getNbDaysLeft() {
        return this.nbDaysLeft;
    }

    public SoldeAbsence nbDaysLeft(Integer nbDaysLeft) {
        this.setNbDaysLeft(nbDaysLeft);
        return this;
    }

    public void setNbDaysLeft(Integer nbDaysLeft) {
        this.nbDaysLeft = nbDaysLeft;
    }

    public String getUtil() {
        return this.util;
    }

    public SoldeAbsence util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public SoldeAbsence dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public SoldeAbsence modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getOp() {
        return this.op;
    }

    public SoldeAbsence op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public SoldeAbsence isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public SoldeAbsence createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public SoldeAbsence modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Employe getEmploye() {
        return this.employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public SoldeAbsence employe(Employe employe) {
        this.setEmploye(employe);
        return this;
    }

    public NatureAbsence getNatureAbsence() {
        return this.natureAbsence;
    }

    public void setNatureAbsence(NatureAbsence natureAbsence) {
        this.natureAbsence = natureAbsence;
    }

    public SoldeAbsence natureAbsence(NatureAbsence natureAbsence) {
        this.setNatureAbsence(natureAbsence);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SoldeAbsence)) {
            return false;
        }
        return id != null && id.equals(((SoldeAbsence) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SoldeAbsence{" +
            "id=" + getId() +
            ", annee=" + getAnnee() +
            ", nbDaysRight=" + getNbDaysRight() +
            ", nbDaysConsumed=" + getNbDaysConsumed() +
            ", nbDaysUnavailble=" + getNbDaysUnavailble() +
            ", nbDaysAvailble=" + getNbDaysAvailble() +
            ", nbDaysLeft=" + getNbDaysLeft() +
            ", util='" + getUtil() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", op='" + getOp() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
