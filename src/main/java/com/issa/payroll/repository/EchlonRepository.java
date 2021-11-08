package com.issa.payroll.repository;

import com.issa.payroll.domain.Echlon;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Echlon entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EchlonRepository extends JpaRepository<Echlon, Long> {}
