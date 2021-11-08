package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Enfant;
import com.issa.payroll.repository.EnfantRepository;
import com.issa.payroll.service.EnfantService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Enfant}.
 */
@Service
@Transactional
public class EnfantServiceImpl implements EnfantService {

    private final Logger log = LoggerFactory.getLogger(EnfantServiceImpl.class);

    private final EnfantRepository enfantRepository;

    public EnfantServiceImpl(EnfantRepository enfantRepository) {
        this.enfantRepository = enfantRepository;
    }

    @Override
    public Enfant save(Enfant enfant) {
        log.debug("Request to save Enfant : {}", enfant);
        return enfantRepository.save(enfant);
    }

    @Override
    public Optional<Enfant> partialUpdate(Enfant enfant) {
        log.debug("Request to partially update Enfant : {}", enfant);

        return enfantRepository
            .findById(enfant.getId())
            .map(existingEnfant -> {
                if (enfant.getNomAr() != null) {
                    existingEnfant.setNomAr(enfant.getNomAr());
                }
                if (enfant.getPrenomAr() != null) {
                    existingEnfant.setPrenomAr(enfant.getPrenomAr());
                }
                if (enfant.getNomEn() != null) {
                    existingEnfant.setNomEn(enfant.getNomEn());
                }
                if (enfant.getPrenomEn() != null) {
                    existingEnfant.setPrenomEn(enfant.getPrenomEn());
                }
                if (enfant.getDateNaiss() != null) {
                    existingEnfant.setDateNaiss(enfant.getDateNaiss());
                }
                if (enfant.getUtil() != null) {
                    existingEnfant.setUtil(enfant.getUtil());
                }
                if (enfant.getDateop() != null) {
                    existingEnfant.setDateop(enfant.getDateop());
                }
                if (enfant.getModifiedBy() != null) {
                    existingEnfant.setModifiedBy(enfant.getModifiedBy());
                }
                if (enfant.getOp() != null) {
                    existingEnfant.setOp(enfant.getOp());
                }
                if (enfant.getIsDeleted() != null) {
                    existingEnfant.setIsDeleted(enfant.getIsDeleted());
                }

                return existingEnfant;
            })
            .map(enfantRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Enfant> findAll(Pageable pageable) {
        log.debug("Request to get all Enfants");
        return enfantRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Enfant> findOne(Long id) {
        log.debug("Request to get Enfant : {}", id);
        return enfantRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Enfant : {}", id);
        enfantRepository.deleteById(id);
    }
}
