package com.issa.payroll.repository;

import com.issa.payroll.domain.NatureMvtPaie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NatureMvtPaie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureMvtPaieRepository extends JpaRepository<NatureMvtPaie, Long> {}
