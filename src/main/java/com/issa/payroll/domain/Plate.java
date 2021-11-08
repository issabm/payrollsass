package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Plate.
 */
@Entity
@Table(name = "grp_rebrique")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Plate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "priorite")
    private Integer priorite;

    @Column(name = "value_calcul")
    private Double valueCalcul;

    @Column(name = "code")
    private String code;

    @Column(name = "lib_ar")
    private String libAr;

    @Column(name = "lib_en")
    private String libEn;

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
    private Pays pays;

    @ManyToOne
    private NatureConfig natureConfig;

    @ManyToOne
    @JsonIgnoreProperties(value = { "entreprise", "direction", "pays", "devise" }, allowSetters = true)
    private Affiliation affilication;

    @ManyToOne
    @JsonIgnoreProperties(value = { "groupe", "pays", "mere", "devise" }, allowSetters = true)
    private Entreprise entreprise;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pays", "devise" }, allowSetters = true)
    private Groupe groupe;

    @ManyToOne
    private ModeCalcul modeCalcul;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Plate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriorite() {
        return this.priorite;
    }

    public Plate priorite(Integer priorite) {
        this.setPriorite(priorite);
        return this;
    }

    public void setPriorite(Integer priorite) {
        this.priorite = priorite;
    }

    public Double getValueCalcul() {
        return this.valueCalcul;
    }

    public Plate valueCalcul(Double valueCalcul) {
        this.setValueCalcul(valueCalcul);
        return this;
    }

    public void setValueCalcul(Double valueCalcul) {
        this.valueCalcul = valueCalcul;
    }

    public String getCode() {
        return this.code;
    }

    public Plate code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibAr() {
        return this.libAr;
    }

    public Plate libAr(String libAr) {
        this.setLibAr(libAr);
        return this;
    }

    public void setLibAr(String libAr) {
        this.libAr = libAr;
    }

    public String getLibEn() {
        return this.libEn;
    }

    public Plate libEn(String libEn) {
        this.setLibEn(libEn);
        return this;
    }

    public void setLibEn(String libEn) {
        this.libEn = libEn;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public Plate dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getUtil() {
        return this.util;
    }

    public Plate util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Plate modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getOp() {
        return this.op;
    }

    public Plate op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Plate isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public Plate createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public Plate modifiedDate(ZonedDateTime modifiedDate) {
        this.setModifiedDate(modifiedDate);
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Pays getPays() {
        return this.pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Plate pays(Pays pays) {
        this.setPays(pays);
        return this;
    }

    public NatureConfig getNatureConfig() {
        return this.natureConfig;
    }

    public void setNatureConfig(NatureConfig natureConfig) {
        this.natureConfig = natureConfig;
    }

    public Plate natureConfig(NatureConfig natureConfig) {
        this.setNatureConfig(natureConfig);
        return this;
    }

    public Affiliation getAffilication() {
        return this.affilication;
    }

    public void setAffilication(Affiliation affiliation) {
        this.affilication = affiliation;
    }

    public Plate affilication(Affiliation affiliation) {
        this.setAffilication(affiliation);
        return this;
    }

    public Entreprise getEntreprise() {
        return this.entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Plate entreprise(Entreprise entreprise) {
        this.setEntreprise(entreprise);
        return this;
    }

    public Groupe getGroupe() {
        return this.groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Plate groupe(Groupe groupe) {
        this.setGroupe(groupe);
        return this;
    }

    public ModeCalcul getModeCalcul() {
        return this.modeCalcul;
    }

    public void setModeCalcul(ModeCalcul modeCalcul) {
        this.modeCalcul = modeCalcul;
    }

    public Plate modeCalcul(ModeCalcul modeCalcul) {
        this.setModeCalcul(modeCalcul);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plate)) {
            return false;
        }
        return id != null && id.equals(((Plate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plate{" +
            "id=" + getId() +
            ", priorite=" + getPriorite() +
            ", valueCalcul=" + getValueCalcul() +
            ", code='" + getCode() + "'" +
            ", libAr='" + getLibAr() + "'" +
            ", libEn='" + getLibEn() + "'" +
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
