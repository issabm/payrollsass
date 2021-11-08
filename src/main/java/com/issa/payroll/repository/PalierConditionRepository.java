package com.issa.payroll.repository;

import com.issa.payroll.domain.PalierCondition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PalierCondition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PalierConditionRepository extends JpaRepository<PalierCondition, Long> {}
