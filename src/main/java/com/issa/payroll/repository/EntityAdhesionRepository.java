package com.issa.payroll.repository;

import com.issa.payroll.domain.EntityAdhesion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EntityAdhesion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntityAdhesionRepository extends JpaRepository<EntityAdhesion, Long> {}
