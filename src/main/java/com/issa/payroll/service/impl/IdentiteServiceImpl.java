package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Identite;
import com.issa.payroll.repository.IdentiteRepository;
import com.issa.payroll.service.IdentiteService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Identite}.
 */
@Service
@Transactional
public class IdentiteServiceImpl implements IdentiteService {

    private final Logger log = LoggerFactory.getLogger(IdentiteServiceImpl.class);

    private final IdentiteRepository identiteRepository;

    public IdentiteServiceImpl(IdentiteRepository identiteRepository) {
        this.identiteRepository = identiteRepository;
    }

    @Override
    public Identite save(Identite identite) {
        log.debug("Request to save Identite : {}", identite);
        return identiteRepository.save(identite);
    }

    @Override
    public Optional<Identite> partialUpdate(Identite identite) {
        log.debug("Request to partially update Identite : {}", identite);

        return identiteRepository
            .findById(identite.getId())
            .map(existingIdentite -> {
                if (identite.getCode() != null) {
                    existingIdentite.setCode(identite.getCode());
                }
                if (identite.getDateIssued() != null) {
                    existingIdentite.setDateIssued(identite.getDateIssued());
                }
                if (identite.getPlaceIssed() != null) {
                    existingIdentite.setPlaceIssed(identite.getPlaceIssed());
                }
                if (identite.getDateVld() != null) {
                    existingIdentite.setDateVld(identite.getDateVld());
                }
                if (identite.getUtil() != null) {
                    existingIdentite.setUtil(identite.getUtil());
                }
                if (identite.getDateop() != null) {
                    existingIdentite.setDateop(identite.getDateop());
                }
                if (identite.getModifiedBy() != null) {
                    existingIdentite.setModifiedBy(identite.getModifiedBy());
                }
                if (identite.getOp() != null) {
                    existingIdentite.setOp(identite.getOp());
                }
                if (identite.getIsDeleted() != null) {
                    existingIdentite.setIsDeleted(identite.getIsDeleted());
                }
                if (identite.getCreatedDate() != null) {
                    existingIdentite.setCreatedDate(identite.getCreatedDate());
                }
                if (identite.getModifiedDate() != null) {
                    existingIdentite.setModifiedDate(identite.getModifiedDate());
                }

                return existingIdentite;
            })
            .map(identiteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Identite> findAll(Pageable pageable) {
        log.debug("Request to get all Identites");
        return identiteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Identite> findOne(Long id) {
        log.debug("Request to get Identite : {}", id);
        return identiteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Identite : {}", id);
        identiteRepository.deleteById(id);
    }
}
