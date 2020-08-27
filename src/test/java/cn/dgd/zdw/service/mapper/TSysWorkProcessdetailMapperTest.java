package cn.dgd.zdw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TSysWorkProcessdetailMapperTest {

    private TSysWorkProcessdetailMapper tSysWorkProcessdetailMapper;

    @BeforeEach
    public void setUp() {
        tSysWorkProcessdetailMapper = new TSysWorkProcessdetailMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tSysWorkProcessdetailMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tSysWorkProcessdetailMapper.fromId(null)).isNull();
    }
}
