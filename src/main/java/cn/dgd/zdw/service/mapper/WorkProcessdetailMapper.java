package cn.dgd.zdw.service.mapper;


import cn.dgd.zdw.domain.*;
import cn.dgd.zdw.service.dto.WorkProcessdetailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkProcessdetail} and its DTO {@link WorkProcessdetailDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkProcessdetailMapper extends EntityMapper<WorkProcessdetailDTO, WorkProcessdetail> {



    default WorkProcessdetail fromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkProcessdetail workProcessdetail = new WorkProcessdetail();
        workProcessdetail.setId(id);
        return workProcessdetail;
    }
}
