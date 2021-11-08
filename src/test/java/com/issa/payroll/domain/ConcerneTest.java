package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConcerneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Concerne.class);
        Concerne concerne1 = new Concerne();
        concerne1.setId(1L);
        Concerne concerne2 = new Concerne();
        concerne2.setId(concerne1.getId());
        assertThat(concerne1).isEqualTo(concerne2);
        concerne2.setId(2L);
        assertThat(concerne1).isNotEqualTo(concerne2);
        concerne1.setId(null);
        assertThat(concerne1).isNotEqualTo(concerne2);
    }
}
