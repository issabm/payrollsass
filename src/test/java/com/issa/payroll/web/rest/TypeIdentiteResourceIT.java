package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.TypeIdentite;
import com.issa.payroll.repository.TypeIdentiteRepository;
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
 * Integration tests for the {@link TypeIdentiteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TypeIdentiteResourceIT {

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

    private static final String ENTITY_API_URL = "/api/type-identites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TypeIdentiteRepository typeIdentiteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeIdentiteMockMvc;

    private TypeIdentite typeIdentite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeIdentite createEntity(EntityManager em) {
        TypeIdentite typeIdentite = new TypeIdentite()
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
        return typeIdentite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeIdentite createUpdatedEntity(EntityManager em) {
        TypeIdentite typeIdentite = new TypeIdentite()
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
        return typeIdentite;
    }

    @BeforeEach
    public void initTest() {
        typeIdentite = createEntity(em);
    }

    @Test
    @Transactional
    void createTypeIdentite() throws Exception {
        int databaseSizeBeforeCreate = typeIdentiteRepository.findAll().size();
        // Create the TypeIdentite
        restTypeIdentiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeIdentite)))
            .andExpect(status().isCreated());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeCreate + 1);
        TypeIdentite testTypeIdentite = typeIdentiteList.get(typeIdentiteList.size() - 1);
        assertThat(testTypeIdentite.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeIdentite.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testTypeIdentite.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testTypeIdentite.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testTypeIdentite.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testTypeIdentite.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testTypeIdentite.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testTypeIdentite.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTypeIdentite.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testTypeIdentite.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createTypeIdentiteWithExistingId() throws Exception {
        // Create the TypeIdentite with an existing ID
        typeIdentite.setId(1L);

        int databaseSizeBeforeCreate = typeIdentiteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeIdentiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeIdentite)))
            .andExpect(status().isBadRequest());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTypeIdentites() throws Exception {
        // Initialize the database
        typeIdentiteRepository.saveAndFlush(typeIdentite);

        // Get all the typeIdentiteList
        restTypeIdentiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeIdentite.getId().intValue())))
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
    void getTypeIdentite() throws Exception {
        // Initialize the database
        typeIdentiteRepository.saveAndFlush(typeIdentite);

        // Get the typeIdentite
        restTypeIdentiteMockMvc
            .perform(get(ENTITY_API_URL_ID, typeIdentite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeIdentite.getId().intValue()))
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
    void getNonExistingTypeIdentite() throws Exception {
        // Get the typeIdentite
        restTypeIdentiteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTypeIdentite() throws Exception {
        // Initialize the database
        typeIdentiteRepository.saveAndFlush(typeIdentite);

        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();

        // Update the typeIdentite
        TypeIdentite updatedTypeIdentite = typeIdentiteRepository.findById(typeIdentite.getId()).get();
        // Disconnect from session so that the updates on updatedTypeIdentite are not directly saved in db
        em.detach(updatedTypeIdentite);
        updatedTypeIdentite
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

        restTypeIdentiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTypeIdentite.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTypeIdentite))
            )
            .andExpect(status().isOk());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
        TypeIdentite testTypeIdentite = typeIdentiteList.get(typeIdentiteList.size() - 1);
        assertThat(testTypeIdentite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeIdentite.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testTypeIdentite.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testTypeIdentite.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testTypeIdentite.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testTypeIdentite.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTypeIdentite.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testTypeIdentite.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTypeIdentite.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTypeIdentite.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTypeIdentite() throws Exception {
        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();
        typeIdentite.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeIdentiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, typeIdentite.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeIdentite))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTypeIdentite() throws Exception {
        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();
        typeIdentite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeIdentiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(typeIdentite))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTypeIdentite() throws Exception {
        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();
        typeIdentite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeIdentiteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeIdentite)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTypeIdentiteWithPatch() throws Exception {
        // Initialize the database
        typeIdentiteRepository.saveAndFlush(typeIdentite);

        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();

        // Update the typeIdentite using partial update
        TypeIdentite partialUpdatedTypeIdentite = new TypeIdentite();
        partialUpdatedTypeIdentite.setId(typeIdentite.getId());

        partialUpdatedTypeIdentite.dateop(UPDATED_DATEOP).modifiedBy(UPDATED_MODIFIED_BY).op(UPDATED_OP).createdDate(UPDATED_CREATED_DATE);

        restTypeIdentiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeIdentite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeIdentite))
            )
            .andExpect(status().isOk());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
        TypeIdentite testTypeIdentite = typeIdentiteList.get(typeIdentiteList.size() - 1);
        assertThat(testTypeIdentite.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTypeIdentite.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testTypeIdentite.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testTypeIdentite.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testTypeIdentite.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testTypeIdentite.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTypeIdentite.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testTypeIdentite.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTypeIdentite.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTypeIdentite.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTypeIdentiteWithPatch() throws Exception {
        // Initialize the database
        typeIdentiteRepository.saveAndFlush(typeIdentite);

        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();

        // Update the typeIdentite using partial update
        TypeIdentite partialUpdatedTypeIdentite = new TypeIdentite();
        partialUpdatedTypeIdentite.setId(typeIdentite.getId());

        partialUpdatedTypeIdentite
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

        restTypeIdentiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTypeIdentite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeIdentite))
            )
            .andExpect(status().isOk());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
        TypeIdentite testTypeIdentite = typeIdentiteList.get(typeIdentiteList.size() - 1);
        assertThat(testTypeIdentite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTypeIdentite.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testTypeIdentite.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testTypeIdentite.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testTypeIdentite.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testTypeIdentite.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testTypeIdentite.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testTypeIdentite.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTypeIdentite.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testTypeIdentite.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTypeIdentite() throws Exception {
        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();
        typeIdentite.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeIdentiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, typeIdentite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeIdentite))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTypeIdentite() throws Exception {
        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();
        typeIdentite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeIdentiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(typeIdentite))
            )
            .andExpect(status().isBadRequest());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTypeIdentite() throws Exception {
        int databaseSizeBeforeUpdate = typeIdentiteRepository.findAll().size();
        typeIdentite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTypeIdentiteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(typeIdentite))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TypeIdentite in the database
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTypeIdentite() throws Exception {
        // Initialize the database
        typeIdentiteRepository.saveAndFlush(typeIdentite);

        int databaseSizeBeforeDelete = typeIdentiteRepository.findAll().size();

        // Delete the typeIdentite
        restTypeIdentiteMockMvc
            .perform(delete(ENTITY_API_URL_ID, typeIdentite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeIdentite> typeIdentiteList = typeIdentiteRepository.findAll();
        assertThat(typeIdentiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
