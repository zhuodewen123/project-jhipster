package cn.dgd.zdw.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class SysWorkProcessDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysWorkProcessDTO.class);
        SysWorkProcessDTO sysWorkProcessDTO1 = new SysWorkProcessDTO();
        sysWorkProcessDTO1.setId(1L);
        SysWorkProcessDTO sysWorkProcessDTO2 = new SysWorkProcessDTO();
        assertThat(sysWorkProcessDTO1).isNotEqualTo(sysWorkProcessDTO2);
        sysWorkProcessDTO2.setId(sysWorkProcessDTO1.getId());
        assertThat(sysWorkProcessDTO1).isEqualTo(sysWorkProcessDTO2);
        sysWorkProcessDTO2.setId(2L);
        assertThat(sysWorkProcessDTO1).isNotEqualTo(sysWorkProcessDTO2);
        sysWorkProcessDTO1.setId(null);
        assertThat(sysWorkProcessDTO1).isNotEqualTo(sysWorkProcessDTO2);
    }
}
