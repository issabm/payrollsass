package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoldeAbsenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoldeAbsence.class);
        SoldeAbsence soldeAbsence1 = new SoldeAbsence();
        soldeAbsence1.setId(1L);
        SoldeAbsence soldeAbsence2 = new SoldeAbsence();
        soldeAbsence2.setId(soldeAbsence1.getId());
        assertThat(soldeAbsence1).isEqualTo(soldeAbsence2);
        soldeAbsence2.setId(2L);
        assertThat(soldeAbsence1).isNotEqualTo(soldeAbsence2);
        soldeAbsence1.setId(null);
        assertThat(soldeAbsence1).isNotEqualTo(soldeAbsence2);
    }
}
