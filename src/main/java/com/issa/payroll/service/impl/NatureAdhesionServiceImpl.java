package com.issa.payroll.service.impl;

import com.issa.payroll.domain.NatureAdhesion;
import com.issa.payroll.repository.NatureAdhesionRepository;
import com.issa.payroll.service.NatureAdhesionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NatureAdhesion}.
 */
@Service
@Transactional
public class NatureAdhesionServiceImpl implements NatureAdhesionService {

    private final Logger log = LoggerFactory.getLogger(NatureAdhesionServiceImpl.class);

    private final NatureAdhesionRepository natureAdhesionRepository;

    public NatureAdhesionServiceImpl(NatureAdhesionRepository natureAdhesionRepository) {
        this.natureAdhesionRepository = natureAdhesionRepository;
    }

    @Override
    public NatureAdhesion save(NatureAdhesion natureAdhesion) {
        log.debug("Request to save NatureAdhesion : {}", natureAdhesion);
        return natureAdhesionRepository.save(natureAdhesion);
    }

    @Override
    public Optional<NatureAdhesion> partialUpdate(NatureAdhesion natureAdhesion) {
        log.debug("Request to partially update NatureAdhesion : {}", natureAdhesion);

        return natureAdhesionRepository
            .findById(natureAdhesion.getId())
            .map(existingNatureAdhesion -> {
                if (natureAdhesion.getCode() != null) {
                    existingNatureAdhesion.setCode(natureAdhesion.getCode());
                }
                if (natureAdhesion.getLibAr() != null) {
                    existingNatureAdhesion.setLibAr(natureAdhesion.getLibAr());
                }
                if (natureAdhesion.getLibEn() != null) {
                    existingNatureAdhesion.setLibEn(natureAdhesion.getLibEn());
                }
                if (natureAdhesion.getUtil() != null) {
                    existingNatureAdhesion.setUtil(natureAdhesion.getUtil());
                }
                if (natureAdhesion.getDateop() != null) {
                    existingNatureAdhesion.setDateop(natureAdhesion.getDateop());
                }
                if (natureAdhesion.getModifiedBy() != null) {
                    existingNatureAdhesion.setModifiedBy(natureAdhesion.getModifiedBy());
                }
                if (natureAdhesion.getOp() != null) {
                    existingNatureAdhesion.setOp(natureAdhesion.getOp());
                }
                if (natureAdhesion.getIsDeleted() != null) {
                    existingNatureAdhesion.setIsDeleted(natureAdhesion.getIsDeleted());
                }
                if (natureAdhesion.getCreatedDate() != null) {
                    existingNatureAdhesion.setCreatedDate(natureAdhesion.getCreatedDate());
                }
                if (natureAdhesion.getModifiedDate() != null) {
                    existingNatureAdhesion.setModifiedDate(natureAdhesion.getModifiedDate());
                }

                return existingNatureAdhesion;
            })
            .map(natureAdhesionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NatureAdhesion> findAll(Pageable pageable) {
        log.debug("Request to get all NatureAdhesions");
        return natureAdhesionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NatureAdhesion> findOne(Long id) {
        log.debug("Request to get NatureAdhesion : {}", id);
        return natureAdhesionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NatureAdhesion : {}", id);
        natureAdhesionRepository.deleteById(id);
    }
}
