package com.issa.payroll.service;

import com.issa.payroll.domain.ManagementResourceFun;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ManagementResourceFun}.
 */
public interface ManagementResourceFunService {
    /**
     * Save a managementResourceFun.
     *
     * @param managementResourceFun the entity to save.
     * @return the persisted entity.
     */
    ManagementResourceFun save(ManagementResourceFun managementResourceFun);

    /**
     * Partially updates a managementResourceFun.
     *
     * @param managementResourceFun the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ManagementResourceFun> partialUpdate(ManagementResourceFun managementResourceFun);

    /**
     * Get all the managementResourceFuns.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ManagementResourceFun> findAll(Pageable pageable);

    /**
     * Get the "id" managementResourceFun.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ManagementResourceFun> findOne(Long id);

    /**
     * Delete the "id" managementResourceFun.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
