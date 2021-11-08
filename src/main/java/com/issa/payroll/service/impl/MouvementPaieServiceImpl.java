package com.issa.payroll.service.impl;

import com.issa.payroll.domain.MouvementPaie;
import com.issa.payroll.repository.MouvementPaieRepository;
import com.issa.payroll.service.MouvementPaieService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MouvementPaie}.
 */
@Service
@Transactional
public class MouvementPaieServiceImpl implements MouvementPaieService {

    private final Logger log = LoggerFactory.getLogger(MouvementPaieServiceImpl.class);

    private final MouvementPaieRepository mouvementPaieRepository;

    public MouvementPaieServiceImpl(MouvementPaieRepository mouvementPaieRepository) {
        this.mouvementPaieRepository = mouvementPaieRepository;
    }

    @Override
    public MouvementPaie save(MouvementPaie mouvementPaie) {
        log.debug("Request to save MouvementPaie : {}", mouvementPaie);
        return mouvementPaieRepository.save(mouvementPaie);
    }

    @Override
    public Optional<MouvementPaie> partialUpdate(MouvementPaie mouvementPaie) {
        log.debug("Request to partially update MouvementPaie : {}", mouvementPaie);

        return mouvementPaieRepository
            .findById(mouvementPaie.getId())
            .map(existingMouvementPaie -> {
                if (mouvementPaie.getCode() != null) {
                    existingMouvementPaie.setCode(mouvementPaie.getCode());
                }
                if (mouvementPaie.getLib() != null) {
                    existingMouvementPaie.setLib(mouvementPaie.getLib());
                }
                if (mouvementPaie.getAnnee() != null) {
                    existingMouvementPaie.setAnnee(mouvementPaie.getAnnee());
                }
                if (mouvementPaie.getMois() != null) {
                    existingMouvementPaie.setMois(mouvementPaie.getMois());
                }
                if (mouvementPaie.getDateCalcul() != null) {
                    existingMouvementPaie.setDateCalcul(mouvementPaie.getDateCalcul());
                }
                if (mouvementPaie.getDateValid() != null) {
                    existingMouvementPaie.setDateValid(mouvementPaie.getDateValid());
                }
                if (mouvementPaie.getDatePayroll() != null) {
                    existingMouvementPaie.setDatePayroll(mouvementPaie.getDatePayroll());
                }
                if (mouvementPaie.getTotalNet() != null) {
                    existingMouvementPaie.setTotalNet(mouvementPaie.getTotalNet());
                }
                if (mouvementPaie.getTotalNetDevise() != null) {
                    existingMouvementPaie.setTotalNetDevise(mouvementPaie.getTotalNetDevise());
                }
                if (mouvementPaie.getTauxChange() != null) {
                    existingMouvementPaie.setTauxChange(mouvementPaie.getTauxChange());
                }
                if (mouvementPaie.getCalculBy() != null) {
                    existingMouvementPaie.setCalculBy(mouvementPaie.getCalculBy());
                }
                if (mouvementPaie.getEffectBy() != null) {
                    existingMouvementPaie.setEffectBy(mouvementPaie.getEffectBy());
                }
                if (mouvementPaie.getDateSituation() != null) {
                    existingMouvementPaie.setDateSituation(mouvementPaie.getDateSituation());
                }
                if (mouvementPaie.getDateop() != null) {
                    existingMouvementPaie.setDateop(mouvementPaie.getDateop());
                }
                if (mouvementPaie.getModifiedBy() != null) {
                    existingMouvementPaie.setModifiedBy(mouvementPaie.getModifiedBy());
                }
                if (mouvementPaie.getCreatedBy() != null) {
                    existingMouvementPaie.setCreatedBy(mouvementPaie.getCreatedBy());
                }
                if (mouvementPaie.getOp() != null) {
                    existingMouvementPaie.setOp(mouvementPaie.getOp());
                }
                if (mouvementPaie.getUtil() != null) {
                    existingMouvementPaie.setUtil(mouvementPaie.getUtil());
                }
                if (mouvementPaie.getIsDeleted() != null) {
                    existingMouvementPaie.setIsDeleted(mouvementPaie.getIsDeleted());
                }
                if (mouvementPaie.getCreatedDate() != null) {
                    existingMouvementPaie.setCreatedDate(mouvementPaie.getCreatedDate());
                }
                if (mouvementPaie.getModifiedDate() != null) {
                    existingMouvementPaie.setModifiedDate(mouvementPaie.getModifiedDate());
                }

                return existingMouvementPaie;
            })
            .map(mouvementPaieRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MouvementPaie> findAll(Pageable pageable) {
        log.debug("Request to get all MouvementPaies");
        return mouvementPaieRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MouvementPaie> findOne(Long id) {
        log.debug("Request to get MouvementPaie : {}", id);
        return mouvementPaieRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MouvementPaie : {}", id);
        mouvementPaieRepository.deleteById(id);
    }
}
