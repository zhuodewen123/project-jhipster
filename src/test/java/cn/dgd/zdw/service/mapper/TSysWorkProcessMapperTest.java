package cn.dgd.zdw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TSysWorkProcessMapperTest {

    private TSysWorkProcessMapper tSysWorkProcessMapper;

    @BeforeEach
    public void setUp() {
        tSysWorkProcessMapper = new TSysWorkProcessMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tSysWorkProcessMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tSysWorkProcessMapper.fromId(null)).isNull();
    }
}
