package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.ZhuodewenApp;
import cn.dgd.zdw.domain.TSysWorkProcess;
import cn.dgd.zdw.repository.TSysWorkProcessRepository;
import cn.dgd.zdw.service.TSysWorkProcessService;
import cn.dgd.zdw.service.dto.TSysWorkProcessDTO;
import cn.dgd.zdw.service.mapper.TSysWorkProcessMapper;

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
 * Integration tests for the {@link TSysWorkProcessResource} REST controller.
 */
@SpringBootTest(classes = ZhuodewenApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TSysWorkProcessResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RECEIVE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECEIVE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RECEIVE_ORG = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVE_ORG = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_PROCESS = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_PROCESS = "BBBBBBBBBB";

    @Autowired
    private TSysWorkProcessRepository tSysWorkProcessRepository;

    @Autowired
    private TSysWorkProcessMapper tSysWorkProcessMapper;

    @Autowired
    private TSysWorkProcessService tSysWorkProcessService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTSysWorkProcessMockMvc;

    private TSysWorkProcess tSysWorkProcess;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TSysWorkProcess createEntity(EntityManager em) {
        TSysWorkProcess tSysWorkProcess = new TSysWorkProcess()
            .title(DEFAULT_TITLE)
            .receiveDate(DEFAULT_RECEIVE_DATE)
            .receiveOrg(DEFAULT_RECEIVE_ORG)
            .currentProcess(DEFAULT_CURRENT_PROCESS);
        return tSysWorkProcess;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TSysWorkProcess createUpdatedEntity(EntityManager em) {
        TSysWorkProcess tSysWorkProcess = new TSysWorkProcess()
            .title(UPDATED_TITLE)
            .receiveDate(UPDATED_RECEIVE_DATE)
            .receiveOrg(UPDATED_RECEIVE_ORG)
            .currentProcess(UPDATED_CURRENT_PROCESS);
        return tSysWorkProcess;
    }

    @BeforeEach
    public void initTest() {
        tSysWorkProcess = createEntity(em);
    }

    @Test
    @Transactional
    public void createTSysWorkProcess() throws Exception {
        int databaseSizeBeforeCreate = tSysWorkProcessRepository.findAll().size();
        // Create the TSysWorkProcess
        TSysWorkProcessDTO tSysWorkProcessDTO = tSysWorkProcessMapper.toDto(tSysWorkProcess);
        restTSysWorkProcessMockMvc.perform(post("/api/t-sys-work-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tSysWorkProcessDTO)))
            .andExpect(status().isCreated());

        // Validate the TSysWorkProcess in the database
        List<TSysWorkProcess> tSysWorkProcessList = tSysWorkProcessRepository.findAll();
        assertThat(tSysWorkProcessList).hasSize(databaseSizeBeforeCreate + 1);
        TSysWorkProcess testTSysWorkProcess = tSysWorkProcessList.get(tSysWorkProcessList.size() - 1);
        assertThat(testTSysWorkProcess.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTSysWorkProcess.getReceiveDate()).isEqualTo(DEFAULT_RECEIVE_DATE);
        assertThat(testTSysWorkProcess.getReceiveOrg()).isEqualTo(DEFAULT_RECEIVE_ORG);
        assertThat(testTSysWorkProcess.getCurrentProcess()).isEqualTo(DEFAULT_CURRENT_PROCESS);
    }

    @Test
    @Transactional
    public void createTSysWorkProcessWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tSysWorkProcessRepository.findAll().size();

        // Create the TSysWorkProcess with an existing ID
        tSysWorkProcess.setId(1L);
        TSysWorkProcessDTO tSysWorkProcessDTO = tSysWorkProcessMapper.toDto(tSysWorkProcess);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTSysWorkProcessMockMvc.perform(post("/api/t-sys-work-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tSysWorkProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TSysWorkProcess in the database
        List<TSysWorkProcess> tSysWorkProcessList = tSysWorkProcessRepository.findAll();
        assertThat(tSysWorkProcessList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTSysWorkProcesses() throws Exception {
        // Initialize the database
        tSysWorkProcessRepository.saveAndFlush(tSysWorkProcess);

        // Get all the tSysWorkProcessList
        restTSysWorkProcessMockMvc.perform(get("/api/t-sys-work-processes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tSysWorkProcess.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].receiveDate").value(hasItem(DEFAULT_RECEIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].receiveOrg").value(hasItem(DEFAULT_RECEIVE_ORG)))
            .andExpect(jsonPath("$.[*].currentProcess").value(hasItem(DEFAULT_CURRENT_PROCESS)));
    }
    
    @Test
    @Transactional
    public void getTSysWorkProcess() throws Exception {
        // Initialize the database
        tSysWorkProcessRepository.saveAndFlush(tSysWorkProcess);

        // Get the tSysWorkProcess
        restTSysWorkProcessMockMvc.perform(get("/api/t-sys-work-processes/{id}", tSysWorkProcess.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tSysWorkProcess.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.receiveDate").value(DEFAULT_RECEIVE_DATE.toString()))
            .andExpect(jsonPath("$.receiveOrg").value(DEFAULT_RECEIVE_ORG))
            .andExpect(jsonPath("$.currentProcess").value(DEFAULT_CURRENT_PROCESS));
    }
    @Test
    @Transactional
    public void getNonExistingTSysWorkProcess() throws Exception {
        // Get the tSysWorkProcess
        restTSysWorkProcessMockMvc.perform(get("/api/t-sys-work-processes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTSysWorkProcess() throws Exception {
        // Initialize the database
        tSysWorkProcessRepository.saveAndFlush(tSysWorkProcess);

        int databaseSizeBeforeUpdate = tSysWorkProcessRepository.findAll().size();

        // Update the tSysWorkProcess
        TSysWorkProcess updatedTSysWorkProcess = tSysWorkProcessRepository.findById(tSysWorkProcess.getId()).get();
        // Disconnect from session so that the updates on updatedTSysWorkProcess are not directly saved in db
        em.detach(updatedTSysWorkProcess);
        updatedTSysWorkProcess
            .title(UPDATED_TITLE)
            .receiveDate(UPDATED_RECEIVE_DATE)
            .receiveOrg(UPDATED_RECEIVE_ORG)
            .currentProcess(UPDATED_CURRENT_PROCESS);
        TSysWorkProcessDTO tSysWorkProcessDTO = tSysWorkProcessMapper.toDto(updatedTSysWorkProcess);

        restTSysWorkProcessMockMvc.perform(put("/api/t-sys-work-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tSysWorkProcessDTO)))
            .andExpect(status().isOk());

        // Validate the TSysWorkProcess in the database
        List<TSysWorkProcess> tSysWorkProcessList = tSysWorkProcessRepository.findAll();
        assertThat(tSysWorkProcessList).hasSize(databaseSizeBeforeUpdate);
        TSysWorkProcess testTSysWorkProcess = tSysWorkProcessList.get(tSysWorkProcessList.size() - 1);
        assertThat(testTSysWorkProcess.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTSysWorkProcess.getReceiveDate()).isEqualTo(UPDATED_RECEIVE_DATE);
        assertThat(testTSysWorkProcess.getReceiveOrg()).isEqualTo(UPDATED_RECEIVE_ORG);
        assertThat(testTSysWorkProcess.getCurrentProcess()).isEqualTo(UPDATED_CURRENT_PROCESS);
    }

    @Test
    @Transactional
    public void updateNonExistingTSysWorkProcess() throws Exception {
        int databaseSizeBeforeUpdate = tSysWorkProcessRepository.findAll().size();

        // Create the TSysWorkProcess
        TSysWorkProcessDTO tSysWorkProcessDTO = tSysWorkProcessMapper.toDto(tSysWorkProcess);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTSysWorkProcessMockMvc.perform(put("/api/t-sys-work-processes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tSysWorkProcessDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TSysWorkProcess in the database
        List<TSysWorkProcess> tSysWorkProcessList = tSysWorkProcessRepository.findAll();
        assertThat(tSysWorkProcessList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTSysWorkProcess() throws Exception {
        // Initialize the database
        tSysWorkProcessRepository.saveAndFlush(tSysWorkProcess);

        int databaseSizeBeforeDelete = tSysWorkProcessRepository.findAll().size();

        // Delete the tSysWorkProcess
        restTSysWorkProcessMockMvc.perform(delete("/api/t-sys-work-processes/{id}", tSysWorkProcess.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TSysWorkProcess> tSysWorkProcessList = tSysWorkProcessRepository.findAll();
        assertThat(tSysWorkProcessList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
