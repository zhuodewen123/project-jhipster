package cn.dgd.zdw.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import cn.dgd.zdw.domain.SysWorkProcessdetail;
import cn.dgd.zdw.domain.*; // for static metamodels
import cn.dgd.zdw.repository.SysWorkProcessdetailRepository;
import cn.dgd.zdw.service.dto.SysWorkProcessdetailCriteria;
import cn.dgd.zdw.service.dto.SysWorkProcessdetailDTO;
import cn.dgd.zdw.service.mapper.SysWorkProcessdetailMapper;

/**
 * Service for executing complex queries for {@link SysWorkProcessdetail} entities in the database.
 * The main input is a {@link SysWorkProcessdetailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SysWorkProcessdetailDTO} or a {@link Page} of {@link SysWorkProcessdetailDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SysWorkProcessdetailQueryService extends QueryService<SysWorkProcessdetail> {

    private final Logger log = LoggerFactory.getLogger(SysWorkProcessdetailQueryService.class);

    private final SysWorkProcessdetailRepository sysWorkProcessdetailRepository;

    private final SysWorkProcessdetailMapper sysWorkProcessdetailMapper;

    public SysWorkProcessdetailQueryService(SysWorkProcessdetailRepository sysWorkProcessdetailRepository, SysWorkProcessdetailMapper sysWorkProcessdetailMapper) {
        this.sysWorkProcessdetailRepository = sysWorkProcessdetailRepository;
        this.sysWorkProcessdetailMapper = sysWorkProcessdetailMapper;
    }

    /**
     * Return a {@link List} of {@link SysWorkProcessdetailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SysWorkProcessdetailDTO> findByCriteria(SysWorkProcessdetailCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SysWorkProcessdetail> specification = createSpecification(criteria);
        return sysWorkProcessdetailMapper.toDto(sysWorkProcessdetailRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SysWorkProcessdetailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SysWorkProcessdetailDTO> findByCriteria(SysWorkProcessdetailCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SysWorkProcessdetail> specification = createSpecification(criteria);
        return sysWorkProcessdetailRepository.findAll(specification, page)
            .map(sysWorkProcessdetailMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SysWorkProcessdetailCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SysWorkProcessdetail> specification = createSpecification(criteria);
        return sysWorkProcessdetailRepository.count(specification);
    }

    /**
     * Function to convert {@link SysWorkProcessdetailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SysWorkProcessdetail> createSpecification(SysWorkProcessdetailCriteria criteria) {
        Specification<SysWorkProcessdetail> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SysWorkProcessdetail_.id));
            }
            if (criteria.getFkWorkProcessId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFkWorkProcessId(), SysWorkProcessdetail_.fkWorkProcessId));
            }
            if (criteria.getTodoOrg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTodoOrg(), SysWorkProcessdetail_.todoOrg));
            }
            if (criteria.getResult() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResult(), SysWorkProcessdetail_.result));
            }
            if (criteria.getApprovalComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApprovalComments(), SysWorkProcessdetail_.approvalComments));
            }
            if (criteria.getReceiveTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceiveTime(), SysWorkProcessdetail_.receiveTime));
            }
            if (criteria.getApprovalTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApprovalTime(), SysWorkProcessdetail_.approvalTime));
            }
        }
        return specification;
    }
}
