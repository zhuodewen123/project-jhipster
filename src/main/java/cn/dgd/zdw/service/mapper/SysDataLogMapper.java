package cn.dgd.zdw.service.mapper;


import cn.dgd.zdw.domain.*;
import cn.dgd.zdw.service.dto.SysDataLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysDataLog} and its DTO {@link SysDataLogDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysDataLogMapper extends EntityMapper<SysDataLogDTO, SysDataLog> {



    default SysDataLog fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysDataLog sysDataLog = new SysDataLog();
        sysDataLog.setId(id);
        return sysDataLog;
    }
}
