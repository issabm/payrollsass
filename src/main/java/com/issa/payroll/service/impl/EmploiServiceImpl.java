package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Emploi;
import com.issa.payroll.repository.EmploiRepository;
import com.issa.payroll.service.EmploiService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Emploi}.
 */
@Service
@Transactional
public class EmploiServiceImpl implements EmploiService {

    private final Logger log = LoggerFactory.getLogger(EmploiServiceImpl.class);

    private final EmploiRepository emploiRepository;

    public EmploiServiceImpl(EmploiRepository emploiRepository) {
        this.emploiRepository = emploiRepository;
    }

    @Override
    public Emploi save(Emploi emploi) {
        log.debug("Request to save Emploi : {}", emploi);
        return emploiRepository.save(emploi);
    }

    @Override
    public Optional<Emploi> partialUpdate(Emploi emploi) {
        log.debug("Request to partially update Emploi : {}", emploi);

        return emploiRepository
            .findById(emploi.getId())
            .map(existingEmploi -> {
                if (emploi.getCode() != null) {
                    existingEmploi.setCode(emploi.getCode());
                }
                if (emploi.getLibAr() != null) {
                    existingEmploi.setLibAr(emploi.getLibAr());
                }
                if (emploi.getLibEn() != null) {
                    existingEmploi.setLibEn(emploi.getLibEn());
                }
                if (emploi.getUtil() != null) {
                    existingEmploi.setUtil(emploi.getUtil());
                }
                if (emploi.getDateop() != null) {
                    existingEmploi.setDateop(emploi.getDateop());
                }
                if (emploi.getModifiedBy() != null) {
                    existingEmploi.setModifiedBy(emploi.getModifiedBy());
                }
                if (emploi.getOp() != null) {
                    existingEmploi.setOp(emploi.getOp());
                }
                if (emploi.getIsDeleted() != null) {
                    existingEmploi.setIsDeleted(emploi.getIsDeleted());
                }
                if (emploi.getCreatedDate() != null) {
                    existingEmploi.setCreatedDate(emploi.getCreatedDate());
                }
                if (emploi.getModifiedDate() != null) {
                    existingEmploi.setModifiedDate(emploi.getModifiedDate());
                }

                return existingEmploi;
            })
            .map(emploiRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Emploi> findAll(Pageable pageable) {
        log.debug("Request to get all Emplois");
        return emploiRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Emploi> findOne(Long id) {
        log.debug("Request to get Emploi : {}", id);
        return emploiRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Emploi : {}", id);
        emploiRepository.deleteById(id);
    }
}
