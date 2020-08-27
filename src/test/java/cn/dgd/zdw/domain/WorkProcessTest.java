package cn.dgd.zdw.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import cn.dgd.zdw.web.rest.TestUtil;

public class WorkProcessTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkProcess.class);
        WorkProcess workProcess1 = new WorkProcess();
        workProcess1.setId(1L);
        WorkProcess workProcess2 = new WorkProcess();
        workProcess2.setId(workProcess1.getId());
        assertThat(workProcess1).isEqualTo(workProcess2);
        workProcess2.setId(2L);
        assertThat(workProcess1).isNotEqualTo(workProcess2);
        workProcess1.setId(null);
        assertThat(workProcess1).isNotEqualTo(workProcess2);
    }
}
