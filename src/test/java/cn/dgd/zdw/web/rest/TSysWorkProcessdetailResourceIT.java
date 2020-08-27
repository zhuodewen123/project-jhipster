package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.ZhuodewenApp;
import cn.dgd.zdw.domain.TSysWorkProcessdetail;
import cn.dgd.zdw.repository.TSysWorkProcessdetailRepository;
import cn.dgd.zdw.service.TSysWorkProcessdetailService;
import cn.dgd.zdw.service.dto.TSysWorkProcessdetailDTO;
import cn.dgd.zdw.service.mapper.TSysWorkProcessdetailMapper;

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
 * Integration tests for the {@link TSysWorkProcessdetailResource} REST controller.
 */
@SpringBootTest(classes = ZhuodewenApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TSysWorkProcessdetailResourceIT {

    private static final String DEFAULT_FK_WORK_PROCESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_FK_WORK_PROCESS_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TODO_ORG = "AAAAAAAAAA";
    private static final String UPDATED_TODO_ORG = "BBBBBBBBBB";

    private static final String DEFAULT_RESULT = "AAAAAAAAAA";
    private static final String UPDATED_RESULT = "BBBBBBBBBB";

    private static final String DEFAULT_APPROVAL_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_APPROVAL_COMMENTS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RECEIVE_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECEIVE_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_APPROVAL_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPROVAL_TIME = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private TSysWorkProcessdetailRepository tSysWorkProcessdetailRepository;

    @Autowired
    private TSysWorkProcessdetailMapper tSysWorkProcessdetailMapper;

    @Autowired
    private TSysWorkProcessdetailService tSysWorkProcessdetailService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTSysWorkProcessdetailMockMvc;

    private TSysWorkProcessdetail tSysWorkProcessdetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TSysWorkProcessdetail createEntity(EntityManager em) {
        TSysWorkProcessdetail tSysWorkProcessdetail = new TSysWorkProcessdetail()
            .fkWorkProcessId(DEFAULT_FK_WORK_PROCESS_ID)
            .todoOrg(DEFAULT_TODO_ORG)
            .result(DEFAULT_RESULT)
            .approvalComments(DEFAULT_APPROVAL_COMMENTS)
            .receiveTime(DEFAULT_RECEIVE_TIME)
            .approvalTime(DEFAULT_APPROVAL_TIME);
        return tSysWorkProcessdetail;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TSysWorkProcessdetail createUpdatedEntity(EntityManager em) {
        TSysWorkProcessdetail tSysWorkProcessdetail = new TSysWorkProcessdetail()
            .fkWorkProcessId(UPDATED_FK_WORK_PROCESS_ID)
            .todoOrg(UPDATED_TODO_ORG)
            .result(UPDATED_RESULT)
            .approvalComments(UPDATED_APPROVAL_COMMENTS)
            .receiveTime(UPDATED_RECEIVE_TIME)
            .approvalTime(UPDATED_APPROVAL_TIME);
        return tSysWorkProcessdetail;
    }

    @BeforeEach
    public void initTest() {
        tSysWorkProcessdetail = createEntity(em);
    }

    @Test
    @Transactional
    public void createTSysWorkProcessdetail() throws Exception {
        int databaseSizeBeforeCreate = tSysWorkProcessdetailRepository.findAll().size();
        // Create the TSysWorkProcessdetail
        TSysWorkProcessdetailDTO tSysWorkProcessdetailDTO = tSysWorkProcessdetailMapper.toDto(tSysWorkProcessdetail);
        restTSysWorkProcessdetailMockMvc.perform(post("/api/t-sys-work-processdetails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tSysWorkProcessdetailDTO)))
            .andExpect(status().isCreated());

        // Validate the TSysWorkProcessdetail in the database
        List<TSysWorkProcessdetail> tSysWorkProcessdetailList = tSysWorkProcessdetailRepository.findAll();
        assertThat(tSysWorkProcessdetailList).hasSize(databaseSizeBeforeCreate + 1);
        TSysWorkProcessdetail testTSysWorkProcessdetail = tSysWorkProcessdetailList.get(tSysWorkProcessdetailList.size() - 1);
        assertThat(testTSysWorkProcessdetail.getFkWorkProcessId()).isEqualTo(DEFAULT_FK_WORK_PROCESS_ID);
        assertThat(testTSysWorkProcessdetail.getTodoOrg()).isEqualTo(DEFAULT_TODO_ORG);
        assertThat(testTSysWorkProcessdetail.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testTSysWorkProcessdetail.getApprovalComments()).isEqualTo(DEFAULT_APPROVAL_COMMENTS);
        assertThat(testTSysWorkProcessdetail.getReceiveTime()).isEqualTo(DEFAULT_RECEIVE_TIME);
        assertThat(testTSysWorkProcessdetail.getApprovalTime()).isEqualTo(DEFAULT_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void createTSysWorkProcessdetailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tSysWorkProcessdetailRepository.findAll().size();

        // Create the TSysWorkProcessdetail with an existing ID
        tSysWorkProcessdetail.setId(1L);
        TSysWorkProcessdetailDTO tSysWorkProcessdetailDTO = tSysWorkProcessdetailMapper.toDto(tSysWorkProcessdetail);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTSysWorkProcessdetailMockMvc.perform(post("/api/t-sys-work-processdetails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tSysWorkProcessdetailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TSysWorkProcessdetail in the database
        List<TSysWorkProcessdetail> tSysWorkProcessdetailList = tSysWorkProcessdetailRepository.findAll();
        assertThat(tSysWorkProcessdetailList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTSysWorkProcessdetails() throws Exception {
        // Initialize the database
        tSysWorkProcessdetailRepository.saveAndFlush(tSysWorkProcessdetail);

        // Get all the tSysWorkProcessdetailList
        restTSysWorkProcessdetailMockMvc.perform(get("/api/t-sys-work-processdetails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tSysWorkProcessdetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].fkWorkProcessId").value(hasItem(DEFAULT_FK_WORK_PROCESS_ID)))
            .andExpect(jsonPath("$.[*].todoOrg").value(hasItem(DEFAULT_TODO_ORG)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].approvalComments").value(hasItem(DEFAULT_APPROVAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].receiveTime").value(hasItem(DEFAULT_RECEIVE_TIME.toString())))
            .andExpect(jsonPath("$.[*].approvalTime").value(hasItem(DEFAULT_APPROVAL_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getTSysWorkProcessdetail() throws Exception {
        // Initialize the database
        tSysWorkProcessdetailRepository.saveAndFlush(tSysWorkProcessdetail);

        // Get the tSysWorkProcessdetail
        restTSysWorkProcessdetailMockMvc.perform(get("/api/t-sys-work-processdetails/{id}", tSysWorkProcessdetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tSysWorkProcessdetail.getId().intValue()))
            .andExpect(jsonPath("$.fkWorkProcessId").value(DEFAULT_FK_WORK_PROCESS_ID))
            .andExpect(jsonPath("$.todoOrg").value(DEFAULT_TODO_ORG))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.approvalComments").value(DEFAULT_APPROVAL_COMMENTS))
            .andExpect(jsonPath("$.receiveTime").value(DEFAULT_RECEIVE_TIME.toString()))
            .andExpect(jsonPath("$.approvalTime").value(DEFAULT_APPROVAL_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingTSysWorkProcessdetail() throws Exception {
        // Get the tSysWorkProcessdetail
        restTSysWorkProcessdetailMockMvc.perform(get("/api/t-sys-work-processdetails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTSysWorkProcessdetail() throws Exception {
        // Initialize the database
        tSysWorkProcessdetailRepository.saveAndFlush(tSysWorkProcessdetail);

        int databaseSizeBeforeUpdate = tSysWorkProcessdetailRepository.findAll().size();

        // Update the tSysWorkProcessdetail
        TSysWorkProcessdetail updatedTSysWorkProcessdetail = tSysWorkProcessdetailRepository.findById(tSysWorkProcessdetail.getId()).get();
        // Disconnect from session so that the updates on updatedTSysWorkProcessdetail are not directly saved in db
        em.detach(updatedTSysWorkProcessdetail);
        updatedTSysWorkProcessdetail
            .fkWorkProcessId(UPDATED_FK_WORK_PROCESS_ID)
            .todoOrg(UPDATED_TODO_ORG)
            .result(UPDATED_RESULT)
            .approvalComments(UPDATED_APPROVAL_COMMENTS)
            .receiveTime(UPDATED_RECEIVE_TIME)
            .approvalTime(UPDATED_APPROVAL_TIME);
        TSysWorkProcessdetailDTO tSysWorkProcessdetailDTO = tSysWorkProcessdetailMapper.toDto(updatedTSysWorkProcessdetail);

        restTSysWorkProcessdetailMockMvc.perform(put("/api/t-sys-work-processdetails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tSysWorkProcessdetailDTO)))
            .andExpect(status().isOk());

        // Validate the TSysWorkProcessdetail in the database
        List<TSysWorkProcessdetail> tSysWorkProcessdetailList = tSysWorkProcessdetailRepository.findAll();
        assertThat(tSysWorkProcessdetailList).hasSize(databaseSizeBeforeUpdate);
        TSysWorkProcessdetail testTSysWorkProcessdetail = tSysWorkProcessdetailList.get(tSysWorkProcessdetailList.size() - 1);
        assertThat(testTSysWorkProcessdetail.getFkWorkProcessId()).isEqualTo(UPDATED_FK_WORK_PROCESS_ID);
        assertThat(testTSysWorkProcessdetail.getTodoOrg()).isEqualTo(UPDATED_TODO_ORG);
        assertThat(testTSysWorkProcessdetail.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testTSysWorkProcessdetail.getApprovalComments()).isEqualTo(UPDATED_APPROVAL_COMMENTS);
        assertThat(testTSysWorkProcessdetail.getReceiveTime()).isEqualTo(UPDATED_RECEIVE_TIME);
        assertThat(testTSysWorkProcessdetail.getApprovalTime()).isEqualTo(UPDATED_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingTSysWorkProcessdetail() throws Exception {
        int databaseSizeBeforeUpdate = tSysWorkProcessdetailRepository.findAll().size();

        // Create the TSysWorkProcessdetail
        TSysWorkProcessdetailDTO tSysWorkProcessdetailDTO = tSysWorkProcessdetailMapper.toDto(tSysWorkProcessdetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTSysWorkProcessdetailMockMvc.perform(put("/api/t-sys-work-processdetails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tSysWorkProcessdetailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TSysWorkProcessdetail in the database
        List<TSysWorkProcessdetail> tSysWorkProcessdetailList = tSysWorkProcessdetailRepository.findAll();
        assertThat(tSysWorkProcessdetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTSysWorkProcessdetail() throws Exception {
        // Initialize the database
        tSysWorkProcessdetailRepository.saveAndFlush(tSysWorkProcessdetail);

        int databaseSizeBeforeDelete = tSysWorkProcessdetailRepository.findAll().size();

        // Delete the tSysWorkProcessdetail
        restTSysWorkProcessdetailMockMvc.perform(delete("/api/t-sys-work-processdetails/{id}", tSysWorkProcessdetail.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TSysWorkProcessdetail> tSysWorkProcessdetailList = tSysWorkProcessdetailRepository.findAll();
        assertThat(tSysWorkProcessdetailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
