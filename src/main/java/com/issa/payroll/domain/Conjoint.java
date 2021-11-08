package com.issa.payroll.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Conjoint.
 */
@Entity
@Table(name = "rh_conjoint")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Conjoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "nom_ar")
    private String nomAr;

    @Column(name = "prenom_ar")
    private String prenomAr;

    @Column(name = "nom_en")
    private String nomEn;

    @Column(name = "prenom_en")
    private String prenomEn;

    @Column(name = "date_naiss")
    private LocalDate dateNaiss;

    @Column(name = "does_work")
    private Boolean doesWork;

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

    @ManyToOne
    private Sexe sexe;

    @ManyToOne
    private Pays nationalite;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Conjoint id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public Conjoint matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNomAr() {
        return this.nomAr;
    }

    public Conjoint nomAr(String nomAr) {
        this.setNomAr(nomAr);
        return this;
    }

    public void setNomAr(String nomAr) {
        this.nomAr = nomAr;
    }

    public String getPrenomAr() {
        return this.prenomAr;
    }

    public Conjoint prenomAr(String prenomAr) {
        this.setPrenomAr(prenomAr);
        return this;
    }

    public void setPrenomAr(String prenomAr) {
        this.prenomAr = prenomAr;
    }

    public String getNomEn() {
        return this.nomEn;
    }

    public Conjoint nomEn(String nomEn) {
        this.setNomEn(nomEn);
        return this;
    }

    public void setNomEn(String nomEn) {
        this.nomEn = nomEn;
    }

    public String getPrenomEn() {
        return this.prenomEn;
    }

    public Conjoint prenomEn(String prenomEn) {
        this.setPrenomEn(prenomEn);
        return this;
    }

    public void setPrenomEn(String prenomEn) {
        this.prenomEn = prenomEn;
    }

    public LocalDate getDateNaiss() {
        return this.dateNaiss;
    }

    public Conjoint dateNaiss(LocalDate dateNaiss) {
        this.setDateNaiss(dateNaiss);
        return this;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public Boolean getDoesWork() {
        return this.doesWork;
    }

    public Conjoint doesWork(Boolean doesWork) {
        this.setDoesWork(doesWork);
        return this;
    }

    public void setDoesWork(Boolean doesWork) {
        this.doesWork = doesWork;
    }

    public String getUtil() {
        return this.util;
    }

    public Conjoint util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public Conjoint dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Conjoint modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getOp() {
        return this.op;
    }

    public Conjoint op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Conjoint isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Conjoint sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public Pays getNationalite() {
        return this.nationalite;
    }

    public void setNationalite(Pays pays) {
        this.nationalite = pays;
    }

    public Conjoint nationalite(Pays pays) {
        this.setNationalite(pays);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Conjoint)) {
            return false;
        }
        return id != null && id.equals(((Conjoint) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Conjoint{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", nomAr='" + getNomAr() + "'" +
            ", prenomAr='" + getPrenomAr() + "'" +
            ", nomEn='" + getNomEn() + "'" +
            ", prenomEn='" + getPrenomEn() + "'" +
            ", dateNaiss='" + getDateNaiss() + "'" +
            ", doesWork='" + getDoesWork() + "'" +
            ", util='" + getUtil() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", op='" + getOp() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            "}";
    }
}
