package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.EntityAdhesion;
import com.issa.payroll.repository.EntityAdhesionRepository;
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
 * Integration tests for the {@link EntityAdhesionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EntityAdhesionResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_FR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_FR = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/entity-adhesions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EntityAdhesionRepository entityAdhesionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntityAdhesionMockMvc;

    private EntityAdhesion entityAdhesion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntityAdhesion createEntity(EntityManager em) {
        EntityAdhesion entityAdhesion = new EntityAdhesion()
            .code(DEFAULT_CODE)
            .libEn(DEFAULT_LIB_EN)
            .libAr(DEFAULT_LIB_AR)
            .libFr(DEFAULT_LIB_FR)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return entityAdhesion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EntityAdhesion createUpdatedEntity(EntityManager em) {
        EntityAdhesion entityAdhesion = new EntityAdhesion()
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return entityAdhesion;
    }

    @BeforeEach
    public void initTest() {
        entityAdhesion = createEntity(em);
    }

    @Test
    @Transactional
    void createEntityAdhesion() throws Exception {
        int databaseSizeBeforeCreate = entityAdhesionRepository.findAll().size();
        // Create the EntityAdhesion
        restEntityAdhesionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityAdhesion))
            )
            .andExpect(status().isCreated());

        // Validate the EntityAdhesion in the database
        List<EntityAdhesion> entityAdhesionList = entityAdhesionRepository.findAll();
        assertThat(entityAdhesionList).hasSize(databaseSizeBeforeCreate + 1);
        EntityAdhesion testEntityAdhesion = entityAdhesionList.get(entityAdhesionList.size() - 1);
        assertThat(testEntityAdhesion.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEntityAdhesion.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testEntityAdhesion.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testEntityAdhesion.getLibFr()).isEqualTo(DEFAULT_LIB_FR);
        assertThat(testEntityAdhesion.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testEntityAdhesion.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testEntityAdhesion.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testEntityAdhesion.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testEntityAdhesion.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testEntityAdhesion.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEntityAdhesion.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createEntityAdhesionWithExistingId() throws Exception {
        // Create the EntityAdhesion with an existing ID
        entityAdhesion.setId(1L);

        int databaseSizeBeforeCreate = entityAdhesionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntityAdhesionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityAdhesion))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntityAdhesion in the database
        List<EntityAdhesion> entityAdhesionList = entityAdhesionRepository.findAll();
        assertThat(entityAdhesionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEntityAdhesions() throws Exception {
        // Initialize the database
        entityAdhesionRepository.saveAndFlush(entityAdhesion);

        // Get all the entityAdhesionList
        restEntityAdhesionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entityAdhesion.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].libFr").value(hasItem(DEFAULT_LIB_FR)))
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
    void getEntityAdhesion() throws Exception {
        // Initialize the database
        entityAdhesionRepository.saveAndFlush(entityAdhesion);

        // Get the entityAdhesion
        restEntityAdhesionMockMvc
            .perform(get(ENTITY_API_URL_ID, entityAdhesion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entityAdhesion.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.libFr").value(DEFAULT_LIB_FR))
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
    void getNonExistingEntityAdhesion() throws Exception {
        // Get the entityAdhesion
        restEntityAdhesionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEntityAdhesion() throws Exception {
        // Initialize the database
        entityAdhesionRepository.saveAndFlush(entityAdhesion);

        int databaseSizeBeforeUpdate = entityAdhesionRepository.findAll().size();

        // Update the entityAdhesion
        EntityAdhesion updatedEntityAdhesion = entityAdhesionRepository.findById(entityAdhesion.getId()).get();
        // Disconnect from session so that the updates on updatedEntityAdhesion are not directly saved in db
        em.detach(updatedEntityAdhesion);
        updatedEntityAdhesion
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restEntityAdhesionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEntityAdhesion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEntityAdhesion))
            )
            .andExpect(status().isOk());

        // Validate the EntityAdhesion in the database
        List<EntityAdhesion> entityAdhesionList = entityAdhesionRepository.findAll();
        assertThat(entityAdhesionList).hasSize(databaseSizeBeforeUpdate);
        EntityAdhesion testEntityAdhesion = entityAdhesionList.get(entityAdhesionList.size() - 1);
        assertThat(testEntityAdhesion.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEntityAdhesion.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testEntityAdhesion.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testEntityAdhesion.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testEntityAdhesion.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEntityAdhesion.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEntityAdhesion.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEntityAdhesion.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEntityAdhesion.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEntityAdhesion.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEntityAdhesion.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingEntityAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = entityAdhesionRepository.findAll().size();
        entityAdhesion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntityAdhesionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entityAdhesion.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entityAdhesion))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntityAdhesion in the database
        List<EntityAdhesion> entityAdhesionList = entityAdhesionRepository.findAll();
        assertThat(entityAdhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntityAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = entityAdhesionRepository.findAll().size();
        entityAdhesion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntityAdhesionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entityAdhesion))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntityAdhesion in the database
        List<EntityAdhesion> entityAdhesionList = entityAdhesionRepository.findAll();
        assertThat(entityAdhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntityAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = entityAdhesionRepository.findAll().size();
        entityAdhesion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntityAdhesionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entityAdhesion)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EntityAdhesion in the database
        List<EntityAdhesion> entityAdhesionList = entityAdhesionRepository.findAll();
        assertThat(entityAdhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntityAdhesionWithPatch() throws Exception {
        // Initialize the database
        entityAdhesionRepository.saveAndFlush(entityAdhesion);

        int databaseSizeBeforeUpdate = entityAdhesionRepository.findAll().size();

        // Update the entityAdhesion using partial update
        EntityAdhesion partialUpdatedEntityAdhesion = new EntityAdhesion();
        partialUpdatedEntityAdhesion.setId(entityAdhesion.getId());

        partialUpdatedEntityAdhesion
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restEntityAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntityAdhesion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntityAdhesion))
            )
            .andExpect(status().isOk());

        // Validate the EntityAdhesion in the database
        List<EntityAdhesion> entityAdhesionList = entityAdhesionRepository.findAll();
        assertThat(entityAdhesionList).hasSize(databaseSizeBeforeUpdate);
        EntityAdhesion testEntityAdhesion = entityAdhesionList.get(entityAdhesionList.size() - 1);
        assertThat(testEntityAdhesion.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEntityAdhesion.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testEntityAdhesion.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testEntityAdhesion.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testEntityAdhesion.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEntityAdhesion.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEntityAdhesion.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testEntityAdhesion.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testEntityAdhesion.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEntityAdhesion.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEntityAdhesion.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateEntityAdhesionWithPatch() throws Exception {
        // Initialize the database
        entityAdhesionRepository.saveAndFlush(entityAdhesion);

        int databaseSizeBeforeUpdate = entityAdhesionRepository.findAll().size();

        // Update the entityAdhesion using partial update
        EntityAdhesion partialUpdatedEntityAdhesion = new EntityAdhesion();
        partialUpdatedEntityAdhesion.setId(entityAdhesion.getId());

        partialUpdatedEntityAdhesion
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restEntityAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntityAdhesion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntityAdhesion))
            )
            .andExpect(status().isOk());

        // Validate the EntityAdhesion in the database
        List<EntityAdhesion> entityAdhesionList = entityAdhesionRepository.findAll();
        assertThat(entityAdhesionList).hasSize(databaseSizeBeforeUpdate);
        EntityAdhesion testEntityAdhesion = entityAdhesionList.get(entityAdhesionList.size() - 1);
        assertThat(testEntityAdhesion.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEntityAdhesion.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testEntityAdhesion.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testEntityAdhesion.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testEntityAdhesion.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEntityAdhesion.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEntityAdhesion.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEntityAdhesion.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEntityAdhesion.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEntityAdhesion.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEntityAdhesion.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingEntityAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = entityAdhesionRepository.findAll().size();
        entityAdhesion.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntityAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entityAdhesion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entityAdhesion))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntityAdhesion in the database
        List<EntityAdhesion> entityAdhesionList = entityAdhesionRepository.findAll();
        assertThat(entityAdhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntityAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = entityAdhesionRepository.findAll().size();
        entityAdhesion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntityAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entityAdhesion))
            )
            .andExpect(status().isBadRequest());

        // Validate the EntityAdhesion in the database
        List<EntityAdhesion> entityAdhesionList = entityAdhesionRepository.findAll();
        assertThat(entityAdhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntityAdhesion() throws Exception {
        int databaseSizeBeforeUpdate = entityAdhesionRepository.findAll().size();
        entityAdhesion.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntityAdhesionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(entityAdhesion))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EntityAdhesion in the database
        List<EntityAdhesion> entityAdhesionList = entityAdhesionRepository.findAll();
        assertThat(entityAdhesionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntityAdhesion() throws Exception {
        // Initialize the database
        entityAdhesionRepository.saveAndFlush(entityAdhesion);

        int databaseSizeBeforeDelete = entityAdhesionRepository.findAll().size();

        // Delete the entityAdhesion
        restEntityAdhesionMockMvc
            .perform(delete(ENTITY_API_URL_ID, entityAdhesion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EntityAdhesion> entityAdhesionList = entityAdhesionRepository.findAll();
        assertThat(entityAdhesionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
