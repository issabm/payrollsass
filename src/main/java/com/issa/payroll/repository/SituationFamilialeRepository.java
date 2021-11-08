package com.issa.payroll.repository;

import com.issa.payroll.domain.SituationFamiliale;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SituationFamiliale entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SituationFamilialeRepository extends JpaRepository<SituationFamiliale, Long> {}
