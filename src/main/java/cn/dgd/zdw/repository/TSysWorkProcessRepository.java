package cn.dgd.zdw.repository;

import cn.dgd.zdw.domain.TSysWorkProcess;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TSysWorkProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TSysWorkProcessRepository extends JpaRepository<TSysWorkProcess, Long> {
}
