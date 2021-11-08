package com.issa.payroll.service.impl;

import com.issa.payroll.domain.NiveauScolaire;
import com.issa.payroll.repository.NiveauScolaireRepository;
import com.issa.payroll.service.NiveauScolaireService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link NiveauScolaire}.
 */
@Service
@Transactional
public class NiveauScolaireServiceImpl implements NiveauScolaireService {

    private final Logger log = LoggerFactory.getLogger(NiveauScolaireServiceImpl.class);

    private final NiveauScolaireRepository niveauScolaireRepository;

    public NiveauScolaireServiceImpl(NiveauScolaireRepository niveauScolaireRepository) {
        this.niveauScolaireRepository = niveauScolaireRepository;
    }

    @Override
    public NiveauScolaire save(NiveauScolaire niveauScolaire) {
        log.debug("Request to save NiveauScolaire : {}", niveauScolaire);
        return niveauScolaireRepository.save(niveauScolaire);
    }

    @Override
    public Optional<NiveauScolaire> partialUpdate(NiveauScolaire niveauScolaire) {
        log.debug("Request to partially update NiveauScolaire : {}", niveauScolaire);

        return niveauScolaireRepository
            .findById(niveauScolaire.getId())
            .map(existingNiveauScolaire -> {
                if (niveauScolaire.getOrderLevel() != null) {
                    existingNiveauScolaire.setOrderLevel(niveauScolaire.getOrderLevel());
                }
                if (niveauScolaire.getCode() != null) {
                    existingNiveauScolaire.setCode(niveauScolaire.getCode());
                }
                if (niveauScolaire.getLibAr() != null) {
                    existingNiveauScolaire.setLibAr(niveauScolaire.getLibAr());
                }
                if (niveauScolaire.getLibEn() != null) {
                    existingNiveauScolaire.setLibEn(niveauScolaire.getLibEn());
                }
                if (niveauScolaire.getUtil() != null) {
                    existingNiveauScolaire.setUtil(niveauScolaire.getUtil());
                }
                if (niveauScolaire.getDateop() != null) {
                    existingNiveauScolaire.setDateop(niveauScolaire.getDateop());
                }
                if (niveauScolaire.getModifiedBy() != null) {
                    existingNiveauScolaire.setModifiedBy(niveauScolaire.getModifiedBy());
                }
                if (niveauScolaire.getOp() != null) {
                    existingNiveauScolaire.setOp(niveauScolaire.getOp());
                }
                if (niveauScolaire.getIsDeleted() != null) {
                    existingNiveauScolaire.setIsDeleted(niveauScolaire.getIsDeleted());
                }
                if (niveauScolaire.getCreatedDate() != null) {
                    existingNiveauScolaire.setCreatedDate(niveauScolaire.getCreatedDate());
                }
                if (niveauScolaire.getModifiedDate() != null) {
                    existingNiveauScolaire.setModifiedDate(niveauScolaire.getModifiedDate());
                }

                return existingNiveauScolaire;
            })
            .map(niveauScolaireRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NiveauScolaire> findAll(Pageable pageable) {
        log.debug("Request to get all NiveauScolaires");
        return niveauScolaireRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<NiveauScolaire> findOne(Long id) {
        log.debug("Request to get NiveauScolaire : {}", id);
        return niveauScolaireRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete NiveauScolaire : {}", id);
        niveauScolaireRepository.deleteById(id);
    }
}
