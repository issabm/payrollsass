package com.issa.payroll.service;

import com.issa.payroll.domain.SousTypeContrat;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SousTypeContrat}.
 */
public interface SousTypeContratService {
    /**
     * Save a sousTypeContrat.
     *
     * @param sousTypeContrat the entity to save.
     * @return the persisted entity.
     */
    SousTypeContrat save(SousTypeContrat sousTypeContrat);

    /**
     * Partially updates a sousTypeContrat.
     *
     * @param sousTypeContrat the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SousTypeContrat> partialUpdate(SousTypeContrat sousTypeContrat);

    /**
     * Get all the sousTypeContrats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SousTypeContrat> findAll(Pageable pageable);

    /**
     * Get the "id" sousTypeContrat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SousTypeContrat> findOne(Long id);

    /**
     * Delete the "id" sousTypeContrat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
