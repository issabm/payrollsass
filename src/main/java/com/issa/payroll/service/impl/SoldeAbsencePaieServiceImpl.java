package com.issa.payroll.service.impl;

import com.issa.payroll.domain.SoldeAbsencePaie;
import com.issa.payroll.repository.SoldeAbsencePaieRepository;
import com.issa.payroll.service.SoldeAbsencePaieService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SoldeAbsencePaie}.
 */
@Service
@Transactional
public class SoldeAbsencePaieServiceImpl implements SoldeAbsencePaieService {

    private final Logger log = LoggerFactory.getLogger(SoldeAbsencePaieServiceImpl.class);

    private final SoldeAbsencePaieRepository soldeAbsencePaieRepository;

    public SoldeAbsencePaieServiceImpl(SoldeAbsencePaieRepository soldeAbsencePaieRepository) {
        this.soldeAbsencePaieRepository = soldeAbsencePaieRepository;
    }

    @Override
    public SoldeAbsencePaie save(SoldeAbsencePaie soldeAbsencePaie) {
        log.debug("Request to save SoldeAbsencePaie : {}", soldeAbsencePaie);
        return soldeAbsencePaieRepository.save(soldeAbsencePaie);
    }

    @Override
    public Optional<SoldeAbsencePaie> partialUpdate(SoldeAbsencePaie soldeAbsencePaie) {
        log.debug("Request to partially update SoldeAbsencePaie : {}", soldeAbsencePaie);

        return soldeAbsencePaieRepository
            .findById(soldeAbsencePaie.getId())
            .map(existingSoldeAbsencePaie -> {
                if (soldeAbsencePaie.getAnnee() != null) {
                    existingSoldeAbsencePaie.setAnnee(soldeAbsencePaie.getAnnee());
                }
                if (soldeAbsencePaie.getMois() != null) {
                    existingSoldeAbsencePaie.setMois(soldeAbsencePaie.getMois());
                }
                if (soldeAbsencePaie.getNbDays() != null) {
                    existingSoldeAbsencePaie.setNbDays(soldeAbsencePaie.getNbDays());
                }
                if (soldeAbsencePaie.getUtil() != null) {
                    existingSoldeAbsencePaie.setUtil(soldeAbsencePaie.getUtil());
                }
                if (soldeAbsencePaie.getDateop() != null) {
                    existingSoldeAbsencePaie.setDateop(soldeAbsencePaie.getDateop());
                }
                if (soldeAbsencePaie.getModifiedBy() != null) {
                    existingSoldeAbsencePaie.setModifiedBy(soldeAbsencePaie.getModifiedBy());
                }
                if (soldeAbsencePaie.getOp() != null) {
                    existingSoldeAbsencePaie.setOp(soldeAbsencePaie.getOp());
                }
                if (soldeAbsencePaie.getIsDeleted() != null) {
                    existingSoldeAbsencePaie.setIsDeleted(soldeAbsencePaie.getIsDeleted());
                }
                if (soldeAbsencePaie.getCreatedDate() != null) {
                    existingSoldeAbsencePaie.setCreatedDate(soldeAbsencePaie.getCreatedDate());
                }
                if (soldeAbsencePaie.getModifiedDate() != null) {
                    existingSoldeAbsencePaie.setModifiedDate(soldeAbsencePaie.getModifiedDate());
                }

                return existingSoldeAbsencePaie;
            })
            .map(soldeAbsencePaieRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SoldeAbsencePaie> findAll(Pageable pageable) {
        log.debug("Request to get all SoldeAbsencePaies");
        return soldeAbsencePaieRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SoldeAbsencePaie> findOne(Long id) {
        log.debug("Request to get SoldeAbsencePaie : {}", id);
        return soldeAbsencePaieRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SoldeAbsencePaie : {}", id);
        soldeAbsencePaieRepository.deleteById(id);
    }
}
