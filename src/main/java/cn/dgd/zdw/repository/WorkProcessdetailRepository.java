package cn.dgd.zdw.repository;

import cn.dgd.zdw.domain.WorkProcessdetail;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the WorkProcessdetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkProcessdetailRepository extends JpaRepository<WorkProcessdetail, Long>, JpaSpecificationExecutor<WorkProcessdetail> {
}
