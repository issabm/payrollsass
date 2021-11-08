package com.issa.payroll.repository;

import com.issa.payroll.domain.Sens;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Sens entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SensRepository extends JpaRepository<Sens, Long> {}
