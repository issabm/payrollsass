package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NatureMvtPaieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NatureMvtPaie.class);
        NatureMvtPaie natureMvtPaie1 = new NatureMvtPaie();
        natureMvtPaie1.setId(1L);
        NatureMvtPaie natureMvtPaie2 = new NatureMvtPaie();
        natureMvtPaie2.setId(natureMvtPaie1.getId());
        assertThat(natureMvtPaie1).isEqualTo(natureMvtPaie2);
        natureMvtPaie2.setId(2L);
        assertThat(natureMvtPaie1).isNotEqualTo(natureMvtPaie2);
        natureMvtPaie1.setId(null);
        assertThat(natureMvtPaie1).isNotEqualTo(natureMvtPaie2);
    }
}
