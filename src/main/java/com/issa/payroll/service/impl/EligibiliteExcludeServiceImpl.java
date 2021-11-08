package com.issa.payroll.service.impl;

import com.issa.payroll.domain.EligibiliteExclude;
import com.issa.payroll.repository.EligibiliteExcludeRepository;
import com.issa.payroll.service.EligibiliteExcludeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EligibiliteExclude}.
 */
@Service
@Transactional
public class EligibiliteExcludeServiceImpl implements EligibiliteExcludeService {

    private final Logger log = LoggerFactory.getLogger(EligibiliteExcludeServiceImpl.class);

    private final EligibiliteExcludeRepository eligibiliteExcludeRepository;

    public EligibiliteExcludeServiceImpl(EligibiliteExcludeRepository eligibiliteExcludeRepository) {
        this.eligibiliteExcludeRepository = eligibiliteExcludeRepository;
    }

    @Override
    public EligibiliteExclude save(EligibiliteExclude eligibiliteExclude) {
        log.debug("Request to save EligibiliteExclude : {}", eligibiliteExclude);
        return eligibiliteExcludeRepository.save(eligibiliteExclude);
    }

    @Override
    public Optional<EligibiliteExclude> partialUpdate(EligibiliteExclude eligibiliteExclude) {
        log.debug("Request to partially update EligibiliteExclude : {}", eligibiliteExclude);

        return eligibiliteExcludeRepository
            .findById(eligibiliteExclude.getId())
            .map(existingEligibiliteExclude -> {
                if (eligibiliteExclude.getAnneeBegin() != null) {
                    existingEligibiliteExclude.setAnneeBegin(eligibiliteExclude.getAnneeBegin());
                }
                if (eligibiliteExclude.getMoisBegin() != null) {
                    existingEligibiliteExclude.setMoisBegin(eligibiliteExclude.getMoisBegin());
                }
                if (eligibiliteExclude.getAnneeEnd() != null) {
                    existingEligibiliteExclude.setAnneeEnd(eligibiliteExclude.getAnneeEnd());
                }
                if (eligibiliteExclude.getMoisEnd() != null) {
                    existingEligibiliteExclude.setMoisEnd(eligibiliteExclude.getMoisEnd());
                }
                if (eligibiliteExclude.getMatricule() != null) {
                    existingEligibiliteExclude.setMatricule(eligibiliteExclude.getMatricule());
                }
                if (eligibiliteExclude.getCode() != null) {
                    existingEligibiliteExclude.setCode(eligibiliteExclude.getCode());
                }
                if (eligibiliteExclude.getLibEn() != null) {
                    existingEligibiliteExclude.setLibEn(eligibiliteExclude.getLibEn());
                }
                if (eligibiliteExclude.getLibAr() != null) {
                    existingEligibiliteExclude.setLibAr(eligibiliteExclude.getLibAr());
                }
                if (eligibiliteExclude.getLibFr() != null) {
                    existingEligibiliteExclude.setLibFr(eligibiliteExclude.getLibFr());
                }
                if (eligibiliteExclude.getAnnee() != null) {
                    existingEligibiliteExclude.setAnnee(eligibiliteExclude.getAnnee());
                }
                if (eligibiliteExclude.getValPayroll() != null) {
                    existingEligibiliteExclude.setValPayroll(eligibiliteExclude.getValPayroll());
                }
                if (eligibiliteExclude.getNbDaysLeave() != null) {
                    existingEligibiliteExclude.setNbDaysLeave(eligibiliteExclude.getNbDaysLeave());
                }
                if (eligibiliteExclude.getPourValPayroll() != null) {
                    existingEligibiliteExclude.setPourValPayroll(eligibiliteExclude.getPourValPayroll());
                }
                if (eligibiliteExclude.getDateop() != null) {
                    existingEligibiliteExclude.setDateop(eligibiliteExclude.getDateop());
                }
                if (eligibiliteExclude.getModifiedBy() != null) {
                    existingEligibiliteExclude.setModifiedBy(eligibiliteExclude.getModifiedBy());
                }
                if (eligibiliteExclude.getCreatedBy() != null) {
                    existingEligibiliteExclude.setCreatedBy(eligibiliteExclude.getCreatedBy());
                }
                if (eligibiliteExclude.getOp() != null) {
                    existingEligibiliteExclude.setOp(eligibiliteExclude.getOp());
                }
                if (eligibiliteExclude.getUtil() != null) {
                    existingEligibiliteExclude.setUtil(eligibiliteExclude.getUtil());
                }
                if (eligibiliteExclude.getIsDeleted() != null) {
                    existingEligibiliteExclude.setIsDeleted(eligibiliteExclude.getIsDeleted());
                }
                if (eligibiliteExclude.getCreatedDate() != null) {
                    existingEligibiliteExclude.setCreatedDate(eligibiliteExclude.getCreatedDate());
                }
                if (eligibiliteExclude.getModifiedDate() != null) {
                    existingEligibiliteExclude.setModifiedDate(eligibiliteExclude.getModifiedDate());
                }

                return existingEligibiliteExclude;
            })
            .map(eligibiliteExcludeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EligibiliteExclude> findAll(Pageable pageable) {
        log.debug("Request to get all EligibiliteExcludes");
        return eligibiliteExcludeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EligibiliteExclude> findOne(Long id) {
        log.debug("Request to get EligibiliteExclude : {}", id);
        return eligibiliteExcludeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EligibiliteExclude : {}", id);
        eligibiliteExcludeRepository.deleteById(id);
    }
}
