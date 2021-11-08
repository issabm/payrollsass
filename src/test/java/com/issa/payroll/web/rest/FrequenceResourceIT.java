package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Frequence;
import com.issa.payroll.repository.FrequenceRepository;
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
 * Integration tests for the {@link FrequenceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FrequenceResourceIT {

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

    private static final String ENTITY_API_URL = "/api/frequences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FrequenceRepository frequenceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFrequenceMockMvc;

    private Frequence frequence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frequence createEntity(EntityManager em) {
        Frequence frequence = new Frequence()
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
        return frequence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Frequence createUpdatedEntity(EntityManager em) {
        Frequence frequence = new Frequence()
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
        return frequence;
    }

    @BeforeEach
    public void initTest() {
        frequence = createEntity(em);
    }

    @Test
    @Transactional
    void createFrequence() throws Exception {
        int databaseSizeBeforeCreate = frequenceRepository.findAll().size();
        // Create the Frequence
        restFrequenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frequence)))
            .andExpect(status().isCreated());

        // Validate the Frequence in the database
        List<Frequence> frequenceList = frequenceRepository.findAll();
        assertThat(frequenceList).hasSize(databaseSizeBeforeCreate + 1);
        Frequence testFrequence = frequenceList.get(frequenceList.size() - 1);
        assertThat(testFrequence.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFrequence.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testFrequence.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testFrequence.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testFrequence.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testFrequence.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testFrequence.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testFrequence.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testFrequence.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFrequence.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createFrequenceWithExistingId() throws Exception {
        // Create the Frequence with an existing ID
        frequence.setId(1L);

        int databaseSizeBeforeCreate = frequenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFrequenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frequence)))
            .andExpect(status().isBadRequest());

        // Validate the Frequence in the database
        List<Frequence> frequenceList = frequenceRepository.findAll();
        assertThat(frequenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFrequences() throws Exception {
        // Initialize the database
        frequenceRepository.saveAndFlush(frequence);

        // Get all the frequenceList
        restFrequenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(frequence.getId().intValue())))
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
    void getFrequence() throws Exception {
        // Initialize the database
        frequenceRepository.saveAndFlush(frequence);

        // Get the frequence
        restFrequenceMockMvc
            .perform(get(ENTITY_API_URL_ID, frequence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(frequence.getId().intValue()))
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
    void getNonExistingFrequence() throws Exception {
        // Get the frequence
        restFrequenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFrequence() throws Exception {
        // Initialize the database
        frequenceRepository.saveAndFlush(frequence);

        int databaseSizeBeforeUpdate = frequenceRepository.findAll().size();

        // Update the frequence
        Frequence updatedFrequence = frequenceRepository.findById(frequence.getId()).get();
        // Disconnect from session so that the updates on updatedFrequence are not directly saved in db
        em.detach(updatedFrequence);
        updatedFrequence
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

        restFrequenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFrequence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFrequence))
            )
            .andExpect(status().isOk());

        // Validate the Frequence in the database
        List<Frequence> frequenceList = frequenceRepository.findAll();
        assertThat(frequenceList).hasSize(databaseSizeBeforeUpdate);
        Frequence testFrequence = frequenceList.get(frequenceList.size() - 1);
        assertThat(testFrequence.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFrequence.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testFrequence.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testFrequence.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testFrequence.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testFrequence.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFrequence.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testFrequence.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testFrequence.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFrequence.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingFrequence() throws Exception {
        int databaseSizeBeforeUpdate = frequenceRepository.findAll().size();
        frequence.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFrequenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, frequence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(frequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frequence in the database
        List<Frequence> frequenceList = frequenceRepository.findAll();
        assertThat(frequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFrequence() throws Exception {
        int databaseSizeBeforeUpdate = frequenceRepository.findAll().size();
        frequence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrequenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(frequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frequence in the database
        List<Frequence> frequenceList = frequenceRepository.findAll();
        assertThat(frequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFrequence() throws Exception {
        int databaseSizeBeforeUpdate = frequenceRepository.findAll().size();
        frequence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrequenceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(frequence)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Frequence in the database
        List<Frequence> frequenceList = frequenceRepository.findAll();
        assertThat(frequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFrequenceWithPatch() throws Exception {
        // Initialize the database
        frequenceRepository.saveAndFlush(frequence);

        int databaseSizeBeforeUpdate = frequenceRepository.findAll().size();

        // Update the frequence using partial update
        Frequence partialUpdatedFrequence = new Frequence();
        partialUpdatedFrequence.setId(frequence.getId());

        partialUpdatedFrequence.code(UPDATED_CODE).util(UPDATED_UTIL).isDeleted(UPDATED_IS_DELETED).modifiedDate(UPDATED_MODIFIED_DATE);

        restFrequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFrequence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFrequence))
            )
            .andExpect(status().isOk());

        // Validate the Frequence in the database
        List<Frequence> frequenceList = frequenceRepository.findAll();
        assertThat(frequenceList).hasSize(databaseSizeBeforeUpdate);
        Frequence testFrequence = frequenceList.get(frequenceList.size() - 1);
        assertThat(testFrequence.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFrequence.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testFrequence.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testFrequence.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testFrequence.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testFrequence.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testFrequence.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testFrequence.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testFrequence.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFrequence.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateFrequenceWithPatch() throws Exception {
        // Initialize the database
        frequenceRepository.saveAndFlush(frequence);

        int databaseSizeBeforeUpdate = frequenceRepository.findAll().size();

        // Update the frequence using partial update
        Frequence partialUpdatedFrequence = new Frequence();
        partialUpdatedFrequence.setId(frequence.getId());

        partialUpdatedFrequence
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

        restFrequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFrequence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFrequence))
            )
            .andExpect(status().isOk());

        // Validate the Frequence in the database
        List<Frequence> frequenceList = frequenceRepository.findAll();
        assertThat(frequenceList).hasSize(databaseSizeBeforeUpdate);
        Frequence testFrequence = frequenceList.get(frequenceList.size() - 1);
        assertThat(testFrequence.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFrequence.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testFrequence.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testFrequence.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testFrequence.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testFrequence.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFrequence.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testFrequence.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testFrequence.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFrequence.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingFrequence() throws Exception {
        int databaseSizeBeforeUpdate = frequenceRepository.findAll().size();
        frequence.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFrequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, frequence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(frequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frequence in the database
        List<Frequence> frequenceList = frequenceRepository.findAll();
        assertThat(frequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFrequence() throws Exception {
        int databaseSizeBeforeUpdate = frequenceRepository.findAll().size();
        frequence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrequenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(frequence))
            )
            .andExpect(status().isBadRequest());

        // Validate the Frequence in the database
        List<Frequence> frequenceList = frequenceRepository.findAll();
        assertThat(frequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFrequence() throws Exception {
        int databaseSizeBeforeUpdate = frequenceRepository.findAll().size();
        frequence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFrequenceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(frequence))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Frequence in the database
        List<Frequence> frequenceList = frequenceRepository.findAll();
        assertThat(frequenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFrequence() throws Exception {
        // Initialize the database
        frequenceRepository.saveAndFlush(frequence);

        int databaseSizeBeforeDelete = frequenceRepository.findAll().size();

        // Delete the frequence
        restFrequenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, frequence.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Frequence> frequenceList = frequenceRepository.findAll();
        assertThat(frequenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
