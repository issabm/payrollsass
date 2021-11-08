package com.issa.payroll.repository;

import com.issa.payroll.domain.NatureConfig;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NatureConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureConfigRepository extends JpaRepository<NatureConfig, Long> {}
