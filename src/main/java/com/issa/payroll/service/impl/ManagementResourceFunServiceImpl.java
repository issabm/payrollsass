package com.issa.payroll.service.impl;

import com.issa.payroll.domain.ManagementResourceFun;
import com.issa.payroll.repository.ManagementResourceFunRepository;
import com.issa.payroll.service.ManagementResourceFunService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ManagementResourceFun}.
 */
@Service
@Transactional
public class ManagementResourceFunServiceImpl implements ManagementResourceFunService {

    private final Logger log = LoggerFactory.getLogger(ManagementResourceFunServiceImpl.class);

    private final ManagementResourceFunRepository managementResourceFunRepository;

    public ManagementResourceFunServiceImpl(ManagementResourceFunRepository managementResourceFunRepository) {
        this.managementResourceFunRepository = managementResourceFunRepository;
    }

    @Override
    public ManagementResourceFun save(ManagementResourceFun managementResourceFun) {
        log.debug("Request to save ManagementResourceFun : {}", managementResourceFun);
        return managementResourceFunRepository.save(managementResourceFun);
    }

    @Override
    public Optional<ManagementResourceFun> partialUpdate(ManagementResourceFun managementResourceFun) {
        log.debug("Request to partially update ManagementResourceFun : {}", managementResourceFun);

        return managementResourceFunRepository
            .findById(managementResourceFun.getId())
            .map(existingManagementResourceFun -> {
                if (managementResourceFun.getLibEn() != null) {
                    existingManagementResourceFun.setLibEn(managementResourceFun.getLibEn());
                }
                if (managementResourceFun.getProfile() != null) {
                    existingManagementResourceFun.setProfile(managementResourceFun.getProfile());
                }
                if (managementResourceFun.getEnableAdd() != null) {
                    existingManagementResourceFun.setEnableAdd(managementResourceFun.getEnableAdd());
                }
                if (managementResourceFun.getEnableCnst() != null) {
                    existingManagementResourceFun.setEnableCnst(managementResourceFun.getEnableCnst());
                }
                if (managementResourceFun.getEnableDel() != null) {
                    existingManagementResourceFun.setEnableDel(managementResourceFun.getEnableDel());
                }
                if (managementResourceFun.getEnableEd() != null) {
                    existingManagementResourceFun.setEnableEd(managementResourceFun.getEnableEd());
                }
                if (managementResourceFun.getDateop() != null) {
                    existingManagementResourceFun.setDateop(managementResourceFun.getDateop());
                }
                if (managementResourceFun.getModifiedBy() != null) {
                    existingManagementResourceFun.setModifiedBy(managementResourceFun.getModifiedBy());
                }
                if (managementResourceFun.getCreatedBy() != null) {
                    existingManagementResourceFun.setCreatedBy(managementResourceFun.getCreatedBy());
                }
                if (managementResourceFun.getOp() != null) {
                    existingManagementResourceFun.setOp(managementResourceFun.getOp());
                }
                if (managementResourceFun.getUtil() != null) {
                    existingManagementResourceFun.setUtil(managementResourceFun.getUtil());
                }
                if (managementResourceFun.getIsDeleted() != null) {
                    existingManagementResourceFun.setIsDeleted(managementResourceFun.getIsDeleted());
                }
                if (managementResourceFun.getCreatedDate() != null) {
                    existingManagementResourceFun.setCreatedDate(managementResourceFun.getCreatedDate());
                }
                if (managementResourceFun.getModifiedDate() != null) {
                    existingManagementResourceFun.setModifiedDate(managementResourceFun.getModifiedDate());
                }

                return existingManagementResourceFun;
            })
            .map(managementResourceFunRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ManagementResourceFun> findAll(Pageable pageable) {
        log.debug("Request to get all ManagementResourceFuns");
        return managementResourceFunRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ManagementResourceFun> findOne(Long id) {
        log.debug("Request to get ManagementResourceFun : {}", id);
        return managementResourceFunRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ManagementResourceFun : {}", id);
        managementResourceFunRepository.deleteById(id);
    }
}
