package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TypeHandicapTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeHandicap.class);
        TypeHandicap typeHandicap1 = new TypeHandicap();
        typeHandicap1.setId(1L);
        TypeHandicap typeHandicap2 = new TypeHandicap();
        typeHandicap2.setId(typeHandicap1.getId());
        assertThat(typeHandicap1).isEqualTo(typeHandicap2);
        typeHandicap2.setId(2L);
        assertThat(typeHandicap1).isNotEqualTo(typeHandicap2);
        typeHandicap1.setId(null);
        assertThat(typeHandicap1).isNotEqualTo(typeHandicap2);
    }
}
