package com.issa.payroll.repository;

import com.issa.payroll.domain.SousTypeContrat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SousTypeContrat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SousTypeContratRepository extends JpaRepository<SousTypeContrat, Long> {}
