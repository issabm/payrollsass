package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NatureEligibiliteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureEligibilite.class);
        NatureEligibilite natureEligibilite1 = new NatureEligibilite();
        natureEligibilite1.setId(1L);
        NatureEligibilite natureEligibilite2 = new NatureEligibilite();
        natureEligibilite2.setId(natureEligibilite1.getId());
        assertThat(natureEligibilite1).isEqualTo(natureEligibilite2);
        natureEligibilite2.setId(2L);
        assertThat(natureEligibilite1).isNotEqualTo(natureEligibilite2);
        natureEligibilite1.setId(null);
        assertThat(natureEligibilite1).isNotEqualTo(natureEligibilite2);
    }
}
