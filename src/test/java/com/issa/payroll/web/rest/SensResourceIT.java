package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Sens;
import com.issa.payroll.repository.SensRepository;
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
 * Integration tests for the {@link SensResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SensResourceIT {

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

    private static final String ENTITY_API_URL = "/api/sens";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SensRepository sensRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSensMockMvc;

    private Sens sens;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sens createEntity(EntityManager em) {
        Sens sens = new Sens()
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
        return sens;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sens createUpdatedEntity(EntityManager em) {
        Sens sens = new Sens()
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
        return sens;
    }

    @BeforeEach
    public void initTest() {
        sens = createEntity(em);
    }

    @Test
    @Transactional
    void createSens() throws Exception {
        int databaseSizeBeforeCreate = sensRepository.findAll().size();
        // Create the Sens
        restSensMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sens)))
            .andExpect(status().isCreated());

        // Validate the Sens in the database
        List<Sens> sensList = sensRepository.findAll();
        assertThat(sensList).hasSize(databaseSizeBeforeCreate + 1);
        Sens testSens = sensList.get(sensList.size() - 1);
        assertThat(testSens.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSens.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testSens.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testSens.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testSens.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testSens.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSens.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testSens.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSens.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSens.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSensWithExistingId() throws Exception {
        // Create the Sens with an existing ID
        sens.setId(1L);

        int databaseSizeBeforeCreate = sensRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSensMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sens)))
            .andExpect(status().isBadRequest());

        // Validate the Sens in the database
        List<Sens> sensList = sensRepository.findAll();
        assertThat(sensList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSens() throws Exception {
        // Initialize the database
        sensRepository.saveAndFlush(sens);

        // Get all the sensList
        restSensMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sens.getId().intValue())))
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
    void getSens() throws Exception {
        // Initialize the database
        sensRepository.saveAndFlush(sens);

        // Get the sens
        restSensMockMvc
            .perform(get(ENTITY_API_URL_ID, sens.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sens.getId().intValue()))
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
    void getNonExistingSens() throws Exception {
        // Get the sens
        restSensMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSens() throws Exception {
        // Initialize the database
        sensRepository.saveAndFlush(sens);

        int databaseSizeBeforeUpdate = sensRepository.findAll().size();

        // Update the sens
        Sens updatedSens = sensRepository.findById(sens.getId()).get();
        // Disconnect from session so that the updates on updatedSens are not directly saved in db
        em.detach(updatedSens);
        updatedSens
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

        restSensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSens.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSens))
            )
            .andExpect(status().isOk());

        // Validate the Sens in the database
        List<Sens> sensList = sensRepository.findAll();
        assertThat(sensList).hasSize(databaseSizeBeforeUpdate);
        Sens testSens = sensList.get(sensList.size() - 1);
        assertThat(testSens.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSens.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testSens.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testSens.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSens.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSens.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSens.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSens.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSens.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSens.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSens() throws Exception {
        int databaseSizeBeforeUpdate = sensRepository.findAll().size();
        sens.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sens.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sens in the database
        List<Sens> sensList = sensRepository.findAll();
        assertThat(sensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSens() throws Exception {
        int databaseSizeBeforeUpdate = sensRepository.findAll().size();
        sens.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sens in the database
        List<Sens> sensList = sensRepository.findAll();
        assertThat(sensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSens() throws Exception {
        int databaseSizeBeforeUpdate = sensRepository.findAll().size();
        sens.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sens)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sens in the database
        List<Sens> sensList = sensRepository.findAll();
        assertThat(sensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSensWithPatch() throws Exception {
        // Initialize the database
        sensRepository.saveAndFlush(sens);

        int databaseSizeBeforeUpdate = sensRepository.findAll().size();

        // Update the sens using partial update
        Sens partialUpdatedSens = new Sens();
        partialUpdatedSens.setId(sens.getId());

        partialUpdatedSens.libEn(UPDATED_LIB_EN).isDeleted(UPDATED_IS_DELETED);

        restSensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSens))
            )
            .andExpect(status().isOk());

        // Validate the Sens in the database
        List<Sens> sensList = sensRepository.findAll();
        assertThat(sensList).hasSize(databaseSizeBeforeUpdate);
        Sens testSens = sensList.get(sensList.size() - 1);
        assertThat(testSens.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSens.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testSens.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testSens.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testSens.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testSens.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSens.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testSens.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSens.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSens.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSensWithPatch() throws Exception {
        // Initialize the database
        sensRepository.saveAndFlush(sens);

        int databaseSizeBeforeUpdate = sensRepository.findAll().size();

        // Update the sens using partial update
        Sens partialUpdatedSens = new Sens();
        partialUpdatedSens.setId(sens.getId());

        partialUpdatedSens
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

        restSensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSens))
            )
            .andExpect(status().isOk());

        // Validate the Sens in the database
        List<Sens> sensList = sensRepository.findAll();
        assertThat(sensList).hasSize(databaseSizeBeforeUpdate);
        Sens testSens = sensList.get(sensList.size() - 1);
        assertThat(testSens.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSens.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testSens.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testSens.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSens.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSens.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSens.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSens.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSens.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSens.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSens() throws Exception {
        int databaseSizeBeforeUpdate = sensRepository.findAll().size();
        sens.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sens.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sens in the database
        List<Sens> sensList = sensRepository.findAll();
        assertThat(sensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSens() throws Exception {
        int databaseSizeBeforeUpdate = sensRepository.findAll().size();
        sens.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sens))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sens in the database
        List<Sens> sensList = sensRepository.findAll();
        assertThat(sensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSens() throws Exception {
        int databaseSizeBeforeUpdate = sensRepository.findAll().size();
        sens.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSensMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sens)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sens in the database
        List<Sens> sensList = sensRepository.findAll();
        assertThat(sensList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSens() throws Exception {
        // Initialize the database
        sensRepository.saveAndFlush(sens);

        int databaseSizeBeforeDelete = sensRepository.findAll().size();

        // Delete the sens
        restSensMockMvc
            .perform(delete(ENTITY_API_URL_ID, sens.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sens> sensList = sensRepository.findAll();
        assertThat(sensList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
