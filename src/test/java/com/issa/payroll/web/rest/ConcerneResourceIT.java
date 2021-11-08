package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Concerne;
import com.issa.payroll.repository.ConcerneRepository;
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
 * Integration tests for the {@link ConcerneResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ConcerneResourceIT {

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

    private static final String ENTITY_API_URL = "/api/concernes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConcerneRepository concerneRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConcerneMockMvc;

    private Concerne concerne;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concerne createEntity(EntityManager em) {
        Concerne concerne = new Concerne()
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
        return concerne;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Concerne createUpdatedEntity(EntityManager em) {
        Concerne concerne = new Concerne()
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
        return concerne;
    }

    @BeforeEach
    public void initTest() {
        concerne = createEntity(em);
    }

    @Test
    @Transactional
    void createConcerne() throws Exception {
        int databaseSizeBeforeCreate = concerneRepository.findAll().size();
        // Create the Concerne
        restConcerneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concerne)))
            .andExpect(status().isCreated());

        // Validate the Concerne in the database
        List<Concerne> concerneList = concerneRepository.findAll();
        assertThat(concerneList).hasSize(databaseSizeBeforeCreate + 1);
        Concerne testConcerne = concerneList.get(concerneList.size() - 1);
        assertThat(testConcerne.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testConcerne.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testConcerne.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testConcerne.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testConcerne.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testConcerne.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testConcerne.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testConcerne.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testConcerne.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testConcerne.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createConcerneWithExistingId() throws Exception {
        // Create the Concerne with an existing ID
        concerne.setId(1L);

        int databaseSizeBeforeCreate = concerneRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConcerneMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concerne)))
            .andExpect(status().isBadRequest());

        // Validate the Concerne in the database
        List<Concerne> concerneList = concerneRepository.findAll();
        assertThat(concerneList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConcernes() throws Exception {
        // Initialize the database
        concerneRepository.saveAndFlush(concerne);

        // Get all the concerneList
        restConcerneMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(concerne.getId().intValue())))
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
    void getConcerne() throws Exception {
        // Initialize the database
        concerneRepository.saveAndFlush(concerne);

        // Get the concerne
        restConcerneMockMvc
            .perform(get(ENTITY_API_URL_ID, concerne.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(concerne.getId().intValue()))
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
    void getNonExistingConcerne() throws Exception {
        // Get the concerne
        restConcerneMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConcerne() throws Exception {
        // Initialize the database
        concerneRepository.saveAndFlush(concerne);

        int databaseSizeBeforeUpdate = concerneRepository.findAll().size();

        // Update the concerne
        Concerne updatedConcerne = concerneRepository.findById(concerne.getId()).get();
        // Disconnect from session so that the updates on updatedConcerne are not directly saved in db
        em.detach(updatedConcerne);
        updatedConcerne
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

        restConcerneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConcerne.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConcerne))
            )
            .andExpect(status().isOk());

        // Validate the Concerne in the database
        List<Concerne> concerneList = concerneRepository.findAll();
        assertThat(concerneList).hasSize(databaseSizeBeforeUpdate);
        Concerne testConcerne = concerneList.get(concerneList.size() - 1);
        assertThat(testConcerne.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testConcerne.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testConcerne.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testConcerne.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testConcerne.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testConcerne.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testConcerne.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testConcerne.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testConcerne.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testConcerne.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingConcerne() throws Exception {
        int databaseSizeBeforeUpdate = concerneRepository.findAll().size();
        concerne.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcerneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, concerne.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concerne))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concerne in the database
        List<Concerne> concerneList = concerneRepository.findAll();
        assertThat(concerneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConcerne() throws Exception {
        int databaseSizeBeforeUpdate = concerneRepository.findAll().size();
        concerne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcerneMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(concerne))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concerne in the database
        List<Concerne> concerneList = concerneRepository.findAll();
        assertThat(concerneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConcerne() throws Exception {
        int databaseSizeBeforeUpdate = concerneRepository.findAll().size();
        concerne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcerneMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(concerne)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Concerne in the database
        List<Concerne> concerneList = concerneRepository.findAll();
        assertThat(concerneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConcerneWithPatch() throws Exception {
        // Initialize the database
        concerneRepository.saveAndFlush(concerne);

        int databaseSizeBeforeUpdate = concerneRepository.findAll().size();

        // Update the concerne using partial update
        Concerne partialUpdatedConcerne = new Concerne();
        partialUpdatedConcerne.setId(concerne.getId());

        partialUpdatedConcerne.libAr(UPDATED_LIB_AR).dateop(UPDATED_DATEOP).op(UPDATED_OP).modifiedDate(UPDATED_MODIFIED_DATE);

        restConcerneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcerne.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcerne))
            )
            .andExpect(status().isOk());

        // Validate the Concerne in the database
        List<Concerne> concerneList = concerneRepository.findAll();
        assertThat(concerneList).hasSize(databaseSizeBeforeUpdate);
        Concerne testConcerne = concerneList.get(concerneList.size() - 1);
        assertThat(testConcerne.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testConcerne.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testConcerne.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testConcerne.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testConcerne.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testConcerne.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testConcerne.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testConcerne.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testConcerne.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testConcerne.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateConcerneWithPatch() throws Exception {
        // Initialize the database
        concerneRepository.saveAndFlush(concerne);

        int databaseSizeBeforeUpdate = concerneRepository.findAll().size();

        // Update the concerne using partial update
        Concerne partialUpdatedConcerne = new Concerne();
        partialUpdatedConcerne.setId(concerne.getId());

        partialUpdatedConcerne
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

        restConcerneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConcerne.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConcerne))
            )
            .andExpect(status().isOk());

        // Validate the Concerne in the database
        List<Concerne> concerneList = concerneRepository.findAll();
        assertThat(concerneList).hasSize(databaseSizeBeforeUpdate);
        Concerne testConcerne = concerneList.get(concerneList.size() - 1);
        assertThat(testConcerne.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testConcerne.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testConcerne.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testConcerne.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testConcerne.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testConcerne.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testConcerne.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testConcerne.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testConcerne.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testConcerne.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingConcerne() throws Exception {
        int databaseSizeBeforeUpdate = concerneRepository.findAll().size();
        concerne.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConcerneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, concerne.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concerne))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concerne in the database
        List<Concerne> concerneList = concerneRepository.findAll();
        assertThat(concerneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConcerne() throws Exception {
        int databaseSizeBeforeUpdate = concerneRepository.findAll().size();
        concerne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcerneMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(concerne))
            )
            .andExpect(status().isBadRequest());

        // Validate the Concerne in the database
        List<Concerne> concerneList = concerneRepository.findAll();
        assertThat(concerneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConcerne() throws Exception {
        int databaseSizeBeforeUpdate = concerneRepository.findAll().size();
        concerne.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConcerneMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(concerne)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Concerne in the database
        List<Concerne> concerneList = concerneRepository.findAll();
        assertThat(concerneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConcerne() throws Exception {
        // Initialize the database
        concerneRepository.saveAndFlush(concerne);

        int databaseSizeBeforeDelete = concerneRepository.findAll().size();

        // Delete the concerne
        restConcerneMockMvc
            .perform(delete(ENTITY_API_URL_ID, concerne.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Concerne> concerneList = concerneRepository.findAll();
        assertThat(concerneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
