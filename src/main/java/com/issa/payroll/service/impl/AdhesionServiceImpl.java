package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Adhesion;
import com.issa.payroll.repository.AdhesionRepository;
import com.issa.payroll.service.AdhesionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Adhesion}.
 */
@Service
@Transactional
public class AdhesionServiceImpl implements AdhesionService {

    private final Logger log = LoggerFactory.getLogger(AdhesionServiceImpl.class);

    private final AdhesionRepository adhesionRepository;

    public AdhesionServiceImpl(AdhesionRepository adhesionRepository) {
        this.adhesionRepository = adhesionRepository;
    }

    @Override
    public Adhesion save(Adhesion adhesion) {
        log.debug("Request to save Adhesion : {}", adhesion);
        return adhesionRepository.save(adhesion);
    }

    @Override
    public Optional<Adhesion> partialUpdate(Adhesion adhesion) {
        log.debug("Request to partially update Adhesion : {}", adhesion);

        return adhesionRepository
            .findById(adhesion.getId())
            .map(existingAdhesion -> {
                if (adhesion.getDateDebut() != null) {
                    existingAdhesion.setDateDebut(adhesion.getDateDebut());
                }
                if (adhesion.getDateFin() != null) {
                    existingAdhesion.setDateFin(adhesion.getDateFin());
                }
                if (adhesion.getUtil() != null) {
                    existingAdhesion.setUtil(adhesion.getUtil());
                }
                if (adhesion.getDateop() != null) {
                    existingAdhesion.setDateop(adhesion.getDateop());
                }
                if (adhesion.getModifiedBy() != null) {
                    existingAdhesion.setModifiedBy(adhesion.getModifiedBy());
                }
                if (adhesion.getOp() != null) {
                    existingAdhesion.setOp(adhesion.getOp());
                }
                if (adhesion.getIsDeleted() != null) {
                    existingAdhesion.setIsDeleted(adhesion.getIsDeleted());
                }
                if (adhesion.getCreatedDate() != null) {
                    existingAdhesion.setCreatedDate(adhesion.getCreatedDate());
                }
                if (adhesion.getModifiedDate() != null) {
                    existingAdhesion.setModifiedDate(adhesion.getModifiedDate());
                }

                return existingAdhesion;
            })
            .map(adhesionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Adhesion> findAll(Pageable pageable) {
        log.debug("Request to get all Adhesions");
        return adhesionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Adhesion> findOne(Long id) {
        log.debug("Request to get Adhesion : {}", id);
        return adhesionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Adhesion : {}", id);
        adhesionRepository.deleteById(id);
    }
}
