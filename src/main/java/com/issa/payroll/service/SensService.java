package com.issa.payroll.service;

import com.issa.payroll.domain.Sens;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Sens}.
 */
public interface SensService {
    /**
     * Save a sens.
     *
     * @param sens the entity to save.
     * @return the persisted entity.
     */
    Sens save(Sens sens);

    /**
     * Partially updates a sens.
     *
     * @param sens the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Sens> partialUpdate(Sens sens);

    /**
     * Get all the sens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Sens> findAll(Pageable pageable);

    /**
     * Get the "id" sens.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Sens> findOne(Long id);

    /**
     * Delete the "id" sens.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
