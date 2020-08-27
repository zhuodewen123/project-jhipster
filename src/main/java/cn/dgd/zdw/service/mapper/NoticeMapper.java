package cn.dgd.zdw.service.mapper;


import cn.dgd.zdw.domain.*;
import cn.dgd.zdw.service.dto.NoticeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notice} and its DTO {@link NoticeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NoticeMapper extends EntityMapper<NoticeDTO, Notice> {



    default Notice fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notice notice = new Notice();
        notice.setId(id);
        return notice;
    }
}
