package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.ZhuodewenApp;
import cn.dgd.zdw.domain.Notice;
import cn.dgd.zdw.repository.NoticeRepository;
import cn.dgd.zdw.service.NoticeService;
import cn.dgd.zdw.service.dto.NoticeDTO;
import cn.dgd.zdw.service.mapper.NoticeMapper;
import cn.dgd.zdw.service.dto.NoticeCriteria;
import cn.dgd.zdw.service.NoticeQueryService;

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
 * Integration tests for the {@link NoticeResource} REST controller.
 */
@SpringBootTest(classes = ZhuodewenApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NoticeResourceIT {

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
    private NoticeRepository noticeRepository;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private NoticeQueryService noticeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNoticeMockMvc;

    private Notice notice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notice createEntity(EntityManager em) {
        Notice notice = new Notice()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .sender(DEFAULT_SENDER)
            .sendDate(DEFAULT_SEND_DATE)
            .receiver(DEFAULT_RECEIVER)
            .auditOrg(DEFAULT_AUDIT_ORG)
            .delFlag(DEFAULT_DEL_FLAG);
        return notice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notice createUpdatedEntity(EntityManager em) {
        Notice notice = new Notice()
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .sender(UPDATED_SENDER)
            .sendDate(UPDATED_SEND_DATE)
            .receiver(UPDATED_RECEIVER)
            .auditOrg(UPDATED_AUDIT_ORG)
            .delFlag(UPDATED_DEL_FLAG);
        return notice;
    }

    @BeforeEach
    public void initTest() {
        notice = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotice() throws Exception {
        int databaseSizeBeforeCreate = noticeRepository.findAll().size();
        // Create the Notice
        NoticeDTO noticeDTO = noticeMapper.toDto(notice);
        restNoticeMockMvc.perform(post("/api/notices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticeDTO)))
            .andExpect(status().isCreated());

        // Validate the Notice in the database
        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeCreate + 1);
        Notice testNotice = noticeList.get(noticeList.size() - 1);
        assertThat(testNotice.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testNotice.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testNotice.getSender()).isEqualTo(DEFAULT_SENDER);
        assertThat(testNotice.getSendDate()).isEqualTo(DEFAULT_SEND_DATE);
        assertThat(testNotice.getReceiver()).isEqualTo(DEFAULT_RECEIVER);
        assertThat(testNotice.getAuditOrg()).isEqualTo(DEFAULT_AUDIT_ORG);
        assertThat(testNotice.getDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
    }

    @Test
    @Transactional
    public void createNoticeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = noticeRepository.findAll().size();

        // Create the Notice with an existing ID
        notice.setId(1L);
        NoticeDTO noticeDTO = noticeMapper.toDto(notice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNoticeMockMvc.perform(post("/api/notices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notice in the database
        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNotices() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList
        restNoticeMockMvc.perform(get("/api/notices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notice.getId().intValue())))
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
    public void getNotice() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get the notice
        restNoticeMockMvc.perform(get("/api/notices/{id}", notice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(notice.getId().intValue()))
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
    public void getNoticesByIdFiltering() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        Long id = notice.getId();

        defaultNoticeShouldBeFound("id.equals=" + id);
        defaultNoticeShouldNotBeFound("id.notEquals=" + id);

        defaultNoticeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNoticeShouldNotBeFound("id.greaterThan=" + id);

        defaultNoticeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNoticeShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNoticesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where title equals to DEFAULT_TITLE
        defaultNoticeShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the noticeList where title equals to UPDATED_TITLE
        defaultNoticeShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNoticesByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where title not equals to DEFAULT_TITLE
        defaultNoticeShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the noticeList where title not equals to UPDATED_TITLE
        defaultNoticeShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNoticesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultNoticeShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the noticeList where title equals to UPDATED_TITLE
        defaultNoticeShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNoticesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where title is not null
        defaultNoticeShouldBeFound("title.specified=true");

        // Get all the noticeList where title is null
        defaultNoticeShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllNoticesByTitleContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where title contains DEFAULT_TITLE
        defaultNoticeShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the noticeList where title contains UPDATED_TITLE
        defaultNoticeShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllNoticesByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where title does not contain DEFAULT_TITLE
        defaultNoticeShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the noticeList where title does not contain UPDATED_TITLE
        defaultNoticeShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllNoticesByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where content equals to DEFAULT_CONTENT
        defaultNoticeShouldBeFound("content.equals=" + DEFAULT_CONTENT);

        // Get all the noticeList where content equals to UPDATED_CONTENT
        defaultNoticeShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllNoticesByContentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where content not equals to DEFAULT_CONTENT
        defaultNoticeShouldNotBeFound("content.notEquals=" + DEFAULT_CONTENT);

        // Get all the noticeList where content not equals to UPDATED_CONTENT
        defaultNoticeShouldBeFound("content.notEquals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllNoticesByContentIsInShouldWork() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where content in DEFAULT_CONTENT or UPDATED_CONTENT
        defaultNoticeShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);

        // Get all the noticeList where content equals to UPDATED_CONTENT
        defaultNoticeShouldNotBeFound("content.in=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllNoticesByContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where content is not null
        defaultNoticeShouldBeFound("content.specified=true");

        // Get all the noticeList where content is null
        defaultNoticeShouldNotBeFound("content.specified=false");
    }
                @Test
    @Transactional
    public void getAllNoticesByContentContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where content contains DEFAULT_CONTENT
        defaultNoticeShouldBeFound("content.contains=" + DEFAULT_CONTENT);

        // Get all the noticeList where content contains UPDATED_CONTENT
        defaultNoticeShouldNotBeFound("content.contains=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllNoticesByContentNotContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where content does not contain DEFAULT_CONTENT
        defaultNoticeShouldNotBeFound("content.doesNotContain=" + DEFAULT_CONTENT);

        // Get all the noticeList where content does not contain UPDATED_CONTENT
        defaultNoticeShouldBeFound("content.doesNotContain=" + UPDATED_CONTENT);
    }


    @Test
    @Transactional
    public void getAllNoticesBySenderIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where sender equals to DEFAULT_SENDER
        defaultNoticeShouldBeFound("sender.equals=" + DEFAULT_SENDER);

        // Get all the noticeList where sender equals to UPDATED_SENDER
        defaultNoticeShouldNotBeFound("sender.equals=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    public void getAllNoticesBySenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where sender not equals to DEFAULT_SENDER
        defaultNoticeShouldNotBeFound("sender.notEquals=" + DEFAULT_SENDER);

        // Get all the noticeList where sender not equals to UPDATED_SENDER
        defaultNoticeShouldBeFound("sender.notEquals=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    public void getAllNoticesBySenderIsInShouldWork() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where sender in DEFAULT_SENDER or UPDATED_SENDER
        defaultNoticeShouldBeFound("sender.in=" + DEFAULT_SENDER + "," + UPDATED_SENDER);

        // Get all the noticeList where sender equals to UPDATED_SENDER
        defaultNoticeShouldNotBeFound("sender.in=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    public void getAllNoticesBySenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where sender is not null
        defaultNoticeShouldBeFound("sender.specified=true");

        // Get all the noticeList where sender is null
        defaultNoticeShouldNotBeFound("sender.specified=false");
    }
                @Test
    @Transactional
    public void getAllNoticesBySenderContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where sender contains DEFAULT_SENDER
        defaultNoticeShouldBeFound("sender.contains=" + DEFAULT_SENDER);

        // Get all the noticeList where sender contains UPDATED_SENDER
        defaultNoticeShouldNotBeFound("sender.contains=" + UPDATED_SENDER);
    }

    @Test
    @Transactional
    public void getAllNoticesBySenderNotContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where sender does not contain DEFAULT_SENDER
        defaultNoticeShouldNotBeFound("sender.doesNotContain=" + DEFAULT_SENDER);

        // Get all the noticeList where sender does not contain UPDATED_SENDER
        defaultNoticeShouldBeFound("sender.doesNotContain=" + UPDATED_SENDER);
    }


    @Test
    @Transactional
    public void getAllNoticesBySendDateIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where sendDate equals to DEFAULT_SEND_DATE
        defaultNoticeShouldBeFound("sendDate.equals=" + DEFAULT_SEND_DATE);

        // Get all the noticeList where sendDate equals to UPDATED_SEND_DATE
        defaultNoticeShouldNotBeFound("sendDate.equals=" + UPDATED_SEND_DATE);
    }

    @Test
    @Transactional
    public void getAllNoticesBySendDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where sendDate not equals to DEFAULT_SEND_DATE
        defaultNoticeShouldNotBeFound("sendDate.notEquals=" + DEFAULT_SEND_DATE);

        // Get all the noticeList where sendDate not equals to UPDATED_SEND_DATE
        defaultNoticeShouldBeFound("sendDate.notEquals=" + UPDATED_SEND_DATE);
    }

    @Test
    @Transactional
    public void getAllNoticesBySendDateIsInShouldWork() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where sendDate in DEFAULT_SEND_DATE or UPDATED_SEND_DATE
        defaultNoticeShouldBeFound("sendDate.in=" + DEFAULT_SEND_DATE + "," + UPDATED_SEND_DATE);

        // Get all the noticeList where sendDate equals to UPDATED_SEND_DATE
        defaultNoticeShouldNotBeFound("sendDate.in=" + UPDATED_SEND_DATE);
    }

    @Test
    @Transactional
    public void getAllNoticesBySendDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where sendDate is not null
        defaultNoticeShouldBeFound("sendDate.specified=true");

        // Get all the noticeList where sendDate is null
        defaultNoticeShouldNotBeFound("sendDate.specified=false");
    }
                @Test
    @Transactional
    public void getAllNoticesBySendDateContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where sendDate contains DEFAULT_SEND_DATE
        defaultNoticeShouldBeFound("sendDate.contains=" + DEFAULT_SEND_DATE);

        // Get all the noticeList where sendDate contains UPDATED_SEND_DATE
        defaultNoticeShouldNotBeFound("sendDate.contains=" + UPDATED_SEND_DATE);
    }

    @Test
    @Transactional
    public void getAllNoticesBySendDateNotContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where sendDate does not contain DEFAULT_SEND_DATE
        defaultNoticeShouldNotBeFound("sendDate.doesNotContain=" + DEFAULT_SEND_DATE);

        // Get all the noticeList where sendDate does not contain UPDATED_SEND_DATE
        defaultNoticeShouldBeFound("sendDate.doesNotContain=" + UPDATED_SEND_DATE);
    }


    @Test
    @Transactional
    public void getAllNoticesByReceiverIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where receiver equals to DEFAULT_RECEIVER
        defaultNoticeShouldBeFound("receiver.equals=" + DEFAULT_RECEIVER);

        // Get all the noticeList where receiver equals to UPDATED_RECEIVER
        defaultNoticeShouldNotBeFound("receiver.equals=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    public void getAllNoticesByReceiverIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where receiver not equals to DEFAULT_RECEIVER
        defaultNoticeShouldNotBeFound("receiver.notEquals=" + DEFAULT_RECEIVER);

        // Get all the noticeList where receiver not equals to UPDATED_RECEIVER
        defaultNoticeShouldBeFound("receiver.notEquals=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    public void getAllNoticesByReceiverIsInShouldWork() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where receiver in DEFAULT_RECEIVER or UPDATED_RECEIVER
        defaultNoticeShouldBeFound("receiver.in=" + DEFAULT_RECEIVER + "," + UPDATED_RECEIVER);

        // Get all the noticeList where receiver equals to UPDATED_RECEIVER
        defaultNoticeShouldNotBeFound("receiver.in=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    public void getAllNoticesByReceiverIsNullOrNotNull() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where receiver is not null
        defaultNoticeShouldBeFound("receiver.specified=true");

        // Get all the noticeList where receiver is null
        defaultNoticeShouldNotBeFound("receiver.specified=false");
    }

    @Test
    @Transactional
    public void getAllNoticesByReceiverIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where receiver is greater than or equal to DEFAULT_RECEIVER
        defaultNoticeShouldBeFound("receiver.greaterThanOrEqual=" + DEFAULT_RECEIVER);

        // Get all the noticeList where receiver is greater than or equal to UPDATED_RECEIVER
        defaultNoticeShouldNotBeFound("receiver.greaterThanOrEqual=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    public void getAllNoticesByReceiverIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where receiver is less than or equal to DEFAULT_RECEIVER
        defaultNoticeShouldBeFound("receiver.lessThanOrEqual=" + DEFAULT_RECEIVER);

        // Get all the noticeList where receiver is less than or equal to SMALLER_RECEIVER
        defaultNoticeShouldNotBeFound("receiver.lessThanOrEqual=" + SMALLER_RECEIVER);
    }

    @Test
    @Transactional
    public void getAllNoticesByReceiverIsLessThanSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where receiver is less than DEFAULT_RECEIVER
        defaultNoticeShouldNotBeFound("receiver.lessThan=" + DEFAULT_RECEIVER);

        // Get all the noticeList where receiver is less than UPDATED_RECEIVER
        defaultNoticeShouldBeFound("receiver.lessThan=" + UPDATED_RECEIVER);
    }

    @Test
    @Transactional
    public void getAllNoticesByReceiverIsGreaterThanSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where receiver is greater than DEFAULT_RECEIVER
        defaultNoticeShouldNotBeFound("receiver.greaterThan=" + DEFAULT_RECEIVER);

        // Get all the noticeList where receiver is greater than SMALLER_RECEIVER
        defaultNoticeShouldBeFound("receiver.greaterThan=" + SMALLER_RECEIVER);
    }


    @Test
    @Transactional
    public void getAllNoticesByAuditOrgIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where auditOrg equals to DEFAULT_AUDIT_ORG
        defaultNoticeShouldBeFound("auditOrg.equals=" + DEFAULT_AUDIT_ORG);

        // Get all the noticeList where auditOrg equals to UPDATED_AUDIT_ORG
        defaultNoticeShouldNotBeFound("auditOrg.equals=" + UPDATED_AUDIT_ORG);
    }

    @Test
    @Transactional
    public void getAllNoticesByAuditOrgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where auditOrg not equals to DEFAULT_AUDIT_ORG
        defaultNoticeShouldNotBeFound("auditOrg.notEquals=" + DEFAULT_AUDIT_ORG);

        // Get all the noticeList where auditOrg not equals to UPDATED_AUDIT_ORG
        defaultNoticeShouldBeFound("auditOrg.notEquals=" + UPDATED_AUDIT_ORG);
    }

    @Test
    @Transactional
    public void getAllNoticesByAuditOrgIsInShouldWork() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where auditOrg in DEFAULT_AUDIT_ORG or UPDATED_AUDIT_ORG
        defaultNoticeShouldBeFound("auditOrg.in=" + DEFAULT_AUDIT_ORG + "," + UPDATED_AUDIT_ORG);

        // Get all the noticeList where auditOrg equals to UPDATED_AUDIT_ORG
        defaultNoticeShouldNotBeFound("auditOrg.in=" + UPDATED_AUDIT_ORG);
    }

    @Test
    @Transactional
    public void getAllNoticesByAuditOrgIsNullOrNotNull() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where auditOrg is not null
        defaultNoticeShouldBeFound("auditOrg.specified=true");

        // Get all the noticeList where auditOrg is null
        defaultNoticeShouldNotBeFound("auditOrg.specified=false");
    }

    @Test
    @Transactional
    public void getAllNoticesByAuditOrgIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where auditOrg is greater than or equal to DEFAULT_AUDIT_ORG
        defaultNoticeShouldBeFound("auditOrg.greaterThanOrEqual=" + DEFAULT_AUDIT_ORG);

        // Get all the noticeList where auditOrg is greater than or equal to UPDATED_AUDIT_ORG
        defaultNoticeShouldNotBeFound("auditOrg.greaterThanOrEqual=" + UPDATED_AUDIT_ORG);
    }

    @Test
    @Transactional
    public void getAllNoticesByAuditOrgIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where auditOrg is less than or equal to DEFAULT_AUDIT_ORG
        defaultNoticeShouldBeFound("auditOrg.lessThanOrEqual=" + DEFAULT_AUDIT_ORG);

        // Get all the noticeList where auditOrg is less than or equal to SMALLER_AUDIT_ORG
        defaultNoticeShouldNotBeFound("auditOrg.lessThanOrEqual=" + SMALLER_AUDIT_ORG);
    }

    @Test
    @Transactional
    public void getAllNoticesByAuditOrgIsLessThanSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where auditOrg is less than DEFAULT_AUDIT_ORG
        defaultNoticeShouldNotBeFound("auditOrg.lessThan=" + DEFAULT_AUDIT_ORG);

        // Get all the noticeList where auditOrg is less than UPDATED_AUDIT_ORG
        defaultNoticeShouldBeFound("auditOrg.lessThan=" + UPDATED_AUDIT_ORG);
    }

    @Test
    @Transactional
    public void getAllNoticesByAuditOrgIsGreaterThanSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where auditOrg is greater than DEFAULT_AUDIT_ORG
        defaultNoticeShouldNotBeFound("auditOrg.greaterThan=" + DEFAULT_AUDIT_ORG);

        // Get all the noticeList where auditOrg is greater than SMALLER_AUDIT_ORG
        defaultNoticeShouldBeFound("auditOrg.greaterThan=" + SMALLER_AUDIT_ORG);
    }


    @Test
    @Transactional
    public void getAllNoticesByDelFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where delFlag equals to DEFAULT_DEL_FLAG
        defaultNoticeShouldBeFound("delFlag.equals=" + DEFAULT_DEL_FLAG);

        // Get all the noticeList where delFlag equals to UPDATED_DEL_FLAG
        defaultNoticeShouldNotBeFound("delFlag.equals=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    public void getAllNoticesByDelFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where delFlag not equals to DEFAULT_DEL_FLAG
        defaultNoticeShouldNotBeFound("delFlag.notEquals=" + DEFAULT_DEL_FLAG);

        // Get all the noticeList where delFlag not equals to UPDATED_DEL_FLAG
        defaultNoticeShouldBeFound("delFlag.notEquals=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    public void getAllNoticesByDelFlagIsInShouldWork() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where delFlag in DEFAULT_DEL_FLAG or UPDATED_DEL_FLAG
        defaultNoticeShouldBeFound("delFlag.in=" + DEFAULT_DEL_FLAG + "," + UPDATED_DEL_FLAG);

        // Get all the noticeList where delFlag equals to UPDATED_DEL_FLAG
        defaultNoticeShouldNotBeFound("delFlag.in=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    public void getAllNoticesByDelFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where delFlag is not null
        defaultNoticeShouldBeFound("delFlag.specified=true");

        // Get all the noticeList where delFlag is null
        defaultNoticeShouldNotBeFound("delFlag.specified=false");
    }
                @Test
    @Transactional
    public void getAllNoticesByDelFlagContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where delFlag contains DEFAULT_DEL_FLAG
        defaultNoticeShouldBeFound("delFlag.contains=" + DEFAULT_DEL_FLAG);

        // Get all the noticeList where delFlag contains UPDATED_DEL_FLAG
        defaultNoticeShouldNotBeFound("delFlag.contains=" + UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    public void getAllNoticesByDelFlagNotContainsSomething() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        // Get all the noticeList where delFlag does not contain DEFAULT_DEL_FLAG
        defaultNoticeShouldNotBeFound("delFlag.doesNotContain=" + DEFAULT_DEL_FLAG);

        // Get all the noticeList where delFlag does not contain UPDATED_DEL_FLAG
        defaultNoticeShouldBeFound("delFlag.doesNotContain=" + UPDATED_DEL_FLAG);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNoticeShouldBeFound(String filter) throws Exception {
        restNoticeMockMvc.perform(get("/api/notices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notice.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].sender").value(hasItem(DEFAULT_SENDER)))
            .andExpect(jsonPath("$.[*].sendDate").value(hasItem(DEFAULT_SEND_DATE)))
            .andExpect(jsonPath("$.[*].receiver").value(hasItem(DEFAULT_RECEIVER.toString())))
            .andExpect(jsonPath("$.[*].auditOrg").value(hasItem(DEFAULT_AUDIT_ORG.toString())))
            .andExpect(jsonPath("$.[*].delFlag").value(hasItem(DEFAULT_DEL_FLAG)));

        // Check, that the count call also returns 1
        restNoticeMockMvc.perform(get("/api/notices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNoticeShouldNotBeFound(String filter) throws Exception {
        restNoticeMockMvc.perform(get("/api/notices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNoticeMockMvc.perform(get("/api/notices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNotice() throws Exception {
        // Get the notice
        restNoticeMockMvc.perform(get("/api/notices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotice() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        int databaseSizeBeforeUpdate = noticeRepository.findAll().size();

        // Update the notice
        Notice updatedNotice = noticeRepository.findById(notice.getId()).get();
        // Disconnect from session so that the updates on updatedNotice are not directly saved in db
        em.detach(updatedNotice);
        updatedNotice
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .sender(UPDATED_SENDER)
            .sendDate(UPDATED_SEND_DATE)
            .receiver(UPDATED_RECEIVER)
            .auditOrg(UPDATED_AUDIT_ORG)
            .delFlag(UPDATED_DEL_FLAG);
        NoticeDTO noticeDTO = noticeMapper.toDto(updatedNotice);

        restNoticeMockMvc.perform(put("/api/notices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticeDTO)))
            .andExpect(status().isOk());

        // Validate the Notice in the database
        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeUpdate);
        Notice testNotice = noticeList.get(noticeList.size() - 1);
        assertThat(testNotice.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testNotice.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testNotice.getSender()).isEqualTo(UPDATED_SENDER);
        assertThat(testNotice.getSendDate()).isEqualTo(UPDATED_SEND_DATE);
        assertThat(testNotice.getReceiver()).isEqualTo(UPDATED_RECEIVER);
        assertThat(testNotice.getAuditOrg()).isEqualTo(UPDATED_AUDIT_ORG);
        assertThat(testNotice.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    public void updateNonExistingNotice() throws Exception {
        int databaseSizeBeforeUpdate = noticeRepository.findAll().size();

        // Create the Notice
        NoticeDTO noticeDTO = noticeMapper.toDto(notice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNoticeMockMvc.perform(put("/api/notices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(noticeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Notice in the database
        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotice() throws Exception {
        // Initialize the database
        noticeRepository.saveAndFlush(notice);

        int databaseSizeBeforeDelete = noticeRepository.findAll().size();

        // Delete the notice
        restNoticeMockMvc.perform(delete("/api/notices/{id}", notice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Notice> noticeList = noticeRepository.findAll();
        assertThat(noticeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
