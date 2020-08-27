package cn.dgd.zdw.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class TSysNoticeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TSysNoticeDTO.class);
        TSysNoticeDTO tSysNoticeDTO1 = new TSysNoticeDTO();
        tSysNoticeDTO1.setId(1L);
        TSysNoticeDTO tSysNoticeDTO2 = new TSysNoticeDTO();
        assertThat(tSysNoticeDTO1).isNotEqualTo(tSysNoticeDTO2);
        tSysNoticeDTO2.setId(tSysNoticeDTO1.getId());
        assertThat(tSysNoticeDTO1).isEqualTo(tSysNoticeDTO2);
        tSysNoticeDTO2.setId(2L);
        assertThat(tSysNoticeDTO1).isNotEqualTo(tSysNoticeDTO2);
        tSysNoticeDTO1.setId(null);
        assertThat(tSysNoticeDTO1).isNotEqualTo(tSysNoticeDTO2);
    }
}
