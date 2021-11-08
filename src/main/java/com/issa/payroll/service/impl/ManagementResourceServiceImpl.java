package com.issa.payroll.service.impl;

import com.issa.payroll.domain.ManagementResource;
import com.issa.payroll.repository.ManagementResourceRepository;
import com.issa.payroll.service.ManagementResourceService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ManagementResource}.
 */
@Service
@Transactional
public class ManagementResourceServiceImpl implements ManagementResourceService {

    private final Logger log = LoggerFactory.getLogger(ManagementResourceServiceImpl.class);

    private final ManagementResourceRepository managementResourceRepository;

    public ManagementResourceServiceImpl(ManagementResourceRepository managementResourceRepository) {
        this.managementResourceRepository = managementResourceRepository;
    }

    @Override
    public ManagementResource save(ManagementResource managementResource) {
        log.debug("Request to save ManagementResource : {}", managementResource);
        return managementResourceRepository.save(managementResource);
    }

    @Override
    public Optional<ManagementResource> partialUpdate(ManagementResource managementResource) {
        log.debug("Request to partially update ManagementResource : {}", managementResource);

        return managementResourceRepository
            .findById(managementResource.getId())
            .map(existingManagementResource -> {
                if (managementResource.getUtil() != null) {
                    existingManagementResource.setUtil(managementResource.getUtil());
                }
                if (managementResource.getDateop() != null) {
                    existingManagementResource.setDateop(managementResource.getDateop());
                }
                if (managementResource.getModifiedBy() != null) {
                    existingManagementResource.setModifiedBy(managementResource.getModifiedBy());
                }
                if (managementResource.getCreatedBy() != null) {
                    existingManagementResource.setCreatedBy(managementResource.getCreatedBy());
                }
                if (managementResource.getOp() != null) {
                    existingManagementResource.setOp(managementResource.getOp());
                }
                if (managementResource.getIsDeleted() != null) {
                    existingManagementResource.setIsDeleted(managementResource.getIsDeleted());
                }
                if (managementResource.getCreatedDate() != null) {
                    existingManagementResource.setCreatedDate(managementResource.getCreatedDate());
                }
                if (managementResource.getModifiedDate() != null) {
                    existingManagementResource.setModifiedDate(managementResource.getModifiedDate());
                }

                return existingManagementResource;
            })
            .map(managementResourceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ManagementResource> findAll(Pageable pageable) {
        log.debug("Request to get all ManagementResources");
        return managementResourceRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ManagementResource> findOne(Long id) {
        log.debug("Request to get ManagementResource : {}", id);
        return managementResourceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ManagementResource : {}", id);
        managementResourceRepository.deleteById(id);
    }
}
