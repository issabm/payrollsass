package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Famille;
import com.issa.payroll.repository.FamilleRepository;
import com.issa.payroll.service.FamilleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Famille}.
 */
@Service
@Transactional
public class FamilleServiceImpl implements FamilleService {

    private final Logger log = LoggerFactory.getLogger(FamilleServiceImpl.class);

    private final FamilleRepository familleRepository;

    public FamilleServiceImpl(FamilleRepository familleRepository) {
        this.familleRepository = familleRepository;
    }

    @Override
    public Famille save(Famille famille) {
        log.debug("Request to save Famille : {}", famille);
        return familleRepository.save(famille);
    }

    @Override
    public Optional<Famille> partialUpdate(Famille famille) {
        log.debug("Request to partially update Famille : {}", famille);

        return familleRepository
            .findById(famille.getId())
            .map(existingFamille -> {
                if (famille.getDateSituation() != null) {
                    existingFamille.setDateSituation(famille.getDateSituation());
                }
                if (famille.getDateop() != null) {
                    existingFamille.setDateop(famille.getDateop());
                }
                if (famille.getUtil() != null) {
                    existingFamille.setUtil(famille.getUtil());
                }
                if (famille.getModifiedBy() != null) {
                    existingFamille.setModifiedBy(famille.getModifiedBy());
                }
                if (famille.getOp() != null) {
                    existingFamille.setOp(famille.getOp());
                }
                if (famille.getIsDeleted() != null) {
                    existingFamille.setIsDeleted(famille.getIsDeleted());
                }
                if (famille.getCreatedDate() != null) {
                    existingFamille.setCreatedDate(famille.getCreatedDate());
                }
                if (famille.getModifiedDate() != null) {
                    existingFamille.setModifiedDate(famille.getModifiedDate());
                }

                return existingFamille;
            })
            .map(familleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Famille> findAll(Pageable pageable) {
        log.debug("Request to get all Familles");
        return familleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Famille> findOne(Long id) {
        log.debug("Request to get Famille : {}", id);
        return familleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Famille : {}", id);
        familleRepository.deleteById(id);
    }
}
