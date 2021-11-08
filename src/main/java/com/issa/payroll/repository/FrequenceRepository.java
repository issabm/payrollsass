package com.issa.payroll.repository;

import com.issa.payroll.domain.Frequence;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Frequence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FrequenceRepository extends JpaRepository<Frequence, Long> {}
