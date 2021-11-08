package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Echlon;
import com.issa.payroll.repository.EchlonRepository;
import com.issa.payroll.service.EchlonService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Echlon}.
 */
@Service
@Transactional
public class EchlonServiceImpl implements EchlonService {

    private final Logger log = LoggerFactory.getLogger(EchlonServiceImpl.class);

    private final EchlonRepository echlonRepository;

    public EchlonServiceImpl(EchlonRepository echlonRepository) {
        this.echlonRepository = echlonRepository;
    }

    @Override
    public Echlon save(Echlon echlon) {
        log.debug("Request to save Echlon : {}", echlon);
        return echlonRepository.save(echlon);
    }

    @Override
    public Optional<Echlon> partialUpdate(Echlon echlon) {
        log.debug("Request to partially update Echlon : {}", echlon);

        return echlonRepository
            .findById(echlon.getId())
            .map(existingEchlon -> {
                if (echlon.getCode() != null) {
                    existingEchlon.setCode(echlon.getCode());
                }
                if (echlon.getLibAr() != null) {
                    existingEchlon.setLibAr(echlon.getLibAr());
                }
                if (echlon.getLibEn() != null) {
                    existingEchlon.setLibEn(echlon.getLibEn());
                }
                if (echlon.getUtil() != null) {
                    existingEchlon.setUtil(echlon.getUtil());
                }
                if (echlon.getDateop() != null) {
                    existingEchlon.setDateop(echlon.getDateop());
                }
                if (echlon.getModifiedBy() != null) {
                    existingEchlon.setModifiedBy(echlon.getModifiedBy());
                }
                if (echlon.getOp() != null) {
                    existingEchlon.setOp(echlon.getOp());
                }
                if (echlon.getIsDeleted() != null) {
                    existingEchlon.setIsDeleted(echlon.getIsDeleted());
                }
                if (echlon.getCreatedDate() != null) {
                    existingEchlon.setCreatedDate(echlon.getCreatedDate());
                }
                if (echlon.getModifiedDate() != null) {
                    existingEchlon.setModifiedDate(echlon.getModifiedDate());
                }

                return existingEchlon;
            })
            .map(echlonRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Echlon> findAll(Pageable pageable) {
        log.debug("Request to get all Echlons");
        return echlonRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Echlon> findOne(Long id) {
        log.debug("Request to get Echlon : {}", id);
        return echlonRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Echlon : {}", id);
        echlonRepository.deleteById(id);
    }
}
