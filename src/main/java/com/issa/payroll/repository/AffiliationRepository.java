package com.issa.payroll.repository;

import com.issa.payroll.domain.Affiliation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Affiliation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {}
