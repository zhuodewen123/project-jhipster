package cn.dgd.zdw.service;

import cn.dgd.zdw.domain.WorkProcess;
import cn.dgd.zdw.repository.WorkProcessRepository;
import cn.dgd.zdw.service.dto.WorkProcessDTO;
import cn.dgd.zdw.service.mapper.WorkProcessMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkProcess}.
 */
@Service
@Transactional
public class WorkProcessService {

    private final Logger log = LoggerFactory.getLogger(WorkProcessService.class);

    private final WorkProcessRepository workProcessRepository;

    private final WorkProcessMapper workProcessMapper;

    public WorkProcessService(WorkProcessRepository workProcessRepository, WorkProcessMapper workProcessMapper) {
        this.workProcessRepository = workProcessRepository;
        this.workProcessMapper = workProcessMapper;
    }

    /**
     * Save a workProcess.
     *
     * @param workProcessDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkProcessDTO save(WorkProcessDTO workProcessDTO) {
        log.debug("Request to save WorkProcess : {}", workProcessDTO);
        WorkProcess workProcess = workProcessMapper.toEntity(workProcessDTO);
        workProcess = workProcessRepository.save(workProcess);
        return workProcessMapper.toDto(workProcess);
    }

    /**
     * Get all the workProcesses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkProcessDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkProcesses");
        return workProcessRepository.findAll(pageable)
            .map(workProcessMapper::toDto);
    }


    /**
     * Get one workProcess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkProcessDTO> findOne(Long id) {
        log.debug("Request to get WorkProcess : {}", id);
        return workProcessRepository.findById(id)
            .map(workProcessMapper::toDto);
    }

    /**
     * Delete the workProcess by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkProcess : {}", id);
        workProcessRepository.deleteById(id);
    }
}
