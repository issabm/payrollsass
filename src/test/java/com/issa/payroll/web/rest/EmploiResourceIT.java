package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Emploi;
import com.issa.payroll.repository.EmploiRepository;
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
 * Integration tests for the {@link EmploiResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EmploiResourceIT {

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

    private static final String ENTITY_API_URL = "/api/emplois";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmploiRepository emploiRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmploiMockMvc;

    private Emploi emploi;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emploi createEntity(EntityManager em) {
        Emploi emploi = new Emploi()
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
        return emploi;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Emploi createUpdatedEntity(EntityManager em) {
        Emploi emploi = new Emploi()
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
        return emploi;
    }

    @BeforeEach
    public void initTest() {
        emploi = createEntity(em);
    }

    @Test
    @Transactional
    void createEmploi() throws Exception {
        int databaseSizeBeforeCreate = emploiRepository.findAll().size();
        // Create the Emploi
        restEmploiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emploi)))
            .andExpect(status().isCreated());

        // Validate the Emploi in the database
        List<Emploi> emploiList = emploiRepository.findAll();
        assertThat(emploiList).hasSize(databaseSizeBeforeCreate + 1);
        Emploi testEmploi = emploiList.get(emploiList.size() - 1);
        assertThat(testEmploi.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEmploi.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testEmploi.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testEmploi.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testEmploi.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testEmploi.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testEmploi.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testEmploi.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testEmploi.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEmploi.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createEmploiWithExistingId() throws Exception {
        // Create the Emploi with an existing ID
        emploi.setId(1L);

        int databaseSizeBeforeCreate = emploiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmploiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emploi)))
            .andExpect(status().isBadRequest());

        // Validate the Emploi in the database
        List<Emploi> emploiList = emploiRepository.findAll();
        assertThat(emploiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmplois() throws Exception {
        // Initialize the database
        emploiRepository.saveAndFlush(emploi);

        // Get all the emploiList
        restEmploiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emploi.getId().intValue())))
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
    void getEmploi() throws Exception {
        // Initialize the database
        emploiRepository.saveAndFlush(emploi);

        // Get the emploi
        restEmploiMockMvc
            .perform(get(ENTITY_API_URL_ID, emploi.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emploi.getId().intValue()))
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
    void getNonExistingEmploi() throws Exception {
        // Get the emploi
        restEmploiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmploi() throws Exception {
        // Initialize the database
        emploiRepository.saveAndFlush(emploi);

        int databaseSizeBeforeUpdate = emploiRepository.findAll().size();

        // Update the emploi
        Emploi updatedEmploi = emploiRepository.findById(emploi.getId()).get();
        // Disconnect from session so that the updates on updatedEmploi are not directly saved in db
        em.detach(updatedEmploi);
        updatedEmploi
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

        restEmploiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmploi.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmploi))
            )
            .andExpect(status().isOk());

        // Validate the Emploi in the database
        List<Emploi> emploiList = emploiRepository.findAll();
        assertThat(emploiList).hasSize(databaseSizeBeforeUpdate);
        Emploi testEmploi = emploiList.get(emploiList.size() - 1);
        assertThat(testEmploi.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEmploi.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testEmploi.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testEmploi.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEmploi.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEmploi.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEmploi.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEmploi.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEmploi.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmploi.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingEmploi() throws Exception {
        int databaseSizeBeforeUpdate = emploiRepository.findAll().size();
        emploi.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, emploi.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emploi))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emploi in the database
        List<Emploi> emploiList = emploiRepository.findAll();
        assertThat(emploiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmploi() throws Exception {
        int databaseSizeBeforeUpdate = emploiRepository.findAll().size();
        emploi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(emploi))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emploi in the database
        List<Emploi> emploiList = emploiRepository.findAll();
        assertThat(emploiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmploi() throws Exception {
        int databaseSizeBeforeUpdate = emploiRepository.findAll().size();
        emploi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(emploi)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emploi in the database
        List<Emploi> emploiList = emploiRepository.findAll();
        assertThat(emploiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmploiWithPatch() throws Exception {
        // Initialize the database
        emploiRepository.saveAndFlush(emploi);

        int databaseSizeBeforeUpdate = emploiRepository.findAll().size();

        // Update the emploi using partial update
        Emploi partialUpdatedEmploi = new Emploi();
        partialUpdatedEmploi.setId(emploi.getId());

        partialUpdatedEmploi
            .libEn(UPDATED_LIB_EN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restEmploiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploi))
            )
            .andExpect(status().isOk());

        // Validate the Emploi in the database
        List<Emploi> emploiList = emploiRepository.findAll();
        assertThat(emploiList).hasSize(databaseSizeBeforeUpdate);
        Emploi testEmploi = emploiList.get(emploiList.size() - 1);
        assertThat(testEmploi.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEmploi.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testEmploi.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testEmploi.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEmploi.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEmploi.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEmploi.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testEmploi.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testEmploi.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmploi.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateEmploiWithPatch() throws Exception {
        // Initialize the database
        emploiRepository.saveAndFlush(emploi);

        int databaseSizeBeforeUpdate = emploiRepository.findAll().size();

        // Update the emploi using partial update
        Emploi partialUpdatedEmploi = new Emploi();
        partialUpdatedEmploi.setId(emploi.getId());

        partialUpdatedEmploi
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

        restEmploiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploi))
            )
            .andExpect(status().isOk());

        // Validate the Emploi in the database
        List<Emploi> emploiList = emploiRepository.findAll();
        assertThat(emploiList).hasSize(databaseSizeBeforeUpdate);
        Emploi testEmploi = emploiList.get(emploiList.size() - 1);
        assertThat(testEmploi.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEmploi.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testEmploi.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testEmploi.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEmploi.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEmploi.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEmploi.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEmploi.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEmploi.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEmploi.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingEmploi() throws Exception {
        int databaseSizeBeforeUpdate = emploiRepository.findAll().size();
        emploi.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmploiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, emploi.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emploi))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emploi in the database
        List<Emploi> emploiList = emploiRepository.findAll();
        assertThat(emploiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmploi() throws Exception {
        int databaseSizeBeforeUpdate = emploiRepository.findAll().size();
        emploi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(emploi))
            )
            .andExpect(status().isBadRequest());

        // Validate the Emploi in the database
        List<Emploi> emploiList = emploiRepository.findAll();
        assertThat(emploiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmploi() throws Exception {
        int databaseSizeBeforeUpdate = emploiRepository.findAll().size();
        emploi.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmploiMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(emploi)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Emploi in the database
        List<Emploi> emploiList = emploiRepository.findAll();
        assertThat(emploiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmploi() throws Exception {
        // Initialize the database
        emploiRepository.saveAndFlush(emploi);

        int databaseSizeBeforeDelete = emploiRepository.findAll().size();

        // Delete the emploi
        restEmploiMockMvc
            .perform(delete(ENTITY_API_URL_ID, emploi.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Emploi> emploiList = emploiRepository.findAll();
        assertThat(emploiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
