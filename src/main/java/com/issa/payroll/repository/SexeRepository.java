package com.issa.payroll.repository;

import com.issa.payroll.domain.Sexe;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Sexe entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SexeRepository extends JpaRepository<Sexe, Long> {}
