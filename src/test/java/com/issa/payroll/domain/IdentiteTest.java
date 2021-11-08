package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IdentiteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Identite.class);
        Identite identite1 = new Identite();
        identite1.setId(1L);
        Identite identite2 = new Identite();
        identite2.setId(identite1.getId());
        assertThat(identite1).isEqualTo(identite2);
        identite2.setId(2L);
        assertThat(identite1).isNotEqualTo(identite2);
        identite1.setId(null);
        assertThat(identite1).isNotEqualTo(identite2);
    }
}
