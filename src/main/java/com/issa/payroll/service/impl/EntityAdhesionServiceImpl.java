package com.issa.payroll.service.impl;

import com.issa.payroll.domain.EntityAdhesion;
import com.issa.payroll.repository.EntityAdhesionRepository;
import com.issa.payroll.service.EntityAdhesionService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EntityAdhesion}.
 */
@Service
@Transactional
public class EntityAdhesionServiceImpl implements EntityAdhesionService {

    private final Logger log = LoggerFactory.getLogger(EntityAdhesionServiceImpl.class);

    private final EntityAdhesionRepository entityAdhesionRepository;

    public EntityAdhesionServiceImpl(EntityAdhesionRepository entityAdhesionRepository) {
        this.entityAdhesionRepository = entityAdhesionRepository;
    }

    @Override
    public EntityAdhesion save(EntityAdhesion entityAdhesion) {
        log.debug("Request to save EntityAdhesion : {}", entityAdhesion);
        return entityAdhesionRepository.save(entityAdhesion);
    }

    @Override
    public Optional<EntityAdhesion> partialUpdate(EntityAdhesion entityAdhesion) {
        log.debug("Request to partially update EntityAdhesion : {}", entityAdhesion);

        return entityAdhesionRepository
            .findById(entityAdhesion.getId())
            .map(existingEntityAdhesion -> {
                if (entityAdhesion.getCode() != null) {
                    existingEntityAdhesion.setCode(entityAdhesion.getCode());
                }
                if (entityAdhesion.getLibEn() != null) {
                    existingEntityAdhesion.setLibEn(entityAdhesion.getLibEn());
                }
                if (entityAdhesion.getLibAr() != null) {
                    existingEntityAdhesion.setLibAr(entityAdhesion.getLibAr());
                }
                if (entityAdhesion.getLibFr() != null) {
                    existingEntityAdhesion.setLibFr(entityAdhesion.getLibFr());
                }
                if (entityAdhesion.getUtil() != null) {
                    existingEntityAdhesion.setUtil(entityAdhesion.getUtil());
                }
                if (entityAdhesion.getDateop() != null) {
                    existingEntityAdhesion.setDateop(entityAdhesion.getDateop());
                }
                if (entityAdhesion.getModifiedBy() != null) {
                    existingEntityAdhesion.setModifiedBy(entityAdhesion.getModifiedBy());
                }
                if (entityAdhesion.getOp() != null) {
                    existingEntityAdhesion.setOp(entityAdhesion.getOp());
                }
                if (entityAdhesion.getIsDeleted() != null) {
                    existingEntityAdhesion.setIsDeleted(entityAdhesion.getIsDeleted());
                }
                if (entityAdhesion.getCreatedDate() != null) {
                    existingEntityAdhesion.setCreatedDate(entityAdhesion.getCreatedDate());
                }
                if (entityAdhesion.getModifiedDate() != null) {
                    existingEntityAdhesion.setModifiedDate(entityAdhesion.getModifiedDate());
                }

                return existingEntityAdhesion;
            })
            .map(entityAdhesionRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EntityAdhesion> findAll(Pageable pageable) {
        log.debug("Request to get all EntityAdhesions");
        return entityAdhesionRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EntityAdhesion> findOne(Long id) {
        log.debug("Request to get EntityAdhesion : {}", id);
        return entityAdhesionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EntityAdhesion : {}", id);
        entityAdhesionRepository.deleteById(id);
    }
}
