package com.issa.payroll.service.impl;

import com.issa.payroll.domain.TypeContrat;
import com.issa.payroll.repository.TypeContratRepository;
import com.issa.payroll.service.TypeContratService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypeContrat}.
 */
@Service
@Transactional
public class TypeContratServiceImpl implements TypeContratService {

    private final Logger log = LoggerFactory.getLogger(TypeContratServiceImpl.class);

    private final TypeContratRepository typeContratRepository;

    public TypeContratServiceImpl(TypeContratRepository typeContratRepository) {
        this.typeContratRepository = typeContratRepository;
    }

    @Override
    public TypeContrat save(TypeContrat typeContrat) {
        log.debug("Request to save TypeContrat : {}", typeContrat);
        return typeContratRepository.save(typeContrat);
    }

    @Override
    public Optional<TypeContrat> partialUpdate(TypeContrat typeContrat) {
        log.debug("Request to partially update TypeContrat : {}", typeContrat);

        return typeContratRepository
            .findById(typeContrat.getId())
            .map(existingTypeContrat -> {
                if (typeContrat.getCode() != null) {
                    existingTypeContrat.setCode(typeContrat.getCode());
                }
                if (typeContrat.getLibAr() != null) {
                    existingTypeContrat.setLibAr(typeContrat.getLibAr());
                }
                if (typeContrat.getLibEn() != null) {
                    existingTypeContrat.setLibEn(typeContrat.getLibEn());
                }
                if (typeContrat.getUtil() != null) {
                    existingTypeContrat.setUtil(typeContrat.getUtil());
                }
                if (typeContrat.getDateop() != null) {
                    existingTypeContrat.setDateop(typeContrat.getDateop());
                }
                if (typeContrat.getModifiedBy() != null) {
                    existingTypeContrat.setModifiedBy(typeContrat.getModifiedBy());
                }
                if (typeContrat.getOp() != null) {
                    existingTypeContrat.setOp(typeContrat.getOp());
                }
                if (typeContrat.getIsDeleted() != null) {
                    existingTypeContrat.setIsDeleted(typeContrat.getIsDeleted());
                }
                if (typeContrat.getCreatedDate() != null) {
                    existingTypeContrat.setCreatedDate(typeContrat.getCreatedDate());
                }
                if (typeContrat.getModifiedDate() != null) {
                    existingTypeContrat.setModifiedDate(typeContrat.getModifiedDate());
                }

                return existingTypeContrat;
            })
            .map(typeContratRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeContrat> findAll(Pageable pageable) {
        log.debug("Request to get all TypeContrats");
        return typeContratRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeContrat> findOne(Long id) {
        log.debug("Request to get TypeContrat : {}", id);
        return typeContratRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeContrat : {}", id);
        typeContratRepository.deleteById(id);
    }
}
