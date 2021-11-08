package com.issa.payroll.service.impl;

import com.issa.payroll.domain.NatureConfig;
import com.issa.payroll.repository.NatureConfigRepository;
import com.issa.payroll.service.NatureConfigService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NatureConfig}.
 */
@Service
@Transactional
public class NatureConfigServiceImpl implements NatureConfigService {

    private final Logger log = LoggerFactory.getLogger(NatureConfigServiceImpl.class);

    private final NatureConfigRepository natureConfigRepository;

    public NatureConfigServiceImpl(NatureConfigRepository natureConfigRepository) {
        this.natureConfigRepository = natureConfigRepository;
    }

    @Override
    public NatureConfig save(NatureConfig natureConfig) {
        log.debug("Request to save NatureConfig : {}", natureConfig);
        return natureConfigRepository.save(natureConfig);
    }

    @Override
    public Optional<NatureConfig> partialUpdate(NatureConfig natureConfig) {
        log.debug("Request to partially update NatureConfig : {}", natureConfig);

        return natureConfigRepository
            .findById(natureConfig.getId())
            .map(existingNatureConfig -> {
                if (natureConfig.getCode() != null) {
                    existingNatureConfig.setCode(natureConfig.getCode());
                }
                if (natureConfig.getLibEn() != null) {
                    existingNatureConfig.setLibEn(natureConfig.getLibEn());
                }
                if (natureConfig.getLibAr() != null) {
                    existingNatureConfig.setLibAr(natureConfig.getLibAr());
                }
                if (natureConfig.getLibFr() != null) {
                    existingNatureConfig.setLibFr(natureConfig.getLibFr());
                }
                if (natureConfig.getDateop() != null) {
                    existingNatureConfig.setDateop(natureConfig.getDateop());
                }
                if (natureConfig.getModifiedBy() != null) {
                    existingNatureConfig.setModifiedBy(natureConfig.getModifiedBy());
                }
                if (natureConfig.getCreatedBy() != null) {
                    existingNatureConfig.setCreatedBy(natureConfig.getCreatedBy());
                }
                if (natureConfig.getOp() != null) {
                    existingNatureConfig.setOp(natureConfig.getOp());
                }
                if (natureConfig.getUtil() != null) {
                    existingNatureConfig.setUtil(natureConfig.getUtil());
                }
                if (natureConfig.getIsDeleted() != null) {
                    existingNatureConfig.setIsDeleted(natureConfig.getIsDeleted());
                }
                if (natureConfig.getCreatedDate() != null) {
                    existingNatureConfig.setCreatedDate(natureConfig.getCreatedDate());
                }
                if (natureConfig.getModifiedDate() != null) {
                    existingNatureConfig.setModifiedDate(natureConfig.getModifiedDate());
                }

                return existingNatureConfig;
            })
            .map(natureConfigRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NatureConfig> findAll(Pageable pageable) {
        log.debug("Request to get all NatureConfigs");
        return natureConfigRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NatureConfig> findOne(Long id) {
        log.debug("Request to get NatureConfig : {}", id);
        return natureConfigRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NatureConfig : {}", id);
        natureConfigRepository.deleteById(id);
    }
}
