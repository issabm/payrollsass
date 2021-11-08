package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EchlonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Echlon.class);
        Echlon echlon1 = new Echlon();
        echlon1.setId(1L);
        Echlon echlon2 = new Echlon();
        echlon2.setId(echlon1.getId());
        assertThat(echlon1).isEqualTo(echlon2);
        echlon2.setId(2L);
        assertThat(echlon1).isNotEqualTo(echlon2);
        echlon1.setId(null);
        assertThat(echlon1).isNotEqualTo(echlon2);
    }
}
