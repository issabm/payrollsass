package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.SituationFamiliale;
import com.issa.payroll.repository.SituationFamilialeRepository;
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
 * Integration tests for the {@link SituationFamilialeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SituationFamilialeResourceIT {

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

    private static final String ENTITY_API_URL = "/api/situation-familiales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SituationFamilialeRepository situationFamilialeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSituationFamilialeMockMvc;

    private SituationFamiliale situationFamiliale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SituationFamiliale createEntity(EntityManager em) {
        SituationFamiliale situationFamiliale = new SituationFamiliale()
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
        return situationFamiliale;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SituationFamiliale createUpdatedEntity(EntityManager em) {
        SituationFamiliale situationFamiliale = new SituationFamiliale()
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
        return situationFamiliale;
    }

    @BeforeEach
    public void initTest() {
        situationFamiliale = createEntity(em);
    }

    @Test
    @Transactional
    void createSituationFamiliale() throws Exception {
        int databaseSizeBeforeCreate = situationFamilialeRepository.findAll().size();
        // Create the SituationFamiliale
        restSituationFamilialeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(situationFamiliale))
            )
            .andExpect(status().isCreated());

        // Validate the SituationFamiliale in the database
        List<SituationFamiliale> situationFamilialeList = situationFamilialeRepository.findAll();
        assertThat(situationFamilialeList).hasSize(databaseSizeBeforeCreate + 1);
        SituationFamiliale testSituationFamiliale = situationFamilialeList.get(situationFamilialeList.size() - 1);
        assertThat(testSituationFamiliale.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSituationFamiliale.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testSituationFamiliale.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testSituationFamiliale.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testSituationFamiliale.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testSituationFamiliale.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSituationFamiliale.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testSituationFamiliale.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSituationFamiliale.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSituationFamiliale.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSituationFamilialeWithExistingId() throws Exception {
        // Create the SituationFamiliale with an existing ID
        situationFamiliale.setId(1L);

        int databaseSizeBeforeCreate = situationFamilialeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSituationFamilialeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(situationFamiliale))
            )
            .andExpect(status().isBadRequest());

        // Validate the SituationFamiliale in the database
        List<SituationFamiliale> situationFamilialeList = situationFamilialeRepository.findAll();
        assertThat(situationFamilialeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSituationFamiliales() throws Exception {
        // Initialize the database
        situationFamilialeRepository.saveAndFlush(situationFamiliale);

        // Get all the situationFamilialeList
        restSituationFamilialeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(situationFamiliale.getId().intValue())))
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
    void getSituationFamiliale() throws Exception {
        // Initialize the database
        situationFamilialeRepository.saveAndFlush(situationFamiliale);

        // Get the situationFamiliale
        restSituationFamilialeMockMvc
            .perform(get(ENTITY_API_URL_ID, situationFamiliale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(situationFamiliale.getId().intValue()))
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
    void getNonExistingSituationFamiliale() throws Exception {
        // Get the situationFamiliale
        restSituationFamilialeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSituationFamiliale() throws Exception {
        // Initialize the database
        situationFamilialeRepository.saveAndFlush(situationFamiliale);

        int databaseSizeBeforeUpdate = situationFamilialeRepository.findAll().size();

        // Update the situationFamiliale
        SituationFamiliale updatedSituationFamiliale = situationFamilialeRepository.findById(situationFamiliale.getId()).get();
        // Disconnect from session so that the updates on updatedSituationFamiliale are not directly saved in db
        em.detach(updatedSituationFamiliale);
        updatedSituationFamiliale
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

        restSituationFamilialeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSituationFamiliale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSituationFamiliale))
            )
            .andExpect(status().isOk());

        // Validate the SituationFamiliale in the database
        List<SituationFamiliale> situationFamilialeList = situationFamilialeRepository.findAll();
        assertThat(situationFamilialeList).hasSize(databaseSizeBeforeUpdate);
        SituationFamiliale testSituationFamiliale = situationFamilialeList.get(situationFamilialeList.size() - 1);
        assertThat(testSituationFamiliale.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSituationFamiliale.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testSituationFamiliale.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testSituationFamiliale.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSituationFamiliale.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSituationFamiliale.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSituationFamiliale.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSituationFamiliale.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSituationFamiliale.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSituationFamiliale.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSituationFamiliale() throws Exception {
        int databaseSizeBeforeUpdate = situationFamilialeRepository.findAll().size();
        situationFamiliale.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituationFamilialeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, situationFamiliale.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(situationFamiliale))
            )
            .andExpect(status().isBadRequest());

        // Validate the SituationFamiliale in the database
        List<SituationFamiliale> situationFamilialeList = situationFamilialeRepository.findAll();
        assertThat(situationFamilialeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSituationFamiliale() throws Exception {
        int databaseSizeBeforeUpdate = situationFamilialeRepository.findAll().size();
        situationFamiliale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituationFamilialeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(situationFamiliale))
            )
            .andExpect(status().isBadRequest());

        // Validate the SituationFamiliale in the database
        List<SituationFamiliale> situationFamilialeList = situationFamilialeRepository.findAll();
        assertThat(situationFamilialeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSituationFamiliale() throws Exception {
        int databaseSizeBeforeUpdate = situationFamilialeRepository.findAll().size();
        situationFamiliale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituationFamilialeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(situationFamiliale))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SituationFamiliale in the database
        List<SituationFamiliale> situationFamilialeList = situationFamilialeRepository.findAll();
        assertThat(situationFamilialeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSituationFamilialeWithPatch() throws Exception {
        // Initialize the database
        situationFamilialeRepository.saveAndFlush(situationFamiliale);

        int databaseSizeBeforeUpdate = situationFamilialeRepository.findAll().size();

        // Update the situationFamiliale using partial update
        SituationFamiliale partialUpdatedSituationFamiliale = new SituationFamiliale();
        partialUpdatedSituationFamiliale.setId(situationFamiliale.getId());

        partialUpdatedSituationFamiliale.modifiedBy(UPDATED_MODIFIED_BY).op(UPDATED_OP).createdDate(UPDATED_CREATED_DATE);

        restSituationFamilialeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSituationFamiliale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSituationFamiliale))
            )
            .andExpect(status().isOk());

        // Validate the SituationFamiliale in the database
        List<SituationFamiliale> situationFamilialeList = situationFamilialeRepository.findAll();
        assertThat(situationFamilialeList).hasSize(databaseSizeBeforeUpdate);
        SituationFamiliale testSituationFamiliale = situationFamilialeList.get(situationFamilialeList.size() - 1);
        assertThat(testSituationFamiliale.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSituationFamiliale.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testSituationFamiliale.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testSituationFamiliale.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testSituationFamiliale.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testSituationFamiliale.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSituationFamiliale.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSituationFamiliale.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSituationFamiliale.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSituationFamiliale.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSituationFamilialeWithPatch() throws Exception {
        // Initialize the database
        situationFamilialeRepository.saveAndFlush(situationFamiliale);

        int databaseSizeBeforeUpdate = situationFamilialeRepository.findAll().size();

        // Update the situationFamiliale using partial update
        SituationFamiliale partialUpdatedSituationFamiliale = new SituationFamiliale();
        partialUpdatedSituationFamiliale.setId(situationFamiliale.getId());

        partialUpdatedSituationFamiliale
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

        restSituationFamilialeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSituationFamiliale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSituationFamiliale))
            )
            .andExpect(status().isOk());

        // Validate the SituationFamiliale in the database
        List<SituationFamiliale> situationFamilialeList = situationFamilialeRepository.findAll();
        assertThat(situationFamilialeList).hasSize(databaseSizeBeforeUpdate);
        SituationFamiliale testSituationFamiliale = situationFamilialeList.get(situationFamilialeList.size() - 1);
        assertThat(testSituationFamiliale.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSituationFamiliale.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testSituationFamiliale.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testSituationFamiliale.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSituationFamiliale.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSituationFamiliale.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSituationFamiliale.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSituationFamiliale.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSituationFamiliale.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSituationFamiliale.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSituationFamiliale() throws Exception {
        int databaseSizeBeforeUpdate = situationFamilialeRepository.findAll().size();
        situationFamiliale.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSituationFamilialeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, situationFamiliale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(situationFamiliale))
            )
            .andExpect(status().isBadRequest());

        // Validate the SituationFamiliale in the database
        List<SituationFamiliale> situationFamilialeList = situationFamilialeRepository.findAll();
        assertThat(situationFamilialeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSituationFamiliale() throws Exception {
        int databaseSizeBeforeUpdate = situationFamilialeRepository.findAll().size();
        situationFamiliale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituationFamilialeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(situationFamiliale))
            )
            .andExpect(status().isBadRequest());

        // Validate the SituationFamiliale in the database
        List<SituationFamiliale> situationFamilialeList = situationFamilialeRepository.findAll();
        assertThat(situationFamilialeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSituationFamiliale() throws Exception {
        int databaseSizeBeforeUpdate = situationFamilialeRepository.findAll().size();
        situationFamiliale.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSituationFamilialeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(situationFamiliale))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SituationFamiliale in the database
        List<SituationFamiliale> situationFamilialeList = situationFamilialeRepository.findAll();
        assertThat(situationFamilialeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSituationFamiliale() throws Exception {
        // Initialize the database
        situationFamilialeRepository.saveAndFlush(situationFamiliale);

        int databaseSizeBeforeDelete = situationFamilialeRepository.findAll().size();

        // Delete the situationFamiliale
        restSituationFamilialeMockMvc
            .perform(delete(ENTITY_API_URL_ID, situationFamiliale.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SituationFamiliale> situationFamilialeList = situationFamilialeRepository.findAll();
        assertThat(situationFamilialeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
