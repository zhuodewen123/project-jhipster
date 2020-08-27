package cn.dgd.zdw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class NoticeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notice.class);
        Notice notice1 = new Notice();
        notice1.setId(1L);
        Notice notice2 = new Notice();
        notice2.setId(notice1.getId());
        assertThat(notice1).isEqualTo(notice2);
        notice2.setId(2L);
        assertThat(notice1).isNotEqualTo(notice2);
        notice1.setId(null);
        assertThat(notice1).isNotEqualTo(notice2);
    }
}
