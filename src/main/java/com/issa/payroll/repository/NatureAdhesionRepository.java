package com.issa.payroll.repository;

import com.issa.payroll.domain.NatureAdhesion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NatureAdhesion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureAdhesionRepository extends JpaRepository<NatureAdhesion, Long> {}
