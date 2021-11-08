package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.NatureConfig;
import com.issa.payroll.repository.NatureConfigRepository;
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
 * Integration tests for the {@link NatureConfigResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class NatureConfigResourceIT {

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

    private static final String ENTITY_API_URL = "/api/nature-configs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NatureConfigRepository natureConfigRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNatureConfigMockMvc;

    private NatureConfig natureConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureConfig createEntity(EntityManager em) {
        NatureConfig natureConfig = new NatureConfig()
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
        return natureConfig;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureConfig createUpdatedEntity(EntityManager em) {
        NatureConfig natureConfig = new NatureConfig()
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
        return natureConfig;
    }

    @BeforeEach
    public void initTest() {
        natureConfig = createEntity(em);
    }

    @Test
    @Transactional
    void createNatureConfig() throws Exception {
        int databaseSizeBeforeCreate = natureConfigRepository.findAll().size();
        // Create the NatureConfig
        restNatureConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureConfig)))
            .andExpect(status().isCreated());

        // Validate the NatureConfig in the database
        List<NatureConfig> natureConfigList = natureConfigRepository.findAll();
        assertThat(natureConfigList).hasSize(databaseSizeBeforeCreate + 1);
        NatureConfig testNatureConfig = natureConfigList.get(natureConfigList.size() - 1);
        assertThat(testNatureConfig.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNatureConfig.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testNatureConfig.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testNatureConfig.getLibFr()).isEqualTo(DEFAULT_LIB_FR);
        assertThat(testNatureConfig.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testNatureConfig.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testNatureConfig.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testNatureConfig.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testNatureConfig.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testNatureConfig.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testNatureConfig.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testNatureConfig.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createNatureConfigWithExistingId() throws Exception {
        // Create the NatureConfig with an existing ID
        natureConfig.setId(1L);

        int databaseSizeBeforeCreate = natureConfigRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatureConfigMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureConfig)))
            .andExpect(status().isBadRequest());

        // Validate the NatureConfig in the database
        List<NatureConfig> natureConfigList = natureConfigRepository.findAll();
        assertThat(natureConfigList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNatureConfigs() throws Exception {
        // Initialize the database
        natureConfigRepository.saveAndFlush(natureConfig);

        // Get all the natureConfigList
        restNatureConfigMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natureConfig.getId().intValue())))
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
    void getNatureConfig() throws Exception {
        // Initialize the database
        natureConfigRepository.saveAndFlush(natureConfig);

        // Get the natureConfig
        restNatureConfigMockMvc
            .perform(get(ENTITY_API_URL_ID, natureConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(natureConfig.getId().intValue()))
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
    void getNonExistingNatureConfig() throws Exception {
        // Get the natureConfig
        restNatureConfigMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNatureConfig() throws Exception {
        // Initialize the database
        natureConfigRepository.saveAndFlush(natureConfig);

        int databaseSizeBeforeUpdate = natureConfigRepository.findAll().size();

        // Update the natureConfig
        NatureConfig updatedNatureConfig = natureConfigRepository.findById(natureConfig.getId()).get();
        // Disconnect from session so that the updates on updatedNatureConfig are not directly saved in db
        em.detach(updatedNatureConfig);
        updatedNatureConfig
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

        restNatureConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedNatureConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedNatureConfig))
            )
            .andExpect(status().isOk());

        // Validate the NatureConfig in the database
        List<NatureConfig> natureConfigList = natureConfigRepository.findAll();
        assertThat(natureConfigList).hasSize(databaseSizeBeforeUpdate);
        NatureConfig testNatureConfig = natureConfigList.get(natureConfigList.size() - 1);
        assertThat(testNatureConfig.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureConfig.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureConfig.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureConfig.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testNatureConfig.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureConfig.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureConfig.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNatureConfig.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureConfig.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNatureConfig.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNatureConfig.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNatureConfig.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingNatureConfig() throws Exception {
        int databaseSizeBeforeUpdate = natureConfigRepository.findAll().size();
        natureConfig.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureConfig.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureConfig in the database
        List<NatureConfig> natureConfigList = natureConfigRepository.findAll();
        assertThat(natureConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNatureConfig() throws Exception {
        int databaseSizeBeforeUpdate = natureConfigRepository.findAll().size();
        natureConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureConfigMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureConfig in the database
        List<NatureConfig> natureConfigList = natureConfigRepository.findAll();
        assertThat(natureConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNatureConfig() throws Exception {
        int databaseSizeBeforeUpdate = natureConfigRepository.findAll().size();
        natureConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureConfigMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureConfig)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureConfig in the database
        List<NatureConfig> natureConfigList = natureConfigRepository.findAll();
        assertThat(natureConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNatureConfigWithPatch() throws Exception {
        // Initialize the database
        natureConfigRepository.saveAndFlush(natureConfig);

        int databaseSizeBeforeUpdate = natureConfigRepository.findAll().size();

        // Update the natureConfig using partial update
        NatureConfig partialUpdatedNatureConfig = new NatureConfig();
        partialUpdatedNatureConfig.setId(natureConfig.getId());

        partialUpdatedNatureConfig
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .createdDate(UPDATED_CREATED_DATE);

        restNatureConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureConfig))
            )
            .andExpect(status().isOk());

        // Validate the NatureConfig in the database
        List<NatureConfig> natureConfigList = natureConfigRepository.findAll();
        assertThat(natureConfigList).hasSize(databaseSizeBeforeUpdate);
        NatureConfig testNatureConfig = natureConfigList.get(natureConfigList.size() - 1);
        assertThat(testNatureConfig.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureConfig.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureConfig.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureConfig.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testNatureConfig.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureConfig.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureConfig.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testNatureConfig.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureConfig.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testNatureConfig.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testNatureConfig.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNatureConfig.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateNatureConfigWithPatch() throws Exception {
        // Initialize the database
        natureConfigRepository.saveAndFlush(natureConfig);

        int databaseSizeBeforeUpdate = natureConfigRepository.findAll().size();

        // Update the natureConfig using partial update
        NatureConfig partialUpdatedNatureConfig = new NatureConfig();
        partialUpdatedNatureConfig.setId(natureConfig.getId());

        partialUpdatedNatureConfig
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

        restNatureConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureConfig))
            )
            .andExpect(status().isOk());

        // Validate the NatureConfig in the database
        List<NatureConfig> natureConfigList = natureConfigRepository.findAll();
        assertThat(natureConfigList).hasSize(databaseSizeBeforeUpdate);
        NatureConfig testNatureConfig = natureConfigList.get(natureConfigList.size() - 1);
        assertThat(testNatureConfig.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNatureConfig.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testNatureConfig.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testNatureConfig.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testNatureConfig.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testNatureConfig.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testNatureConfig.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testNatureConfig.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testNatureConfig.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testNatureConfig.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testNatureConfig.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testNatureConfig.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingNatureConfig() throws Exception {
        int databaseSizeBeforeUpdate = natureConfigRepository.findAll().size();
        natureConfig.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, natureConfig.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureConfig in the database
        List<NatureConfig> natureConfigList = natureConfigRepository.findAll();
        assertThat(natureConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNatureConfig() throws Exception {
        int databaseSizeBeforeUpdate = natureConfigRepository.findAll().size();
        natureConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureConfigMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureConfig))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureConfig in the database
        List<NatureConfig> natureConfigList = natureConfigRepository.findAll();
        assertThat(natureConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNatureConfig() throws Exception {
        int databaseSizeBeforeUpdate = natureConfigRepository.findAll().size();
        natureConfig.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureConfigMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(natureConfig))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureConfig in the database
        List<NatureConfig> natureConfigList = natureConfigRepository.findAll();
        assertThat(natureConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNatureConfig() throws Exception {
        // Initialize the database
        natureConfigRepository.saveAndFlush(natureConfig);

        int databaseSizeBeforeDelete = natureConfigRepository.findAll().size();

        // Delete the natureConfig
        restNatureConfigMockMvc
            .perform(delete(ENTITY_API_URL_ID, natureConfig.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NatureConfig> natureConfigList = natureConfigRepository.findAll();
        assertThat(natureConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
