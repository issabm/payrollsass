package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ModeInputTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModeInput.class);
        ModeInput modeInput1 = new ModeInput();
        modeInput1.setId(1L);
        ModeInput modeInput2 = new ModeInput();
        modeInput2.setId(modeInput1.getId());
        assertThat(modeInput1).isEqualTo(modeInput2);
        modeInput2.setId(2L);
        assertThat(modeInput1).isNotEqualTo(modeInput2);
        modeInput1.setId(null);
        assertThat(modeInput1).isNotEqualTo(modeInput2);
    }
}
