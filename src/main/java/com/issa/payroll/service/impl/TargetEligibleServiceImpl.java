package com.issa.payroll.service.impl;

import com.issa.payroll.domain.TargetEligible;
import com.issa.payroll.repository.TargetEligibleRepository;
import com.issa.payroll.service.TargetEligibleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TargetEligible}.
 */
@Service
@Transactional
public class TargetEligibleServiceImpl implements TargetEligibleService {

    private final Logger log = LoggerFactory.getLogger(TargetEligibleServiceImpl.class);

    private final TargetEligibleRepository targetEligibleRepository;

    public TargetEligibleServiceImpl(TargetEligibleRepository targetEligibleRepository) {
        this.targetEligibleRepository = targetEligibleRepository;
    }

    @Override
    public TargetEligible save(TargetEligible targetEligible) {
        log.debug("Request to save TargetEligible : {}", targetEligible);
        return targetEligibleRepository.save(targetEligible);
    }

    @Override
    public Optional<TargetEligible> partialUpdate(TargetEligible targetEligible) {
        log.debug("Request to partially update TargetEligible : {}", targetEligible);

        return targetEligibleRepository
            .findById(targetEligible.getId())
            .map(existingTargetEligible -> {
                if (targetEligible.getCode() != null) {
                    existingTargetEligible.setCode(targetEligible.getCode());
                }
                if (targetEligible.getLibEn() != null) {
                    existingTargetEligible.setLibEn(targetEligible.getLibEn());
                }
                if (targetEligible.getLibAr() != null) {
                    existingTargetEligible.setLibAr(targetEligible.getLibAr());
                }
                if (targetEligible.getLibFr() != null) {
                    existingTargetEligible.setLibFr(targetEligible.getLibFr());
                }
                if (targetEligible.getDateop() != null) {
                    existingTargetEligible.setDateop(targetEligible.getDateop());
                }
                if (targetEligible.getModifiedBy() != null) {
                    existingTargetEligible.setModifiedBy(targetEligible.getModifiedBy());
                }
                if (targetEligible.getCreatedBy() != null) {
                    existingTargetEligible.setCreatedBy(targetEligible.getCreatedBy());
                }
                if (targetEligible.getOp() != null) {
                    existingTargetEligible.setOp(targetEligible.getOp());
                }
                if (targetEligible.getUtil() != null) {
                    existingTargetEligible.setUtil(targetEligible.getUtil());
                }
                if (targetEligible.getIsDeleted() != null) {
                    existingTargetEligible.setIsDeleted(targetEligible.getIsDeleted());
                }
                if (targetEligible.getCreatedDate() != null) {
                    existingTargetEligible.setCreatedDate(targetEligible.getCreatedDate());
                }
                if (targetEligible.getModifiedDate() != null) {
                    existingTargetEligible.setModifiedDate(targetEligible.getModifiedDate());
                }

                return existingTargetEligible;
            })
            .map(targetEligibleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TargetEligible> findAll(Pageable pageable) {
        log.debug("Request to get all TargetEligibles");
        return targetEligibleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TargetEligible> findOne(Long id) {
        log.debug("Request to get TargetEligible : {}", id);
        return targetEligibleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TargetEligible : {}", id);
        targetEligibleRepository.deleteById(id);
    }
}
