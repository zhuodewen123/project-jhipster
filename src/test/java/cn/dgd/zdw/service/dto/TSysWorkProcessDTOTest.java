package cn.dgd.zdw.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class TSysWorkProcessDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TSysWorkProcessDTO.class);
        TSysWorkProcessDTO tSysWorkProcessDTO1 = new TSysWorkProcessDTO();
        tSysWorkProcessDTO1.setId(1L);
        TSysWorkProcessDTO tSysWorkProcessDTO2 = new TSysWorkProcessDTO();
        assertThat(tSysWorkProcessDTO1).isNotEqualTo(tSysWorkProcessDTO2);
        tSysWorkProcessDTO2.setId(tSysWorkProcessDTO1.getId());
        assertThat(tSysWorkProcessDTO1).isEqualTo(tSysWorkProcessDTO2);
        tSysWorkProcessDTO2.setId(2L);
        assertThat(tSysWorkProcessDTO1).isNotEqualTo(tSysWorkProcessDTO2);
        tSysWorkProcessDTO1.setId(null);
        assertThat(tSysWorkProcessDTO1).isNotEqualTo(tSysWorkProcessDTO2);
    }
}
