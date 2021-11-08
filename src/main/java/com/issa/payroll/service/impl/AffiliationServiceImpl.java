package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Affiliation;
import com.issa.payroll.repository.AffiliationRepository;
import com.issa.payroll.service.AffiliationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Affiliation}.
 */
@Service
@Transactional
public class AffiliationServiceImpl implements AffiliationService {

    private final Logger log = LoggerFactory.getLogger(AffiliationServiceImpl.class);

    private final AffiliationRepository affiliationRepository;

    public AffiliationServiceImpl(AffiliationRepository affiliationRepository) {
        this.affiliationRepository = affiliationRepository;
    }

    @Override
    public Affiliation save(Affiliation affiliation) {
        log.debug("Request to save Affiliation : {}", affiliation);
        return affiliationRepository.save(affiliation);
    }

    @Override
    public Optional<Affiliation> partialUpdate(Affiliation affiliation) {
        log.debug("Request to partially update Affiliation : {}", affiliation);

        return affiliationRepository
            .findById(affiliation.getId())
            .map(existingAffiliation -> {
                if (affiliation.getCode() != null) {
                    existingAffiliation.setCode(affiliation.getCode());
                }
                if (affiliation.getLibAr() != null) {
                    existingAffiliation.setLibAr(affiliation.getLibAr());
                }
                if (affiliation.getLibEn() != null) {
                    existingAffiliation.setLibEn(affiliation.getLibEn());
                }
                if (affiliation.getTel() != null) {
                    existingAffiliation.setTel(affiliation.getTel());
                }
                if (affiliation.getEmail() != null) {
                    existingAffiliation.setEmail(affiliation.getEmail());
                }
                if (affiliation.getFax() != null) {
                    existingAffiliation.setFax(affiliation.getFax());
                }
                if (affiliation.getUtil() != null) {
                    existingAffiliation.setUtil(affiliation.getUtil());
                }
                if (affiliation.getDateop() != null) {
                    existingAffiliation.setDateop(affiliation.getDateop());
                }
                if (affiliation.getModifiedBy() != null) {
                    existingAffiliation.setModifiedBy(affiliation.getModifiedBy());
                }
                if (affiliation.getOp() != null) {
                    existingAffiliation.setOp(affiliation.getOp());
                }
                if (affiliation.getIsDeleted() != null) {
                    existingAffiliation.setIsDeleted(affiliation.getIsDeleted());
                }

                return existingAffiliation;
            })
            .map(affiliationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Affiliation> findAll(Pageable pageable) {
        log.debug("Request to get all Affiliations");
        return affiliationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Affiliation> findOne(Long id) {
        log.debug("Request to get Affiliation : {}", id);
        return affiliationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Affiliation : {}", id);
        affiliationRepository.deleteById(id);
    }
}
