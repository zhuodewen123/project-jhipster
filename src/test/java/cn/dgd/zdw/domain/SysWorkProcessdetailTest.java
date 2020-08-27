package cn.dgd.zdw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class SysWorkProcessdetailTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysWorkProcessdetail.class);
        SysWorkProcessdetail sysWorkProcessdetail1 = new SysWorkProcessdetail();
        sysWorkProcessdetail1.setId(1L);
        SysWorkProcessdetail sysWorkProcessdetail2 = new SysWorkProcessdetail();
        sysWorkProcessdetail2.setId(sysWorkProcessdetail1.getId());
        assertThat(sysWorkProcessdetail1).isEqualTo(sysWorkProcessdetail2);
        sysWorkProcessdetail2.setId(2L);
        assertThat(sysWorkProcessdetail1).isNotEqualTo(sysWorkProcessdetail2);
        sysWorkProcessdetail1.setId(null);
        assertThat(sysWorkProcessdetail1).isNotEqualTo(sysWorkProcessdetail2);
    }
}
