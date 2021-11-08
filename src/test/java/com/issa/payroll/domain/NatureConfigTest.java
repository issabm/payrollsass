package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NatureConfigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureConfig.class);
        NatureConfig natureConfig1 = new NatureConfig();
        natureConfig1.setId(1L);
        NatureConfig natureConfig2 = new NatureConfig();
        natureConfig2.setId(natureConfig1.getId());
        assertThat(natureConfig1).isEqualTo(natureConfig2);
        natureConfig2.setId(2L);
        assertThat(natureConfig1).isNotEqualTo(natureConfig2);
        natureConfig1.setId(null);
        assertThat(natureConfig1).isNotEqualTo(natureConfig2);
    }
}
