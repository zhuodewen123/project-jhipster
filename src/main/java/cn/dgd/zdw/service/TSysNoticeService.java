package cn.dgd.zdw.service;

import cn.dgd.zdw.domain.TSysNotice;
import cn.dgd.zdw.repository.TSysNoticeRepository;
import cn.dgd.zdw.service.dto.TSysNoticeDTO;
import cn.dgd.zdw.service.mapper.TSysNoticeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TSysNotice}.
 */
@Service
@Transactional
public class TSysNoticeService {

    private final Logger log = LoggerFactory.getLogger(TSysNoticeService.class);

    private final TSysNoticeRepository tSysNoticeRepository;

    private final TSysNoticeMapper tSysNoticeMapper;

    public TSysNoticeService(TSysNoticeRepository tSysNoticeRepository, TSysNoticeMapper tSysNoticeMapper) {
        this.tSysNoticeRepository = tSysNoticeRepository;
        this.tSysNoticeMapper = tSysNoticeMapper;
    }

    /**
     * Save a tSysNotice.
     *
     * @param tSysNoticeDTO the entity to save.
     * @return the persisted entity.
     */
    public TSysNoticeDTO save(TSysNoticeDTO tSysNoticeDTO) {
        log.debug("Request to save TSysNotice : {}", tSysNoticeDTO);
        TSysNotice tSysNotice = tSysNoticeMapper.toEntity(tSysNoticeDTO);
        tSysNotice = tSysNoticeRepository.save(tSysNotice);
        return tSysNoticeMapper.toDto(tSysNotice);
    }

    /**
     * Get all the tSysNotices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TSysNoticeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TSysNotices");
        return tSysNoticeRepository.findAll(pageable)
            .map(tSysNoticeMapper::toDto);
    }


    /**
     * Get one tSysNotice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TSysNoticeDTO> findOne(Long id) {
        log.debug("Request to get TSysNotice : {}", id);
        return tSysNoticeRepository.findById(id)
            .map(tSysNoticeMapper::toDto);
    }

    /**
     * Delete the tSysNotice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TSysNotice : {}", id);
        tSysNoticeRepository.deleteById(id);
    }
}
