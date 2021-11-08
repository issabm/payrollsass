package com.issa.payroll.service;

import com.issa.payroll.domain.NatureMvtPaie;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link NatureMvtPaie}.
 */
public interface NatureMvtPaieService {
    /**
     * Save a natureMvtPaie.
     *
     * @param natureMvtPaie the entity to save.
     * @return the persisted entity.
     */
    NatureMvtPaie save(NatureMvtPaie natureMvtPaie);

    /**
     * Partially updates a natureMvtPaie.
     *
     * @param natureMvtPaie the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NatureMvtPaie> partialUpdate(NatureMvtPaie natureMvtPaie);

    /**
     * Get all the natureMvtPaies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NatureMvtPaie> findAll(Pageable pageable);

    /**
     * Get the "id" natureMvtPaie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NatureMvtPaie> findOne(Long id);

    /**
     * Delete the "id" natureMvtPaie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
