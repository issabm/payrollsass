package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Sexe;
import com.issa.payroll.repository.SexeRepository;
import com.issa.payroll.service.SexeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Sexe}.
 */
@Service
@Transactional
public class SexeServiceImpl implements SexeService {

    private final Logger log = LoggerFactory.getLogger(SexeServiceImpl.class);

    private final SexeRepository sexeRepository;

    public SexeServiceImpl(SexeRepository sexeRepository) {
        this.sexeRepository = sexeRepository;
    }

    @Override
    public Sexe save(Sexe sexe) {
        log.debug("Request to save Sexe : {}", sexe);
        return sexeRepository.save(sexe);
    }

    @Override
    public Optional<Sexe> partialUpdate(Sexe sexe) {
        log.debug("Request to partially update Sexe : {}", sexe);

        return sexeRepository
            .findById(sexe.getId())
            .map(existingSexe -> {
                if (sexe.getCode() != null) {
                    existingSexe.setCode(sexe.getCode());
                }
                if (sexe.getLibAr() != null) {
                    existingSexe.setLibAr(sexe.getLibAr());
                }
                if (sexe.getLibEn() != null) {
                    existingSexe.setLibEn(sexe.getLibEn());
                }
                if (sexe.getUtil() != null) {
                    existingSexe.setUtil(sexe.getUtil());
                }
                if (sexe.getDateop() != null) {
                    existingSexe.setDateop(sexe.getDateop());
                }
                if (sexe.getModifiedBy() != null) {
                    existingSexe.setModifiedBy(sexe.getModifiedBy());
                }
                if (sexe.getOp() != null) {
                    existingSexe.setOp(sexe.getOp());
                }
                if (sexe.getIsDeleted() != null) {
                    existingSexe.setIsDeleted(sexe.getIsDeleted());
                }
                if (sexe.getCreatedDate() != null) {
                    existingSexe.setCreatedDate(sexe.getCreatedDate());
                }
                if (sexe.getModifiedDate() != null) {
                    existingSexe.setModifiedDate(sexe.getModifiedDate());
                }

                return existingSexe;
            })
            .map(sexeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sexe> findAll(Pageable pageable) {
        log.debug("Request to get all Sexes");
        return sexeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sexe> findOne(Long id) {
        log.debug("Request to get Sexe : {}", id);
        return sexeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Sexe : {}", id);
        sexeRepository.deleteById(id);
    }
}
