package cn.dgd.zdw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class SysWorkProcessTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysWorkProcess.class);
        SysWorkProcess sysWorkProcess1 = new SysWorkProcess();
        sysWorkProcess1.setId(1L);
        SysWorkProcess sysWorkProcess2 = new SysWorkProcess();
        sysWorkProcess2.setId(sysWorkProcess1.getId());
        assertThat(sysWorkProcess1).isEqualTo(sysWorkProcess2);
        sysWorkProcess2.setId(2L);
        assertThat(sysWorkProcess1).isNotEqualTo(sysWorkProcess2);
        sysWorkProcess1.setId(null);
        assertThat(sysWorkProcess1).isNotEqualTo(sysWorkProcess2);
    }
}
