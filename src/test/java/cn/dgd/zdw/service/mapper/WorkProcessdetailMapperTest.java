package cn.dgd.zdw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkProcessdetailMapperTest {

    private WorkProcessdetailMapper workProcessdetailMapper;

    @BeforeEach
    public void setUp() {
        workProcessdetailMapper = new WorkProcessdetailMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(workProcessdetailMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(workProcessdetailMapper.fromId(null)).isNull();
    }
}
