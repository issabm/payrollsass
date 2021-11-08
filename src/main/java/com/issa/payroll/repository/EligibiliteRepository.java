package com.issa.payroll.repository;

import com.issa.payroll.domain.Eligibilite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Eligibilite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EligibiliteRepository extends JpaRepository<Eligibilite, Long> {}
