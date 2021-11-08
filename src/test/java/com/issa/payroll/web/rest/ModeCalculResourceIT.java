package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.ModeCalcul;
import com.issa.payroll.repository.ModeCalculRepository;
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
 * Integration tests for the {@link ModeCalculResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ModeCalculResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATEOP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_OP = "AAAAAAAAAA";
    private static final String UPDATED_OP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/mode-calculs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ModeCalculRepository modeCalculRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModeCalculMockMvc;

    private ModeCalcul modeCalcul;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModeCalcul createEntity(EntityManager em) {
        ModeCalcul modeCalcul = new ModeCalcul()
            .code(DEFAULT_CODE)
            .libAr(DEFAULT_LIB_AR)
            .libEn(DEFAULT_LIB_EN)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return modeCalcul;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModeCalcul createUpdatedEntity(EntityManager em) {
        ModeCalcul modeCalcul = new ModeCalcul()
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return modeCalcul;
    }

    @BeforeEach
    public void initTest() {
        modeCalcul = createEntity(em);
    }

    @Test
    @Transactional
    void createModeCalcul() throws Exception {
        int databaseSizeBeforeCreate = modeCalculRepository.findAll().size();
        // Create the ModeCalcul
        restModeCalculMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modeCalcul)))
            .andExpect(status().isCreated());

        // Validate the ModeCalcul in the database
        List<ModeCalcul> modeCalculList = modeCalculRepository.findAll();
        assertThat(modeCalculList).hasSize(databaseSizeBeforeCreate + 1);
        ModeCalcul testModeCalcul = modeCalculList.get(modeCalculList.size() - 1);
        assertThat(testModeCalcul.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testModeCalcul.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testModeCalcul.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testModeCalcul.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testModeCalcul.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testModeCalcul.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testModeCalcul.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testModeCalcul.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testModeCalcul.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testModeCalcul.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createModeCalculWithExistingId() throws Exception {
        // Create the ModeCalcul with an existing ID
        modeCalcul.setId(1L);

        int databaseSizeBeforeCreate = modeCalculRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModeCalculMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modeCalcul)))
            .andExpect(status().isBadRequest());

        // Validate the ModeCalcul in the database
        List<ModeCalcul> modeCalculList = modeCalculRepository.findAll();
        assertThat(modeCalculList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllModeCalculs() throws Exception {
        // Initialize the database
        modeCalculRepository.saveAndFlush(modeCalcul);

        // Get all the modeCalculList
        restModeCalculMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modeCalcul.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    void getModeCalcul() throws Exception {
        // Initialize the database
        modeCalculRepository.saveAndFlush(modeCalcul);

        // Get the modeCalcul
        restModeCalculMockMvc
            .perform(get(ENTITY_API_URL_ID, modeCalcul.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modeCalcul.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingModeCalcul() throws Exception {
        // Get the modeCalcul
        restModeCalculMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewModeCalcul() throws Exception {
        // Initialize the database
        modeCalculRepository.saveAndFlush(modeCalcul);

        int databaseSizeBeforeUpdate = modeCalculRepository.findAll().size();

        // Update the modeCalcul
        ModeCalcul updatedModeCalcul = modeCalculRepository.findById(modeCalcul.getId()).get();
        // Disconnect from session so that the updates on updatedModeCalcul are not directly saved in db
        em.detach(updatedModeCalcul);
        updatedModeCalcul
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restModeCalculMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedModeCalcul.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedModeCalcul))
            )
            .andExpect(status().isOk());

        // Validate the ModeCalcul in the database
        List<ModeCalcul> modeCalculList = modeCalculRepository.findAll();
        assertThat(modeCalculList).hasSize(databaseSizeBeforeUpdate);
        ModeCalcul testModeCalcul = modeCalculList.get(modeCalculList.size() - 1);
        assertThat(testModeCalcul.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModeCalcul.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testModeCalcul.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testModeCalcul.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testModeCalcul.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testModeCalcul.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testModeCalcul.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testModeCalcul.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testModeCalcul.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testModeCalcul.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingModeCalcul() throws Exception {
        int databaseSizeBeforeUpdate = modeCalculRepository.findAll().size();
        modeCalcul.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModeCalculMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modeCalcul.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modeCalcul))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModeCalcul in the database
        List<ModeCalcul> modeCalculList = modeCalculRepository.findAll();
        assertThat(modeCalculList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModeCalcul() throws Exception {
        int databaseSizeBeforeUpdate = modeCalculRepository.findAll().size();
        modeCalcul.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModeCalculMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modeCalcul))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModeCalcul in the database
        List<ModeCalcul> modeCalculList = modeCalculRepository.findAll();
        assertThat(modeCalculList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModeCalcul() throws Exception {
        int databaseSizeBeforeUpdate = modeCalculRepository.findAll().size();
        modeCalcul.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModeCalculMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modeCalcul)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModeCalcul in the database
        List<ModeCalcul> modeCalculList = modeCalculRepository.findAll();
        assertThat(modeCalculList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModeCalculWithPatch() throws Exception {
        // Initialize the database
        modeCalculRepository.saveAndFlush(modeCalcul);

        int databaseSizeBeforeUpdate = modeCalculRepository.findAll().size();

        // Update the modeCalcul using partial update
        ModeCalcul partialUpdatedModeCalcul = new ModeCalcul();
        partialUpdatedModeCalcul.setId(modeCalcul.getId());

        partialUpdatedModeCalcul
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .isDeleted(UPDATED_IS_DELETED)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restModeCalculMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModeCalcul.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModeCalcul))
            )
            .andExpect(status().isOk());

        // Validate the ModeCalcul in the database
        List<ModeCalcul> modeCalculList = modeCalculRepository.findAll();
        assertThat(modeCalculList).hasSize(databaseSizeBeforeUpdate);
        ModeCalcul testModeCalcul = modeCalculList.get(modeCalculList.size() - 1);
        assertThat(testModeCalcul.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testModeCalcul.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testModeCalcul.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testModeCalcul.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testModeCalcul.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testModeCalcul.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testModeCalcul.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testModeCalcul.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testModeCalcul.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testModeCalcul.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateModeCalculWithPatch() throws Exception {
        // Initialize the database
        modeCalculRepository.saveAndFlush(modeCalcul);

        int databaseSizeBeforeUpdate = modeCalculRepository.findAll().size();

        // Update the modeCalcul using partial update
        ModeCalcul partialUpdatedModeCalcul = new ModeCalcul();
        partialUpdatedModeCalcul.setId(modeCalcul.getId());

        partialUpdatedModeCalcul
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restModeCalculMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModeCalcul.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModeCalcul))
            )
            .andExpect(status().isOk());

        // Validate the ModeCalcul in the database
        List<ModeCalcul> modeCalculList = modeCalculRepository.findAll();
        assertThat(modeCalculList).hasSize(databaseSizeBeforeUpdate);
        ModeCalcul testModeCalcul = modeCalculList.get(modeCalculList.size() - 1);
        assertThat(testModeCalcul.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModeCalcul.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testModeCalcul.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testModeCalcul.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testModeCalcul.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testModeCalcul.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testModeCalcul.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testModeCalcul.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testModeCalcul.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testModeCalcul.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingModeCalcul() throws Exception {
        int databaseSizeBeforeUpdate = modeCalculRepository.findAll().size();
        modeCalcul.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModeCalculMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, modeCalcul.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modeCalcul))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModeCalcul in the database
        List<ModeCalcul> modeCalculList = modeCalculRepository.findAll();
        assertThat(modeCalculList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModeCalcul() throws Exception {
        int databaseSizeBeforeUpdate = modeCalculRepository.findAll().size();
        modeCalcul.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModeCalculMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modeCalcul))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModeCalcul in the database
        List<ModeCalcul> modeCalculList = modeCalculRepository.findAll();
        assertThat(modeCalculList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModeCalcul() throws Exception {
        int databaseSizeBeforeUpdate = modeCalculRepository.findAll().size();
        modeCalcul.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModeCalculMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(modeCalcul))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModeCalcul in the database
        List<ModeCalcul> modeCalculList = modeCalculRepository.findAll();
        assertThat(modeCalculList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModeCalcul() throws Exception {
        // Initialize the database
        modeCalculRepository.saveAndFlush(modeCalcul);

        int databaseSizeBeforeDelete = modeCalculRepository.findAll().size();

        // Delete the modeCalcul
        restModeCalculMockMvc
            .perform(delete(ENTITY_API_URL_ID, modeCalcul.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModeCalcul> modeCalculList = modeCalculRepository.findAll();
        assertThat(modeCalculList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
