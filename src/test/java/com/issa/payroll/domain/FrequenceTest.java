package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FrequenceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Frequence.class);
        Frequence frequence1 = new Frequence();
        frequence1.setId(1L);
        Frequence frequence2 = new Frequence();
        frequence2.setId(frequence1.getId());
        assertThat(frequence1).isEqualTo(frequence2);
        frequence2.setId(2L);
        assertThat(frequence1).isNotEqualTo(frequence2);
        frequence1.setId(null);
        assertThat(frequence1).isNotEqualTo(frequence2);
    }
}
