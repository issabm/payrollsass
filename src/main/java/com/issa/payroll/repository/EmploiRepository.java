package com.issa.payroll.repository;

import com.issa.payroll.domain.Emploi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Emploi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmploiRepository extends JpaRepository<Emploi, Long> {}
