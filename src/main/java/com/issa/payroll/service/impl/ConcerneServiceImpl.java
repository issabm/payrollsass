package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Concerne;
import com.issa.payroll.repository.ConcerneRepository;
import com.issa.payroll.service.ConcerneService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Concerne}.
 */
@Service
@Transactional
public class ConcerneServiceImpl implements ConcerneService {

    private final Logger log = LoggerFactory.getLogger(ConcerneServiceImpl.class);

    private final ConcerneRepository concerneRepository;

    public ConcerneServiceImpl(ConcerneRepository concerneRepository) {
        this.concerneRepository = concerneRepository;
    }

    @Override
    public Concerne save(Concerne concerne) {
        log.debug("Request to save Concerne : {}", concerne);
        return concerneRepository.save(concerne);
    }

    @Override
    public Optional<Concerne> partialUpdate(Concerne concerne) {
        log.debug("Request to partially update Concerne : {}", concerne);

        return concerneRepository
            .findById(concerne.getId())
            .map(existingConcerne -> {
                if (concerne.getCode() != null) {
                    existingConcerne.setCode(concerne.getCode());
                }
                if (concerne.getLibAr() != null) {
                    existingConcerne.setLibAr(concerne.getLibAr());
                }
                if (concerne.getLibEn() != null) {
                    existingConcerne.setLibEn(concerne.getLibEn());
                }
                if (concerne.getUtil() != null) {
                    existingConcerne.setUtil(concerne.getUtil());
                }
                if (concerne.getDateop() != null) {
                    existingConcerne.setDateop(concerne.getDateop());
                }
                if (concerne.getModifiedBy() != null) {
                    existingConcerne.setModifiedBy(concerne.getModifiedBy());
                }
                if (concerne.getOp() != null) {
                    existingConcerne.setOp(concerne.getOp());
                }
                if (concerne.getIsDeleted() != null) {
                    existingConcerne.setIsDeleted(concerne.getIsDeleted());
                }
                if (concerne.getCreatedDate() != null) {
                    existingConcerne.setCreatedDate(concerne.getCreatedDate());
                }
                if (concerne.getModifiedDate() != null) {
                    existingConcerne.setModifiedDate(concerne.getModifiedDate());
                }

                return existingConcerne;
            })
            .map(concerneRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Concerne> findAll(Pageable pageable) {
        log.debug("Request to get all Concernes");
        return concerneRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Concerne> findOne(Long id) {
        log.debug("Request to get Concerne : {}", id);
        return concerneRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Concerne : {}", id);
        concerneRepository.deleteById(id);
    }
}
