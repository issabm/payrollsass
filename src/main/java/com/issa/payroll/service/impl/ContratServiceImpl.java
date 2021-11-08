package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Contrat;
import com.issa.payroll.repository.ContratRepository;
import com.issa.payroll.service.ContratService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Contrat}.
 */
@Service
@Transactional
public class ContratServiceImpl implements ContratService {

    private final Logger log = LoggerFactory.getLogger(ContratServiceImpl.class);

    private final ContratRepository contratRepository;

    public ContratServiceImpl(ContratRepository contratRepository) {
        this.contratRepository = contratRepository;
    }

    @Override
    public Contrat save(Contrat contrat) {
        log.debug("Request to save Contrat : {}", contrat);
        return contratRepository.save(contrat);
    }

    @Override
    public Optional<Contrat> partialUpdate(Contrat contrat) {
        log.debug("Request to partially update Contrat : {}", contrat);

        return contratRepository
            .findById(contrat.getId())
            .map(existingContrat -> {
                if (contrat.getRef() != null) {
                    existingContrat.setRef(contrat.getRef());
                }
                if (contrat.getMatricule() != null) {
                    existingContrat.setMatricule(contrat.getMatricule());
                }
                if (contrat.getDateDebut() != null) {
                    existingContrat.setDateDebut(contrat.getDateDebut());
                }
                if (contrat.getDateFin() != null) {
                    existingContrat.setDateFin(contrat.getDateFin());
                }
                if (contrat.getUtil() != null) {
                    existingContrat.setUtil(contrat.getUtil());
                }
                if (contrat.getDateop() != null) {
                    existingContrat.setDateop(contrat.getDateop());
                }
                if (contrat.getModifiedBy() != null) {
                    existingContrat.setModifiedBy(contrat.getModifiedBy());
                }
                if (contrat.getOp() != null) {
                    existingContrat.setOp(contrat.getOp());
                }
                if (contrat.getIsDeleted() != null) {
                    existingContrat.setIsDeleted(contrat.getIsDeleted());
                }
                if (contrat.getCreatedDate() != null) {
                    existingContrat.setCreatedDate(contrat.getCreatedDate());
                }
                if (contrat.getModifiedDate() != null) {
                    existingContrat.setModifiedDate(contrat.getModifiedDate());
                }

                return existingContrat;
            })
            .map(contratRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Contrat> findAll(Pageable pageable) {
        log.debug("Request to get all Contrats");
        return contratRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Contrat> findOne(Long id) {
        log.debug("Request to get Contrat : {}", id);
        return contratRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Contrat : {}", id);
        contratRepository.deleteById(id);
    }
}
