package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntityAdhesionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntityAdhesion.class);
        EntityAdhesion entityAdhesion1 = new EntityAdhesion();
        entityAdhesion1.setId(1L);
        EntityAdhesion entityAdhesion2 = new EntityAdhesion();
        entityAdhesion2.setId(entityAdhesion1.getId());
        assertThat(entityAdhesion1).isEqualTo(entityAdhesion2);
        entityAdhesion2.setId(2L);
        assertThat(entityAdhesion1).isNotEqualTo(entityAdhesion2);
        entityAdhesion1.setId(null);
        assertThat(entityAdhesion1).isNotEqualTo(entityAdhesion2);
    }
}
