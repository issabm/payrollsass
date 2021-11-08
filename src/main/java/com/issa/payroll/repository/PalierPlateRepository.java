package com.issa.payroll.repository;

import com.issa.payroll.domain.PalierPlate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PalierPlate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PalierPlateRepository extends JpaRepository<PalierPlate, Long> {}
