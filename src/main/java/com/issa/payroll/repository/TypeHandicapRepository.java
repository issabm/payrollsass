package com.issa.payroll.repository;

import com.issa.payroll.domain.TypeHandicap;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TypeHandicap entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeHandicapRepository extends JpaRepository<TypeHandicap, Long> {}
