package cn.dgd.zdw.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class SysWorkProcessdetailDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SysWorkProcessdetailDTO.class);
        SysWorkProcessdetailDTO sysWorkProcessdetailDTO1 = new SysWorkProcessdetailDTO();
        sysWorkProcessdetailDTO1.setId(1L);
        SysWorkProcessdetailDTO sysWorkProcessdetailDTO2 = new SysWorkProcessdetailDTO();
        assertThat(sysWorkProcessdetailDTO1).isNotEqualTo(sysWorkProcessdetailDTO2);
        sysWorkProcessdetailDTO2.setId(sysWorkProcessdetailDTO1.getId());
        assertThat(sysWorkProcessdetailDTO1).isEqualTo(sysWorkProcessdetailDTO2);
        sysWorkProcessdetailDTO2.setId(2L);
        assertThat(sysWorkProcessdetailDTO1).isNotEqualTo(sysWorkProcessdetailDTO2);
        sysWorkProcessdetailDTO1.setId(null);
        assertThat(sysWorkProcessdetailDTO1).isNotEqualTo(sysWorkProcessdetailDTO2);
    }
}
