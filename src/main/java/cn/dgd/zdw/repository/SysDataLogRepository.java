package cn.dgd.zdw.repository;

import cn.dgd.zdw.domain.SysDataLog;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the SysDataLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SysDataLogRepository extends JpaRepository<SysDataLog, Long> {
}
