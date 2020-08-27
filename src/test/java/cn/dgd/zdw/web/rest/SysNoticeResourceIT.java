package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.ZhuodewenApp;
import cn.dgd.zdw.domain.SysNotice;
import cn.dgd.zdw.repository.SysNoticeRepository;
import cn.dgd.zdw.service.SysNoticeService;
import cn.dgd.zdw.service.dto.SysNoticeDTO;
import cn.dgd.zdw.service.mapper.SysNoticeMapper;
import cn.dgd.zdw.service.dto.SysNoticeCriteria;
import cn.dgd.zdw.service.SysNoticeQueryService;

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
 * Integration tests for the {@link SysNoticeResource} REST controller.
 */
@SpringBootTest(classes = ZhuodewenApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SysNoticeResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_SENDER = "AAAAAAAAAA";
    private static final String UPDATED_SENDER = "BBBBBBBBBB";

    private static final String DEFAULT_SEND_DATE = "AAAAAAAAAA";
    private static final String UPDATED_SEND_DATE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RECEIVER = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECEIVER = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_RECEIVER = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_AUDIT_ORG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AUDIT_ORG = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_AUDIT_ORG = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_DEL_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_DEL_FLAG = "BBBBBBBBBB";

    @Autowired
    private SysNoticeRepository sysNoticeRepository;

    @Autowired
    private SysNoticeMapper sysNoticeMapper;

    @Autowired
    private SysNoticeService sysNoticeService;

    @Autowired
    private SysNoticeQueryService sysNoticeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSysNoticeMockMvc;

    private SysNotice sysNotice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysNotice createEntity(EntityManager em) {
        SysNotice sysNotice = new SysNotice()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .sender(DEFAULT_SENDER)
            .sendDate(DEFAULT_SEND_DATE)
            .receiver(DEFAULT_RECEIVER)
            .auditOrg(DEFAULT_AUDIT_ORG)
            .delFlag(DEFAULT_DEL_FLAG);
        return sysNotice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SysNotice createUpdatedEntity(EntityManager em) {
        SysNotice sysNotice = new SysNotice()
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .sender(UPDATED_SENDER)
            .sendDate(UPDATED_SEND_DATE)
            .receiver(UPDATED_RECEIVER)
            .auditOrg(UPDATED_AUDIT_ORG)
            .delFlag(UPDATED_DEL_FLAG);
        return sysNotice;
    }

    @BeforeEach
    public void initTest() {
        sysNotice = createEntity(em);
    }

    @Test
    @Transactional
    public void createSysNotice() throws Exception {
        int databaseSizeBeforeCreate = sysNoticeRepository.findAll().size();
        // Create the SysNotice
        SysNoticeDTO sysNoticeDTO = sysNoticeMapper.toDto(sysNotice);
        restSysNoticeMockMvc.perform(post("/api/sys-notices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysNoticeDTO)))
            .andExpect(status().isCreated());

        // Validate the SysNotice in the database
        List<SysNotice> sysNoticeList = sysNoticeRepository.findAll();
        assertThat(sysNoticeList).hasSize(databaseSizeBeforeCreate + 1);
        SysNotice testSysNotice = sysNoticeList.get(sysNoticeList.size() - 1);
        assertThat(testSysNotice.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testSysNotice.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testSysNotice.getSender()).isEqualTo(DEFAULT_SENDER);
        assertThat(testSysNotice.getSendDate()).isEqualTo(DEFAULT_SEND_DATE);
        assertThat(testSysNotice.getReceiver()).isEqualTo(DEFAULT_RECEIVER);
        assertThat(testSysNotice.getAuditOrg()).isEqualTo(DEFAULT_AUDIT_ORG);
        assertThat(testSysNotice.getDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
    }

    @Test
    @Transactional
    public void createSysNoticeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sysNoticeRepository.findAll().size();

        // Create the SysNotice with an existing ID
        sysNotice.setId(1L);
        SysNoticeDTO sysNoticeDTO = sysNoticeMapper.toDto(sysNotice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSysNoticeMockMvc.perform(post("/api/sys-notices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysNoticeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysNotice in the database
        List<SysNotice> sysNoticeList = sysNoticeRepository.findAll();
        assertThat(sysNoticeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSysNotices() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList
        restSysNoticeMockMvc.perform(get("/api/sys-notices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysNotice.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER)))
            .andExpect(jsonPath("$.[*].sendDate").value(hasItem(DEFAULT_SEND_DATE)))
            .andExpect(jsonPath("$.[*].receiver").value(hasItem(DEFAULT_RECEIVER.toString())))
            .andExpect(jsonPath("$.[*].auditOrg").value(hasItem(DEFAULT_AUDIT_ORG.toString())))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG)));
    }
    
    @Test
    @Transactional
    public void getSysNotice() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get the sysNotice
        restSysNoticeMockMvc.perform(get("/api/sys-notices/{id}", sysNotice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sysNotice.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.sender").value(DEFAULT_SENDER))
            .andExpect(jsonPath("$.sendDate").value(DEFAULT_SEND_DATE))
            .andExpect(jsonPath("$.receiver").value(DEFAULT_RECEIVER.toString()))
            .andExpect(jsonPath("$.auditOrg").value(DEFAULT_AUDIT_ORG.toString()))
            .andExpect(jsonPath("$.delFlag").value(DEFAULT_DEL_FLAG));
    }


    @Test
    @Transactional
    public void getSysNoticesByIdFiltering() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        Long id = sysNotice.getId();

        defaultSysNoticeShouldBeFound("id.equals=" + id);
        defaultSysNoticeShouldNotBeFound("id.notEquals=" + id);

        defaultSysNoticeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSysNoticeShouldNotBeFound("id.greaterThan=" + id);

        defaultSysNoticeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSysNoticeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSysNoticesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where title equals to DEFAULT_TITLE
        defaultSysNoticeShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the sysNoticeList where title equals to UPDATED_TITLE
        defaultSysNoticeShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where title not equals to DEFAULT_TITLE
        defaultSysNoticeShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the sysNoticeList where title not equals to UPDATED_TITLE
        defaultSysNoticeShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultSysNoticeShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the sysNoticeList where title equals to UPDATED_TITLE
        defaultSysNoticeShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where title is not null
        defaultSysNoticeShouldBeFound("title.specified=true");

        // Get all the sysNoticeList where title is null
        defaultSysNoticeShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysNoticesByTitleContainsSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where title contains DEFAULT_TITLE
        defaultSysNoticeShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the sysNoticeList where title contains UPDATED_TITLE
        defaultSysNoticeShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where title does not contain DEFAULT_TITLE
        defaultSysNoticeShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the sysNoticeList where title does not contain UPDATED_TITLE
        defaultSysNoticeShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllSysNoticesByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where content equals to DEFAULT_CONTENT
        defaultSysNoticeShouldBeFound("content.equals=" + DEFAULT_CONTENT);

        // Get all the sysNoticeList where content equals to UPDATED_CONTENT
        defaultSysNoticeShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByContentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where content not equals to DEFAULT_CONTENT
        defaultSysNoticeShouldNotBeFound("content.notEquals=" + DEFAULT_CONTENT);

        // Get all the sysNoticeList where content not equals to UPDATED_CONTENT
        defaultSysNoticeShouldBeFound("content.notEquals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByContentIsInShouldWork() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where content in DEFAULT_CONTENT or UPDATED_CONTENT
        defaultSysNoticeShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);

        // Get all the sysNoticeList where content equals to UPDATED_CONTENT
        defaultSysNoticeShouldNotBeFound("content.in=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where content is not null
        defaultSysNoticeShouldBeFound("content.specified=true");

        // Get all the sysNoticeList where content is null
        defaultSysNoticeShouldNotBeFound("content.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysNoticesByContentContainsSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where content contains DEFAULT_CONTENT
        defaultSysNoticeShouldBeFound("content.contains=" + DEFAULT_CONTENT);

        // Get all the sysNoticeList where content contains UPDATED_CONTENT
        defaultSysNoticeShouldNotBeFound("content.contains=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByContentNotContainsSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where content does not contain DEFAULT_CONTENT
        defaultSysNoticeShouldNotBeFound("content.doesNotContain=" + DEFAULT_CONTENT);

        // Get all the sysNoticeList where content does not contain UPDATED_CONTENT
        defaultSysNoticeShouldBeFound("content.doesNotContain=" + UPDATED_CONTENT);
    }


    @Test
    @Transactional
    public void getAllSysNoticesBySenderIsEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where sender equals to DEFAULT_SENDER
        defaultSysNoticeShouldBeFound("sender.equals=" + DEFAULT_SENDER);

        // Get all the sysNoticeList where sender equals to UPDATED_SENDER
        defaultSysNoticeShouldNotBeFound("sender.equals=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    public void getAllSysNoticesBySenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where sender not equals to DEFAULT_SENDER
        defaultSysNoticeShouldNotBeFound("sender.notEquals=" + DEFAULT_SENDER);

        // Get all the sysNoticeList where sender not equals to UPDATED_SENDER
        defaultSysNoticeShouldBeFound("sender.notEquals=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    public void getAllSysNoticesBySenderIsInShouldWork() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where sender in DEFAULT_SENDER or UPDATED_SENDER
        defaultSysNoticeShouldBeFound("sender.in=" + DEFAULT_SENDER + "," + UPDATED_SENDER);

        // Get all the sysNoticeList where sender equals to UPDATED_SENDER
        defaultSysNoticeShouldNotBeFound("sender.in=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    public void getAllSysNoticesBySenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where sender is not null
        defaultSysNoticeShouldBeFound("sender.specified=true");

        // Get all the sysNoticeList where sender is null
        defaultSysNoticeShouldNotBeFound("sender.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysNoticesBySenderContainsSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where sender contains DEFAULT_SENDER
        defaultSysNoticeShouldBeFound("sender.contains=" + DEFAULT_SENDER);

        // Get all the sysNoticeList where sender contains UPDATED_SENDER
        defaultSysNoticeShouldNotBeFound("sender.contains=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    public void getAllSysNoticesBySenderNotContainsSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where sender does not contain DEFAULT_SENDER
        defaultSysNoticeShouldNotBeFound("sender.doesNotContain=" + DEFAULT_SENDER);

        // Get all the sysNoticeList where sender does not contain UPDATED_SENDER
        defaultSysNoticeShouldBeFound("sender.doesNotContain=" + UPDATED_SENDER);
    }


    @Test
    @Transactional
    public void getAllSysNoticesBySendDateIsEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where sendDate equals to DEFAULT_SEND_DATE
        defaultSysNoticeShouldBeFound("sendDate.equals=" + DEFAULT_SEND_DATE);

        // Get all the sysNoticeList where sendDate equals to UPDATED_SEND_DATE
        defaultSysNoticeShouldNotBeFound("sendDate.equals=" + UPDATED_SEND_DATE);
    }

    @Test
    @Transactional
    public void getAllSysNoticesBySendDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where sendDate not equals to DEFAULT_SEND_DATE
        defaultSysNoticeShouldNotBeFound("sendDate.notEquals=" + DEFAULT_SEND_DATE);

        // Get all the sysNoticeList where sendDate not equals to UPDATED_SEND_DATE
        defaultSysNoticeShouldBeFound("sendDate.notEquals=" + UPDATED_SEND_DATE);
    }

    @Test
    @Transactional
    public void getAllSysNoticesBySendDateIsInShouldWork() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where sendDate in DEFAULT_SEND_DATE or UPDATED_SEND_DATE
        defaultSysNoticeShouldBeFound("sendDate.in=" + DEFAULT_SEND_DATE + "," + UPDATED_SEND_DATE);

        // Get all the sysNoticeList where sendDate equals to UPDATED_SEND_DATE
        defaultSysNoticeShouldNotBeFound("sendDate.in=" + UPDATED_SEND_DATE);
    }

    @Test
    @Transactional
    public void getAllSysNoticesBySendDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where sendDate is not null
        defaultSysNoticeShouldBeFound("sendDate.specified=true");

        // Get all the sysNoticeList where sendDate is null
        defaultSysNoticeShouldNotBeFound("sendDate.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysNoticesBySendDateContainsSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where sendDate contains DEFAULT_SEND_DATE
        defaultSysNoticeShouldBeFound("sendDate.contains=" + DEFAULT_SEND_DATE);

        // Get all the sysNoticeList where sendDate contains UPDATED_SEND_DATE
        defaultSysNoticeShouldNotBeFound("sendDate.contains=" + UPDATED_SEND_DATE);
    }

    @Test
    @Transactional
    public void getAllSysNoticesBySendDateNotContainsSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where sendDate does not contain DEFAULT_SEND_DATE
        defaultSysNoticeShouldNotBeFound("sendDate.doesNotContain=" + DEFAULT_SEND_DATE);

        // Get all the sysNoticeList where sendDate does not contain UPDATED_SEND_DATE
        defaultSysNoticeShouldBeFound("sendDate.doesNotContain=" + UPDATED_SEND_DATE);
    }


    @Test
    @Transactional
    public void getAllSysNoticesByReceiverIsEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where receiver equals to DEFAULT_RECEIVER
        defaultSysNoticeShouldBeFound("receiver.equals=" + DEFAULT_RECEIVER);

        // Get all the sysNoticeList where receiver equals to UPDATED_RECEIVER
        defaultSysNoticeShouldNotBeFound("receiver.equals=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByReceiverIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where receiver not equals to DEFAULT_RECEIVER
        defaultSysNoticeShouldNotBeFound("receiver.notEquals=" + DEFAULT_RECEIVER);

        // Get all the sysNoticeList where receiver not equals to UPDATED_RECEIVER
        defaultSysNoticeShouldBeFound("receiver.notEquals=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByReceiverIsInShouldWork() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where receiver in DEFAULT_RECEIVER or UPDATED_RECEIVER
        defaultSysNoticeShouldBeFound("receiver.in=" + DEFAULT_RECEIVER + "," + UPDATED_RECEIVER);

        // Get all the sysNoticeList where receiver equals to UPDATED_RECEIVER
        defaultSysNoticeShouldNotBeFound("receiver.in=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByReceiverIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where receiver is not null
        defaultSysNoticeShouldBeFound("receiver.specified=true");

        // Get all the sysNoticeList where receiver is null
        defaultSysNoticeShouldNotBeFound("receiver.specified=false");
    }

    @Test
    @Transactional
    public void getAllSysNoticesByReceiverIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where receiver is greater than or equal to DEFAULT_RECEIVER
        defaultSysNoticeShouldBeFound("receiver.greaterThanOrEqual=" + DEFAULT_RECEIVER);

        // Get all the sysNoticeList where receiver is greater than or equal to UPDATED_RECEIVER
        defaultSysNoticeShouldNotBeFound("receiver.greaterThanOrEqual=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByReceiverIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where receiver is less than or equal to DEFAULT_RECEIVER
        defaultSysNoticeShouldBeFound("receiver.lessThanOrEqual=" + DEFAULT_RECEIVER);

        // Get all the sysNoticeList where receiver is less than or equal to SMALLER_RECEIVER
        defaultSysNoticeShouldNotBeFound("receiver.lessThanOrEqual=" + SMALLER_RECEIVER);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByReceiverIsLessThanSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where receiver is less than DEFAULT_RECEIVER
        defaultSysNoticeShouldNotBeFound("receiver.lessThan=" + DEFAULT_RECEIVER);

        // Get all the sysNoticeList where receiver is less than UPDATED_RECEIVER
        defaultSysNoticeShouldBeFound("receiver.lessThan=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByReceiverIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where receiver is greater than DEFAULT_RECEIVER
        defaultSysNoticeShouldNotBeFound("receiver.greaterThan=" + DEFAULT_RECEIVER);

        // Get all the sysNoticeList where receiver is greater than SMALLER_RECEIVER
        defaultSysNoticeShouldBeFound("receiver.greaterThan=" + SMALLER_RECEIVER);
    }


    @Test
    @Transactional
    public void getAllSysNoticesByAuditOrgIsEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where auditOrg equals to DEFAULT_AUDIT_ORG
        defaultSysNoticeShouldBeFound("auditOrg.equals=" + DEFAULT_AUDIT_ORG);

        // Get all the sysNoticeList where auditOrg equals to UPDATED_AUDIT_ORG
        defaultSysNoticeShouldNotBeFound("auditOrg.equals=" + UPDATED_AUDIT_ORG);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByAuditOrgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where auditOrg not equals to DEFAULT_AUDIT_ORG
        defaultSysNoticeShouldNotBeFound("auditOrg.notEquals=" + DEFAULT_AUDIT_ORG);

        // Get all the sysNoticeList where auditOrg not equals to UPDATED_AUDIT_ORG
        defaultSysNoticeShouldBeFound("auditOrg.notEquals=" + UPDATED_AUDIT_ORG);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByAuditOrgIsInShouldWork() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where auditOrg in DEFAULT_AUDIT_ORG or UPDATED_AUDIT_ORG
        defaultSysNoticeShouldBeFound("auditOrg.in=" + DEFAULT_AUDIT_ORG + "," + UPDATED_AUDIT_ORG);

        // Get all the sysNoticeList where auditOrg equals to UPDATED_AUDIT_ORG
        defaultSysNoticeShouldNotBeFound("auditOrg.in=" + UPDATED_AUDIT_ORG);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByAuditOrgIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where auditOrg is not null
        defaultSysNoticeShouldBeFound("auditOrg.specified=true");

        // Get all the sysNoticeList where auditOrg is null
        defaultSysNoticeShouldNotBeFound("auditOrg.specified=false");
    }

    @Test
    @Transactional
    public void getAllSysNoticesByAuditOrgIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where auditOrg is greater than or equal to DEFAULT_AUDIT_ORG
        defaultSysNoticeShouldBeFound("auditOrg.greaterThanOrEqual=" + DEFAULT_AUDIT_ORG);

        // Get all the sysNoticeList where auditOrg is greater than or equal to UPDATED_AUDIT_ORG
        defaultSysNoticeShouldNotBeFound("auditOrg.greaterThanOrEqual=" + UPDATED_AUDIT_ORG);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByAuditOrgIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where auditOrg is less than or equal to DEFAULT_AUDIT_ORG
        defaultSysNoticeShouldBeFound("auditOrg.lessThanOrEqual=" + DEFAULT_AUDIT_ORG);

        // Get all the sysNoticeList where auditOrg is less than or equal to SMALLER_AUDIT_ORG
        defaultSysNoticeShouldNotBeFound("auditOrg.lessThanOrEqual=" + SMALLER_AUDIT_ORG);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByAuditOrgIsLessThanSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where auditOrg is less than DEFAULT_AUDIT_ORG
        defaultSysNoticeShouldNotBeFound("auditOrg.lessThan=" + DEFAULT_AUDIT_ORG);

        // Get all the sysNoticeList where auditOrg is less than UPDATED_AUDIT_ORG
        defaultSysNoticeShouldBeFound("auditOrg.lessThan=" + UPDATED_AUDIT_ORG);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByAuditOrgIsGreaterThanSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where auditOrg is greater than DEFAULT_AUDIT_ORG
        defaultSysNoticeShouldNotBeFound("auditOrg.greaterThan=" + DEFAULT_AUDIT_ORG);

        // Get all the sysNoticeList where auditOrg is greater than SMALLER_AUDIT_ORG
        defaultSysNoticeShouldBeFound("auditOrg.greaterThan=" + SMALLER_AUDIT_ORG);
    }


    @Test
    @Transactional
    public void getAllSysNoticesByDelFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where delFlag equals to DEFAULT_DEL_FLAG
        defaultSysNoticeShouldBeFound("delFlag.equals=" + DEFAULT_DEL_FLAG);

        // Get all the sysNoticeList where delFlag equals to UPDATED_DEL_FLAG
        defaultSysNoticeShouldNotBeFound("delFlag.equals=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByDelFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where delFlag not equals to DEFAULT_DEL_FLAG
        defaultSysNoticeShouldNotBeFound("delFlag.notEquals=" + DEFAULT_DEL_FLAG);

        // Get all the sysNoticeList where delFlag not equals to UPDATED_DEL_FLAG
        defaultSysNoticeShouldBeFound("delFlag.notEquals=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByDelFlagIsInShouldWork() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where delFlag in DEFAULT_DEL_FLAG or UPDATED_DEL_FLAG
        defaultSysNoticeShouldBeFound("delFlag.in=" + DEFAULT_DEL_FLAG + "," + UPDATED_DEL_FLAG);

        // Get all the sysNoticeList where delFlag equals to UPDATED_DEL_FLAG
        defaultSysNoticeShouldNotBeFound("delFlag.in=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByDelFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where delFlag is not null
        defaultSysNoticeShouldBeFound("delFlag.specified=true");

        // Get all the sysNoticeList where delFlag is null
        defaultSysNoticeShouldNotBeFound("delFlag.specified=false");
    }
                @Test
    @Transactional
    public void getAllSysNoticesByDelFlagContainsSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where delFlag contains DEFAULT_DEL_FLAG
        defaultSysNoticeShouldBeFound("delFlag.contains=" + DEFAULT_DEL_FLAG);

        // Get all the sysNoticeList where delFlag contains UPDATED_DEL_FLAG
        defaultSysNoticeShouldNotBeFound("delFlag.contains=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    public void getAllSysNoticesByDelFlagNotContainsSomething() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        // Get all the sysNoticeList where delFlag does not contain DEFAULT_DEL_FLAG
        defaultSysNoticeShouldNotBeFound("delFlag.doesNotContain=" + DEFAULT_DEL_FLAG);

        // Get all the sysNoticeList where delFlag does not contain UPDATED_DEL_FLAG
        defaultSysNoticeShouldBeFound("delFlag.doesNotContain=" + UPDATED_DEL_FLAG);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSysNoticeShouldBeFound(String filter) throws Exception {
        restSysNoticeMockMvc.perform(get("/api/sys-notices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sysNotice.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER)))
            .andExpect(jsonPath("$.[*].sendDate").value(hasItem(DEFAULT_SEND_DATE)))
            .andExpect(jsonPath("$.[*].receiver").value(hasItem(DEFAULT_RECEIVER.toString())))
            .andExpect(jsonPath("$.[*].auditOrg").value(hasItem(DEFAULT_AUDIT_ORG.toString())))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG)));

        // Check, that the count call also returns 1
        restSysNoticeMockMvc.perform(get("/api/sys-notices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSysNoticeShouldNotBeFound(String filter) throws Exception {
        restSysNoticeMockMvc.perform(get("/api/sys-notices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSysNoticeMockMvc.perform(get("/api/sys-notices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSysNotice() throws Exception {
        // Get the sysNotice
        restSysNoticeMockMvc.perform(get("/api/sys-notices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSysNotice() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        int databaseSizeBeforeUpdate = sysNoticeRepository.findAll().size();

        // Update the sysNotice
        SysNotice updatedSysNotice = sysNoticeRepository.findById(sysNotice.getId()).get();
        // Disconnect from session so that the updates on updatedSysNotice are not directly saved in db
        em.detach(updatedSysNotice);
        updatedSysNotice
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .sender(UPDATED_SENDER)
            .sendDate(UPDATED_SEND_DATE)
            .receiver(UPDATED_RECEIVER)
            .auditOrg(UPDATED_AUDIT_ORG)
            .delFlag(UPDATED_DEL_FLAG);
        SysNoticeDTO sysNoticeDTO = sysNoticeMapper.toDto(updatedSysNotice);

        restSysNoticeMockMvc.perform(put("/api/sys-notices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysNoticeDTO)))
            .andExpect(status().isOk());

        // Validate the SysNotice in the database
        List<SysNotice> sysNoticeList = sysNoticeRepository.findAll();
        assertThat(sysNoticeList).hasSize(databaseSizeBeforeUpdate);
        SysNotice testSysNotice = sysNoticeList.get(sysNoticeList.size() - 1);
        assertThat(testSysNotice.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testSysNotice.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testSysNotice.getSender()).isEqualTo(UPDATED_SENDER);
        assertThat(testSysNotice.getSendDate()).isEqualTo(UPDATED_SEND_DATE);
        assertThat(testSysNotice.getReceiver()).isEqualTo(UPDATED_RECEIVER);
        assertThat(testSysNotice.getAuditOrg()).isEqualTo(UPDATED_AUDIT_ORG);
        assertThat(testSysNotice.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    public void updateNonExistingSysNotice() throws Exception {
        int databaseSizeBeforeUpdate = sysNoticeRepository.findAll().size();

        // Create the SysNotice
        SysNoticeDTO sysNoticeDTO = sysNoticeMapper.toDto(sysNotice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSysNoticeMockMvc.perform(put("/api/sys-notices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sysNoticeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SysNotice in the database
        List<SysNotice> sysNoticeList = sysNoticeRepository.findAll();
        assertThat(sysNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSysNotice() throws Exception {
        // Initialize the database
        sysNoticeRepository.saveAndFlush(sysNotice);

        int databaseSizeBeforeDelete = sysNoticeRepository.findAll().size();

        // Delete the sysNotice
        restSysNoticeMockMvc.perform(delete("/api/sys-notices/{id}", sysNotice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SysNotice> sysNoticeList = sysNoticeRepository.findAll();
        assertThat(sysNoticeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
