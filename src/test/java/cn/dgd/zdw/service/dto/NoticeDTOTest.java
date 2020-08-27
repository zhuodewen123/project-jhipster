package cn.dgd.zdw.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class NoticeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(NoticeDTO.class);
        NoticeDTO noticeDTO1 = new NoticeDTO();
        noticeDTO1.setId(1L);
        NoticeDTO noticeDTO2 = new NoticeDTO();
        assertThat(noticeDTO1).isNotEqualTo(noticeDTO2);
        noticeDTO2.setId(noticeDTO1.getId());
        assertThat(noticeDTO1).isEqualTo(noticeDTO2);
        noticeDTO2.setId(2L);
        assertThat(noticeDTO1).isNotEqualTo(noticeDTO2);
        noticeDTO1.setId(null);
        assertThat(noticeDTO1).isNotEqualTo(noticeDTO2);
    }
}
