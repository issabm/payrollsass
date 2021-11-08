package com.issa.payroll.repository;

import com.issa.payroll.domain.Rebrique;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Rebrique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RebriqueRepository extends JpaRepository<Rebrique, Long> {}
