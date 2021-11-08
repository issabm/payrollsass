package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SexeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sexe.class);
        Sexe sexe1 = new Sexe();
        sexe1.setId(1L);
        Sexe sexe2 = new Sexe();
        sexe2.setId(sexe1.getId());
        assertThat(sexe1).isEqualTo(sexe2);
        sexe2.setId(2L);
        assertThat(sexe1).isNotEqualTo(sexe2);
        sexe1.setId(null);
        assertThat(sexe1).isNotEqualTo(sexe2);
    }
}
