package com.issa.payroll.service.impl;

import com.issa.payroll.domain.TypeHandicap;
import com.issa.payroll.repository.TypeHandicapRepository;
import com.issa.payroll.service.TypeHandicapService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TypeHandicap}.
 */
@Service
@Transactional
public class TypeHandicapServiceImpl implements TypeHandicapService {

    private final Logger log = LoggerFactory.getLogger(TypeHandicapServiceImpl.class);

    private final TypeHandicapRepository typeHandicapRepository;

    public TypeHandicapServiceImpl(TypeHandicapRepository typeHandicapRepository) {
        this.typeHandicapRepository = typeHandicapRepository;
    }

    @Override
    public TypeHandicap save(TypeHandicap typeHandicap) {
        log.debug("Request to save TypeHandicap : {}", typeHandicap);
        return typeHandicapRepository.save(typeHandicap);
    }

    @Override
    public Optional<TypeHandicap> partialUpdate(TypeHandicap typeHandicap) {
        log.debug("Request to partially update TypeHandicap : {}", typeHandicap);

        return typeHandicapRepository
            .findById(typeHandicap.getId())
            .map(existingTypeHandicap -> {
                if (typeHandicap.getCode() != null) {
                    existingTypeHandicap.setCode(typeHandicap.getCode());
                }
                if (typeHandicap.getLibAr() != null) {
                    existingTypeHandicap.setLibAr(typeHandicap.getLibAr());
                }
                if (typeHandicap.getLibEn() != null) {
                    existingTypeHandicap.setLibEn(typeHandicap.getLibEn());
                }
                if (typeHandicap.getUtil() != null) {
                    existingTypeHandicap.setUtil(typeHandicap.getUtil());
                }
                if (typeHandicap.getDateop() != null) {
                    existingTypeHandicap.setDateop(typeHandicap.getDateop());
                }
                if (typeHandicap.getModifiedBy() != null) {
                    existingTypeHandicap.setModifiedBy(typeHandicap.getModifiedBy());
                }
                if (typeHandicap.getOp() != null) {
                    existingTypeHandicap.setOp(typeHandicap.getOp());
                }
                if (typeHandicap.getIsDeleted() != null) {
                    existingTypeHandicap.setIsDeleted(typeHandicap.getIsDeleted());
                }
                if (typeHandicap.getCreatedDate() != null) {
                    existingTypeHandicap.setCreatedDate(typeHandicap.getCreatedDate());
                }
                if (typeHandicap.getModifiedDate() != null) {
                    existingTypeHandicap.setModifiedDate(typeHandicap.getModifiedDate());
                }

                return existingTypeHandicap;
            })
            .map(typeHandicapRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TypeHandicap> findAll(Pageable pageable) {
        log.debug("Request to get all TypeHandicaps");
        return typeHandicapRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TypeHandicap> findOne(Long id) {
        log.debug("Request to get TypeHandicap : {}", id);
        return typeHandicapRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeHandicap : {}", id);
        typeHandicapRepository.deleteById(id);
    }
}
