package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Situation;
import com.issa.payroll.repository.SituationRepository;
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
 * Integration tests for the {@link SituationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SituationResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ENTITY_TARGET = "AAAAAAAAAA";
    private static final String UPDATED_ENTITY_TARGET = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/situations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SituationRepository situationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSituationMockMvc;

    private Situation situation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Situation createEntity(EntityManager em) {
        Situation situation = new Situation()
            .code(DEFAULT_CODE)
            .entityTarget(DEFAULT_ENTITY_TARGET)
            .libAr(DEFAULT_LIB_AR)
            .libEn(DEFAULT_LIB_EN)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return situation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Situation createUpdatedEntity(EntityManager em) {
        Situation situation = new Situation()
            .code(UPDATED_CODE)
            .entityTarget(UPDATED_ENTITY_TARGET)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return situation;
    }

    @BeforeEach
    public void initTest() {
        situation = createEntity(em);
    }

    @Test
    @Transactional
    void createSituation() throws Exception {
        int databaseSizeBeforeCreate = situationRepository.findAll().size();
        // Create the Situation
        restSituationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(situation)))
            .andExpect(status().isCreated());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeCreate + 1);
        Situation testSituation = situationList.get(situationList.size() - 1);
        assertThat(testSituation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSituation.getEntityTarget()).isEqualTo(DEFAULT_ENTITY_TARGET);
        assertThat(testSituation.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testSituation.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testSituation.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testSituation.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testSituation.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSituation.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testSituation.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSituation.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSituation.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSituationWithExistingId() throws Exception {
        // Create the Situation with an existing ID
        situation.setId(1L);

        int databaseSizeBeforeCreate = situationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSituationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(situation)))
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSituations() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        // Get all the situationList
        restSituationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].entityTarget").value(hasItem(DEFAULT_ENTITY_TARGET)))
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
    void getSituation() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        // Get the situation
        restSituationMockMvc
            .perform(get(ENTITY_API_URL_ID, situation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(situation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.entityTarget").value(DEFAULT_ENTITY_TARGET))
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
    void getNonExistingSituation() throws Exception {
        // Get the situation
        restSituationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSituation() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        int databaseSizeBeforeUpdate = situationRepository.findAll().size();

        // Update the situation
        Situation updatedSituation = situationRepository.findById(situation.getId()).get();
        // Disconnect from session so that the updates on updatedSituation are not directly saved in db
        em.detach(updatedSituation);
        updatedSituation
            .code(UPDATED_CODE)
            .entityTarget(UPDATED_ENTITY_TARGET)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restSituationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSituation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSituation))
            )
            .andExpect(status().isOk());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
        Situation testSituation = situationList.get(situationList.size() - 1);
        assertThat(testSituation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSituation.getEntityTarget()).isEqualTo(UPDATED_ENTITY_TARGET);
        assertThat(testSituation.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testSituation.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testSituation.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSituation.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSituation.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSituation.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSituation.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSituation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSituation.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSituation() throws Exception {
        int databaseSizeBeforeUpdate = situationRepository.findAll().size();
        situation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, situation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(situation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSituation() throws Exception {
        int databaseSizeBeforeUpdate = situationRepository.findAll().size();
        situation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(situation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSituation() throws Exception {
        int databaseSizeBeforeUpdate = situationRepository.findAll().size();
        situation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(situation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSituationWithPatch() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        int databaseSizeBeforeUpdate = situationRepository.findAll().size();

        // Update the situation using partial update
        Situation partialUpdatedSituation = new Situation();
        partialUpdatedSituation.setId(situation.getId());

        partialUpdatedSituation
            .entityTarget(UPDATED_ENTITY_TARGET)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restSituationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSituation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSituation))
            )
            .andExpect(status().isOk());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
        Situation testSituation = situationList.get(situationList.size() - 1);
        assertThat(testSituation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSituation.getEntityTarget()).isEqualTo(UPDATED_ENTITY_TARGET);
        assertThat(testSituation.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testSituation.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testSituation.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSituation.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSituation.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSituation.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testSituation.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSituation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSituation.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSituationWithPatch() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        int databaseSizeBeforeUpdate = situationRepository.findAll().size();

        // Update the situation using partial update
        Situation partialUpdatedSituation = new Situation();
        partialUpdatedSituation.setId(situation.getId());

        partialUpdatedSituation
            .code(UPDATED_CODE)
            .entityTarget(UPDATED_ENTITY_TARGET)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restSituationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSituation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSituation))
            )
            .andExpect(status().isOk());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
        Situation testSituation = situationList.get(situationList.size() - 1);
        assertThat(testSituation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSituation.getEntityTarget()).isEqualTo(UPDATED_ENTITY_TARGET);
        assertThat(testSituation.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testSituation.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testSituation.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSituation.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSituation.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSituation.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSituation.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSituation.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSituation.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSituation() throws Exception {
        int databaseSizeBeforeUpdate = situationRepository.findAll().size();
        situation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, situation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(situation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSituation() throws Exception {
        int databaseSizeBeforeUpdate = situationRepository.findAll().size();
        situation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(situation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSituation() throws Exception {
        int databaseSizeBeforeUpdate = situationRepository.findAll().size();
        situation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(situation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Situation in the database
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSituation() throws Exception {
        // Initialize the database
        situationRepository.saveAndFlush(situation);

        int databaseSizeBeforeDelete = situationRepository.findAll().size();

        // Delete the situation
        restSituationMockMvc
            .perform(delete(ENTITY_API_URL_ID, situation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Situation> situationList = situationRepository.findAll();
        assertThat(situationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
