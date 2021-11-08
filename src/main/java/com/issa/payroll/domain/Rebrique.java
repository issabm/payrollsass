package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Rebrique.
 */
@Entity
@Table(name = "pr_rebrique")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rebrique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "priorite")
    private Integer priorite;

    @Column(name = "code")
    private String code;

    @Column(name = "lib_ar")
    private String libAr;

    @Column(name = "lib_fr")
    private String libFr;

    @Column(name = "lib_en")
    private String libEn;

    @Column(name = "in_tax")
    private Boolean inTax;

    @Column(name = "min_value")
    private Double minValue;

    @Column(name = "max_value")
    private Double maxValue;

    @Column(name = "date_situation")
    private LocalDate dateSituation;

    @Column(name = "dateop")
    private ZonedDateTime dateop;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "util")
    private String util;

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
    private ModeInput modeInput;

    @ManyToOne
    private Sens sens;

    @ManyToOne
    private Concerne concerne;

    @ManyToOne
    private Frequence frequence;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Rebrique id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriorite() {
        return this.priorite;
    }

    public Rebrique priorite(Integer priorite) {
        this.setPriorite(priorite);
        return this;
    }

    public void setPriorite(Integer priorite) {
        this.priorite = priorite;
    }

    public String getCode() {
        return this.code;
    }

    public Rebrique code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibAr() {
        return this.libAr;
    }

    public Rebrique libAr(String libAr) {
        this.setLibAr(libAr);
        return this;
    }

    public void setLibAr(String libAr) {
        this.libAr = libAr;
    }

    public String getLibFr() {
        return this.libFr;
    }

    public Rebrique libFr(String libFr) {
        this.setLibFr(libFr);
        return this;
    }

    public void setLibFr(String libFr) {
        this.libFr = libFr;
    }

    public String getLibEn() {
        return this.libEn;
    }

    public Rebrique libEn(String libEn) {
        this.setLibEn(libEn);
        return this;
    }

    public void setLibEn(String libEn) {
        this.libEn = libEn;
    }

    public Boolean getInTax() {
        return this.inTax;
    }

    public Rebrique inTax(Boolean inTax) {
        this.setInTax(inTax);
        return this;
    }

    public void setInTax(Boolean inTax) {
        this.inTax = inTax;
    }

    public Double getMinValue() {
        return this.minValue;
    }

    public Rebrique minValue(Double minValue) {
        this.setMinValue(minValue);
        return this;
    }

    public void setMinValue(Double minValue) {
        this.minValue = minValue;
    }

    public Double getMaxValue() {
        return this.maxValue;
    }

    public Rebrique maxValue(Double maxValue) {
        this.setMaxValue(maxValue);
        return this;
    }

    public void setMaxValue(Double maxValue) {
        this.maxValue = maxValue;
    }

    public LocalDate getDateSituation() {
        return this.dateSituation;
    }

    public Rebrique dateSituation(LocalDate dateSituation) {
        this.setDateSituation(dateSituation);
        return this;
    }

    public void setDateSituation(LocalDate dateSituation) {
        this.dateSituation = dateSituation;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public Rebrique dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Rebrique modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Rebrique createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUtil() {
        return this.util;
    }

    public Rebrique util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public String getOp() {
        return this.op;
    }

    public Rebrique op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Rebrique isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public Rebrique createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public Rebrique modifiedDate(ZonedDateTime modifiedDate) {
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

    public Rebrique pays(Pays pays) {
        this.setPays(pays);
        return this;
    }

    public NatureConfig getNatureConfig() {
        return this.natureConfig;
    }

    public void setNatureConfig(NatureConfig natureConfig) {
        this.natureConfig = natureConfig;
    }

    public Rebrique natureConfig(NatureConfig natureConfig) {
        this.setNatureConfig(natureConfig);
        return this;
    }

    public Affiliation getAffilication() {
        return this.affilication;
    }

    public void setAffilication(Affiliation affiliation) {
        this.affilication = affiliation;
    }

    public Rebrique affilication(Affiliation affiliation) {
        this.setAffilication(affiliation);
        return this;
    }

    public Entreprise getEntreprise() {
        return this.entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Rebrique entreprise(Entreprise entreprise) {
        this.setEntreprise(entreprise);
        return this;
    }

    public Groupe getGroupe() {
        return this.groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Rebrique groupe(Groupe groupe) {
        this.setGroupe(groupe);
        return this;
    }

    public ModeInput getModeInput() {
        return this.modeInput;
    }

    public void setModeInput(ModeInput modeInput) {
        this.modeInput = modeInput;
    }

    public Rebrique modeInput(ModeInput modeInput) {
        this.setModeInput(modeInput);
        return this;
    }

    public Sens getSens() {
        return this.sens;
    }

    public void setSens(Sens sens) {
        this.sens = sens;
    }

    public Rebrique sens(Sens sens) {
        this.setSens(sens);
        return this;
    }

    public Concerne getConcerne() {
        return this.concerne;
    }

    public void setConcerne(Concerne concerne) {
        this.concerne = concerne;
    }

    public Rebrique concerne(Concerne concerne) {
        this.setConcerne(concerne);
        return this;
    }

    public Frequence getFrequence() {
        return this.frequence;
    }

    public void setFrequence(Frequence frequence) {
        this.frequence = frequence;
    }

    public Rebrique frequence(Frequence frequence) {
        this.setFrequence(frequence);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rebrique)) {
            return false;
        }
        return id != null && id.equals(((Rebrique) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rebrique{" +
            "id=" + getId() +
            ", priorite=" + getPriorite() +
            ", code='" + getCode() + "'" +
            ", libAr='" + getLibAr() + "'" +
            ", libFr='" + getLibFr() + "'" +
            ", libEn='" + getLibEn() + "'" +
            ", inTax='" + getInTax() + "'" +
            ", minValue=" + getMinValue() +
            ", maxValue=" + getMaxValue() +
            ", dateSituation='" + getDateSituation() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", util='" + getUtil() + "'" +
            ", op='" + getOp() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            "}";
    }
}
