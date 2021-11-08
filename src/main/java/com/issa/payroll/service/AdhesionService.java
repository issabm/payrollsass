package com.issa.payroll.service;

import com.issa.payroll.domain.Adhesion;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Adhesion}.
 */
public interface AdhesionService {
    /**
     * Save a adhesion.
     *
     * @param adhesion the entity to save.
     * @return the persisted entity.
     */
    Adhesion save(Adhesion adhesion);

    /**
     * Partially updates a adhesion.
     *
     * @param adhesion the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Adhesion> partialUpdate(Adhesion adhesion);

    /**
     * Get all the adhesions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Adhesion> findAll(Pageable pageable);

    /**
     * Get the "id" adhesion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Adhesion> findOne(Long id);

    /**
     * Delete the "id" adhesion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
