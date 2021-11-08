package com.issa.payroll.repository;

import com.issa.payroll.domain.Carriere;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Carriere entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarriereRepository extends JpaRepository<Carriere, Long> {}
