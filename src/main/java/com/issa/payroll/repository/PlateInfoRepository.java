package com.issa.payroll.repository;

import com.issa.payroll.domain.PlateInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PlateInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlateInfoRepository extends JpaRepository<PlateInfo, Long> {}
