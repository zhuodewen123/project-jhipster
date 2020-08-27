package cn.dgd.zdw.service.mapper;


import cn.dgd.zdw.domain.*;
import cn.dgd.zdw.service.dto.SysNoticeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysNotice} and its DTO {@link SysNoticeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysNoticeMapper extends EntityMapper<SysNoticeDTO, SysNotice> {



    default SysNotice fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysNotice sysNotice = new SysNotice();
        sysNotice.setId(id);
        return sysNotice;
    }
}
