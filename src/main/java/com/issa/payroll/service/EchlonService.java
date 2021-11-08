package com.issa.payroll.service;

import com.issa.payroll.domain.Echlon;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Echlon}.
 */
public interface EchlonService {
    /**
     * Save a echlon.
     *
     * @param echlon the entity to save.
     * @return the persisted entity.
     */
    Echlon save(Echlon echlon);

    /**
     * Partially updates a echlon.
     *
     * @param echlon the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Echlon> partialUpdate(Echlon echlon);

    /**
     * Get all the echlons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Echlon> findAll(Pageable pageable);

    /**
     * Get the "id" echlon.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Echlon> findOne(Long id);

    /**
     * Delete the "id" echlon.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
