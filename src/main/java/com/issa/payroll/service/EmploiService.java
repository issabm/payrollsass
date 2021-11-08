package com.issa.payroll.service;

import com.issa.payroll.domain.Emploi;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Emploi}.
 */
public interface EmploiService {
    /**
     * Save a emploi.
     *
     * @param emploi the entity to save.
     * @return the persisted entity.
     */
    Emploi save(Emploi emploi);

    /**
     * Partially updates a emploi.
     *
     * @param emploi the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Emploi> partialUpdate(Emploi emploi);

    /**
     * Get all the emplois.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Emploi> findAll(Pageable pageable);

    /**
     * Get the "id" emploi.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Emploi> findOne(Long id);

    /**
     * Delete the "id" emploi.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
