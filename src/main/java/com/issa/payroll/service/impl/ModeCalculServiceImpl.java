package com.issa.payroll.service.impl;

import com.issa.payroll.domain.ModeCalcul;
import com.issa.payroll.repository.ModeCalculRepository;
import com.issa.payroll.service.ModeCalculService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ModeCalcul}.
 */
@Service
@Transactional
public class ModeCalculServiceImpl implements ModeCalculService {

    private final Logger log = LoggerFactory.getLogger(ModeCalculServiceImpl.class);

    private final ModeCalculRepository modeCalculRepository;

    public ModeCalculServiceImpl(ModeCalculRepository modeCalculRepository) {
        this.modeCalculRepository = modeCalculRepository;
    }

    @Override
    public ModeCalcul save(ModeCalcul modeCalcul) {
        log.debug("Request to save ModeCalcul : {}", modeCalcul);
        return modeCalculRepository.save(modeCalcul);
    }

    @Override
    public Optional<ModeCalcul> partialUpdate(ModeCalcul modeCalcul) {
        log.debug("Request to partially update ModeCalcul : {}", modeCalcul);

        return modeCalculRepository
            .findById(modeCalcul.getId())
            .map(existingModeCalcul -> {
                if (modeCalcul.getCode() != null) {
                    existingModeCalcul.setCode(modeCalcul.getCode());
                }
                if (modeCalcul.getLibAr() != null) {
                    existingModeCalcul.setLibAr(modeCalcul.getLibAr());
                }
                if (modeCalcul.getLibEn() != null) {
                    existingModeCalcul.setLibEn(modeCalcul.getLibEn());
                }
                if (modeCalcul.getUtil() != null) {
                    existingModeCalcul.setUtil(modeCalcul.getUtil());
                }
                if (modeCalcul.getDateop() != null) {
                    existingModeCalcul.setDateop(modeCalcul.getDateop());
                }
                if (modeCalcul.getModifiedBy() != null) {
                    existingModeCalcul.setModifiedBy(modeCalcul.getModifiedBy());
                }
                if (modeCalcul.getOp() != null) {
                    existingModeCalcul.setOp(modeCalcul.getOp());
                }
                if (modeCalcul.getIsDeleted() != null) {
                    existingModeCalcul.setIsDeleted(modeCalcul.getIsDeleted());
                }
                if (modeCalcul.getCreatedDate() != null) {
                    existingModeCalcul.setCreatedDate(modeCalcul.getCreatedDate());
                }
                if (modeCalcul.getModifiedDate() != null) {
                    existingModeCalcul.setModifiedDate(modeCalcul.getModifiedDate());
                }

                return existingModeCalcul;
            })
            .map(modeCalculRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModeCalcul> findAll(Pageable pageable) {
        log.debug("Request to get all ModeCalculs");
        return modeCalculRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ModeCalcul> findOne(Long id) {
        log.debug("Request to get ModeCalcul : {}", id);
        return modeCalculRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModeCalcul : {}", id);
        modeCalculRepository.deleteById(id);
    }
}
