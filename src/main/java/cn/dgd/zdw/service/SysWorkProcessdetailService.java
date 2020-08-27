package cn.dgd.zdw.service;

import cn.dgd.zdw.domain.SysWorkProcessdetail;
import cn.dgd.zdw.repository.SysWorkProcessdetailRepository;
import cn.dgd.zdw.service.dto.SysWorkProcessdetailDTO;
import cn.dgd.zdw.service.mapper.SysWorkProcessdetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SysWorkProcessdetail}.
 */
@Service
@Transactional
public class SysWorkProcessdetailService {

    private final Logger log = LoggerFactory.getLogger(SysWorkProcessdetailService.class);

    private final SysWorkProcessdetailRepository sysWorkProcessdetailRepository;

    private final SysWorkProcessdetailMapper sysWorkProcessdetailMapper;

    public SysWorkProcessdetailService(SysWorkProcessdetailRepository sysWorkProcessdetailRepository, SysWorkProcessdetailMapper sysWorkProcessdetailMapper) {
        this.sysWorkProcessdetailRepository = sysWorkProcessdetailRepository;
        this.sysWorkProcessdetailMapper = sysWorkProcessdetailMapper;
    }

    /**
     * Save a sysWorkProcessdetail.
     *
     * @param sysWorkProcessdetailDTO the entity to save.
     * @return the persisted entity.
     */
    public SysWorkProcessdetailDTO save(SysWorkProcessdetailDTO sysWorkProcessdetailDTO) {
        log.debug("Request to save SysWorkProcessdetail : {}", sysWorkProcessdetailDTO);
        SysWorkProcessdetail sysWorkProcessdetail = sysWorkProcessdetailMapper.toEntity(sysWorkProcessdetailDTO);
        sysWorkProcessdetail = sysWorkProcessdetailRepository.save(sysWorkProcessdetail);
        return sysWorkProcessdetailMapper.toDto(sysWorkProcessdetail);
    }

    /**
     * Get all the sysWorkProcessdetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SysWorkProcessdetailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SysWorkProcessdetails");
        return sysWorkProcessdetailRepository.findAll(pageable)
            .map(sysWorkProcessdetailMapper::toDto);
    }


    /**
     * Get one sysWorkProcessdetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SysWorkProcessdetailDTO> findOne(Long id) {
        log.debug("Request to get SysWorkProcessdetail : {}", id);
        return sysWorkProcessdetailRepository.findById(id)
            .map(sysWorkProcessdetailMapper::toDto);
    }

    /**
     * Delete the sysWorkProcessdetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SysWorkProcessdetail : {}", id);
        sysWorkProcessdetailRepository.deleteById(id);
    }
}
