package com.issa.payroll.service;

import com.issa.payroll.domain.NatureConfig;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link NatureConfig}.
 */
public interface NatureConfigService {
    /**
     * Save a natureConfig.
     *
     * @param natureConfig the entity to save.
     * @return the persisted entity.
     */
    NatureConfig save(NatureConfig natureConfig);

    /**
     * Partially updates a natureConfig.
     *
     * @param natureConfig the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NatureConfig> partialUpdate(NatureConfig natureConfig);

    /**
     * Get all the natureConfigs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NatureConfig> findAll(Pageable pageable);

    /**
     * Get the "id" natureConfig.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NatureConfig> findOne(Long id);

    /**
     * Delete the "id" natureConfig.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
