package cn.dgd.zdw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SysNoticeMapperTest {

    private SysNoticeMapper sysNoticeMapper;

    @BeforeEach
    public void setUp() {
        sysNoticeMapper = new SysNoticeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(sysNoticeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(sysNoticeMapper.fromId(null)).isNull();
    }
}
