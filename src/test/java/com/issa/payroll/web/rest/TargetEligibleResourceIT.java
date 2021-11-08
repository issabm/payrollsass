package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.TargetEligible;
import com.issa.payroll.repository.TargetEligibleRepository;
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
 * Integration tests for the {@link TargetEligibleResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TargetEligibleResourceIT {

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

    private static final String ENTITY_API_URL = "/api/target-eligibles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TargetEligibleRepository targetEligibleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTargetEligibleMockMvc;

    private TargetEligible targetEligible;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TargetEligible createEntity(EntityManager em) {
        TargetEligible targetEligible = new TargetEligible()
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
        return targetEligible;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TargetEligible createUpdatedEntity(EntityManager em) {
        TargetEligible targetEligible = new TargetEligible()
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
        return targetEligible;
    }

    @BeforeEach
    public void initTest() {
        targetEligible = createEntity(em);
    }

    @Test
    @Transactional
    void createTargetEligible() throws Exception {
        int databaseSizeBeforeCreate = targetEligibleRepository.findAll().size();
        // Create the TargetEligible
        restTargetEligibleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(targetEligible))
            )
            .andExpect(status().isCreated());

        // Validate the TargetEligible in the database
        List<TargetEligible> targetEligibleList = targetEligibleRepository.findAll();
        assertThat(targetEligibleList).hasSize(databaseSizeBeforeCreate + 1);
        TargetEligible testTargetEligible = targetEligibleList.get(targetEligibleList.size() - 1);
        assertThat(testTargetEligible.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTargetEligible.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testTargetEligible.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testTargetEligible.getLibFr()).isEqualTo(DEFAULT_LIB_FR);
        assertThat(testTargetEligible.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testTargetEligible.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTargetEligible.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTargetEligible.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testTargetEligible.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testTargetEligible.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTargetEligible.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTargetEligible.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createTargetEligibleWithExistingId() throws Exception {
        // Create the TargetEligible with an existing ID
        targetEligible.setId(1L);

        int databaseSizeBeforeCreate = targetEligibleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTargetEligibleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(targetEligible))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetEligible in the database
        List<TargetEligible> targetEligibleList = targetEligibleRepository.findAll();
        assertThat(targetEligibleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTargetEligibles() throws Exception {
        // Initialize the database
        targetEligibleRepository.saveAndFlush(targetEligible);

        // Get all the targetEligibleList
        restTargetEligibleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(targetEligible.getId().intValue())))
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
    void getTargetEligible() throws Exception {
        // Initialize the database
        targetEligibleRepository.saveAndFlush(targetEligible);

        // Get the targetEligible
        restTargetEligibleMockMvc
            .perform(get(ENTITY_API_URL_ID, targetEligible.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(targetEligible.getId().intValue()))
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
    void getNonExistingTargetEligible() throws Exception {
        // Get the targetEligible
        restTargetEligibleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTargetEligible() throws Exception {
        // Initialize the database
        targetEligibleRepository.saveAndFlush(targetEligible);

        int databaseSizeBeforeUpdate = targetEligibleRepository.findAll().size();

        // Update the targetEligible
        TargetEligible updatedTargetEligible = targetEligibleRepository.findById(targetEligible.getId()).get();
        // Disconnect from session so that the updates on updatedTargetEligible are not directly saved in db
        em.detach(updatedTargetEligible);
        updatedTargetEligible
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

        restTargetEligibleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTargetEligible.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTargetEligible))
            )
            .andExpect(status().isOk());

        // Validate the TargetEligible in the database
        List<TargetEligible> targetEligibleList = targetEligibleRepository.findAll();
        assertThat(targetEligibleList).hasSize(databaseSizeBeforeUpdate);
        TargetEligible testTargetEligible = targetEligibleList.get(targetEligibleList.size() - 1);
        assertThat(testTargetEligible.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTargetEligible.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testTargetEligible.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testTargetEligible.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testTargetEligible.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testTargetEligible.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTargetEligible.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTargetEligible.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testTargetEligible.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testTargetEligible.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTargetEligible.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTargetEligible.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTargetEligible() throws Exception {
        int databaseSizeBeforeUpdate = targetEligibleRepository.findAll().size();
        targetEligible.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTargetEligibleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, targetEligible.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetEligible))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetEligible in the database
        List<TargetEligible> targetEligibleList = targetEligibleRepository.findAll();
        assertThat(targetEligibleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTargetEligible() throws Exception {
        int databaseSizeBeforeUpdate = targetEligibleRepository.findAll().size();
        targetEligible.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetEligibleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(targetEligible))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetEligible in the database
        List<TargetEligible> targetEligibleList = targetEligibleRepository.findAll();
        assertThat(targetEligibleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTargetEligible() throws Exception {
        int databaseSizeBeforeUpdate = targetEligibleRepository.findAll().size();
        targetEligible.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetEligibleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(targetEligible)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TargetEligible in the database
        List<TargetEligible> targetEligibleList = targetEligibleRepository.findAll();
        assertThat(targetEligibleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTargetEligibleWithPatch() throws Exception {
        // Initialize the database
        targetEligibleRepository.saveAndFlush(targetEligible);

        int databaseSizeBeforeUpdate = targetEligibleRepository.findAll().size();

        // Update the targetEligible using partial update
        TargetEligible partialUpdatedTargetEligible = new TargetEligible();
        partialUpdatedTargetEligible.setId(targetEligible.getId());

        partialUpdatedTargetEligible
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .dateop(UPDATED_DATEOP)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .createdDate(UPDATED_CREATED_DATE);

        restTargetEligibleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTargetEligible.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTargetEligible))
            )
            .andExpect(status().isOk());

        // Validate the TargetEligible in the database
        List<TargetEligible> targetEligibleList = targetEligibleRepository.findAll();
        assertThat(targetEligibleList).hasSize(databaseSizeBeforeUpdate);
        TargetEligible testTargetEligible = targetEligibleList.get(targetEligibleList.size() - 1);
        assertThat(testTargetEligible.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTargetEligible.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testTargetEligible.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testTargetEligible.getLibFr()).isEqualTo(DEFAULT_LIB_FR);
        assertThat(testTargetEligible.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testTargetEligible.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTargetEligible.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTargetEligible.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testTargetEligible.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testTargetEligible.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTargetEligible.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTargetEligible.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTargetEligibleWithPatch() throws Exception {
        // Initialize the database
        targetEligibleRepository.saveAndFlush(targetEligible);

        int databaseSizeBeforeUpdate = targetEligibleRepository.findAll().size();

        // Update the targetEligible using partial update
        TargetEligible partialUpdatedTargetEligible = new TargetEligible();
        partialUpdatedTargetEligible.setId(targetEligible.getId());

        partialUpdatedTargetEligible
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

        restTargetEligibleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTargetEligible.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTargetEligible))
            )
            .andExpect(status().isOk());

        // Validate the TargetEligible in the database
        List<TargetEligible> targetEligibleList = targetEligibleRepository.findAll();
        assertThat(targetEligibleList).hasSize(databaseSizeBeforeUpdate);
        TargetEligible testTargetEligible = targetEligibleList.get(targetEligibleList.size() - 1);
        assertThat(testTargetEligible.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTargetEligible.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testTargetEligible.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testTargetEligible.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testTargetEligible.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testTargetEligible.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTargetEligible.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTargetEligible.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testTargetEligible.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testTargetEligible.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTargetEligible.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTargetEligible.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTargetEligible() throws Exception {
        int databaseSizeBeforeUpdate = targetEligibleRepository.findAll().size();
        targetEligible.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTargetEligibleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, targetEligible.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(targetEligible))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetEligible in the database
        List<TargetEligible> targetEligibleList = targetEligibleRepository.findAll();
        assertThat(targetEligibleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTargetEligible() throws Exception {
        int databaseSizeBeforeUpdate = targetEligibleRepository.findAll().size();
        targetEligible.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetEligibleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(targetEligible))
            )
            .andExpect(status().isBadRequest());

        // Validate the TargetEligible in the database
        List<TargetEligible> targetEligibleList = targetEligibleRepository.findAll();
        assertThat(targetEligibleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTargetEligible() throws Exception {
        int databaseSizeBeforeUpdate = targetEligibleRepository.findAll().size();
        targetEligible.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTargetEligibleMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(targetEligible))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TargetEligible in the database
        List<TargetEligible> targetEligibleList = targetEligibleRepository.findAll();
        assertThat(targetEligibleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTargetEligible() throws Exception {
        // Initialize the database
        targetEligibleRepository.saveAndFlush(targetEligible);

        int databaseSizeBeforeDelete = targetEligibleRepository.findAll().size();

        // Delete the targetEligible
        restTargetEligibleMockMvc
            .perform(delete(ENTITY_API_URL_ID, targetEligible.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TargetEligible> targetEligibleList = targetEligibleRepository.findAll();
        assertThat(targetEligibleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
