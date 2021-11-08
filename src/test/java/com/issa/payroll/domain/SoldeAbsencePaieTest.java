package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoldeAbsencePaieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SoldeAbsencePaie.class);
        SoldeAbsencePaie soldeAbsencePaie1 = new SoldeAbsencePaie();
        soldeAbsencePaie1.setId(1L);
        SoldeAbsencePaie soldeAbsencePaie2 = new SoldeAbsencePaie();
        soldeAbsencePaie2.setId(soldeAbsencePaie1.getId());
        assertThat(soldeAbsencePaie1).isEqualTo(soldeAbsencePaie2);
        soldeAbsencePaie2.setId(2L);
        assertThat(soldeAbsencePaie1).isNotEqualTo(soldeAbsencePaie2);
        soldeAbsencePaie1.setId(null);
        assertThat(soldeAbsencePaie1).isNotEqualTo(soldeAbsencePaie2);
    }
}
