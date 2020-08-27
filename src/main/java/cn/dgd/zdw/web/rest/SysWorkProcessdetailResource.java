package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.service.SysWorkProcessdetailService;
import cn.dgd.zdw.web.rest.errors.BadRequestAlertException;
import cn.dgd.zdw.service.dto.SysWorkProcessdetailDTO;
import cn.dgd.zdw.service.dto.SysWorkProcessdetailCriteria;
import cn.dgd.zdw.service.SysWorkProcessdetailQueryService;

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
 * REST controller for managing {@link cn.dgd.zdw.domain.SysWorkProcessdetail}.
 */
@RestController
@RequestMapping("/api")
public class SysWorkProcessdetailResource {

    private final Logger log = LoggerFactory.getLogger(SysWorkProcessdetailResource.class);

    private static final String ENTITY_NAME = "zhuodewenSysWorkProcessdetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SysWorkProcessdetailService sysWorkProcessdetailService;

    private final SysWorkProcessdetailQueryService sysWorkProcessdetailQueryService;

    public SysWorkProcessdetailResource(SysWorkProcessdetailService sysWorkProcessdetailService, SysWorkProcessdetailQueryService sysWorkProcessdetailQueryService) {
        this.sysWorkProcessdetailService = sysWorkProcessdetailService;
        this.sysWorkProcessdetailQueryService = sysWorkProcessdetailQueryService;
    }

    /**
     * {@code POST  /sys-work-processdetails} : Create a new sysWorkProcessdetail.
     *
     * @param sysWorkProcessdetailDTO the sysWorkProcessdetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sysWorkProcessdetailDTO, or with status {@code 400 (Bad Request)} if the sysWorkProcessdetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sys-work-processdetails")
    public ResponseEntity<SysWorkProcessdetailDTO> createSysWorkProcessdetail(@RequestBody SysWorkProcessdetailDTO sysWorkProcessdetailDTO) throws URISyntaxException {
        log.debug("REST request to save SysWorkProcessdetail : {}", sysWorkProcessdetailDTO);
        if (sysWorkProcessdetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new sysWorkProcessdetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SysWorkProcessdetailDTO result = sysWorkProcessdetailService.save(sysWorkProcessdetailDTO);
        return ResponseEntity.created(new URI("/api/sys-work-processdetails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sys-work-processdetails} : Updates an existing sysWorkProcessdetail.
     *
     * @param sysWorkProcessdetailDTO the sysWorkProcessdetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sysWorkProcessdetailDTO,
     * or with status {@code 400 (Bad Request)} if the sysWorkProcessdetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sysWorkProcessdetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sys-work-processdetails")
    public ResponseEntity<SysWorkProcessdetailDTO> updateSysWorkProcessdetail(@RequestBody SysWorkProcessdetailDTO sysWorkProcessdetailDTO) throws URISyntaxException {
        log.debug("REST request to update SysWorkProcessdetail : {}", sysWorkProcessdetailDTO);
        if (sysWorkProcessdetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SysWorkProcessdetailDTO result = sysWorkProcessdetailService.save(sysWorkProcessdetailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sysWorkProcessdetailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sys-work-processdetails} : get all the sysWorkProcessdetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sysWorkProcessdetails in body.
     */
    @GetMapping("/sys-work-processdetails")
    public ResponseEntity<List<SysWorkProcessdetailDTO>> getAllSysWorkProcessdetails(SysWorkProcessdetailCriteria criteria, Pageable pageable) {
        log.debug("REST request to get SysWorkProcessdetails by criteria: {}", criteria);
        Page<SysWorkProcessdetailDTO> page = sysWorkProcessdetailQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sys-work-processdetails/count} : count all the sysWorkProcessdetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/sys-work-processdetails/count")
    public ResponseEntity<Long> countSysWorkProcessdetails(SysWorkProcessdetailCriteria criteria) {
        log.debug("REST request to count SysWorkProcessdetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(sysWorkProcessdetailQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /sys-work-processdetails/:id} : get the "id" sysWorkProcessdetail.
     *
     * @param id the id of the sysWorkProcessdetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sysWorkProcessdetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sys-work-processdetails/{id}")
    public ResponseEntity<SysWorkProcessdetailDTO> getSysWorkProcessdetail(@PathVariable Long id) {
        log.debug("REST request to get SysWorkProcessdetail : {}", id);
        Optional<SysWorkProcessdetailDTO> sysWorkProcessdetailDTO = sysWorkProcessdetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sysWorkProcessdetailDTO);
    }

    /**
     * {@code DELETE  /sys-work-processdetails/:id} : delete the "id" sysWorkProcessdetail.
     *
     * @param id the id of the sysWorkProcessdetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sys-work-processdetails/{id}")
    public ResponseEntity<Void> deleteSysWorkProcessdetail(@PathVariable Long id) {
        log.debug("REST request to delete SysWorkProcessdetail : {}", id);
        sysWorkProcessdetailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
