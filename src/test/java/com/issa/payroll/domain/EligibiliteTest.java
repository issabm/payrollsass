package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EligibiliteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eligibilite.class);
        Eligibilite eligibilite1 = new Eligibilite();
        eligibilite1.setId(1L);
        Eligibilite eligibilite2 = new Eligibilite();
        eligibilite2.setId(eligibilite1.getId());
        assertThat(eligibilite1).isEqualTo(eligibilite2);
        eligibilite2.setId(2L);
        assertThat(eligibilite1).isNotEqualTo(eligibilite2);
        eligibilite1.setId(null);
        assertThat(eligibilite1).isNotEqualTo(eligibilite2);
    }
}
