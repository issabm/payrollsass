package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NatureAdhesionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureAdhesion.class);
        NatureAdhesion natureAdhesion1 = new NatureAdhesion();
        natureAdhesion1.setId(1L);
        NatureAdhesion natureAdhesion2 = new NatureAdhesion();
        natureAdhesion2.setId(natureAdhesion1.getId());
        assertThat(natureAdhesion1).isEqualTo(natureAdhesion2);
        natureAdhesion2.setId(2L);
        assertThat(natureAdhesion1).isNotEqualTo(natureAdhesion2);
        natureAdhesion1.setId(null);
        assertThat(natureAdhesion1).isNotEqualTo(natureAdhesion2);
    }
}
