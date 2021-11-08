package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PalierConditionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PalierCondition.class);
        PalierCondition palierCondition1 = new PalierCondition();
        palierCondition1.setId(1L);
        PalierCondition palierCondition2 = new PalierCondition();
        palierCondition2.setId(palierCondition1.getId());
        assertThat(palierCondition1).isEqualTo(palierCondition2);
        palierCondition2.setId(2L);
        assertThat(palierCondition1).isNotEqualTo(palierCondition2);
        palierCondition1.setId(null);
        assertThat(palierCondition1).isNotEqualTo(palierCondition2);
    }
}
