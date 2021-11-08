package com.issa.payroll.repository;

import com.issa.payroll.domain.NiveauScolaire;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the NiveauScolaire entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NiveauScolaireRepository extends JpaRepository<NiveauScolaire, Long> {}
