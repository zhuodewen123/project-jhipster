package cn.dgd.zdw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SysWorkProcessMapperTest {

    private SysWorkProcessMapper sysWorkProcessMapper;

    @BeforeEach
    public void setUp() {
        sysWorkProcessMapper = new SysWorkProcessMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sysWorkProcessMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sysWorkProcessMapper.fromId(null)).isNull();
    }
}
