package cn.dgd.zdw.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class WorkProcessdetailDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkProcessdetailDTO.class);
        WorkProcessdetailDTO workProcessdetailDTO1 = new WorkProcessdetailDTO();
        workProcessdetailDTO1.setId(1L);
        WorkProcessdetailDTO workProcessdetailDTO2 = new WorkProcessdetailDTO();
        assertThat(workProcessdetailDTO1).isNotEqualTo(workProcessdetailDTO2);
        workProcessdetailDTO2.setId(workProcessdetailDTO1.getId());
        assertThat(workProcessdetailDTO1).isEqualTo(workProcessdetailDTO2);
        workProcessdetailDTO2.setId(2L);
        assertThat(workProcessdetailDTO1).isNotEqualTo(workProcessdetailDTO2);
        workProcessdetailDTO1.setId(null);
        assertThat(workProcessdetailDTO1).isNotEqualTo(workProcessdetailDTO2);
    }
}
