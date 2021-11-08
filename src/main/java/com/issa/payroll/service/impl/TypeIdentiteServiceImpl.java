package com.issa.payroll.service.impl;

import com.issa.payroll.domain.TypeIdentite;
import com.issa.payroll.repository.TypeIdentiteRepository;
import com.issa.payroll.service.TypeIdentiteService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypeIdentite}.
 */
@Service
@Transactional
public class TypeIdentiteServiceImpl implements TypeIdentiteService {

    private final Logger log = LoggerFactory.getLogger(TypeIdentiteServiceImpl.class);

    private final TypeIdentiteRepository typeIdentiteRepository;

    public TypeIdentiteServiceImpl(TypeIdentiteRepository typeIdentiteRepository) {
        this.typeIdentiteRepository = typeIdentiteRepository;
    }

    @Override
    public TypeIdentite save(TypeIdentite typeIdentite) {
        log.debug("Request to save TypeIdentite : {}", typeIdentite);
        return typeIdentiteRepository.save(typeIdentite);
    }

    @Override
    public Optional<TypeIdentite> partialUpdate(TypeIdentite typeIdentite) {
        log.debug("Request to partially update TypeIdentite : {}", typeIdentite);

        return typeIdentiteRepository
            .findById(typeIdentite.getId())
            .map(existingTypeIdentite -> {
                if (typeIdentite.getCode() != null) {
                    existingTypeIdentite.setCode(typeIdentite.getCode());
                }
                if (typeIdentite.getLibAr() != null) {
                    existingTypeIdentite.setLibAr(typeIdentite.getLibAr());
                }
                if (typeIdentite.getLibEn() != null) {
                    existingTypeIdentite.setLibEn(typeIdentite.getLibEn());
                }
                if (typeIdentite.getUtil() != null) {
                    existingTypeIdentite.setUtil(typeIdentite.getUtil());
                }
                if (typeIdentite.getDateop() != null) {
                    existingTypeIdentite.setDateop(typeIdentite.getDateop());
                }
                if (typeIdentite.getModifiedBy() != null) {
                    existingTypeIdentite.setModifiedBy(typeIdentite.getModifiedBy());
                }
                if (typeIdentite.getOp() != null) {
                    existingTypeIdentite.setOp(typeIdentite.getOp());
                }
                if (typeIdentite.getIsDeleted() != null) {
                    existingTypeIdentite.setIsDeleted(typeIdentite.getIsDeleted());
                }
                if (typeIdentite.getCreatedDate() != null) {
                    existingTypeIdentite.setCreatedDate(typeIdentite.getCreatedDate());
                }
                if (typeIdentite.getModifiedDate() != null) {
                    existingTypeIdentite.setModifiedDate(typeIdentite.getModifiedDate());
                }

                return existingTypeIdentite;
            })
            .map(typeIdentiteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeIdentite> findAll(Pageable pageable) {
        log.debug("Request to get all TypeIdentites");
        return typeIdentiteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeIdentite> findOne(Long id) {
        log.debug("Request to get TypeIdentite : {}", id);
        return typeIdentiteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeIdentite : {}", id);
        typeIdentiteRepository.deleteById(id);
    }
}
