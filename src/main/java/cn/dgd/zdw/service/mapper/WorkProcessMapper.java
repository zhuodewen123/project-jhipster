package cn.dgd.zdw.service.mapper;


import cn.dgd.zdw.domain.*;
import cn.dgd.zdw.service.dto.WorkProcessDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkProcess} and its DTO {@link WorkProcessDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkProcessMapper extends EntityMapper<WorkProcessDTO, WorkProcess> {



    default WorkProcess fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkProcess workProcess = new WorkProcess();
        workProcess.setId(id);
        return workProcess;
    }
}
