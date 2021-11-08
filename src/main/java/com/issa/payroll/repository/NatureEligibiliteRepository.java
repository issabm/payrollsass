package com.issa.payroll.repository;

import com.issa.payroll.domain.NatureEligibilite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NatureEligibilite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureEligibiliteRepository extends JpaRepository<NatureEligibilite, Long> {}
