package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PalierPlateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PalierPlate.class);
        PalierPlate palierPlate1 = new PalierPlate();
        palierPlate1.setId(1L);
        PalierPlate palierPlate2 = new PalierPlate();
        palierPlate2.setId(palierPlate1.getId());
        assertThat(palierPlate1).isEqualTo(palierPlate2);
        palierPlate2.setId(2L);
        assertThat(palierPlate1).isNotEqualTo(palierPlate2);
        palierPlate1.setId(null);
        assertThat(palierPlate1).isNotEqualTo(palierPlate2);
    }
}
