package com.issa.payroll.service.impl;

import com.issa.payroll.domain.SituationFamiliale;
import com.issa.payroll.repository.SituationFamilialeRepository;
import com.issa.payroll.service.SituationFamilialeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SituationFamiliale}.
 */
@Service
@Transactional
public class SituationFamilialeServiceImpl implements SituationFamilialeService {

    private final Logger log = LoggerFactory.getLogger(SituationFamilialeServiceImpl.class);

    private final SituationFamilialeRepository situationFamilialeRepository;

    public SituationFamilialeServiceImpl(SituationFamilialeRepository situationFamilialeRepository) {
        this.situationFamilialeRepository = situationFamilialeRepository;
    }

    @Override
    public SituationFamiliale save(SituationFamiliale situationFamiliale) {
        log.debug("Request to save SituationFamiliale : {}", situationFamiliale);
        return situationFamilialeRepository.save(situationFamiliale);
    }

    @Override
    public Optional<SituationFamiliale> partialUpdate(SituationFamiliale situationFamiliale) {
        log.debug("Request to partially update SituationFamiliale : {}", situationFamiliale);

        return situationFamilialeRepository
            .findById(situationFamiliale.getId())
            .map(existingSituationFamiliale -> {
                if (situationFamiliale.getCode() != null) {
                    existingSituationFamiliale.setCode(situationFamiliale.getCode());
                }
                if (situationFamiliale.getLibAr() != null) {
                    existingSituationFamiliale.setLibAr(situationFamiliale.getLibAr());
                }
                if (situationFamiliale.getLibEn() != null) {
                    existingSituationFamiliale.setLibEn(situationFamiliale.getLibEn());
                }
                if (situationFamiliale.getUtil() != null) {
                    existingSituationFamiliale.setUtil(situationFamiliale.getUtil());
                }
                if (situationFamiliale.getDateop() != null) {
                    existingSituationFamiliale.setDateop(situationFamiliale.getDateop());
                }
                if (situationFamiliale.getModifiedBy() != null) {
                    existingSituationFamiliale.setModifiedBy(situationFamiliale.getModifiedBy());
                }
                if (situationFamiliale.getOp() != null) {
                    existingSituationFamiliale.setOp(situationFamiliale.getOp());
                }
                if (situationFamiliale.getIsDeleted() != null) {
                    existingSituationFamiliale.setIsDeleted(situationFamiliale.getIsDeleted());
                }
                if (situationFamiliale.getCreatedDate() != null) {
                    existingSituationFamiliale.setCreatedDate(situationFamiliale.getCreatedDate());
                }
                if (situationFamiliale.getModifiedDate() != null) {
                    existingSituationFamiliale.setModifiedDate(situationFamiliale.getModifiedDate());
                }

                return existingSituationFamiliale;
            })
            .map(situationFamilialeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SituationFamiliale> findAll(Pageable pageable) {
        log.debug("Request to get all SituationFamiliales");
        return situationFamilialeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SituationFamiliale> findOne(Long id) {
        log.debug("Request to get SituationFamiliale : {}", id);
        return situationFamilialeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SituationFamiliale : {}", id);
        situationFamilialeRepository.deleteById(id);
    }
}
