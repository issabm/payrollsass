package com.issa.payroll.service;

import com.issa.payroll.domain.Frequence;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Frequence}.
 */
public interface FrequenceService {
    /**
     * Save a frequence.
     *
     * @param frequence the entity to save.
     * @return the persisted entity.
     */
    Frequence save(Frequence frequence);

    /**
     * Partially updates a frequence.
     *
     * @param frequence the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Frequence> partialUpdate(Frequence frequence);

    /**
     * Get all the frequences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Frequence> findAll(Pageable pageable);

    /**
     * Get the "id" frequence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Frequence> findOne(Long id);

    /**
     * Delete the "id" frequence.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
