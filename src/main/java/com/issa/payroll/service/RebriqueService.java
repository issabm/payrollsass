package com.issa.payroll.service;

import com.issa.payroll.domain.Rebrique;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Rebrique}.
 */
public interface RebriqueService {
    /**
     * Save a rebrique.
     *
     * @param rebrique the entity to save.
     * @return the persisted entity.
     */
    Rebrique save(Rebrique rebrique);

    /**
     * Partially updates a rebrique.
     *
     * @param rebrique the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Rebrique> partialUpdate(Rebrique rebrique);

    /**
     * Get all the rebriques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Rebrique> findAll(Pageable pageable);

    /**
     * Get the "id" rebrique.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Rebrique> findOne(Long id);

    /**
     * Delete the "id" rebrique.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
