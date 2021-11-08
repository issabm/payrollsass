package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.ModeInput;
import com.issa.payroll.repository.ModeInputRepository;
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
 * Integration tests for the {@link ModeInputResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ModeInputResourceIT {

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

    private static final String ENTITY_API_URL = "/api/mode-inputs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ModeInputRepository modeInputRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restModeInputMockMvc;

    private ModeInput modeInput;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModeInput createEntity(EntityManager em) {
        ModeInput modeInput = new ModeInput()
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
        return modeInput;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ModeInput createUpdatedEntity(EntityManager em) {
        ModeInput modeInput = new ModeInput()
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
        return modeInput;
    }

    @BeforeEach
    public void initTest() {
        modeInput = createEntity(em);
    }

    @Test
    @Transactional
    void createModeInput() throws Exception {
        int databaseSizeBeforeCreate = modeInputRepository.findAll().size();
        // Create the ModeInput
        restModeInputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modeInput)))
            .andExpect(status().isCreated());

        // Validate the ModeInput in the database
        List<ModeInput> modeInputList = modeInputRepository.findAll();
        assertThat(modeInputList).hasSize(databaseSizeBeforeCreate + 1);
        ModeInput testModeInput = modeInputList.get(modeInputList.size() - 1);
        assertThat(testModeInput.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testModeInput.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testModeInput.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testModeInput.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testModeInput.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testModeInput.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testModeInput.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testModeInput.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testModeInput.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testModeInput.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createModeInputWithExistingId() throws Exception {
        // Create the ModeInput with an existing ID
        modeInput.setId(1L);

        int databaseSizeBeforeCreate = modeInputRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restModeInputMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modeInput)))
            .andExpect(status().isBadRequest());

        // Validate the ModeInput in the database
        List<ModeInput> modeInputList = modeInputRepository.findAll();
        assertThat(modeInputList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllModeInputs() throws Exception {
        // Initialize the database
        modeInputRepository.saveAndFlush(modeInput);

        // Get all the modeInputList
        restModeInputMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(modeInput.getId().intValue())))
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
    void getModeInput() throws Exception {
        // Initialize the database
        modeInputRepository.saveAndFlush(modeInput);

        // Get the modeInput
        restModeInputMockMvc
            .perform(get(ENTITY_API_URL_ID, modeInput.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(modeInput.getId().intValue()))
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
    void getNonExistingModeInput() throws Exception {
        // Get the modeInput
        restModeInputMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewModeInput() throws Exception {
        // Initialize the database
        modeInputRepository.saveAndFlush(modeInput);

        int databaseSizeBeforeUpdate = modeInputRepository.findAll().size();

        // Update the modeInput
        ModeInput updatedModeInput = modeInputRepository.findById(modeInput.getId()).get();
        // Disconnect from session so that the updates on updatedModeInput are not directly saved in db
        em.detach(updatedModeInput);
        updatedModeInput
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

        restModeInputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedModeInput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedModeInput))
            )
            .andExpect(status().isOk());

        // Validate the ModeInput in the database
        List<ModeInput> modeInputList = modeInputRepository.findAll();
        assertThat(modeInputList).hasSize(databaseSizeBeforeUpdate);
        ModeInput testModeInput = modeInputList.get(modeInputList.size() - 1);
        assertThat(testModeInput.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModeInput.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testModeInput.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testModeInput.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testModeInput.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testModeInput.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testModeInput.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testModeInput.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testModeInput.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testModeInput.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingModeInput() throws Exception {
        int databaseSizeBeforeUpdate = modeInputRepository.findAll().size();
        modeInput.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModeInputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, modeInput.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modeInput))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModeInput in the database
        List<ModeInput> modeInputList = modeInputRepository.findAll();
        assertThat(modeInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchModeInput() throws Exception {
        int databaseSizeBeforeUpdate = modeInputRepository.findAll().size();
        modeInput.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModeInputMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(modeInput))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModeInput in the database
        List<ModeInput> modeInputList = modeInputRepository.findAll();
        assertThat(modeInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamModeInput() throws Exception {
        int databaseSizeBeforeUpdate = modeInputRepository.findAll().size();
        modeInput.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModeInputMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(modeInput)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModeInput in the database
        List<ModeInput> modeInputList = modeInputRepository.findAll();
        assertThat(modeInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateModeInputWithPatch() throws Exception {
        // Initialize the database
        modeInputRepository.saveAndFlush(modeInput);

        int databaseSizeBeforeUpdate = modeInputRepository.findAll().size();

        // Update the modeInput using partial update
        ModeInput partialUpdatedModeInput = new ModeInput();
        partialUpdatedModeInput.setId(modeInput.getId());

        partialUpdatedModeInput.libAr(UPDATED_LIB_AR);

        restModeInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModeInput.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModeInput))
            )
            .andExpect(status().isOk());

        // Validate the ModeInput in the database
        List<ModeInput> modeInputList = modeInputRepository.findAll();
        assertThat(modeInputList).hasSize(databaseSizeBeforeUpdate);
        ModeInput testModeInput = modeInputList.get(modeInputList.size() - 1);
        assertThat(testModeInput.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testModeInput.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testModeInput.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testModeInput.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testModeInput.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testModeInput.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testModeInput.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testModeInput.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testModeInput.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testModeInput.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateModeInputWithPatch() throws Exception {
        // Initialize the database
        modeInputRepository.saveAndFlush(modeInput);

        int databaseSizeBeforeUpdate = modeInputRepository.findAll().size();

        // Update the modeInput using partial update
        ModeInput partialUpdatedModeInput = new ModeInput();
        partialUpdatedModeInput.setId(modeInput.getId());

        partialUpdatedModeInput
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

        restModeInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedModeInput.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedModeInput))
            )
            .andExpect(status().isOk());

        // Validate the ModeInput in the database
        List<ModeInput> modeInputList = modeInputRepository.findAll();
        assertThat(modeInputList).hasSize(databaseSizeBeforeUpdate);
        ModeInput testModeInput = modeInputList.get(modeInputList.size() - 1);
        assertThat(testModeInput.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testModeInput.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testModeInput.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testModeInput.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testModeInput.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testModeInput.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testModeInput.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testModeInput.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testModeInput.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testModeInput.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingModeInput() throws Exception {
        int databaseSizeBeforeUpdate = modeInputRepository.findAll().size();
        modeInput.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restModeInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, modeInput.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modeInput))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModeInput in the database
        List<ModeInput> modeInputList = modeInputRepository.findAll();
        assertThat(modeInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchModeInput() throws Exception {
        int databaseSizeBeforeUpdate = modeInputRepository.findAll().size();
        modeInput.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModeInputMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(modeInput))
            )
            .andExpect(status().isBadRequest());

        // Validate the ModeInput in the database
        List<ModeInput> modeInputList = modeInputRepository.findAll();
        assertThat(modeInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamModeInput() throws Exception {
        int databaseSizeBeforeUpdate = modeInputRepository.findAll().size();
        modeInput.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restModeInputMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(modeInput))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ModeInput in the database
        List<ModeInput> modeInputList = modeInputRepository.findAll();
        assertThat(modeInputList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteModeInput() throws Exception {
        // Initialize the database
        modeInputRepository.saveAndFlush(modeInput);

        int databaseSizeBeforeDelete = modeInputRepository.findAll().size();

        // Delete the modeInput
        restModeInputMockMvc
            .perform(delete(ENTITY_API_URL_ID, modeInput.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ModeInput> modeInputList = modeInputRepository.findAll();
        assertThat(modeInputList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
