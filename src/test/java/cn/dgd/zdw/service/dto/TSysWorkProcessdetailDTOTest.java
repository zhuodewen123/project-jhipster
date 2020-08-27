package cn.dgd.zdw.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class TSysWorkProcessdetailDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TSysWorkProcessdetailDTO.class);
        TSysWorkProcessdetailDTO tSysWorkProcessdetailDTO1 = new TSysWorkProcessdetailDTO();
        tSysWorkProcessdetailDTO1.setId(1L);
        TSysWorkProcessdetailDTO tSysWorkProcessdetailDTO2 = new TSysWorkProcessdetailDTO();
        assertThat(tSysWorkProcessdetailDTO1).isNotEqualTo(tSysWorkProcessdetailDTO2);
        tSysWorkProcessdetailDTO2.setId(tSysWorkProcessdetailDTO1.getId());
        assertThat(tSysWorkProcessdetailDTO1).isEqualTo(tSysWorkProcessdetailDTO2);
        tSysWorkProcessdetailDTO2.setId(2L);
        assertThat(tSysWorkProcessdetailDTO1).isNotEqualTo(tSysWorkProcessdetailDTO2);
        tSysWorkProcessdetailDTO1.setId(null);
        assertThat(tSysWorkProcessdetailDTO1).isNotEqualTo(tSysWorkProcessdetailDTO2);
    }
}
