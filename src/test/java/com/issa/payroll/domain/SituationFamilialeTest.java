package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SituationFamilialeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SituationFamiliale.class);
        SituationFamiliale situationFamiliale1 = new SituationFamiliale();
        situationFamiliale1.setId(1L);
        SituationFamiliale situationFamiliale2 = new SituationFamiliale();
        situationFamiliale2.setId(situationFamiliale1.getId());
        assertThat(situationFamiliale1).isEqualTo(situationFamiliale2);
        situationFamiliale2.setId(2L);
        assertThat(situationFamiliale1).isNotEqualTo(situationFamiliale2);
        situationFamiliale1.setId(null);
        assertThat(situationFamiliale1).isNotEqualTo(situationFamiliale2);
    }
}
