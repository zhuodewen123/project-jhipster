package cn.dgd.zdw.web.rest;

import cn.dgd.zdw.ZhuodewenApp;
import cn.dgd.zdw.domain.TSysNotice;
import cn.dgd.zdw.repository.TSysNoticeRepository;
import cn.dgd.zdw.service.TSysNoticeService;
import cn.dgd.zdw.service.dto.TSysNoticeDTO;
import cn.dgd.zdw.service.mapper.TSysNoticeMapper;

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
 * Integration tests for the {@link TSysNoticeResource} REST controller.
 */
@SpringBootTest(classes = ZhuodewenApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TSysNoticeResourceIT {

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

    private static final LocalDate DEFAULT_AUDIT_ORG = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AUDIT_ORG = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEL_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_DEL_FLAG = "BBBBBBBBBB";

    @Autowired
    private TSysNoticeRepository tSysNoticeRepository;

    @Autowired
    private TSysNoticeMapper tSysNoticeMapper;

    @Autowired
    private TSysNoticeService tSysNoticeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTSysNoticeMockMvc;

    private TSysNotice tSysNotice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TSysNotice createEntity(EntityManager em) {
        TSysNotice tSysNotice = new TSysNotice()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .sender(DEFAULT_SENDER)
            .sendDate(DEFAULT_SEND_DATE)
            .receiver(DEFAULT_RECEIVER)
            .auditOrg(DEFAULT_AUDIT_ORG)
            .delFlag(DEFAULT_DEL_FLAG);
        return tSysNotice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TSysNotice createUpdatedEntity(EntityManager em) {
        TSysNotice tSysNotice = new TSysNotice()
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .sender(UPDATED_SENDER)
            .sendDate(UPDATED_SEND_DATE)
            .receiver(UPDATED_RECEIVER)
            .auditOrg(UPDATED_AUDIT_ORG)
            .delFlag(UPDATED_DEL_FLAG);
        return tSysNotice;
    }

    @BeforeEach
    public void initTest() {
        tSysNotice = createEntity(em);
    }

    @Test
    @Transactional
    public void createTSysNotice() throws Exception {
        int databaseSizeBeforeCreate = tSysNoticeRepository.findAll().size();
        // Create the TSysNotice
        TSysNoticeDTO tSysNoticeDTO = tSysNoticeMapper.toDto(tSysNotice);
        restTSysNoticeMockMvc.perform(post("/api/t-sys-notices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tSysNoticeDTO)))
            .andExpect(status().isCreated());

        // Validate the TSysNotice in the database
        List<TSysNotice> tSysNoticeList = tSysNoticeRepository.findAll();
        assertThat(tSysNoticeList).hasSize(databaseSizeBeforeCreate + 1);
        TSysNotice testTSysNotice = tSysNoticeList.get(tSysNoticeList.size() - 1);
        assertThat(testTSysNotice.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTSysNotice.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testTSysNotice.getSender()).isEqualTo(DEFAULT_SENDER);
        assertThat(testTSysNotice.getSendDate()).isEqualTo(DEFAULT_SEND_DATE);
        assertThat(testTSysNotice.getReceiver()).isEqualTo(DEFAULT_RECEIVER);
        assertThat(testTSysNotice.getAuditOrg()).isEqualTo(DEFAULT_AUDIT_ORG);
        assertThat(testTSysNotice.getDelFlag()).isEqualTo(DEFAULT_DEL_FLAG);
    }

    @Test
    @Transactional
    public void createTSysNoticeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tSysNoticeRepository.findAll().size();

        // Create the TSysNotice with an existing ID
        tSysNotice.setId(1L);
        TSysNoticeDTO tSysNoticeDTO = tSysNoticeMapper.toDto(tSysNotice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTSysNoticeMockMvc.perform(post("/api/t-sys-notices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tSysNoticeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TSysNotice in the database
        List<TSysNotice> tSysNoticeList = tSysNoticeRepository.findAll();
        assertThat(tSysNoticeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTSysNotices() throws Exception {
        // Initialize the database
        tSysNoticeRepository.saveAndFlush(tSysNotice);

        // Get all the tSysNoticeList
        restTSysNoticeMockMvc.perform(get("/api/t-sys-notices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tSysNotice.getId().intValue())))
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
    public void getTSysNotice() throws Exception {
        // Initialize the database
        tSysNoticeRepository.saveAndFlush(tSysNotice);

        // Get the tSysNotice
        restTSysNoticeMockMvc.perform(get("/api/t-sys-notices/{id}", tSysNotice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tSysNotice.getId().intValue()))
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
    public void getNonExistingTSysNotice() throws Exception {
        // Get the tSysNotice
        restTSysNoticeMockMvc.perform(get("/api/t-sys-notices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTSysNotice() throws Exception {
        // Initialize the database
        tSysNoticeRepository.saveAndFlush(tSysNotice);

        int databaseSizeBeforeUpdate = tSysNoticeRepository.findAll().size();

        // Update the tSysNotice
        TSysNotice updatedTSysNotice = tSysNoticeRepository.findById(tSysNotice.getId()).get();
        // Disconnect from session so that the updates on updatedTSysNotice are not directly saved in db
        em.detach(updatedTSysNotice);
        updatedTSysNotice
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .sender(UPDATED_SENDER)
            .sendDate(UPDATED_SEND_DATE)
            .receiver(UPDATED_RECEIVER)
            .auditOrg(UPDATED_AUDIT_ORG)
            .delFlag(UPDATED_DEL_FLAG);
        TSysNoticeDTO tSysNoticeDTO = tSysNoticeMapper.toDto(updatedTSysNotice);

        restTSysNoticeMockMvc.perform(put("/api/t-sys-notices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tSysNoticeDTO)))
            .andExpect(status().isOk());

        // Validate the TSysNotice in the database
        List<TSysNotice> tSysNoticeList = tSysNoticeRepository.findAll();
        assertThat(tSysNoticeList).hasSize(databaseSizeBeforeUpdate);
        TSysNotice testTSysNotice = tSysNoticeList.get(tSysNoticeList.size() - 1);
        assertThat(testTSysNotice.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTSysNotice.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTSysNotice.getSender()).isEqualTo(UPDATED_SENDER);
        assertThat(testTSysNotice.getSendDate()).isEqualTo(UPDATED_SEND_DATE);
        assertThat(testTSysNotice.getReceiver()).isEqualTo(UPDATED_RECEIVER);
        assertThat(testTSysNotice.getAuditOrg()).isEqualTo(UPDATED_AUDIT_ORG);
        assertThat(testTSysNotice.getDelFlag()).isEqualTo(UPDATED_DEL_FLAG);
    }

    @Test
    @Transactional
    public void updateNonExistingTSysNotice() throws Exception {
        int databaseSizeBeforeUpdate = tSysNoticeRepository.findAll().size();

        // Create the TSysNotice
        TSysNoticeDTO tSysNoticeDTO = tSysNoticeMapper.toDto(tSysNotice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTSysNoticeMockMvc.perform(put("/api/t-sys-notices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tSysNoticeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TSysNotice in the database
        List<TSysNotice> tSysNoticeList = tSysNoticeRepository.findAll();
        assertThat(tSysNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTSysNotice() throws Exception {
        // Initialize the database
        tSysNoticeRepository.saveAndFlush(tSysNotice);

        int databaseSizeBeforeDelete = tSysNoticeRepository.findAll().size();

        // Delete the tSysNotice
        restTSysNoticeMockMvc.perform(delete("/api/t-sys-notices/{id}", tSysNotice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TSysNotice> tSysNoticeList = tSysNoticeRepository.findAll();
        assertThat(tSysNoticeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
