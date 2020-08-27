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

import cn.dgd.zdw.domain.SysNotice;
import cn.dgd.zdw.domain.*; // for static metamodels
import cn.dgd.zdw.repository.SysNoticeRepository;
import cn.dgd.zdw.service.dto.SysNoticeCriteria;
import cn.dgd.zdw.service.dto.SysNoticeDTO;
import cn.dgd.zdw.service.mapper.SysNoticeMapper;

/**
 * Service for executing complex queries for {@link SysNotice} entities in the database.
 * The main input is a {@link SysNoticeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link SysNoticeDTO} or a {@link Page} of {@link SysNoticeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SysNoticeQueryService extends QueryService<SysNotice> {

    private final Logger log = LoggerFactory.getLogger(SysNoticeQueryService.class);

    private final SysNoticeRepository sysNoticeRepository;

    private final SysNoticeMapper sysNoticeMapper;

    public SysNoticeQueryService(SysNoticeRepository sysNoticeRepository, SysNoticeMapper sysNoticeMapper) {
        this.sysNoticeRepository = sysNoticeRepository;
        this.sysNoticeMapper = sysNoticeMapper;
    }

    /**
     * Return a {@link List} of {@link SysNoticeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<SysNoticeDTO> findByCriteria(SysNoticeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<SysNotice> specification = createSpecification(criteria);
        return sysNoticeMapper.toDto(sysNoticeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link SysNoticeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SysNoticeDTO> findByCriteria(SysNoticeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<SysNotice> specification = createSpecification(criteria);
        return sysNoticeRepository.findAll(specification, page)
            .map(sysNoticeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SysNoticeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<SysNotice> specification = createSpecification(criteria);
        return sysNoticeRepository.count(specification);
    }

    /**
     * Function to convert {@link SysNoticeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<SysNotice> createSpecification(SysNoticeCriteria criteria) {
        Specification<SysNotice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), SysNotice_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), SysNotice_.title));
            }
            if (criteria.getContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContent(), SysNotice_.content));
            }
            if (criteria.getSender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSender(), SysNotice_.sender));
            }
            if (criteria.getSendDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSendDate(), SysNotice_.sendDate));
            }
            if (criteria.getReceiver() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceiver(), SysNotice_.receiver));
            }
            if (criteria.getAuditOrg() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAuditOrg(), SysNotice_.auditOrg));
            }
            if (criteria.getDelFlag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDelFlag(), SysNotice_.delFlag));
            }
        }
        return specification;
    }
}
