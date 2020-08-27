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

import cn.dgd.zdw.domain.SysWorkProcess;
import cn.dgd.zdw.domain.*; // for static metamodels
import cn.dgd.zdw.repository.SysWorkProcessRepository;
import cn.dgd.zdw.service.dto.SysWorkProcessCriteria;
import cn.dgd.zdw.service.dto.SysWorkProcessDTO;
import cn.dgd.zdw.service.mapper.SysWorkProcessMapper;

/**
 * Service for executing complex queries for {@link SysWorkProcess} entities in the database.
 * The main input is a {@link SysWorkProcessCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SysWorkProcessDTO} or a {@link Page} of {@link SysWorkProcessDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SysWorkProcessQueryService extends QueryService<SysWorkProcess> {

    private final Logger log = LoggerFactory.getLogger(SysWorkProcessQueryService.class);

    private final SysWorkProcessRepository sysWorkProcessRepository;

    private final SysWorkProcessMapper sysWorkProcessMapper;

    public SysWorkProcessQueryService(SysWorkProcessRepository sysWorkProcessRepository, SysWorkProcessMapper sysWorkProcessMapper) {
        this.sysWorkProcessRepository = sysWorkProcessRepository;
        this.sysWorkProcessMapper = sysWorkProcessMapper;
    }

    /**
     * Return a {@link List} of {@link SysWorkProcessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SysWorkProcessDTO> findByCriteria(SysWorkProcessCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SysWorkProcess> specification = createSpecification(criteria);
        return sysWorkProcessMapper.toDto(sysWorkProcessRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SysWorkProcessDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SysWorkProcessDTO> findByCriteria(SysWorkProcessCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SysWorkProcess> specification = createSpecification(criteria);
        return sysWorkProcessRepository.findAll(specification, page)
            .map(sysWorkProcessMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SysWorkProcessCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SysWorkProcess> specification = createSpecification(criteria);
        return sysWorkProcessRepository.count(specification);
    }

    /**
     * Function to convert {@link SysWorkProcessCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SysWorkProcess> createSpecification(SysWorkProcessCriteria criteria) {
        Specification<SysWorkProcess> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SysWorkProcess_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), SysWorkProcess_.title));
            }
            if (criteria.getReceiveDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceiveDate(), SysWorkProcess_.receiveDate));
            }
            if (criteria.getReceiveOrg() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReceiveOrg(), SysWorkProcess_.receiveOrg));
            }
            if (criteria.getCurrentProcess() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentProcess(), SysWorkProcess_.currentProcess));
            }
        }
        return specification;
    }
}
