package com.issa.payroll.repository;

import com.issa.payroll.domain.SoldeAbsencePaie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SoldeAbsencePaie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SoldeAbsencePaieRepository extends JpaRepository<SoldeAbsencePaie, Long> {}
