package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.PalierCondition;
import com.issa.payroll.repository.PalierConditionRepository;
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
 * Integration tests for the {@link PalierConditionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PalierConditionResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final Double DEFAULT_MIN_VAL = 1D;
    private static final Double UPDATED_MIN_VAL = 2D;

    private static final Double DEFAULT_MAX_VAL = 1D;
    private static final Double UPDATED_MAX_VAL = 2D;

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATEOP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_MODIF = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIF = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_OP = "AAAAAAAAAA";
    private static final String UPDATED_OP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final String ENTITY_API_URL = "/api/palier-conditions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PalierConditionRepository palierConditionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPalierConditionMockMvc;

    private PalierCondition palierCondition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PalierCondition createEntity(EntityManager em) {
        PalierCondition palierCondition = new PalierCondition()
            .code(DEFAULT_CODE)
            .libEn(DEFAULT_LIB_EN)
            .libAr(DEFAULT_LIB_AR)
            .annee(DEFAULT_ANNEE)
            .minVal(DEFAULT_MIN_VAL)
            .maxVal(DEFAULT_MAX_VAL)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .dateModif(DEFAULT_DATE_MODIF)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED);
        return palierCondition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PalierCondition createUpdatedEntity(EntityManager em) {
        PalierCondition palierCondition = new PalierCondition()
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .annee(UPDATED_ANNEE)
            .minVal(UPDATED_MIN_VAL)
            .maxVal(UPDATED_MAX_VAL)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .dateModif(UPDATED_DATE_MODIF)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);
        return palierCondition;
    }

    @BeforeEach
    public void initTest() {
        palierCondition = createEntity(em);
    }

    @Test
    @Transactional
    void createPalierCondition() throws Exception {
        int databaseSizeBeforeCreate = palierConditionRepository.findAll().size();
        // Create the PalierCondition
        restPalierConditionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(palierCondition))
            )
            .andExpect(status().isCreated());

        // Validate the PalierCondition in the database
        List<PalierCondition> palierConditionList = palierConditionRepository.findAll();
        assertThat(palierConditionList).hasSize(databaseSizeBeforeCreate + 1);
        PalierCondition testPalierCondition = palierConditionList.get(palierConditionList.size() - 1);
        assertThat(testPalierCondition.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPalierCondition.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testPalierCondition.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testPalierCondition.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testPalierCondition.getMinVal()).isEqualTo(DEFAULT_MIN_VAL);
        assertThat(testPalierCondition.getMaxVal()).isEqualTo(DEFAULT_MAX_VAL);
        assertThat(testPalierCondition.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testPalierCondition.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testPalierCondition.getDateModif()).isEqualTo(DEFAULT_DATE_MODIF);
        assertThat(testPalierCondition.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPalierCondition.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testPalierCondition.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createPalierConditionWithExistingId() throws Exception {
        // Create the PalierCondition with an existing ID
        palierCondition.setId(1L);

        int databaseSizeBeforeCreate = palierConditionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPalierConditionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(palierCondition))
            )
            .andExpect(status().isBadRequest());

        // Validate the PalierCondition in the database
        List<PalierCondition> palierConditionList = palierConditionRepository.findAll();
        assertThat(palierConditionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPalierConditions() throws Exception {
        // Initialize the database
        palierConditionRepository.saveAndFlush(palierCondition);

        // Get all the palierConditionList
        restPalierConditionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(palierCondition.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].minVal").value(hasItem(DEFAULT_MIN_VAL.doubleValue())))
            .andExpect(jsonPath("$.[*].maxVal").value(hasItem(DEFAULT_MAX_VAL.doubleValue())))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].dateModif").value(hasItem(sameInstant(DEFAULT_DATE_MODIF))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getPalierCondition() throws Exception {
        // Initialize the database
        palierConditionRepository.saveAndFlush(palierCondition);

        // Get the palierCondition
        restPalierConditionMockMvc
            .perform(get(ENTITY_API_URL_ID, palierCondition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(palierCondition.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.minVal").value(DEFAULT_MIN_VAL.doubleValue()))
            .andExpect(jsonPath("$.maxVal").value(DEFAULT_MAX_VAL.doubleValue()))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.dateModif").value(sameInstant(DEFAULT_DATE_MODIF)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPalierCondition() throws Exception {
        // Get the palierCondition
        restPalierConditionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPalierCondition() throws Exception {
        // Initialize the database
        palierConditionRepository.saveAndFlush(palierCondition);

        int databaseSizeBeforeUpdate = palierConditionRepository.findAll().size();

        // Update the palierCondition
        PalierCondition updatedPalierCondition = palierConditionRepository.findById(palierCondition.getId()).get();
        // Disconnect from session so that the updates on updatedPalierCondition are not directly saved in db
        em.detach(updatedPalierCondition);
        updatedPalierCondition
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .annee(UPDATED_ANNEE)
            .minVal(UPDATED_MIN_VAL)
            .maxVal(UPDATED_MAX_VAL)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .dateModif(UPDATED_DATE_MODIF)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restPalierConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPalierCondition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPalierCondition))
            )
            .andExpect(status().isOk());

        // Validate the PalierCondition in the database
        List<PalierCondition> palierConditionList = palierConditionRepository.findAll();
        assertThat(palierConditionList).hasSize(databaseSizeBeforeUpdate);
        PalierCondition testPalierCondition = palierConditionList.get(palierConditionList.size() - 1);
        assertThat(testPalierCondition.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPalierCondition.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testPalierCondition.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testPalierCondition.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testPalierCondition.getMinVal()).isEqualTo(UPDATED_MIN_VAL);
        assertThat(testPalierCondition.getMaxVal()).isEqualTo(UPDATED_MAX_VAL);
        assertThat(testPalierCondition.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPalierCondition.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testPalierCondition.getDateModif()).isEqualTo(UPDATED_DATE_MODIF);
        assertThat(testPalierCondition.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPalierCondition.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testPalierCondition.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingPalierCondition() throws Exception {
        int databaseSizeBeforeUpdate = palierConditionRepository.findAll().size();
        palierCondition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPalierConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, palierCondition.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(palierCondition))
            )
            .andExpect(status().isBadRequest());

        // Validate the PalierCondition in the database
        List<PalierCondition> palierConditionList = palierConditionRepository.findAll();
        assertThat(palierConditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPalierCondition() throws Exception {
        int databaseSizeBeforeUpdate = palierConditionRepository.findAll().size();
        palierCondition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalierConditionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(palierCondition))
            )
            .andExpect(status().isBadRequest());

        // Validate the PalierCondition in the database
        List<PalierCondition> palierConditionList = palierConditionRepository.findAll();
        assertThat(palierConditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPalierCondition() throws Exception {
        int databaseSizeBeforeUpdate = palierConditionRepository.findAll().size();
        palierCondition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalierConditionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(palierCondition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PalierCondition in the database
        List<PalierCondition> palierConditionList = palierConditionRepository.findAll();
        assertThat(palierConditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePalierConditionWithPatch() throws Exception {
        // Initialize the database
        palierConditionRepository.saveAndFlush(palierCondition);

        int databaseSizeBeforeUpdate = palierConditionRepository.findAll().size();

        // Update the palierCondition using partial update
        PalierCondition partialUpdatedPalierCondition = new PalierCondition();
        partialUpdatedPalierCondition.setId(palierCondition.getId());

        partialUpdatedPalierCondition
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .util(UPDATED_UTIL)
            .dateModif(UPDATED_DATE_MODIF)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED);

        restPalierConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPalierCondition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPalierCondition))
            )
            .andExpect(status().isOk());

        // Validate the PalierCondition in the database
        List<PalierCondition> palierConditionList = palierConditionRepository.findAll();
        assertThat(palierConditionList).hasSize(databaseSizeBeforeUpdate);
        PalierCondition testPalierCondition = palierConditionList.get(palierConditionList.size() - 1);
        assertThat(testPalierCondition.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPalierCondition.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testPalierCondition.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testPalierCondition.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testPalierCondition.getMinVal()).isEqualTo(DEFAULT_MIN_VAL);
        assertThat(testPalierCondition.getMaxVal()).isEqualTo(DEFAULT_MAX_VAL);
        assertThat(testPalierCondition.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPalierCondition.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testPalierCondition.getDateModif()).isEqualTo(UPDATED_DATE_MODIF);
        assertThat(testPalierCondition.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPalierCondition.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testPalierCondition.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdatePalierConditionWithPatch() throws Exception {
        // Initialize the database
        palierConditionRepository.saveAndFlush(palierCondition);

        int databaseSizeBeforeUpdate = palierConditionRepository.findAll().size();

        // Update the palierCondition using partial update
        PalierCondition partialUpdatedPalierCondition = new PalierCondition();
        partialUpdatedPalierCondition.setId(palierCondition.getId());

        partialUpdatedPalierCondition
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .annee(UPDATED_ANNEE)
            .minVal(UPDATED_MIN_VAL)
            .maxVal(UPDATED_MAX_VAL)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .dateModif(UPDATED_DATE_MODIF)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restPalierConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPalierCondition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPalierCondition))
            )
            .andExpect(status().isOk());

        // Validate the PalierCondition in the database
        List<PalierCondition> palierConditionList = palierConditionRepository.findAll();
        assertThat(palierConditionList).hasSize(databaseSizeBeforeUpdate);
        PalierCondition testPalierCondition = palierConditionList.get(palierConditionList.size() - 1);
        assertThat(testPalierCondition.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPalierCondition.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testPalierCondition.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testPalierCondition.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testPalierCondition.getMinVal()).isEqualTo(UPDATED_MIN_VAL);
        assertThat(testPalierCondition.getMaxVal()).isEqualTo(UPDATED_MAX_VAL);
        assertThat(testPalierCondition.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPalierCondition.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testPalierCondition.getDateModif()).isEqualTo(UPDATED_DATE_MODIF);
        assertThat(testPalierCondition.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPalierCondition.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testPalierCondition.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingPalierCondition() throws Exception {
        int databaseSizeBeforeUpdate = palierConditionRepository.findAll().size();
        palierCondition.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPalierConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, palierCondition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(palierCondition))
            )
            .andExpect(status().isBadRequest());

        // Validate the PalierCondition in the database
        List<PalierCondition> palierConditionList = palierConditionRepository.findAll();
        assertThat(palierConditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPalierCondition() throws Exception {
        int databaseSizeBeforeUpdate = palierConditionRepository.findAll().size();
        palierCondition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalierConditionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(palierCondition))
            )
            .andExpect(status().isBadRequest());

        // Validate the PalierCondition in the database
        List<PalierCondition> palierConditionList = palierConditionRepository.findAll();
        assertThat(palierConditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPalierCondition() throws Exception {
        int databaseSizeBeforeUpdate = palierConditionRepository.findAll().size();
        palierCondition.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalierConditionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(palierCondition))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PalierCondition in the database
        List<PalierCondition> palierConditionList = palierConditionRepository.findAll();
        assertThat(palierConditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePalierCondition() throws Exception {
        // Initialize the database
        palierConditionRepository.saveAndFlush(palierCondition);

        int databaseSizeBeforeDelete = palierConditionRepository.findAll().size();

        // Delete the palierCondition
        restPalierConditionMockMvc
            .perform(delete(ENTITY_API_URL_ID, palierCondition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PalierCondition> palierConditionList = palierConditionRepository.findAll();
        assertThat(palierConditionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
