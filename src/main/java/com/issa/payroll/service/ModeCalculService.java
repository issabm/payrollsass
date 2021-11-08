package com.issa.payroll.service;

import com.issa.payroll.domain.ModeCalcul;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ModeCalcul}.
 */
public interface ModeCalculService {
    /**
     * Save a modeCalcul.
     *
     * @param modeCalcul the entity to save.
     * @return the persisted entity.
     */
    ModeCalcul save(ModeCalcul modeCalcul);

    /**
     * Partially updates a modeCalcul.
     *
     * @param modeCalcul the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ModeCalcul> partialUpdate(ModeCalcul modeCalcul);

    /**
     * Get all the modeCalculs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ModeCalcul> findAll(Pageable pageable);

    /**
     * Get the "id" modeCalcul.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModeCalcul> findOne(Long id);

    /**
     * Delete the "id" modeCalcul.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
