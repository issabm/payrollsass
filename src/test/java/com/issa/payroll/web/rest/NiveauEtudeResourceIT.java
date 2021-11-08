package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.NiveauEtude;
import com.issa.payroll.repository.NiveauEtudeRepository;
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
 * Integration tests for the {@link NiveauEtudeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NiveauEtudeResourceIT {

    private static final Integer DEFAULT_ORDER_LEVEL = 1;
    private static final Integer UPDATED_ORDER_LEVEL = 2;

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

    private static final String ENTITY_API_URL = "/api/niveau-etudes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NiveauEtudeRepository niveauEtudeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNiveauEtudeMockMvc;

    private NiveauEtude niveauEtude;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NiveauEtude createEntity(EntityManager em) {
        NiveauEtude niveauEtude = new NiveauEtude()
            .orderLevel(DEFAULT_ORDER_LEVEL)
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
        return niveauEtude;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NiveauEtude createUpdatedEntity(EntityManager em) {
        NiveauEtude niveauEtude = new NiveauEtude()
            .orderLevel(UPDATED_ORDER_LEVEL)
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
        return niveauEtude;
    }

    @BeforeEach
    public void initTest() {
        niveauEtude = createEntity(em);
    }

    @Test
    @Transactional
    void createNiveauEtude() throws Exception {
        int databaseSizeBeforeCreate = niveauEtudeRepository.findAll().size();
        // Create the NiveauEtude
        restNiveauEtudeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(niveauEtude)))
            .andExpect(status().isCreated());

        // Validate the NiveauEtude in the database
        List<NiveauEtude> niveauEtudeList = niveauEtudeRepository.findAll();
        assertThat(niveauEtudeList).hasSize(databaseSizeBeforeCreate + 1);
        NiveauEtude testNiveauEtude = niveauEtudeList.get(niveauEtudeList.size() - 1);
        assertThat(testNiveauEtude.getOrderLevel()).isEqualTo(DEFAULT_ORDER_LEVEL);
        assertThat(testNiveauEtude.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNiveauEtude.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testNiveauEtude.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testNiveauEtude.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testNiveauEtude.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testNiveauEtude.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testNiveauEtude.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testNiveauEtude.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testNiveauEtude.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNiveauEtude.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createNiveauEtudeWithExistingId() throws Exception {
        // Create the NiveauEtude with an existing ID
        niveauEtude.setId(1L);

        int databaseSizeBeforeCreate = niveauEtudeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNiveauEtudeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(niveauEtude)))
            .andExpect(status().isBadRequest());

        // Validate the NiveauEtude in the database
        List<NiveauEtude> niveauEtudeList = niveauEtudeRepository.findAll();
        assertThat(niveauEtudeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNiveauEtudes() throws Exception {
        // Initialize the database
        niveauEtudeRepository.saveAndFlush(niveauEtude);

        // Get all the niveauEtudeList
        restNiveauEtudeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niveauEtude.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderLevel").value(hasItem(DEFAULT_ORDER_LEVEL)))
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
    void getNiveauEtude() throws Exception {
        // Initialize the database
        niveauEtudeRepository.saveAndFlush(niveauEtude);

        // Get the niveauEtude
        restNiveauEtudeMockMvc
            .perform(get(ENTITY_API_URL_ID, niveauEtude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(niveauEtude.getId().intValue()))
            .andExpect(jsonPath("$.orderLevel").value(DEFAULT_ORDER_LEVEL))
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
    void getNonExistingNiveauEtude() throws Exception {
        // Get the niveauEtude
        restNiveauEtudeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNiveauEtude() throws Exception {
        // Initialize the database
        niveauEtudeRepository.saveAndFlush(niveauEtude);

        int databaseSizeBeforeUpdate = niveauEtudeRepository.findAll().size();

        // Update the niveauEtude
        NiveauEtude updatedNiveauEtude = niveauEtudeRepository.findById(niveauEtude.getId()).get();
        // Disconnect from session so that the updates on updatedNiveauEtude are not directly saved in db
        em.detach(updatedNiveauEtude);
        updatedNiveauEtude
            .orderLevel(UPDATED_ORDER_LEVEL)
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

        restNiveauEtudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNiveauEtude.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNiveauEtude))
            )
            .andExpect(status().isOk());

        // Validate the NiveauEtude in the database
        List<NiveauEtude> niveauEtudeList = niveauEtudeRepository.findAll();
        assertThat(niveauEtudeList).hasSize(databaseSizeBeforeUpdate);
        NiveauEtude testNiveauEtude = niveauEtudeList.get(niveauEtudeList.size() - 1);
        assertThat(testNiveauEtude.getOrderLevel()).isEqualTo(UPDATED_ORDER_LEVEL);
        assertThat(testNiveauEtude.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNiveauEtude.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNiveauEtude.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNiveauEtude.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNiveauEtude.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNiveauEtude.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNiveauEtude.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNiveauEtude.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNiveauEtude.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNiveauEtude.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingNiveauEtude() throws Exception {
        int databaseSizeBeforeUpdate = niveauEtudeRepository.findAll().size();
        niveauEtude.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNiveauEtudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, niveauEtude.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(niveauEtude))
            )
            .andExpect(status().isBadRequest());

        // Validate the NiveauEtude in the database
        List<NiveauEtude> niveauEtudeList = niveauEtudeRepository.findAll();
        assertThat(niveauEtudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNiveauEtude() throws Exception {
        int databaseSizeBeforeUpdate = niveauEtudeRepository.findAll().size();
        niveauEtude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNiveauEtudeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(niveauEtude))
            )
            .andExpect(status().isBadRequest());

        // Validate the NiveauEtude in the database
        List<NiveauEtude> niveauEtudeList = niveauEtudeRepository.findAll();
        assertThat(niveauEtudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNiveauEtude() throws Exception {
        int databaseSizeBeforeUpdate = niveauEtudeRepository.findAll().size();
        niveauEtude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNiveauEtudeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(niveauEtude)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NiveauEtude in the database
        List<NiveauEtude> niveauEtudeList = niveauEtudeRepository.findAll();
        assertThat(niveauEtudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNiveauEtudeWithPatch() throws Exception {
        // Initialize the database
        niveauEtudeRepository.saveAndFlush(niveauEtude);

        int databaseSizeBeforeUpdate = niveauEtudeRepository.findAll().size();

        // Update the niveauEtude using partial update
        NiveauEtude partialUpdatedNiveauEtude = new NiveauEtude();
        partialUpdatedNiveauEtude.setId(niveauEtude.getId());

        partialUpdatedNiveauEtude
            .orderLevel(UPDATED_ORDER_LEVEL)
            .libAr(UPDATED_LIB_AR)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE);

        restNiveauEtudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNiveauEtude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNiveauEtude))
            )
            .andExpect(status().isOk());

        // Validate the NiveauEtude in the database
        List<NiveauEtude> niveauEtudeList = niveauEtudeRepository.findAll();
        assertThat(niveauEtudeList).hasSize(databaseSizeBeforeUpdate);
        NiveauEtude testNiveauEtude = niveauEtudeList.get(niveauEtudeList.size() - 1);
        assertThat(testNiveauEtude.getOrderLevel()).isEqualTo(UPDATED_ORDER_LEVEL);
        assertThat(testNiveauEtude.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNiveauEtude.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNiveauEtude.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testNiveauEtude.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testNiveauEtude.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testNiveauEtude.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testNiveauEtude.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testNiveauEtude.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNiveauEtude.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNiveauEtude.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateNiveauEtudeWithPatch() throws Exception {
        // Initialize the database
        niveauEtudeRepository.saveAndFlush(niveauEtude);

        int databaseSizeBeforeUpdate = niveauEtudeRepository.findAll().size();

        // Update the niveauEtude using partial update
        NiveauEtude partialUpdatedNiveauEtude = new NiveauEtude();
        partialUpdatedNiveauEtude.setId(niveauEtude.getId());

        partialUpdatedNiveauEtude
            .orderLevel(UPDATED_ORDER_LEVEL)
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

        restNiveauEtudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNiveauEtude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNiveauEtude))
            )
            .andExpect(status().isOk());

        // Validate the NiveauEtude in the database
        List<NiveauEtude> niveauEtudeList = niveauEtudeRepository.findAll();
        assertThat(niveauEtudeList).hasSize(databaseSizeBeforeUpdate);
        NiveauEtude testNiveauEtude = niveauEtudeList.get(niveauEtudeList.size() - 1);
        assertThat(testNiveauEtude.getOrderLevel()).isEqualTo(UPDATED_ORDER_LEVEL);
        assertThat(testNiveauEtude.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNiveauEtude.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNiveauEtude.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNiveauEtude.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNiveauEtude.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNiveauEtude.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNiveauEtude.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNiveauEtude.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNiveauEtude.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNiveauEtude.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingNiveauEtude() throws Exception {
        int databaseSizeBeforeUpdate = niveauEtudeRepository.findAll().size();
        niveauEtude.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNiveauEtudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, niveauEtude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(niveauEtude))
            )
            .andExpect(status().isBadRequest());

        // Validate the NiveauEtude in the database
        List<NiveauEtude> niveauEtudeList = niveauEtudeRepository.findAll();
        assertThat(niveauEtudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNiveauEtude() throws Exception {
        int databaseSizeBeforeUpdate = niveauEtudeRepository.findAll().size();
        niveauEtude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNiveauEtudeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(niveauEtude))
            )
            .andExpect(status().isBadRequest());

        // Validate the NiveauEtude in the database
        List<NiveauEtude> niveauEtudeList = niveauEtudeRepository.findAll();
        assertThat(niveauEtudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNiveauEtude() throws Exception {
        int databaseSizeBeforeUpdate = niveauEtudeRepository.findAll().size();
        niveauEtude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNiveauEtudeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(niveauEtude))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NiveauEtude in the database
        List<NiveauEtude> niveauEtudeList = niveauEtudeRepository.findAll();
        assertThat(niveauEtudeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNiveauEtude() throws Exception {
        // Initialize the database
        niveauEtudeRepository.saveAndFlush(niveauEtude);

        int databaseSizeBeforeDelete = niveauEtudeRepository.findAll().size();

        // Delete the niveauEtude
        restNiveauEtudeMockMvc
            .perform(delete(ENTITY_API_URL_ID, niveauEtude.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NiveauEtude> niveauEtudeList = niveauEtudeRepository.findAll();
        assertThat(niveauEtudeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
