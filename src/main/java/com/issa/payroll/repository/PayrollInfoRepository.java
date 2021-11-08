package com.issa.payroll.repository;

import com.issa.payroll.domain.PayrollInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PayrollInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PayrollInfoRepository extends JpaRepository<PayrollInfo, Long> {}
