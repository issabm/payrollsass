package com.issa.payroll.service;

import com.issa.payroll.domain.ModeInput;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ModeInput}.
 */
public interface ModeInputService {
    /**
     * Save a modeInput.
     *
     * @param modeInput the entity to save.
     * @return the persisted entity.
     */
    ModeInput save(ModeInput modeInput);

    /**
     * Partially updates a modeInput.
     *
     * @param modeInput the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ModeInput> partialUpdate(ModeInput modeInput);

    /**
     * Get all the modeInputs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ModeInput> findAll(Pageable pageable);

    /**
     * Get the "id" modeInput.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ModeInput> findOne(Long id);

    /**
     * Delete the "id" modeInput.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
