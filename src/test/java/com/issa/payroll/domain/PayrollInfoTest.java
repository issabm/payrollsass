package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayrollInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayrollInfo.class);
        PayrollInfo payrollInfo1 = new PayrollInfo();
        payrollInfo1.setId(1L);
        PayrollInfo payrollInfo2 = new PayrollInfo();
        payrollInfo2.setId(payrollInfo1.getId());
        assertThat(payrollInfo1).isEqualTo(payrollInfo2);
        payrollInfo2.setId(2L);
        assertThat(payrollInfo1).isNotEqualTo(payrollInfo2);
        payrollInfo1.setId(null);
        assertThat(payrollInfo1).isNotEqualTo(payrollInfo2);
    }
}
