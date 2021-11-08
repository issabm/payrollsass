package com.issa.payroll.service.impl;

import com.issa.payroll.domain.NatureMvtPaie;
import com.issa.payroll.repository.NatureMvtPaieRepository;
import com.issa.payroll.service.NatureMvtPaieService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NatureMvtPaie}.
 */
@Service
@Transactional
public class NatureMvtPaieServiceImpl implements NatureMvtPaieService {

    private final Logger log = LoggerFactory.getLogger(NatureMvtPaieServiceImpl.class);

    private final NatureMvtPaieRepository natureMvtPaieRepository;

    public NatureMvtPaieServiceImpl(NatureMvtPaieRepository natureMvtPaieRepository) {
        this.natureMvtPaieRepository = natureMvtPaieRepository;
    }

    @Override
    public NatureMvtPaie save(NatureMvtPaie natureMvtPaie) {
        log.debug("Request to save NatureMvtPaie : {}", natureMvtPaie);
        return natureMvtPaieRepository.save(natureMvtPaie);
    }

    @Override
    public Optional<NatureMvtPaie> partialUpdate(NatureMvtPaie natureMvtPaie) {
        log.debug("Request to partially update NatureMvtPaie : {}", natureMvtPaie);

        return natureMvtPaieRepository
            .findById(natureMvtPaie.getId())
            .map(existingNatureMvtPaie -> {
                if (natureMvtPaie.getCode() != null) {
                    existingNatureMvtPaie.setCode(natureMvtPaie.getCode());
                }
                if (natureMvtPaie.getLibEn() != null) {
                    existingNatureMvtPaie.setLibEn(natureMvtPaie.getLibEn());
                }
                if (natureMvtPaie.getLibAr() != null) {
                    existingNatureMvtPaie.setLibAr(natureMvtPaie.getLibAr());
                }
                if (natureMvtPaie.getLibFr() != null) {
                    existingNatureMvtPaie.setLibFr(natureMvtPaie.getLibFr());
                }
                if (natureMvtPaie.getDateop() != null) {
                    existingNatureMvtPaie.setDateop(natureMvtPaie.getDateop());
                }
                if (natureMvtPaie.getModifiedBy() != null) {
                    existingNatureMvtPaie.setModifiedBy(natureMvtPaie.getModifiedBy());
                }
                if (natureMvtPaie.getCreatedBy() != null) {
                    existingNatureMvtPaie.setCreatedBy(natureMvtPaie.getCreatedBy());
                }
                if (natureMvtPaie.getOp() != null) {
                    existingNatureMvtPaie.setOp(natureMvtPaie.getOp());
                }
                if (natureMvtPaie.getUtil() != null) {
                    existingNatureMvtPaie.setUtil(natureMvtPaie.getUtil());
                }
                if (natureMvtPaie.getIsDeleted() != null) {
                    existingNatureMvtPaie.setIsDeleted(natureMvtPaie.getIsDeleted());
                }
                if (natureMvtPaie.getCreatedDate() != null) {
                    existingNatureMvtPaie.setCreatedDate(natureMvtPaie.getCreatedDate());
                }
                if (natureMvtPaie.getModifiedDate() != null) {
                    existingNatureMvtPaie.setModifiedDate(natureMvtPaie.getModifiedDate());
                }

                return existingNatureMvtPaie;
            })
            .map(natureMvtPaieRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NatureMvtPaie> findAll(Pageable pageable) {
        log.debug("Request to get all NatureMvtPaies");
        return natureMvtPaieRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NatureMvtPaie> findOne(Long id) {
        log.debug("Request to get NatureMvtPaie : {}", id);
        return natureMvtPaieRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NatureMvtPaie : {}", id);
        natureMvtPaieRepository.deleteById(id);
    }
}
