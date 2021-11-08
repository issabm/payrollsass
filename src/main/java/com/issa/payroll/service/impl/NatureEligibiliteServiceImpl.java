package com.issa.payroll.service.impl;

import com.issa.payroll.domain.NatureEligibilite;
import com.issa.payroll.repository.NatureEligibiliteRepository;
import com.issa.payroll.service.NatureEligibiliteService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NatureEligibilite}.
 */
@Service
@Transactional
public class NatureEligibiliteServiceImpl implements NatureEligibiliteService {

    private final Logger log = LoggerFactory.getLogger(NatureEligibiliteServiceImpl.class);

    private final NatureEligibiliteRepository natureEligibiliteRepository;

    public NatureEligibiliteServiceImpl(NatureEligibiliteRepository natureEligibiliteRepository) {
        this.natureEligibiliteRepository = natureEligibiliteRepository;
    }

    @Override
    public NatureEligibilite save(NatureEligibilite natureEligibilite) {
        log.debug("Request to save NatureEligibilite : {}", natureEligibilite);
        return natureEligibiliteRepository.save(natureEligibilite);
    }

    @Override
    public Optional<NatureEligibilite> partialUpdate(NatureEligibilite natureEligibilite) {
        log.debug("Request to partially update NatureEligibilite : {}", natureEligibilite);

        return natureEligibiliteRepository
            .findById(natureEligibilite.getId())
            .map(existingNatureEligibilite -> {
                if (natureEligibilite.getCode() != null) {
                    existingNatureEligibilite.setCode(natureEligibilite.getCode());
                }
                if (natureEligibilite.getLibEn() != null) {
                    existingNatureEligibilite.setLibEn(natureEligibilite.getLibEn());
                }
                if (natureEligibilite.getLibAr() != null) {
                    existingNatureEligibilite.setLibAr(natureEligibilite.getLibAr());
                }
                if (natureEligibilite.getLibFr() != null) {
                    existingNatureEligibilite.setLibFr(natureEligibilite.getLibFr());
                }
                if (natureEligibilite.getDateop() != null) {
                    existingNatureEligibilite.setDateop(natureEligibilite.getDateop());
                }
                if (natureEligibilite.getModifiedBy() != null) {
                    existingNatureEligibilite.setModifiedBy(natureEligibilite.getModifiedBy());
                }
                if (natureEligibilite.getCreatedBy() != null) {
                    existingNatureEligibilite.setCreatedBy(natureEligibilite.getCreatedBy());
                }
                if (natureEligibilite.getOp() != null) {
                    existingNatureEligibilite.setOp(natureEligibilite.getOp());
                }
                if (natureEligibilite.getUtil() != null) {
                    existingNatureEligibilite.setUtil(natureEligibilite.getUtil());
                }
                if (natureEligibilite.getIsDeleted() != null) {
                    existingNatureEligibilite.setIsDeleted(natureEligibilite.getIsDeleted());
                }
                if (natureEligibilite.getCreatedDate() != null) {
                    existingNatureEligibilite.setCreatedDate(natureEligibilite.getCreatedDate());
                }
                if (natureEligibilite.getModifiedDate() != null) {
                    existingNatureEligibilite.setModifiedDate(natureEligibilite.getModifiedDate());
                }

                return existingNatureEligibilite;
            })
            .map(natureEligibiliteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NatureEligibilite> findAll(Pageable pageable) {
        log.debug("Request to get all NatureEligibilites");
        return natureEligibiliteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NatureEligibilite> findOne(Long id) {
        log.debug("Request to get NatureEligibilite : {}", id);
        return natureEligibiliteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NatureEligibilite : {}", id);
        natureEligibiliteRepository.deleteById(id);
    }
}
