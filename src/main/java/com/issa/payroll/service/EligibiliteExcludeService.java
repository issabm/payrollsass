package com.issa.payroll.service;

import com.issa.payroll.domain.EligibiliteExclude;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link EligibiliteExclude}.
 */
public interface EligibiliteExcludeService {
    /**
     * Save a eligibiliteExclude.
     *
     * @param eligibiliteExclude the entity to save.
     * @return the persisted entity.
     */
    EligibiliteExclude save(EligibiliteExclude eligibiliteExclude);

    /**
     * Partially updates a eligibiliteExclude.
     *
     * @param eligibiliteExclude the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EligibiliteExclude> partialUpdate(EligibiliteExclude eligibiliteExclude);

    /**
     * Get all the eligibiliteExcludes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EligibiliteExclude> findAll(Pageable pageable);

    /**
     * Get the "id" eligibiliteExclude.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EligibiliteExclude> findOne(Long id);

    /**
     * Delete the "id" eligibiliteExclude.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
