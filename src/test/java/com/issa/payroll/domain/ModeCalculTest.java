package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModeCalculTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeCalcul.class);
        ModeCalcul modeCalcul1 = new ModeCalcul();
        modeCalcul1.setId(1L);
        ModeCalcul modeCalcul2 = new ModeCalcul();
        modeCalcul2.setId(modeCalcul1.getId());
        assertThat(modeCalcul1).isEqualTo(modeCalcul2);
        modeCalcul2.setId(2L);
        assertThat(modeCalcul1).isNotEqualTo(modeCalcul2);
        modeCalcul1.setId(null);
        assertThat(modeCalcul1).isNotEqualTo(modeCalcul2);
    }
}
