package cn.dgd.zdw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class TSysWorkProcessTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TSysWorkProcess.class);
        TSysWorkProcess tSysWorkProcess1 = new TSysWorkProcess();
        tSysWorkProcess1.setId(1L);
        TSysWorkProcess tSysWorkProcess2 = new TSysWorkProcess();
        tSysWorkProcess2.setId(tSysWorkProcess1.getId());
        assertThat(tSysWorkProcess1).isEqualTo(tSysWorkProcess2);
        tSysWorkProcess2.setId(2L);
        assertThat(tSysWorkProcess1).isNotEqualTo(tSysWorkProcess2);
        tSysWorkProcess1.setId(null);
        assertThat(tSysWorkProcess1).isNotEqualTo(tSysWorkProcess2);
    }
}
