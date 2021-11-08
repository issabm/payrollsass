package com.issa.payroll.repository;

import com.issa.payroll.domain.DemandeCalculPaie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DemandeCalculPaie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DemandeCalculPaieRepository extends JpaRepository<DemandeCalculPaie, Long> {}
