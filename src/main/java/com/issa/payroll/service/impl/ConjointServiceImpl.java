package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Conjoint;
import com.issa.payroll.repository.ConjointRepository;
import com.issa.payroll.service.ConjointService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Conjoint}.
 */
@Service
@Transactional
public class ConjointServiceImpl implements ConjointService {

    private final Logger log = LoggerFactory.getLogger(ConjointServiceImpl.class);

    private final ConjointRepository conjointRepository;

    public ConjointServiceImpl(ConjointRepository conjointRepository) {
        this.conjointRepository = conjointRepository;
    }

    @Override
    public Conjoint save(Conjoint conjoint) {
        log.debug("Request to save Conjoint : {}", conjoint);
        return conjointRepository.save(conjoint);
    }

    @Override
    public Optional<Conjoint> partialUpdate(Conjoint conjoint) {
        log.debug("Request to partially update Conjoint : {}", conjoint);

        return conjointRepository
            .findById(conjoint.getId())
            .map(existingConjoint -> {
                if (conjoint.getMatricule() != null) {
                    existingConjoint.setMatricule(conjoint.getMatricule());
                }
                if (conjoint.getNomAr() != null) {
                    existingConjoint.setNomAr(conjoint.getNomAr());
                }
                if (conjoint.getPrenomAr() != null) {
                    existingConjoint.setPrenomAr(conjoint.getPrenomAr());
                }
                if (conjoint.getNomEn() != null) {
                    existingConjoint.setNomEn(conjoint.getNomEn());
                }
                if (conjoint.getPrenomEn() != null) {
                    existingConjoint.setPrenomEn(conjoint.getPrenomEn());
                }
                if (conjoint.getDateNaiss() != null) {
                    existingConjoint.setDateNaiss(conjoint.getDateNaiss());
                }
                if (conjoint.getDoesWork() != null) {
                    existingConjoint.setDoesWork(conjoint.getDoesWork());
                }
                if (conjoint.getUtil() != null) {
                    existingConjoint.setUtil(conjoint.getUtil());
                }
                if (conjoint.getDateop() != null) {
                    existingConjoint.setDateop(conjoint.getDateop());
                }
                if (conjoint.getModifiedBy() != null) {
                    existingConjoint.setModifiedBy(conjoint.getModifiedBy());
                }
                if (conjoint.getOp() != null) {
                    existingConjoint.setOp(conjoint.getOp());
                }
                if (conjoint.getIsDeleted() != null) {
                    existingConjoint.setIsDeleted(conjoint.getIsDeleted());
                }

                return existingConjoint;
            })
            .map(conjointRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Conjoint> findAll(Pageable pageable) {
        log.debug("Request to get all Conjoints");
        return conjointRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Conjoint> findOne(Long id) {
        log.debug("Request to get Conjoint : {}", id);
        return conjointRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Conjoint : {}", id);
        conjointRepository.deleteById(id);
    }
}
