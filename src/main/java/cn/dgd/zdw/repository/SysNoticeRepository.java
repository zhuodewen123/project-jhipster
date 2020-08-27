package cn.dgd.zdw.repository;

import cn.dgd.zdw.domain.SysNotice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SysNotice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysNoticeRepository extends JpaRepository<SysNotice, Long>, JpaSpecificationExecutor<SysNotice> {
}
