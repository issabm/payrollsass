package com.issa.payroll.repository;

import com.issa.payroll.domain.Conjoint;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Conjoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConjointRepository extends JpaRepository<Conjoint, Long> {}
