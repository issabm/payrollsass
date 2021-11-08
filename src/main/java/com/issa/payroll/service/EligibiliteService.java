package com.issa.payroll.service;

import com.issa.payroll.domain.Eligibilite;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Eligibilite}.
 */
public interface EligibiliteService {
    /**
     * Save a eligibilite.
     *
     * @param eligibilite the entity to save.
     * @return the persisted entity.
     */
    Eligibilite save(Eligibilite eligibilite);

    /**
     * Partially updates a eligibilite.
     *
     * @param eligibilite the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Eligibilite> partialUpdate(Eligibilite eligibilite);

    /**
     * Get all the eligibilites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Eligibilite> findAll(Pageable pageable);

    /**
     * Get the "id" eligibilite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Eligibilite> findOne(Long id);

    /**
     * Delete the "id" eligibilite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
