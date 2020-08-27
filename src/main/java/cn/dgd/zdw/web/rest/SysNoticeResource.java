package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.service.SysNoticeService;
import cn.dgd.zdw.web.rest.errors.BadRequestAlertException;
import cn.dgd.zdw.service.dto.SysNoticeDTO;
import cn.dgd.zdw.service.dto.SysNoticeCriteria;
import cn.dgd.zdw.service.SysNoticeQueryService;

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
 * REST controller for managing {@link cn.dgd.zdw.domain.SysNotice}.
 */
@RestController
@RequestMapping("/api")
public class SysNoticeResource {

    private final Logger log = LoggerFactory.getLogger(SysNoticeResource.class);

    private static final String ENTITY_NAME = "zhuodewenSysNotice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysNoticeService sysNoticeService;

    private final SysNoticeQueryService sysNoticeQueryService;

    public SysNoticeResource(SysNoticeService sysNoticeService, SysNoticeQueryService sysNoticeQueryService) {
        this.sysNoticeService = sysNoticeService;
        this.sysNoticeQueryService = sysNoticeQueryService;
    }

    /**
     * {@code POST  /sys-notices} : Create a new sysNotice.
     *
     * @param sysNoticeDTO the sysNoticeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysNoticeDTO, or with status {@code 400 (Bad Request)} if the sysNotice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-notices")
    public ResponseEntity<SysNoticeDTO> createSysNotice(@RequestBody SysNoticeDTO sysNoticeDTO) throws URISyntaxException {
        log.debug("REST request to save SysNotice : {}", sysNoticeDTO);
        if (sysNoticeDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysNotice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysNoticeDTO result = sysNoticeService.save(sysNoticeDTO);
        return ResponseEntity.created(new URI("/api/sys-notices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-notices} : Updates an existing sysNotice.
     *
     * @param sysNoticeDTO the sysNoticeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysNoticeDTO,
     * or with status {@code 400 (Bad Request)} if the sysNoticeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysNoticeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-notices")
    public ResponseEntity<SysNoticeDTO> updateSysNotice(@RequestBody SysNoticeDTO sysNoticeDTO) throws URISyntaxException {
        log.debug("REST request to update SysNotice : {}", sysNoticeDTO);
        if (sysNoticeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysNoticeDTO result = sysNoticeService.save(sysNoticeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysNoticeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-notices} : get all the sysNotices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysNotices in body.
     */
    @GetMapping("/sys-notices")
    public ResponseEntity<List<SysNoticeDTO>> getAllSysNotices(SysNoticeCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SysNotices by criteria: {}", criteria);
        Page<SysNoticeDTO> page = sysNoticeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sys-notices/count} : count all the sysNotices.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sys-notices/count")
    public ResponseEntity<Long> countSysNotices(SysNoticeCriteria criteria) {
        log.debug("REST request to count SysNotices by criteria: {}", criteria);
        return ResponseEntity.ok().body(sysNoticeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sys-notices/:id} : get the "id" sysNotice.
     *
     * @param id the id of the sysNoticeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysNoticeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-notices/{id}")
    public ResponseEntity<SysNoticeDTO> getSysNotice(@PathVariable Long id) {
        log.debug("REST request to get SysNotice : {}", id);
        Optional<SysNoticeDTO> sysNoticeDTO = sysNoticeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysNoticeDTO);
    }

    /**
     * {@code DELETE  /sys-notices/:id} : delete the "id" sysNotice.
     *
     * @param id the id of the sysNoticeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-notices/{id}")
    public ResponseEntity<Void> deleteSysNotice(@PathVariable Long id) {
        log.debug("REST request to delete SysNotice : {}", id);
        sysNoticeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
