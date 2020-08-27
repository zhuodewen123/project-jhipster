package cn.dgd.zdw.repository;

import cn.dgd.zdw.domain.WorkProcess;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkProcess entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkProcessRepository extends JpaRepository<WorkProcess, Long>, JpaSpecificationExecutor<WorkProcess> {
}
