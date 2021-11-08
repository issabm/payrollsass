package com.issa.payroll.service;

import com.issa.payroll.domain.PayrollInfo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PayrollInfo}.
 */
public interface PayrollInfoService {
    /**
     * Save a payrollInfo.
     *
     * @param payrollInfo the entity to save.
     * @return the persisted entity.
     */
    PayrollInfo save(PayrollInfo payrollInfo);

    /**
     * Partially updates a payrollInfo.
     *
     * @param payrollInfo the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PayrollInfo> partialUpdate(PayrollInfo payrollInfo);

    /**
     * Get all the payrollInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PayrollInfo> findAll(Pageable pageable);

    /**
     * Get the "id" payrollInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PayrollInfo> findOne(Long id);

    /**
     * Delete the "id" payrollInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
