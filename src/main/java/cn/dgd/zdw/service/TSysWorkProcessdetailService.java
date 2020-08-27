package cn.dgd.zdw.service;

import cn.dgd.zdw.domain.TSysWorkProcessdetail;
import cn.dgd.zdw.repository.TSysWorkProcessdetailRepository;
import cn.dgd.zdw.service.dto.TSysWorkProcessdetailDTO;
import cn.dgd.zdw.service.mapper.TSysWorkProcessdetailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TSysWorkProcessdetail}.
 */
@Service
@Transactional
public class TSysWorkProcessdetailService {

    private final Logger log = LoggerFactory.getLogger(TSysWorkProcessdetailService.class);

    private final TSysWorkProcessdetailRepository tSysWorkProcessdetailRepository;

    private final TSysWorkProcessdetailMapper tSysWorkProcessdetailMapper;

    public TSysWorkProcessdetailService(TSysWorkProcessdetailRepository tSysWorkProcessdetailRepository, TSysWorkProcessdetailMapper tSysWorkProcessdetailMapper) {
        this.tSysWorkProcessdetailRepository = tSysWorkProcessdetailRepository;
        this.tSysWorkProcessdetailMapper = tSysWorkProcessdetailMapper;
    }

    /**
     * Save a tSysWorkProcessdetail.
     *
     * @param tSysWorkProcessdetailDTO the entity to save.
     * @return the persisted entity.
     */
    public TSysWorkProcessdetailDTO save(TSysWorkProcessdetailDTO tSysWorkProcessdetailDTO) {
        log.debug("Request to save TSysWorkProcessdetail : {}", tSysWorkProcessdetailDTO);
        TSysWorkProcessdetail tSysWorkProcessdetail = tSysWorkProcessdetailMapper.toEntity(tSysWorkProcessdetailDTO);
        tSysWorkProcessdetail = tSysWorkProcessdetailRepository.save(tSysWorkProcessdetail);
        return tSysWorkProcessdetailMapper.toDto(tSysWorkProcessdetail);
    }

    /**
     * Get all the tSysWorkProcessdetails.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TSysWorkProcessdetailDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TSysWorkProcessdetails");
        return tSysWorkProcessdetailRepository.findAll(pageable)
            .map(tSysWorkProcessdetailMapper::toDto);
    }


    /**
     * Get one tSysWorkProcessdetail by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TSysWorkProcessdetailDTO> findOne(Long id) {
        log.debug("Request to get TSysWorkProcessdetail : {}", id);
        return tSysWorkProcessdetailRepository.findById(id)
            .map(tSysWorkProcessdetailMapper::toDto);
    }

    /**
     * Delete the tSysWorkProcessdetail by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TSysWorkProcessdetail : {}", id);
        tSysWorkProcessdetailRepository.deleteById(id);
    }
}
