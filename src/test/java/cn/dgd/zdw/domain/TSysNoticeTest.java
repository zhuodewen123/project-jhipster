package cn.dgd.zdw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class TSysNoticeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TSysNotice.class);
        TSysNotice tSysNotice1 = new TSysNotice();
        tSysNotice1.setId(1L);
        TSysNotice tSysNotice2 = new TSysNotice();
        tSysNotice2.setId(tSysNotice1.getId());
        assertThat(tSysNotice1).isEqualTo(tSysNotice2);
        tSysNotice2.setId(2L);
        assertThat(tSysNotice1).isNotEqualTo(tSysNotice2);
        tSysNotice1.setId(null);
        assertThat(tSysNotice1).isNotEqualTo(tSysNotice2);
    }
}
