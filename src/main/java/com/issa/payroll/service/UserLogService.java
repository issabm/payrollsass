package com.issa.payroll.service;

import com.issa.payroll.domain.UserLog;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link UserLog}.
 */
public interface UserLogService {
    /**
     * Save a userLog.
     *
     * @param userLog the entity to save.
     * @return the persisted entity.
     */
    UserLog save(UserLog userLog);

    /**
     * Partially updates a userLog.
     *
     * @param userLog the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserLog> partialUpdate(UserLog userLog);

    /**
     * Get all the userLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserLog> findAll(Pageable pageable);

    /**
     * Get the "id" userLog.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserLog> findOne(Long id);

    /**
     * Delete the "id" userLog.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
