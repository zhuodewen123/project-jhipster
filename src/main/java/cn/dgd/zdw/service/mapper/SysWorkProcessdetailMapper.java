package cn.dgd.zdw.service.mapper;


import cn.dgd.zdw.domain.*;
import cn.dgd.zdw.service.dto.SysWorkProcessdetailDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SysWorkProcessdetail} and its DTO {@link SysWorkProcessdetailDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SysWorkProcessdetailMapper extends EntityMapper<SysWorkProcessdetailDTO, SysWorkProcessdetail> {



    default SysWorkProcessdetail fromId(Long id) {
        if (id == null) {
            return null;
        }
        SysWorkProcessdetail sysWorkProcessdetail = new SysWorkProcessdetail();
        sysWorkProcessdetail.setId(id);
        return sysWorkProcessdetail;
    }
}
