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

import cn.dgd.zdw.domain.Notice;
import cn.dgd.zdw.domain.*; // for static metamodels
import cn.dgd.zdw.repository.NoticeRepository;
import cn.dgd.zdw.service.dto.NoticeCriteria;
import cn.dgd.zdw.service.dto.NoticeDTO;
import cn.dgd.zdw.service.mapper.NoticeMapper;

/**
 * Service for executing complex queries for {@link Notice} entities in the database.
 * The main input is a {@link NoticeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NoticeDTO} or a {@link Page} of {@link NoticeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NoticeQueryService extends QueryService<Notice> {

    private final Logger log = LoggerFactory.getLogger(NoticeQueryService.class);

    private final NoticeRepository noticeRepository;

    private final NoticeMapper noticeMapper;

    public NoticeQueryService(NoticeRepository noticeRepository, NoticeMapper noticeMapper) {
        this.noticeRepository = noticeRepository;
        this.noticeMapper = noticeMapper;
    }

    /**
     * Return a {@link List} of {@link NoticeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NoticeDTO> findByCriteria(NoticeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Notice> specification = createSpecification(criteria);
        return noticeMapper.toDto(noticeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link NoticeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NoticeDTO> findByCriteria(NoticeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Notice> specification = createSpecification(criteria);
        return noticeRepository.findAll(specification, page)
            .map(noticeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NoticeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Notice> specification = createSpecification(criteria);
        return noticeRepository.count(specification);
    }

    /**
     * Function to convert {@link NoticeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Notice> createSpecification(NoticeCriteria criteria) {
        Specification<Notice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Notice_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Notice_.title));
            }
            if (criteria.getContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContent(), Notice_.content));
            }
            if (criteria.getSender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSender(), Notice_.sender));
            }
            if (criteria.getSendDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSendDate(), Notice_.sendDate));
            }
            if (criteria.getReceiver() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceiver(), Notice_.receiver));
            }
            if (criteria.getAuditOrg() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAuditOrg(), Notice_.auditOrg));
            }
            if (criteria.getDelFlag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDelFlag(), Notice_.delFlag));
            }
        }
        return specification;
    }
}
