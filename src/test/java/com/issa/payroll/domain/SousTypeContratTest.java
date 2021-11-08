package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SousTypeContratTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SousTypeContrat.class);
        SousTypeContrat sousTypeContrat1 = new SousTypeContrat();
        sousTypeContrat1.setId(1L);
        SousTypeContrat sousTypeContrat2 = new SousTypeContrat();
        sousTypeContrat2.setId(sousTypeContrat1.getId());
        assertThat(sousTypeContrat1).isEqualTo(sousTypeContrat2);
        sousTypeContrat2.setId(2L);
        assertThat(sousTypeContrat1).isNotEqualTo(sousTypeContrat2);
        sousTypeContrat1.setId(null);
        assertThat(sousTypeContrat1).isNotEqualTo(sousTypeContrat2);
    }
}
