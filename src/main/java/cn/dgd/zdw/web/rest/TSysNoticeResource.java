package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.service.TSysNoticeService;
import cn.dgd.zdw.web.rest.errors.BadRequestAlertException;
import cn.dgd.zdw.service.dto.TSysNoticeDTO;

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
 * REST controller for managing {@link cn.dgd.zdw.domain.TSysNotice}.
 */
@RestController
@RequestMapping("/api")
public class TSysNoticeResource {

    private final Logger log = LoggerFactory.getLogger(TSysNoticeResource.class);

    private static final String ENTITY_NAME = "zhuodewenTSysNotice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TSysNoticeService tSysNoticeService;

    public TSysNoticeResource(TSysNoticeService tSysNoticeService) {
        this.tSysNoticeService = tSysNoticeService;
    }

    /**
     * {@code POST  /t-sys-notices} : Create a new tSysNotice.
     *
     * @param tSysNoticeDTO the tSysNoticeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tSysNoticeDTO, or with status {@code 400 (Bad Request)} if the tSysNotice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/t-sys-notices")
    public ResponseEntity<TSysNoticeDTO> createTSysNotice(@RequestBody TSysNoticeDTO tSysNoticeDTO) throws URISyntaxException {
        log.debug("REST request to save TSysNotice : {}", tSysNoticeDTO);
        if (tSysNoticeDTO.getId() != null) {
            throw new BadRequestAlertException("A new tSysNotice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TSysNoticeDTO result = tSysNoticeService.save(tSysNoticeDTO);
        return ResponseEntity.created(new URI("/api/t-sys-notices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /t-sys-notices} : Updates an existing tSysNotice.
     *
     * @param tSysNoticeDTO the tSysNoticeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tSysNoticeDTO,
     * or with status {@code 400 (Bad Request)} if the tSysNoticeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tSysNoticeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/t-sys-notices")
    public ResponseEntity<TSysNoticeDTO> updateTSysNotice(@RequestBody TSysNoticeDTO tSysNoticeDTO) throws URISyntaxException {
        log.debug("REST request to update TSysNotice : {}", tSysNoticeDTO);
        if (tSysNoticeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TSysNoticeDTO result = tSysNoticeService.save(tSysNoticeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tSysNoticeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /t-sys-notices} : get all the tSysNotices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tSysNotices in body.
     */
    @GetMapping("/t-sys-notices")
    public ResponseEntity<List<TSysNoticeDTO>> getAllTSysNotices(Pageable pageable) {
        log.debug("REST request to get a page of TSysNotices");
        Page<TSysNoticeDTO> page = tSysNoticeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /t-sys-notices/:id} : get the "id" tSysNotice.
     *
     * @param id the id of the tSysNoticeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tSysNoticeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/t-sys-notices/{id}")
    public ResponseEntity<TSysNoticeDTO> getTSysNotice(@PathVariable Long id) {
        log.debug("REST request to get TSysNotice : {}", id);
        Optional<TSysNoticeDTO> tSysNoticeDTO = tSysNoticeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tSysNoticeDTO);
    }

    /**
     * {@code DELETE  /t-sys-notices/:id} : delete the "id" tSysNotice.
     *
     * @param id the id of the tSysNoticeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/t-sys-notices/{id}")
    public ResponseEntity<Void> deleteTSysNotice(@PathVariable Long id) {
        log.debug("REST request to delete TSysNotice : {}", id);
        tSysNoticeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
