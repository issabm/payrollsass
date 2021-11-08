package com.issa.payroll.service;

import com.issa.payroll.domain.Affectation;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Affectation}.
 */
public interface AffectationService {
    /**
     * Save a affectation.
     *
     * @param affectation the entity to save.
     * @return the persisted entity.
     */
    Affectation save(Affectation affectation);

    /**
     * Partially updates a affectation.
     *
     * @param affectation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Affectation> partialUpdate(Affectation affectation);

    /**
     * Get all the affectations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Affectation> findAll(Pageable pageable);

    /**
     * Get the "id" affectation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Affectation> findOne(Long id);

    /**
     * Delete the "id" affectation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
