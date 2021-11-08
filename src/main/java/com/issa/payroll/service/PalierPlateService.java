package com.issa.payroll.service;

import com.issa.payroll.domain.PalierPlate;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PalierPlate}.
 */
public interface PalierPlateService {
    /**
     * Save a palierPlate.
     *
     * @param palierPlate the entity to save.
     * @return the persisted entity.
     */
    PalierPlate save(PalierPlate palierPlate);

    /**
     * Partially updates a palierPlate.
     *
     * @param palierPlate the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PalierPlate> partialUpdate(PalierPlate palierPlate);

    /**
     * Get all the palierPlates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PalierPlate> findAll(Pageable pageable);

    /**
     * Get the "id" palierPlate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PalierPlate> findOne(Long id);

    /**
     * Delete the "id" palierPlate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
