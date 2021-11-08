package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Situation;
import com.issa.payroll.repository.SituationRepository;
import com.issa.payroll.service.SituationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Situation}.
 */
@Service
@Transactional
public class SituationServiceImpl implements SituationService {

    private final Logger log = LoggerFactory.getLogger(SituationServiceImpl.class);

    private final SituationRepository situationRepository;

    public SituationServiceImpl(SituationRepository situationRepository) {
        this.situationRepository = situationRepository;
    }

    @Override
    public Situation save(Situation situation) {
        log.debug("Request to save Situation : {}", situation);
        return situationRepository.save(situation);
    }

    @Override
    public Optional<Situation> partialUpdate(Situation situation) {
        log.debug("Request to partially update Situation : {}", situation);

        return situationRepository
            .findById(situation.getId())
            .map(existingSituation -> {
                if (situation.getCode() != null) {
                    existingSituation.setCode(situation.getCode());
                }
                if (situation.getEntityTarget() != null) {
                    existingSituation.setEntityTarget(situation.getEntityTarget());
                }
                if (situation.getLibAr() != null) {
                    existingSituation.setLibAr(situation.getLibAr());
                }
                if (situation.getLibEn() != null) {
                    existingSituation.setLibEn(situation.getLibEn());
                }
                if (situation.getUtil() != null) {
                    existingSituation.setUtil(situation.getUtil());
                }
                if (situation.getDateop() != null) {
                    existingSituation.setDateop(situation.getDateop());
                }
                if (situation.getModifiedBy() != null) {
                    existingSituation.setModifiedBy(situation.getModifiedBy());
                }
                if (situation.getOp() != null) {
                    existingSituation.setOp(situation.getOp());
                }
                if (situation.getIsDeleted() != null) {
                    existingSituation.setIsDeleted(situation.getIsDeleted());
                }
                if (situation.getCreatedDate() != null) {
                    existingSituation.setCreatedDate(situation.getCreatedDate());
                }
                if (situation.getModifiedDate() != null) {
                    existingSituation.setModifiedDate(situation.getModifiedDate());
                }

                return existingSituation;
            })
            .map(situationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Situation> findAll(Pageable pageable) {
        log.debug("Request to get all Situations");
        return situationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Situation> findOne(Long id) {
        log.debug("Request to get Situation : {}", id);
        return situationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Situation : {}", id);
        situationRepository.deleteById(id);
    }
}
