package com.issa.payroll.service;

import com.issa.payroll.domain.Situation;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Situation}.
 */
public interface SituationService {
    /**
     * Save a situation.
     *
     * @param situation the entity to save.
     * @return the persisted entity.
     */
    Situation save(Situation situation);

    /**
     * Partially updates a situation.
     *
     * @param situation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Situation> partialUpdate(Situation situation);

    /**
     * Get all the situations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Situation> findAll(Pageable pageable);

    /**
     * Get the "id" situation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Situation> findOne(Long id);

    /**
     * Delete the "id" situation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
