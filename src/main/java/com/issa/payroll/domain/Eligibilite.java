package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Eligibilite.
 */
@Entity
@Table(name = "pr_eligible")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Eligibilite implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "priorite")
    private Integer priorite;

    @Column(name = "annee")
    private Integer annee;

    @Column(name = "mois")
    private Integer mois;

    @Column(name = "nb_ent")
    private Integer nbEnt;

    @Column(name = "age_ent")
    private Integer ageEnt;

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "code")
    private String code;

    @Column(name = "lib_en")
    private String libEn;

    @Column(name = "lib_ar")
    private String libAr;

    @Column(name = "lib_fr")
    private String libFr;

    @Column(name = "val_payroll")
    private Double valPayroll;

    @Column(name = "nb_days_leave")
    private Integer nbDaysLeave;

    @Column(name = "pour_val_payroll")
    private Double pourValPayroll;

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

    @ManyToOne
    private Pays pays;

    @ManyToOne
    private NatureConfig natureConfig;

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
    private TypeHandicap typeHandicap;

    @ManyToOne
    private Pays nationalite;

    @ManyToOne
    private Sexe sexe;

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
    @JsonIgnoreProperties(value = { "pays" }, allowSetters = true)
    private TypeContrat typeContrat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "typeContrat" }, allowSetters = true)
    private SousTypeContrat sousTypeContrat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pays" }, allowSetters = true)
    private NiveauEtude niveauEtude;

    @ManyToOne
    private NatureEligibilite natureEligible;

    @ManyToOne
    @JsonIgnoreProperties(
        value = { "pays", "natureConfig", "affilication", "entreprise", "groupe", "modeInput", "sens", "concerne", "frequence" },
        allowSetters = true
    )
    private Rebrique rebrique;

    @ManyToOne
    @JsonIgnoreProperties(value = { "natureConfig", "affilication", "entreprise", "groupe", "sexe" }, allowSetters = true)
    private NatureAbsence natureAbsence;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pays", "natureConfig", "affilication", "entreprise", "groupe", "modeCalcul" }, allowSetters = true)
    private Plate plate;

    @ManyToOne
    private TargetEligible targetEnt;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Eligibilite id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPriorite() {
        return this.priorite;
    }

    public Eligibilite priorite(Integer priorite) {
        this.setPriorite(priorite);
        return this;
    }

    public void setPriorite(Integer priorite) {
        this.priorite = priorite;
    }

    public Integer getAnnee() {
        return this.annee;
    }

    public Eligibilite annee(Integer annee) {
        this.setAnnee(annee);
        return this;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getMois() {
        return this.mois;
    }

    public Eligibilite mois(Integer mois) {
        this.setMois(mois);
        return this;
    }

    public void setMois(Integer mois) {
        this.mois = mois;
    }

    public Integer getNbEnt() {
        return this.nbEnt;
    }

    public Eligibilite nbEnt(Integer nbEnt) {
        this.setNbEnt(nbEnt);
        return this;
    }

    public void setNbEnt(Integer nbEnt) {
        this.nbEnt = nbEnt;
    }

    public Integer getAgeEnt() {
        return this.ageEnt;
    }

    public Eligibilite ageEnt(Integer ageEnt) {
        this.setAgeEnt(ageEnt);
        return this;
    }

    public void setAgeEnt(Integer ageEnt) {
        this.ageEnt = ageEnt;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public Eligibilite matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getCode() {
        return this.code;
    }

    public Eligibilite code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibEn() {
        return this.libEn;
    }

    public Eligibilite libEn(String libEn) {
        this.setLibEn(libEn);
        return this;
    }

    public void setLibEn(String libEn) {
        this.libEn = libEn;
    }

    public String getLibAr() {
        return this.libAr;
    }

    public Eligibilite libAr(String libAr) {
        this.setLibAr(libAr);
        return this;
    }

    public void setLibAr(String libAr) {
        this.libAr = libAr;
    }

    public String getLibFr() {
        return this.libFr;
    }

    public Eligibilite libFr(String libFr) {
        this.setLibFr(libFr);
        return this;
    }

    public void setLibFr(String libFr) {
        this.libFr = libFr;
    }

    public Double getValPayroll() {
        return this.valPayroll;
    }

    public Eligibilite valPayroll(Double valPayroll) {
        this.setValPayroll(valPayroll);
        return this;
    }

    public void setValPayroll(Double valPayroll) {
        this.valPayroll = valPayroll;
    }

    public Integer getNbDaysLeave() {
        return this.nbDaysLeave;
    }

    public Eligibilite nbDaysLeave(Integer nbDaysLeave) {
        this.setNbDaysLeave(nbDaysLeave);
        return this;
    }

    public void setNbDaysLeave(Integer nbDaysLeave) {
        this.nbDaysLeave = nbDaysLeave;
    }

    public Double getPourValPayroll() {
        return this.pourValPayroll;
    }

    public Eligibilite pourValPayroll(Double pourValPayroll) {
        this.setPourValPayroll(pourValPayroll);
        return this;
    }

    public void setPourValPayroll(Double pourValPayroll) {
        this.pourValPayroll = pourValPayroll;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public Eligibilite dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Eligibilite modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Eligibilite createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getOp() {
        return this.op;
    }

    public Eligibilite op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getUtil() {
        return this.util;
    }

    public Eligibilite util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Eligibilite isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public ZonedDateTime getCreatedDate() {
        return this.createdDate;
    }

    public Eligibilite createdDate(ZonedDateTime createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getModifiedDate() {
        return this.modifiedDate;
    }

    public Eligibilite modifiedDate(ZonedDateTime modifiedDate) {
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

    public Eligibilite pays(Pays pays) {
        this.setPays(pays);
        return this;
    }

    public NatureConfig getNatureConfig() {
        return this.natureConfig;
    }

    public void setNatureConfig(NatureConfig natureConfig) {
        this.natureConfig = natureConfig;
    }

    public Eligibilite natureConfig(NatureConfig natureConfig) {
        this.setNatureConfig(natureConfig);
        return this;
    }

    public Echlon getEchlon() {
        return this.echlon;
    }

    public void setEchlon(Echlon echlon) {
        this.echlon = echlon;
    }

    public Eligibilite echlon(Echlon echlon) {
        this.setEchlon(echlon);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Eligibilite category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Emploi getEmploi() {
        return this.emploi;
    }

    public void setEmploi(Emploi emploi) {
        this.emploi = emploi;
    }

    public Eligibilite emploi(Emploi emploi) {
        this.setEmploi(emploi);
        return this;
    }

    public TypeHandicap getTypeHandicap() {
        return this.typeHandicap;
    }

    public void setTypeHandicap(TypeHandicap typeHandicap) {
        this.typeHandicap = typeHandicap;
    }

    public Eligibilite typeHandicap(TypeHandicap typeHandicap) {
        this.setTypeHandicap(typeHandicap);
        return this;
    }

    public Pays getNationalite() {
        return this.nationalite;
    }

    public void setNationalite(Pays pays) {
        this.nationalite = pays;
    }

    public Eligibilite nationalite(Pays pays) {
        this.setNationalite(pays);
        return this;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public Eligibilite sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public Affiliation getAffilication() {
        return this.affilication;
    }

    public void setAffilication(Affiliation affiliation) {
        this.affilication = affiliation;
    }

    public Eligibilite affilication(Affiliation affiliation) {
        this.setAffilication(affiliation);
        return this;
    }

    public Entreprise getEntreprise() {
        return this.entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Eligibilite entreprise(Entreprise entreprise) {
        this.setEntreprise(entreprise);
        return this;
    }

    public Groupe getGroupe() {
        return this.groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Eligibilite groupe(Groupe groupe) {
        this.setGroupe(groupe);
        return this;
    }

    public TypeContrat getTypeContrat() {
        return this.typeContrat;
    }

    public void setTypeContrat(TypeContrat typeContrat) {
        this.typeContrat = typeContrat;
    }

    public Eligibilite typeContrat(TypeContrat typeContrat) {
        this.setTypeContrat(typeContrat);
        return this;
    }

    public SousTypeContrat getSousTypeContrat() {
        return this.sousTypeContrat;
    }

    public void setSousTypeContrat(SousTypeContrat sousTypeContrat) {
        this.sousTypeContrat = sousTypeContrat;
    }

    public Eligibilite sousTypeContrat(SousTypeContrat sousTypeContrat) {
        this.setSousTypeContrat(sousTypeContrat);
        return this;
    }

    public NiveauEtude getNiveauEtude() {
        return this.niveauEtude;
    }

    public void setNiveauEtude(NiveauEtude niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public Eligibilite niveauEtude(NiveauEtude niveauEtude) {
        this.setNiveauEtude(niveauEtude);
        return this;
    }

    public NatureEligibilite getNatureEligible() {
        return this.natureEligible;
    }

    public void setNatureEligible(NatureEligibilite natureEligibilite) {
        this.natureEligible = natureEligibilite;
    }

    public Eligibilite natureEligible(NatureEligibilite natureEligibilite) {
        this.setNatureEligible(natureEligibilite);
        return this;
    }

    public Rebrique getRebrique() {
        return this.rebrique;
    }

    public void setRebrique(Rebrique rebrique) {
        this.rebrique = rebrique;
    }

    public Eligibilite rebrique(Rebrique rebrique) {
        this.setRebrique(rebrique);
        return this;
    }

    public NatureAbsence getNatureAbsence() {
        return this.natureAbsence;
    }

    public void setNatureAbsence(NatureAbsence natureAbsence) {
        this.natureAbsence = natureAbsence;
    }

    public Eligibilite natureAbsence(NatureAbsence natureAbsence) {
        this.setNatureAbsence(natureAbsence);
        return this;
    }

    public Plate getPlate() {
        return this.plate;
    }

    public void setPlate(Plate plate) {
        this.plate = plate;
    }

    public Eligibilite plate(Plate plate) {
        this.setPlate(plate);
        return this;
    }

    public TargetEligible getTargetEnt() {
        return this.targetEnt;
    }

    public void setTargetEnt(TargetEligible targetEligible) {
        this.targetEnt = targetEligible;
    }

    public Eligibilite targetEnt(TargetEligible targetEligible) {
        this.setTargetEnt(targetEligible);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Eligibilite)) {
            return false;
        }
        return id != null && id.equals(((Eligibilite) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Eligibilite{" +
            "id=" + getId() +
            ", priorite=" + getPriorite() +
            ", annee=" + getAnnee() +
            ", mois=" + getMois() +
            ", nbEnt=" + getNbEnt() +
            ", ageEnt=" + getAgeEnt() +
            ", matricule='" + getMatricule() + "'" +
            ", code='" + getCode() + "'" +
            ", libEn='" + getLibEn() + "'" +
            ", libAr='" + getLibAr() + "'" +
            ", libFr='" + getLibFr() + "'" +
            ", valPayroll=" + getValPayroll() +
            ", nbDaysLeave=" + getNbDaysLeave() +
            ", pourValPayroll=" + getPourValPayroll() +
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
