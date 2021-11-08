package com.issa.payroll.repository;

import com.issa.payroll.domain.ManagementResourceFun;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ManagementResourceFun entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManagementResourceFunRepository extends JpaRepository<ManagementResourceFun, Long> {}
