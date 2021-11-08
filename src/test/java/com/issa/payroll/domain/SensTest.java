package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SensTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sens.class);
        Sens sens1 = new Sens();
        sens1.setId(1L);
        Sens sens2 = new Sens();
        sens2.setId(sens1.getId());
        assertThat(sens1).isEqualTo(sens2);
        sens2.setId(2L);
        assertThat(sens1).isNotEqualTo(sens2);
        sens1.setId(null);
        assertThat(sens1).isNotEqualTo(sens2);
    }
}
