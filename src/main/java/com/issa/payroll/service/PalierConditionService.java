package com.issa.payroll.service;

import com.issa.payroll.domain.PalierCondition;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PalierCondition}.
 */
public interface PalierConditionService {
    /**
     * Save a palierCondition.
     *
     * @param palierCondition the entity to save.
     * @return the persisted entity.
     */
    PalierCondition save(PalierCondition palierCondition);

    /**
     * Partially updates a palierCondition.
     *
     * @param palierCondition the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PalierCondition> partialUpdate(PalierCondition palierCondition);

    /**
     * Get all the palierConditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PalierCondition> findAll(Pageable pageable);

    /**
     * Get the "id" palierCondition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PalierCondition> findOne(Long id);

    /**
     * Delete the "id" palierCondition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
