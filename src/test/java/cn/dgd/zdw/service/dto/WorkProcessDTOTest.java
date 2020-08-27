package cn.dgd.zdw.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class WorkProcessDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkProcessDTO.class);
        WorkProcessDTO workProcessDTO1 = new WorkProcessDTO();
        workProcessDTO1.setId(1L);
        WorkProcessDTO workProcessDTO2 = new WorkProcessDTO();
        assertThat(workProcessDTO1).isNotEqualTo(workProcessDTO2);
        workProcessDTO2.setId(workProcessDTO1.getId());
        assertThat(workProcessDTO1).isEqualTo(workProcessDTO2);
        workProcessDTO2.setId(2L);
        assertThat(workProcessDTO1).isNotEqualTo(workProcessDTO2);
        workProcessDTO1.setId(null);
        assertThat(workProcessDTO1).isNotEqualTo(workProcessDTO2);
    }
}
