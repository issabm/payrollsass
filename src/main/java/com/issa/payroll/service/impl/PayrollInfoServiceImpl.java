package com.issa.payroll.service.impl;

import com.issa.payroll.domain.PayrollInfo;
import com.issa.payroll.repository.PayrollInfoRepository;
import com.issa.payroll.service.PayrollInfoService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PayrollInfo}.
 */
@Service
@Transactional
public class PayrollInfoServiceImpl implements PayrollInfoService {

    private final Logger log = LoggerFactory.getLogger(PayrollInfoServiceImpl.class);

    private final PayrollInfoRepository payrollInfoRepository;

    public PayrollInfoServiceImpl(PayrollInfoRepository payrollInfoRepository) {
        this.payrollInfoRepository = payrollInfoRepository;
    }

    @Override
    public PayrollInfo save(PayrollInfo payrollInfo) {
        log.debug("Request to save PayrollInfo : {}", payrollInfo);
        return payrollInfoRepository.save(payrollInfo);
    }

    @Override
    public Optional<PayrollInfo> partialUpdate(PayrollInfo payrollInfo) {
        log.debug("Request to partially update PayrollInfo : {}", payrollInfo);

        return payrollInfoRepository
            .findById(payrollInfo.getId())
            .map(existingPayrollInfo -> {
                if (payrollInfo.getUtil() != null) {
                    existingPayrollInfo.setUtil(payrollInfo.getUtil());
                }
                if (payrollInfo.getCode() != null) {
                    existingPayrollInfo.setCode(payrollInfo.getCode());
                }
                if (payrollInfo.getLibAr() != null) {
                    existingPayrollInfo.setLibAr(payrollInfo.getLibAr());
                }
                if (payrollInfo.getLibFr() != null) {
                    existingPayrollInfo.setLibFr(payrollInfo.getLibFr());
                }
                if (payrollInfo.getLibEr() != null) {
                    existingPayrollInfo.setLibEr(payrollInfo.getLibEr());
                }
                if (payrollInfo.getValueRebrique() != null) {
                    existingPayrollInfo.setValueRebrique(payrollInfo.getValueRebrique());
                }
                if (payrollInfo.getValueRebDevise() != null) {
                    existingPayrollInfo.setValueRebDevise(payrollInfo.getValueRebDevise());
                }
                if (payrollInfo.getTauxChange() != null) {
                    existingPayrollInfo.setTauxChange(payrollInfo.getTauxChange());
                }
                if (payrollInfo.getDateCalcul() != null) {
                    existingPayrollInfo.setDateCalcul(payrollInfo.getDateCalcul());
                }
                if (payrollInfo.getDateEffect() != null) {
                    existingPayrollInfo.setDateEffect(payrollInfo.getDateEffect());
                }
                if (payrollInfo.getCalculBy() != null) {
                    existingPayrollInfo.setCalculBy(payrollInfo.getCalculBy());
                }
                if (payrollInfo.getEffectBy() != null) {
                    existingPayrollInfo.setEffectBy(payrollInfo.getEffectBy());
                }
                if (payrollInfo.getDateSituation() != null) {
                    existingPayrollInfo.setDateSituation(payrollInfo.getDateSituation());
                }
                if (payrollInfo.getDateop() != null) {
                    existingPayrollInfo.setDateop(payrollInfo.getDateop());
                }
                if (payrollInfo.getModifiedBy() != null) {
                    existingPayrollInfo.setModifiedBy(payrollInfo.getModifiedBy());
                }
                if (payrollInfo.getCreatedBy() != null) {
                    existingPayrollInfo.setCreatedBy(payrollInfo.getCreatedBy());
                }
                if (payrollInfo.getOp() != null) {
                    existingPayrollInfo.setOp(payrollInfo.getOp());
                }
                if (payrollInfo.getIsDeleted() != null) {
                    existingPayrollInfo.setIsDeleted(payrollInfo.getIsDeleted());
                }
                if (payrollInfo.getCreatedDate() != null) {
                    existingPayrollInfo.setCreatedDate(payrollInfo.getCreatedDate());
                }
                if (payrollInfo.getModifiedDate() != null) {
                    existingPayrollInfo.setModifiedDate(payrollInfo.getModifiedDate());
                }

                return existingPayrollInfo;
            })
            .map(payrollInfoRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PayrollInfo> findAll(Pageable pageable) {
        log.debug("Request to get all PayrollInfos");
        return payrollInfoRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PayrollInfo> findOne(Long id) {
        log.debug("Request to get PayrollInfo : {}", id);
        return payrollInfoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PayrollInfo : {}", id);
        payrollInfoRepository.deleteById(id);
    }
}
