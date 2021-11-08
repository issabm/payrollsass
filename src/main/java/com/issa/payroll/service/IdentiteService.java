package com.issa.payroll.service;

import com.issa.payroll.domain.Identite;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Identite}.
 */
public interface IdentiteService {
    /**
     * Save a identite.
     *
     * @param identite the entity to save.
     * @return the persisted entity.
     */
    Identite save(Identite identite);

    /**
     * Partially updates a identite.
     *
     * @param identite the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Identite> partialUpdate(Identite identite);

    /**
     * Get all the identites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Identite> findAll(Pageable pageable);

    /**
     * Get the "id" identite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Identite> findOne(Long id);

    /**
     * Delete the "id" identite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
