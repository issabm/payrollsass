package com.issa.payroll.service;

import com.issa.payroll.domain.DemandeCalculPaie;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link DemandeCalculPaie}.
 */
public interface DemandeCalculPaieService {
    /**
     * Save a demandeCalculPaie.
     *
     * @param demandeCalculPaie the entity to save.
     * @return the persisted entity.
     */
    DemandeCalculPaie save(DemandeCalculPaie demandeCalculPaie);

    /**
     * Partially updates a demandeCalculPaie.
     *
     * @param demandeCalculPaie the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DemandeCalculPaie> partialUpdate(DemandeCalculPaie demandeCalculPaie);

    /**
     * Get all the demandeCalculPaies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DemandeCalculPaie> findAll(Pageable pageable);

    /**
     * Get the "id" demandeCalculPaie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DemandeCalculPaie> findOne(Long id);

    /**
     * Delete the "id" demandeCalculPaie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
