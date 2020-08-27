package cn.dgd.zdw.service.mapper;


import cn.dgd.zdw.domain.*;
import cn.dgd.zdw.service.dto.TSysWorkProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TSysWorkProcess} and its DTO {@link TSysWorkProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TSysWorkProcessMapper extends EntityMapper<TSysWorkProcessDTO, TSysWorkProcess> {



    default TSysWorkProcess fromId(Long id) {
        if (id == null) {
            return null;
        }
        TSysWorkProcess tSysWorkProcess = new TSysWorkProcess();
        tSysWorkProcess.setId(id);
        return tSysWorkProcess;
    }
}
