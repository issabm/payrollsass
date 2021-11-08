package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.NiveauScolaire;
import com.issa.payroll.repository.NiveauScolaireRepository;
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
 * Integration tests for the {@link NiveauScolaireResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NiveauScolaireResourceIT {

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

    private static final String ENTITY_API_URL = "/api/niveau-scolaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NiveauScolaireRepository niveauScolaireRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNiveauScolaireMockMvc;

    private NiveauScolaire niveauScolaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NiveauScolaire createEntity(EntityManager em) {
        NiveauScolaire niveauScolaire = new NiveauScolaire()
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
        return niveauScolaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NiveauScolaire createUpdatedEntity(EntityManager em) {
        NiveauScolaire niveauScolaire = new NiveauScolaire()
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
        return niveauScolaire;
    }

    @BeforeEach
    public void initTest() {
        niveauScolaire = createEntity(em);
    }

    @Test
    @Transactional
    void createNiveauScolaire() throws Exception {
        int databaseSizeBeforeCreate = niveauScolaireRepository.findAll().size();
        // Create the NiveauScolaire
        restNiveauScolaireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(niveauScolaire))
            )
            .andExpect(status().isCreated());

        // Validate the NiveauScolaire in the database
        List<NiveauScolaire> niveauScolaireList = niveauScolaireRepository.findAll();
        assertThat(niveauScolaireList).hasSize(databaseSizeBeforeCreate + 1);
        NiveauScolaire testNiveauScolaire = niveauScolaireList.get(niveauScolaireList.size() - 1);
        assertThat(testNiveauScolaire.getOrderLevel()).isEqualTo(DEFAULT_ORDER_LEVEL);
        assertThat(testNiveauScolaire.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNiveauScolaire.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testNiveauScolaire.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testNiveauScolaire.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testNiveauScolaire.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testNiveauScolaire.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testNiveauScolaire.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testNiveauScolaire.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testNiveauScolaire.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNiveauScolaire.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createNiveauScolaireWithExistingId() throws Exception {
        // Create the NiveauScolaire with an existing ID
        niveauScolaire.setId(1L);

        int databaseSizeBeforeCreate = niveauScolaireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNiveauScolaireMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(niveauScolaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the NiveauScolaire in the database
        List<NiveauScolaire> niveauScolaireList = niveauScolaireRepository.findAll();
        assertThat(niveauScolaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNiveauScolaires() throws Exception {
        // Initialize the database
        niveauScolaireRepository.saveAndFlush(niveauScolaire);

        // Get all the niveauScolaireList
        restNiveauScolaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niveauScolaire.getId().intValue())))
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
    void getNiveauScolaire() throws Exception {
        // Initialize the database
        niveauScolaireRepository.saveAndFlush(niveauScolaire);

        // Get the niveauScolaire
        restNiveauScolaireMockMvc
            .perform(get(ENTITY_API_URL_ID, niveauScolaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(niveauScolaire.getId().intValue()))
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
    void getNonExistingNiveauScolaire() throws Exception {
        // Get the niveauScolaire
        restNiveauScolaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNiveauScolaire() throws Exception {
        // Initialize the database
        niveauScolaireRepository.saveAndFlush(niveauScolaire);

        int databaseSizeBeforeUpdate = niveauScolaireRepository.findAll().size();

        // Update the niveauScolaire
        NiveauScolaire updatedNiveauScolaire = niveauScolaireRepository.findById(niveauScolaire.getId()).get();
        // Disconnect from session so that the updates on updatedNiveauScolaire are not directly saved in db
        em.detach(updatedNiveauScolaire);
        updatedNiveauScolaire
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

        restNiveauScolaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNiveauScolaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNiveauScolaire))
            )
            .andExpect(status().isOk());

        // Validate the NiveauScolaire in the database
        List<NiveauScolaire> niveauScolaireList = niveauScolaireRepository.findAll();
        assertThat(niveauScolaireList).hasSize(databaseSizeBeforeUpdate);
        NiveauScolaire testNiveauScolaire = niveauScolaireList.get(niveauScolaireList.size() - 1);
        assertThat(testNiveauScolaire.getOrderLevel()).isEqualTo(UPDATED_ORDER_LEVEL);
        assertThat(testNiveauScolaire.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNiveauScolaire.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNiveauScolaire.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNiveauScolaire.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNiveauScolaire.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNiveauScolaire.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNiveauScolaire.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNiveauScolaire.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNiveauScolaire.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNiveauScolaire.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingNiveauScolaire() throws Exception {
        int databaseSizeBeforeUpdate = niveauScolaireRepository.findAll().size();
        niveauScolaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNiveauScolaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, niveauScolaire.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(niveauScolaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the NiveauScolaire in the database
        List<NiveauScolaire> niveauScolaireList = niveauScolaireRepository.findAll();
        assertThat(niveauScolaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNiveauScolaire() throws Exception {
        int databaseSizeBeforeUpdate = niveauScolaireRepository.findAll().size();
        niveauScolaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNiveauScolaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(niveauScolaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the NiveauScolaire in the database
        List<NiveauScolaire> niveauScolaireList = niveauScolaireRepository.findAll();
        assertThat(niveauScolaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNiveauScolaire() throws Exception {
        int databaseSizeBeforeUpdate = niveauScolaireRepository.findAll().size();
        niveauScolaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNiveauScolaireMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(niveauScolaire)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NiveauScolaire in the database
        List<NiveauScolaire> niveauScolaireList = niveauScolaireRepository.findAll();
        assertThat(niveauScolaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNiveauScolaireWithPatch() throws Exception {
        // Initialize the database
        niveauScolaireRepository.saveAndFlush(niveauScolaire);

        int databaseSizeBeforeUpdate = niveauScolaireRepository.findAll().size();

        // Update the niveauScolaire using partial update
        NiveauScolaire partialUpdatedNiveauScolaire = new NiveauScolaire();
        partialUpdatedNiveauScolaire.setId(niveauScolaire.getId());

        partialUpdatedNiveauScolaire
            .orderLevel(UPDATED_ORDER_LEVEL)
            .code(UPDATED_CODE)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restNiveauScolaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNiveauScolaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNiveauScolaire))
            )
            .andExpect(status().isOk());

        // Validate the NiveauScolaire in the database
        List<NiveauScolaire> niveauScolaireList = niveauScolaireRepository.findAll();
        assertThat(niveauScolaireList).hasSize(databaseSizeBeforeUpdate);
        NiveauScolaire testNiveauScolaire = niveauScolaireList.get(niveauScolaireList.size() - 1);
        assertThat(testNiveauScolaire.getOrderLevel()).isEqualTo(UPDATED_ORDER_LEVEL);
        assertThat(testNiveauScolaire.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNiveauScolaire.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testNiveauScolaire.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testNiveauScolaire.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNiveauScolaire.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNiveauScolaire.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testNiveauScolaire.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNiveauScolaire.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNiveauScolaire.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNiveauScolaire.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateNiveauScolaireWithPatch() throws Exception {
        // Initialize the database
        niveauScolaireRepository.saveAndFlush(niveauScolaire);

        int databaseSizeBeforeUpdate = niveauScolaireRepository.findAll().size();

        // Update the niveauScolaire using partial update
        NiveauScolaire partialUpdatedNiveauScolaire = new NiveauScolaire();
        partialUpdatedNiveauScolaire.setId(niveauScolaire.getId());

        partialUpdatedNiveauScolaire
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

        restNiveauScolaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNiveauScolaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNiveauScolaire))
            )
            .andExpect(status().isOk());

        // Validate the NiveauScolaire in the database
        List<NiveauScolaire> niveauScolaireList = niveauScolaireRepository.findAll();
        assertThat(niveauScolaireList).hasSize(databaseSizeBeforeUpdate);
        NiveauScolaire testNiveauScolaire = niveauScolaireList.get(niveauScolaireList.size() - 1);
        assertThat(testNiveauScolaire.getOrderLevel()).isEqualTo(UPDATED_ORDER_LEVEL);
        assertThat(testNiveauScolaire.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNiveauScolaire.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNiveauScolaire.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNiveauScolaire.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNiveauScolaire.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNiveauScolaire.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNiveauScolaire.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNiveauScolaire.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNiveauScolaire.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNiveauScolaire.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingNiveauScolaire() throws Exception {
        int databaseSizeBeforeUpdate = niveauScolaireRepository.findAll().size();
        niveauScolaire.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNiveauScolaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, niveauScolaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(niveauScolaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the NiveauScolaire in the database
        List<NiveauScolaire> niveauScolaireList = niveauScolaireRepository.findAll();
        assertThat(niveauScolaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNiveauScolaire() throws Exception {
        int databaseSizeBeforeUpdate = niveauScolaireRepository.findAll().size();
        niveauScolaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNiveauScolaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(niveauScolaire))
            )
            .andExpect(status().isBadRequest());

        // Validate the NiveauScolaire in the database
        List<NiveauScolaire> niveauScolaireList = niveauScolaireRepository.findAll();
        assertThat(niveauScolaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNiveauScolaire() throws Exception {
        int databaseSizeBeforeUpdate = niveauScolaireRepository.findAll().size();
        niveauScolaire.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNiveauScolaireMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(niveauScolaire))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NiveauScolaire in the database
        List<NiveauScolaire> niveauScolaireList = niveauScolaireRepository.findAll();
        assertThat(niveauScolaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNiveauScolaire() throws Exception {
        // Initialize the database
        niveauScolaireRepository.saveAndFlush(niveauScolaire);

        int databaseSizeBeforeDelete = niveauScolaireRepository.findAll().size();

        // Delete the niveauScolaire
        restNiveauScolaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, niveauScolaire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NiveauScolaire> niveauScolaireList = niveauScolaireRepository.findAll();
        assertThat(niveauScolaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
