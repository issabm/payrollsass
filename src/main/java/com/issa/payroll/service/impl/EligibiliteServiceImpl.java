package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Eligibilite;
import com.issa.payroll.repository.EligibiliteRepository;
import com.issa.payroll.service.EligibiliteService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Eligibilite}.
 */
@Service
@Transactional
public class EligibiliteServiceImpl implements EligibiliteService {

    private final Logger log = LoggerFactory.getLogger(EligibiliteServiceImpl.class);

    private final EligibiliteRepository eligibiliteRepository;

    public EligibiliteServiceImpl(EligibiliteRepository eligibiliteRepository) {
        this.eligibiliteRepository = eligibiliteRepository;
    }

    @Override
    public Eligibilite save(Eligibilite eligibilite) {
        log.debug("Request to save Eligibilite : {}", eligibilite);
        return eligibiliteRepository.save(eligibilite);
    }

    @Override
    public Optional<Eligibilite> partialUpdate(Eligibilite eligibilite) {
        log.debug("Request to partially update Eligibilite : {}", eligibilite);

        return eligibiliteRepository
            .findById(eligibilite.getId())
            .map(existingEligibilite -> {
                if (eligibilite.getPriorite() != null) {
                    existingEligibilite.setPriorite(eligibilite.getPriorite());
                }
                if (eligibilite.getAnnee() != null) {
                    existingEligibilite.setAnnee(eligibilite.getAnnee());
                }
                if (eligibilite.getMois() != null) {
                    existingEligibilite.setMois(eligibilite.getMois());
                }
                if (eligibilite.getNbEnt() != null) {
                    existingEligibilite.setNbEnt(eligibilite.getNbEnt());
                }
                if (eligibilite.getAgeEnt() != null) {
                    existingEligibilite.setAgeEnt(eligibilite.getAgeEnt());
                }
                if (eligibilite.getMatricule() != null) {
                    existingEligibilite.setMatricule(eligibilite.getMatricule());
                }
                if (eligibilite.getCode() != null) {
                    existingEligibilite.setCode(eligibilite.getCode());
                }
                if (eligibilite.getLibEn() != null) {
                    existingEligibilite.setLibEn(eligibilite.getLibEn());
                }
                if (eligibilite.getLibAr() != null) {
                    existingEligibilite.setLibAr(eligibilite.getLibAr());
                }
                if (eligibilite.getLibFr() != null) {
                    existingEligibilite.setLibFr(eligibilite.getLibFr());
                }
                if (eligibilite.getValPayroll() != null) {
                    existingEligibilite.setValPayroll(eligibilite.getValPayroll());
                }
                if (eligibilite.getNbDaysLeave() != null) {
                    existingEligibilite.setNbDaysLeave(eligibilite.getNbDaysLeave());
                }
                if (eligibilite.getPourValPayroll() != null) {
                    existingEligibilite.setPourValPayroll(eligibilite.getPourValPayroll());
                }
                if (eligibilite.getDateop() != null) {
                    existingEligibilite.setDateop(eligibilite.getDateop());
                }
                if (eligibilite.getModifiedBy() != null) {
                    existingEligibilite.setModifiedBy(eligibilite.getModifiedBy());
                }
                if (eligibilite.getCreatedBy() != null) {
                    existingEligibilite.setCreatedBy(eligibilite.getCreatedBy());
                }
                if (eligibilite.getOp() != null) {
                    existingEligibilite.setOp(eligibilite.getOp());
                }
                if (eligibilite.getUtil() != null) {
                    existingEligibilite.setUtil(eligibilite.getUtil());
                }
                if (eligibilite.getIsDeleted() != null) {
                    existingEligibilite.setIsDeleted(eligibilite.getIsDeleted());
                }
                if (eligibilite.getCreatedDate() != null) {
                    existingEligibilite.setCreatedDate(eligibilite.getCreatedDate());
                }
                if (eligibilite.getModifiedDate() != null) {
                    existingEligibilite.setModifiedDate(eligibilite.getModifiedDate());
                }

                return existingEligibilite;
            })
            .map(eligibiliteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Eligibilite> findAll(Pageable pageable) {
        log.debug("Request to get all Eligibilites");
        return eligibiliteRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Eligibilite> findOne(Long id) {
        log.debug("Request to get Eligibilite : {}", id);
        return eligibiliteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eligibilite : {}", id);
        eligibiliteRepository.deleteById(id);
    }
}
