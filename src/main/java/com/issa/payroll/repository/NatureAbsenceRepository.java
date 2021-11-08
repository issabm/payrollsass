package com.issa.payroll.repository;

import com.issa.payroll.domain.NatureAbsence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NatureAbsence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureAbsenceRepository extends JpaRepository<NatureAbsence, Long> {}
