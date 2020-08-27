package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.service.WorkProcessService;
import cn.dgd.zdw.web.rest.errors.BadRequestAlertException;
import cn.dgd.zdw.service.dto.WorkProcessDTO;
import cn.dgd.zdw.service.dto.WorkProcessCriteria;
import cn.dgd.zdw.service.WorkProcessQueryService;

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
 * REST controller for managing {@link cn.dgd.zdw.domain.WorkProcess}.
 */
@RestController
@RequestMapping("/api")
public class WorkProcessResource {

    private final Logger log = LoggerFactory.getLogger(WorkProcessResource.class);

    private static final String ENTITY_NAME = "zhuodewenWorkProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkProcessService workProcessService;

    private final WorkProcessQueryService workProcessQueryService;

    public WorkProcessResource(WorkProcessService workProcessService, WorkProcessQueryService workProcessQueryService) {
        this.workProcessService = workProcessService;
        this.workProcessQueryService = workProcessQueryService;
    }

    /**
     * {@code POST  /work-processes} : Create a new workProcess.
     *
     * @param workProcessDTO the workProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workProcessDTO, or with status {@code 400 (Bad Request)} if the workProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/work-processes")
    public ResponseEntity<WorkProcessDTO> createWorkProcess(@RequestBody WorkProcessDTO workProcessDTO) throws URISyntaxException {
        log.debug("REST request to save WorkProcess : {}", workProcessDTO);
        if (workProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new workProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkProcessDTO result = workProcessService.save(workProcessDTO);
        return ResponseEntity.created(new URI("/api/work-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /work-processes} : Updates an existing workProcess.
     *
     * @param workProcessDTO the workProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workProcessDTO,
     * or with status {@code 400 (Bad Request)} if the workProcessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/work-processes")
    public ResponseEntity<WorkProcessDTO> updateWorkProcess(@RequestBody WorkProcessDTO workProcessDTO) throws URISyntaxException {
        log.debug("REST request to update WorkProcess : {}", workProcessDTO);
        if (workProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WorkProcessDTO result = workProcessService.save(workProcessDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /work-processes} : get all the workProcesses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workProcesses in body.
     */
    @GetMapping("/work-processes")
    public ResponseEntity<List<WorkProcessDTO>> getAllWorkProcesses(WorkProcessCriteria criteria, Pageable pageable) {
        log.debug("REST request to get WorkProcesses by criteria: {}", criteria);
        Page<WorkProcessDTO> page = workProcessQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /work-processes/count} : count all the workProcesses.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/work-processes/count")
    public ResponseEntity<Long> countWorkProcesses(WorkProcessCriteria criteria) {
        log.debug("REST request to count WorkProcesses by criteria: {}", criteria);
        return ResponseEntity.ok().body(workProcessQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /work-processes/:id} : get the "id" workProcess.
     *
     * @param id the id of the workProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/work-processes/{id}")
    public ResponseEntity<WorkProcessDTO> getWorkProcess(@PathVariable Long id) {
        log.debug("REST request to get WorkProcess : {}", id);
        Optional<WorkProcessDTO> workProcessDTO = workProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workProcessDTO);
    }

    /**
     * {@code DELETE  /work-processes/:id} : delete the "id" workProcess.
     *
     * @param id the id of the workProcessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/work-processes/{id}")
    public ResponseEntity<Void> deleteWorkProcess(@PathVariable Long id) {
        log.debug("REST request to delete WorkProcess : {}", id);
        workProcessService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
