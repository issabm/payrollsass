package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.NatureEligibilite;
import com.issa.payroll.repository.NatureEligibiliteRepository;
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
 * Integration tests for the {@link NatureEligibiliteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NatureEligibiliteResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_FR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_FR = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/nature-eligibilites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NatureEligibiliteRepository natureEligibiliteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNatureEligibiliteMockMvc;

    private NatureEligibilite natureEligibilite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureEligibilite createEntity(EntityManager em) {
        NatureEligibilite natureEligibilite = new NatureEligibilite()
            .code(DEFAULT_CODE)
            .libEn(DEFAULT_LIB_EN)
            .libAr(DEFAULT_LIB_AR)
            .libFr(DEFAULT_LIB_FR)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .op(DEFAULT_OP)
            .util(DEFAULT_UTIL)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return natureEligibilite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureEligibilite createUpdatedEntity(EntityManager em) {
        NatureEligibilite natureEligibilite = new NatureEligibilite()
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return natureEligibilite;
    }

    @BeforeEach
    public void initTest() {
        natureEligibilite = createEntity(em);
    }

    @Test
    @Transactional
    void createNatureEligibilite() throws Exception {
        int databaseSizeBeforeCreate = natureEligibiliteRepository.findAll().size();
        // Create the NatureEligibilite
        restNatureEligibiliteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureEligibilite))
            )
            .andExpect(status().isCreated());

        // Validate the NatureEligibilite in the database
        List<NatureEligibilite> natureEligibiliteList = natureEligibiliteRepository.findAll();
        assertThat(natureEligibiliteList).hasSize(databaseSizeBeforeCreate + 1);
        NatureEligibilite testNatureEligibilite = natureEligibiliteList.get(natureEligibiliteList.size() - 1);
        assertThat(testNatureEligibilite.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNatureEligibilite.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testNatureEligibilite.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testNatureEligibilite.getLibFr()).isEqualTo(DEFAULT_LIB_FR);
        assertThat(testNatureEligibilite.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testNatureEligibilite.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testNatureEligibilite.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testNatureEligibilite.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testNatureEligibilite.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testNatureEligibilite.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testNatureEligibilite.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNatureEligibilite.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createNatureEligibiliteWithExistingId() throws Exception {
        // Create the NatureEligibilite with an existing ID
        natureEligibilite.setId(1L);

        int databaseSizeBeforeCreate = natureEligibiliteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatureEligibiliteMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureEligibilite))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureEligibilite in the database
        List<NatureEligibilite> natureEligibiliteList = natureEligibiliteRepository.findAll();
        assertThat(natureEligibiliteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNatureEligibilites() throws Exception {
        // Initialize the database
        natureEligibiliteRepository.saveAndFlush(natureEligibilite);

        // Get all the natureEligibiliteList
        restNatureEligibiliteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natureEligibilite.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].libFr").value(hasItem(DEFAULT_LIB_FR)))
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
    void getNatureEligibilite() throws Exception {
        // Initialize the database
        natureEligibiliteRepository.saveAndFlush(natureEligibilite);

        // Get the natureEligibilite
        restNatureEligibiliteMockMvc
            .perform(get(ENTITY_API_URL_ID, natureEligibilite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(natureEligibilite.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.libFr").value(DEFAULT_LIB_FR))
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
    void getNonExistingNatureEligibilite() throws Exception {
        // Get the natureEligibilite
        restNatureEligibiliteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNatureEligibilite() throws Exception {
        // Initialize the database
        natureEligibiliteRepository.saveAndFlush(natureEligibilite);

        int databaseSizeBeforeUpdate = natureEligibiliteRepository.findAll().size();

        // Update the natureEligibilite
        NatureEligibilite updatedNatureEligibilite = natureEligibiliteRepository.findById(natureEligibilite.getId()).get();
        // Disconnect from session so that the updates on updatedNatureEligibilite are not directly saved in db
        em.detach(updatedNatureEligibilite);
        updatedNatureEligibilite
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restNatureEligibiliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNatureEligibilite.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNatureEligibilite))
            )
            .andExpect(status().isOk());

        // Validate the NatureEligibilite in the database
        List<NatureEligibilite> natureEligibiliteList = natureEligibiliteRepository.findAll();
        assertThat(natureEligibiliteList).hasSize(databaseSizeBeforeUpdate);
        NatureEligibilite testNatureEligibilite = natureEligibiliteList.get(natureEligibiliteList.size() - 1);
        assertThat(testNatureEligibilite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureEligibilite.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureEligibilite.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureEligibilite.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testNatureEligibilite.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureEligibilite.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureEligibilite.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNatureEligibilite.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureEligibilite.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNatureEligibilite.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNatureEligibilite.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNatureEligibilite.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingNatureEligibilite() throws Exception {
        int databaseSizeBeforeUpdate = natureEligibiliteRepository.findAll().size();
        natureEligibilite.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureEligibiliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureEligibilite.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureEligibilite))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureEligibilite in the database
        List<NatureEligibilite> natureEligibiliteList = natureEligibiliteRepository.findAll();
        assertThat(natureEligibiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNatureEligibilite() throws Exception {
        int databaseSizeBeforeUpdate = natureEligibiliteRepository.findAll().size();
        natureEligibilite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureEligibiliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureEligibilite))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureEligibilite in the database
        List<NatureEligibilite> natureEligibiliteList = natureEligibiliteRepository.findAll();
        assertThat(natureEligibiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNatureEligibilite() throws Exception {
        int databaseSizeBeforeUpdate = natureEligibiliteRepository.findAll().size();
        natureEligibilite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureEligibiliteMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureEligibilite))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureEligibilite in the database
        List<NatureEligibilite> natureEligibiliteList = natureEligibiliteRepository.findAll();
        assertThat(natureEligibiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNatureEligibiliteWithPatch() throws Exception {
        // Initialize the database
        natureEligibiliteRepository.saveAndFlush(natureEligibilite);

        int databaseSizeBeforeUpdate = natureEligibiliteRepository.findAll().size();

        // Update the natureEligibilite using partial update
        NatureEligibilite partialUpdatedNatureEligibilite = new NatureEligibilite();
        partialUpdatedNatureEligibilite.setId(natureEligibilite.getId());

        partialUpdatedNatureEligibilite.code(UPDATED_CODE).libEn(UPDATED_LIB_EN).libFr(UPDATED_LIB_FR).modifiedDate(UPDATED_MODIFIED_DATE);

        restNatureEligibiliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureEligibilite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureEligibilite))
            )
            .andExpect(status().isOk());

        // Validate the NatureEligibilite in the database
        List<NatureEligibilite> natureEligibiliteList = natureEligibiliteRepository.findAll();
        assertThat(natureEligibiliteList).hasSize(databaseSizeBeforeUpdate);
        NatureEligibilite testNatureEligibilite = natureEligibiliteList.get(natureEligibiliteList.size() - 1);
        assertThat(testNatureEligibilite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureEligibilite.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureEligibilite.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testNatureEligibilite.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testNatureEligibilite.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testNatureEligibilite.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testNatureEligibilite.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testNatureEligibilite.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testNatureEligibilite.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testNatureEligibilite.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testNatureEligibilite.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNatureEligibilite.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateNatureEligibiliteWithPatch() throws Exception {
        // Initialize the database
        natureEligibiliteRepository.saveAndFlush(natureEligibilite);

        int databaseSizeBeforeUpdate = natureEligibiliteRepository.findAll().size();

        // Update the natureEligibilite using partial update
        NatureEligibilite partialUpdatedNatureEligibilite = new NatureEligibilite();
        partialUpdatedNatureEligibilite.setId(natureEligibilite.getId());

        partialUpdatedNatureEligibilite
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restNatureEligibiliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureEligibilite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureEligibilite))
            )
            .andExpect(status().isOk());

        // Validate the NatureEligibilite in the database
        List<NatureEligibilite> natureEligibiliteList = natureEligibiliteRepository.findAll();
        assertThat(natureEligibiliteList).hasSize(databaseSizeBeforeUpdate);
        NatureEligibilite testNatureEligibilite = natureEligibiliteList.get(natureEligibiliteList.size() - 1);
        assertThat(testNatureEligibilite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureEligibilite.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureEligibilite.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureEligibilite.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testNatureEligibilite.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureEligibilite.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureEligibilite.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNatureEligibilite.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureEligibilite.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNatureEligibilite.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNatureEligibilite.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNatureEligibilite.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingNatureEligibilite() throws Exception {
        int databaseSizeBeforeUpdate = natureEligibiliteRepository.findAll().size();
        natureEligibilite.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureEligibiliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, natureEligibilite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureEligibilite))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureEligibilite in the database
        List<NatureEligibilite> natureEligibiliteList = natureEligibiliteRepository.findAll();
        assertThat(natureEligibiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNatureEligibilite() throws Exception {
        int databaseSizeBeforeUpdate = natureEligibiliteRepository.findAll().size();
        natureEligibilite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureEligibiliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureEligibilite))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureEligibilite in the database
        List<NatureEligibilite> natureEligibiliteList = natureEligibiliteRepository.findAll();
        assertThat(natureEligibiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNatureEligibilite() throws Exception {
        int databaseSizeBeforeUpdate = natureEligibiliteRepository.findAll().size();
        natureEligibilite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureEligibiliteMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureEligibilite))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureEligibilite in the database
        List<NatureEligibilite> natureEligibiliteList = natureEligibiliteRepository.findAll();
        assertThat(natureEligibiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNatureEligibilite() throws Exception {
        // Initialize the database
        natureEligibiliteRepository.saveAndFlush(natureEligibilite);

        int databaseSizeBeforeDelete = natureEligibiliteRepository.findAll().size();

        // Delete the natureEligibilite
        restNatureEligibiliteMockMvc
            .perform(delete(ENTITY_API_URL_ID, natureEligibilite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NatureEligibilite> natureEligibiliteList = natureEligibiliteRepository.findAll();
        assertThat(natureEligibiliteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
