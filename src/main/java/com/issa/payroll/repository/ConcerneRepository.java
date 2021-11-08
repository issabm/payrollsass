package com.issa.payroll.repository;

import com.issa.payroll.domain.Concerne;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Concerne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConcerneRepository extends JpaRepository<Concerne, Long> {}
