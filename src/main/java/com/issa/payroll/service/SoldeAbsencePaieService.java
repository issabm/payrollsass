package com.issa.payroll.service;

import com.issa.payroll.domain.SoldeAbsencePaie;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SoldeAbsencePaie}.
 */
public interface SoldeAbsencePaieService {
    /**
     * Save a soldeAbsencePaie.
     *
     * @param soldeAbsencePaie the entity to save.
     * @return the persisted entity.
     */
    SoldeAbsencePaie save(SoldeAbsencePaie soldeAbsencePaie);

    /**
     * Partially updates a soldeAbsencePaie.
     *
     * @param soldeAbsencePaie the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SoldeAbsencePaie> partialUpdate(SoldeAbsencePaie soldeAbsencePaie);

    /**
     * Get all the soldeAbsencePaies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SoldeAbsencePaie> findAll(Pageable pageable);

    /**
     * Get the "id" soldeAbsencePaie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SoldeAbsencePaie> findOne(Long id);

    /**
     * Delete the "id" soldeAbsencePaie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
