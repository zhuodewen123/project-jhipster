package cn.dgd.zdw.service.mapper;


import cn.dgd.zdw.domain.*;
import cn.dgd.zdw.service.dto.TSysWorkProcessdetailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TSysWorkProcessdetail} and its DTO {@link TSysWorkProcessdetailDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TSysWorkProcessdetailMapper extends EntityMapper<TSysWorkProcessdetailDTO, TSysWorkProcessdetail> {



    default TSysWorkProcessdetail fromId(Long id) {
        if (id == null) {
            return null;
        }
        TSysWorkProcessdetail tSysWorkProcessdetail = new TSysWorkProcessdetail();
        tSysWorkProcessdetail.setId(id);
        return tSysWorkProcessdetail;
    }
}
