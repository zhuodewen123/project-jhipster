package cn.dgd.zdw.repository;

import cn.dgd.zdw.domain.TSysNotice;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TSysNotice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TSysNoticeRepository extends JpaRepository<TSysNotice, Long> {
}
