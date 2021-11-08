package com.issa.payroll.service;

import com.issa.payroll.domain.ManagementResource;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ManagementResource}.
 */
public interface ManagementResourceService {
    /**
     * Save a managementResource.
     *
     * @param managementResource the entity to save.
     * @return the persisted entity.
     */
    ManagementResource save(ManagementResource managementResource);

    /**
     * Partially updates a managementResource.
     *
     * @param managementResource the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ManagementResource> partialUpdate(ManagementResource managementResource);

    /**
     * Get all the managementResources.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ManagementResource> findAll(Pageable pageable);

    /**
     * Get the "id" managementResource.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ManagementResource> findOne(Long id);

    /**
     * Delete the "id" managementResource.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
