package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Rebrique;
import com.issa.payroll.repository.RebriqueRepository;
import com.issa.payroll.service.RebriqueService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rebrique}.
 */
@Service
@Transactional
public class RebriqueServiceImpl implements RebriqueService {

    private final Logger log = LoggerFactory.getLogger(RebriqueServiceImpl.class);

    private final RebriqueRepository rebriqueRepository;

    public RebriqueServiceImpl(RebriqueRepository rebriqueRepository) {
        this.rebriqueRepository = rebriqueRepository;
    }

    @Override
    public Rebrique save(Rebrique rebrique) {
        log.debug("Request to save Rebrique : {}", rebrique);
        return rebriqueRepository.save(rebrique);
    }

    @Override
    public Optional<Rebrique> partialUpdate(Rebrique rebrique) {
        log.debug("Request to partially update Rebrique : {}", rebrique);

        return rebriqueRepository
            .findById(rebrique.getId())
            .map(existingRebrique -> {
                if (rebrique.getPriorite() != null) {
                    existingRebrique.setPriorite(rebrique.getPriorite());
                }
                if (rebrique.getCode() != null) {
                    existingRebrique.setCode(rebrique.getCode());
                }
                if (rebrique.getLibAr() != null) {
                    existingRebrique.setLibAr(rebrique.getLibAr());
                }
                if (rebrique.getLibFr() != null) {
                    existingRebrique.setLibFr(rebrique.getLibFr());
                }
                if (rebrique.getLibEn() != null) {
                    existingRebrique.setLibEn(rebrique.getLibEn());
                }
                if (rebrique.getInTax() != null) {
                    existingRebrique.setInTax(rebrique.getInTax());
                }
                if (rebrique.getMinValue() != null) {
                    existingRebrique.setMinValue(rebrique.getMinValue());
                }
                if (rebrique.getMaxValue() != null) {
                    existingRebrique.setMaxValue(rebrique.getMaxValue());
                }
                if (rebrique.getDateSituation() != null) {
                    existingRebrique.setDateSituation(rebrique.getDateSituation());
                }
                if (rebrique.getDateop() != null) {
                    existingRebrique.setDateop(rebrique.getDateop());
                }
                if (rebrique.getModifiedBy() != null) {
                    existingRebrique.setModifiedBy(rebrique.getModifiedBy());
                }
                if (rebrique.getCreatedBy() != null) {
                    existingRebrique.setCreatedBy(rebrique.getCreatedBy());
                }
                if (rebrique.getUtil() != null) {
                    existingRebrique.setUtil(rebrique.getUtil());
                }
                if (rebrique.getOp() != null) {
                    existingRebrique.setOp(rebrique.getOp());
                }
                if (rebrique.getIsDeleted() != null) {
                    existingRebrique.setIsDeleted(rebrique.getIsDeleted());
                }
                if (rebrique.getCreatedDate() != null) {
                    existingRebrique.setCreatedDate(rebrique.getCreatedDate());
                }
                if (rebrique.getModifiedDate() != null) {
                    existingRebrique.setModifiedDate(rebrique.getModifiedDate());
                }

                return existingRebrique;
            })
            .map(rebriqueRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Rebrique> findAll(Pageable pageable) {
        log.debug("Request to get all Rebriques");
        return rebriqueRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rebrique> findOne(Long id) {
        log.debug("Request to get Rebrique : {}", id);
        return rebriqueRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rebrique : {}", id);
        rebriqueRepository.deleteById(id);
    }
}
