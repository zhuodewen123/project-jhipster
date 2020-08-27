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

import cn.dgd.zdw.domain.WorkProcess;
import cn.dgd.zdw.domain.*; // for static metamodels
import cn.dgd.zdw.repository.WorkProcessRepository;
import cn.dgd.zdw.service.dto.WorkProcessCriteria;
import cn.dgd.zdw.service.dto.WorkProcessDTO;
import cn.dgd.zdw.service.mapper.WorkProcessMapper;

/**
 * Service for executing complex queries for {@link WorkProcess} entities in the database.
 * The main input is a {@link WorkProcessCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WorkProcessDTO} or a {@link Page} of {@link WorkProcessDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WorkProcessQueryService extends QueryService<WorkProcess> {

    private final Logger log = LoggerFactory.getLogger(WorkProcessQueryService.class);

    private final WorkProcessRepository workProcessRepository;

    private final WorkProcessMapper workProcessMapper;

    public WorkProcessQueryService(WorkProcessRepository workProcessRepository, WorkProcessMapper workProcessMapper) {
        this.workProcessRepository = workProcessRepository;
        this.workProcessMapper = workProcessMapper;
    }

    /**
     * Return a {@link List} of {@link WorkProcessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WorkProcessDTO> findByCriteria(WorkProcessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WorkProcess> specification = createSpecification(criteria);
        return workProcessMapper.toDto(workProcessRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WorkProcessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkProcessDTO> findByCriteria(WorkProcessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WorkProcess> specification = createSpecification(criteria);
        return workProcessRepository.findAll(specification, page)
            .map(workProcessMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WorkProcessCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WorkProcess> specification = createSpecification(criteria);
        return workProcessRepository.count(specification);
    }

    /**
     * Function to convert {@link WorkProcessCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<WorkProcess> createSpecification(WorkProcessCriteria criteria) {
        Specification<WorkProcess> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), WorkProcess_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), WorkProcess_.title));
            }
            if (criteria.getReceiveDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceiveDate(), WorkProcess_.receiveDate));
            }
            if (criteria.getReceiveOrg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReceiveOrg(), WorkProcess_.receiveOrg));
            }
            if (criteria.getCurrentProcess() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentProcess(), WorkProcess_.currentProcess));
            }
        }
        return specification;
    }
}
