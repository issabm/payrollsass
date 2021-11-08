package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PalierCondition.
 */
@Entity
@Table(name = "cfg_global")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PalierCondition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "lib_en")
    private String libEn;

    @Column(name = "lib_ar")
    private String libAr;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "min_val")
    private Double minVal;

    @Column(name = "max_val")
    private Double maxVal;

    @Column(name = "util")
    private String util;

    @Column(name = "dateop")
    private ZonedDateTime dateop;

    @Column(name = "date_modif")
    private ZonedDateTime dateModif;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "op")
    private String op;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pays", "affilication", "entreprise", "groupe", "plateTarget", "platBaseCalcul" }, allowSetters = true)
    private PalierPlate palierPlate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PalierCondition id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public PalierCondition code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibEn() {
        return this.libEn;
    }

    public PalierCondition libEn(String libEn) {
        this.setLibEn(libEn);
        return this;
    }

    public void setLibEn(String libEn) {
        this.libEn = libEn;
    }

    public String getLibAr() {
        return this.libAr;
    }

    public PalierCondition libAr(String libAr) {
        this.setLibAr(libAr);
        return this;
    }

    public void setLibAr(String libAr) {
        this.libAr = libAr;
    }

    public Integer getAnnee() {
        return this.annee;
    }

    public PalierCondition annee(Integer annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Double getMinVal() {
        return this.minVal;
    }

    public PalierCondition minVal(Double minVal) {
        this.setMinVal(minVal);
        return this;
    }

    public void setMinVal(Double minVal) {
        this.minVal = minVal;
    }

    public Double getMaxVal() {
        return this.maxVal;
    }

    public PalierCondition maxVal(Double maxVal) {
        this.setMaxVal(maxVal);
        return this;
    }

    public void setMaxVal(Double maxVal) {
        this.maxVal = maxVal;
    }

    public String getUtil() {
        return this.util;
    }

    public PalierCondition util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public PalierCondition dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public ZonedDateTime getDateModif() {
        return this.dateModif;
    }

    public PalierCondition dateModif(ZonedDateTime dateModif) {
        this.setDateModif(dateModif);
        return this;
    }

    public void setDateModif(ZonedDateTime dateModif) {
        this.dateModif = dateModif;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public PalierCondition modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getOp() {
        return this.op;
    }

    public PalierCondition op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public PalierCondition isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public PalierPlate getPalierPlate() {
        return this.palierPlate;
    }

    public void setPalierPlate(PalierPlate palierPlate) {
        this.palierPlate = palierPlate;
    }

    public PalierCondition palierPlate(PalierPlate palierPlate) {
        this.setPalierPlate(palierPlate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PalierCondition)) {
            return false;
        }
        return id != null && id.equals(((PalierCondition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PalierCondition{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libEn='" + getLibEn() + "'" +
            ", libAr='" + getLibAr() + "'" +
            ", annee=" + getAnnee() +
            ", minVal=" + getMinVal() +
            ", maxVal=" + getMaxVal() +
            ", util='" + getUtil() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", dateModif='" + getDateModif() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", op='" + getOp() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            "}";
    }
}
