package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.NatureAdhesion;
import com.issa.payroll.repository.NatureAdhesionRepository;
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
 * Integration tests for the {@link NatureAdhesionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NatureAdhesionResourceIT {

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

    private static final String ENTITY_API_URL = "/api/nature-adhesions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NatureAdhesionRepository natureAdhesionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNatureAdhesionMockMvc;

    private NatureAdhesion natureAdhesion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureAdhesion createEntity(EntityManager em) {
        NatureAdhesion natureAdhesion = new NatureAdhesion()
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
        return natureAdhesion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureAdhesion createUpdatedEntity(EntityManager em) {
        NatureAdhesion natureAdhesion = new NatureAdhesion()
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
        return natureAdhesion;
    }

    @BeforeEach
    public void initTest() {
        natureAdhesion = createEntity(em);
    }

    @Test
    @Transactional
    void createNatureAdhesion() throws Exception {
        int databaseSizeBeforeCreate = natureAdhesionRepository.findAll().size();
        // Create the NatureAdhesion
        restNatureAdhesionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureAdhesion))
            )
            .andExpect(status().isCreated());

        // Validate the NatureAdhesion in the database
        List<NatureAdhesion> natureAdhesionList = natureAdhesionRepository.findAll();
        assertThat(natureAdhesionList).hasSize(databaseSizeBeforeCreate + 1);
        NatureAdhesion testNatureAdhesion = natureAdhesionList.get(natureAdhesionList.size() - 1);
        assertThat(testNatureAdhesion.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNatureAdhesion.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testNatureAdhesion.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testNatureAdhesion.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testNatureAdhesion.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testNatureAdhesion.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testNatureAdhesion.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testNatureAdhesion.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testNatureAdhesion.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNatureAdhesion.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createNatureAdhesionWithExistingId() throws Exception {
        // Create the NatureAdhesion with an existing ID
        natureAdhesion.setId(1L);

        int databaseSizeBeforeCreate = natureAdhesionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatureAdhesionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureAdhesion))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAdhesion in the database
        List<NatureAdhesion> natureAdhesionList = natureAdhesionRepository.findAll();
        assertThat(natureAdhesionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNatureAdhesions() throws Exception {
        // Initialize the database
        natureAdhesionRepository.saveAndFlush(natureAdhesion);

        // Get all the natureAdhesionList
        restNatureAdhesionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natureAdhesion.getId().intValue())))
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
    void getNatureAdhesion() throws Exception {
        // Initialize the database
        natureAdhesionRepository.saveAndFlush(natureAdhesion);

        // Get the natureAdhesion
        restNatureAdhesionMockMvc
            .perform(get(ENTITY_API_URL_ID, natureAdhesion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(natureAdhesion.getId().intValue()))
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
    void getNonExistingNatureAdhesion() throws Exception {
        // Get the natureAdhesion
        restNatureAdhesionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNatureAdhesion() throws Exception {
        // Initialize the database
        natureAdhesionRepository.saveAndFlush(natureAdhesion);

        int databaseSizeBeforeUpdate = natureAdhesionRepository.findAll().size();

        // Update the natureAdhesion
        NatureAdhesion updatedNatureAdhesion = natureAdhesionRepository.findById(natureAdhesion.getId()).get();
        // Disconnect from session so that the updates on updatedNatureAdhesion are not directly saved in db
        em.detach(updatedNatureAdhesion);
        updatedNatureAdhesion
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

        restNatureAdhesionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNatureAdhesion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNatureAdhesion))
            )
            .andExpect(status().isOk());

        // Validate the NatureAdhesion in the database
        List<NatureAdhesion> natureAdhesionList = natureAdhesionRepository.findAll();
        assertThat(natureAdhesionList).hasSize(databaseSizeBeforeUpdate);
        NatureAdhesion testNatureAdhesion = natureAdhesionList.get(natureAdhesionList.size() - 1);
        assertThat(testNatureAdhesion.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureAdhesion.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureAdhesion.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureAdhesion.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNatureAdhesion.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureAdhesion.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureAdhesion.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureAdhesion.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNatureAdhesion.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNatureAdhesion.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingNatureAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = natureAdhesionRepository.findAll().size();
        natureAdhesion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureAdhesionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureAdhesion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureAdhesion))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAdhesion in the database
        List<NatureAdhesion> natureAdhesionList = natureAdhesionRepository.findAll();
        assertThat(natureAdhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNatureAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = natureAdhesionRepository.findAll().size();
        natureAdhesion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAdhesionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureAdhesion))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAdhesion in the database
        List<NatureAdhesion> natureAdhesionList = natureAdhesionRepository.findAll();
        assertThat(natureAdhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNatureAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = natureAdhesionRepository.findAll().size();
        natureAdhesion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAdhesionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureAdhesion)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureAdhesion in the database
        List<NatureAdhesion> natureAdhesionList = natureAdhesionRepository.findAll();
        assertThat(natureAdhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNatureAdhesionWithPatch() throws Exception {
        // Initialize the database
        natureAdhesionRepository.saveAndFlush(natureAdhesion);

        int databaseSizeBeforeUpdate = natureAdhesionRepository.findAll().size();

        // Update the natureAdhesion using partial update
        NatureAdhesion partialUpdatedNatureAdhesion = new NatureAdhesion();
        partialUpdatedNatureAdhesion.setId(natureAdhesion.getId());

        partialUpdatedNatureAdhesion
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restNatureAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureAdhesion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureAdhesion))
            )
            .andExpect(status().isOk());

        // Validate the NatureAdhesion in the database
        List<NatureAdhesion> natureAdhesionList = natureAdhesionRepository.findAll();
        assertThat(natureAdhesionList).hasSize(databaseSizeBeforeUpdate);
        NatureAdhesion testNatureAdhesion = natureAdhesionList.get(natureAdhesionList.size() - 1);
        assertThat(testNatureAdhesion.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureAdhesion.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testNatureAdhesion.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureAdhesion.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testNatureAdhesion.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureAdhesion.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureAdhesion.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureAdhesion.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNatureAdhesion.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNatureAdhesion.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateNatureAdhesionWithPatch() throws Exception {
        // Initialize the database
        natureAdhesionRepository.saveAndFlush(natureAdhesion);

        int databaseSizeBeforeUpdate = natureAdhesionRepository.findAll().size();

        // Update the natureAdhesion using partial update
        NatureAdhesion partialUpdatedNatureAdhesion = new NatureAdhesion();
        partialUpdatedNatureAdhesion.setId(natureAdhesion.getId());

        partialUpdatedNatureAdhesion
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

        restNatureAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureAdhesion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureAdhesion))
            )
            .andExpect(status().isOk());

        // Validate the NatureAdhesion in the database
        List<NatureAdhesion> natureAdhesionList = natureAdhesionRepository.findAll();
        assertThat(natureAdhesionList).hasSize(databaseSizeBeforeUpdate);
        NatureAdhesion testNatureAdhesion = natureAdhesionList.get(natureAdhesionList.size() - 1);
        assertThat(testNatureAdhesion.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureAdhesion.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureAdhesion.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureAdhesion.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNatureAdhesion.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureAdhesion.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureAdhesion.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureAdhesion.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNatureAdhesion.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNatureAdhesion.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingNatureAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = natureAdhesionRepository.findAll().size();
        natureAdhesion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, natureAdhesion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureAdhesion))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAdhesion in the database
        List<NatureAdhesion> natureAdhesionList = natureAdhesionRepository.findAll();
        assertThat(natureAdhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNatureAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = natureAdhesionRepository.findAll().size();
        natureAdhesion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureAdhesion))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAdhesion in the database
        List<NatureAdhesion> natureAdhesionList = natureAdhesionRepository.findAll();
        assertThat(natureAdhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNatureAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = natureAdhesionRepository.findAll().size();
        natureAdhesion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(natureAdhesion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureAdhesion in the database
        List<NatureAdhesion> natureAdhesionList = natureAdhesionRepository.findAll();
        assertThat(natureAdhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNatureAdhesion() throws Exception {
        // Initialize the database
        natureAdhesionRepository.saveAndFlush(natureAdhesion);

        int databaseSizeBeforeDelete = natureAdhesionRepository.findAll().size();

        // Delete the natureAdhesion
        restNatureAdhesionMockMvc
            .perform(delete(ENTITY_API_URL_ID, natureAdhesion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NatureAdhesion> natureAdhesionList = natureAdhesionRepository.findAll();
        assertThat(natureAdhesionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
