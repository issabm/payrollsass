package com.issa.payroll.repository;

import com.issa.payroll.domain.TypeIdentite;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TypeIdentite entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeIdentiteRepository extends JpaRepository<TypeIdentite, Long> {}
