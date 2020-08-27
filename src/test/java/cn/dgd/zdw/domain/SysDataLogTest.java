package cn.dgd.zdw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class SysDataLogTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysDataLog.class);
        SysDataLog sysDataLog1 = new SysDataLog();
        sysDataLog1.setId(1L);
        SysDataLog sysDataLog2 = new SysDataLog();
        sysDataLog2.setId(sysDataLog1.getId());
        assertThat(sysDataLog1).isEqualTo(sysDataLog2);
        sysDataLog2.setId(2L);
        assertThat(sysDataLog1).isNotEqualTo(sysDataLog2);
        sysDataLog1.setId(null);
        assertThat(sysDataLog1).isNotEqualTo(sysDataLog2);
    }
}
