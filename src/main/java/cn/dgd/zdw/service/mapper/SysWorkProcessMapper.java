package cn.dgd.zdw.service.mapper;


import cn.dgd.zdw.domain.*;
import cn.dgd.zdw.service.dto.SysWorkProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysWorkProcess} and its DTO {@link SysWorkProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysWorkProcessMapper extends EntityMapper<SysWorkProcessDTO, SysWorkProcess> {



    default SysWorkProcess fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysWorkProcess sysWorkProcess = new SysWorkProcess();
        sysWorkProcess.setId(id);
        return sysWorkProcess;
    }
}
