package cn.dgd.zdw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SysDataLogMapperTest {

    private SysDataLogMapper sysDataLogMapper;

    @BeforeEach
    public void setUp() {
        sysDataLogMapper = new SysDataLogMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sysDataLogMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sysDataLogMapper.fromId(null)).isNull();
    }
}
