package cn.dgd.zdw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class TSysWorkProcessdetailTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TSysWorkProcessdetail.class);
        TSysWorkProcessdetail tSysWorkProcessdetail1 = new TSysWorkProcessdetail();
        tSysWorkProcessdetail1.setId(1L);
        TSysWorkProcessdetail tSysWorkProcessdetail2 = new TSysWorkProcessdetail();
        tSysWorkProcessdetail2.setId(tSysWorkProcessdetail1.getId());
        assertThat(tSysWorkProcessdetail1).isEqualTo(tSysWorkProcessdetail2);
        tSysWorkProcessdetail2.setId(2L);
        assertThat(tSysWorkProcessdetail1).isNotEqualTo(tSysWorkProcessdetail2);
        tSysWorkProcessdetail1.setId(null);
        assertThat(tSysWorkProcessdetail1).isNotEqualTo(tSysWorkProcessdetail2);
    }
}
