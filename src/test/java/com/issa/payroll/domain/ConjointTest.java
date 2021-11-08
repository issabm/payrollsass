package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConjointTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conjoint.class);
        Conjoint conjoint1 = new Conjoint();
        conjoint1.setId(1L);
        Conjoint conjoint2 = new Conjoint();
        conjoint2.setId(conjoint1.getId());
        assertThat(conjoint1).isEqualTo(conjoint2);
        conjoint2.setId(2L);
        assertThat(conjoint1).isNotEqualTo(conjoint2);
        conjoint1.setId(null);
        assertThat(conjoint1).isNotEqualTo(conjoint2);
    }
}
