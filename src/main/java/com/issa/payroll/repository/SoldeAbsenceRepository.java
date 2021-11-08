package com.issa.payroll.repository;

import com.issa.payroll.domain.SoldeAbsence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SoldeAbsence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoldeAbsenceRepository extends JpaRepository<SoldeAbsence, Long> {}
