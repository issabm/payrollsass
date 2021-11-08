package com.issa.payroll.service;

import com.issa.payroll.domain.TypeIdentite;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TypeIdentite}.
 */
public interface TypeIdentiteService {
    /**
     * Save a typeIdentite.
     *
     * @param typeIdentite the entity to save.
     * @return the persisted entity.
     */
    TypeIdentite save(TypeIdentite typeIdentite);

    /**
     * Partially updates a typeIdentite.
     *
     * @param typeIdentite the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeIdentite> partialUpdate(TypeIdentite typeIdentite);

    /**
     * Get all the typeIdentites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeIdentite> findAll(Pageable pageable);

    /**
     * Get the "id" typeIdentite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeIdentite> findOne(Long id);

    /**
     * Delete the "id" typeIdentite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
