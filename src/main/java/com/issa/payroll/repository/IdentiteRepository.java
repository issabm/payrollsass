package com.issa.payroll.repository;

import com.issa.payroll.domain.Identite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Identite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IdentiteRepository extends JpaRepository<Identite, Long> {}
