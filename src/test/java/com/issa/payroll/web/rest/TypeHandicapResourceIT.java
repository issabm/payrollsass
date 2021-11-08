package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.TypeHandicap;
import com.issa.payroll.repository.TypeHandicapRepository;
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
 * Integration tests for the {@link TypeHandicapResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TypeHandicapResourceIT {

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

    private static final String ENTITY_API_URL = "/api/type-handicaps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeHandicapRepository typeHandicapRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeHandicapMockMvc;

    private TypeHandicap typeHandicap;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeHandicap createEntity(EntityManager em) {
        TypeHandicap typeHandicap = new TypeHandicap()
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
        return typeHandicap;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeHandicap createUpdatedEntity(EntityManager em) {
        TypeHandicap typeHandicap = new TypeHandicap()
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
        return typeHandicap;
    }

    @BeforeEach
    public void initTest() {
        typeHandicap = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeHandicap() throws Exception {
        int databaseSizeBeforeCreate = typeHandicapRepository.findAll().size();
        // Create the TypeHandicap
        restTypeHandicapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeHandicap)))
            .andExpect(status().isCreated());

        // Validate the TypeHandicap in the database
        List<TypeHandicap> typeHandicapList = typeHandicapRepository.findAll();
        assertThat(typeHandicapList).hasSize(databaseSizeBeforeCreate + 1);
        TypeHandicap testTypeHandicap = typeHandicapList.get(typeHandicapList.size() - 1);
        assertThat(testTypeHandicap.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeHandicap.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testTypeHandicap.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testTypeHandicap.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testTypeHandicap.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testTypeHandicap.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTypeHandicap.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testTypeHandicap.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTypeHandicap.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTypeHandicap.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createTypeHandicapWithExistingId() throws Exception {
        // Create the TypeHandicap with an existing ID
        typeHandicap.setId(1L);

        int databaseSizeBeforeCreate = typeHandicapRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeHandicapMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeHandicap)))
            .andExpect(status().isBadRequest());

        // Validate the TypeHandicap in the database
        List<TypeHandicap> typeHandicapList = typeHandicapRepository.findAll();
        assertThat(typeHandicapList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypeHandicaps() throws Exception {
        // Initialize the database
        typeHandicapRepository.saveAndFlush(typeHandicap);

        // Get all the typeHandicapList
        restTypeHandicapMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeHandicap.getId().intValue())))
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
    void getTypeHandicap() throws Exception {
        // Initialize the database
        typeHandicapRepository.saveAndFlush(typeHandicap);

        // Get the typeHandicap
        restTypeHandicapMockMvc
            .perform(get(ENTITY_API_URL_ID, typeHandicap.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeHandicap.getId().intValue()))
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
    void getNonExistingTypeHandicap() throws Exception {
        // Get the typeHandicap
        restTypeHandicapMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTypeHandicap() throws Exception {
        // Initialize the database
        typeHandicapRepository.saveAndFlush(typeHandicap);

        int databaseSizeBeforeUpdate = typeHandicapRepository.findAll().size();

        // Update the typeHandicap
        TypeHandicap updatedTypeHandicap = typeHandicapRepository.findById(typeHandicap.getId()).get();
        // Disconnect from session so that the updates on updatedTypeHandicap are not directly saved in db
        em.detach(updatedTypeHandicap);
        updatedTypeHandicap
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

        restTypeHandicapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTypeHandicap.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTypeHandicap))
            )
            .andExpect(status().isOk());

        // Validate the TypeHandicap in the database
        List<TypeHandicap> typeHandicapList = typeHandicapRepository.findAll();
        assertThat(typeHandicapList).hasSize(databaseSizeBeforeUpdate);
        TypeHandicap testTypeHandicap = typeHandicapList.get(typeHandicapList.size() - 1);
        assertThat(testTypeHandicap.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeHandicap.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testTypeHandicap.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testTypeHandicap.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testTypeHandicap.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testTypeHandicap.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTypeHandicap.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testTypeHandicap.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTypeHandicap.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTypeHandicap.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTypeHandicap() throws Exception {
        int databaseSizeBeforeUpdate = typeHandicapRepository.findAll().size();
        typeHandicap.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeHandicapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeHandicap.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeHandicap))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeHandicap in the database
        List<TypeHandicap> typeHandicapList = typeHandicapRepository.findAll();
        assertThat(typeHandicapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeHandicap() throws Exception {
        int databaseSizeBeforeUpdate = typeHandicapRepository.findAll().size();
        typeHandicap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeHandicapMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeHandicap))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeHandicap in the database
        List<TypeHandicap> typeHandicapList = typeHandicapRepository.findAll();
        assertThat(typeHandicapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeHandicap() throws Exception {
        int databaseSizeBeforeUpdate = typeHandicapRepository.findAll().size();
        typeHandicap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeHandicapMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeHandicap)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeHandicap in the database
        List<TypeHandicap> typeHandicapList = typeHandicapRepository.findAll();
        assertThat(typeHandicapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeHandicapWithPatch() throws Exception {
        // Initialize the database
        typeHandicapRepository.saveAndFlush(typeHandicap);

        int databaseSizeBeforeUpdate = typeHandicapRepository.findAll().size();

        // Update the typeHandicap using partial update
        TypeHandicap partialUpdatedTypeHandicap = new TypeHandicap();
        partialUpdatedTypeHandicap.setId(typeHandicap.getId());

        partialUpdatedTypeHandicap.op(UPDATED_OP).modifiedDate(UPDATED_MODIFIED_DATE);

        restTypeHandicapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeHandicap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeHandicap))
            )
            .andExpect(status().isOk());

        // Validate the TypeHandicap in the database
        List<TypeHandicap> typeHandicapList = typeHandicapRepository.findAll();
        assertThat(typeHandicapList).hasSize(databaseSizeBeforeUpdate);
        TypeHandicap testTypeHandicap = typeHandicapList.get(typeHandicapList.size() - 1);
        assertThat(testTypeHandicap.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeHandicap.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testTypeHandicap.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testTypeHandicap.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testTypeHandicap.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testTypeHandicap.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTypeHandicap.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testTypeHandicap.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTypeHandicap.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTypeHandicap.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTypeHandicapWithPatch() throws Exception {
        // Initialize the database
        typeHandicapRepository.saveAndFlush(typeHandicap);

        int databaseSizeBeforeUpdate = typeHandicapRepository.findAll().size();

        // Update the typeHandicap using partial update
        TypeHandicap partialUpdatedTypeHandicap = new TypeHandicap();
        partialUpdatedTypeHandicap.setId(typeHandicap.getId());

        partialUpdatedTypeHandicap
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

        restTypeHandicapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeHandicap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeHandicap))
            )
            .andExpect(status().isOk());

        // Validate the TypeHandicap in the database
        List<TypeHandicap> typeHandicapList = typeHandicapRepository.findAll();
        assertThat(typeHandicapList).hasSize(databaseSizeBeforeUpdate);
        TypeHandicap testTypeHandicap = typeHandicapList.get(typeHandicapList.size() - 1);
        assertThat(testTypeHandicap.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeHandicap.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testTypeHandicap.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testTypeHandicap.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testTypeHandicap.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testTypeHandicap.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTypeHandicap.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testTypeHandicap.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTypeHandicap.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTypeHandicap.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTypeHandicap() throws Exception {
        int databaseSizeBeforeUpdate = typeHandicapRepository.findAll().size();
        typeHandicap.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeHandicapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeHandicap.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeHandicap))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeHandicap in the database
        List<TypeHandicap> typeHandicapList = typeHandicapRepository.findAll();
        assertThat(typeHandicapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeHandicap() throws Exception {
        int databaseSizeBeforeUpdate = typeHandicapRepository.findAll().size();
        typeHandicap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeHandicapMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeHandicap))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeHandicap in the database
        List<TypeHandicap> typeHandicapList = typeHandicapRepository.findAll();
        assertThat(typeHandicapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeHandicap() throws Exception {
        int databaseSizeBeforeUpdate = typeHandicapRepository.findAll().size();
        typeHandicap.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeHandicapMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(typeHandicap))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeHandicap in the database
        List<TypeHandicap> typeHandicapList = typeHandicapRepository.findAll();
        assertThat(typeHandicapList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeHandicap() throws Exception {
        // Initialize the database
        typeHandicapRepository.saveAndFlush(typeHandicap);

        int databaseSizeBeforeDelete = typeHandicapRepository.findAll().size();

        // Delete the typeHandicap
        restTypeHandicapMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeHandicap.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeHandicap> typeHandicapList = typeHandicapRepository.findAll();
        assertThat(typeHandicapList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
