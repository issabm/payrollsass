package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Echlon;
import com.issa.payroll.repository.EchlonRepository;
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
 * Integration tests for the {@link EchlonResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EchlonResourceIT {

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

    private static final String ENTITY_API_URL = "/api/echlons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EchlonRepository echlonRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEchlonMockMvc;

    private Echlon echlon;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Echlon createEntity(EntityManager em) {
        Echlon echlon = new Echlon()
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
        return echlon;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Echlon createUpdatedEntity(EntityManager em) {
        Echlon echlon = new Echlon()
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
        return echlon;
    }

    @BeforeEach
    public void initTest() {
        echlon = createEntity(em);
    }

    @Test
    @Transactional
    void createEchlon() throws Exception {
        int databaseSizeBeforeCreate = echlonRepository.findAll().size();
        // Create the Echlon
        restEchlonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(echlon)))
            .andExpect(status().isCreated());

        // Validate the Echlon in the database
        List<Echlon> echlonList = echlonRepository.findAll();
        assertThat(echlonList).hasSize(databaseSizeBeforeCreate + 1);
        Echlon testEchlon = echlonList.get(echlonList.size() - 1);
        assertThat(testEchlon.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEchlon.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testEchlon.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testEchlon.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testEchlon.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testEchlon.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testEchlon.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testEchlon.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testEchlon.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEchlon.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createEchlonWithExistingId() throws Exception {
        // Create the Echlon with an existing ID
        echlon.setId(1L);

        int databaseSizeBeforeCreate = echlonRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEchlonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(echlon)))
            .andExpect(status().isBadRequest());

        // Validate the Echlon in the database
        List<Echlon> echlonList = echlonRepository.findAll();
        assertThat(echlonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEchlons() throws Exception {
        // Initialize the database
        echlonRepository.saveAndFlush(echlon);

        // Get all the echlonList
        restEchlonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(echlon.getId().intValue())))
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
    void getEchlon() throws Exception {
        // Initialize the database
        echlonRepository.saveAndFlush(echlon);

        // Get the echlon
        restEchlonMockMvc
            .perform(get(ENTITY_API_URL_ID, echlon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(echlon.getId().intValue()))
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
    void getNonExistingEchlon() throws Exception {
        // Get the echlon
        restEchlonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEchlon() throws Exception {
        // Initialize the database
        echlonRepository.saveAndFlush(echlon);

        int databaseSizeBeforeUpdate = echlonRepository.findAll().size();

        // Update the echlon
        Echlon updatedEchlon = echlonRepository.findById(echlon.getId()).get();
        // Disconnect from session so that the updates on updatedEchlon are not directly saved in db
        em.detach(updatedEchlon);
        updatedEchlon
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

        restEchlonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEchlon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEchlon))
            )
            .andExpect(status().isOk());

        // Validate the Echlon in the database
        List<Echlon> echlonList = echlonRepository.findAll();
        assertThat(echlonList).hasSize(databaseSizeBeforeUpdate);
        Echlon testEchlon = echlonList.get(echlonList.size() - 1);
        assertThat(testEchlon.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEchlon.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testEchlon.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testEchlon.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEchlon.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEchlon.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEchlon.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEchlon.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEchlon.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEchlon.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingEchlon() throws Exception {
        int databaseSizeBeforeUpdate = echlonRepository.findAll().size();
        echlon.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEchlonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, echlon.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(echlon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Echlon in the database
        List<Echlon> echlonList = echlonRepository.findAll();
        assertThat(echlonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEchlon() throws Exception {
        int databaseSizeBeforeUpdate = echlonRepository.findAll().size();
        echlon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEchlonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(echlon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Echlon in the database
        List<Echlon> echlonList = echlonRepository.findAll();
        assertThat(echlonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEchlon() throws Exception {
        int databaseSizeBeforeUpdate = echlonRepository.findAll().size();
        echlon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEchlonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(echlon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Echlon in the database
        List<Echlon> echlonList = echlonRepository.findAll();
        assertThat(echlonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEchlonWithPatch() throws Exception {
        // Initialize the database
        echlonRepository.saveAndFlush(echlon);

        int databaseSizeBeforeUpdate = echlonRepository.findAll().size();

        // Update the echlon using partial update
        Echlon partialUpdatedEchlon = new Echlon();
        partialUpdatedEchlon.setId(echlon.getId());

        partialUpdatedEchlon
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restEchlonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEchlon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEchlon))
            )
            .andExpect(status().isOk());

        // Validate the Echlon in the database
        List<Echlon> echlonList = echlonRepository.findAll();
        assertThat(echlonList).hasSize(databaseSizeBeforeUpdate);
        Echlon testEchlon = echlonList.get(echlonList.size() - 1);
        assertThat(testEchlon.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEchlon.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testEchlon.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testEchlon.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEchlon.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEchlon.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testEchlon.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEchlon.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEchlon.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEchlon.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateEchlonWithPatch() throws Exception {
        // Initialize the database
        echlonRepository.saveAndFlush(echlon);

        int databaseSizeBeforeUpdate = echlonRepository.findAll().size();

        // Update the echlon using partial update
        Echlon partialUpdatedEchlon = new Echlon();
        partialUpdatedEchlon.setId(echlon.getId());

        partialUpdatedEchlon
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

        restEchlonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEchlon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEchlon))
            )
            .andExpect(status().isOk());

        // Validate the Echlon in the database
        List<Echlon> echlonList = echlonRepository.findAll();
        assertThat(echlonList).hasSize(databaseSizeBeforeUpdate);
        Echlon testEchlon = echlonList.get(echlonList.size() - 1);
        assertThat(testEchlon.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEchlon.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testEchlon.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testEchlon.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEchlon.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEchlon.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEchlon.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEchlon.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEchlon.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEchlon.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingEchlon() throws Exception {
        int databaseSizeBeforeUpdate = echlonRepository.findAll().size();
        echlon.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEchlonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, echlon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(echlon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Echlon in the database
        List<Echlon> echlonList = echlonRepository.findAll();
        assertThat(echlonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEchlon() throws Exception {
        int databaseSizeBeforeUpdate = echlonRepository.findAll().size();
        echlon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEchlonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(echlon))
            )
            .andExpect(status().isBadRequest());

        // Validate the Echlon in the database
        List<Echlon> echlonList = echlonRepository.findAll();
        assertThat(echlonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEchlon() throws Exception {
        int databaseSizeBeforeUpdate = echlonRepository.findAll().size();
        echlon.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEchlonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(echlon)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Echlon in the database
        List<Echlon> echlonList = echlonRepository.findAll();
        assertThat(echlonList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEchlon() throws Exception {
        // Initialize the database
        echlonRepository.saveAndFlush(echlon);

        int databaseSizeBeforeDelete = echlonRepository.findAll().size();

        // Delete the echlon
        restEchlonMockMvc
            .perform(delete(ENTITY_API_URL_ID, echlon.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Echlon> echlonList = echlonRepository.findAll();
        assertThat(echlonList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
