package cn.dgd.zdw.repository;

import cn.dgd.zdw.domain.SysWorkProcess;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SysWorkProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysWorkProcessRepository extends JpaRepository<SysWorkProcess, Long>, JpaSpecificationExecutor<SysWorkProcess> {
}
