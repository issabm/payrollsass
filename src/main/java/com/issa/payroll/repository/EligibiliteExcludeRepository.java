package com.issa.payroll.repository;

import com.issa.payroll.domain.EligibiliteExclude;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EligibiliteExclude entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EligibiliteExcludeRepository extends JpaRepository<EligibiliteExclude, Long> {}
