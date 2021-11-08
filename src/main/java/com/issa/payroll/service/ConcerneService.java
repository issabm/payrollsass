package com.issa.payroll.service;

import com.issa.payroll.domain.Concerne;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Concerne}.
 */
public interface ConcerneService {
    /**
     * Save a concerne.
     *
     * @param concerne the entity to save.
     * @return the persisted entity.
     */
    Concerne save(Concerne concerne);

    /**
     * Partially updates a concerne.
     *
     * @param concerne the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Concerne> partialUpdate(Concerne concerne);

    /**
     * Get all the concernes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Concerne> findAll(Pageable pageable);

    /**
     * Get the "id" concerne.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Concerne> findOne(Long id);

    /**
     * Delete the "id" concerne.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
