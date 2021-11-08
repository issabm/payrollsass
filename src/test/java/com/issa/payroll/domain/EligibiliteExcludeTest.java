package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EligibiliteExcludeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EligibiliteExclude.class);
        EligibiliteExclude eligibiliteExclude1 = new EligibiliteExclude();
        eligibiliteExclude1.setId(1L);
        EligibiliteExclude eligibiliteExclude2 = new EligibiliteExclude();
        eligibiliteExclude2.setId(eligibiliteExclude1.getId());
        assertThat(eligibiliteExclude1).isEqualTo(eligibiliteExclude2);
        eligibiliteExclude2.setId(2L);
        assertThat(eligibiliteExclude1).isNotEqualTo(eligibiliteExclude2);
        eligibiliteExclude1.setId(null);
        assertThat(eligibiliteExclude1).isNotEqualTo(eligibiliteExclude2);
    }
}
