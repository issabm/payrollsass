package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Frequence;
import com.issa.payroll.repository.FrequenceRepository;
import com.issa.payroll.service.FrequenceService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Frequence}.
 */
@Service
@Transactional
public class FrequenceServiceImpl implements FrequenceService {

    private final Logger log = LoggerFactory.getLogger(FrequenceServiceImpl.class);

    private final FrequenceRepository frequenceRepository;

    public FrequenceServiceImpl(FrequenceRepository frequenceRepository) {
        this.frequenceRepository = frequenceRepository;
    }

    @Override
    public Frequence save(Frequence frequence) {
        log.debug("Request to save Frequence : {}", frequence);
        return frequenceRepository.save(frequence);
    }

    @Override
    public Optional<Frequence> partialUpdate(Frequence frequence) {
        log.debug("Request to partially update Frequence : {}", frequence);

        return frequenceRepository
            .findById(frequence.getId())
            .map(existingFrequence -> {
                if (frequence.getCode() != null) {
                    existingFrequence.setCode(frequence.getCode());
                }
                if (frequence.getLibAr() != null) {
                    existingFrequence.setLibAr(frequence.getLibAr());
                }
                if (frequence.getLibEn() != null) {
                    existingFrequence.setLibEn(frequence.getLibEn());
                }
                if (frequence.getUtil() != null) {
                    existingFrequence.setUtil(frequence.getUtil());
                }
                if (frequence.getDateop() != null) {
                    existingFrequence.setDateop(frequence.getDateop());
                }
                if (frequence.getModifiedBy() != null) {
                    existingFrequence.setModifiedBy(frequence.getModifiedBy());
                }
                if (frequence.getOp() != null) {
                    existingFrequence.setOp(frequence.getOp());
                }
                if (frequence.getIsDeleted() != null) {
                    existingFrequence.setIsDeleted(frequence.getIsDeleted());
                }
                if (frequence.getCreatedDate() != null) {
                    existingFrequence.setCreatedDate(frequence.getCreatedDate());
                }
                if (frequence.getModifiedDate() != null) {
                    existingFrequence.setModifiedDate(frequence.getModifiedDate());
                }

                return existingFrequence;
            })
            .map(frequenceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Frequence> findAll(Pageable pageable) {
        log.debug("Request to get all Frequences");
        return frequenceRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Frequence> findOne(Long id) {
        log.debug("Request to get Frequence : {}", id);
        return frequenceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Frequence : {}", id);
        frequenceRepository.deleteById(id);
    }
}
