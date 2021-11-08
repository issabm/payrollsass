package com.issa.payroll.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Entreprise.
 */
@Entity
@Table(name = "rh_entreprise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "mat_fiscal")
    private String matFiscal;

    @Column(name = "registre_commerce")
    private String registreCommerce;

    @Column(name = "nom_commerce_ar")
    private String nomCommerceAr;

    @Column(name = "nom_commerce_en")
    private String nomCommerceEn;

    @Column(name = "raison_social_ar")
    private String raisonSocialAr;

    @Column(name = "raison_social_en")
    private String raisonSocialEn;

    @Column(name = "addresse_ar")
    private String addresseAr;

    @Column(name = "addresse_en")
    private String addresseEn;

    @Column(name = "code_postal")
    private String codePostal;

    @Column(name = "tel")
    private String tel;

    @Column(name = "email")
    private String email;

    @Column(name = "fax")
    private String fax;

    @Column(name = "util")
    private String util;

    @Column(name = "dateop")
    private ZonedDateTime dateop;

    @Column(name = "modified_by")
    private String modifiedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = { "pays", "devise" }, allowSetters = true)
    private Groupe groupe;

    @ManyToOne
    private Pays pays;

    @ManyToOne
    @JsonIgnoreProperties(value = { "groupe", "pays", "mere", "devise" }, allowSetters = true)
    private Entreprise mere;

    @ManyToOne
    private Devise devise;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Entreprise id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Entreprise code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMatFiscal() {
        return this.matFiscal;
    }

    public Entreprise matFiscal(String matFiscal) {
        this.setMatFiscal(matFiscal);
        return this;
    }

    public void setMatFiscal(String matFiscal) {
        this.matFiscal = matFiscal;
    }

    public String getRegistreCommerce() {
        return this.registreCommerce;
    }

    public Entreprise registreCommerce(String registreCommerce) {
        this.setRegistreCommerce(registreCommerce);
        return this;
    }

    public void setRegistreCommerce(String registreCommerce) {
        this.registreCommerce = registreCommerce;
    }

    public String getNomCommerceAr() {
        return this.nomCommerceAr;
    }

    public Entreprise nomCommerceAr(String nomCommerceAr) {
        this.setNomCommerceAr(nomCommerceAr);
        return this;
    }

    public void setNomCommerceAr(String nomCommerceAr) {
        this.nomCommerceAr = nomCommerceAr;
    }

    public String getNomCommerceEn() {
        return this.nomCommerceEn;
    }

    public Entreprise nomCommerceEn(String nomCommerceEn) {
        this.setNomCommerceEn(nomCommerceEn);
        return this;
    }

    public void setNomCommerceEn(String nomCommerceEn) {
        this.nomCommerceEn = nomCommerceEn;
    }

    public String getRaisonSocialAr() {
        return this.raisonSocialAr;
    }

    public Entreprise raisonSocialAr(String raisonSocialAr) {
        this.setRaisonSocialAr(raisonSocialAr);
        return this;
    }

    public void setRaisonSocialAr(String raisonSocialAr) {
        this.raisonSocialAr = raisonSocialAr;
    }

    public String getRaisonSocialEn() {
        return this.raisonSocialEn;
    }

    public Entreprise raisonSocialEn(String raisonSocialEn) {
        this.setRaisonSocialEn(raisonSocialEn);
        return this;
    }

    public void setRaisonSocialEn(String raisonSocialEn) {
        this.raisonSocialEn = raisonSocialEn;
    }

    public String getAddresseAr() {
        return this.addresseAr;
    }

    public Entreprise addresseAr(String addresseAr) {
        this.setAddresseAr(addresseAr);
        return this;
    }

    public void setAddresseAr(String addresseAr) {
        this.addresseAr = addresseAr;
    }

    public String getAddresseEn() {
        return this.addresseEn;
    }

    public Entreprise addresseEn(String addresseEn) {
        this.setAddresseEn(addresseEn);
        return this;
    }

    public void setAddresseEn(String addresseEn) {
        this.addresseEn = addresseEn;
    }

    public String getCodePostal() {
        return this.codePostal;
    }

    public Entreprise codePostal(String codePostal) {
        this.setCodePostal(codePostal);
        return this;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getTel() {
        return this.tel;
    }

    public Entreprise tel(String tel) {
        this.setTel(tel);
        return this;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return this.email;
    }

    public Entreprise email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return this.fax;
    }

    public Entreprise fax(String fax) {
        this.setFax(fax);
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getUtil() {
        return this.util;
    }

    public Entreprise util(String util) {
        this.setUtil(util);
        return this;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public ZonedDateTime getDateop() {
        return this.dateop;
    }

    public Entreprise dateop(ZonedDateTime dateop) {
        this.setDateop(dateop);
        return this;
    }

    public void setDateop(ZonedDateTime dateop) {
        this.dateop = dateop;
    }

    public String getModifiedBy() {
        return this.modifiedBy;
    }

    public Entreprise modifiedBy(String modifiedBy) {
        this.setModifiedBy(modifiedBy);
        return this;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Groupe getGroupe() {
        return this.groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Entreprise groupe(Groupe groupe) {
        this.setGroupe(groupe);
        return this;
    }

    public Pays getPays() {
        return this.pays;
    }

    public void setPays(Pays pays) {
        this.pays = pays;
    }

    public Entreprise pays(Pays pays) {
        this.setPays(pays);
        return this;
    }

    public Entreprise getMere() {
        return this.mere;
    }

    public void setMere(Entreprise entreprise) {
        this.mere = entreprise;
    }

    public Entreprise mere(Entreprise entreprise) {
        this.setMere(entreprise);
        return this;
    }

    public Devise getDevise() {
        return this.devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public Entreprise devise(Devise devise) {
        this.setDevise(devise);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entreprise)) {
            return false;
        }
        return id != null && id.equals(((Entreprise) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entreprise{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", matFiscal='" + getMatFiscal() + "'" +
            ", registreCommerce='" + getRegistreCommerce() + "'" +
            ", nomCommerceAr='" + getNomCommerceAr() + "'" +
            ", nomCommerceEn='" + getNomCommerceEn() + "'" +
            ", raisonSocialAr='" + getRaisonSocialAr() + "'" +
            ", raisonSocialEn='" + getRaisonSocialEn() + "'" +
            ", addresseAr='" + getAddresseAr() + "'" +
            ", addresseEn='" + getAddresseEn() + "'" +
            ", codePostal='" + getCodePostal() + "'" +
            ", tel='" + getTel() + "'" +
            ", email='" + getEmail() + "'" +
            ", fax='" + getFax() + "'" +
            ", util='" + getUtil() + "'" +
            ", dateop='" + getDateop() + "'" +
            ", modifiedBy='" + getModifiedBy() + "'" +
            "}";
    }
}
