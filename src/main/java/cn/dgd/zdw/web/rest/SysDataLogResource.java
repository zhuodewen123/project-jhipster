package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.service.SysDataLogService;
import cn.dgd.zdw.web.rest.errors.BadRequestAlertException;
import cn.dgd.zdw.service.dto.SysDataLogDTO;

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
 * REST controller for managing {@link cn.dgd.zdw.domain.SysDataLog}.
 */
@RestController
@RequestMapping("/api")
public class SysDataLogResource {

    private final Logger log = LoggerFactory.getLogger(SysDataLogResource.class);

    private static final String ENTITY_NAME = "zhuodewenSysDataLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysDataLogService sysDataLogService;

    public SysDataLogResource(SysDataLogService sysDataLogService) {
        this.sysDataLogService = sysDataLogService;
    }

    /**
     * {@code POST  /sys-data-logs} : Create a new sysDataLog.
     *
     * @param sysDataLogDTO the sysDataLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysDataLogDTO, or with status {@code 400 (Bad Request)} if the sysDataLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-data-logs")
    public ResponseEntity<SysDataLogDTO> createSysDataLog(@RequestBody SysDataLogDTO sysDataLogDTO) throws URISyntaxException {
        log.debug("REST request to save SysDataLog : {}", sysDataLogDTO);
        if (sysDataLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysDataLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysDataLogDTO result = sysDataLogService.save(sysDataLogDTO);
        return ResponseEntity.created(new URI("/api/sys-data-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-data-logs} : Updates an existing sysDataLog.
     *
     * @param sysDataLogDTO the sysDataLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysDataLogDTO,
     * or with status {@code 400 (Bad Request)} if the sysDataLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysDataLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-data-logs")
    public ResponseEntity<SysDataLogDTO> updateSysDataLog(@RequestBody SysDataLogDTO sysDataLogDTO) throws URISyntaxException {
        log.debug("REST request to update SysDataLog : {}", sysDataLogDTO);
        if (sysDataLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysDataLogDTO result = sysDataLogService.save(sysDataLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysDataLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-data-logs} : get all the sysDataLogs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysDataLogs in body.
     */
    @GetMapping("/sys-data-logs")
    public ResponseEntity<List<SysDataLogDTO>> getAllSysDataLogs(Pageable pageable) {
        log.debug("REST request to get a page of SysDataLogs");
        Page<SysDataLogDTO> page = sysDataLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sys-data-logs/:id} : get the "id" sysDataLog.
     *
     * @param id the id of the sysDataLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysDataLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-data-logs/{id}")
    public ResponseEntity<SysDataLogDTO> getSysDataLog(@PathVariable Long id) {
        log.debug("REST request to get SysDataLog : {}", id);
        Optional<SysDataLogDTO> sysDataLogDTO = sysDataLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysDataLogDTO);
    }

    /**
     * {@code DELETE  /sys-data-logs/:id} : delete the "id" sysDataLog.
     *
     * @param id the id of the sysDataLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-data-logs/{id}")
    public ResponseEntity<Void> deleteSysDataLog(@PathVariable Long id) {
        log.debug("REST request to delete SysDataLog : {}", id);
        sysDataLogService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
