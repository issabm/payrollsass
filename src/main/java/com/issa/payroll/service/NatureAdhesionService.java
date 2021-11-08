package com.issa.payroll.service;

import com.issa.payroll.domain.NatureAdhesion;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link NatureAdhesion}.
 */
public interface NatureAdhesionService {
    /**
     * Save a natureAdhesion.
     *
     * @param natureAdhesion the entity to save.
     * @return the persisted entity.
     */
    NatureAdhesion save(NatureAdhesion natureAdhesion);

    /**
     * Partially updates a natureAdhesion.
     *
     * @param natureAdhesion the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NatureAdhesion> partialUpdate(NatureAdhesion natureAdhesion);

    /**
     * Get all the natureAdhesions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NatureAdhesion> findAll(Pageable pageable);

    /**
     * Get the "id" natureAdhesion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NatureAdhesion> findOne(Long id);

    /**
     * Delete the "id" natureAdhesion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
