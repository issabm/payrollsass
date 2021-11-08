package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.ManagementResourceFun;
import com.issa.payroll.repository.ManagementResourceFunRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ManagementResourceFunResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ManagementResourceFunResourceIT {

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE_ADD = false;
    private static final Boolean UPDATED_ENABLE_ADD = true;

    private static final Boolean DEFAULT_ENABLE_CNST = false;
    private static final Boolean UPDATED_ENABLE_CNST = true;

    private static final Boolean DEFAULT_ENABLE_DEL = false;
    private static final Boolean UPDATED_ENABLE_DEL = true;

    private static final Boolean DEFAULT_ENABLE_ED = false;
    private static final Boolean UPDATED_ENABLE_ED = true;

    private static final ZonedDateTime DEFAULT_DATEOP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_OP = "AAAAAAAAAA";
    private static final String UPDATED_OP = "BBBBBBBBBB";

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/management-resource-funs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ManagementResourceFunRepository managementResourceFunRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restManagementResourceFunMockMvc;

    private ManagementResourceFun managementResourceFun;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ManagementResourceFun createEntity(EntityManager em) {
        ManagementResourceFun managementResourceFun = new ManagementResourceFun()
            .libEn(DEFAULT_LIB_EN)
            .profile(DEFAULT_PROFILE)
            .enableAdd(DEFAULT_ENABLE_ADD)
            .enableCnst(DEFAULT_ENABLE_CNST)
            .enableDel(DEFAULT_ENABLE_DEL)
            .enableEd(DEFAULT_ENABLE_ED)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .op(DEFAULT_OP)
            .util(DEFAULT_UTIL)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return managementResourceFun;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ManagementResourceFun createUpdatedEntity(EntityManager em) {
        ManagementResourceFun managementResourceFun = new ManagementResourceFun()
            .libEn(UPDATED_LIB_EN)
            .profile(UPDATED_PROFILE)
            .enableAdd(UPDATED_ENABLE_ADD)
            .enableCnst(UPDATED_ENABLE_CNST)
            .enableDel(UPDATED_ENABLE_DEL)
            .enableEd(UPDATED_ENABLE_ED)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return managementResourceFun;
    }

    @BeforeEach
    public void initTest() {
        managementResourceFun = createEntity(em);
    }

    @Test
    @Transactional
    void createManagementResourceFun() throws Exception {
        int databaseSizeBeforeCreate = managementResourceFunRepository.findAll().size();
        // Create the ManagementResourceFun
        restManagementResourceFunMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceFun))
            )
            .andExpect(status().isCreated());

        // Validate the ManagementResourceFun in the database
        List<ManagementResourceFun> managementResourceFunList = managementResourceFunRepository.findAll();
        assertThat(managementResourceFunList).hasSize(databaseSizeBeforeCreate + 1);
        ManagementResourceFun testManagementResourceFun = managementResourceFunList.get(managementResourceFunList.size() - 1);
        assertThat(testManagementResourceFun.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testManagementResourceFun.getProfile()).isEqualTo(DEFAULT_PROFILE);
        assertThat(testManagementResourceFun.getEnableAdd()).isEqualTo(DEFAULT_ENABLE_ADD);
        assertThat(testManagementResourceFun.getEnableCnst()).isEqualTo(DEFAULT_ENABLE_CNST);
        assertThat(testManagementResourceFun.getEnableDel()).isEqualTo(DEFAULT_ENABLE_DEL);
        assertThat(testManagementResourceFun.getEnableEd()).isEqualTo(DEFAULT_ENABLE_ED);
        assertThat(testManagementResourceFun.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testManagementResourceFun.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testManagementResourceFun.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testManagementResourceFun.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testManagementResourceFun.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testManagementResourceFun.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testManagementResourceFun.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testManagementResourceFun.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createManagementResourceFunWithExistingId() throws Exception {
        // Create the ManagementResourceFun with an existing ID
        managementResourceFun.setId(1L);

        int databaseSizeBeforeCreate = managementResourceFunRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restManagementResourceFunMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceFun))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResourceFun in the database
        List<ManagementResourceFun> managementResourceFunList = managementResourceFunRepository.findAll();
        assertThat(managementResourceFunList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllManagementResourceFuns() throws Exception {
        // Initialize the database
        managementResourceFunRepository.saveAndFlush(managementResourceFun);

        // Get all the managementResourceFunList
        restManagementResourceFunMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(managementResourceFun.getId().intValue())))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].profile").value(hasItem(DEFAULT_PROFILE)))
            .andExpect(jsonPath("$.[*].enableAdd").value(hasItem(DEFAULT_ENABLE_ADD.booleanValue())))
            .andExpect(jsonPath("$.[*].enableCnst").value(hasItem(DEFAULT_ENABLE_CNST.booleanValue())))
            .andExpect(jsonPath("$.[*].enableDel").value(hasItem(DEFAULT_ENABLE_DEL.booleanValue())))
            .andExpect(jsonPath("$.[*].enableEd").value(hasItem(DEFAULT_ENABLE_ED.booleanValue())))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    void getManagementResourceFun() throws Exception {
        // Initialize the database
        managementResourceFunRepository.saveAndFlush(managementResourceFun);

        // Get the managementResourceFun
        restManagementResourceFunMockMvc
            .perform(get(ENTITY_API_URL_ID, managementResourceFun.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(managementResourceFun.getId().intValue()))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.profile").value(DEFAULT_PROFILE))
            .andExpect(jsonPath("$.enableAdd").value(DEFAULT_ENABLE_ADD.booleanValue()))
            .andExpect(jsonPath("$.enableCnst").value(DEFAULT_ENABLE_CNST.booleanValue()))
            .andExpect(jsonPath("$.enableDel").value(DEFAULT_ENABLE_DEL.booleanValue()))
            .andExpect(jsonPath("$.enableEd").value(DEFAULT_ENABLE_ED.booleanValue()))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingManagementResourceFun() throws Exception {
        // Get the managementResourceFun
        restManagementResourceFunMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewManagementResourceFun() throws Exception {
        // Initialize the database
        managementResourceFunRepository.saveAndFlush(managementResourceFun);

        int databaseSizeBeforeUpdate = managementResourceFunRepository.findAll().size();

        // Update the managementResourceFun
        ManagementResourceFun updatedManagementResourceFun = managementResourceFunRepository.findById(managementResourceFun.getId()).get();
        // Disconnect from session so that the updates on updatedManagementResourceFun are not directly saved in db
        em.detach(updatedManagementResourceFun);
        updatedManagementResourceFun
            .libEn(UPDATED_LIB_EN)
            .profile(UPDATED_PROFILE)
            .enableAdd(UPDATED_ENABLE_ADD)
            .enableCnst(UPDATED_ENABLE_CNST)
            .enableDel(UPDATED_ENABLE_DEL)
            .enableEd(UPDATED_ENABLE_ED)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restManagementResourceFunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedManagementResourceFun.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedManagementResourceFun))
            )
            .andExpect(status().isOk());

        // Validate the ManagementResourceFun in the database
        List<ManagementResourceFun> managementResourceFunList = managementResourceFunRepository.findAll();
        assertThat(managementResourceFunList).hasSize(databaseSizeBeforeUpdate);
        ManagementResourceFun testManagementResourceFun = managementResourceFunList.get(managementResourceFunList.size() - 1);
        assertThat(testManagementResourceFun.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testManagementResourceFun.getProfile()).isEqualTo(UPDATED_PROFILE);
        assertThat(testManagementResourceFun.getEnableAdd()).isEqualTo(UPDATED_ENABLE_ADD);
        assertThat(testManagementResourceFun.getEnableCnst()).isEqualTo(UPDATED_ENABLE_CNST);
        assertThat(testManagementResourceFun.getEnableDel()).isEqualTo(UPDATED_ENABLE_DEL);
        assertThat(testManagementResourceFun.getEnableEd()).isEqualTo(UPDATED_ENABLE_ED);
        assertThat(testManagementResourceFun.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testManagementResourceFun.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testManagementResourceFun.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testManagementResourceFun.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testManagementResourceFun.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testManagementResourceFun.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testManagementResourceFun.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testManagementResourceFun.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingManagementResourceFun() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceFunRepository.findAll().size();
        managementResourceFun.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManagementResourceFunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, managementResourceFun.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceFun))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResourceFun in the database
        List<ManagementResourceFun> managementResourceFunList = managementResourceFunRepository.findAll();
        assertThat(managementResourceFunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchManagementResourceFun() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceFunRepository.findAll().size();
        managementResourceFun.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementResourceFunMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceFun))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResourceFun in the database
        List<ManagementResourceFun> managementResourceFunList = managementResourceFunRepository.findAll();
        assertThat(managementResourceFunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamManagementResourceFun() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceFunRepository.findAll().size();
        managementResourceFun.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementResourceFunMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceFun))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ManagementResourceFun in the database
        List<ManagementResourceFun> managementResourceFunList = managementResourceFunRepository.findAll();
        assertThat(managementResourceFunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateManagementResourceFunWithPatch() throws Exception {
        // Initialize the database
        managementResourceFunRepository.saveAndFlush(managementResourceFun);

        int databaseSizeBeforeUpdate = managementResourceFunRepository.findAll().size();

        // Update the managementResourceFun using partial update
        ManagementResourceFun partialUpdatedManagementResourceFun = new ManagementResourceFun();
        partialUpdatedManagementResourceFun.setId(managementResourceFun.getId());

        partialUpdatedManagementResourceFun
            .enableCnst(UPDATED_ENABLE_CNST)
            .enableDel(UPDATED_ENABLE_DEL)
            .enableEd(UPDATED_ENABLE_ED)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restManagementResourceFunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManagementResourceFun.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManagementResourceFun))
            )
            .andExpect(status().isOk());

        // Validate the ManagementResourceFun in the database
        List<ManagementResourceFun> managementResourceFunList = managementResourceFunRepository.findAll();
        assertThat(managementResourceFunList).hasSize(databaseSizeBeforeUpdate);
        ManagementResourceFun testManagementResourceFun = managementResourceFunList.get(managementResourceFunList.size() - 1);
        assertThat(testManagementResourceFun.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testManagementResourceFun.getProfile()).isEqualTo(DEFAULT_PROFILE);
        assertThat(testManagementResourceFun.getEnableAdd()).isEqualTo(DEFAULT_ENABLE_ADD);
        assertThat(testManagementResourceFun.getEnableCnst()).isEqualTo(UPDATED_ENABLE_CNST);
        assertThat(testManagementResourceFun.getEnableDel()).isEqualTo(UPDATED_ENABLE_DEL);
        assertThat(testManagementResourceFun.getEnableEd()).isEqualTo(UPDATED_ENABLE_ED);
        assertThat(testManagementResourceFun.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testManagementResourceFun.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testManagementResourceFun.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testManagementResourceFun.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testManagementResourceFun.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testManagementResourceFun.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testManagementResourceFun.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testManagementResourceFun.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateManagementResourceFunWithPatch() throws Exception {
        // Initialize the database
        managementResourceFunRepository.saveAndFlush(managementResourceFun);

        int databaseSizeBeforeUpdate = managementResourceFunRepository.findAll().size();

        // Update the managementResourceFun using partial update
        ManagementResourceFun partialUpdatedManagementResourceFun = new ManagementResourceFun();
        partialUpdatedManagementResourceFun.setId(managementResourceFun.getId());

        partialUpdatedManagementResourceFun
            .libEn(UPDATED_LIB_EN)
            .profile(UPDATED_PROFILE)
            .enableAdd(UPDATED_ENABLE_ADD)
            .enableCnst(UPDATED_ENABLE_CNST)
            .enableDel(UPDATED_ENABLE_DEL)
            .enableEd(UPDATED_ENABLE_ED)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restManagementResourceFunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManagementResourceFun.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManagementResourceFun))
            )
            .andExpect(status().isOk());

        // Validate the ManagementResourceFun in the database
        List<ManagementResourceFun> managementResourceFunList = managementResourceFunRepository.findAll();
        assertThat(managementResourceFunList).hasSize(databaseSizeBeforeUpdate);
        ManagementResourceFun testManagementResourceFun = managementResourceFunList.get(managementResourceFunList.size() - 1);
        assertThat(testManagementResourceFun.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testManagementResourceFun.getProfile()).isEqualTo(UPDATED_PROFILE);
        assertThat(testManagementResourceFun.getEnableAdd()).isEqualTo(UPDATED_ENABLE_ADD);
        assertThat(testManagementResourceFun.getEnableCnst()).isEqualTo(UPDATED_ENABLE_CNST);
        assertThat(testManagementResourceFun.getEnableDel()).isEqualTo(UPDATED_ENABLE_DEL);
        assertThat(testManagementResourceFun.getEnableEd()).isEqualTo(UPDATED_ENABLE_ED);
        assertThat(testManagementResourceFun.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testManagementResourceFun.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testManagementResourceFun.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testManagementResourceFun.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testManagementResourceFun.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testManagementResourceFun.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testManagementResourceFun.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testManagementResourceFun.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingManagementResourceFun() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceFunRepository.findAll().size();
        managementResourceFun.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManagementResourceFunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, managementResourceFun.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceFun))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResourceFun in the database
        List<ManagementResourceFun> managementResourceFunList = managementResourceFunRepository.findAll();
        assertThat(managementResourceFunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchManagementResourceFun() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceFunRepository.findAll().size();
        managementResourceFun.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementResourceFunMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceFun))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResourceFun in the database
        List<ManagementResourceFun> managementResourceFunList = managementResourceFunRepository.findAll();
        assertThat(managementResourceFunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamManagementResourceFun() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceFunRepository.findAll().size();
        managementResourceFun.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementResourceFunMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(managementResourceFun))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ManagementResourceFun in the database
        List<ManagementResourceFun> managementResourceFunList = managementResourceFunRepository.findAll();
        assertThat(managementResourceFunList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteManagementResourceFun() throws Exception {
        // Initialize the database
        managementResourceFunRepository.saveAndFlush(managementResourceFun);

        int databaseSizeBeforeDelete = managementResourceFunRepository.findAll().size();

        // Delete the managementResourceFun
        restManagementResourceFunMockMvc
            .perform(delete(ENTITY_API_URL_ID, managementResourceFun.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ManagementResourceFun> managementResourceFunList = managementResourceFunRepository.findAll();
        assertThat(managementResourceFunList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
