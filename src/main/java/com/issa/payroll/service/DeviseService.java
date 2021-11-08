package com.issa.payroll.service;

import com.issa.payroll.domain.Devise;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Devise}.
 */
public interface DeviseService {
    /**
     * Save a devise.
     *
     * @param devise the entity to save.
     * @return the persisted entity.
     */
    Devise save(Devise devise);

    /**
     * Partially updates a devise.
     *
     * @param devise the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Devise> partialUpdate(Devise devise);

    /**
     * Get all the devises.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Devise> findAll(Pageable pageable);

    /**
     * Get the "id" devise.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Devise> findOne(Long id);

    /**
     * Delete the "id" devise.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
