package com.issa.payroll.service;

import com.issa.payroll.domain.EntityAdhesion;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link EntityAdhesion}.
 */
public interface EntityAdhesionService {
    /**
     * Save a entityAdhesion.
     *
     * @param entityAdhesion the entity to save.
     * @return the persisted entity.
     */
    EntityAdhesion save(EntityAdhesion entityAdhesion);

    /**
     * Partially updates a entityAdhesion.
     *
     * @param entityAdhesion the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EntityAdhesion> partialUpdate(EntityAdhesion entityAdhesion);

    /**
     * Get all the entityAdhesions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EntityAdhesion> findAll(Pageable pageable);

    /**
     * Get the "id" entityAdhesion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EntityAdhesion> findOne(Long id);

    /**
     * Delete the "id" entityAdhesion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
