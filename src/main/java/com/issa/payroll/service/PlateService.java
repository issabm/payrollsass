package com.issa.payroll.service;

import com.issa.payroll.domain.Plate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Plate}.
 */
public interface PlateService {
    /**
     * Save a plate.
     *
     * @param plate the entity to save.
     * @return the persisted entity.
     */
    Plate save(Plate plate);

    /**
     * Partially updates a plate.
     *
     * @param plate the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Plate> partialUpdate(Plate plate);

    /**
     * Get all the plates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Plate> findAll(Pageable pageable);

    /**
     * Get the "id" plate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Plate> findOne(Long id);

    /**
     * Delete the "id" plate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
