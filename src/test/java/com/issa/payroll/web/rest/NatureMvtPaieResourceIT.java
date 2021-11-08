package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.NatureMvtPaie;
import com.issa.payroll.repository.NatureMvtPaieRepository;
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
 * Integration tests for the {@link NatureMvtPaieResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NatureMvtPaieResourceIT {

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

    private static final String ENTITY_API_URL = "/api/nature-mvt-paies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NatureMvtPaieRepository natureMvtPaieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNatureMvtPaieMockMvc;

    private NatureMvtPaie natureMvtPaie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureMvtPaie createEntity(EntityManager em) {
        NatureMvtPaie natureMvtPaie = new NatureMvtPaie()
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
        return natureMvtPaie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureMvtPaie createUpdatedEntity(EntityManager em) {
        NatureMvtPaie natureMvtPaie = new NatureMvtPaie()
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
        return natureMvtPaie;
    }

    @BeforeEach
    public void initTest() {
        natureMvtPaie = createEntity(em);
    }

    @Test
    @Transactional
    void createNatureMvtPaie() throws Exception {
        int databaseSizeBeforeCreate = natureMvtPaieRepository.findAll().size();
        // Create the NatureMvtPaie
        restNatureMvtPaieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureMvtPaie)))
            .andExpect(status().isCreated());

        // Validate the NatureMvtPaie in the database
        List<NatureMvtPaie> natureMvtPaieList = natureMvtPaieRepository.findAll();
        assertThat(natureMvtPaieList).hasSize(databaseSizeBeforeCreate + 1);
        NatureMvtPaie testNatureMvtPaie = natureMvtPaieList.get(natureMvtPaieList.size() - 1);
        assertThat(testNatureMvtPaie.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNatureMvtPaie.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testNatureMvtPaie.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testNatureMvtPaie.getLibFr()).isEqualTo(DEFAULT_LIB_FR);
        assertThat(testNatureMvtPaie.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testNatureMvtPaie.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testNatureMvtPaie.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testNatureMvtPaie.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testNatureMvtPaie.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testNatureMvtPaie.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testNatureMvtPaie.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNatureMvtPaie.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createNatureMvtPaieWithExistingId() throws Exception {
        // Create the NatureMvtPaie with an existing ID
        natureMvtPaie.setId(1L);

        int databaseSizeBeforeCreate = natureMvtPaieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatureMvtPaieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureMvtPaie)))
            .andExpect(status().isBadRequest());

        // Validate the NatureMvtPaie in the database
        List<NatureMvtPaie> natureMvtPaieList = natureMvtPaieRepository.findAll();
        assertThat(natureMvtPaieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNatureMvtPaies() throws Exception {
        // Initialize the database
        natureMvtPaieRepository.saveAndFlush(natureMvtPaie);

        // Get all the natureMvtPaieList
        restNatureMvtPaieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natureMvtPaie.getId().intValue())))
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
    void getNatureMvtPaie() throws Exception {
        // Initialize the database
        natureMvtPaieRepository.saveAndFlush(natureMvtPaie);

        // Get the natureMvtPaie
        restNatureMvtPaieMockMvc
            .perform(get(ENTITY_API_URL_ID, natureMvtPaie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(natureMvtPaie.getId().intValue()))
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
    void getNonExistingNatureMvtPaie() throws Exception {
        // Get the natureMvtPaie
        restNatureMvtPaieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNatureMvtPaie() throws Exception {
        // Initialize the database
        natureMvtPaieRepository.saveAndFlush(natureMvtPaie);

        int databaseSizeBeforeUpdate = natureMvtPaieRepository.findAll().size();

        // Update the natureMvtPaie
        NatureMvtPaie updatedNatureMvtPaie = natureMvtPaieRepository.findById(natureMvtPaie.getId()).get();
        // Disconnect from session so that the updates on updatedNatureMvtPaie are not directly saved in db
        em.detach(updatedNatureMvtPaie);
        updatedNatureMvtPaie
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

        restNatureMvtPaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNatureMvtPaie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNatureMvtPaie))
            )
            .andExpect(status().isOk());

        // Validate the NatureMvtPaie in the database
        List<NatureMvtPaie> natureMvtPaieList = natureMvtPaieRepository.findAll();
        assertThat(natureMvtPaieList).hasSize(databaseSizeBeforeUpdate);
        NatureMvtPaie testNatureMvtPaie = natureMvtPaieList.get(natureMvtPaieList.size() - 1);
        assertThat(testNatureMvtPaie.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureMvtPaie.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureMvtPaie.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureMvtPaie.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testNatureMvtPaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureMvtPaie.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureMvtPaie.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNatureMvtPaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureMvtPaie.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNatureMvtPaie.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNatureMvtPaie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNatureMvtPaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingNatureMvtPaie() throws Exception {
        int databaseSizeBeforeUpdate = natureMvtPaieRepository.findAll().size();
        natureMvtPaie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureMvtPaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureMvtPaie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureMvtPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureMvtPaie in the database
        List<NatureMvtPaie> natureMvtPaieList = natureMvtPaieRepository.findAll();
        assertThat(natureMvtPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNatureMvtPaie() throws Exception {
        int databaseSizeBeforeUpdate = natureMvtPaieRepository.findAll().size();
        natureMvtPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureMvtPaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureMvtPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureMvtPaie in the database
        List<NatureMvtPaie> natureMvtPaieList = natureMvtPaieRepository.findAll();
        assertThat(natureMvtPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNatureMvtPaie() throws Exception {
        int databaseSizeBeforeUpdate = natureMvtPaieRepository.findAll().size();
        natureMvtPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureMvtPaieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureMvtPaie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureMvtPaie in the database
        List<NatureMvtPaie> natureMvtPaieList = natureMvtPaieRepository.findAll();
        assertThat(natureMvtPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNatureMvtPaieWithPatch() throws Exception {
        // Initialize the database
        natureMvtPaieRepository.saveAndFlush(natureMvtPaie);

        int databaseSizeBeforeUpdate = natureMvtPaieRepository.findAll().size();

        // Update the natureMvtPaie using partial update
        NatureMvtPaie partialUpdatedNatureMvtPaie = new NatureMvtPaie();
        partialUpdatedNatureMvtPaie.setId(natureMvtPaie.getId());

        partialUpdatedNatureMvtPaie
            .code(UPDATED_CODE)
            .libFr(UPDATED_LIB_FR)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restNatureMvtPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureMvtPaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureMvtPaie))
            )
            .andExpect(status().isOk());

        // Validate the NatureMvtPaie in the database
        List<NatureMvtPaie> natureMvtPaieList = natureMvtPaieRepository.findAll();
        assertThat(natureMvtPaieList).hasSize(databaseSizeBeforeUpdate);
        NatureMvtPaie testNatureMvtPaie = natureMvtPaieList.get(natureMvtPaieList.size() - 1);
        assertThat(testNatureMvtPaie.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureMvtPaie.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testNatureMvtPaie.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testNatureMvtPaie.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testNatureMvtPaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureMvtPaie.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureMvtPaie.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNatureMvtPaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureMvtPaie.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testNatureMvtPaie.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testNatureMvtPaie.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNatureMvtPaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateNatureMvtPaieWithPatch() throws Exception {
        // Initialize the database
        natureMvtPaieRepository.saveAndFlush(natureMvtPaie);

        int databaseSizeBeforeUpdate = natureMvtPaieRepository.findAll().size();

        // Update the natureMvtPaie using partial update
        NatureMvtPaie partialUpdatedNatureMvtPaie = new NatureMvtPaie();
        partialUpdatedNatureMvtPaie.setId(natureMvtPaie.getId());

        partialUpdatedNatureMvtPaie
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

        restNatureMvtPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureMvtPaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureMvtPaie))
            )
            .andExpect(status().isOk());

        // Validate the NatureMvtPaie in the database
        List<NatureMvtPaie> natureMvtPaieList = natureMvtPaieRepository.findAll();
        assertThat(natureMvtPaieList).hasSize(databaseSizeBeforeUpdate);
        NatureMvtPaie testNatureMvtPaie = natureMvtPaieList.get(natureMvtPaieList.size() - 1);
        assertThat(testNatureMvtPaie.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureMvtPaie.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureMvtPaie.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureMvtPaie.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testNatureMvtPaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureMvtPaie.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureMvtPaie.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNatureMvtPaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureMvtPaie.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNatureMvtPaie.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNatureMvtPaie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNatureMvtPaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingNatureMvtPaie() throws Exception {
        int databaseSizeBeforeUpdate = natureMvtPaieRepository.findAll().size();
        natureMvtPaie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureMvtPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, natureMvtPaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureMvtPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureMvtPaie in the database
        List<NatureMvtPaie> natureMvtPaieList = natureMvtPaieRepository.findAll();
        assertThat(natureMvtPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNatureMvtPaie() throws Exception {
        int databaseSizeBeforeUpdate = natureMvtPaieRepository.findAll().size();
        natureMvtPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureMvtPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureMvtPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureMvtPaie in the database
        List<NatureMvtPaie> natureMvtPaieList = natureMvtPaieRepository.findAll();
        assertThat(natureMvtPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNatureMvtPaie() throws Exception {
        int databaseSizeBeforeUpdate = natureMvtPaieRepository.findAll().size();
        natureMvtPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureMvtPaieMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(natureMvtPaie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureMvtPaie in the database
        List<NatureMvtPaie> natureMvtPaieList = natureMvtPaieRepository.findAll();
        assertThat(natureMvtPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNatureMvtPaie() throws Exception {
        // Initialize the database
        natureMvtPaieRepository.saveAndFlush(natureMvtPaie);

        int databaseSizeBeforeDelete = natureMvtPaieRepository.findAll().size();

        // Delete the natureMvtPaie
        restNatureMvtPaieMockMvc
            .perform(delete(ENTITY_API_URL_ID, natureMvtPaie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NatureMvtPaie> natureMvtPaieList = natureMvtPaieRepository.findAll();
        assertThat(natureMvtPaieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
