package com.issa.payroll.service;

import com.issa.payroll.domain.SoldeAbsence;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SoldeAbsence}.
 */
public interface SoldeAbsenceService {
    /**
     * Save a soldeAbsence.
     *
     * @param soldeAbsence the entity to save.
     * @return the persisted entity.
     */
    SoldeAbsence save(SoldeAbsence soldeAbsence);

    /**
     * Partially updates a soldeAbsence.
     *
     * @param soldeAbsence the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SoldeAbsence> partialUpdate(SoldeAbsence soldeAbsence);

    /**
     * Get all the soldeAbsences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SoldeAbsence> findAll(Pageable pageable);

    /**
     * Get the "id" soldeAbsence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SoldeAbsence> findOne(Long id);

    /**
     * Delete the "id" soldeAbsence.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
