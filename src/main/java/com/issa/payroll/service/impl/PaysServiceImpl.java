package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Pays;
import com.issa.payroll.repository.PaysRepository;
import com.issa.payroll.service.PaysService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pays}.
 */
@Service
@Transactional
public class PaysServiceImpl implements PaysService {

    private final Logger log = LoggerFactory.getLogger(PaysServiceImpl.class);

    private final PaysRepository paysRepository;

    public PaysServiceImpl(PaysRepository paysRepository) {
        this.paysRepository = paysRepository;
    }

    @Override
    public Pays save(Pays pays) {
        log.debug("Request to save Pays : {}", pays);
        return paysRepository.save(pays);
    }

    @Override
    public Optional<Pays> partialUpdate(Pays pays) {
        log.debug("Request to partially update Pays : {}", pays);

        return paysRepository
            .findById(pays.getId())
            .map(existingPays -> {
                if (pays.getCode() != null) {
                    existingPays.setCode(pays.getCode());
                }
                if (pays.getLibAr() != null) {
                    existingPays.setLibAr(pays.getLibAr());
                }
                if (pays.getLibEn() != null) {
                    existingPays.setLibEn(pays.getLibEn());
                }
                if (pays.getDateop() != null) {
                    existingPays.setDateop(pays.getDateop());
                }
                if (pays.getUtil() != null) {
                    existingPays.setUtil(pays.getUtil());
                }
                if (pays.getModifiedBy() != null) {
                    existingPays.setModifiedBy(pays.getModifiedBy());
                }
                if (pays.getOp() != null) {
                    existingPays.setOp(pays.getOp());
                }
                if (pays.getIsDeleted() != null) {
                    existingPays.setIsDeleted(pays.getIsDeleted());
                }
                if (pays.getCreatedDate() != null) {
                    existingPays.setCreatedDate(pays.getCreatedDate());
                }
                if (pays.getModifiedDate() != null) {
                    existingPays.setModifiedDate(pays.getModifiedDate());
                }

                return existingPays;
            })
            .map(paysRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Pays> findAll(Pageable pageable) {
        log.debug("Request to get all Pays");
        return paysRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Pays> findOne(Long id) {
        log.debug("Request to get Pays : {}", id);
        return paysRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pays : {}", id);
        paysRepository.deleteById(id);
    }
}
