package com.issa.payroll.service;

import com.issa.payroll.domain.NatureEligibilite;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link NatureEligibilite}.
 */
public interface NatureEligibiliteService {
    /**
     * Save a natureEligibilite.
     *
     * @param natureEligibilite the entity to save.
     * @return the persisted entity.
     */
    NatureEligibilite save(NatureEligibilite natureEligibilite);

    /**
     * Partially updates a natureEligibilite.
     *
     * @param natureEligibilite the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NatureEligibilite> partialUpdate(NatureEligibilite natureEligibilite);

    /**
     * Get all the natureEligibilites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NatureEligibilite> findAll(Pageable pageable);

    /**
     * Get the "id" natureEligibilite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NatureEligibilite> findOne(Long id);

    /**
     * Delete the "id" natureEligibilite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
