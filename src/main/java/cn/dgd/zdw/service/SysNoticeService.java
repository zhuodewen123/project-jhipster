package cn.dgd.zdw.service;

import cn.dgd.zdw.domain.SysNotice;
import cn.dgd.zdw.repository.SysNoticeRepository;
import cn.dgd.zdw.service.dto.SysNoticeDTO;
import cn.dgd.zdw.service.mapper.SysNoticeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SysNotice}.
 */
@Service
@Transactional
public class SysNoticeService {

    private final Logger log = LoggerFactory.getLogger(SysNoticeService.class);

    private final SysNoticeRepository sysNoticeRepository;

    private final SysNoticeMapper sysNoticeMapper;

    public SysNoticeService(SysNoticeRepository sysNoticeRepository, SysNoticeMapper sysNoticeMapper) {
        this.sysNoticeRepository = sysNoticeRepository;
        this.sysNoticeMapper = sysNoticeMapper;
    }

    /**
     * Save a sysNotice.
     *
     * @param sysNoticeDTO the entity to save.
     * @return the persisted entity.
     */
    public SysNoticeDTO save(SysNoticeDTO sysNoticeDTO) {
        log.debug("Request to save SysNotice : {}", sysNoticeDTO);
        SysNotice sysNotice = sysNoticeMapper.toEntity(sysNoticeDTO);
        sysNotice = sysNoticeRepository.save(sysNotice);
        return sysNoticeMapper.toDto(sysNotice);
    }

    /**
     * Get all the sysNotices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SysNoticeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysNotices");
        return sysNoticeRepository.findAll(pageable)
            .map(sysNoticeMapper::toDto);
    }


    /**
     * Get one sysNotice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SysNoticeDTO> findOne(Long id) {
        log.debug("Request to get SysNotice : {}", id);
        return sysNoticeRepository.findById(id)
            .map(sysNoticeMapper::toDto);
    }

    /**
     * Delete the sysNotice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SysNotice : {}", id);
        sysNoticeRepository.deleteById(id);
    }
}
