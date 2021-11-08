package com.issa.payroll.service;

import com.issa.payroll.domain.MouvementPaie;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link MouvementPaie}.
 */
public interface MouvementPaieService {
    /**
     * Save a mouvementPaie.
     *
     * @param mouvementPaie the entity to save.
     * @return the persisted entity.
     */
    MouvementPaie save(MouvementPaie mouvementPaie);

    /**
     * Partially updates a mouvementPaie.
     *
     * @param mouvementPaie the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MouvementPaie> partialUpdate(MouvementPaie mouvementPaie);

    /**
     * Get all the mouvementPaies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MouvementPaie> findAll(Pageable pageable);

    /**
     * Get the "id" mouvementPaie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MouvementPaie> findOne(Long id);

    /**
     * Delete the "id" mouvementPaie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
