package cn.dgd.zdw.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class SysNoticeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysNoticeDTO.class);
        SysNoticeDTO sysNoticeDTO1 = new SysNoticeDTO();
        sysNoticeDTO1.setId(1L);
        SysNoticeDTO sysNoticeDTO2 = new SysNoticeDTO();
        assertThat(sysNoticeDTO1).isNotEqualTo(sysNoticeDTO2);
        sysNoticeDTO2.setId(sysNoticeDTO1.getId());
        assertThat(sysNoticeDTO1).isEqualTo(sysNoticeDTO2);
        sysNoticeDTO2.setId(2L);
        assertThat(sysNoticeDTO1).isNotEqualTo(sysNoticeDTO2);
        sysNoticeDTO1.setId(null);
        assertThat(sysNoticeDTO1).isNotEqualTo(sysNoticeDTO2);
    }
}
