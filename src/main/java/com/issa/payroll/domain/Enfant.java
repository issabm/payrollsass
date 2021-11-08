package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Enfant.
 */
@Entity
@Table(name = "rh_enfant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Enfant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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
    private TypeHandicap typeHandicap;

    @ManyToOne
    private Sexe sexe;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pays" }, allowSetters = true)
    private NiveauScolaire nivScolaire;

    @ManyToOne
    @JsonIgnoreProperties(value = { "employe", "situationFamiliale", "conjoint" }, allowSetters = true)
    private Famille famille;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Enfant id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomAr() {
        return this.nomAr;
    }

    public Enfant nomAr(String nomAr) {
        this.setNomAr(nomAr);
        return this;
    }

    public void setNomAr(String nomAr) {
        this.nomAr = nomAr;
    }

    public String getPrenomAr() {
        return this.prenomAr;
    }

    public Enfant prenomAr(String prenomAr) {
        this.setPrenomAr(prenomAr);
        return this;
    }

    public void setPrenomAr(String prenomAr) {
        this.prenomAr = prenomAr;
    }

    public String getNomEn() {
        return this.nomEn;
    }

    public Enfant nomEn(String nomEn) {
        this.setNomEn(nomEn);
        return this;
    }

    public void setNomEn(String nomEn) {
        this.nomEn = nomEn;
    }

    public String getPrenomEn() {
        return this.prenomEn;
    }

    public Enfant prenomEn(String prenomEn) {
        this.setPrenomEn(prenomEn);
        return this;
    }

    public void setPrenomEn(String prenomEn) {
        this.prenomEn = prenomEn;
    }

    public LocalDate getDateNaiss() {
        return this.dateNaiss;
    }

    public Enfant dateNaiss(LocalDate dateNaiss) {
        this.setDateNaiss(dateNaiss);
        return this;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getUtil() {
        return this.util;
    }

    public Enfant util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public Enfant dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Enfant modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getOp() {
        return this.op;
    }

    public Enfant op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Enfant isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public TypeHandicap getTypeHandicap() {
        return this.typeHandicap;
    }

    public void setTypeHandicap(TypeHandicap typeHandicap) {
        this.typeHandicap = typeHandicap;
    }

    public Enfant typeHandicap(TypeHandicap typeHandicap) {
        this.setTypeHandicap(typeHandicap);
        return this;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Enfant sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public NiveauScolaire getNivScolaire() {
        return this.nivScolaire;
    }

    public void setNivScolaire(NiveauScolaire niveauScolaire) {
        this.nivScolaire = niveauScolaire;
    }

    public Enfant nivScolaire(NiveauScolaire niveauScolaire) {
        this.setNivScolaire(niveauScolaire);
        return this;
    }

    public Famille getFamille() {
        return this.famille;
    }

    public void setFamille(Famille famille) {
        this.famille = famille;
    }

    public Enfant famille(Famille famille) {
        this.setFamille(famille);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enfant)) {
            return false;
        }
        return id != null && id.equals(((Enfant) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Enfant{" +
            "id=" + getId() +
            ", nomAr='" + getNomAr() + "'" +
            ", prenomAr='" + getPrenomAr() + "'" +
            ", nomEn='" + getNomEn() + "'" +
            ", prenomEn='" + getPrenomEn() + "'" +
            ", dateNaiss='" + getDateNaiss() + "'" +
            ", util='" + getUtil() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", op='" + getOp() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            "}";
    }
}
