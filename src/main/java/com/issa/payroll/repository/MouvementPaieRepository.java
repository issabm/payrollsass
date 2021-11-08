package com.issa.payroll.repository;

import com.issa.payroll.domain.MouvementPaie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the MouvementPaie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MouvementPaieRepository extends JpaRepository<MouvementPaie, Long> {}
