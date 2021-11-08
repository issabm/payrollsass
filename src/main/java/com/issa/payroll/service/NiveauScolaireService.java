package com.issa.payroll.service;

import com.issa.payroll.domain.NiveauScolaire;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link NiveauScolaire}.
 */
public interface NiveauScolaireService {
    /**
     * Save a niveauScolaire.
     *
     * @param niveauScolaire the entity to save.
     * @return the persisted entity.
     */
    NiveauScolaire save(NiveauScolaire niveauScolaire);

    /**
     * Partially updates a niveauScolaire.
     *
     * @param niveauScolaire the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NiveauScolaire> partialUpdate(NiveauScolaire niveauScolaire);

    /**
     * Get all the niveauScolaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NiveauScolaire> findAll(Pageable pageable);

    /**
     * Get the "id" niveauScolaire.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NiveauScolaire> findOne(Long id);

    /**
     * Delete the "id" niveauScolaire.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
