package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.ZhuodewenApp;
import cn.dgd.zdw.domain.SysWorkProcessdetail;
import cn.dgd.zdw.repository.SysWorkProcessdetailRepository;
import cn.dgd.zdw.service.SysWorkProcessdetailService;
import cn.dgd.zdw.service.dto.SysWorkProcessdetailDTO;
import cn.dgd.zdw.service.mapper.SysWorkProcessdetailMapper;
import cn.dgd.zdw.service.dto.SysWorkProcessdetailCriteria;
import cn.dgd.zdw.service.SysWorkProcessdetailQueryService;

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
 * Integration tests for the {@link SysWorkProcessdetailResource} REST controller.
 */
@SpringBootTest(classes = ZhuodewenApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SysWorkProcessdetailResourceIT {

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
    private static final LocalDate SMALLER_RECEIVE_TIME = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_APPROVAL_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPROVAL_TIME = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_APPROVAL_TIME = LocalDate.ofEpochDay(-1L);

    @Autowired
    private SysWorkProcessdetailRepository sysWorkProcessdetailRepository;

    @Autowired
    private SysWorkProcessdetailMapper sysWorkProcessdetailMapper;

    @Autowired
    private SysWorkProcessdetailService sysWorkProcessdetailService;

    @Autowired
    private SysWorkProcessdetailQueryService sysWorkProcessdetailQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSysWorkProcessdetailMockMvc;

    private SysWorkProcessdetail sysWorkProcessdetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysWorkProcessdetail createEntity(EntityManager em) {
        SysWorkProcessdetail sysWorkProcessdetail = new SysWorkProcessdetail()
            .fkWorkProcessId(DEFAULT_FK_WORK_PROCESS_ID)
            .todoOrg(DEFAULT_TODO_ORG)
            .result(DEFAULT_RESULT)
            .approvalComments(DEFAULT_APPROVAL_COMMENTS)
            .receiveTime(DEFAULT_RECEIVE_TIME)
            .approvalTime(DEFAULT_APPROVAL_TIME);
        return sysWorkProcessdetail;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysWorkProcessdetail createUpdatedEntity(EntityManager em) {
        SysWorkProcessdetail sysWorkProcessdetail = new SysWorkProcessdetail()
            .fkWorkProcessId(UPDATED_FK_WORK_PROCESS_ID)
            .todoOrg(UPDATED_TODO_ORG)
            .result(UPDATED_RESULT)
            .approvalComments(UPDATED_APPROVAL_COMMENTS)
            .receiveTime(UPDATED_RECEIVE_TIME)
            .approvalTime(UPDATED_APPROVAL_TIME);
        return sysWorkProcessdetail;
    }

    @BeforeEach
    public void initTest() {
        sysWorkProcessdetail = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysWorkProcessdetail() throws Exception {
        int databaseSizeBeforeCreate = sysWorkProcessdetailRepository.findAll().size();
        // Create the SysWorkProcessdetail
        SysWorkProcessdetailDTO sysWorkProcessdetailDTO = sysWorkProcessdetailMapper.toDto(sysWorkProcessdetail);
        restSysWorkProcessdetailMockMvc.perform(post("/api/sys-work-processdetails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysWorkProcessdetailDTO)))
            .andExpect(status().isCreated());

        // Validate the SysWorkProcessdetail in the database
        List<SysWorkProcessdetail> sysWorkProcessdetailList = sysWorkProcessdetailRepository.findAll();
        assertThat(sysWorkProcessdetailList).hasSize(databaseSizeBeforeCreate + 1);
        SysWorkProcessdetail testSysWorkProcessdetail = sysWorkProcessdetailList.get(sysWorkProcessdetailList.size() - 1);
        assertThat(testSysWorkProcessdetail.getFkWorkProcessId()).isEqualTo(DEFAULT_FK_WORK_PROCESS_ID);
        assertThat(testSysWorkProcessdetail.getTodoOrg()).isEqualTo(DEFAULT_TODO_ORG);
        assertThat(testSysWorkProcessdetail.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testSysWorkProcessdetail.getApprovalComments()).isEqualTo(DEFAULT_APPROVAL_COMMENTS);
        assertThat(testSysWorkProcessdetail.getReceiveTime()).isEqualTo(DEFAULT_RECEIVE_TIME);
        assertThat(testSysWorkProcessdetail.getApprovalTime()).isEqualTo(DEFAULT_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void createSysWorkProcessdetailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysWorkProcessdetailRepository.findAll().size();

        // Create the SysWorkProcessdetail with an existing ID
        sysWorkProcessdetail.setId(1L);
        SysWorkProcessdetailDTO sysWorkProcessdetailDTO = sysWorkProcessdetailMapper.toDto(sysWorkProcessdetail);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysWorkProcessdetailMockMvc.perform(post("/api/sys-work-processdetails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysWorkProcessdetailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysWorkProcessdetail in the database
        List<SysWorkProcessdetail> sysWorkProcessdetailList = sysWorkProcessdetailRepository.findAll();
        assertThat(sysWorkProcessdetailList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysWorkProcessdetails() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList
        restSysWorkProcessdetailMockMvc.perform(get("/api/sys-work-processdetails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysWorkProcessdetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].fkWorkProcessId").value(hasItem(DEFAULT_FK_WORK_PROCESS_ID)))
            .andExpect(jsonPath("$.[*].todoOrg").value(hasItem(DEFAULT_TODO_ORG)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].approvalComments").value(hasItem(DEFAULT_APPROVAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].receiveTime").value(hasItem(DEFAULT_RECEIVE_TIME.toString())))
            .andExpect(jsonPath("$.[*].approvalTime").value(hasItem(DEFAULT_APPROVAL_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getSysWorkProcessdetail() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get the sysWorkProcessdetail
        restSysWorkProcessdetailMockMvc.perform(get("/api/sys-work-processdetails/{id}", sysWorkProcessdetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sysWorkProcessdetail.getId().intValue()))
            .andExpect(jsonPath("$.fkWorkProcessId").value(DEFAULT_FK_WORK_PROCESS_ID))
            .andExpect(jsonPath("$.todoOrg").value(DEFAULT_TODO_ORG))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.approvalComments").value(DEFAULT_APPROVAL_COMMENTS))
            .andExpect(jsonPath("$.receiveTime").value(DEFAULT_RECEIVE_TIME.toString()))
            .andExpect(jsonPath("$.approvalTime").value(DEFAULT_APPROVAL_TIME.toString()));
    }


    @Test
    @Transactional
    public void getSysWorkProcessdetailsByIdFiltering() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        Long id = sysWorkProcessdetail.getId();

        defaultSysWorkProcessdetailShouldBeFound("id.equals=" + id);
        defaultSysWorkProcessdetailShouldNotBeFound("id.notEquals=" + id);

        defaultSysWorkProcessdetailShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSysWorkProcessdetailShouldNotBeFound("id.greaterThan=" + id);

        defaultSysWorkProcessdetailShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSysWorkProcessdetailShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByFkWorkProcessIdIsEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where fkWorkProcessId equals to DEFAULT_FK_WORK_PROCESS_ID
        defaultSysWorkProcessdetailShouldBeFound("fkWorkProcessId.equals=" + DEFAULT_FK_WORK_PROCESS_ID);

        // Get all the sysWorkProcessdetailList where fkWorkProcessId equals to UPDATED_FK_WORK_PROCESS_ID
        defaultSysWorkProcessdetailShouldNotBeFound("fkWorkProcessId.equals=" + UPDATED_FK_WORK_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByFkWorkProcessIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where fkWorkProcessId not equals to DEFAULT_FK_WORK_PROCESS_ID
        defaultSysWorkProcessdetailShouldNotBeFound("fkWorkProcessId.notEquals=" + DEFAULT_FK_WORK_PROCESS_ID);

        // Get all the sysWorkProcessdetailList where fkWorkProcessId not equals to UPDATED_FK_WORK_PROCESS_ID
        defaultSysWorkProcessdetailShouldBeFound("fkWorkProcessId.notEquals=" + UPDATED_FK_WORK_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByFkWorkProcessIdIsInShouldWork() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where fkWorkProcessId in DEFAULT_FK_WORK_PROCESS_ID or UPDATED_FK_WORK_PROCESS_ID
        defaultSysWorkProcessdetailShouldBeFound("fkWorkProcessId.in=" + DEFAULT_FK_WORK_PROCESS_ID + "," + UPDATED_FK_WORK_PROCESS_ID);

        // Get all the sysWorkProcessdetailList where fkWorkProcessId equals to UPDATED_FK_WORK_PROCESS_ID
        defaultSysWorkProcessdetailShouldNotBeFound("fkWorkProcessId.in=" + UPDATED_FK_WORK_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByFkWorkProcessIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where fkWorkProcessId is not null
        defaultSysWorkProcessdetailShouldBeFound("fkWorkProcessId.specified=true");

        // Get all the sysWorkProcessdetailList where fkWorkProcessId is null
        defaultSysWorkProcessdetailShouldNotBeFound("fkWorkProcessId.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByFkWorkProcessIdContainsSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where fkWorkProcessId contains DEFAULT_FK_WORK_PROCESS_ID
        defaultSysWorkProcessdetailShouldBeFound("fkWorkProcessId.contains=" + DEFAULT_FK_WORK_PROCESS_ID);

        // Get all the sysWorkProcessdetailList where fkWorkProcessId contains UPDATED_FK_WORK_PROCESS_ID
        defaultSysWorkProcessdetailShouldNotBeFound("fkWorkProcessId.contains=" + UPDATED_FK_WORK_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByFkWorkProcessIdNotContainsSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where fkWorkProcessId does not contain DEFAULT_FK_WORK_PROCESS_ID
        defaultSysWorkProcessdetailShouldNotBeFound("fkWorkProcessId.doesNotContain=" + DEFAULT_FK_WORK_PROCESS_ID);

        // Get all the sysWorkProcessdetailList where fkWorkProcessId does not contain UPDATED_FK_WORK_PROCESS_ID
        defaultSysWorkProcessdetailShouldBeFound("fkWorkProcessId.doesNotContain=" + UPDATED_FK_WORK_PROCESS_ID);
    }


    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByTodoOrgIsEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where todoOrg equals to DEFAULT_TODO_ORG
        defaultSysWorkProcessdetailShouldBeFound("todoOrg.equals=" + DEFAULT_TODO_ORG);

        // Get all the sysWorkProcessdetailList where todoOrg equals to UPDATED_TODO_ORG
        defaultSysWorkProcessdetailShouldNotBeFound("todoOrg.equals=" + UPDATED_TODO_ORG);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByTodoOrgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where todoOrg not equals to DEFAULT_TODO_ORG
        defaultSysWorkProcessdetailShouldNotBeFound("todoOrg.notEquals=" + DEFAULT_TODO_ORG);

        // Get all the sysWorkProcessdetailList where todoOrg not equals to UPDATED_TODO_ORG
        defaultSysWorkProcessdetailShouldBeFound("todoOrg.notEquals=" + UPDATED_TODO_ORG);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByTodoOrgIsInShouldWork() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where todoOrg in DEFAULT_TODO_ORG or UPDATED_TODO_ORG
        defaultSysWorkProcessdetailShouldBeFound("todoOrg.in=" + DEFAULT_TODO_ORG + "," + UPDATED_TODO_ORG);

        // Get all the sysWorkProcessdetailList where todoOrg equals to UPDATED_TODO_ORG
        defaultSysWorkProcessdetailShouldNotBeFound("todoOrg.in=" + UPDATED_TODO_ORG);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByTodoOrgIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where todoOrg is not null
        defaultSysWorkProcessdetailShouldBeFound("todoOrg.specified=true");

        // Get all the sysWorkProcessdetailList where todoOrg is null
        defaultSysWorkProcessdetailShouldNotBeFound("todoOrg.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByTodoOrgContainsSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where todoOrg contains DEFAULT_TODO_ORG
        defaultSysWorkProcessdetailShouldBeFound("todoOrg.contains=" + DEFAULT_TODO_ORG);

        // Get all the sysWorkProcessdetailList where todoOrg contains UPDATED_TODO_ORG
        defaultSysWorkProcessdetailShouldNotBeFound("todoOrg.contains=" + UPDATED_TODO_ORG);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByTodoOrgNotContainsSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where todoOrg does not contain DEFAULT_TODO_ORG
        defaultSysWorkProcessdetailShouldNotBeFound("todoOrg.doesNotContain=" + DEFAULT_TODO_ORG);

        // Get all the sysWorkProcessdetailList where todoOrg does not contain UPDATED_TODO_ORG
        defaultSysWorkProcessdetailShouldBeFound("todoOrg.doesNotContain=" + UPDATED_TODO_ORG);
    }


    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByResultIsEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where result equals to DEFAULT_RESULT
        defaultSysWorkProcessdetailShouldBeFound("result.equals=" + DEFAULT_RESULT);

        // Get all the sysWorkProcessdetailList where result equals to UPDATED_RESULT
        defaultSysWorkProcessdetailShouldNotBeFound("result.equals=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByResultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where result not equals to DEFAULT_RESULT
        defaultSysWorkProcessdetailShouldNotBeFound("result.notEquals=" + DEFAULT_RESULT);

        // Get all the sysWorkProcessdetailList where result not equals to UPDATED_RESULT
        defaultSysWorkProcessdetailShouldBeFound("result.notEquals=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByResultIsInShouldWork() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where result in DEFAULT_RESULT or UPDATED_RESULT
        defaultSysWorkProcessdetailShouldBeFound("result.in=" + DEFAULT_RESULT + "," + UPDATED_RESULT);

        // Get all the sysWorkProcessdetailList where result equals to UPDATED_RESULT
        defaultSysWorkProcessdetailShouldNotBeFound("result.in=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where result is not null
        defaultSysWorkProcessdetailShouldBeFound("result.specified=true");

        // Get all the sysWorkProcessdetailList where result is null
        defaultSysWorkProcessdetailShouldNotBeFound("result.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByResultContainsSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where result contains DEFAULT_RESULT
        defaultSysWorkProcessdetailShouldBeFound("result.contains=" + DEFAULT_RESULT);

        // Get all the sysWorkProcessdetailList where result contains UPDATED_RESULT
        defaultSysWorkProcessdetailShouldNotBeFound("result.contains=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByResultNotContainsSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where result does not contain DEFAULT_RESULT
        defaultSysWorkProcessdetailShouldNotBeFound("result.doesNotContain=" + DEFAULT_RESULT);

        // Get all the sysWorkProcessdetailList where result does not contain UPDATED_RESULT
        defaultSysWorkProcessdetailShouldBeFound("result.doesNotContain=" + UPDATED_RESULT);
    }


    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByApprovalCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where approvalComments equals to DEFAULT_APPROVAL_COMMENTS
        defaultSysWorkProcessdetailShouldBeFound("approvalComments.equals=" + DEFAULT_APPROVAL_COMMENTS);

        // Get all the sysWorkProcessdetailList where approvalComments equals to UPDATED_APPROVAL_COMMENTS
        defaultSysWorkProcessdetailShouldNotBeFound("approvalComments.equals=" + UPDATED_APPROVAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByApprovalCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where approvalComments not equals to DEFAULT_APPROVAL_COMMENTS
        defaultSysWorkProcessdetailShouldNotBeFound("approvalComments.notEquals=" + DEFAULT_APPROVAL_COMMENTS);

        // Get all the sysWorkProcessdetailList where approvalComments not equals to UPDATED_APPROVAL_COMMENTS
        defaultSysWorkProcessdetailShouldBeFound("approvalComments.notEquals=" + UPDATED_APPROVAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByApprovalCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where approvalComments in DEFAULT_APPROVAL_COMMENTS or UPDATED_APPROVAL_COMMENTS
        defaultSysWorkProcessdetailShouldBeFound("approvalComments.in=" + DEFAULT_APPROVAL_COMMENTS + "," + UPDATED_APPROVAL_COMMENTS);

        // Get all the sysWorkProcessdetailList where approvalComments equals to UPDATED_APPROVAL_COMMENTS
        defaultSysWorkProcessdetailShouldNotBeFound("approvalComments.in=" + UPDATED_APPROVAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByApprovalCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where approvalComments is not null
        defaultSysWorkProcessdetailShouldBeFound("approvalComments.specified=true");

        // Get all the sysWorkProcessdetailList where approvalComments is null
        defaultSysWorkProcessdetailShouldNotBeFound("approvalComments.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByApprovalCommentsContainsSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where approvalComments contains DEFAULT_APPROVAL_COMMENTS
        defaultSysWorkProcessdetailShouldBeFound("approvalComments.contains=" + DEFAULT_APPROVAL_COMMENTS);

        // Get all the sysWorkProcessdetailList where approvalComments contains UPDATED_APPROVAL_COMMENTS
        defaultSysWorkProcessdetailShouldNotBeFound("approvalComments.contains=" + UPDATED_APPROVAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByApprovalCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where approvalComments does not contain DEFAULT_APPROVAL_COMMENTS
        defaultSysWorkProcessdetailShouldNotBeFound("approvalComments.doesNotContain=" + DEFAULT_APPROVAL_COMMENTS);

        // Get all the sysWorkProcessdetailList where approvalComments does not contain UPDATED_APPROVAL_COMMENTS
        defaultSysWorkProcessdetailShouldBeFound("approvalComments.doesNotContain=" + UPDATED_APPROVAL_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByReceiveTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where receiveTime equals to DEFAULT_RECEIVE_TIME
        defaultSysWorkProcessdetailShouldBeFound("receiveTime.equals=" + DEFAULT_RECEIVE_TIME);

        // Get all the sysWorkProcessdetailList where receiveTime equals to UPDATED_RECEIVE_TIME
        defaultSysWorkProcessdetailShouldNotBeFound("receiveTime.equals=" + UPDATED_RECEIVE_TIME);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByReceiveTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where receiveTime not equals to DEFAULT_RECEIVE_TIME
        defaultSysWorkProcessdetailShouldNotBeFound("receiveTime.notEquals=" + DEFAULT_RECEIVE_TIME);

        // Get all the sysWorkProcessdetailList where receiveTime not equals to UPDATED_RECEIVE_TIME
        defaultSysWorkProcessdetailShouldBeFound("receiveTime.notEquals=" + UPDATED_RECEIVE_TIME);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByReceiveTimeIsInShouldWork() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where receiveTime in DEFAULT_RECEIVE_TIME or UPDATED_RECEIVE_TIME
        defaultSysWorkProcessdetailShouldBeFound("receiveTime.in=" + DEFAULT_RECEIVE_TIME + "," + UPDATED_RECEIVE_TIME);

        // Get all the sysWorkProcessdetailList where receiveTime equals to UPDATED_RECEIVE_TIME
        defaultSysWorkProcessdetailShouldNotBeFound("receiveTime.in=" + UPDATED_RECEIVE_TIME);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByReceiveTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where receiveTime is not null
        defaultSysWorkProcessdetailShouldBeFound("receiveTime.specified=true");

        // Get all the sysWorkProcessdetailList where receiveTime is null
        defaultSysWorkProcessdetailShouldNotBeFound("receiveTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByReceiveTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where receiveTime is greater than or equal to DEFAULT_RECEIVE_TIME
        defaultSysWorkProcessdetailShouldBeFound("receiveTime.greaterThanOrEqual=" + DEFAULT_RECEIVE_TIME);

        // Get all the sysWorkProcessdetailList where receiveTime is greater than or equal to UPDATED_RECEIVE_TIME
        defaultSysWorkProcessdetailShouldNotBeFound("receiveTime.greaterThanOrEqual=" + UPDATED_RECEIVE_TIME);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByReceiveTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where receiveTime is less than or equal to DEFAULT_RECEIVE_TIME
        defaultSysWorkProcessdetailShouldBeFound("receiveTime.lessThanOrEqual=" + DEFAULT_RECEIVE_TIME);

        // Get all the sysWorkProcessdetailList where receiveTime is less than or equal to SMALLER_RECEIVE_TIME
        defaultSysWorkProcessdetailShouldNotBeFound("receiveTime.lessThanOrEqual=" + SMALLER_RECEIVE_TIME);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByReceiveTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where receiveTime is less than DEFAULT_RECEIVE_TIME
        defaultSysWorkProcessdetailShouldNotBeFound("receiveTime.lessThan=" + DEFAULT_RECEIVE_TIME);

        // Get all the sysWorkProcessdetailList where receiveTime is less than UPDATED_RECEIVE_TIME
        defaultSysWorkProcessdetailShouldBeFound("receiveTime.lessThan=" + UPDATED_RECEIVE_TIME);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByReceiveTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where receiveTime is greater than DEFAULT_RECEIVE_TIME
        defaultSysWorkProcessdetailShouldNotBeFound("receiveTime.greaterThan=" + DEFAULT_RECEIVE_TIME);

        // Get all the sysWorkProcessdetailList where receiveTime is greater than SMALLER_RECEIVE_TIME
        defaultSysWorkProcessdetailShouldBeFound("receiveTime.greaterThan=" + SMALLER_RECEIVE_TIME);
    }


    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByApprovalTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where approvalTime equals to DEFAULT_APPROVAL_TIME
        defaultSysWorkProcessdetailShouldBeFound("approvalTime.equals=" + DEFAULT_APPROVAL_TIME);

        // Get all the sysWorkProcessdetailList where approvalTime equals to UPDATED_APPROVAL_TIME
        defaultSysWorkProcessdetailShouldNotBeFound("approvalTime.equals=" + UPDATED_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByApprovalTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where approvalTime not equals to DEFAULT_APPROVAL_TIME
        defaultSysWorkProcessdetailShouldNotBeFound("approvalTime.notEquals=" + DEFAULT_APPROVAL_TIME);

        // Get all the sysWorkProcessdetailList where approvalTime not equals to UPDATED_APPROVAL_TIME
        defaultSysWorkProcessdetailShouldBeFound("approvalTime.notEquals=" + UPDATED_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByApprovalTimeIsInShouldWork() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where approvalTime in DEFAULT_APPROVAL_TIME or UPDATED_APPROVAL_TIME
        defaultSysWorkProcessdetailShouldBeFound("approvalTime.in=" + DEFAULT_APPROVAL_TIME + "," + UPDATED_APPROVAL_TIME);

        // Get all the sysWorkProcessdetailList where approvalTime equals to UPDATED_APPROVAL_TIME
        defaultSysWorkProcessdetailShouldNotBeFound("approvalTime.in=" + UPDATED_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByApprovalTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where approvalTime is not null
        defaultSysWorkProcessdetailShouldBeFound("approvalTime.specified=true");

        // Get all the sysWorkProcessdetailList where approvalTime is null
        defaultSysWorkProcessdetailShouldNotBeFound("approvalTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByApprovalTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where approvalTime is greater than or equal to DEFAULT_APPROVAL_TIME
        defaultSysWorkProcessdetailShouldBeFound("approvalTime.greaterThanOrEqual=" + DEFAULT_APPROVAL_TIME);

        // Get all the sysWorkProcessdetailList where approvalTime is greater than or equal to UPDATED_APPROVAL_TIME
        defaultSysWorkProcessdetailShouldNotBeFound("approvalTime.greaterThanOrEqual=" + UPDATED_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByApprovalTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where approvalTime is less than or equal to DEFAULT_APPROVAL_TIME
        defaultSysWorkProcessdetailShouldBeFound("approvalTime.lessThanOrEqual=" + DEFAULT_APPROVAL_TIME);

        // Get all the sysWorkProcessdetailList where approvalTime is less than or equal to SMALLER_APPROVAL_TIME
        defaultSysWorkProcessdetailShouldNotBeFound("approvalTime.lessThanOrEqual=" + SMALLER_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByApprovalTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where approvalTime is less than DEFAULT_APPROVAL_TIME
        defaultSysWorkProcessdetailShouldNotBeFound("approvalTime.lessThan=" + DEFAULT_APPROVAL_TIME);

        // Get all the sysWorkProcessdetailList where approvalTime is less than UPDATED_APPROVAL_TIME
        defaultSysWorkProcessdetailShouldBeFound("approvalTime.lessThan=" + UPDATED_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllSysWorkProcessdetailsByApprovalTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        // Get all the sysWorkProcessdetailList where approvalTime is greater than DEFAULT_APPROVAL_TIME
        defaultSysWorkProcessdetailShouldNotBeFound("approvalTime.greaterThan=" + DEFAULT_APPROVAL_TIME);

        // Get all the sysWorkProcessdetailList where approvalTime is greater than SMALLER_APPROVAL_TIME
        defaultSysWorkProcessdetailShouldBeFound("approvalTime.greaterThan=" + SMALLER_APPROVAL_TIME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSysWorkProcessdetailShouldBeFound(String filter) throws Exception {
        restSysWorkProcessdetailMockMvc.perform(get("/api/sys-work-processdetails?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysWorkProcessdetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].fkWorkProcessId").value(hasItem(DEFAULT_FK_WORK_PROCESS_ID)))
            .andExpect(jsonPath("$.[*].todoOrg").value(hasItem(DEFAULT_TODO_ORG)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].approvalComments").value(hasItem(DEFAULT_APPROVAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].receiveTime").value(hasItem(DEFAULT_RECEIVE_TIME.toString())))
            .andExpect(jsonPath("$.[*].approvalTime").value(hasItem(DEFAULT_APPROVAL_TIME.toString())));

        // Check, that the count call also returns 1
        restSysWorkProcessdetailMockMvc.perform(get("/api/sys-work-processdetails/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSysWorkProcessdetailShouldNotBeFound(String filter) throws Exception {
        restSysWorkProcessdetailMockMvc.perform(get("/api/sys-work-processdetails?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSysWorkProcessdetailMockMvc.perform(get("/api/sys-work-processdetails/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSysWorkProcessdetail() throws Exception {
        // Get the sysWorkProcessdetail
        restSysWorkProcessdetailMockMvc.perform(get("/api/sys-work-processdetails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysWorkProcessdetail() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        int databaseSizeBeforeUpdate = sysWorkProcessdetailRepository.findAll().size();

        // Update the sysWorkProcessdetail
        SysWorkProcessdetail updatedSysWorkProcessdetail = sysWorkProcessdetailRepository.findById(sysWorkProcessdetail.getId()).get();
        // Disconnect from session so that the updates on updatedSysWorkProcessdetail are not directly saved in db
        em.detach(updatedSysWorkProcessdetail);
        updatedSysWorkProcessdetail
            .fkWorkProcessId(UPDATED_FK_WORK_PROCESS_ID)
            .todoOrg(UPDATED_TODO_ORG)
            .result(UPDATED_RESULT)
            .approvalComments(UPDATED_APPROVAL_COMMENTS)
            .receiveTime(UPDATED_RECEIVE_TIME)
            .approvalTime(UPDATED_APPROVAL_TIME);
        SysWorkProcessdetailDTO sysWorkProcessdetailDTO = sysWorkProcessdetailMapper.toDto(updatedSysWorkProcessdetail);

        restSysWorkProcessdetailMockMvc.perform(put("/api/sys-work-processdetails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysWorkProcessdetailDTO)))
            .andExpect(status().isOk());

        // Validate the SysWorkProcessdetail in the database
        List<SysWorkProcessdetail> sysWorkProcessdetailList = sysWorkProcessdetailRepository.findAll();
        assertThat(sysWorkProcessdetailList).hasSize(databaseSizeBeforeUpdate);
        SysWorkProcessdetail testSysWorkProcessdetail = sysWorkProcessdetailList.get(sysWorkProcessdetailList.size() - 1);
        assertThat(testSysWorkProcessdetail.getFkWorkProcessId()).isEqualTo(UPDATED_FK_WORK_PROCESS_ID);
        assertThat(testSysWorkProcessdetail.getTodoOrg()).isEqualTo(UPDATED_TODO_ORG);
        assertThat(testSysWorkProcessdetail.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testSysWorkProcessdetail.getApprovalComments()).isEqualTo(UPDATED_APPROVAL_COMMENTS);
        assertThat(testSysWorkProcessdetail.getReceiveTime()).isEqualTo(UPDATED_RECEIVE_TIME);
        assertThat(testSysWorkProcessdetail.getApprovalTime()).isEqualTo(UPDATED_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingSysWorkProcessdetail() throws Exception {
        int databaseSizeBeforeUpdate = sysWorkProcessdetailRepository.findAll().size();

        // Create the SysWorkProcessdetail
        SysWorkProcessdetailDTO sysWorkProcessdetailDTO = sysWorkProcessdetailMapper.toDto(sysWorkProcessdetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysWorkProcessdetailMockMvc.perform(put("/api/sys-work-processdetails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysWorkProcessdetailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysWorkProcessdetail in the database
        List<SysWorkProcessdetail> sysWorkProcessdetailList = sysWorkProcessdetailRepository.findAll();
        assertThat(sysWorkProcessdetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysWorkProcessdetail() throws Exception {
        // Initialize the database
        sysWorkProcessdetailRepository.saveAndFlush(sysWorkProcessdetail);

        int databaseSizeBeforeDelete = sysWorkProcessdetailRepository.findAll().size();

        // Delete the sysWorkProcessdetail
        restSysWorkProcessdetailMockMvc.perform(delete("/api/sys-work-processdetails/{id}", sysWorkProcessdetail.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysWorkProcessdetail> sysWorkProcessdetailList = sysWorkProcessdetailRepository.findAll();
        assertThat(sysWorkProcessdetailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
