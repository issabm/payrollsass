package com.issa.payroll.service;

import com.issa.payroll.domain.TargetEligible;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TargetEligible}.
 */
public interface TargetEligibleService {
    /**
     * Save a targetEligible.
     *
     * @param targetEligible the entity to save.
     * @return the persisted entity.
     */
    TargetEligible save(TargetEligible targetEligible);

    /**
     * Partially updates a targetEligible.
     *
     * @param targetEligible the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TargetEligible> partialUpdate(TargetEligible targetEligible);

    /**
     * Get all the targetEligibles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TargetEligible> findAll(Pageable pageable);

    /**
     * Get the "id" targetEligible.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TargetEligible> findOne(Long id);

    /**
     * Delete the "id" targetEligible.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
