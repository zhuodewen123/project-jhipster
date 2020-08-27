package cn.dgd.zdw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TSysNoticeMapperTest {

    private TSysNoticeMapper tSysNoticeMapper;

    @BeforeEach
    public void setUp() {
        tSysNoticeMapper = new TSysNoticeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tSysNoticeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tSysNoticeMapper.fromId(null)).isNull();
    }
}
