package com.issa.payroll.service;

import com.issa.payroll.domain.NatureAbsence;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link NatureAbsence}.
 */
public interface NatureAbsenceService {
    /**
     * Save a natureAbsence.
     *
     * @param natureAbsence the entity to save.
     * @return the persisted entity.
     */
    NatureAbsence save(NatureAbsence natureAbsence);

    /**
     * Partially updates a natureAbsence.
     *
     * @param natureAbsence the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NatureAbsence> partialUpdate(NatureAbsence natureAbsence);

    /**
     * Get all the natureAbsences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NatureAbsence> findAll(Pageable pageable);

    /**
     * Get the "id" natureAbsence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NatureAbsence> findOne(Long id);

    /**
     * Delete the "id" natureAbsence.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
