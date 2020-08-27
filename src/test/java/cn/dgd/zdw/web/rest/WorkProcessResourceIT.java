package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.ZhuodewenApp;
import cn.dgd.zdw.domain.WorkProcess;
import cn.dgd.zdw.repository.WorkProcessRepository;
import cn.dgd.zdw.service.WorkProcessService;
import cn.dgd.zdw.service.dto.WorkProcessDTO;
import cn.dgd.zdw.service.mapper.WorkProcessMapper;
import cn.dgd.zdw.service.dto.WorkProcessCriteria;
import cn.dgd.zdw.service.WorkProcessQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WorkProcessResource} REST controller.
 */
@SpringBootTest(classes = ZhuodewenApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkProcessResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RECEIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECEIVE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_RECEIVE_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_RECEIVE_ORG = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVE_ORG = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_PROCESS = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_PROCESS = "BBBBBBBBBB";

    @Autowired
    private WorkProcessRepository workProcessRepository;

    @Autowired
    private WorkProcessMapper workProcessMapper;

    @Autowired
    private WorkProcessService workProcessService;

    @Autowired
    private WorkProcessQueryService workProcessQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkProcessMockMvc;

    private WorkProcess workProcess;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkProcess createEntity(EntityManager em) {
        WorkProcess workProcess = new WorkProcess()
            .title(DEFAULT_TITLE)
            .receiveDate(DEFAULT_RECEIVE_DATE)
            .receiveOrg(DEFAULT_RECEIVE_ORG)
            .currentProcess(DEFAULT_CURRENT_PROCESS);
        return workProcess;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkProcess createUpdatedEntity(EntityManager em) {
        WorkProcess workProcess = new WorkProcess()
            .title(UPDATED_TITLE)
            .receiveDate(UPDATED_RECEIVE_DATE)
            .receiveOrg(UPDATED_RECEIVE_ORG)
            .currentProcess(UPDATED_CURRENT_PROCESS);
        return workProcess;
    }

    @BeforeEach
    public void initTest() {
        workProcess = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkProcess() throws Exception {
        int databaseSizeBeforeCreate = workProcessRepository.findAll().size();
        // Create the WorkProcess
        WorkProcessDTO workProcessDTO = workProcessMapper.toDto(workProcess);
        restWorkProcessMockMvc.perform(post("/api/work-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workProcessDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkProcess in the database
        List<WorkProcess> workProcessList = workProcessRepository.findAll();
        assertThat(workProcessList).hasSize(databaseSizeBeforeCreate + 1);
        WorkProcess testWorkProcess = workProcessList.get(workProcessList.size() - 1);
        assertThat(testWorkProcess.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testWorkProcess.getReceiveDate()).isEqualTo(DEFAULT_RECEIVE_DATE);
        assertThat(testWorkProcess.getReceiveOrg()).isEqualTo(DEFAULT_RECEIVE_ORG);
        assertThat(testWorkProcess.getCurrentProcess()).isEqualTo(DEFAULT_CURRENT_PROCESS);
    }

    @Test
    @Transactional
    public void createWorkProcessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workProcessRepository.findAll().size();

        // Create the WorkProcess with an existing ID
        workProcess.setId(1L);
        WorkProcessDTO workProcessDTO = workProcessMapper.toDto(workProcess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkProcessMockMvc.perform(post("/api/work-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkProcess in the database
        List<WorkProcess> workProcessList = workProcessRepository.findAll();
        assertThat(workProcessList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkProcesses() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList
        restWorkProcessMockMvc.perform(get("/api/work-processes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].receiveDate").value(hasItem(DEFAULT_RECEIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiveOrg").value(hasItem(DEFAULT_RECEIVE_ORG)))
            .andExpect(jsonPath("$.[*].currentProcess").value(hasItem(DEFAULT_CURRENT_PROCESS)));
    }
    
    @Test
    @Transactional
    public void getWorkProcess() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get the workProcess
        restWorkProcessMockMvc.perform(get("/api/work-processes/{id}", workProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workProcess.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.receiveDate").value(DEFAULT_RECEIVE_DATE.toString()))
            .andExpect(jsonPath("$.receiveOrg").value(DEFAULT_RECEIVE_ORG))
            .andExpect(jsonPath("$.currentProcess").value(DEFAULT_CURRENT_PROCESS));
    }


    @Test
    @Transactional
    public void getWorkProcessesByIdFiltering() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        Long id = workProcess.getId();

        defaultWorkProcessShouldBeFound("id.equals=" + id);
        defaultWorkProcessShouldNotBeFound("id.notEquals=" + id);

        defaultWorkProcessShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkProcessShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkProcessShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkProcessShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWorkProcessesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where title equals to DEFAULT_TITLE
        defaultWorkProcessShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the workProcessList where title equals to UPDATED_TITLE
        defaultWorkProcessShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where title not equals to DEFAULT_TITLE
        defaultWorkProcessShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the workProcessList where title not equals to UPDATED_TITLE
        defaultWorkProcessShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultWorkProcessShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the workProcessList where title equals to UPDATED_TITLE
        defaultWorkProcessShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where title is not null
        defaultWorkProcessShouldBeFound("title.specified=true");

        // Get all the workProcessList where title is null
        defaultWorkProcessShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllWorkProcessesByTitleContainsSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where title contains DEFAULT_TITLE
        defaultWorkProcessShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the workProcessList where title contains UPDATED_TITLE
        defaultWorkProcessShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where title does not contain DEFAULT_TITLE
        defaultWorkProcessShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the workProcessList where title does not contain UPDATED_TITLE
        defaultWorkProcessShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllWorkProcessesByReceiveDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where receiveDate equals to DEFAULT_RECEIVE_DATE
        defaultWorkProcessShouldBeFound("receiveDate.equals=" + DEFAULT_RECEIVE_DATE);

        // Get all the workProcessList where receiveDate equals to UPDATED_RECEIVE_DATE
        defaultWorkProcessShouldNotBeFound("receiveDate.equals=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByReceiveDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where receiveDate not equals to DEFAULT_RECEIVE_DATE
        defaultWorkProcessShouldNotBeFound("receiveDate.notEquals=" + DEFAULT_RECEIVE_DATE);

        // Get all the workProcessList where receiveDate not equals to UPDATED_RECEIVE_DATE
        defaultWorkProcessShouldBeFound("receiveDate.notEquals=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByReceiveDateIsInShouldWork() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where receiveDate in DEFAULT_RECEIVE_DATE or UPDATED_RECEIVE_DATE
        defaultWorkProcessShouldBeFound("receiveDate.in=" + DEFAULT_RECEIVE_DATE + "," + UPDATED_RECEIVE_DATE);

        // Get all the workProcessList where receiveDate equals to UPDATED_RECEIVE_DATE
        defaultWorkProcessShouldNotBeFound("receiveDate.in=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByReceiveDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where receiveDate is not null
        defaultWorkProcessShouldBeFound("receiveDate.specified=true");

        // Get all the workProcessList where receiveDate is null
        defaultWorkProcessShouldNotBeFound("receiveDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByReceiveDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where receiveDate is greater than or equal to DEFAULT_RECEIVE_DATE
        defaultWorkProcessShouldBeFound("receiveDate.greaterThanOrEqual=" + DEFAULT_RECEIVE_DATE);

        // Get all the workProcessList where receiveDate is greater than or equal to UPDATED_RECEIVE_DATE
        defaultWorkProcessShouldNotBeFound("receiveDate.greaterThanOrEqual=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByReceiveDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where receiveDate is less than or equal to DEFAULT_RECEIVE_DATE
        defaultWorkProcessShouldBeFound("receiveDate.lessThanOrEqual=" + DEFAULT_RECEIVE_DATE);

        // Get all the workProcessList where receiveDate is less than or equal to SMALLER_RECEIVE_DATE
        defaultWorkProcessShouldNotBeFound("receiveDate.lessThanOrEqual=" + SMALLER_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByReceiveDateIsLessThanSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where receiveDate is less than DEFAULT_RECEIVE_DATE
        defaultWorkProcessShouldNotBeFound("receiveDate.lessThan=" + DEFAULT_RECEIVE_DATE);

        // Get all the workProcessList where receiveDate is less than UPDATED_RECEIVE_DATE
        defaultWorkProcessShouldBeFound("receiveDate.lessThan=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByReceiveDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where receiveDate is greater than DEFAULT_RECEIVE_DATE
        defaultWorkProcessShouldNotBeFound("receiveDate.greaterThan=" + DEFAULT_RECEIVE_DATE);

        // Get all the workProcessList where receiveDate is greater than SMALLER_RECEIVE_DATE
        defaultWorkProcessShouldBeFound("receiveDate.greaterThan=" + SMALLER_RECEIVE_DATE);
    }


    @Test
    @Transactional
    public void getAllWorkProcessesByReceiveOrgIsEqualToSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where receiveOrg equals to DEFAULT_RECEIVE_ORG
        defaultWorkProcessShouldBeFound("receiveOrg.equals=" + DEFAULT_RECEIVE_ORG);

        // Get all the workProcessList where receiveOrg equals to UPDATED_RECEIVE_ORG
        defaultWorkProcessShouldNotBeFound("receiveOrg.equals=" + UPDATED_RECEIVE_ORG);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByReceiveOrgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where receiveOrg not equals to DEFAULT_RECEIVE_ORG
        defaultWorkProcessShouldNotBeFound("receiveOrg.notEquals=" + DEFAULT_RECEIVE_ORG);

        // Get all the workProcessList where receiveOrg not equals to UPDATED_RECEIVE_ORG
        defaultWorkProcessShouldBeFound("receiveOrg.notEquals=" + UPDATED_RECEIVE_ORG);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByReceiveOrgIsInShouldWork() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where receiveOrg in DEFAULT_RECEIVE_ORG or UPDATED_RECEIVE_ORG
        defaultWorkProcessShouldBeFound("receiveOrg.in=" + DEFAULT_RECEIVE_ORG + "," + UPDATED_RECEIVE_ORG);

        // Get all the workProcessList where receiveOrg equals to UPDATED_RECEIVE_ORG
        defaultWorkProcessShouldNotBeFound("receiveOrg.in=" + UPDATED_RECEIVE_ORG);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByReceiveOrgIsNullOrNotNull() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where receiveOrg is not null
        defaultWorkProcessShouldBeFound("receiveOrg.specified=true");

        // Get all the workProcessList where receiveOrg is null
        defaultWorkProcessShouldNotBeFound("receiveOrg.specified=false");
    }
                @Test
    @Transactional
    public void getAllWorkProcessesByReceiveOrgContainsSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where receiveOrg contains DEFAULT_RECEIVE_ORG
        defaultWorkProcessShouldBeFound("receiveOrg.contains=" + DEFAULT_RECEIVE_ORG);

        // Get all the workProcessList where receiveOrg contains UPDATED_RECEIVE_ORG
        defaultWorkProcessShouldNotBeFound("receiveOrg.contains=" + UPDATED_RECEIVE_ORG);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByReceiveOrgNotContainsSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where receiveOrg does not contain DEFAULT_RECEIVE_ORG
        defaultWorkProcessShouldNotBeFound("receiveOrg.doesNotContain=" + DEFAULT_RECEIVE_ORG);

        // Get all the workProcessList where receiveOrg does not contain UPDATED_RECEIVE_ORG
        defaultWorkProcessShouldBeFound("receiveOrg.doesNotContain=" + UPDATED_RECEIVE_ORG);
    }


    @Test
    @Transactional
    public void getAllWorkProcessesByCurrentProcessIsEqualToSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where currentProcess equals to DEFAULT_CURRENT_PROCESS
        defaultWorkProcessShouldBeFound("currentProcess.equals=" + DEFAULT_CURRENT_PROCESS);

        // Get all the workProcessList where currentProcess equals to UPDATED_CURRENT_PROCESS
        defaultWorkProcessShouldNotBeFound("currentProcess.equals=" + UPDATED_CURRENT_PROCESS);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByCurrentProcessIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where currentProcess not equals to DEFAULT_CURRENT_PROCESS
        defaultWorkProcessShouldNotBeFound("currentProcess.notEquals=" + DEFAULT_CURRENT_PROCESS);

        // Get all the workProcessList where currentProcess not equals to UPDATED_CURRENT_PROCESS
        defaultWorkProcessShouldBeFound("currentProcess.notEquals=" + UPDATED_CURRENT_PROCESS);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByCurrentProcessIsInShouldWork() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where currentProcess in DEFAULT_CURRENT_PROCESS or UPDATED_CURRENT_PROCESS
        defaultWorkProcessShouldBeFound("currentProcess.in=" + DEFAULT_CURRENT_PROCESS + "," + UPDATED_CURRENT_PROCESS);

        // Get all the workProcessList where currentProcess equals to UPDATED_CURRENT_PROCESS
        defaultWorkProcessShouldNotBeFound("currentProcess.in=" + UPDATED_CURRENT_PROCESS);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByCurrentProcessIsNullOrNotNull() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where currentProcess is not null
        defaultWorkProcessShouldBeFound("currentProcess.specified=true");

        // Get all the workProcessList where currentProcess is null
        defaultWorkProcessShouldNotBeFound("currentProcess.specified=false");
    }
                @Test
    @Transactional
    public void getAllWorkProcessesByCurrentProcessContainsSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where currentProcess contains DEFAULT_CURRENT_PROCESS
        defaultWorkProcessShouldBeFound("currentProcess.contains=" + DEFAULT_CURRENT_PROCESS);

        // Get all the workProcessList where currentProcess contains UPDATED_CURRENT_PROCESS
        defaultWorkProcessShouldNotBeFound("currentProcess.contains=" + UPDATED_CURRENT_PROCESS);
    }

    @Test
    @Transactional
    public void getAllWorkProcessesByCurrentProcessNotContainsSomething() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        // Get all the workProcessList where currentProcess does not contain DEFAULT_CURRENT_PROCESS
        defaultWorkProcessShouldNotBeFound("currentProcess.doesNotContain=" + DEFAULT_CURRENT_PROCESS);

        // Get all the workProcessList where currentProcess does not contain UPDATED_CURRENT_PROCESS
        defaultWorkProcessShouldBeFound("currentProcess.doesNotContain=" + UPDATED_CURRENT_PROCESS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkProcessShouldBeFound(String filter) throws Exception {
        restWorkProcessMockMvc.perform(get("/api/work-processes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].receiveDate").value(hasItem(DEFAULT_RECEIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiveOrg").value(hasItem(DEFAULT_RECEIVE_ORG)))
            .andExpect(jsonPath("$.[*].currentProcess").value(hasItem(DEFAULT_CURRENT_PROCESS)));

        // Check, that the count call also returns 1
        restWorkProcessMockMvc.perform(get("/api/work-processes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkProcessShouldNotBeFound(String filter) throws Exception {
        restWorkProcessMockMvc.perform(get("/api/work-processes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkProcessMockMvc.perform(get("/api/work-processes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWorkProcess() throws Exception {
        // Get the workProcess
        restWorkProcessMockMvc.perform(get("/api/work-processes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkProcess() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        int databaseSizeBeforeUpdate = workProcessRepository.findAll().size();

        // Update the workProcess
        WorkProcess updatedWorkProcess = workProcessRepository.findById(workProcess.getId()).get();
        // Disconnect from session so that the updates on updatedWorkProcess are not directly saved in db
        em.detach(updatedWorkProcess);
        updatedWorkProcess
            .title(UPDATED_TITLE)
            .receiveDate(UPDATED_RECEIVE_DATE)
            .receiveOrg(UPDATED_RECEIVE_ORG)
            .currentProcess(UPDATED_CURRENT_PROCESS);
        WorkProcessDTO workProcessDTO = workProcessMapper.toDto(updatedWorkProcess);

        restWorkProcessMockMvc.perform(put("/api/work-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workProcessDTO)))
            .andExpect(status().isOk());

        // Validate the WorkProcess in the database
        List<WorkProcess> workProcessList = workProcessRepository.findAll();
        assertThat(workProcessList).hasSize(databaseSizeBeforeUpdate);
        WorkProcess testWorkProcess = workProcessList.get(workProcessList.size() - 1);
        assertThat(testWorkProcess.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testWorkProcess.getReceiveDate()).isEqualTo(UPDATED_RECEIVE_DATE);
        assertThat(testWorkProcess.getReceiveOrg()).isEqualTo(UPDATED_RECEIVE_ORG);
        assertThat(testWorkProcess.getCurrentProcess()).isEqualTo(UPDATED_CURRENT_PROCESS);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkProcess() throws Exception {
        int databaseSizeBeforeUpdate = workProcessRepository.findAll().size();

        // Create the WorkProcess
        WorkProcessDTO workProcessDTO = workProcessMapper.toDto(workProcess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkProcessMockMvc.perform(put("/api/work-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkProcess in the database
        List<WorkProcess> workProcessList = workProcessRepository.findAll();
        assertThat(workProcessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkProcess() throws Exception {
        // Initialize the database
        workProcessRepository.saveAndFlush(workProcess);

        int databaseSizeBeforeDelete = workProcessRepository.findAll().size();

        // Delete the workProcess
        restWorkProcessMockMvc.perform(delete("/api/work-processes/{id}", workProcess.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkProcess> workProcessList = workProcessRepository.findAll();
        assertThat(workProcessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
