package cn.dgd.zdw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class WorkProcessMapperTest {

    private WorkProcessMapper workProcessMapper;

    @BeforeEach
    public void setUp() {
        workProcessMapper = new WorkProcessMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(workProcessMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(workProcessMapper.fromId(null)).isNull();
    }
}
