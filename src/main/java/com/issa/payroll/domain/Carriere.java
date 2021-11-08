package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Carriere.
 */
@Entity
@Table(name = "rh_carriere")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Carriere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @Column(name = "date_emploi")
    private LocalDate dateEmploi;

    @Column(name = "date_echlon")
    private LocalDate dateEchlon;

    @Column(name = "date_categorie")
    private LocalDate dateCategorie;

    @Column(name = "dateop")
    private ZonedDateTime dateop;

    @Column(name = "util")
    private String util;

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
    @JsonIgnoreProperties(value = { "pays", "natureConfig", "affilication", "entreprise", "groupe" }, allowSetters = true)
    private Echlon echlon;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pays", "natureConfig", "affilication", "entreprise", "groupe" }, allowSetters = true)
    private Category category;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pays", "natureConfig", "affilication", "entreprise", "groupe" }, allowSetters = true)
    private Emploi emploi;

    @ManyToOne
    @JsonIgnoreProperties(value = { "contrat", "groupe", "entreprise", "affiliation", "situation" }, allowSetters = true)
    private Affectation affectation;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Carriere id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public Carriere dateDebut(LocalDate dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public Carriere dateFin(LocalDate dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public LocalDate getDateEmploi() {
        return this.dateEmploi;
    }

    public Carriere dateEmploi(LocalDate dateEmploi) {
        this.setDateEmploi(dateEmploi);
        return this;
    }

    public void setDateEmploi(LocalDate dateEmploi) {
        this.dateEmploi = dateEmploi;
    }

    public LocalDate getDateEchlon() {
        return this.dateEchlon;
    }

    public Carriere dateEchlon(LocalDate dateEchlon) {
        this.setDateEchlon(dateEchlon);
        return this;
    }

    public void setDateEchlon(LocalDate dateEchlon) {
        this.dateEchlon = dateEchlon;
    }

    public LocalDate getDateCategorie() {
        return this.dateCategorie;
    }

    public Carriere dateCategorie(LocalDate dateCategorie) {
        this.setDateCategorie(dateCategorie);
        return this;
    }

    public void setDateCategorie(LocalDate dateCategorie) {
        this.dateCategorie = dateCategorie;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public Carriere dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getUtil() {
        return this.util;
    }

    public Carriere util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Carriere modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getOp() {
        return this.op;
    }

    public Carriere op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Carriere isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public Carriere createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public Carriere modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Echlon getEchlon() {
        return this.echlon;
    }

    public void setEchlon(Echlon echlon) {
        this.echlon = echlon;
    }

    public Carriere echlon(Echlon echlon) {
        this.setEchlon(echlon);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Carriere category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Emploi getEmploi() {
        return this.emploi;
    }

    public void setEmploi(Emploi emploi) {
        this.emploi = emploi;
    }

    public Carriere emploi(Emploi emploi) {
        this.setEmploi(emploi);
        return this;
    }

    public Affectation getAffectation() {
        return this.affectation;
    }

    public void setAffectation(Affectation affectation) {
        this.affectation = affectation;
    }

    public Carriere affectation(Affectation affectation) {
        this.setAffectation(affectation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carriere)) {
            return false;
        }
        return id != null && id.equals(((Carriere) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Carriere{" +
            "id=" + getId() +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", dateEmploi='" + getDateEmploi() + "'" +
            ", dateEchlon='" + getDateEchlon() + "'" +
            ", dateCategorie='" + getDateCategorie() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", util='" + getUtil() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", op='" + getOp() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
