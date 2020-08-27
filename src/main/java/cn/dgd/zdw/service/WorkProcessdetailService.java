package cn.dgd.zdw.service;

import cn.dgd.zdw.domain.WorkProcessdetail;
import cn.dgd.zdw.repository.WorkProcessdetailRepository;
import cn.dgd.zdw.service.dto.WorkProcessdetailDTO;
import cn.dgd.zdw.service.mapper.WorkProcessdetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WorkProcessdetail}.
 */
@Service
@Transactional
public class WorkProcessdetailService {

    private final Logger log = LoggerFactory.getLogger(WorkProcessdetailService.class);

    private final WorkProcessdetailRepository workProcessdetailRepository;

    private final WorkProcessdetailMapper workProcessdetailMapper;

    public WorkProcessdetailService(WorkProcessdetailRepository workProcessdetailRepository, WorkProcessdetailMapper workProcessdetailMapper) {
        this.workProcessdetailRepository = workProcessdetailRepository;
        this.workProcessdetailMapper = workProcessdetailMapper;
    }

    /**
     * Save a workProcessdetail.
     *
     * @param workProcessdetailDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkProcessdetailDTO save(WorkProcessdetailDTO workProcessdetailDTO) {
        log.debug("Request to save WorkProcessdetail : {}", workProcessdetailDTO);
        WorkProcessdetail workProcessdetail = workProcessdetailMapper.toEntity(workProcessdetailDTO);
        workProcessdetail = workProcessdetailRepository.save(workProcessdetail);
        return workProcessdetailMapper.toDto(workProcessdetail);
    }

    /**
     * Get all the workProcessdetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkProcessdetailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkProcessdetails");
        return workProcessdetailRepository.findAll(pageable)
            .map(workProcessdetailMapper::toDto);
    }


    /**
     * Get one workProcessdetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkProcessdetailDTO> findOne(Long id) {
        log.debug("Request to get WorkProcessdetail : {}", id);
        return workProcessdetailRepository.findById(id)
            .map(workProcessdetailMapper::toDto);
    }

    /**
     * Delete the workProcessdetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkProcessdetail : {}", id);
        workProcessdetailRepository.deleteById(id);
    }
}
