package com.issa.payroll.service.impl;

import com.issa.payroll.domain.PalierPlate;
import com.issa.payroll.repository.PalierPlateRepository;
import com.issa.payroll.service.PalierPlateService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PalierPlate}.
 */
@Service
@Transactional
public class PalierPlateServiceImpl implements PalierPlateService {

    private final Logger log = LoggerFactory.getLogger(PalierPlateServiceImpl.class);

    private final PalierPlateRepository palierPlateRepository;

    public PalierPlateServiceImpl(PalierPlateRepository palierPlateRepository) {
        this.palierPlateRepository = palierPlateRepository;
    }

    @Override
    public PalierPlate save(PalierPlate palierPlate) {
        log.debug("Request to save PalierPlate : {}", palierPlate);
        return palierPlateRepository.save(palierPlate);
    }

    @Override
    public Optional<PalierPlate> partialUpdate(PalierPlate palierPlate) {
        log.debug("Request to partially update PalierPlate : {}", palierPlate);

        return palierPlateRepository
            .findById(palierPlate.getId())
            .map(existingPalierPlate -> {
                if (palierPlate.getCode() != null) {
                    existingPalierPlate.setCode(palierPlate.getCode());
                }
                if (palierPlate.getLibEn() != null) {
                    existingPalierPlate.setLibEn(palierPlate.getLibEn());
                }
                if (palierPlate.getLibAr() != null) {
                    existingPalierPlate.setLibAr(palierPlate.getLibAr());
                }
                if (palierPlate.getAnnee() != null) {
                    existingPalierPlate.setAnnee(palierPlate.getAnnee());
                }
                if (palierPlate.getEffectiValue() != null) {
                    existingPalierPlate.setEffectiValue(palierPlate.getEffectiValue());
                }
                if (palierPlate.getUtil() != null) {
                    existingPalierPlate.setUtil(palierPlate.getUtil());
                }
                if (palierPlate.getDateop() != null) {
                    existingPalierPlate.setDateop(palierPlate.getDateop());
                }
                if (palierPlate.getDateModif() != null) {
                    existingPalierPlate.setDateModif(palierPlate.getDateModif());
                }
                if (palierPlate.getModifiedBy() != null) {
                    existingPalierPlate.setModifiedBy(palierPlate.getModifiedBy());
                }
                if (palierPlate.getOp() != null) {
                    existingPalierPlate.setOp(palierPlate.getOp());
                }
                if (palierPlate.getIsDeleted() != null) {
                    existingPalierPlate.setIsDeleted(palierPlate.getIsDeleted());
                }

                return existingPalierPlate;
            })
            .map(palierPlateRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PalierPlate> findAll(Pageable pageable) {
        log.debug("Request to get all PalierPlates");
        return palierPlateRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PalierPlate> findOne(Long id) {
        log.debug("Request to get PalierPlate : {}", id);
        return palierPlateRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PalierPlate : {}", id);
        palierPlateRepository.deleteById(id);
    }
}
