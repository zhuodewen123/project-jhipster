package cn.dgd.zdw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class WorkProcessdetailTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkProcessdetail.class);
        WorkProcessdetail workProcessdetail1 = new WorkProcessdetail();
        workProcessdetail1.setId(1L);
        WorkProcessdetail workProcessdetail2 = new WorkProcessdetail();
        workProcessdetail2.setId(workProcessdetail1.getId());
        assertThat(workProcessdetail1).isEqualTo(workProcessdetail2);
        workProcessdetail2.setId(2L);
        assertThat(workProcessdetail1).isNotEqualTo(workProcessdetail2);
        workProcessdetail1.setId(null);
        assertThat(workProcessdetail1).isNotEqualTo(workProcessdetail2);
    }
}
