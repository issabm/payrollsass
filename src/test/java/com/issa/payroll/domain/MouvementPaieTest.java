package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MouvementPaieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MouvementPaie.class);
        MouvementPaie mouvementPaie1 = new MouvementPaie();
        mouvementPaie1.setId(1L);
        MouvementPaie mouvementPaie2 = new MouvementPaie();
        mouvementPaie2.setId(mouvementPaie1.getId());
        assertThat(mouvementPaie1).isEqualTo(mouvementPaie2);
        mouvementPaie2.setId(2L);
        assertThat(mouvementPaie1).isNotEqualTo(mouvementPaie2);
        mouvementPaie1.setId(null);
        assertThat(mouvementPaie1).isNotEqualTo(mouvementPaie2);
    }
}
