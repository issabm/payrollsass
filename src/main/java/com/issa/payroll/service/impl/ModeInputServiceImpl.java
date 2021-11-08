package com.issa.payroll.service.impl;

import com.issa.payroll.domain.ModeInput;
import com.issa.payroll.repository.ModeInputRepository;
import com.issa.payroll.service.ModeInputService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ModeInput}.
 */
@Service
@Transactional
public class ModeInputServiceImpl implements ModeInputService {

    private final Logger log = LoggerFactory.getLogger(ModeInputServiceImpl.class);

    private final ModeInputRepository modeInputRepository;

    public ModeInputServiceImpl(ModeInputRepository modeInputRepository) {
        this.modeInputRepository = modeInputRepository;
    }

    @Override
    public ModeInput save(ModeInput modeInput) {
        log.debug("Request to save ModeInput : {}", modeInput);
        return modeInputRepository.save(modeInput);
    }

    @Override
    public Optional<ModeInput> partialUpdate(ModeInput modeInput) {
        log.debug("Request to partially update ModeInput : {}", modeInput);

        return modeInputRepository
            .findById(modeInput.getId())
            .map(existingModeInput -> {
                if (modeInput.getCode() != null) {
                    existingModeInput.setCode(modeInput.getCode());
                }
                if (modeInput.getLibAr() != null) {
                    existingModeInput.setLibAr(modeInput.getLibAr());
                }
                if (modeInput.getLibEn() != null) {
                    existingModeInput.setLibEn(modeInput.getLibEn());
                }
                if (modeInput.getUtil() != null) {
                    existingModeInput.setUtil(modeInput.getUtil());
                }
                if (modeInput.getDateop() != null) {
                    existingModeInput.setDateop(modeInput.getDateop());
                }
                if (modeInput.getModifiedBy() != null) {
                    existingModeInput.setModifiedBy(modeInput.getModifiedBy());
                }
                if (modeInput.getOp() != null) {
                    existingModeInput.setOp(modeInput.getOp());
                }
                if (modeInput.getIsDeleted() != null) {
                    existingModeInput.setIsDeleted(modeInput.getIsDeleted());
                }
                if (modeInput.getCreatedDate() != null) {
                    existingModeInput.setCreatedDate(modeInput.getCreatedDate());
                }
                if (modeInput.getModifiedDate() != null) {
                    existingModeInput.setModifiedDate(modeInput.getModifiedDate());
                }

                return existingModeInput;
            })
            .map(modeInputRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ModeInput> findAll(Pageable pageable) {
        log.debug("Request to get all ModeInputs");
        return modeInputRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ModeInput> findOne(Long id) {
        log.debug("Request to get ModeInput : {}", id);
        return modeInputRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ModeInput : {}", id);
        modeInputRepository.deleteById(id);
    }
}
