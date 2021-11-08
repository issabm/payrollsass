package com.issa.payroll.service;

import com.issa.payroll.domain.Sexe;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Sexe}.
 */
public interface SexeService {
    /**
     * Save a sexe.
     *
     * @param sexe the entity to save.
     * @return the persisted entity.
     */
    Sexe save(Sexe sexe);

    /**
     * Partially updates a sexe.
     *
     * @param sexe the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Sexe> partialUpdate(Sexe sexe);

    /**
     * Get all the sexes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Sexe> findAll(Pageable pageable);

    /**
     * Get the "id" sexe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Sexe> findOne(Long id);

    /**
     * Delete the "id" sexe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
