package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.ZhuodewenApp;
import cn.dgd.zdw.domain.SysDataLog;
import cn.dgd.zdw.repository.SysDataLogRepository;
import cn.dgd.zdw.service.SysDataLogService;
import cn.dgd.zdw.service.dto.SysDataLogDTO;
import cn.dgd.zdw.service.mapper.SysDataLogMapper;

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
 * Integration tests for the {@link SysDataLogResource} REST controller.
 */
@SpringBootTest(classes = ZhuodewenApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SysDataLogResourceIT {

    private static final String DEFAULT_TABLE_ENG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TABLE_ENG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_ID = "AAAAAAAAAA";
    private static final String UPDATED_DATA_ID = "BBBBBBBBBB";

    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_VERSION = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_DATA_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_REALNAME = "AAAAAAAAAA";
    private static final String UPDATED_REALNAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_TIME = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SysDataLogRepository sysDataLogRepository;

    @Autowired
    private SysDataLogMapper sysDataLogMapper;

    @Autowired
    private SysDataLogService sysDataLogService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSysDataLogMockMvc;

    private SysDataLog sysDataLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysDataLog createEntity(EntityManager em) {
        SysDataLog sysDataLog = new SysDataLog()
            .tableEngName(DEFAULT_TABLE_ENG_NAME)
            .dataId(DEFAULT_DATA_ID)
            .version(DEFAULT_VERSION)
            .dataContent(DEFAULT_DATA_CONTENT)
            .username(DEFAULT_USERNAME)
            .realname(DEFAULT_REALNAME)
            .desc(DEFAULT_DESC)
            .createTime(DEFAULT_CREATE_TIME);
        return sysDataLog;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysDataLog createUpdatedEntity(EntityManager em) {
        SysDataLog sysDataLog = new SysDataLog()
            .tableEngName(UPDATED_TABLE_ENG_NAME)
            .dataId(UPDATED_DATA_ID)
            .version(UPDATED_VERSION)
            .dataContent(UPDATED_DATA_CONTENT)
            .username(UPDATED_USERNAME)
            .realname(UPDATED_REALNAME)
            .desc(UPDATED_DESC)
            .createTime(UPDATED_CREATE_TIME);
        return sysDataLog;
    }

    @BeforeEach
    public void initTest() {
        sysDataLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysDataLog() throws Exception {
        int databaseSizeBeforeCreate = sysDataLogRepository.findAll().size();
        // Create the SysDataLog
        SysDataLogDTO sysDataLogDTO = sysDataLogMapper.toDto(sysDataLog);
        restSysDataLogMockMvc.perform(post("/api/sys-data-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDataLogDTO)))
            .andExpect(status().isCreated());

        // Validate the SysDataLog in the database
        List<SysDataLog> sysDataLogList = sysDataLogRepository.findAll();
        assertThat(sysDataLogList).hasSize(databaseSizeBeforeCreate + 1);
        SysDataLog testSysDataLog = sysDataLogList.get(sysDataLogList.size() - 1);
        assertThat(testSysDataLog.getTableEngName()).isEqualTo(DEFAULT_TABLE_ENG_NAME);
        assertThat(testSysDataLog.getDataId()).isEqualTo(DEFAULT_DATA_ID);
        assertThat(testSysDataLog.getVersion()).isEqualTo(DEFAULT_VERSION);
        assertThat(testSysDataLog.getDataContent()).isEqualTo(DEFAULT_DATA_CONTENT);
        assertThat(testSysDataLog.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testSysDataLog.getRealname()).isEqualTo(DEFAULT_REALNAME);
        assertThat(testSysDataLog.getDesc()).isEqualTo(DEFAULT_DESC);
        assertThat(testSysDataLog.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
    }

    @Test
    @Transactional
    public void createSysDataLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysDataLogRepository.findAll().size();

        // Create the SysDataLog with an existing ID
        sysDataLog.setId(1L);
        SysDataLogDTO sysDataLogDTO = sysDataLogMapper.toDto(sysDataLog);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysDataLogMockMvc.perform(post("/api/sys-data-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDataLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysDataLog in the database
        List<SysDataLog> sysDataLogList = sysDataLogRepository.findAll();
        assertThat(sysDataLogList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysDataLogs() throws Exception {
        // Initialize the database
        sysDataLogRepository.saveAndFlush(sysDataLog);

        // Get all the sysDataLogList
        restSysDataLogMockMvc.perform(get("/api/sys-data-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysDataLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].tableEngName").value(hasItem(DEFAULT_TABLE_ENG_NAME)))
            .andExpect(jsonPath("$.[*].dataId").value(hasItem(DEFAULT_DATA_ID)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].dataContent").value(hasItem(DEFAULT_DATA_CONTENT)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].realname").value(hasItem(DEFAULT_REALNAME)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getSysDataLog() throws Exception {
        // Initialize the database
        sysDataLogRepository.saveAndFlush(sysDataLog);

        // Get the sysDataLog
        restSysDataLogMockMvc.perform(get("/api/sys-data-logs/{id}", sysDataLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sysDataLog.getId().intValue()))
            .andExpect(jsonPath("$.tableEngName").value(DEFAULT_TABLE_ENG_NAME))
            .andExpect(jsonPath("$.dataId").value(DEFAULT_DATA_ID))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.dataContent").value(DEFAULT_DATA_CONTENT))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.realname").value(DEFAULT_REALNAME))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingSysDataLog() throws Exception {
        // Get the sysDataLog
        restSysDataLogMockMvc.perform(get("/api/sys-data-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysDataLog() throws Exception {
        // Initialize the database
        sysDataLogRepository.saveAndFlush(sysDataLog);

        int databaseSizeBeforeUpdate = sysDataLogRepository.findAll().size();

        // Update the sysDataLog
        SysDataLog updatedSysDataLog = sysDataLogRepository.findById(sysDataLog.getId()).get();
        // Disconnect from session so that the updates on updatedSysDataLog are not directly saved in db
        em.detach(updatedSysDataLog);
        updatedSysDataLog
            .tableEngName(UPDATED_TABLE_ENG_NAME)
            .dataId(UPDATED_DATA_ID)
            .version(UPDATED_VERSION)
            .dataContent(UPDATED_DATA_CONTENT)
            .username(UPDATED_USERNAME)
            .realname(UPDATED_REALNAME)
            .desc(UPDATED_DESC)
            .createTime(UPDATED_CREATE_TIME);
        SysDataLogDTO sysDataLogDTO = sysDataLogMapper.toDto(updatedSysDataLog);

        restSysDataLogMockMvc.perform(put("/api/sys-data-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDataLogDTO)))
            .andExpect(status().isOk());

        // Validate the SysDataLog in the database
        List<SysDataLog> sysDataLogList = sysDataLogRepository.findAll();
        assertThat(sysDataLogList).hasSize(databaseSizeBeforeUpdate);
        SysDataLog testSysDataLog = sysDataLogList.get(sysDataLogList.size() - 1);
        assertThat(testSysDataLog.getTableEngName()).isEqualTo(UPDATED_TABLE_ENG_NAME);
        assertThat(testSysDataLog.getDataId()).isEqualTo(UPDATED_DATA_ID);
        assertThat(testSysDataLog.getVersion()).isEqualTo(UPDATED_VERSION);
        assertThat(testSysDataLog.getDataContent()).isEqualTo(UPDATED_DATA_CONTENT);
        assertThat(testSysDataLog.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testSysDataLog.getRealname()).isEqualTo(UPDATED_REALNAME);
        assertThat(testSysDataLog.getDesc()).isEqualTo(UPDATED_DESC);
        assertThat(testSysDataLog.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSysDataLog() throws Exception {
        int databaseSizeBeforeUpdate = sysDataLogRepository.findAll().size();

        // Create the SysDataLog
        SysDataLogDTO sysDataLogDTO = sysDataLogMapper.toDto(sysDataLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysDataLogMockMvc.perform(put("/api/sys-data-logs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysDataLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysDataLog in the database
        List<SysDataLog> sysDataLogList = sysDataLogRepository.findAll();
        assertThat(sysDataLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysDataLog() throws Exception {
        // Initialize the database
        sysDataLogRepository.saveAndFlush(sysDataLog);

        int databaseSizeBeforeDelete = sysDataLogRepository.findAll().size();

        // Delete the sysDataLog
        restSysDataLogMockMvc.perform(delete("/api/sys-data-logs/{id}", sysDataLog.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysDataLog> sysDataLogList = sysDataLogRepository.findAll();
        assertThat(sysDataLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
