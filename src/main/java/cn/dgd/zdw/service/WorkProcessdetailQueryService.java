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

import cn.dgd.zdw.domain.WorkProcessdetail;
import cn.dgd.zdw.domain.*; // for static metamodels
import cn.dgd.zdw.repository.WorkProcessdetailRepository;
import cn.dgd.zdw.service.dto.WorkProcessdetailCriteria;
import cn.dgd.zdw.service.dto.WorkProcessdetailDTO;
import cn.dgd.zdw.service.mapper.WorkProcessdetailMapper;

/**
 * Service for executing complex queries for {@link WorkProcessdetail} entities in the database.
 * The main input is a {@link WorkProcessdetailCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkProcessdetailDTO} or a {@link Page} of {@link WorkProcessdetailDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkProcessdetailQueryService extends QueryService<WorkProcessdetail> {

    private final Logger log = LoggerFactory.getLogger(WorkProcessdetailQueryService.class);

    private final WorkProcessdetailRepository workProcessdetailRepository;

    private final WorkProcessdetailMapper workProcessdetailMapper;

    public WorkProcessdetailQueryService(WorkProcessdetailRepository workProcessdetailRepository, WorkProcessdetailMapper workProcessdetailMapper) {
        this.workProcessdetailRepository = workProcessdetailRepository;
        this.workProcessdetailMapper = workProcessdetailMapper;
    }

    /**
     * Return a {@link List} of {@link WorkProcessdetailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkProcessdetailDTO> findByCriteria(WorkProcessdetailCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkProcessdetail> specification = createSpecification(criteria);
        return workProcessdetailMapper.toDto(workProcessdetailRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WorkProcessdetailDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkProcessdetailDTO> findByCriteria(WorkProcessdetailCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkProcessdetail> specification = createSpecification(criteria);
        return workProcessdetailRepository.findAll(specification, page)
            .map(workProcessdetailMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkProcessdetailCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkProcessdetail> specification = createSpecification(criteria);
        return workProcessdetailRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkProcessdetailCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkProcessdetail> createSpecification(WorkProcessdetailCriteria criteria) {
        Specification<WorkProcessdetail> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkProcessdetail_.id));
            }
            if (criteria.getFkWorkProcessId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFkWorkProcessId(), WorkProcessdetail_.fkWorkProcessId));
            }
            if (criteria.getTodoOrg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTodoOrg(), WorkProcessdetail_.todoOrg));
            }
            if (criteria.getResult() != null) {
                specification = specification.and(buildStringSpecification(criteria.getResult(), WorkProcessdetail_.result));
            }
            if (criteria.getApprovalComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getApprovalComments(), WorkProcessdetail_.approvalComments));
            }
            if (criteria.getReceiveTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceiveTime(), WorkProcessdetail_.receiveTime));
            }
            if (criteria.getApprovalTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getApprovalTime(), WorkProcessdetail_.approvalTime));
            }
        }
        return specification;
    }
}
