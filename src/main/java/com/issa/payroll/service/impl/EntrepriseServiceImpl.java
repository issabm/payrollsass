package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Entreprise;
import com.issa.payroll.repository.EntrepriseRepository;
import com.issa.payroll.service.EntrepriseService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Entreprise}.
 */
@Service
@Transactional
public class EntrepriseServiceImpl implements EntrepriseService {

    private final Logger log = LoggerFactory.getLogger(EntrepriseServiceImpl.class);

    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseServiceImpl(EntrepriseRepository entrepriseRepository) {
        this.entrepriseRepository = entrepriseRepository;
    }

    @Override
    public Entreprise save(Entreprise entreprise) {
        log.debug("Request to save Entreprise : {}", entreprise);
        return entrepriseRepository.save(entreprise);
    }

    @Override
    public Optional<Entreprise> partialUpdate(Entreprise entreprise) {
        log.debug("Request to partially update Entreprise : {}", entreprise);

        return entrepriseRepository
            .findById(entreprise.getId())
            .map(existingEntreprise -> {
                if (entreprise.getCode() != null) {
                    existingEntreprise.setCode(entreprise.getCode());
                }
                if (entreprise.getMatFiscal() != null) {
                    existingEntreprise.setMatFiscal(entreprise.getMatFiscal());
                }
                if (entreprise.getRegistreCommerce() != null) {
                    existingEntreprise.setRegistreCommerce(entreprise.getRegistreCommerce());
                }
                if (entreprise.getNomCommerceAr() != null) {
                    existingEntreprise.setNomCommerceAr(entreprise.getNomCommerceAr());
                }
                if (entreprise.getNomCommerceEn() != null) {
                    existingEntreprise.setNomCommerceEn(entreprise.getNomCommerceEn());
                }
                if (entreprise.getRaisonSocialAr() != null) {
                    existingEntreprise.setRaisonSocialAr(entreprise.getRaisonSocialAr());
                }
                if (entreprise.getRaisonSocialEn() != null) {
                    existingEntreprise.setRaisonSocialEn(entreprise.getRaisonSocialEn());
                }
                if (entreprise.getAddresseAr() != null) {
                    existingEntreprise.setAddresseAr(entreprise.getAddresseAr());
                }
                if (entreprise.getAddresseEn() != null) {
                    existingEntreprise.setAddresseEn(entreprise.getAddresseEn());
                }
                if (entreprise.getCodePostal() != null) {
                    existingEntreprise.setCodePostal(entreprise.getCodePostal());
                }
                if (entreprise.getTel() != null) {
                    existingEntreprise.setTel(entreprise.getTel());
                }
                if (entreprise.getEmail() != null) {
                    existingEntreprise.setEmail(entreprise.getEmail());
                }
                if (entreprise.getFax() != null) {
                    existingEntreprise.setFax(entreprise.getFax());
                }
                if (entreprise.getUtil() != null) {
                    existingEntreprise.setUtil(entreprise.getUtil());
                }
                if (entreprise.getDateop() != null) {
                    existingEntreprise.setDateop(entreprise.getDateop());
                }
                if (entreprise.getModifiedBy() != null) {
                    existingEntreprise.setModifiedBy(entreprise.getModifiedBy());
                }

                return existingEntreprise;
            })
            .map(entrepriseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Entreprise> findAll(Pageable pageable) {
        log.debug("Request to get all Entreprises");
        return entrepriseRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Entreprise> findOne(Long id) {
        log.debug("Request to get Entreprise : {}", id);
        return entrepriseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entreprise : {}", id);
        entrepriseRepository.deleteById(id);
    }
}
