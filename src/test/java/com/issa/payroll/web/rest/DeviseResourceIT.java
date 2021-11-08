package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Devise;
import com.issa.payroll.repository.DeviseRepository;
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
 * Integration tests for the {@link DeviseResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DeviseResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATEOP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/devises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DeviseRepository deviseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDeviseMockMvc;

    private Devise devise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Devise createEntity(EntityManager em) {
        Devise devise = new Devise()
            .code(DEFAULT_CODE)
            .libAr(DEFAULT_LIB_AR)
            .libEn(DEFAULT_LIB_EN)
            .dateop(DEFAULT_DATEOP)
            .util(DEFAULT_UTIL)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return devise;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Devise createUpdatedEntity(EntityManager em) {
        Devise devise = new Devise()
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return devise;
    }

    @BeforeEach
    public void initTest() {
        devise = createEntity(em);
    }

    @Test
    @Transactional
    void createDevise() throws Exception {
        int databaseSizeBeforeCreate = deviseRepository.findAll().size();
        // Create the Devise
        restDeviseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devise)))
            .andExpect(status().isCreated());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeCreate + 1);
        Devise testDevise = deviseList.get(deviseList.size() - 1);
        assertThat(testDevise.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDevise.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testDevise.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testDevise.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testDevise.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testDevise.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testDevise.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testDevise.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testDevise.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDevise.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createDeviseWithExistingId() throws Exception {
        // Create the Devise with an existing ID
        devise.setId(1L);

        int databaseSizeBeforeCreate = deviseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDeviseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devise)))
            .andExpect(status().isBadRequest());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDevises() throws Exception {
        // Initialize the database
        deviseRepository.saveAndFlush(devise);

        // Get all the deviseList
        restDeviseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(devise.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    void getDevise() throws Exception {
        // Initialize the database
        deviseRepository.saveAndFlush(devise);

        // Get the devise
        restDeviseMockMvc
            .perform(get(ENTITY_API_URL_ID, devise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(devise.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingDevise() throws Exception {
        // Get the devise
        restDeviseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDevise() throws Exception {
        // Initialize the database
        deviseRepository.saveAndFlush(devise);

        int databaseSizeBeforeUpdate = deviseRepository.findAll().size();

        // Update the devise
        Devise updatedDevise = deviseRepository.findById(devise.getId()).get();
        // Disconnect from session so that the updates on updatedDevise are not directly saved in db
        em.detach(updatedDevise);
        updatedDevise
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restDeviseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDevise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDevise))
            )
            .andExpect(status().isOk());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeUpdate);
        Devise testDevise = deviseList.get(deviseList.size() - 1);
        assertThat(testDevise.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDevise.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testDevise.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testDevise.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testDevise.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testDevise.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDevise.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testDevise.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDevise.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDevise.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingDevise() throws Exception {
        int databaseSizeBeforeUpdate = deviseRepository.findAll().size();
        devise.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, devise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDevise() throws Exception {
        int databaseSizeBeforeUpdate = deviseRepository.findAll().size();
        devise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(devise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDevise() throws Exception {
        int databaseSizeBeforeUpdate = deviseRepository.findAll().size();
        devise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(devise)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDeviseWithPatch() throws Exception {
        // Initialize the database
        deviseRepository.saveAndFlush(devise);

        int databaseSizeBeforeUpdate = deviseRepository.findAll().size();

        // Update the devise using partial update
        Devise partialUpdatedDevise = new Devise();
        partialUpdatedDevise.setId(devise.getId());

        partialUpdatedDevise
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restDeviseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevise))
            )
            .andExpect(status().isOk());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeUpdate);
        Devise testDevise = deviseList.get(deviseList.size() - 1);
        assertThat(testDevise.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDevise.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testDevise.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testDevise.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testDevise.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testDevise.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDevise.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testDevise.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDevise.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDevise.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateDeviseWithPatch() throws Exception {
        // Initialize the database
        deviseRepository.saveAndFlush(devise);

        int databaseSizeBeforeUpdate = deviseRepository.findAll().size();

        // Update the devise using partial update
        Devise partialUpdatedDevise = new Devise();
        partialUpdatedDevise.setId(devise.getId());

        partialUpdatedDevise
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restDeviseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDevise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDevise))
            )
            .andExpect(status().isOk());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeUpdate);
        Devise testDevise = deviseList.get(deviseList.size() - 1);
        assertThat(testDevise.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDevise.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testDevise.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testDevise.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testDevise.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testDevise.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDevise.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testDevise.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDevise.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDevise.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingDevise() throws Exception {
        int databaseSizeBeforeUpdate = deviseRepository.findAll().size();
        devise.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDeviseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, devise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(devise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDevise() throws Exception {
        int databaseSizeBeforeUpdate = deviseRepository.findAll().size();
        devise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(devise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDevise() throws Exception {
        int databaseSizeBeforeUpdate = deviseRepository.findAll().size();
        devise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDeviseMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(devise)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Devise in the database
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDevise() throws Exception {
        // Initialize the database
        deviseRepository.saveAndFlush(devise);

        int databaseSizeBeforeDelete = deviseRepository.findAll().size();

        // Delete the devise
        restDeviseMockMvc
            .perform(delete(ENTITY_API_URL_ID, devise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Devise> deviseList = deviseRepository.findAll();
        assertThat(deviseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
