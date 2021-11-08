package com.issa.payroll.service;

import com.issa.payroll.domain.Carriere;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Carriere}.
 */
public interface CarriereService {
    /**
     * Save a carriere.
     *
     * @param carriere the entity to save.
     * @return the persisted entity.
     */
    Carriere save(Carriere carriere);

    /**
     * Partially updates a carriere.
     *
     * @param carriere the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Carriere> partialUpdate(Carriere carriere);

    /**
     * Get all the carrieres.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Carriere> findAll(Pageable pageable);

    /**
     * Get the "id" carriere.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Carriere> findOne(Long id);

    /**
     * Delete the "id" carriere.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
