package com.issa.payroll.service.impl;

import com.issa.payroll.domain.SousTypeContrat;
import com.issa.payroll.repository.SousTypeContratRepository;
import com.issa.payroll.service.SousTypeContratService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SousTypeContrat}.
 */
@Service
@Transactional
public class SousTypeContratServiceImpl implements SousTypeContratService {

    private final Logger log = LoggerFactory.getLogger(SousTypeContratServiceImpl.class);

    private final SousTypeContratRepository sousTypeContratRepository;

    public SousTypeContratServiceImpl(SousTypeContratRepository sousTypeContratRepository) {
        this.sousTypeContratRepository = sousTypeContratRepository;
    }

    @Override
    public SousTypeContrat save(SousTypeContrat sousTypeContrat) {
        log.debug("Request to save SousTypeContrat : {}", sousTypeContrat);
        return sousTypeContratRepository.save(sousTypeContrat);
    }

    @Override
    public Optional<SousTypeContrat> partialUpdate(SousTypeContrat sousTypeContrat) {
        log.debug("Request to partially update SousTypeContrat : {}", sousTypeContrat);

        return sousTypeContratRepository
            .findById(sousTypeContrat.getId())
            .map(existingSousTypeContrat -> {
                if (sousTypeContrat.getCode() != null) {
                    existingSousTypeContrat.setCode(sousTypeContrat.getCode());
                }
                if (sousTypeContrat.getLibAr() != null) {
                    existingSousTypeContrat.setLibAr(sousTypeContrat.getLibAr());
                }
                if (sousTypeContrat.getLibEn() != null) {
                    existingSousTypeContrat.setLibEn(sousTypeContrat.getLibEn());
                }
                if (sousTypeContrat.getUtil() != null) {
                    existingSousTypeContrat.setUtil(sousTypeContrat.getUtil());
                }
                if (sousTypeContrat.getDateop() != null) {
                    existingSousTypeContrat.setDateop(sousTypeContrat.getDateop());
                }
                if (sousTypeContrat.getModifiedBy() != null) {
                    existingSousTypeContrat.setModifiedBy(sousTypeContrat.getModifiedBy());
                }
                if (sousTypeContrat.getOp() != null) {
                    existingSousTypeContrat.setOp(sousTypeContrat.getOp());
                }
                if (sousTypeContrat.getIsDeleted() != null) {
                    existingSousTypeContrat.setIsDeleted(sousTypeContrat.getIsDeleted());
                }
                if (sousTypeContrat.getCreatedDate() != null) {
                    existingSousTypeContrat.setCreatedDate(sousTypeContrat.getCreatedDate());
                }
                if (sousTypeContrat.getModifiedDate() != null) {
                    existingSousTypeContrat.setModifiedDate(sousTypeContrat.getModifiedDate());
                }

                return existingSousTypeContrat;
            })
            .map(sousTypeContratRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SousTypeContrat> findAll(Pageable pageable) {
        log.debug("Request to get all SousTypeContrats");
        return sousTypeContratRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SousTypeContrat> findOne(Long id) {
        log.debug("Request to get SousTypeContrat : {}", id);
        return sousTypeContratRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SousTypeContrat : {}", id);
        sousTypeContratRepository.deleteById(id);
    }
}
