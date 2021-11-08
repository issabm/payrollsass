package com.issa.payroll.repository;

import com.issa.payroll.domain.Plate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Plate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlateRepository extends JpaRepository<Plate, Long> {}
