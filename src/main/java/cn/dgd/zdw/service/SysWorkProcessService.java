package cn.dgd.zdw.service;

import cn.dgd.zdw.domain.SysWorkProcess;
import cn.dgd.zdw.repository.SysWorkProcessRepository;
import cn.dgd.zdw.service.dto.SysWorkProcessDTO;
import cn.dgd.zdw.service.mapper.SysWorkProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SysWorkProcess}.
 */
@Service
@Transactional
public class SysWorkProcessService {

    private final Logger log = LoggerFactory.getLogger(SysWorkProcessService.class);

    private final SysWorkProcessRepository sysWorkProcessRepository;

    private final SysWorkProcessMapper sysWorkProcessMapper;

    public SysWorkProcessService(SysWorkProcessRepository sysWorkProcessRepository, SysWorkProcessMapper sysWorkProcessMapper) {
        this.sysWorkProcessRepository = sysWorkProcessRepository;
        this.sysWorkProcessMapper = sysWorkProcessMapper;
    }

    /**
     * Save a sysWorkProcess.
     *
     * @param sysWorkProcessDTO the entity to save.
     * @return the persisted entity.
     */
    public SysWorkProcessDTO save(SysWorkProcessDTO sysWorkProcessDTO) {
        log.debug("Request to save SysWorkProcess : {}", sysWorkProcessDTO);
        SysWorkProcess sysWorkProcess = sysWorkProcessMapper.toEntity(sysWorkProcessDTO);
        sysWorkProcess = sysWorkProcessRepository.save(sysWorkProcess);
        return sysWorkProcessMapper.toDto(sysWorkProcess);
    }

    /**
     * Get all the sysWorkProcesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SysWorkProcessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysWorkProcesses");
        return sysWorkProcessRepository.findAll(pageable)
            .map(sysWorkProcessMapper::toDto);
    }


    /**
     * Get one sysWorkProcess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SysWorkProcessDTO> findOne(Long id) {
        log.debug("Request to get SysWorkProcess : {}", id);
        return sysWorkProcessRepository.findById(id)
            .map(sysWorkProcessMapper::toDto);
    }

    /**
     * Delete the sysWorkProcess by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SysWorkProcess : {}", id);
        sysWorkProcessRepository.deleteById(id);
    }
}
