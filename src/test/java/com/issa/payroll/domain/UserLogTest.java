package com.issa.payroll.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.issa.payroll.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserLog.class);
        UserLog userLog1 = new UserLog();
        userLog1.setId(1L);
        UserLog userLog2 = new UserLog();
        userLog2.setId(userLog1.getId());
        assertThat(userLog1).isEqualTo(userLog2);
        userLog2.setId(2L);
        assertThat(userLog1).isNotEqualTo(userLog2);
        userLog1.setId(null);
        assertThat(userLog1).isNotEqualTo(userLog2);
    }
}
