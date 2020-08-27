package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.ZhuodewenApp;
import cn.dgd.zdw.domain.SysWorkProcess;
import cn.dgd.zdw.repository.SysWorkProcessRepository;
import cn.dgd.zdw.service.SysWorkProcessService;
import cn.dgd.zdw.service.dto.SysWorkProcessDTO;
import cn.dgd.zdw.service.mapper.SysWorkProcessMapper;
import cn.dgd.zdw.service.dto.SysWorkProcessCriteria;
import cn.dgd.zdw.service.SysWorkProcessQueryService;

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
 * Integration tests for the {@link SysWorkProcessResource} REST controller.
 */
@SpringBootTest(classes = ZhuodewenApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SysWorkProcessResourceIT {

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
    private SysWorkProcessRepository sysWorkProcessRepository;

    @Autowired
    private SysWorkProcessMapper sysWorkProcessMapper;

    @Autowired
    private SysWorkProcessService sysWorkProcessService;

    @Autowired
    private SysWorkProcessQueryService sysWorkProcessQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSysWorkProcessMockMvc;

    private SysWorkProcess sysWorkProcess;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysWorkProcess createEntity(EntityManager em) {
        SysWorkProcess sysWorkProcess = new SysWorkProcess()
            .title(DEFAULT_TITLE)
            .receiveDate(DEFAULT_RECEIVE_DATE)
            .receiveOrg(DEFAULT_RECEIVE_ORG)
            .currentProcess(DEFAULT_CURRENT_PROCESS);
        return sysWorkProcess;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysWorkProcess createUpdatedEntity(EntityManager em) {
        SysWorkProcess sysWorkProcess = new SysWorkProcess()
            .title(UPDATED_TITLE)
            .receiveDate(UPDATED_RECEIVE_DATE)
            .receiveOrg(UPDATED_RECEIVE_ORG)
            .currentProcess(UPDATED_CURRENT_PROCESS);
        return sysWorkProcess;
    }

    @BeforeEach
    public void initTest() {
        sysWorkProcess = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysWorkProcess() throws Exception {
        int databaseSizeBeforeCreate = sysWorkProcessRepository.findAll().size();
        // Create the SysWorkProcess
        SysWorkProcessDTO sysWorkProcessDTO = sysWorkProcessMapper.toDto(sysWorkProcess);
        restSysWorkProcessMockMvc.perform(post("/api/sys-work-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysWorkProcessDTO)))
            .andExpect(status().isCreated());

        // Validate the SysWorkProcess in the database
        List<SysWorkProcess> sysWorkProcessList = sysWorkProcessRepository.findAll();
        assertThat(sysWorkProcessList).hasSize(databaseSizeBeforeCreate + 1);
        SysWorkProcess testSysWorkProcess = sysWorkProcessList.get(sysWorkProcessList.size() - 1);
        assertThat(testSysWorkProcess.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSysWorkProcess.getReceiveDate()).isEqualTo(DEFAULT_RECEIVE_DATE);
        assertThat(testSysWorkProcess.getReceiveOrg()).isEqualTo(DEFAULT_RECEIVE_ORG);
        assertThat(testSysWorkProcess.getCurrentProcess()).isEqualTo(DEFAULT_CURRENT_PROCESS);
    }

    @Test
    @Transactional
    public void createSysWorkProcessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysWorkProcessRepository.findAll().size();

        // Create the SysWorkProcess with an existing ID
        sysWorkProcess.setId(1L);
        SysWorkProcessDTO sysWorkProcessDTO = sysWorkProcessMapper.toDto(sysWorkProcess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysWorkProcessMockMvc.perform(post("/api/sys-work-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysWorkProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysWorkProcess in the database
        List<SysWorkProcess> sysWorkProcessList = sysWorkProcessRepository.findAll();
        assertThat(sysWorkProcessList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysWorkProcesses() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList
        restSysWorkProcessMockMvc.perform(get("/api/sys-work-processes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysWorkProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].receiveDate").value(hasItem(DEFAULT_RECEIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiveOrg").value(hasItem(DEFAULT_RECEIVE_ORG)))
            .andExpect(jsonPath("$.[*].currentProcess").value(hasItem(DEFAULT_CURRENT_PROCESS)));
    }
    
    @Test
    @Transactional
    public void getSysWorkProcess() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get the sysWorkProcess
        restSysWorkProcessMockMvc.perform(get("/api/sys-work-processes/{id}", sysWorkProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sysWorkProcess.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.receiveDate").value(DEFAULT_RECEIVE_DATE.toString()))
            .andExpect(jsonPath("$.receiveOrg").value(DEFAULT_RECEIVE_ORG))
            .andExpect(jsonPath("$.currentProcess").value(DEFAULT_CURRENT_PROCESS));
    }


    @Test
    @Transactional
    public void getSysWorkProcessesByIdFiltering() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        Long id = sysWorkProcess.getId();

        defaultSysWorkProcessShouldBeFound("id.equals=" + id);
        defaultSysWorkProcessShouldNotBeFound("id.notEquals=" + id);

        defaultSysWorkProcessShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSysWorkProcessShouldNotBeFound("id.greaterThan=" + id);

        defaultSysWorkProcessShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSysWorkProcessShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSysWorkProcessesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where title equals to DEFAULT_TITLE
        defaultSysWorkProcessShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the sysWorkProcessList where title equals to UPDATED_TITLE
        defaultSysWorkProcessShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where title not equals to DEFAULT_TITLE
        defaultSysWorkProcessShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the sysWorkProcessList where title not equals to UPDATED_TITLE
        defaultSysWorkProcessShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultSysWorkProcessShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the sysWorkProcessList where title equals to UPDATED_TITLE
        defaultSysWorkProcessShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where title is not null
        defaultSysWorkProcessShouldBeFound("title.specified=true");

        // Get all the sysWorkProcessList where title is null
        defaultSysWorkProcessShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysWorkProcessesByTitleContainsSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where title contains DEFAULT_TITLE
        defaultSysWorkProcessShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the sysWorkProcessList where title contains UPDATED_TITLE
        defaultSysWorkProcessShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where title does not contain DEFAULT_TITLE
        defaultSysWorkProcessShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the sysWorkProcessList where title does not contain UPDATED_TITLE
        defaultSysWorkProcessShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllSysWorkProcessesByReceiveDateIsEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where receiveDate equals to DEFAULT_RECEIVE_DATE
        defaultSysWorkProcessShouldBeFound("receiveDate.equals=" + DEFAULT_RECEIVE_DATE);

        // Get all the sysWorkProcessList where receiveDate equals to UPDATED_RECEIVE_DATE
        defaultSysWorkProcessShouldNotBeFound("receiveDate.equals=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByReceiveDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where receiveDate not equals to DEFAULT_RECEIVE_DATE
        defaultSysWorkProcessShouldNotBeFound("receiveDate.notEquals=" + DEFAULT_RECEIVE_DATE);

        // Get all the sysWorkProcessList where receiveDate not equals to UPDATED_RECEIVE_DATE
        defaultSysWorkProcessShouldBeFound("receiveDate.notEquals=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByReceiveDateIsInShouldWork() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where receiveDate in DEFAULT_RECEIVE_DATE or UPDATED_RECEIVE_DATE
        defaultSysWorkProcessShouldBeFound("receiveDate.in=" + DEFAULT_RECEIVE_DATE + "," + UPDATED_RECEIVE_DATE);

        // Get all the sysWorkProcessList where receiveDate equals to UPDATED_RECEIVE_DATE
        defaultSysWorkProcessShouldNotBeFound("receiveDate.in=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByReceiveDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where receiveDate is not null
        defaultSysWorkProcessShouldBeFound("receiveDate.specified=true");

        // Get all the sysWorkProcessList where receiveDate is null
        defaultSysWorkProcessShouldNotBeFound("receiveDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByReceiveDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where receiveDate is greater than or equal to DEFAULT_RECEIVE_DATE
        defaultSysWorkProcessShouldBeFound("receiveDate.greaterThanOrEqual=" + DEFAULT_RECEIVE_DATE);

        // Get all the sysWorkProcessList where receiveDate is greater than or equal to UPDATED_RECEIVE_DATE
        defaultSysWorkProcessShouldNotBeFound("receiveDate.greaterThanOrEqual=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByReceiveDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where receiveDate is less than or equal to DEFAULT_RECEIVE_DATE
        defaultSysWorkProcessShouldBeFound("receiveDate.lessThanOrEqual=" + DEFAULT_RECEIVE_DATE);

        // Get all the sysWorkProcessList where receiveDate is less than or equal to SMALLER_RECEIVE_DATE
        defaultSysWorkProcessShouldNotBeFound("receiveDate.lessThanOrEqual=" + SMALLER_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByReceiveDateIsLessThanSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where receiveDate is less than DEFAULT_RECEIVE_DATE
        defaultSysWorkProcessShouldNotBeFound("receiveDate.lessThan=" + DEFAULT_RECEIVE_DATE);

        // Get all the sysWorkProcessList where receiveDate is less than UPDATED_RECEIVE_DATE
        defaultSysWorkProcessShouldBeFound("receiveDate.lessThan=" + UPDATED_RECEIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByReceiveDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where receiveDate is greater than DEFAULT_RECEIVE_DATE
        defaultSysWorkProcessShouldNotBeFound("receiveDate.greaterThan=" + DEFAULT_RECEIVE_DATE);

        // Get all the sysWorkProcessList where receiveDate is greater than SMALLER_RECEIVE_DATE
        defaultSysWorkProcessShouldBeFound("receiveDate.greaterThan=" + SMALLER_RECEIVE_DATE);
    }


    @Test
    @Transactional
    public void getAllSysWorkProcessesByReceiveOrgIsEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where receiveOrg equals to DEFAULT_RECEIVE_ORG
        defaultSysWorkProcessShouldBeFound("receiveOrg.equals=" + DEFAULT_RECEIVE_ORG);

        // Get all the sysWorkProcessList where receiveOrg equals to UPDATED_RECEIVE_ORG
        defaultSysWorkProcessShouldNotBeFound("receiveOrg.equals=" + UPDATED_RECEIVE_ORG);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByReceiveOrgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where receiveOrg not equals to DEFAULT_RECEIVE_ORG
        defaultSysWorkProcessShouldNotBeFound("receiveOrg.notEquals=" + DEFAULT_RECEIVE_ORG);

        // Get all the sysWorkProcessList where receiveOrg not equals to UPDATED_RECEIVE_ORG
        defaultSysWorkProcessShouldBeFound("receiveOrg.notEquals=" + UPDATED_RECEIVE_ORG);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByReceiveOrgIsInShouldWork() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where receiveOrg in DEFAULT_RECEIVE_ORG or UPDATED_RECEIVE_ORG
        defaultSysWorkProcessShouldBeFound("receiveOrg.in=" + DEFAULT_RECEIVE_ORG + "," + UPDATED_RECEIVE_ORG);

        // Get all the sysWorkProcessList where receiveOrg equals to UPDATED_RECEIVE_ORG
        defaultSysWorkProcessShouldNotBeFound("receiveOrg.in=" + UPDATED_RECEIVE_ORG);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByReceiveOrgIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where receiveOrg is not null
        defaultSysWorkProcessShouldBeFound("receiveOrg.specified=true");

        // Get all the sysWorkProcessList where receiveOrg is null
        defaultSysWorkProcessShouldNotBeFound("receiveOrg.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysWorkProcessesByReceiveOrgContainsSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where receiveOrg contains DEFAULT_RECEIVE_ORG
        defaultSysWorkProcessShouldBeFound("receiveOrg.contains=" + DEFAULT_RECEIVE_ORG);

        // Get all the sysWorkProcessList where receiveOrg contains UPDATED_RECEIVE_ORG
        defaultSysWorkProcessShouldNotBeFound("receiveOrg.contains=" + UPDATED_RECEIVE_ORG);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByReceiveOrgNotContainsSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where receiveOrg does not contain DEFAULT_RECEIVE_ORG
        defaultSysWorkProcessShouldNotBeFound("receiveOrg.doesNotContain=" + DEFAULT_RECEIVE_ORG);

        // Get all the sysWorkProcessList where receiveOrg does not contain UPDATED_RECEIVE_ORG
        defaultSysWorkProcessShouldBeFound("receiveOrg.doesNotContain=" + UPDATED_RECEIVE_ORG);
    }


    @Test
    @Transactional
    public void getAllSysWorkProcessesByCurrentProcessIsEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where currentProcess equals to DEFAULT_CURRENT_PROCESS
        defaultSysWorkProcessShouldBeFound("currentProcess.equals=" + DEFAULT_CURRENT_PROCESS);

        // Get all the sysWorkProcessList where currentProcess equals to UPDATED_CURRENT_PROCESS
        defaultSysWorkProcessShouldNotBeFound("currentProcess.equals=" + UPDATED_CURRENT_PROCESS);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByCurrentProcessIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where currentProcess not equals to DEFAULT_CURRENT_PROCESS
        defaultSysWorkProcessShouldNotBeFound("currentProcess.notEquals=" + DEFAULT_CURRENT_PROCESS);

        // Get all the sysWorkProcessList where currentProcess not equals to UPDATED_CURRENT_PROCESS
        defaultSysWorkProcessShouldBeFound("currentProcess.notEquals=" + UPDATED_CURRENT_PROCESS);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByCurrentProcessIsInShouldWork() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where currentProcess in DEFAULT_CURRENT_PROCESS or UPDATED_CURRENT_PROCESS
        defaultSysWorkProcessShouldBeFound("currentProcess.in=" + DEFAULT_CURRENT_PROCESS + "," + UPDATED_CURRENT_PROCESS);

        // Get all the sysWorkProcessList where currentProcess equals to UPDATED_CURRENT_PROCESS
        defaultSysWorkProcessShouldNotBeFound("currentProcess.in=" + UPDATED_CURRENT_PROCESS);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByCurrentProcessIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where currentProcess is not null
        defaultSysWorkProcessShouldBeFound("currentProcess.specified=true");

        // Get all the sysWorkProcessList where currentProcess is null
        defaultSysWorkProcessShouldNotBeFound("currentProcess.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysWorkProcessesByCurrentProcessContainsSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where currentProcess contains DEFAULT_CURRENT_PROCESS
        defaultSysWorkProcessShouldBeFound("currentProcess.contains=" + DEFAULT_CURRENT_PROCESS);

        // Get all the sysWorkProcessList where currentProcess contains UPDATED_CURRENT_PROCESS
        defaultSysWorkProcessShouldNotBeFound("currentProcess.contains=" + UPDATED_CURRENT_PROCESS);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessesByCurrentProcessNotContainsSomething() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        // Get all the sysWorkProcessList where currentProcess does not contain DEFAULT_CURRENT_PROCESS
        defaultSysWorkProcessShouldNotBeFound("currentProcess.doesNotContain=" + DEFAULT_CURRENT_PROCESS);

        // Get all the sysWorkProcessList where currentProcess does not contain UPDATED_CURRENT_PROCESS
        defaultSysWorkProcessShouldBeFound("currentProcess.doesNotContain=" + UPDATED_CURRENT_PROCESS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSysWorkProcessShouldBeFound(String filter) throws Exception {
        restSysWorkProcessMockMvc.perform(get("/api/sys-work-processes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysWorkProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].receiveDate").value(hasItem(DEFAULT_RECEIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiveOrg").value(hasItem(DEFAULT_RECEIVE_ORG)))
            .andExpect(jsonPath("$.[*].currentProcess").value(hasItem(DEFAULT_CURRENT_PROCESS)));

        // Check, that the count call also returns 1
        restSysWorkProcessMockMvc.perform(get("/api/sys-work-processes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSysWorkProcessShouldNotBeFound(String filter) throws Exception {
        restSysWorkProcessMockMvc.perform(get("/api/sys-work-processes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSysWorkProcessMockMvc.perform(get("/api/sys-work-processes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSysWorkProcess() throws Exception {
        // Get the sysWorkProcess
        restSysWorkProcessMockMvc.perform(get("/api/sys-work-processes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysWorkProcess() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        int databaseSizeBeforeUpdate = sysWorkProcessRepository.findAll().size();

        // Update the sysWorkProcess
        SysWorkProcess updatedSysWorkProcess = sysWorkProcessRepository.findById(sysWorkProcess.getId()).get();
        // Disconnect from session so that the updates on updatedSysWorkProcess are not directly saved in db
        em.detach(updatedSysWorkProcess);
        updatedSysWorkProcess
            .title(UPDATED_TITLE)
            .receiveDate(UPDATED_RECEIVE_DATE)
            .receiveOrg(UPDATED_RECEIVE_ORG)
            .currentProcess(UPDATED_CURRENT_PROCESS);
        SysWorkProcessDTO sysWorkProcessDTO = sysWorkProcessMapper.toDto(updatedSysWorkProcess);

        restSysWorkProcessMockMvc.perform(put("/api/sys-work-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysWorkProcessDTO)))
            .andExpect(status().isOk());

        // Validate the SysWorkProcess in the database
        List<SysWorkProcess> sysWorkProcessList = sysWorkProcessRepository.findAll();
        assertThat(sysWorkProcessList).hasSize(databaseSizeBeforeUpdate);
        SysWorkProcess testSysWorkProcess = sysWorkProcessList.get(sysWorkProcessList.size() - 1);
        assertThat(testSysWorkProcess.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSysWorkProcess.getReceiveDate()).isEqualTo(UPDATED_RECEIVE_DATE);
        assertThat(testSysWorkProcess.getReceiveOrg()).isEqualTo(UPDATED_RECEIVE_ORG);
        assertThat(testSysWorkProcess.getCurrentProcess()).isEqualTo(UPDATED_CURRENT_PROCESS);
    }

    @Test
    @Transactional
    public void updateNonExistingSysWorkProcess() throws Exception {
        int databaseSizeBeforeUpdate = sysWorkProcessRepository.findAll().size();

        // Create the SysWorkProcess
        SysWorkProcessDTO sysWorkProcessDTO = sysWorkProcessMapper.toDto(sysWorkProcess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysWorkProcessMockMvc.perform(put("/api/sys-work-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysWorkProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysWorkProcess in the database
        List<SysWorkProcess> sysWorkProcessList = sysWorkProcessRepository.findAll();
        assertThat(sysWorkProcessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysWorkProcess() throws Exception {
        // Initialize the database
        sysWorkProcessRepository.saveAndFlush(sysWorkProcess);

        int databaseSizeBeforeDelete = sysWorkProcessRepository.findAll().size();

        // Delete the sysWorkProcess
        restSysWorkProcessMockMvc.perform(delete("/api/sys-work-processes/{id}", sysWorkProcess.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysWorkProcess> sysWorkProcessList = sysWorkProcessRepository.findAll();
        assertThat(sysWorkProcessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
