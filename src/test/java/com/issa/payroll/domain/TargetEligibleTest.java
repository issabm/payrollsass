package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TargetEligibleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TargetEligible.class);
        TargetEligible targetEligible1 = new TargetEligible();
        targetEligible1.setId(1L);
        TargetEligible targetEligible2 = new TargetEligible();
        targetEligible2.setId(targetEligible1.getId());
        assertThat(targetEligible1).isEqualTo(targetEligible2);
        targetEligible2.setId(2L);
        assertThat(targetEligible1).isNotEqualTo(targetEligible2);
        targetEligible1.setId(null);
        assertThat(targetEligible1).isNotEqualTo(targetEligible2);
    }
}
