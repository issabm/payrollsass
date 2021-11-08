package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlateInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlateInfo.class);
        PlateInfo plateInfo1 = new PlateInfo();
        plateInfo1.setId(1L);
        PlateInfo plateInfo2 = new PlateInfo();
        plateInfo2.setId(plateInfo1.getId());
        assertThat(plateInfo1).isEqualTo(plateInfo2);
        plateInfo2.setId(2L);
        assertThat(plateInfo1).isNotEqualTo(plateInfo2);
        plateInfo1.setId(null);
        assertThat(plateInfo1).isNotEqualTo(plateInfo2);
    }
}
