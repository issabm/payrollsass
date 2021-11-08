package com.issa.payroll.service;

import com.issa.payroll.domain.Enfant;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Enfant}.
 */
public interface EnfantService {
    /**
     * Save a enfant.
     *
     * @param enfant the entity to save.
     * @return the persisted entity.
     */
    Enfant save(Enfant enfant);

    /**
     * Partially updates a enfant.
     *
     * @param enfant the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Enfant> partialUpdate(Enfant enfant);

    /**
     * Get all the enfants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Enfant> findAll(Pageable pageable);

    /**
     * Get the "id" enfant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Enfant> findOne(Long id);

    /**
     * Delete the "id" enfant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
