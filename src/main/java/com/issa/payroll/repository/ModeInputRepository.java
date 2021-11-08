package com.issa.payroll.repository;

import com.issa.payroll.domain.ModeInput;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ModeInput entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModeInputRepository extends JpaRepository<ModeInput, Long> {}
