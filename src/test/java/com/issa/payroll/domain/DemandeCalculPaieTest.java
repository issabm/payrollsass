package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemandeCalculPaieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemandeCalculPaie.class);
        DemandeCalculPaie demandeCalculPaie1 = new DemandeCalculPaie();
        demandeCalculPaie1.setId(1L);
        DemandeCalculPaie demandeCalculPaie2 = new DemandeCalculPaie();
        demandeCalculPaie2.setId(demandeCalculPaie1.getId());
        assertThat(demandeCalculPaie1).isEqualTo(demandeCalculPaie2);
        demandeCalculPaie2.setId(2L);
        assertThat(demandeCalculPaie1).isNotEqualTo(demandeCalculPaie2);
        demandeCalculPaie1.setId(null);
        assertThat(demandeCalculPaie1).isNotEqualTo(demandeCalculPaie2);
    }
}
