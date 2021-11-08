package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Sens;
import com.issa.payroll.repository.SensRepository;
import com.issa.payroll.service.SensService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sens}.
 */
@Service
@Transactional
public class SensServiceImpl implements SensService {

    private final Logger log = LoggerFactory.getLogger(SensServiceImpl.class);

    private final SensRepository sensRepository;

    public SensServiceImpl(SensRepository sensRepository) {
        this.sensRepository = sensRepository;
    }

    @Override
    public Sens save(Sens sens) {
        log.debug("Request to save Sens : {}", sens);
        return sensRepository.save(sens);
    }

    @Override
    public Optional<Sens> partialUpdate(Sens sens) {
        log.debug("Request to partially update Sens : {}", sens);

        return sensRepository
            .findById(sens.getId())
            .map(existingSens -> {
                if (sens.getCode() != null) {
                    existingSens.setCode(sens.getCode());
                }
                if (sens.getLibAr() != null) {
                    existingSens.setLibAr(sens.getLibAr());
                }
                if (sens.getLibEn() != null) {
                    existingSens.setLibEn(sens.getLibEn());
                }
                if (sens.getUtil() != null) {
                    existingSens.setUtil(sens.getUtil());
                }
                if (sens.getDateop() != null) {
                    existingSens.setDateop(sens.getDateop());
                }
                if (sens.getModifiedBy() != null) {
                    existingSens.setModifiedBy(sens.getModifiedBy());
                }
                if (sens.getOp() != null) {
                    existingSens.setOp(sens.getOp());
                }
                if (sens.getIsDeleted() != null) {
                    existingSens.setIsDeleted(sens.getIsDeleted());
                }
                if (sens.getCreatedDate() != null) {
                    existingSens.setCreatedDate(sens.getCreatedDate());
                }
                if (sens.getModifiedDate() != null) {
                    existingSens.setModifiedDate(sens.getModifiedDate());
                }

                return existingSens;
            })
            .map(sensRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sens> findAll(Pageable pageable) {
        log.debug("Request to get all Sens");
        return sensRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sens> findOne(Long id) {
        log.debug("Request to get Sens : {}", id);
        return sensRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sens : {}", id);
        sensRepository.deleteById(id);
    }
}
