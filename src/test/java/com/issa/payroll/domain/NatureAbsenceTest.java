package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NatureAbsenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureAbsence.class);
        NatureAbsence natureAbsence1 = new NatureAbsence();
        natureAbsence1.setId(1L);
        NatureAbsence natureAbsence2 = new NatureAbsence();
        natureAbsence2.setId(natureAbsence1.getId());
        assertThat(natureAbsence1).isEqualTo(natureAbsence2);
        natureAbsence2.setId(2L);
        assertThat(natureAbsence1).isNotEqualTo(natureAbsence2);
        natureAbsence1.setId(null);
        assertThat(natureAbsence1).isNotEqualTo(natureAbsence2);
    }
}
