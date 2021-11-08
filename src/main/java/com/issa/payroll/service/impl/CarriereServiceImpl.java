package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Carriere;
import com.issa.payroll.repository.CarriereRepository;
import com.issa.payroll.service.CarriereService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Carriere}.
 */
@Service
@Transactional
public class CarriereServiceImpl implements CarriereService {

    private final Logger log = LoggerFactory.getLogger(CarriereServiceImpl.class);

    private final CarriereRepository carriereRepository;

    public CarriereServiceImpl(CarriereRepository carriereRepository) {
        this.carriereRepository = carriereRepository;
    }

    @Override
    public Carriere save(Carriere carriere) {
        log.debug("Request to save Carriere : {}", carriere);
        return carriereRepository.save(carriere);
    }

    @Override
    public Optional<Carriere> partialUpdate(Carriere carriere) {
        log.debug("Request to partially update Carriere : {}", carriere);

        return carriereRepository
            .findById(carriere.getId())
            .map(existingCarriere -> {
                if (carriere.getDateDebut() != null) {
                    existingCarriere.setDateDebut(carriere.getDateDebut());
                }
                if (carriere.getDateFin() != null) {
                    existingCarriere.setDateFin(carriere.getDateFin());
                }
                if (carriere.getDateEmploi() != null) {
                    existingCarriere.setDateEmploi(carriere.getDateEmploi());
                }
                if (carriere.getDateEchlon() != null) {
                    existingCarriere.setDateEchlon(carriere.getDateEchlon());
                }
                if (carriere.getDateCategorie() != null) {
                    existingCarriere.setDateCategorie(carriere.getDateCategorie());
                }
                if (carriere.getDateop() != null) {
                    existingCarriere.setDateop(carriere.getDateop());
                }
                if (carriere.getUtil() != null) {
                    existingCarriere.setUtil(carriere.getUtil());
                }
                if (carriere.getModifiedBy() != null) {
                    existingCarriere.setModifiedBy(carriere.getModifiedBy());
                }
                if (carriere.getOp() != null) {
                    existingCarriere.setOp(carriere.getOp());
                }
                if (carriere.getIsDeleted() != null) {
                    existingCarriere.setIsDeleted(carriere.getIsDeleted());
                }
                if (carriere.getCreatedDate() != null) {
                    existingCarriere.setCreatedDate(carriere.getCreatedDate());
                }
                if (carriere.getModifiedDate() != null) {
                    existingCarriere.setModifiedDate(carriere.getModifiedDate());
                }

                return existingCarriere;
            })
            .map(carriereRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Carriere> findAll(Pageable pageable) {
        log.debug("Request to get all Carrieres");
        return carriereRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Carriere> findOne(Long id) {
        log.debug("Request to get Carriere : {}", id);
        return carriereRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Carriere : {}", id);
        carriereRepository.deleteById(id);
    }
}
