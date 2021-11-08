package com.issa.payroll.service;

import com.issa.payroll.domain.SituationFamiliale;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SituationFamiliale}.
 */
public interface SituationFamilialeService {
    /**
     * Save a situationFamiliale.
     *
     * @param situationFamiliale the entity to save.
     * @return the persisted entity.
     */
    SituationFamiliale save(SituationFamiliale situationFamiliale);

    /**
     * Partially updates a situationFamiliale.
     *
     * @param situationFamiliale the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SituationFamiliale> partialUpdate(SituationFamiliale situationFamiliale);

    /**
     * Get all the situationFamiliales.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SituationFamiliale> findAll(Pageable pageable);

    /**
     * Get the "id" situationFamiliale.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SituationFamiliale> findOne(Long id);

    /**
     * Delete the "id" situationFamiliale.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
