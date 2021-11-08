package com.issa.payroll.service;

import com.issa.payroll.domain.PlateInfo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link PlateInfo}.
 */
public interface PlateInfoService {
    /**
     * Save a plateInfo.
     *
     * @param plateInfo the entity to save.
     * @return the persisted entity.
     */
    PlateInfo save(PlateInfo plateInfo);

    /**
     * Partially updates a plateInfo.
     *
     * @param plateInfo the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlateInfo> partialUpdate(PlateInfo plateInfo);

    /**
     * Get all the plateInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlateInfo> findAll(Pageable pageable);

    /**
     * Get the "id" plateInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlateInfo> findOne(Long id);

    /**
     * Delete the "id" plateInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
