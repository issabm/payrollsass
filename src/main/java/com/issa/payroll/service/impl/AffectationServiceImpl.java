package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Affectation;
import com.issa.payroll.repository.AffectationRepository;
import com.issa.payroll.service.AffectationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Affectation}.
 */
@Service
@Transactional
public class AffectationServiceImpl implements AffectationService {

    private final Logger log = LoggerFactory.getLogger(AffectationServiceImpl.class);

    private final AffectationRepository affectationRepository;

    public AffectationServiceImpl(AffectationRepository affectationRepository) {
        this.affectationRepository = affectationRepository;
    }

    @Override
    public Affectation save(Affectation affectation) {
        log.debug("Request to save Affectation : {}", affectation);
        return affectationRepository.save(affectation);
    }

    @Override
    public Optional<Affectation> partialUpdate(Affectation affectation) {
        log.debug("Request to partially update Affectation : {}", affectation);

        return affectationRepository
            .findById(affectation.getId())
            .map(existingAffectation -> {
                if (affectation.getMatricule() != null) {
                    existingAffectation.setMatricule(affectation.getMatricule());
                }
                if (affectation.getDateDebut() != null) {
                    existingAffectation.setDateDebut(affectation.getDateDebut());
                }
                if (affectation.getDateFin() != null) {
                    existingAffectation.setDateFin(affectation.getDateFin());
                }
                if (affectation.getDateop() != null) {
                    existingAffectation.setDateop(affectation.getDateop());
                }
                if (affectation.getUtil() != null) {
                    existingAffectation.setUtil(affectation.getUtil());
                }
                if (affectation.getModifiedBy() != null) {
                    existingAffectation.setModifiedBy(affectation.getModifiedBy());
                }
                if (affectation.getOp() != null) {
                    existingAffectation.setOp(affectation.getOp());
                }
                if (affectation.getIsDeleted() != null) {
                    existingAffectation.setIsDeleted(affectation.getIsDeleted());
                }
                if (affectation.getCreatedDate() != null) {
                    existingAffectation.setCreatedDate(affectation.getCreatedDate());
                }
                if (affectation.getModifiedDate() != null) {
                    existingAffectation.setModifiedDate(affectation.getModifiedDate());
                }

                return existingAffectation;
            })
            .map(affectationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Affectation> findAll(Pageable pageable) {
        log.debug("Request to get all Affectations");
        return affectationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Affectation> findOne(Long id) {
        log.debug("Request to get Affectation : {}", id);
        return affectationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Affectation : {}", id);
        affectationRepository.deleteById(id);
    }
}
