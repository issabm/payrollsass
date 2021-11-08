package com.issa.payroll.service.impl;

import com.issa.payroll.domain.DemandeCalculPaie;
import com.issa.payroll.repository.DemandeCalculPaieRepository;
import com.issa.payroll.service.DemandeCalculPaieService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DemandeCalculPaie}.
 */
@Service
@Transactional
public class DemandeCalculPaieServiceImpl implements DemandeCalculPaieService {

    private final Logger log = LoggerFactory.getLogger(DemandeCalculPaieServiceImpl.class);

    private final DemandeCalculPaieRepository demandeCalculPaieRepository;

    public DemandeCalculPaieServiceImpl(DemandeCalculPaieRepository demandeCalculPaieRepository) {
        this.demandeCalculPaieRepository = demandeCalculPaieRepository;
    }

    @Override
    public DemandeCalculPaie save(DemandeCalculPaie demandeCalculPaie) {
        log.debug("Request to save DemandeCalculPaie : {}", demandeCalculPaie);
        return demandeCalculPaieRepository.save(demandeCalculPaie);
    }

    @Override
    public Optional<DemandeCalculPaie> partialUpdate(DemandeCalculPaie demandeCalculPaie) {
        log.debug("Request to partially update DemandeCalculPaie : {}", demandeCalculPaie);

        return demandeCalculPaieRepository
            .findById(demandeCalculPaie.getId())
            .map(existingDemandeCalculPaie -> {
                if (demandeCalculPaie.getCode() != null) {
                    existingDemandeCalculPaie.setCode(demandeCalculPaie.getCode());
                }
                if (demandeCalculPaie.getLib() != null) {
                    existingDemandeCalculPaie.setLib(demandeCalculPaie.getLib());
                }
                if (demandeCalculPaie.getAnnee() != null) {
                    existingDemandeCalculPaie.setAnnee(demandeCalculPaie.getAnnee());
                }
                if (demandeCalculPaie.getMois() != null) {
                    existingDemandeCalculPaie.setMois(demandeCalculPaie.getMois());
                }
                if (demandeCalculPaie.getDateCalcul() != null) {
                    existingDemandeCalculPaie.setDateCalcul(demandeCalculPaie.getDateCalcul());
                }
                if (demandeCalculPaie.getDateValid() != null) {
                    existingDemandeCalculPaie.setDateValid(demandeCalculPaie.getDateValid());
                }
                if (demandeCalculPaie.getDatePayroll() != null) {
                    existingDemandeCalculPaie.setDatePayroll(demandeCalculPaie.getDatePayroll());
                }
                if (demandeCalculPaie.getTotalNet() != null) {
                    existingDemandeCalculPaie.setTotalNet(demandeCalculPaie.getTotalNet());
                }
                if (demandeCalculPaie.getTotalNetDevise() != null) {
                    existingDemandeCalculPaie.setTotalNetDevise(demandeCalculPaie.getTotalNetDevise());
                }
                if (demandeCalculPaie.getTauxChange() != null) {
                    existingDemandeCalculPaie.setTauxChange(demandeCalculPaie.getTauxChange());
                }
                if (demandeCalculPaie.getCalculBy() != null) {
                    existingDemandeCalculPaie.setCalculBy(demandeCalculPaie.getCalculBy());
                }
                if (demandeCalculPaie.getEffectBy() != null) {
                    existingDemandeCalculPaie.setEffectBy(demandeCalculPaie.getEffectBy());
                }
                if (demandeCalculPaie.getDateSituation() != null) {
                    existingDemandeCalculPaie.setDateSituation(demandeCalculPaie.getDateSituation());
                }
                if (demandeCalculPaie.getDateop() != null) {
                    existingDemandeCalculPaie.setDateop(demandeCalculPaie.getDateop());
                }
                if (demandeCalculPaie.getModifiedBy() != null) {
                    existingDemandeCalculPaie.setModifiedBy(demandeCalculPaie.getModifiedBy());
                }
                if (demandeCalculPaie.getCreatedBy() != null) {
                    existingDemandeCalculPaie.setCreatedBy(demandeCalculPaie.getCreatedBy());
                }
                if (demandeCalculPaie.getOp() != null) {
                    existingDemandeCalculPaie.setOp(demandeCalculPaie.getOp());
                }
                if (demandeCalculPaie.getUtil() != null) {
                    existingDemandeCalculPaie.setUtil(demandeCalculPaie.getUtil());
                }
                if (demandeCalculPaie.getIsDeleted() != null) {
                    existingDemandeCalculPaie.setIsDeleted(demandeCalculPaie.getIsDeleted());
                }
                if (demandeCalculPaie.getCreatedDate() != null) {
                    existingDemandeCalculPaie.setCreatedDate(demandeCalculPaie.getCreatedDate());
                }
                if (demandeCalculPaie.getModifiedDate() != null) {
                    existingDemandeCalculPaie.setModifiedDate(demandeCalculPaie.getModifiedDate());
                }

                return existingDemandeCalculPaie;
            })
            .map(demandeCalculPaieRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DemandeCalculPaie> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeCalculPaies");
        return demandeCalculPaieRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DemandeCalculPaie> findOne(Long id) {
        log.debug("Request to get DemandeCalculPaie : {}", id);
        return demandeCalculPaieRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DemandeCalculPaie : {}", id);
        demandeCalculPaieRepository.deleteById(id);
    }
}
