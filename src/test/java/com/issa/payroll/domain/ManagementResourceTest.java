package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ManagementResourceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManagementResource.class);
        ManagementResource managementResource1 = new ManagementResource();
        managementResource1.setId(1L);
        ManagementResource managementResource2 = new ManagementResource();
        managementResource2.setId(managementResource1.getId());
        assertThat(managementResource1).isEqualTo(managementResource2);
        managementResource2.setId(2L);
        assertThat(managementResource1).isNotEqualTo(managementResource2);
        managementResource1.setId(null);
        assertThat(managementResource1).isNotEqualTo(managementResource2);
    }
}
