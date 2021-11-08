package com.issa.payroll.service.impl;

import com.issa.payroll.domain.SoldeAbsence;
import com.issa.payroll.repository.SoldeAbsenceRepository;
import com.issa.payroll.service.SoldeAbsenceService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SoldeAbsence}.
 */
@Service
@Transactional
public class SoldeAbsenceServiceImpl implements SoldeAbsenceService {

    private final Logger log = LoggerFactory.getLogger(SoldeAbsenceServiceImpl.class);

    private final SoldeAbsenceRepository soldeAbsenceRepository;

    public SoldeAbsenceServiceImpl(SoldeAbsenceRepository soldeAbsenceRepository) {
        this.soldeAbsenceRepository = soldeAbsenceRepository;
    }

    @Override
    public SoldeAbsence save(SoldeAbsence soldeAbsence) {
        log.debug("Request to save SoldeAbsence : {}", soldeAbsence);
        return soldeAbsenceRepository.save(soldeAbsence);
    }

    @Override
    public Optional<SoldeAbsence> partialUpdate(SoldeAbsence soldeAbsence) {
        log.debug("Request to partially update SoldeAbsence : {}", soldeAbsence);

        return soldeAbsenceRepository
            .findById(soldeAbsence.getId())
            .map(existingSoldeAbsence -> {
                if (soldeAbsence.getAnnee() != null) {
                    existingSoldeAbsence.setAnnee(soldeAbsence.getAnnee());
                }
                if (soldeAbsence.getNbDaysRight() != null) {
                    existingSoldeAbsence.setNbDaysRight(soldeAbsence.getNbDaysRight());
                }
                if (soldeAbsence.getNbDaysConsumed() != null) {
                    existingSoldeAbsence.setNbDaysConsumed(soldeAbsence.getNbDaysConsumed());
                }
                if (soldeAbsence.getNbDaysUnavailble() != null) {
                    existingSoldeAbsence.setNbDaysUnavailble(soldeAbsence.getNbDaysUnavailble());
                }
                if (soldeAbsence.getNbDaysAvailble() != null) {
                    existingSoldeAbsence.setNbDaysAvailble(soldeAbsence.getNbDaysAvailble());
                }
                if (soldeAbsence.getNbDaysLeft() != null) {
                    existingSoldeAbsence.setNbDaysLeft(soldeAbsence.getNbDaysLeft());
                }
                if (soldeAbsence.getUtil() != null) {
                    existingSoldeAbsence.setUtil(soldeAbsence.getUtil());
                }
                if (soldeAbsence.getDateop() != null) {
                    existingSoldeAbsence.setDateop(soldeAbsence.getDateop());
                }
                if (soldeAbsence.getModifiedBy() != null) {
                    existingSoldeAbsence.setModifiedBy(soldeAbsence.getModifiedBy());
                }
                if (soldeAbsence.getOp() != null) {
                    existingSoldeAbsence.setOp(soldeAbsence.getOp());
                }
                if (soldeAbsence.getIsDeleted() != null) {
                    existingSoldeAbsence.setIsDeleted(soldeAbsence.getIsDeleted());
                }
                if (soldeAbsence.getCreatedDate() != null) {
                    existingSoldeAbsence.setCreatedDate(soldeAbsence.getCreatedDate());
                }
                if (soldeAbsence.getModifiedDate() != null) {
                    existingSoldeAbsence.setModifiedDate(soldeAbsence.getModifiedDate());
                }

                return existingSoldeAbsence;
            })
            .map(soldeAbsenceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoldeAbsence> findAll(Pageable pageable) {
        log.debug("Request to get all SoldeAbsences");
        return soldeAbsenceRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SoldeAbsence> findOne(Long id) {
        log.debug("Request to get SoldeAbsence : {}", id);
        return soldeAbsenceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SoldeAbsence : {}", id);
        soldeAbsenceRepository.deleteById(id);
    }
}
