package com.issa.payroll.service;

import com.issa.payroll.domain.Payroll;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Payroll}.
 */
public interface PayrollService {
    /**
     * Save a payroll.
     *
     * @param payroll the entity to save.
     * @return the persisted entity.
     */
    Payroll save(Payroll payroll);

    /**
     * Partially updates a payroll.
     *
     * @param payroll the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Payroll> partialUpdate(Payroll payroll);

    /**
     * Get all the payrolls.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Payroll> findAll(Pageable pageable);

    /**
     * Get the "id" payroll.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Payroll> findOne(Long id);

    /**
     * Delete the "id" payroll.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
