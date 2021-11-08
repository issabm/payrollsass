package com.issa.payroll.repository;

import com.issa.payroll.domain.ModeCalcul;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ModeCalcul entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModeCalculRepository extends JpaRepository<ModeCalcul, Long> {}
