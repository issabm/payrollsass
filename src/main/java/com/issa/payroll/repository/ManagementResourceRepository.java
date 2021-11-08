package com.issa.payroll.repository;

import com.issa.payroll.domain.ManagementResource;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ManagementResource entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManagementResourceRepository extends JpaRepository<ManagementResource, Long> {}
