package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Identite.
 */
@Entity
@Table(name = "rh_identite")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Identite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "date_issued")
    private LocalDate dateIssued;

    @Column(name = "place_issed")
    private String placeIssed;

    @Column(name = "date_vld")
    private LocalDate dateVld;

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
    @JsonIgnoreProperties(value = { "pays" }, allowSetters = true)
    private TypeIdentite typeIdentite;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Identite id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Identite code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDate getDateIssued() {
        return this.dateIssued;
    }

    public Identite dateIssued(LocalDate dateIssued) {
        this.setDateIssued(dateIssued);
        return this;
    }

    public void setDateIssued(LocalDate dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getPlaceIssed() {
        return this.placeIssed;
    }

    public Identite placeIssed(String placeIssed) {
        this.setPlaceIssed(placeIssed);
        return this;
    }

    public void setPlaceIssed(String placeIssed) {
        this.placeIssed = placeIssed;
    }

    public LocalDate getDateVld() {
        return this.dateVld;
    }

    public Identite dateVld(LocalDate dateVld) {
        this.setDateVld(dateVld);
        return this;
    }

    public void setDateVld(LocalDate dateVld) {
        this.dateVld = dateVld;
    }

    public String getUtil() {
        return this.util;
    }

    public Identite util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public Identite dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Identite modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getOp() {
        return this.op;
    }

    public Identite op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Identite isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public Identite createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public Identite modifiedDate(ZonedDateTime modifiedDate) {
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

    public Identite employe(Employe employe) {
        this.setEmploye(employe);
        return this;
    }

    public TypeIdentite getTypeIdentite() {
        return this.typeIdentite;
    }

    public void setTypeIdentite(TypeIdentite typeIdentite) {
        this.typeIdentite = typeIdentite;
    }

    public Identite typeIdentite(TypeIdentite typeIdentite) {
        this.setTypeIdentite(typeIdentite);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Identite)) {
            return false;
        }
        return id != null && id.equals(((Identite) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Identite{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", dateIssued='" + getDateIssued() + "'" +
            ", placeIssed='" + getPlaceIssed() + "'" +
            ", dateVld='" + getDateVld() + "'" +
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
