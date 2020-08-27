package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.service.TSysWorkProcessService;
import cn.dgd.zdw.web.rest.errors.BadRequestAlertException;
import cn.dgd.zdw.service.dto.TSysWorkProcessDTO;

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
 * REST controller for managing {@link cn.dgd.zdw.domain.TSysWorkProcess}.
 */
@RestController
@RequestMapping("/api")
public class TSysWorkProcessResource {

    private final Logger log = LoggerFactory.getLogger(TSysWorkProcessResource.class);

    private static final String ENTITY_NAME = "zhuodewenTSysWorkProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TSysWorkProcessService tSysWorkProcessService;

    public TSysWorkProcessResource(TSysWorkProcessService tSysWorkProcessService) {
        this.tSysWorkProcessService = tSysWorkProcessService;
    }

    /**
     * {@code POST  /t-sys-work-processes} : Create a new tSysWorkProcess.
     *
     * @param tSysWorkProcessDTO the tSysWorkProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tSysWorkProcessDTO, or with status {@code 400 (Bad Request)} if the tSysWorkProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/t-sys-work-processes")
    public ResponseEntity<TSysWorkProcessDTO> createTSysWorkProcess(@RequestBody TSysWorkProcessDTO tSysWorkProcessDTO) throws URISyntaxException {
        log.debug("REST request to save TSysWorkProcess : {}", tSysWorkProcessDTO);
        if (tSysWorkProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new tSysWorkProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TSysWorkProcessDTO result = tSysWorkProcessService.save(tSysWorkProcessDTO);
        return ResponseEntity.created(new URI("/api/t-sys-work-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /t-sys-work-processes} : Updates an existing tSysWorkProcess.
     *
     * @param tSysWorkProcessDTO the tSysWorkProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tSysWorkProcessDTO,
     * or with status {@code 400 (Bad Request)} if the tSysWorkProcessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tSysWorkProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/t-sys-work-processes")
    public ResponseEntity<TSysWorkProcessDTO> updateTSysWorkProcess(@RequestBody TSysWorkProcessDTO tSysWorkProcessDTO) throws URISyntaxException {
        log.debug("REST request to update TSysWorkProcess : {}", tSysWorkProcessDTO);
        if (tSysWorkProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TSysWorkProcessDTO result = tSysWorkProcessService.save(tSysWorkProcessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tSysWorkProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /t-sys-work-processes} : get all the tSysWorkProcesses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tSysWorkProcesses in body.
     */
    @GetMapping("/t-sys-work-processes")
    public ResponseEntity<List<TSysWorkProcessDTO>> getAllTSysWorkProcesses(Pageable pageable) {
        log.debug("REST request to get a page of TSysWorkProcesses");
        Page<TSysWorkProcessDTO> page = tSysWorkProcessService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /t-sys-work-processes/:id} : get the "id" tSysWorkProcess.
     *
     * @param id the id of the tSysWorkProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tSysWorkProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/t-sys-work-processes/{id}")
    public ResponseEntity<TSysWorkProcessDTO> getTSysWorkProcess(@PathVariable Long id) {
        log.debug("REST request to get TSysWorkProcess : {}", id);
        Optional<TSysWorkProcessDTO> tSysWorkProcessDTO = tSysWorkProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tSysWorkProcessDTO);
    }

    /**
     * {@code DELETE  /t-sys-work-processes/:id} : delete the "id" tSysWorkProcess.
     *
     * @param id the id of the tSysWorkProcessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/t-sys-work-processes/{id}")
    public ResponseEntity<Void> deleteTSysWorkProcess(@PathVariable Long id) {
        log.debug("REST request to delete TSysWorkProcess : {}", id);
        tSysWorkProcessService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
