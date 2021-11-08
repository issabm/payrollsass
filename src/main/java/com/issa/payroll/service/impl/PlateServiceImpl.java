package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Plate;
import com.issa.payroll.repository.PlateRepository;
import com.issa.payroll.service.PlateService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Plate}.
 */
@Service
@Transactional
public class PlateServiceImpl implements PlateService {

    private final Logger log = LoggerFactory.getLogger(PlateServiceImpl.class);

    private final PlateRepository plateRepository;

    public PlateServiceImpl(PlateRepository plateRepository) {
        this.plateRepository = plateRepository;
    }

    @Override
    public Plate save(Plate plate) {
        log.debug("Request to save Plate : {}", plate);
        return plateRepository.save(plate);
    }

    @Override
    public Optional<Plate> partialUpdate(Plate plate) {
        log.debug("Request to partially update Plate : {}", plate);

        return plateRepository
            .findById(plate.getId())
            .map(existingPlate -> {
                if (plate.getPriorite() != null) {
                    existingPlate.setPriorite(plate.getPriorite());
                }
                if (plate.getValueCalcul() != null) {
                    existingPlate.setValueCalcul(plate.getValueCalcul());
                }
                if (plate.getCode() != null) {
                    existingPlate.setCode(plate.getCode());
                }
                if (plate.getLibAr() != null) {
                    existingPlate.setLibAr(plate.getLibAr());
                }
                if (plate.getLibEn() != null) {
                    existingPlate.setLibEn(plate.getLibEn());
                }
                if (plate.getDateop() != null) {
                    existingPlate.setDateop(plate.getDateop());
                }
                if (plate.getUtil() != null) {
                    existingPlate.setUtil(plate.getUtil());
                }
                if (plate.getModifiedBy() != null) {
                    existingPlate.setModifiedBy(plate.getModifiedBy());
                }
                if (plate.getOp() != null) {
                    existingPlate.setOp(plate.getOp());
                }
                if (plate.getIsDeleted() != null) {
                    existingPlate.setIsDeleted(plate.getIsDeleted());
                }
                if (plate.getCreatedDate() != null) {
                    existingPlate.setCreatedDate(plate.getCreatedDate());
                }
                if (plate.getModifiedDate() != null) {
                    existingPlate.setModifiedDate(plate.getModifiedDate());
                }

                return existingPlate;
            })
            .map(plateRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Plate> findAll(Pageable pageable) {
        log.debug("Request to get all Plates");
        return plateRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Plate> findOne(Long id) {
        log.debug("Request to get Plate : {}", id);
        return plateRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Plate : {}", id);
        plateRepository.deleteById(id);
    }
}
