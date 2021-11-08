package com.issa.payroll.service.impl;

import com.issa.payroll.domain.NatureAbsence;
import com.issa.payroll.repository.NatureAbsenceRepository;
import com.issa.payroll.service.NatureAbsenceService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NatureAbsence}.
 */
@Service
@Transactional
public class NatureAbsenceServiceImpl implements NatureAbsenceService {

    private final Logger log = LoggerFactory.getLogger(NatureAbsenceServiceImpl.class);

    private final NatureAbsenceRepository natureAbsenceRepository;

    public NatureAbsenceServiceImpl(NatureAbsenceRepository natureAbsenceRepository) {
        this.natureAbsenceRepository = natureAbsenceRepository;
    }

    @Override
    public NatureAbsence save(NatureAbsence natureAbsence) {
        log.debug("Request to save NatureAbsence : {}", natureAbsence);
        return natureAbsenceRepository.save(natureAbsence);
    }

    @Override
    public Optional<NatureAbsence> partialUpdate(NatureAbsence natureAbsence) {
        log.debug("Request to partially update NatureAbsence : {}", natureAbsence);

        return natureAbsenceRepository
            .findById(natureAbsence.getId())
            .map(existingNatureAbsence -> {
                if (natureAbsence.getCode() != null) {
                    existingNatureAbsence.setCode(natureAbsence.getCode());
                }
                if (natureAbsence.getLibAr() != null) {
                    existingNatureAbsence.setLibAr(natureAbsence.getLibAr());
                }
                if (natureAbsence.getLibEn() != null) {
                    existingNatureAbsence.setLibEn(natureAbsence.getLibEn());
                }
                if (natureAbsence.getUtil() != null) {
                    existingNatureAbsence.setUtil(natureAbsence.getUtil());
                }
                if (natureAbsence.getDateop() != null) {
                    existingNatureAbsence.setDateop(natureAbsence.getDateop());
                }
                if (natureAbsence.getNbDays() != null) {
                    existingNatureAbsence.setNbDays(natureAbsence.getNbDays());
                }
                if (natureAbsence.getValuePaied() != null) {
                    existingNatureAbsence.setValuePaied(natureAbsence.getValuePaied());
                }
                if (natureAbsence.getModifiedBy() != null) {
                    existingNatureAbsence.setModifiedBy(natureAbsence.getModifiedBy());
                }
                if (natureAbsence.getOp() != null) {
                    existingNatureAbsence.setOp(natureAbsence.getOp());
                }
                if (natureAbsence.getIsDeleted() != null) {
                    existingNatureAbsence.setIsDeleted(natureAbsence.getIsDeleted());
                }
                if (natureAbsence.getCreatedDate() != null) {
                    existingNatureAbsence.setCreatedDate(natureAbsence.getCreatedDate());
                }
                if (natureAbsence.getModifiedDate() != null) {
                    existingNatureAbsence.setModifiedDate(natureAbsence.getModifiedDate());
                }

                return existingNatureAbsence;
            })
            .map(natureAbsenceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NatureAbsence> findAll(Pageable pageable) {
        log.debug("Request to get all NatureAbsences");
        return natureAbsenceRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NatureAbsence> findOne(Long id) {
        log.debug("Request to get NatureAbsence : {}", id);
        return natureAbsenceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NatureAbsence : {}", id);
        natureAbsenceRepository.deleteById(id);
    }
}
