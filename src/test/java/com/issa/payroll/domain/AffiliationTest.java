package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AffiliationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Affiliation.class);
        Affiliation affiliation1 = new Affiliation();
        affiliation1.setId(1L);
        Affiliation affiliation2 = new Affiliation();
        affiliation2.setId(affiliation1.getId());
        assertThat(affiliation1).isEqualTo(affiliation2);
        affiliation2.setId(2L);
        assertThat(affiliation1).isNotEqualTo(affiliation2);
        affiliation1.setId(null);
        assertThat(affiliation1).isNotEqualTo(affiliation2);
    }
}
