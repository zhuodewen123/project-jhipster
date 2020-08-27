package cn.dgd.zdw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SysWorkProcessdetailMapperTest {

    private SysWorkProcessdetailMapper sysWorkProcessdetailMapper;

    @BeforeEach
    public void setUp() {
        sysWorkProcessdetailMapper = new SysWorkProcessdetailMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sysWorkProcessdetailMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sysWorkProcessdetailMapper.fromId(null)).isNull();
    }
}
