package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.ZhuodewenApp;
import cn.dgd.zdw.domain.WorkProcessdetail;
import cn.dgd.zdw.repository.WorkProcessdetailRepository;
import cn.dgd.zdw.service.WorkProcessdetailService;
import cn.dgd.zdw.service.dto.WorkProcessdetailDTO;
import cn.dgd.zdw.service.mapper.WorkProcessdetailMapper;
import cn.dgd.zdw.service.dto.WorkProcessdetailCriteria;
import cn.dgd.zdw.service.WorkProcessdetailQueryService;

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
 * Integration tests for the {@link WorkProcessdetailResource} REST controller.
 */
@SpringBootTest(classes = ZhuodewenApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkProcessdetailResourceIT {

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
    private WorkProcessdetailRepository workProcessdetailRepository;

    @Autowired
    private WorkProcessdetailMapper workProcessdetailMapper;

    @Autowired
    private WorkProcessdetailService workProcessdetailService;

    @Autowired
    private WorkProcessdetailQueryService workProcessdetailQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkProcessdetailMockMvc;

    private WorkProcessdetail workProcessdetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkProcessdetail createEntity(EntityManager em) {
        WorkProcessdetail workProcessdetail = new WorkProcessdetail()
            .fkWorkProcessId(DEFAULT_FK_WORK_PROCESS_ID)
            .todoOrg(DEFAULT_TODO_ORG)
            .result(DEFAULT_RESULT)
            .approvalComments(DEFAULT_APPROVAL_COMMENTS)
            .receiveTime(DEFAULT_RECEIVE_TIME)
            .approvalTime(DEFAULT_APPROVAL_TIME);
        return workProcessdetail;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkProcessdetail createUpdatedEntity(EntityManager em) {
        WorkProcessdetail workProcessdetail = new WorkProcessdetail()
            .fkWorkProcessId(UPDATED_FK_WORK_PROCESS_ID)
            .todoOrg(UPDATED_TODO_ORG)
            .result(UPDATED_RESULT)
            .approvalComments(UPDATED_APPROVAL_COMMENTS)
            .receiveTime(UPDATED_RECEIVE_TIME)
            .approvalTime(UPDATED_APPROVAL_TIME);
        return workProcessdetail;
    }

    @BeforeEach
    public void initTest() {
        workProcessdetail = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkProcessdetail() throws Exception {
        int databaseSizeBeforeCreate = workProcessdetailRepository.findAll().size();
        // Create the WorkProcessdetail
        WorkProcessdetailDTO workProcessdetailDTO = workProcessdetailMapper.toDto(workProcessdetail);
        restWorkProcessdetailMockMvc.perform(post("/api/work-processdetails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workProcessdetailDTO)))
            .andExpect(status().isCreated());

        // Validate the WorkProcessdetail in the database
        List<WorkProcessdetail> workProcessdetailList = workProcessdetailRepository.findAll();
        assertThat(workProcessdetailList).hasSize(databaseSizeBeforeCreate + 1);
        WorkProcessdetail testWorkProcessdetail = workProcessdetailList.get(workProcessdetailList.size() - 1);
        assertThat(testWorkProcessdetail.getFkWorkProcessId()).isEqualTo(DEFAULT_FK_WORK_PROCESS_ID);
        assertThat(testWorkProcessdetail.getTodoOrg()).isEqualTo(DEFAULT_TODO_ORG);
        assertThat(testWorkProcessdetail.getResult()).isEqualTo(DEFAULT_RESULT);
        assertThat(testWorkProcessdetail.getApprovalComments()).isEqualTo(DEFAULT_APPROVAL_COMMENTS);
        assertThat(testWorkProcessdetail.getReceiveTime()).isEqualTo(DEFAULT_RECEIVE_TIME);
        assertThat(testWorkProcessdetail.getApprovalTime()).isEqualTo(DEFAULT_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void createWorkProcessdetailWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workProcessdetailRepository.findAll().size();

        // Create the WorkProcessdetail with an existing ID
        workProcessdetail.setId(1L);
        WorkProcessdetailDTO workProcessdetailDTO = workProcessdetailMapper.toDto(workProcessdetail);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkProcessdetailMockMvc.perform(post("/api/work-processdetails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workProcessdetailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkProcessdetail in the database
        List<WorkProcessdetail> workProcessdetailList = workProcessdetailRepository.findAll();
        assertThat(workProcessdetailList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkProcessdetails() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList
        restWorkProcessdetailMockMvc.perform(get("/api/work-processdetails?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workProcessdetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].fkWorkProcessId").value(hasItem(DEFAULT_FK_WORK_PROCESS_ID)))
            .andExpect(jsonPath("$.[*].todoOrg").value(hasItem(DEFAULT_TODO_ORG)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].approvalComments").value(hasItem(DEFAULT_APPROVAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].receiveTime").value(hasItem(DEFAULT_RECEIVE_TIME.toString())))
            .andExpect(jsonPath("$.[*].approvalTime").value(hasItem(DEFAULT_APPROVAL_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getWorkProcessdetail() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get the workProcessdetail
        restWorkProcessdetailMockMvc.perform(get("/api/work-processdetails/{id}", workProcessdetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workProcessdetail.getId().intValue()))
            .andExpect(jsonPath("$.fkWorkProcessId").value(DEFAULT_FK_WORK_PROCESS_ID))
            .andExpect(jsonPath("$.todoOrg").value(DEFAULT_TODO_ORG))
            .andExpect(jsonPath("$.result").value(DEFAULT_RESULT))
            .andExpect(jsonPath("$.approvalComments").value(DEFAULT_APPROVAL_COMMENTS))
            .andExpect(jsonPath("$.receiveTime").value(DEFAULT_RECEIVE_TIME.toString()))
            .andExpect(jsonPath("$.approvalTime").value(DEFAULT_APPROVAL_TIME.toString()));
    }


    @Test
    @Transactional
    public void getWorkProcessdetailsByIdFiltering() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        Long id = workProcessdetail.getId();

        defaultWorkProcessdetailShouldBeFound("id.equals=" + id);
        defaultWorkProcessdetailShouldNotBeFound("id.notEquals=" + id);

        defaultWorkProcessdetailShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkProcessdetailShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkProcessdetailShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkProcessdetailShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWorkProcessdetailsByFkWorkProcessIdIsEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where fkWorkProcessId equals to DEFAULT_FK_WORK_PROCESS_ID
        defaultWorkProcessdetailShouldBeFound("fkWorkProcessId.equals=" + DEFAULT_FK_WORK_PROCESS_ID);

        // Get all the workProcessdetailList where fkWorkProcessId equals to UPDATED_FK_WORK_PROCESS_ID
        defaultWorkProcessdetailShouldNotBeFound("fkWorkProcessId.equals=" + UPDATED_FK_WORK_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByFkWorkProcessIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where fkWorkProcessId not equals to DEFAULT_FK_WORK_PROCESS_ID
        defaultWorkProcessdetailShouldNotBeFound("fkWorkProcessId.notEquals=" + DEFAULT_FK_WORK_PROCESS_ID);

        // Get all the workProcessdetailList where fkWorkProcessId not equals to UPDATED_FK_WORK_PROCESS_ID
        defaultWorkProcessdetailShouldBeFound("fkWorkProcessId.notEquals=" + UPDATED_FK_WORK_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByFkWorkProcessIdIsInShouldWork() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where fkWorkProcessId in DEFAULT_FK_WORK_PROCESS_ID or UPDATED_FK_WORK_PROCESS_ID
        defaultWorkProcessdetailShouldBeFound("fkWorkProcessId.in=" + DEFAULT_FK_WORK_PROCESS_ID + "," + UPDATED_FK_WORK_PROCESS_ID);

        // Get all the workProcessdetailList where fkWorkProcessId equals to UPDATED_FK_WORK_PROCESS_ID
        defaultWorkProcessdetailShouldNotBeFound("fkWorkProcessId.in=" + UPDATED_FK_WORK_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByFkWorkProcessIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where fkWorkProcessId is not null
        defaultWorkProcessdetailShouldBeFound("fkWorkProcessId.specified=true");

        // Get all the workProcessdetailList where fkWorkProcessId is null
        defaultWorkProcessdetailShouldNotBeFound("fkWorkProcessId.specified=false");
    }
                @Test
    @Transactional
    public void getAllWorkProcessdetailsByFkWorkProcessIdContainsSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where fkWorkProcessId contains DEFAULT_FK_WORK_PROCESS_ID
        defaultWorkProcessdetailShouldBeFound("fkWorkProcessId.contains=" + DEFAULT_FK_WORK_PROCESS_ID);

        // Get all the workProcessdetailList where fkWorkProcessId contains UPDATED_FK_WORK_PROCESS_ID
        defaultWorkProcessdetailShouldNotBeFound("fkWorkProcessId.contains=" + UPDATED_FK_WORK_PROCESS_ID);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByFkWorkProcessIdNotContainsSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where fkWorkProcessId does not contain DEFAULT_FK_WORK_PROCESS_ID
        defaultWorkProcessdetailShouldNotBeFound("fkWorkProcessId.doesNotContain=" + DEFAULT_FK_WORK_PROCESS_ID);

        // Get all the workProcessdetailList where fkWorkProcessId does not contain UPDATED_FK_WORK_PROCESS_ID
        defaultWorkProcessdetailShouldBeFound("fkWorkProcessId.doesNotContain=" + UPDATED_FK_WORK_PROCESS_ID);
    }


    @Test
    @Transactional
    public void getAllWorkProcessdetailsByTodoOrgIsEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where todoOrg equals to DEFAULT_TODO_ORG
        defaultWorkProcessdetailShouldBeFound("todoOrg.equals=" + DEFAULT_TODO_ORG);

        // Get all the workProcessdetailList where todoOrg equals to UPDATED_TODO_ORG
        defaultWorkProcessdetailShouldNotBeFound("todoOrg.equals=" + UPDATED_TODO_ORG);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByTodoOrgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where todoOrg not equals to DEFAULT_TODO_ORG
        defaultWorkProcessdetailShouldNotBeFound("todoOrg.notEquals=" + DEFAULT_TODO_ORG);

        // Get all the workProcessdetailList where todoOrg not equals to UPDATED_TODO_ORG
        defaultWorkProcessdetailShouldBeFound("todoOrg.notEquals=" + UPDATED_TODO_ORG);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByTodoOrgIsInShouldWork() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where todoOrg in DEFAULT_TODO_ORG or UPDATED_TODO_ORG
        defaultWorkProcessdetailShouldBeFound("todoOrg.in=" + DEFAULT_TODO_ORG + "," + UPDATED_TODO_ORG);

        // Get all the workProcessdetailList where todoOrg equals to UPDATED_TODO_ORG
        defaultWorkProcessdetailShouldNotBeFound("todoOrg.in=" + UPDATED_TODO_ORG);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByTodoOrgIsNullOrNotNull() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where todoOrg is not null
        defaultWorkProcessdetailShouldBeFound("todoOrg.specified=true");

        // Get all the workProcessdetailList where todoOrg is null
        defaultWorkProcessdetailShouldNotBeFound("todoOrg.specified=false");
    }
                @Test
    @Transactional
    public void getAllWorkProcessdetailsByTodoOrgContainsSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where todoOrg contains DEFAULT_TODO_ORG
        defaultWorkProcessdetailShouldBeFound("todoOrg.contains=" + DEFAULT_TODO_ORG);

        // Get all the workProcessdetailList where todoOrg contains UPDATED_TODO_ORG
        defaultWorkProcessdetailShouldNotBeFound("todoOrg.contains=" + UPDATED_TODO_ORG);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByTodoOrgNotContainsSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where todoOrg does not contain DEFAULT_TODO_ORG
        defaultWorkProcessdetailShouldNotBeFound("todoOrg.doesNotContain=" + DEFAULT_TODO_ORG);

        // Get all the workProcessdetailList where todoOrg does not contain UPDATED_TODO_ORG
        defaultWorkProcessdetailShouldBeFound("todoOrg.doesNotContain=" + UPDATED_TODO_ORG);
    }


    @Test
    @Transactional
    public void getAllWorkProcessdetailsByResultIsEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where result equals to DEFAULT_RESULT
        defaultWorkProcessdetailShouldBeFound("result.equals=" + DEFAULT_RESULT);

        // Get all the workProcessdetailList where result equals to UPDATED_RESULT
        defaultWorkProcessdetailShouldNotBeFound("result.equals=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByResultIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where result not equals to DEFAULT_RESULT
        defaultWorkProcessdetailShouldNotBeFound("result.notEquals=" + DEFAULT_RESULT);

        // Get all the workProcessdetailList where result not equals to UPDATED_RESULT
        defaultWorkProcessdetailShouldBeFound("result.notEquals=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByResultIsInShouldWork() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where result in DEFAULT_RESULT or UPDATED_RESULT
        defaultWorkProcessdetailShouldBeFound("result.in=" + DEFAULT_RESULT + "," + UPDATED_RESULT);

        // Get all the workProcessdetailList where result equals to UPDATED_RESULT
        defaultWorkProcessdetailShouldNotBeFound("result.in=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByResultIsNullOrNotNull() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where result is not null
        defaultWorkProcessdetailShouldBeFound("result.specified=true");

        // Get all the workProcessdetailList where result is null
        defaultWorkProcessdetailShouldNotBeFound("result.specified=false");
    }
                @Test
    @Transactional
    public void getAllWorkProcessdetailsByResultContainsSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where result contains DEFAULT_RESULT
        defaultWorkProcessdetailShouldBeFound("result.contains=" + DEFAULT_RESULT);

        // Get all the workProcessdetailList where result contains UPDATED_RESULT
        defaultWorkProcessdetailShouldNotBeFound("result.contains=" + UPDATED_RESULT);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByResultNotContainsSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where result does not contain DEFAULT_RESULT
        defaultWorkProcessdetailShouldNotBeFound("result.doesNotContain=" + DEFAULT_RESULT);

        // Get all the workProcessdetailList where result does not contain UPDATED_RESULT
        defaultWorkProcessdetailShouldBeFound("result.doesNotContain=" + UPDATED_RESULT);
    }


    @Test
    @Transactional
    public void getAllWorkProcessdetailsByApprovalCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where approvalComments equals to DEFAULT_APPROVAL_COMMENTS
        defaultWorkProcessdetailShouldBeFound("approvalComments.equals=" + DEFAULT_APPROVAL_COMMENTS);

        // Get all the workProcessdetailList where approvalComments equals to UPDATED_APPROVAL_COMMENTS
        defaultWorkProcessdetailShouldNotBeFound("approvalComments.equals=" + UPDATED_APPROVAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByApprovalCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where approvalComments not equals to DEFAULT_APPROVAL_COMMENTS
        defaultWorkProcessdetailShouldNotBeFound("approvalComments.notEquals=" + DEFAULT_APPROVAL_COMMENTS);

        // Get all the workProcessdetailList where approvalComments not equals to UPDATED_APPROVAL_COMMENTS
        defaultWorkProcessdetailShouldBeFound("approvalComments.notEquals=" + UPDATED_APPROVAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByApprovalCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where approvalComments in DEFAULT_APPROVAL_COMMENTS or UPDATED_APPROVAL_COMMENTS
        defaultWorkProcessdetailShouldBeFound("approvalComments.in=" + DEFAULT_APPROVAL_COMMENTS + "," + UPDATED_APPROVAL_COMMENTS);

        // Get all the workProcessdetailList where approvalComments equals to UPDATED_APPROVAL_COMMENTS
        defaultWorkProcessdetailShouldNotBeFound("approvalComments.in=" + UPDATED_APPROVAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByApprovalCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where approvalComments is not null
        defaultWorkProcessdetailShouldBeFound("approvalComments.specified=true");

        // Get all the workProcessdetailList where approvalComments is null
        defaultWorkProcessdetailShouldNotBeFound("approvalComments.specified=false");
    }
                @Test
    @Transactional
    public void getAllWorkProcessdetailsByApprovalCommentsContainsSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where approvalComments contains DEFAULT_APPROVAL_COMMENTS
        defaultWorkProcessdetailShouldBeFound("approvalComments.contains=" + DEFAULT_APPROVAL_COMMENTS);

        // Get all the workProcessdetailList where approvalComments contains UPDATED_APPROVAL_COMMENTS
        defaultWorkProcessdetailShouldNotBeFound("approvalComments.contains=" + UPDATED_APPROVAL_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByApprovalCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where approvalComments does not contain DEFAULT_APPROVAL_COMMENTS
        defaultWorkProcessdetailShouldNotBeFound("approvalComments.doesNotContain=" + DEFAULT_APPROVAL_COMMENTS);

        // Get all the workProcessdetailList where approvalComments does not contain UPDATED_APPROVAL_COMMENTS
        defaultWorkProcessdetailShouldBeFound("approvalComments.doesNotContain=" + UPDATED_APPROVAL_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllWorkProcessdetailsByReceiveTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where receiveTime equals to DEFAULT_RECEIVE_TIME
        defaultWorkProcessdetailShouldBeFound("receiveTime.equals=" + DEFAULT_RECEIVE_TIME);

        // Get all the workProcessdetailList where receiveTime equals to UPDATED_RECEIVE_TIME
        defaultWorkProcessdetailShouldNotBeFound("receiveTime.equals=" + UPDATED_RECEIVE_TIME);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByReceiveTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where receiveTime not equals to DEFAULT_RECEIVE_TIME
        defaultWorkProcessdetailShouldNotBeFound("receiveTime.notEquals=" + DEFAULT_RECEIVE_TIME);

        // Get all the workProcessdetailList where receiveTime not equals to UPDATED_RECEIVE_TIME
        defaultWorkProcessdetailShouldBeFound("receiveTime.notEquals=" + UPDATED_RECEIVE_TIME);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByReceiveTimeIsInShouldWork() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where receiveTime in DEFAULT_RECEIVE_TIME or UPDATED_RECEIVE_TIME
        defaultWorkProcessdetailShouldBeFound("receiveTime.in=" + DEFAULT_RECEIVE_TIME + "," + UPDATED_RECEIVE_TIME);

        // Get all the workProcessdetailList where receiveTime equals to UPDATED_RECEIVE_TIME
        defaultWorkProcessdetailShouldNotBeFound("receiveTime.in=" + UPDATED_RECEIVE_TIME);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByReceiveTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where receiveTime is not null
        defaultWorkProcessdetailShouldBeFound("receiveTime.specified=true");

        // Get all the workProcessdetailList where receiveTime is null
        defaultWorkProcessdetailShouldNotBeFound("receiveTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByReceiveTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where receiveTime is greater than or equal to DEFAULT_RECEIVE_TIME
        defaultWorkProcessdetailShouldBeFound("receiveTime.greaterThanOrEqual=" + DEFAULT_RECEIVE_TIME);

        // Get all the workProcessdetailList where receiveTime is greater than or equal to UPDATED_RECEIVE_TIME
        defaultWorkProcessdetailShouldNotBeFound("receiveTime.greaterThanOrEqual=" + UPDATED_RECEIVE_TIME);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByReceiveTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where receiveTime is less than or equal to DEFAULT_RECEIVE_TIME
        defaultWorkProcessdetailShouldBeFound("receiveTime.lessThanOrEqual=" + DEFAULT_RECEIVE_TIME);

        // Get all the workProcessdetailList where receiveTime is less than or equal to SMALLER_RECEIVE_TIME
        defaultWorkProcessdetailShouldNotBeFound("receiveTime.lessThanOrEqual=" + SMALLER_RECEIVE_TIME);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByReceiveTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where receiveTime is less than DEFAULT_RECEIVE_TIME
        defaultWorkProcessdetailShouldNotBeFound("receiveTime.lessThan=" + DEFAULT_RECEIVE_TIME);

        // Get all the workProcessdetailList where receiveTime is less than UPDATED_RECEIVE_TIME
        defaultWorkProcessdetailShouldBeFound("receiveTime.lessThan=" + UPDATED_RECEIVE_TIME);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByReceiveTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where receiveTime is greater than DEFAULT_RECEIVE_TIME
        defaultWorkProcessdetailShouldNotBeFound("receiveTime.greaterThan=" + DEFAULT_RECEIVE_TIME);

        // Get all the workProcessdetailList where receiveTime is greater than SMALLER_RECEIVE_TIME
        defaultWorkProcessdetailShouldBeFound("receiveTime.greaterThan=" + SMALLER_RECEIVE_TIME);
    }


    @Test
    @Transactional
    public void getAllWorkProcessdetailsByApprovalTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where approvalTime equals to DEFAULT_APPROVAL_TIME
        defaultWorkProcessdetailShouldBeFound("approvalTime.equals=" + DEFAULT_APPROVAL_TIME);

        // Get all the workProcessdetailList where approvalTime equals to UPDATED_APPROVAL_TIME
        defaultWorkProcessdetailShouldNotBeFound("approvalTime.equals=" + UPDATED_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByApprovalTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where approvalTime not equals to DEFAULT_APPROVAL_TIME
        defaultWorkProcessdetailShouldNotBeFound("approvalTime.notEquals=" + DEFAULT_APPROVAL_TIME);

        // Get all the workProcessdetailList where approvalTime not equals to UPDATED_APPROVAL_TIME
        defaultWorkProcessdetailShouldBeFound("approvalTime.notEquals=" + UPDATED_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByApprovalTimeIsInShouldWork() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where approvalTime in DEFAULT_APPROVAL_TIME or UPDATED_APPROVAL_TIME
        defaultWorkProcessdetailShouldBeFound("approvalTime.in=" + DEFAULT_APPROVAL_TIME + "," + UPDATED_APPROVAL_TIME);

        // Get all the workProcessdetailList where approvalTime equals to UPDATED_APPROVAL_TIME
        defaultWorkProcessdetailShouldNotBeFound("approvalTime.in=" + UPDATED_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByApprovalTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where approvalTime is not null
        defaultWorkProcessdetailShouldBeFound("approvalTime.specified=true");

        // Get all the workProcessdetailList where approvalTime is null
        defaultWorkProcessdetailShouldNotBeFound("approvalTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByApprovalTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where approvalTime is greater than or equal to DEFAULT_APPROVAL_TIME
        defaultWorkProcessdetailShouldBeFound("approvalTime.greaterThanOrEqual=" + DEFAULT_APPROVAL_TIME);

        // Get all the workProcessdetailList where approvalTime is greater than or equal to UPDATED_APPROVAL_TIME
        defaultWorkProcessdetailShouldNotBeFound("approvalTime.greaterThanOrEqual=" + UPDATED_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByApprovalTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where approvalTime is less than or equal to DEFAULT_APPROVAL_TIME
        defaultWorkProcessdetailShouldBeFound("approvalTime.lessThanOrEqual=" + DEFAULT_APPROVAL_TIME);

        // Get all the workProcessdetailList where approvalTime is less than or equal to SMALLER_APPROVAL_TIME
        defaultWorkProcessdetailShouldNotBeFound("approvalTime.lessThanOrEqual=" + SMALLER_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByApprovalTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where approvalTime is less than DEFAULT_APPROVAL_TIME
        defaultWorkProcessdetailShouldNotBeFound("approvalTime.lessThan=" + DEFAULT_APPROVAL_TIME);

        // Get all the workProcessdetailList where approvalTime is less than UPDATED_APPROVAL_TIME
        defaultWorkProcessdetailShouldBeFound("approvalTime.lessThan=" + UPDATED_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllWorkProcessdetailsByApprovalTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        // Get all the workProcessdetailList where approvalTime is greater than DEFAULT_APPROVAL_TIME
        defaultWorkProcessdetailShouldNotBeFound("approvalTime.greaterThan=" + DEFAULT_APPROVAL_TIME);

        // Get all the workProcessdetailList where approvalTime is greater than SMALLER_APPROVAL_TIME
        defaultWorkProcessdetailShouldBeFound("approvalTime.greaterThan=" + SMALLER_APPROVAL_TIME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkProcessdetailShouldBeFound(String filter) throws Exception {
        restWorkProcessdetailMockMvc.perform(get("/api/work-processdetails?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workProcessdetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].fkWorkProcessId").value(hasItem(DEFAULT_FK_WORK_PROCESS_ID)))
            .andExpect(jsonPath("$.[*].todoOrg").value(hasItem(DEFAULT_TODO_ORG)))
            .andExpect(jsonPath("$.[*].result").value(hasItem(DEFAULT_RESULT)))
            .andExpect(jsonPath("$.[*].approvalComments").value(hasItem(DEFAULT_APPROVAL_COMMENTS)))
            .andExpect(jsonPath("$.[*].receiveTime").value(hasItem(DEFAULT_RECEIVE_TIME.toString())))
            .andExpect(jsonPath("$.[*].approvalTime").value(hasItem(DEFAULT_APPROVAL_TIME.toString())));

        // Check, that the count call also returns 1
        restWorkProcessdetailMockMvc.perform(get("/api/work-processdetails/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkProcessdetailShouldNotBeFound(String filter) throws Exception {
        restWorkProcessdetailMockMvc.perform(get("/api/work-processdetails?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkProcessdetailMockMvc.perform(get("/api/work-processdetails/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWorkProcessdetail() throws Exception {
        // Get the workProcessdetail
        restWorkProcessdetailMockMvc.perform(get("/api/work-processdetails/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkProcessdetail() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        int databaseSizeBeforeUpdate = workProcessdetailRepository.findAll().size();

        // Update the workProcessdetail
        WorkProcessdetail updatedWorkProcessdetail = workProcessdetailRepository.findById(workProcessdetail.getId()).get();
        // Disconnect from session so that the updates on updatedWorkProcessdetail are not directly saved in db
        em.detach(updatedWorkProcessdetail);
        updatedWorkProcessdetail
            .fkWorkProcessId(UPDATED_FK_WORK_PROCESS_ID)
            .todoOrg(UPDATED_TODO_ORG)
            .result(UPDATED_RESULT)
            .approvalComments(UPDATED_APPROVAL_COMMENTS)
            .receiveTime(UPDATED_RECEIVE_TIME)
            .approvalTime(UPDATED_APPROVAL_TIME);
        WorkProcessdetailDTO workProcessdetailDTO = workProcessdetailMapper.toDto(updatedWorkProcessdetail);

        restWorkProcessdetailMockMvc.perform(put("/api/work-processdetails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workProcessdetailDTO)))
            .andExpect(status().isOk());

        // Validate the WorkProcessdetail in the database
        List<WorkProcessdetail> workProcessdetailList = workProcessdetailRepository.findAll();
        assertThat(workProcessdetailList).hasSize(databaseSizeBeforeUpdate);
        WorkProcessdetail testWorkProcessdetail = workProcessdetailList.get(workProcessdetailList.size() - 1);
        assertThat(testWorkProcessdetail.getFkWorkProcessId()).isEqualTo(UPDATED_FK_WORK_PROCESS_ID);
        assertThat(testWorkProcessdetail.getTodoOrg()).isEqualTo(UPDATED_TODO_ORG);
        assertThat(testWorkProcessdetail.getResult()).isEqualTo(UPDATED_RESULT);
        assertThat(testWorkProcessdetail.getApprovalComments()).isEqualTo(UPDATED_APPROVAL_COMMENTS);
        assertThat(testWorkProcessdetail.getReceiveTime()).isEqualTo(UPDATED_RECEIVE_TIME);
        assertThat(testWorkProcessdetail.getApprovalTime()).isEqualTo(UPDATED_APPROVAL_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkProcessdetail() throws Exception {
        int databaseSizeBeforeUpdate = workProcessdetailRepository.findAll().size();

        // Create the WorkProcessdetail
        WorkProcessdetailDTO workProcessdetailDTO = workProcessdetailMapper.toDto(workProcessdetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkProcessdetailMockMvc.perform(put("/api/work-processdetails")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workProcessdetailDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WorkProcessdetail in the database
        List<WorkProcessdetail> workProcessdetailList = workProcessdetailRepository.findAll();
        assertThat(workProcessdetailList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkProcessdetail() throws Exception {
        // Initialize the database
        workProcessdetailRepository.saveAndFlush(workProcessdetail);

        int databaseSizeBeforeDelete = workProcessdetailRepository.findAll().size();

        // Delete the workProcessdetail
        restWorkProcessdetailMockMvc.perform(delete("/api/work-processdetails/{id}", workProcessdetail.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkProcessdetail> workProcessdetailList = workProcessdetailRepository.findAll();
        assertThat(workProcessdetailList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
