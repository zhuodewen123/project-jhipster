package cn.dgd.zdw.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NoticeMapperTest {

    private NoticeMapper noticeMapper;

    @BeforeEach
    public void setUp() {
        noticeMapper = new NoticeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(noticeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(noticeMapper.fromId(null)).isNull();
    }
}
