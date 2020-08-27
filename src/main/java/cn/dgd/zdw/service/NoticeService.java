package cn.dgd.zdw.service;

import cn.dgd.zdw.domain.Notice;
import cn.dgd.zdw.repository.NoticeRepository;
import cn.dgd.zdw.service.dto.NoticeDTO;
import cn.dgd.zdw.service.mapper.NoticeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Notice}.
 */
@Service
@Transactional
public class NoticeService {

    private final Logger log = LoggerFactory.getLogger(NoticeService.class);

    private final NoticeRepository noticeRepository;

    private final NoticeMapper noticeMapper;

    public NoticeService(NoticeRepository noticeRepository, NoticeMapper noticeMapper) {
        this.noticeRepository = noticeRepository;
        this.noticeMapper = noticeMapper;
    }

    /**
     * Save a notice.
     *
     * @param noticeDTO the entity to save.
     * @return the persisted entity.
     */
    public NoticeDTO save(NoticeDTO noticeDTO) {
        log.debug("Request to save Notice : {}", noticeDTO);
        Notice notice = noticeMapper.toEntity(noticeDTO);
        notice = noticeRepository.save(notice);
        return noticeMapper.toDto(notice);
    }

    /**
     * Get all the notices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NoticeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Notices");
        return noticeRepository.findAll(pageable)
            .map(noticeMapper::toDto);
    }


    /**
     * Get one notice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NoticeDTO> findOne(Long id) {
        log.debug("Request to get Notice : {}", id);
        return noticeRepository.findById(id)
            .map(noticeMapper::toDto);
    }

    /**
     * Delete the notice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Notice : {}", id);
        noticeRepository.deleteById(id);
    }
}
