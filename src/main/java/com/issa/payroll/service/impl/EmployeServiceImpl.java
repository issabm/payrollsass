package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Employe;
import com.issa.payroll.repository.EmployeRepository;
import com.issa.payroll.service.EmployeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Employe}.
 */
@Service
@Transactional
public class EmployeServiceImpl implements EmployeService {

    private final Logger log = LoggerFactory.getLogger(EmployeServiceImpl.class);

    private final EmployeRepository employeRepository;

    public EmployeServiceImpl(EmployeRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    @Override
    public Employe save(Employe employe) {
        log.debug("Request to save Employe : {}", employe);
        return employeRepository.save(employe);
    }

    @Override
    public Optional<Employe> partialUpdate(Employe employe) {
        log.debug("Request to partially update Employe : {}", employe);

        return employeRepository
            .findById(employe.getId())
            .map(existingEmploye -> {
                if (employe.getMatricule() != null) {
                    existingEmploye.setMatricule(employe.getMatricule());
                }
                if (employe.getNomAr() != null) {
                    existingEmploye.setNomAr(employe.getNomAr());
                }
                if (employe.getNomJfAr() != null) {
                    existingEmploye.setNomJfAr(employe.getNomJfAr());
                }
                if (employe.getPrenomAr() != null) {
                    existingEmploye.setPrenomAr(employe.getPrenomAr());
                }
                if (employe.getNomEn() != null) {
                    existingEmploye.setNomEn(employe.getNomEn());
                }
                if (employe.getNomJfEn() != null) {
                    existingEmploye.setNomJfEn(employe.getNomJfEn());
                }
                if (employe.getPrenomEn() != null) {
                    existingEmploye.setPrenomEn(employe.getPrenomEn());
                }
                if (employe.getNomPereAr() != null) {
                    existingEmploye.setNomPereAr(employe.getNomPereAr());
                }
                if (employe.getNomPereEn() != null) {
                    existingEmploye.setNomPereEn(employe.getNomPereEn());
                }
                if (employe.getNomMereAr() != null) {
                    existingEmploye.setNomMereAr(employe.getNomMereAr());
                }
                if (employe.getNomMereEn() != null) {
                    existingEmploye.setNomMereEn(employe.getNomMereEn());
                }
                if (employe.getNomGpAr() != null) {
                    existingEmploye.setNomGpAr(employe.getNomGpAr());
                }
                if (employe.getNomGpEn() != null) {
                    existingEmploye.setNomGpEn(employe.getNomGpEn());
                }
                if (employe.getDateNaiss() != null) {
                    existingEmploye.setDateNaiss(employe.getDateNaiss());
                }
                if (employe.getRib() != null) {
                    existingEmploye.setRib(employe.getRib());
                }
                if (employe.getBanque() != null) {
                    existingEmploye.setBanque(employe.getBanque());
                }
                if (employe.getDateRib() != null) {
                    existingEmploye.setDateRib(employe.getDateRib());
                }
                if (employe.getDateBanque() != null) {
                    existingEmploye.setDateBanque(employe.getDateBanque());
                }
                if (employe.getImg() != null) {
                    existingEmploye.setImg(employe.getImg());
                }
                if (employe.getImgContentType() != null) {
                    existingEmploye.setImgContentType(employe.getImgContentType());
                }
                if (employe.getUtil() != null) {
                    existingEmploye.setUtil(employe.getUtil());
                }
                if (employe.getDateop() != null) {
                    existingEmploye.setDateop(employe.getDateop());
                }
                if (employe.getDateModif() != null) {
                    existingEmploye.setDateModif(employe.getDateModif());
                }
                if (employe.getModifiedBy() != null) {
                    existingEmploye.setModifiedBy(employe.getModifiedBy());
                }
                if (employe.getOp() != null) {
                    existingEmploye.setOp(employe.getOp());
                }
                if (employe.getIsDeleted() != null) {
                    existingEmploye.setIsDeleted(employe.getIsDeleted());
                }

                return existingEmploye;
            })
            .map(employeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Employe> findAll(Pageable pageable) {
        log.debug("Request to get all Employes");
        return employeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employe> findOne(Long id) {
        log.debug("Request to get Employe : {}", id);
        return employeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Employe : {}", id);
        employeRepository.deleteById(id);
    }
}
