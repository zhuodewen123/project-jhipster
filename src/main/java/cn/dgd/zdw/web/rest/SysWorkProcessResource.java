package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.service.SysWorkProcessService;
import cn.dgd.zdw.web.rest.errors.BadRequestAlertException;
import cn.dgd.zdw.service.dto.SysWorkProcessDTO;
import cn.dgd.zdw.service.dto.SysWorkProcessCriteria;
import cn.dgd.zdw.service.SysWorkProcessQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link cn.dgd.zdw.domain.SysWorkProcess}.
 */
@RestController
@RequestMapping("/api")
public class SysWorkProcessResource {

    private final Logger log = LoggerFactory.getLogger(SysWorkProcessResource.class);

    private static final String ENTITY_NAME = "zhuodewenSysWorkProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysWorkProcessService sysWorkProcessService;

    private final SysWorkProcessQueryService sysWorkProcessQueryService;

    public SysWorkProcessResource(SysWorkProcessService sysWorkProcessService, SysWorkProcessQueryService sysWorkProcessQueryService) {
        this.sysWorkProcessService = sysWorkProcessService;
        this.sysWorkProcessQueryService = sysWorkProcessQueryService;
    }

    /**
     * {@code POST  /sys-work-processes} : Create a new sysWorkProcess.
     *
     * @param sysWorkProcessDTO the sysWorkProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysWorkProcessDTO, or with status {@code 400 (Bad Request)} if the sysWorkProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-work-processes")
    public ResponseEntity<SysWorkProcessDTO> createSysWorkProcess(@RequestBody SysWorkProcessDTO sysWorkProcessDTO) throws URISyntaxException {
        log.debug("REST request to save SysWorkProcess : {}", sysWorkProcessDTO);
        if (sysWorkProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysWorkProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysWorkProcessDTO result = sysWorkProcessService.save(sysWorkProcessDTO);
        return ResponseEntity.created(new URI("/api/sys-work-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-work-processes} : Updates an existing sysWorkProcess.
     *
     * @param sysWorkProcessDTO the sysWorkProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysWorkProcessDTO,
     * or with status {@code 400 (Bad Request)} if the sysWorkProcessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysWorkProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-work-processes")
    public ResponseEntity<SysWorkProcessDTO> updateSysWorkProcess(@RequestBody SysWorkProcessDTO sysWorkProcessDTO) throws URISyntaxException {
        log.debug("REST request to update SysWorkProcess : {}", sysWorkProcessDTO);
        if (sysWorkProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysWorkProcessDTO result = sysWorkProcessService.save(sysWorkProcessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysWorkProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-work-processes} : get all the sysWorkProcesses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysWorkProcesses in body.
     */
    @GetMapping("/sys-work-processes")
    public ResponseEntity<List<SysWorkProcessDTO>> getAllSysWorkProcesses(SysWorkProcessCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SysWorkProcesses by criteria: {}", criteria);
        Page<SysWorkProcessDTO> page = sysWorkProcessQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sys-work-processes/count} : count all the sysWorkProcesses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sys-work-processes/count")
    public ResponseEntity<Long> countSysWorkProcesses(SysWorkProcessCriteria criteria) {
        log.debug("REST request to count SysWorkProcesses by criteria: {}", criteria);
        return ResponseEntity.ok().body(sysWorkProcessQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sys-work-processes/:id} : get the "id" sysWorkProcess.
     *
     * @param id the id of the sysWorkProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysWorkProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-work-processes/{id}")
    public ResponseEntity<SysWorkProcessDTO> getSysWorkProcess(@PathVariable Long id) {
        log.debug("REST request to get SysWorkProcess : {}", id);
        Optional<SysWorkProcessDTO> sysWorkProcessDTO = sysWorkProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysWorkProcessDTO);
    }

    /**
     * {@code DELETE  /sys-work-processes/:id} : delete the "id" sysWorkProcess.
     *
     * @param id the id of the sysWorkProcessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-work-processes/{id}")
    public ResponseEntity<Void> deleteSysWorkProcess(@PathVariable Long id) {
        log.debug("REST request to delete SysWorkProcess : {}", id);
        sysWorkProcessService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
