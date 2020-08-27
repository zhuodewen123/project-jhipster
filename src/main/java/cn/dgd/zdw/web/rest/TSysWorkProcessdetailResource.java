package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.service.TSysWorkProcessdetailService;
import cn.dgd.zdw.web.rest.errors.BadRequestAlertException;
import cn.dgd.zdw.service.dto.TSysWorkProcessdetailDTO;

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
 * REST controller for managing {@link cn.dgd.zdw.domain.TSysWorkProcessdetail}.
 */
@RestController
@RequestMapping("/api")
public class TSysWorkProcessdetailResource {

    private final Logger log = LoggerFactory.getLogger(TSysWorkProcessdetailResource.class);

    private static final String ENTITY_NAME = "zhuodewenTSysWorkProcessdetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TSysWorkProcessdetailService tSysWorkProcessdetailService;

    public TSysWorkProcessdetailResource(TSysWorkProcessdetailService tSysWorkProcessdetailService) {
        this.tSysWorkProcessdetailService = tSysWorkProcessdetailService;
    }

    /**
     * {@code POST  /t-sys-work-processdetails} : Create a new tSysWorkProcessdetail.
     *
     * @param tSysWorkProcessdetailDTO the tSysWorkProcessdetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tSysWorkProcessdetailDTO, or with status {@code 400 (Bad Request)} if the tSysWorkProcessdetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/t-sys-work-processdetails")
    public ResponseEntity<TSysWorkProcessdetailDTO> createTSysWorkProcessdetail(@RequestBody TSysWorkProcessdetailDTO tSysWorkProcessdetailDTO) throws URISyntaxException {
        log.debug("REST request to save TSysWorkProcessdetail : {}", tSysWorkProcessdetailDTO);
        if (tSysWorkProcessdetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new tSysWorkProcessdetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TSysWorkProcessdetailDTO result = tSysWorkProcessdetailService.save(tSysWorkProcessdetailDTO);
        return ResponseEntity.created(new URI("/api/t-sys-work-processdetails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /t-sys-work-processdetails} : Updates an existing tSysWorkProcessdetail.
     *
     * @param tSysWorkProcessdetailDTO the tSysWorkProcessdetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tSysWorkProcessdetailDTO,
     * or with status {@code 400 (Bad Request)} if the tSysWorkProcessdetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tSysWorkProcessdetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/t-sys-work-processdetails")
    public ResponseEntity<TSysWorkProcessdetailDTO> updateTSysWorkProcessdetail(@RequestBody TSysWorkProcessdetailDTO tSysWorkProcessdetailDTO) throws URISyntaxException {
        log.debug("REST request to update TSysWorkProcessdetail : {}", tSysWorkProcessdetailDTO);
        if (tSysWorkProcessdetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TSysWorkProcessdetailDTO result = tSysWorkProcessdetailService.save(tSysWorkProcessdetailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tSysWorkProcessdetailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /t-sys-work-processdetails} : get all the tSysWorkProcessdetails.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tSysWorkProcessdetails in body.
     */
    @GetMapping("/t-sys-work-processdetails")
    public ResponseEntity<List<TSysWorkProcessdetailDTO>> getAllTSysWorkProcessdetails(Pageable pageable) {
        log.debug("REST request to get a page of TSysWorkProcessdetails");
        Page<TSysWorkProcessdetailDTO> page = tSysWorkProcessdetailService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /t-sys-work-processdetails/:id} : get the "id" tSysWorkProcessdetail.
     *
     * @param id the id of the tSysWorkProcessdetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tSysWorkProcessdetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/t-sys-work-processdetails/{id}")
    public ResponseEntity<TSysWorkProcessdetailDTO> getTSysWorkProcessdetail(@PathVariable Long id) {
        log.debug("REST request to get TSysWorkProcessdetail : {}", id);
        Optional<TSysWorkProcessdetailDTO> tSysWorkProcessdetailDTO = tSysWorkProcessdetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tSysWorkProcessdetailDTO);
    }

    /**
     * {@code DELETE  /t-sys-work-processdetails/:id} : delete the "id" tSysWorkProcessdetail.
     *
     * @param id the id of the tSysWorkProcessdetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/t-sys-work-processdetails/{id}")
    public ResponseEntity<Void> deleteTSysWorkProcessdetail(@PathVariable Long id) {
        log.debug("REST request to delete TSysWorkProcessdetail : {}", id);
        tSysWorkProcessdetailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
