package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ManagementResourceFunTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManagementResourceFun.class);
        ManagementResourceFun managementResourceFun1 = new ManagementResourceFun();
        managementResourceFun1.setId(1L);
        ManagementResourceFun managementResourceFun2 = new ManagementResourceFun();
        managementResourceFun2.setId(managementResourceFun1.getId());
        assertThat(managementResourceFun1).isEqualTo(managementResourceFun2);
        managementResourceFun2.setId(2L);
        assertThat(managementResourceFun1).isNotEqualTo(managementResourceFun2);
        managementResourceFun1.setId(null);
        assertThat(managementResourceFun1).isNotEqualTo(managementResourceFun2);
    }
}
