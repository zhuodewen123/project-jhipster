package cn.dgd.zdw.repository;

import cn.dgd.zdw.domain.TSysWorkProcessdetail;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TSysWorkProcessdetail entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TSysWorkProcessdetailRepository extends JpaRepository<TSysWorkProcessdetail, Long> {
}
