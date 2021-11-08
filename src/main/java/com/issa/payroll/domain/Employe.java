package com.issa.payroll.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employe.
 */
@Entity
@Table(name = "rh_employe")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "matricule")
    private String matricule;

    @Column(name = "nom_ar")
    private String nomAr;

    @Column(name = "nom_jf_ar")
    private String nomJfAr;

    @Column(name = "prenom_ar")
    private String prenomAr;

    @Column(name = "nom_en")
    private String nomEn;

    @Column(name = "nom_jf_en")
    private String nomJfEn;

    @Column(name = "prenom_en")
    private String prenomEn;

    @Column(name = "nom_pere_ar")
    private String nomPereAr;

    @Column(name = "nom_pere_en")
    private String nomPereEn;

    @Column(name = "nom_mere_ar")
    private String nomMereAr;

    @Column(name = "nom_mere_en")
    private String nomMereEn;

    @Column(name = "nom_gp_ar")
    private String nomGpAr;

    @Column(name = "nom_gp_en")
    private String nomGpEn;

    @Column(name = "date_naiss")
    private LocalDate dateNaiss;

    @Column(name = "rib")
    private String rib;

    @Column(name = "banque")
    private String banque;

    @Column(name = "date_rib")
    private String dateRib;

    @Column(name = "date_banque")
    private String dateBanque;

    @Lob
    @Column(name = "img")
    private byte[] img;

    @Column(name = "img_content_type")
    private String imgContentType;

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
    private Situation situation;

    @ManyToOne
    private Pays nationalite;

    @ManyToOne
    private TypeHandicap typeHandicap;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employe id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricule() {
        return this.matricule;
    }

    public Employe matricule(String matricule) {
        this.setMatricule(matricule);
        return this;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNomAr() {
        return this.nomAr;
    }

    public Employe nomAr(String nomAr) {
        this.setNomAr(nomAr);
        return this;
    }

    public void setNomAr(String nomAr) {
        this.nomAr = nomAr;
    }

    public String getNomJfAr() {
        return this.nomJfAr;
    }

    public Employe nomJfAr(String nomJfAr) {
        this.setNomJfAr(nomJfAr);
        return this;
    }

    public void setNomJfAr(String nomJfAr) {
        this.nomJfAr = nomJfAr;
    }

    public String getPrenomAr() {
        return this.prenomAr;
    }

    public Employe prenomAr(String prenomAr) {
        this.setPrenomAr(prenomAr);
        return this;
    }

    public void setPrenomAr(String prenomAr) {
        this.prenomAr = prenomAr;
    }

    public String getNomEn() {
        return this.nomEn;
    }

    public Employe nomEn(String nomEn) {
        this.setNomEn(nomEn);
        return this;
    }

    public void setNomEn(String nomEn) {
        this.nomEn = nomEn;
    }

    public String getNomJfEn() {
        return this.nomJfEn;
    }

    public Employe nomJfEn(String nomJfEn) {
        this.setNomJfEn(nomJfEn);
        return this;
    }

    public void setNomJfEn(String nomJfEn) {
        this.nomJfEn = nomJfEn;
    }

    public String getPrenomEn() {
        return this.prenomEn;
    }

    public Employe prenomEn(String prenomEn) {
        this.setPrenomEn(prenomEn);
        return this;
    }

    public void setPrenomEn(String prenomEn) {
        this.prenomEn = prenomEn;
    }

    public String getNomPereAr() {
        return this.nomPereAr;
    }

    public Employe nomPereAr(String nomPereAr) {
        this.setNomPereAr(nomPereAr);
        return this;
    }

    public void setNomPereAr(String nomPereAr) {
        this.nomPereAr = nomPereAr;
    }

    public String getNomPereEn() {
        return this.nomPereEn;
    }

    public Employe nomPereEn(String nomPereEn) {
        this.setNomPereEn(nomPereEn);
        return this;
    }

    public void setNomPereEn(String nomPereEn) {
        this.nomPereEn = nomPereEn;
    }

    public String getNomMereAr() {
        return this.nomMereAr;
    }

    public Employe nomMereAr(String nomMereAr) {
        this.setNomMereAr(nomMereAr);
        return this;
    }

    public void setNomMereAr(String nomMereAr) {
        this.nomMereAr = nomMereAr;
    }

    public String getNomMereEn() {
        return this.nomMereEn;
    }

    public Employe nomMereEn(String nomMereEn) {
        this.setNomMereEn(nomMereEn);
        return this;
    }

    public void setNomMereEn(String nomMereEn) {
        this.nomMereEn = nomMereEn;
    }

    public String getNomGpAr() {
        return this.nomGpAr;
    }

    public Employe nomGpAr(String nomGpAr) {
        this.setNomGpAr(nomGpAr);
        return this;
    }

    public void setNomGpAr(String nomGpAr) {
        this.nomGpAr = nomGpAr;
    }

    public String getNomGpEn() {
        return this.nomGpEn;
    }

    public Employe nomGpEn(String nomGpEn) {
        this.setNomGpEn(nomGpEn);
        return this;
    }

    public void setNomGpEn(String nomGpEn) {
        this.nomGpEn = nomGpEn;
    }

    public LocalDate getDateNaiss() {
        return this.dateNaiss;
    }

    public Employe dateNaiss(LocalDate dateNaiss) {
        this.setDateNaiss(dateNaiss);
        return this;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getRib() {
        return this.rib;
    }

    public Employe rib(String rib) {
        this.setRib(rib);
        return this;
    }

    public void setRib(String rib) {
        this.rib = rib;
    }

    public String getBanque() {
        return this.banque;
    }

    public Employe banque(String banque) {
        this.setBanque(banque);
        return this;
    }

    public void setBanque(String banque) {
        this.banque = banque;
    }

    public String getDateRib() {
        return this.dateRib;
    }

    public Employe dateRib(String dateRib) {
        this.setDateRib(dateRib);
        return this;
    }

    public void setDateRib(String dateRib) {
        this.dateRib = dateRib;
    }

    public String getDateBanque() {
        return this.dateBanque;
    }

    public Employe dateBanque(String dateBanque) {
        this.setDateBanque(dateBanque);
        return this;
    }

    public void setDateBanque(String dateBanque) {
        this.dateBanque = dateBanque;
    }

    public byte[] getImg() {
        return this.img;
    }

    public Employe img(byte[] img) {
        this.setImg(img);
        return this;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public String getImgContentType() {
        return this.imgContentType;
    }

    public Employe imgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
        return this;
    }

    public void setImgContentType(String imgContentType) {
        this.imgContentType = imgContentType;
    }

    public String getUtil() {
        return this.util;
    }

    public Employe util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public Employe dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public ZonedDateTime getDateModif() {
        return this.dateModif;
    }

    public Employe dateModif(ZonedDateTime dateModif) {
        this.setDateModif(dateModif);
        return this;
    }

    public void setDateModif(ZonedDateTime dateModif) {
        this.dateModif = dateModif;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Employe modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getOp() {
        return this.op;
    }

    public Employe op(String op) {
        this.setOp(op);
        return this;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public Boolean getIsDeleted() {
        return this.isDeleted;
    }

    public Employe isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Situation getSituation() {
        return this.situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }

    public Employe situation(Situation situation) {
        this.setSituation(situation);
        return this;
    }

    public Pays getNationalite() {
        return this.nationalite;
    }

    public void setNationalite(Pays pays) {
        this.nationalite = pays;
    }

    public Employe nationalite(Pays pays) {
        this.setNationalite(pays);
        return this;
    }

    public TypeHandicap getTypeHandicap() {
        return this.typeHandicap;
    }

    public void setTypeHandicap(TypeHandicap typeHandicap) {
        this.typeHandicap = typeHandicap;
    }

    public Employe typeHandicap(TypeHandicap typeHandicap) {
        this.setTypeHandicap(typeHandicap);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employe)) {
            return false;
        }
        return id != null && id.equals(((Employe) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employe{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", nomAr='" + getNomAr() + "'" +
            ", nomJfAr='" + getNomJfAr() + "'" +
            ", prenomAr='" + getPrenomAr() + "'" +
            ", nomEn='" + getNomEn() + "'" +
            ", nomJfEn='" + getNomJfEn() + "'" +
            ", prenomEn='" + getPrenomEn() + "'" +
            ", nomPereAr='" + getNomPereAr() + "'" +
            ", nomPereEn='" + getNomPereEn() + "'" +
            ", nomMereAr='" + getNomMereAr() + "'" +
            ", nomMereEn='" + getNomMereEn() + "'" +
            ", nomGpAr='" + getNomGpAr() + "'" +
            ", nomGpEn='" + getNomGpEn() + "'" +
            ", dateNaiss='" + getDateNaiss() + "'" +
            ", rib='" + getRib() + "'" +
            ", banque='" + getBanque() + "'" +
            ", dateRib='" + getDateRib() + "'" +
            ", dateBanque='" + getDateBanque() + "'" +
            ", img='" + getImg() + "'" +
            ", imgContentType='" + getImgContentType() + "'" +
            ", util='" + getUtil() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", dateModif='" + getDateModif() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            ", op='" + getOp() + "'" +
            ", isDeleted='" + getIsDeleted() + "'" +
            "}";
    }
}
