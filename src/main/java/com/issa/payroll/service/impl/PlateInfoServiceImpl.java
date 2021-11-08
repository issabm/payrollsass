package com.issa.payroll.service.impl;

import com.issa.payroll.domain.PlateInfo;
import com.issa.payroll.repository.PlateInfoRepository;
import com.issa.payroll.service.PlateInfoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PlateInfo}.
 */
@Service
@Transactional
public class PlateInfoServiceImpl implements PlateInfoService {

    private final Logger log = LoggerFactory.getLogger(PlateInfoServiceImpl.class);

    private final PlateInfoRepository plateInfoRepository;

    public PlateInfoServiceImpl(PlateInfoRepository plateInfoRepository) {
        this.plateInfoRepository = plateInfoRepository;
    }

    @Override
    public PlateInfo save(PlateInfo plateInfo) {
        log.debug("Request to save PlateInfo : {}", plateInfo);
        return plateInfoRepository.save(plateInfo);
    }

    @Override
    public Optional<PlateInfo> partialUpdate(PlateInfo plateInfo) {
        log.debug("Request to partially update PlateInfo : {}", plateInfo);

        return plateInfoRepository
            .findById(plateInfo.getId())
            .map(existingPlateInfo -> {
                if (plateInfo.getCode() != null) {
                    existingPlateInfo.setCode(plateInfo.getCode());
                }
                if (plateInfo.getLib() != null) {
                    existingPlateInfo.setLib(plateInfo.getLib());
                }
                if (plateInfo.getValTaken() != null) {
                    existingPlateInfo.setValTaken(plateInfo.getValTaken());
                }
                if (plateInfo.getDateSituation() != null) {
                    existingPlateInfo.setDateSituation(plateInfo.getDateSituation());
                }
                if (plateInfo.getDateop() != null) {
                    existingPlateInfo.setDateop(plateInfo.getDateop());
                }
                if (plateInfo.getModifiedBy() != null) {
                    existingPlateInfo.setModifiedBy(plateInfo.getModifiedBy());
                }
                if (plateInfo.getCreatedBy() != null) {
                    existingPlateInfo.setCreatedBy(plateInfo.getCreatedBy());
                }
                if (plateInfo.getOp() != null) {
                    existingPlateInfo.setOp(plateInfo.getOp());
                }
                if (plateInfo.getUtil() != null) {
                    existingPlateInfo.setUtil(plateInfo.getUtil());
                }
                if (plateInfo.getIsDeleted() != null) {
                    existingPlateInfo.setIsDeleted(plateInfo.getIsDeleted());
                }
                if (plateInfo.getCreatedDate() != null) {
                    existingPlateInfo.setCreatedDate(plateInfo.getCreatedDate());
                }
                if (plateInfo.getModifiedDate() != null) {
                    existingPlateInfo.setModifiedDate(plateInfo.getModifiedDate());
                }

                return existingPlateInfo;
            })
            .map(plateInfoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlateInfo> findAll(Pageable pageable) {
        log.debug("Request to get all PlateInfos");
        return plateInfoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlateInfo> findOne(Long id) {
        log.debug("Request to get PlateInfo : {}", id);
        return plateInfoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PlateInfo : {}", id);
        plateInfoRepository.deleteById(id);
    }
}
