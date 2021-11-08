package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.NatureAbsence;
import com.issa.payroll.repository.NatureAbsenceRepository;
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
 * Integration tests for the {@link NatureAbsenceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NatureAbsenceResourceIT {

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

    private static final Integer DEFAULT_NB_DAYS = 1;
    private static final Integer UPDATED_NB_DAYS = 2;

    private static final Double DEFAULT_VALUE_PAIED = 1D;
    private static final Double UPDATED_VALUE_PAIED = 2D;

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

    private static final String ENTITY_API_URL = "/api/nature-absences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NatureAbsenceRepository natureAbsenceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNatureAbsenceMockMvc;

    private NatureAbsence natureAbsence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureAbsence createEntity(EntityManager em) {
        NatureAbsence natureAbsence = new NatureAbsence()
            .code(DEFAULT_CODE)
            .libAr(DEFAULT_LIB_AR)
            .libEn(DEFAULT_LIB_EN)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .nbDays(DEFAULT_NB_DAYS)
            .valuePaied(DEFAULT_VALUE_PAIED)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return natureAbsence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureAbsence createUpdatedEntity(EntityManager em) {
        NatureAbsence natureAbsence = new NatureAbsence()
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .nbDays(UPDATED_NB_DAYS)
            .valuePaied(UPDATED_VALUE_PAIED)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return natureAbsence;
    }

    @BeforeEach
    public void initTest() {
        natureAbsence = createEntity(em);
    }

    @Test
    @Transactional
    void createNatureAbsence() throws Exception {
        int databaseSizeBeforeCreate = natureAbsenceRepository.findAll().size();
        // Create the NatureAbsence
        restNatureAbsenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureAbsence)))
            .andExpect(status().isCreated());

        // Validate the NatureAbsence in the database
        List<NatureAbsence> natureAbsenceList = natureAbsenceRepository.findAll();
        assertThat(natureAbsenceList).hasSize(databaseSizeBeforeCreate + 1);
        NatureAbsence testNatureAbsence = natureAbsenceList.get(natureAbsenceList.size() - 1);
        assertThat(testNatureAbsence.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNatureAbsence.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testNatureAbsence.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testNatureAbsence.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testNatureAbsence.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testNatureAbsence.getNbDays()).isEqualTo(DEFAULT_NB_DAYS);
        assertThat(testNatureAbsence.getValuePaied()).isEqualTo(DEFAULT_VALUE_PAIED);
        assertThat(testNatureAbsence.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testNatureAbsence.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testNatureAbsence.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testNatureAbsence.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNatureAbsence.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createNatureAbsenceWithExistingId() throws Exception {
        // Create the NatureAbsence with an existing ID
        natureAbsence.setId(1L);

        int databaseSizeBeforeCreate = natureAbsenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatureAbsenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureAbsence)))
            .andExpect(status().isBadRequest());

        // Validate the NatureAbsence in the database
        List<NatureAbsence> natureAbsenceList = natureAbsenceRepository.findAll();
        assertThat(natureAbsenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNatureAbsences() throws Exception {
        // Initialize the database
        natureAbsenceRepository.saveAndFlush(natureAbsence);

        // Get all the natureAbsenceList
        restNatureAbsenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natureAbsence.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].nbDays").value(hasItem(DEFAULT_NB_DAYS)))
            .andExpect(jsonPath("$.[*].valuePaied").value(hasItem(DEFAULT_VALUE_PAIED.doubleValue())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    void getNatureAbsence() throws Exception {
        // Initialize the database
        natureAbsenceRepository.saveAndFlush(natureAbsence);

        // Get the natureAbsence
        restNatureAbsenceMockMvc
            .perform(get(ENTITY_API_URL_ID, natureAbsence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(natureAbsence.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.nbDays").value(DEFAULT_NB_DAYS))
            .andExpect(jsonPath("$.valuePaied").value(DEFAULT_VALUE_PAIED.doubleValue()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingNatureAbsence() throws Exception {
        // Get the natureAbsence
        restNatureAbsenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNatureAbsence() throws Exception {
        // Initialize the database
        natureAbsenceRepository.saveAndFlush(natureAbsence);

        int databaseSizeBeforeUpdate = natureAbsenceRepository.findAll().size();

        // Update the natureAbsence
        NatureAbsence updatedNatureAbsence = natureAbsenceRepository.findById(natureAbsence.getId()).get();
        // Disconnect from session so that the updates on updatedNatureAbsence are not directly saved in db
        em.detach(updatedNatureAbsence);
        updatedNatureAbsence
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .nbDays(UPDATED_NB_DAYS)
            .valuePaied(UPDATED_VALUE_PAIED)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restNatureAbsenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNatureAbsence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNatureAbsence))
            )
            .andExpect(status().isOk());

        // Validate the NatureAbsence in the database
        List<NatureAbsence> natureAbsenceList = natureAbsenceRepository.findAll();
        assertThat(natureAbsenceList).hasSize(databaseSizeBeforeUpdate);
        NatureAbsence testNatureAbsence = natureAbsenceList.get(natureAbsenceList.size() - 1);
        assertThat(testNatureAbsence.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureAbsence.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureAbsence.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureAbsence.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNatureAbsence.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureAbsence.getNbDays()).isEqualTo(UPDATED_NB_DAYS);
        assertThat(testNatureAbsence.getValuePaied()).isEqualTo(UPDATED_VALUE_PAIED);
        assertThat(testNatureAbsence.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureAbsence.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureAbsence.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNatureAbsence.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNatureAbsence.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingNatureAbsence() throws Exception {
        int databaseSizeBeforeUpdate = natureAbsenceRepository.findAll().size();
        natureAbsence.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureAbsenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureAbsence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureAbsence))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAbsence in the database
        List<NatureAbsence> natureAbsenceList = natureAbsenceRepository.findAll();
        assertThat(natureAbsenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNatureAbsence() throws Exception {
        int databaseSizeBeforeUpdate = natureAbsenceRepository.findAll().size();
        natureAbsence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAbsenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureAbsence))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAbsence in the database
        List<NatureAbsence> natureAbsenceList = natureAbsenceRepository.findAll();
        assertThat(natureAbsenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNatureAbsence() throws Exception {
        int databaseSizeBeforeUpdate = natureAbsenceRepository.findAll().size();
        natureAbsence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAbsenceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureAbsence)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureAbsence in the database
        List<NatureAbsence> natureAbsenceList = natureAbsenceRepository.findAll();
        assertThat(natureAbsenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNatureAbsenceWithPatch() throws Exception {
        // Initialize the database
        natureAbsenceRepository.saveAndFlush(natureAbsence);

        int databaseSizeBeforeUpdate = natureAbsenceRepository.findAll().size();

        // Update the natureAbsence using partial update
        NatureAbsence partialUpdatedNatureAbsence = new NatureAbsence();
        partialUpdatedNatureAbsence.setId(natureAbsence.getId());

        partialUpdatedNatureAbsence
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .util(UPDATED_UTIL)
            .nbDays(UPDATED_NB_DAYS)
            .valuePaied(UPDATED_VALUE_PAIED)
            .isDeleted(UPDATED_IS_DELETED);

        restNatureAbsenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureAbsence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureAbsence))
            )
            .andExpect(status().isOk());

        // Validate the NatureAbsence in the database
        List<NatureAbsence> natureAbsenceList = natureAbsenceRepository.findAll();
        assertThat(natureAbsenceList).hasSize(databaseSizeBeforeUpdate);
        NatureAbsence testNatureAbsence = natureAbsenceList.get(natureAbsenceList.size() - 1);
        assertThat(testNatureAbsence.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureAbsence.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureAbsence.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testNatureAbsence.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNatureAbsence.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testNatureAbsence.getNbDays()).isEqualTo(UPDATED_NB_DAYS);
        assertThat(testNatureAbsence.getValuePaied()).isEqualTo(UPDATED_VALUE_PAIED);
        assertThat(testNatureAbsence.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testNatureAbsence.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testNatureAbsence.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNatureAbsence.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNatureAbsence.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateNatureAbsenceWithPatch() throws Exception {
        // Initialize the database
        natureAbsenceRepository.saveAndFlush(natureAbsence);

        int databaseSizeBeforeUpdate = natureAbsenceRepository.findAll().size();

        // Update the natureAbsence using partial update
        NatureAbsence partialUpdatedNatureAbsence = new NatureAbsence();
        partialUpdatedNatureAbsence.setId(natureAbsence.getId());

        partialUpdatedNatureAbsence
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .nbDays(UPDATED_NB_DAYS)
            .valuePaied(UPDATED_VALUE_PAIED)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restNatureAbsenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureAbsence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureAbsence))
            )
            .andExpect(status().isOk());

        // Validate the NatureAbsence in the database
        List<NatureAbsence> natureAbsenceList = natureAbsenceRepository.findAll();
        assertThat(natureAbsenceList).hasSize(databaseSizeBeforeUpdate);
        NatureAbsence testNatureAbsence = natureAbsenceList.get(natureAbsenceList.size() - 1);
        assertThat(testNatureAbsence.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureAbsence.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureAbsence.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureAbsence.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNatureAbsence.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureAbsence.getNbDays()).isEqualTo(UPDATED_NB_DAYS);
        assertThat(testNatureAbsence.getValuePaied()).isEqualTo(UPDATED_VALUE_PAIED);
        assertThat(testNatureAbsence.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureAbsence.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureAbsence.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNatureAbsence.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNatureAbsence.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingNatureAbsence() throws Exception {
        int databaseSizeBeforeUpdate = natureAbsenceRepository.findAll().size();
        natureAbsence.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureAbsenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, natureAbsence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureAbsence))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAbsence in the database
        List<NatureAbsence> natureAbsenceList = natureAbsenceRepository.findAll();
        assertThat(natureAbsenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNatureAbsence() throws Exception {
        int databaseSizeBeforeUpdate = natureAbsenceRepository.findAll().size();
        natureAbsence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAbsenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureAbsence))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureAbsence in the database
        List<NatureAbsence> natureAbsenceList = natureAbsenceRepository.findAll();
        assertThat(natureAbsenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNatureAbsence() throws Exception {
        int databaseSizeBeforeUpdate = natureAbsenceRepository.findAll().size();
        natureAbsence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureAbsenceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(natureAbsence))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureAbsence in the database
        List<NatureAbsence> natureAbsenceList = natureAbsenceRepository.findAll();
        assertThat(natureAbsenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNatureAbsence() throws Exception {
        // Initialize the database
        natureAbsenceRepository.saveAndFlush(natureAbsence);

        int databaseSizeBeforeDelete = natureAbsenceRepository.findAll().size();

        // Delete the natureAbsence
        restNatureAbsenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, natureAbsence.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NatureAbsence> natureAbsenceList = natureAbsenceRepository.findAll();
        assertThat(natureAbsenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
