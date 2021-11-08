package com.issa.payroll.repository;

import com.issa.payroll.domain.TargetEligible;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TargetEligible entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TargetEligibleRepository extends JpaRepository<TargetEligible, Long> {}
