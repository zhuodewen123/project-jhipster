package cn.dgd.zdw.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class SysDataLogDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysDataLogDTO.class);
        SysDataLogDTO sysDataLogDTO1 = new SysDataLogDTO();
        sysDataLogDTO1.setId(1L);
        SysDataLogDTO sysDataLogDTO2 = new SysDataLogDTO();
        assertThat(sysDataLogDTO1).isNotEqualTo(sysDataLogDTO2);
        sysDataLogDTO2.setId(sysDataLogDTO1.getId());
        assertThat(sysDataLogDTO1).isEqualTo(sysDataLogDTO2);
        sysDataLogDTO2.setId(2L);
        assertThat(sysDataLogDTO1).isNotEqualTo(sysDataLogDTO2);
        sysDataLogDTO1.setId(null);
        assertThat(sysDataLogDTO1).isNotEqualTo(sysDataLogDTO2);
    }
}
