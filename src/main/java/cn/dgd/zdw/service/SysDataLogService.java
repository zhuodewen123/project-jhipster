package cn.dgd.zdw.service;

import cn.dgd.zdw.domain.SysDataLog;
import cn.dgd.zdw.repository.SysDataLogRepository;
import cn.dgd.zdw.service.dto.SysDataLogDTO;
import cn.dgd.zdw.service.mapper.SysDataLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SysDataLog}.
 */
@Service
@Transactional
public class SysDataLogService {

    private final Logger log = LoggerFactory.getLogger(SysDataLogService.class);

    private final SysDataLogRepository sysDataLogRepository;

    private final SysDataLogMapper sysDataLogMapper;

    public SysDataLogService(SysDataLogRepository sysDataLogRepository, SysDataLogMapper sysDataLogMapper) {
        this.sysDataLogRepository = sysDataLogRepository;
        this.sysDataLogMapper = sysDataLogMapper;
    }

    /**
     * Save a sysDataLog.
     *
     * @param sysDataLogDTO the entity to save.
     * @return the persisted entity.
     */
    public SysDataLogDTO save(SysDataLogDTO sysDataLogDTO) {
        log.debug("Request to save SysDataLog : {}", sysDataLogDTO);
        SysDataLog sysDataLog = sysDataLogMapper.toEntity(sysDataLogDTO);
        sysDataLog = sysDataLogRepository.save(sysDataLog);
        return sysDataLogMapper.toDto(sysDataLog);
    }

    /**
     * Get all the sysDataLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SysDataLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysDataLogs");
        return sysDataLogRepository.findAll(pageable)
            .map(sysDataLogMapper::toDto);
    }


    /**
     * Get one sysDataLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SysDataLogDTO> findOne(Long id) {
        log.debug("Request to get SysDataLog : {}", id);
        return sysDataLogRepository.findById(id)
            .map(sysDataLogMapper::toDto);
    }

    /**
     * Delete the sysDataLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SysDataLog : {}", id);
        sysDataLogRepository.deleteById(id);
    }
}
