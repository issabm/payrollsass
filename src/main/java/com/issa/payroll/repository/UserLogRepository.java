package com.issa.payroll.repository;

import com.issa.payroll.domain.UserLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Long> {}
