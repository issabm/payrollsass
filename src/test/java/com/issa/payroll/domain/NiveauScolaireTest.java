package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NiveauScolaireTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NiveauScolaire.class);
        NiveauScolaire niveauScolaire1 = new NiveauScolaire();
        niveauScolaire1.setId(1L);
        NiveauScolaire niveauScolaire2 = new NiveauScolaire();
        niveauScolaire2.setId(niveauScolaire1.getId());
        assertThat(niveauScolaire1).isEqualTo(niveauScolaire2);
        niveauScolaire2.setId(2L);
        assertThat(niveauScolaire1).isNotEqualTo(niveauScolaire2);
        niveauScolaire1.setId(null);
        assertThat(niveauScolaire1).isNotEqualTo(niveauScolaire2);
    }
}
