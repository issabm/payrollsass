package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Devise;
import com.issa.payroll.repository.DeviseRepository;
import com.issa.payroll.service.DeviseService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Devise}.
 */
@Service
@Transactional
public class DeviseServiceImpl implements DeviseService {

    private final Logger log = LoggerFactory.getLogger(DeviseServiceImpl.class);

    private final DeviseRepository deviseRepository;

    public DeviseServiceImpl(DeviseRepository deviseRepository) {
        this.deviseRepository = deviseRepository;
    }

    @Override
    public Devise save(Devise devise) {
        log.debug("Request to save Devise : {}", devise);
        return deviseRepository.save(devise);
    }

    @Override
    public Optional<Devise> partialUpdate(Devise devise) {
        log.debug("Request to partially update Devise : {}", devise);

        return deviseRepository
            .findById(devise.getId())
            .map(existingDevise -> {
                if (devise.getCode() != null) {
                    existingDevise.setCode(devise.getCode());
                }
                if (devise.getLibAr() != null) {
                    existingDevise.setLibAr(devise.getLibAr());
                }
                if (devise.getLibEn() != null) {
                    existingDevise.setLibEn(devise.getLibEn());
                }
                if (devise.getDateop() != null) {
                    existingDevise.setDateop(devise.getDateop());
                }
                if (devise.getUtil() != null) {
                    existingDevise.setUtil(devise.getUtil());
                }
                if (devise.getModifiedBy() != null) {
                    existingDevise.setModifiedBy(devise.getModifiedBy());
                }
                if (devise.getOp() != null) {
                    existingDevise.setOp(devise.getOp());
                }
                if (devise.getIsDeleted() != null) {
                    existingDevise.setIsDeleted(devise.getIsDeleted());
                }
                if (devise.getCreatedDate() != null) {
                    existingDevise.setCreatedDate(devise.getCreatedDate());
                }
                if (devise.getModifiedDate() != null) {
                    existingDevise.setModifiedDate(devise.getModifiedDate());
                }

                return existingDevise;
            })
            .map(deviseRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Devise> findAll(Pageable pageable) {
        log.debug("Request to get all Devises");
        return deviseRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Devise> findOne(Long id) {
        log.debug("Request to get Devise : {}", id);
        return deviseRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Devise : {}", id);
        deviseRepository.deleteById(id);
    }
}
