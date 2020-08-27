package cn.dgd.zdw.service;

import cn.dgd.zdw.domain.TSysWorkProcess;
import cn.dgd.zdw.repository.TSysWorkProcessRepository;
import cn.dgd.zdw.service.dto.TSysWorkProcessDTO;
import cn.dgd.zdw.service.mapper.TSysWorkProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TSysWorkProcess}.
 */
@Service
@Transactional
public class TSysWorkProcessService {

    private final Logger log = LoggerFactory.getLogger(TSysWorkProcessService.class);

    private final TSysWorkProcessRepository tSysWorkProcessRepository;

    private final TSysWorkProcessMapper tSysWorkProcessMapper;

    public TSysWorkProcessService(TSysWorkProcessRepository tSysWorkProcessRepository, TSysWorkProcessMapper tSysWorkProcessMapper) {
        this.tSysWorkProcessRepository = tSysWorkProcessRepository;
        this.tSysWorkProcessMapper = tSysWorkProcessMapper;
    }

    /**
     * Save a tSysWorkProcess.
     *
     * @param tSysWorkProcessDTO the entity to save.
     * @return the persisted entity.
     */
    public TSysWorkProcessDTO save(TSysWorkProcessDTO tSysWorkProcessDTO) {
        log.debug("Request to save TSysWorkProcess : {}", tSysWorkProcessDTO);
        TSysWorkProcess tSysWorkProcess = tSysWorkProcessMapper.toEntity(tSysWorkProcessDTO);
        tSysWorkProcess = tSysWorkProcessRepository.save(tSysWorkProcess);
        return tSysWorkProcessMapper.toDto(tSysWorkProcess);
    }

    /**
     * Get all the tSysWorkProcesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TSysWorkProcessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TSysWorkProcesses");
        return tSysWorkProcessRepository.findAll(pageable)
            .map(tSysWorkProcessMapper::toDto);
    }


    /**
     * Get one tSysWorkProcess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TSysWorkProcessDTO> findOne(Long id) {
        log.debug("Request to get TSysWorkProcess : {}", id);
        return tSysWorkProcessRepository.findById(id)
            .map(tSysWorkProcessMapper::toDto);
    }

    /**
     * Delete the tSysWorkProcess by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TSysWorkProcess : {}", id);
        tSysWorkProcessRepository.deleteById(id);
    }
}
