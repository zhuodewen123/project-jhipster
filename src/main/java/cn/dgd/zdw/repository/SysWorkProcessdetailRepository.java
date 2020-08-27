package cn.dgd.zdw.repository;

import cn.dgd.zdw.domain.SysWorkProcessdetail;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SysWorkProcessdetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysWorkProcessdetailRepository extends JpaRepository<SysWorkProcessdetail, Long>, JpaSpecificationExecutor<SysWorkProcessdetail> {
}
