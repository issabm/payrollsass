package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PalierPlate.
 */
@Entity
@Table(name = "cfg_global")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PalierPlate implements Serializable {

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

    @Column(name = "effecti_value")
    private Double effectiValue;

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
    private Pays pays;

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
    @JsonIgnoreProperties(value = { "pays", "natureConfig", "affilication", "entreprise", "groupe", "modeCalcul" }, allowSetters = true)
    private Plate plateTarget;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pays", "natureConfig", "affilication", "entreprise", "groupe", "modeCalcul" }, allowSetters = true)
    private Plate platBaseCalcul;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PalierPlate id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public PalierPlate code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibEn() {
        return this.libEn;
    }

    public PalierPlate libEn(String libEn) {
        this.setLibEn(libEn);
        return this;
    }

    public void setLibEn(String libEn) {
        this.libEn = libEn;
    }

    public String getLibAr() {
        return this.libAr;
    }

    public PalierPlate libAr(String libAr) {
        this.setLibAr(libAr);
        return this;
    }

    public void setLibAr(String libAr) {
        this.libAr = libAr;
    }

    public Integer getAnnee() {
        return this.annee;
    }

    public PalierPlate annee(Integer annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Double getEffectiValue() {
        return this.effectiValue;
    }

    public PalierPlate effectiValue(Double effectiValue) {
        this.setEffectiValue(effectiValue);
        return this;
    }

    public void setEffectiValue(Double effectiValue) {
        this.effectiValue = effectiValue;
    }

    public String getUtil() {
        return this.util;
    }

    public PalierPlate util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public PalierPlate dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public ZonedDateTime getDateModif() {
        return this.dateModif;
    }

    public PalierPlate dateModif(ZonedDateTime dateModif) {
        this.setDateModif(dateModif);
        return this;
    }

    public void setDateModif(ZonedDateTime dateModif) {
        this.dateModif = dateModif;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public PalierPlate modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getOp() {
        return this.op;
    }

    public PalierPlate op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public PalierPlate isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Pays getPays() {
        return this.pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public PalierPlate pays(Pays pays) {
        this.setPays(pays);
        return this;
    }

    public Affiliation getAffilication() {
        return this.affilication;
    }

    public void setAffilication(Affiliation affiliation) {
        this.affilication = affiliation;
    }

    public PalierPlate affilication(Affiliation affiliation) {
        this.setAffilication(affiliation);
        return this;
    }

    public Entreprise getEntreprise() {
        return this.entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public PalierPlate entreprise(Entreprise entreprise) {
        this.setEntreprise(entreprise);
        return this;
    }

    public Groupe getGroupe() {
        return this.groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public PalierPlate groupe(Groupe groupe) {
        this.setGroupe(groupe);
        return this;
    }

    public Plate getPlateTarget() {
        return this.plateTarget;
    }

    public void setPlateTarget(Plate plate) {
        this.plateTarget = plate;
    }

    public PalierPlate plateTarget(Plate plate) {
        this.setPlateTarget(plate);
        return this;
    }

    public Plate getPlatBaseCalcul() {
        return this.platBaseCalcul;
    }

    public void setPlatBaseCalcul(Plate plate) {
        this.platBaseCalcul = plate;
    }

    public PalierPlate platBaseCalcul(Plate plate) {
        this.setPlatBaseCalcul(plate);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PalierPlate)) {
            return false;
        }
        return id != null && id.equals(((PalierPlate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PalierPlate{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", libEn='" + getLibEn() + "'" +
            ", libAr='" + getLibAr() + "'" +
            ", annee=" + getAnnee() +
            ", effectiValue=" + getEffectiValue() +
            ", util='" + getUtil() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", dateModif='" + getDateModif() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", op='" + getOp() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            "}";
    }
}
