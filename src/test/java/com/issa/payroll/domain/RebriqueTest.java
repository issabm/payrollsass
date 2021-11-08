package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RebriqueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rebrique.class);
        Rebrique rebrique1 = new Rebrique();
        rebrique1.setId(1L);
        Rebrique rebrique2 = new Rebrique();
        rebrique2.setId(rebrique1.getId());
        assertThat(rebrique1).isEqualTo(rebrique2);
        rebrique2.setId(2L);
        assertThat(rebrique1).isNotEqualTo(rebrique2);
        rebrique1.setId(null);
        assertThat(rebrique1).isNotEqualTo(rebrique2);
    }
}
