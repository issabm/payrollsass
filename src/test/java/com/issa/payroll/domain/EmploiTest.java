package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmploiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Emploi.class);
        Emploi emploi1 = new Emploi();
        emploi1.setId(1L);
        Emploi emploi2 = new Emploi();
        emploi2.setId(emploi1.getId());
        assertThat(emploi1).isEqualTo(emploi2);
        emploi2.setId(2L);
        assertThat(emploi1).isNotEqualTo(emploi2);
        emploi1.setId(null);
        assertThat(emploi1).isNotEqualTo(emploi2);
    }
}
