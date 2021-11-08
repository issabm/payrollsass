package com.issa.payroll.service;

import com.issa.payroll.domain.Famille;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Famille}.
 */
public interface FamilleService {
    /**
     * Save a famille.
     *
     * @param famille the entity to save.
     * @return the persisted entity.
     */
    Famille save(Famille famille);

    /**
     * Partially updates a famille.
     *
     * @param famille the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Famille> partialUpdate(Famille famille);

    /**
     * Get all the familles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Famille> findAll(Pageable pageable);

    /**
     * Get the "id" famille.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Famille> findOne(Long id);

    /**
     * Delete the "id" famille.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
