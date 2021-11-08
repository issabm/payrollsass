package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeviseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Devise.class);
        Devise devise1 = new Devise();
        devise1.setId(1L);
        Devise devise2 = new Devise();
        devise2.setId(devise1.getId());
        assertThat(devise1).isEqualTo(devise2);
        devise2.setId(2L);
        assertThat(devise1).isNotEqualTo(devise2);
        devise1.setId(null);
        assertThat(devise1).isNotEqualTo(devise2);
    }
}
