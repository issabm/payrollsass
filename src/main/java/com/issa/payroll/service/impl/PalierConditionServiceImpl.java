package com.issa.payroll.service.impl;

import com.issa.payroll.domain.PalierCondition;
import com.issa.payroll.repository.PalierConditionRepository;
import com.issa.payroll.service.PalierConditionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PalierCondition}.
 */
@Service
@Transactional
public class PalierConditionServiceImpl implements PalierConditionService {

    private final Logger log = LoggerFactory.getLogger(PalierConditionServiceImpl.class);

    private final PalierConditionRepository palierConditionRepository;

    public PalierConditionServiceImpl(PalierConditionRepository palierConditionRepository) {
        this.palierConditionRepository = palierConditionRepository;
    }

    @Override
    public PalierCondition save(PalierCondition palierCondition) {
        log.debug("Request to save PalierCondition : {}", palierCondition);
        return palierConditionRepository.save(palierCondition);
    }

    @Override
    public Optional<PalierCondition> partialUpdate(PalierCondition palierCondition) {
        log.debug("Request to partially update PalierCondition : {}", palierCondition);

        return palierConditionRepository
            .findById(palierCondition.getId())
            .map(existingPalierCondition -> {
                if (palierCondition.getCode() != null) {
                    existingPalierCondition.setCode(palierCondition.getCode());
                }
                if (palierCondition.getLibEn() != null) {
                    existingPalierCondition.setLibEn(palierCondition.getLibEn());
                }
                if (palierCondition.getLibAr() != null) {
                    existingPalierCondition.setLibAr(palierCondition.getLibAr());
                }
                if (palierCondition.getAnnee() != null) {
                    existingPalierCondition.setAnnee(palierCondition.getAnnee());
                }
                if (palierCondition.getMinVal() != null) {
                    existingPalierCondition.setMinVal(palierCondition.getMinVal());
                }
                if (palierCondition.getMaxVal() != null) {
                    existingPalierCondition.setMaxVal(palierCondition.getMaxVal());
                }
                if (palierCondition.getUtil() != null) {
                    existingPalierCondition.setUtil(palierCondition.getUtil());
                }
                if (palierCondition.getDateop() != null) {
                    existingPalierCondition.setDateop(palierCondition.getDateop());
                }
                if (palierCondition.getDateModif() != null) {
                    existingPalierCondition.setDateModif(palierCondition.getDateModif());
                }
                if (palierCondition.getModifiedBy() != null) {
                    existingPalierCondition.setModifiedBy(palierCondition.getModifiedBy());
                }
                if (palierCondition.getOp() != null) {
                    existingPalierCondition.setOp(palierCondition.getOp());
                }
                if (palierCondition.getIsDeleted() != null) {
                    existingPalierCondition.setIsDeleted(palierCondition.getIsDeleted());
                }

                return existingPalierCondition;
            })
            .map(palierConditionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PalierCondition> findAll(Pageable pageable) {
        log.debug("Request to get all PalierConditions");
        return palierConditionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PalierCondition> findOne(Long id) {
        log.debug("Request to get PalierCondition : {}", id);
        return palierConditionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PalierCondition : {}", id);
        palierConditionRepository.deleteById(id);
    }
}
