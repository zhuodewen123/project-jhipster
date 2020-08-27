package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.service.WorkProcessdetailService;
import cn.dgd.zdw.web.rest.errors.BadRequestAlertException;
import cn.dgd.zdw.service.dto.WorkProcessdetailDTO;
import cn.dgd.zdw.service.dto.WorkProcessdetailCriteria;
import cn.dgd.zdw.service.WorkProcessdetailQueryService;

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
 * REST controller for managing {@link cn.dgd.zdw.domain.WorkProcessdetail}.
 */
@RestController
@RequestMapping("/api")
public class WorkProcessdetailResource {

    private final Logger log = LoggerFactory.getLogger(WorkProcessdetailResource.class);

    private static final String ENTITY_NAME = "zhuodewenWorkProcessdetail";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkProcessdetailService workProcessdetailService;

    private final WorkProcessdetailQueryService workProcessdetailQueryService;

    public WorkProcessdetailResource(WorkProcessdetailService workProcessdetailService, WorkProcessdetailQueryService workProcessdetailQueryService) {
        this.workProcessdetailService = workProcessdetailService;
        this.workProcessdetailQueryService = workProcessdetailQueryService;
    }

    /**
     * {@code POST  /work-processdetails} : Create a new workProcessdetail.
     *
     * @param workProcessdetailDTO the workProcessdetailDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workProcessdetailDTO, or with status {@code 400 (Bad Request)} if the workProcessdetail has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-processdetails")
    public ResponseEntity<WorkProcessdetailDTO> createWorkProcessdetail(@RequestBody WorkProcessdetailDTO workProcessdetailDTO) throws URISyntaxException {
        log.debug("REST request to save WorkProcessdetail : {}", workProcessdetailDTO);
        if (workProcessdetailDTO.getId() != null) {
            throw new BadRequestAlertException("A new workProcessdetail cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkProcessdetailDTO result = workProcessdetailService.save(workProcessdetailDTO);
        return ResponseEntity.created(new URI("/api/work-processdetails/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-processdetails} : Updates an existing workProcessdetail.
     *
     * @param workProcessdetailDTO the workProcessdetailDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workProcessdetailDTO,
     * or with status {@code 400 (Bad Request)} if the workProcessdetailDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workProcessdetailDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-processdetails")
    public ResponseEntity<WorkProcessdetailDTO> updateWorkProcessdetail(@RequestBody WorkProcessdetailDTO workProcessdetailDTO) throws URISyntaxException {
        log.debug("REST request to update WorkProcessdetail : {}", workProcessdetailDTO);
        if (workProcessdetailDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkProcessdetailDTO result = workProcessdetailService.save(workProcessdetailDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workProcessdetailDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-processdetails} : get all the workProcessdetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workProcessdetails in body.
     */
    @GetMapping("/work-processdetails")
    public ResponseEntity<List<WorkProcessdetailDTO>> getAllWorkProcessdetails(WorkProcessdetailCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WorkProcessdetails by criteria: {}", criteria);
        Page<WorkProcessdetailDTO> page = workProcessdetailQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-processdetails/count} : count all the workProcessdetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/work-processdetails/count")
    public ResponseEntity<Long> countWorkProcessdetails(WorkProcessdetailCriteria criteria) {
        log.debug("REST request to count WorkProcessdetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(workProcessdetailQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /work-processdetails/:id} : get the "id" workProcessdetail.
     *
     * @param id the id of the workProcessdetailDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workProcessdetailDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-processdetails/{id}")
    public ResponseEntity<WorkProcessdetailDTO> getWorkProcessdetail(@PathVariable Long id) {
        log.debug("REST request to get WorkProcessdetail : {}", id);
        Optional<WorkProcessdetailDTO> workProcessdetailDTO = workProcessdetailService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workProcessdetailDTO);
    }

    /**
     * {@code DELETE  /work-processdetails/:id} : delete the "id" workProcessdetail.
     *
     * @param id the id of the workProcessdetailDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-processdetails/{id}")
    public ResponseEntity<Void> deleteWorkProcessdetail(@PathVariable Long id) {
        log.debug("REST request to delete WorkProcessdetail : {}", id);
        workProcessdetailService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
