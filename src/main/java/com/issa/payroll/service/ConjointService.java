package com.issa.payroll.service;

import com.issa.payroll.domain.Conjoint;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Conjoint}.
 */
public interface ConjointService {
    /**
     * Save a conjoint.
     *
     * @param conjoint the entity to save.
     * @return the persisted entity.
     */
    Conjoint save(Conjoint conjoint);

    /**
     * Partially updates a conjoint.
     *
     * @param conjoint the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Conjoint> partialUpdate(Conjoint conjoint);

    /**
     * Get all the conjoints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Conjoint> findAll(Pageable pageable);

    /**
     * Get the "id" conjoint.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Conjoint> findOne(Long id);

    /**
     * Delete the "id" conjoint.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
