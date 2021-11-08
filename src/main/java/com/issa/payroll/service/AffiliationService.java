package com.issa.payroll.service;

import com.issa.payroll.domain.Affiliation;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Affiliation}.
 */
public interface AffiliationService {
    /**
     * Save a affiliation.
     *
     * @param affiliation the entity to save.
     * @return the persisted entity.
     */
    Affiliation save(Affiliation affiliation);

    /**
     * Partially updates a affiliation.
     *
     * @param affiliation the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Affiliation> partialUpdate(Affiliation affiliation);

    /**
     * Get all the affiliations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Affiliation> findAll(Pageable pageable);

    /**
     * Get the "id" affiliation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Affiliation> findOne(Long id);

    /**
     * Delete the "id" affiliation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
