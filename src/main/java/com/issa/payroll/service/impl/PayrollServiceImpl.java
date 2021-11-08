package com.issa.payroll.service.impl;

import com.issa.payroll.domain.Payroll;
import com.issa.payroll.repository.PayrollRepository;
import com.issa.payroll.service.PayrollService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Payroll}.
 */
@Service
@Transactional
public class PayrollServiceImpl implements PayrollService {

    private final Logger log = LoggerFactory.getLogger(PayrollServiceImpl.class);

    private final PayrollRepository payrollRepository;

    public PayrollServiceImpl(PayrollRepository payrollRepository) {
        this.payrollRepository = payrollRepository;
    }

    @Override
    public Payroll save(Payroll payroll) {
        log.debug("Request to save Payroll : {}", payroll);
        return payrollRepository.save(payroll);
    }

    @Override
    public Optional<Payroll> partialUpdate(Payroll payroll) {
        log.debug("Request to partially update Payroll : {}", payroll);

        return payrollRepository
            .findById(payroll.getId())
            .map(existingPayroll -> {
                if (payroll.getCode() != null) {
                    existingPayroll.setCode(payroll.getCode());
                }
                if (payroll.getLib() != null) {
                    existingPayroll.setLib(payroll.getLib());
                }
                if (payroll.getAnnee() != null) {
                    existingPayroll.setAnnee(payroll.getAnnee());
                }
                if (payroll.getMois() != null) {
                    existingPayroll.setMois(payroll.getMois());
                }
                if (payroll.getDateCalcul() != null) {
                    existingPayroll.setDateCalcul(payroll.getDateCalcul());
                }
                if (payroll.getDateValid() != null) {
                    existingPayroll.setDateValid(payroll.getDateValid());
                }
                if (payroll.getDatePayroll() != null) {
                    existingPayroll.setDatePayroll(payroll.getDatePayroll());
                }
                if (payroll.getTotalNet() != null) {
                    existingPayroll.setTotalNet(payroll.getTotalNet());
                }
                if (payroll.getTotalNetDevise() != null) {
                    existingPayroll.setTotalNetDevise(payroll.getTotalNetDevise());
                }
                if (payroll.getTauxChange() != null) {
                    existingPayroll.setTauxChange(payroll.getTauxChange());
                }
                if (payroll.getCalculBy() != null) {
                    existingPayroll.setCalculBy(payroll.getCalculBy());
                }
                if (payroll.getEffectBy() != null) {
                    existingPayroll.setEffectBy(payroll.getEffectBy());
                }
                if (payroll.getDateSituation() != null) {
                    existingPayroll.setDateSituation(payroll.getDateSituation());
                }
                if (payroll.getDateop() != null) {
                    existingPayroll.setDateop(payroll.getDateop());
                }
                if (payroll.getModifiedBy() != null) {
                    existingPayroll.setModifiedBy(payroll.getModifiedBy());
                }
                if (payroll.getCreatedBy() != null) {
                    existingPayroll.setCreatedBy(payroll.getCreatedBy());
                }
                if (payroll.getOp() != null) {
                    existingPayroll.setOp(payroll.getOp());
                }
                if (payroll.getUtil() != null) {
                    existingPayroll.setUtil(payroll.getUtil());
                }
                if (payroll.getIsDeleted() != null) {
                    existingPayroll.setIsDeleted(payroll.getIsDeleted());
                }
                if (payroll.getCreatedDate() != null) {
                    existingPayroll.setCreatedDate(payroll.getCreatedDate());
                }
                if (payroll.getModifiedDate() != null) {
                    existingPayroll.setModifiedDate(payroll.getModifiedDate());
                }

                return existingPayroll;
            })
            .map(payrollRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Payroll> findAll(Pageable pageable) {
        log.debug("Request to get all Payrolls");
        return payrollRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Payroll> findOne(Long id) {
        log.debug("Request to get Payroll : {}", id);
        return payrollRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Payroll : {}", id);
        payrollRepository.deleteById(id);
    }
}
