package cn.dgd.zdw.service.mapper;


import cn.dgd.zdw.domain.*;
import cn.dgd.zdw.service.dto.TSysNoticeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TSysNotice} and its DTO {@link TSysNoticeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TSysNoticeMapper extends EntityMapper<TSysNoticeDTO, TSysNotice> {



    default TSysNotice fromId(Long id) {
        if (id == null) {
            return null;
        }
        TSysNotice tSysNotice = new TSysNotice();
        tSysNotice.setId(id);
        return tSysNotice;
    }
}
