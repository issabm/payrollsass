package com.issa.payroll.service;

import com.issa.payroll.domain.TypeHandicap;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TypeHandicap}.
 */
public interface TypeHandicapService {
    /**
     * Save a typeHandicap.
     *
     * @param typeHandicap the entity to save.
     * @return the persisted entity.
     */
    TypeHandicap save(TypeHandicap typeHandicap);

    /**
     * Partially updates a typeHandicap.
     *
     * @param typeHandicap the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TypeHandicap> partialUpdate(TypeHandicap typeHandicap);

    /**
     * Get all the typeHandicaps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TypeHandicap> findAll(Pageable pageable);

    /**
     * Get the "id" typeHandicap.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TypeHandicap> findOne(Long id);

    /**
     * Delete the "id" typeHandicap.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
