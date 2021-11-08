package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.SousTypeContrat;
import com.issa.payroll.repository.SousTypeContratRepository;
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
 * Integration tests for the {@link SousTypeContratResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SousTypeContratResourceIT {

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

    private static final String ENTITY_API_URL = "/api/sous-type-contrats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SousTypeContratRepository sousTypeContratRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSousTypeContratMockMvc;

    private SousTypeContrat sousTypeContrat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SousTypeContrat createEntity(EntityManager em) {
        SousTypeContrat sousTypeContrat = new SousTypeContrat()
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
        return sousTypeContrat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SousTypeContrat createUpdatedEntity(EntityManager em) {
        SousTypeContrat sousTypeContrat = new SousTypeContrat()
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
        return sousTypeContrat;
    }

    @BeforeEach
    public void initTest() {
        sousTypeContrat = createEntity(em);
    }

    @Test
    @Transactional
    void createSousTypeContrat() throws Exception {
        int databaseSizeBeforeCreate = sousTypeContratRepository.findAll().size();
        // Create the SousTypeContrat
        restSousTypeContratMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sousTypeContrat))
            )
            .andExpect(status().isCreated());

        // Validate the SousTypeContrat in the database
        List<SousTypeContrat> sousTypeContratList = sousTypeContratRepository.findAll();
        assertThat(sousTypeContratList).hasSize(databaseSizeBeforeCreate + 1);
        SousTypeContrat testSousTypeContrat = sousTypeContratList.get(sousTypeContratList.size() - 1);
        assertThat(testSousTypeContrat.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSousTypeContrat.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testSousTypeContrat.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testSousTypeContrat.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testSousTypeContrat.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testSousTypeContrat.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSousTypeContrat.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testSousTypeContrat.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSousTypeContrat.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSousTypeContrat.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSousTypeContratWithExistingId() throws Exception {
        // Create the SousTypeContrat with an existing ID
        sousTypeContrat.setId(1L);

        int databaseSizeBeforeCreate = sousTypeContratRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSousTypeContratMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sousTypeContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousTypeContrat in the database
        List<SousTypeContrat> sousTypeContratList = sousTypeContratRepository.findAll();
        assertThat(sousTypeContratList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSousTypeContrats() throws Exception {
        // Initialize the database
        sousTypeContratRepository.saveAndFlush(sousTypeContrat);

        // Get all the sousTypeContratList
        restSousTypeContratMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sousTypeContrat.getId().intValue())))
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
    void getSousTypeContrat() throws Exception {
        // Initialize the database
        sousTypeContratRepository.saveAndFlush(sousTypeContrat);

        // Get the sousTypeContrat
        restSousTypeContratMockMvc
            .perform(get(ENTITY_API_URL_ID, sousTypeContrat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sousTypeContrat.getId().intValue()))
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
    void getNonExistingSousTypeContrat() throws Exception {
        // Get the sousTypeContrat
        restSousTypeContratMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSousTypeContrat() throws Exception {
        // Initialize the database
        sousTypeContratRepository.saveAndFlush(sousTypeContrat);

        int databaseSizeBeforeUpdate = sousTypeContratRepository.findAll().size();

        // Update the sousTypeContrat
        SousTypeContrat updatedSousTypeContrat = sousTypeContratRepository.findById(sousTypeContrat.getId()).get();
        // Disconnect from session so that the updates on updatedSousTypeContrat are not directly saved in db
        em.detach(updatedSousTypeContrat);
        updatedSousTypeContrat
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

        restSousTypeContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSousTypeContrat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSousTypeContrat))
            )
            .andExpect(status().isOk());

        // Validate the SousTypeContrat in the database
        List<SousTypeContrat> sousTypeContratList = sousTypeContratRepository.findAll();
        assertThat(sousTypeContratList).hasSize(databaseSizeBeforeUpdate);
        SousTypeContrat testSousTypeContrat = sousTypeContratList.get(sousTypeContratList.size() - 1);
        assertThat(testSousTypeContrat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSousTypeContrat.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testSousTypeContrat.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testSousTypeContrat.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSousTypeContrat.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSousTypeContrat.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSousTypeContrat.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSousTypeContrat.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSousTypeContrat.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSousTypeContrat.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSousTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = sousTypeContratRepository.findAll().size();
        sousTypeContrat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSousTypeContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sousTypeContrat.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sousTypeContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousTypeContrat in the database
        List<SousTypeContrat> sousTypeContratList = sousTypeContratRepository.findAll();
        assertThat(sousTypeContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSousTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = sousTypeContratRepository.findAll().size();
        sousTypeContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousTypeContratMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sousTypeContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousTypeContrat in the database
        List<SousTypeContrat> sousTypeContratList = sousTypeContratRepository.findAll();
        assertThat(sousTypeContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSousTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = sousTypeContratRepository.findAll().size();
        sousTypeContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousTypeContratMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sousTypeContrat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SousTypeContrat in the database
        List<SousTypeContrat> sousTypeContratList = sousTypeContratRepository.findAll();
        assertThat(sousTypeContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSousTypeContratWithPatch() throws Exception {
        // Initialize the database
        sousTypeContratRepository.saveAndFlush(sousTypeContrat);

        int databaseSizeBeforeUpdate = sousTypeContratRepository.findAll().size();

        // Update the sousTypeContrat using partial update
        SousTypeContrat partialUpdatedSousTypeContrat = new SousTypeContrat();
        partialUpdatedSousTypeContrat.setId(sousTypeContrat.getId());

        partialUpdatedSousTypeContrat
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restSousTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSousTypeContrat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSousTypeContrat))
            )
            .andExpect(status().isOk());

        // Validate the SousTypeContrat in the database
        List<SousTypeContrat> sousTypeContratList = sousTypeContratRepository.findAll();
        assertThat(sousTypeContratList).hasSize(databaseSizeBeforeUpdate);
        SousTypeContrat testSousTypeContrat = sousTypeContratList.get(sousTypeContratList.size() - 1);
        assertThat(testSousTypeContrat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSousTypeContrat.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testSousTypeContrat.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testSousTypeContrat.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSousTypeContrat.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSousTypeContrat.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSousTypeContrat.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSousTypeContrat.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSousTypeContrat.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSousTypeContrat.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSousTypeContratWithPatch() throws Exception {
        // Initialize the database
        sousTypeContratRepository.saveAndFlush(sousTypeContrat);

        int databaseSizeBeforeUpdate = sousTypeContratRepository.findAll().size();

        // Update the sousTypeContrat using partial update
        SousTypeContrat partialUpdatedSousTypeContrat = new SousTypeContrat();
        partialUpdatedSousTypeContrat.setId(sousTypeContrat.getId());

        partialUpdatedSousTypeContrat
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

        restSousTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSousTypeContrat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSousTypeContrat))
            )
            .andExpect(status().isOk());

        // Validate the SousTypeContrat in the database
        List<SousTypeContrat> sousTypeContratList = sousTypeContratRepository.findAll();
        assertThat(sousTypeContratList).hasSize(databaseSizeBeforeUpdate);
        SousTypeContrat testSousTypeContrat = sousTypeContratList.get(sousTypeContratList.size() - 1);
        assertThat(testSousTypeContrat.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSousTypeContrat.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testSousTypeContrat.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testSousTypeContrat.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSousTypeContrat.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSousTypeContrat.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSousTypeContrat.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSousTypeContrat.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSousTypeContrat.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSousTypeContrat.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSousTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = sousTypeContratRepository.findAll().size();
        sousTypeContrat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSousTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sousTypeContrat.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sousTypeContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousTypeContrat in the database
        List<SousTypeContrat> sousTypeContratList = sousTypeContratRepository.findAll();
        assertThat(sousTypeContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSousTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = sousTypeContratRepository.findAll().size();
        sousTypeContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sousTypeContrat))
            )
            .andExpect(status().isBadRequest());

        // Validate the SousTypeContrat in the database
        List<SousTypeContrat> sousTypeContratList = sousTypeContratRepository.findAll();
        assertThat(sousTypeContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSousTypeContrat() throws Exception {
        int databaseSizeBeforeUpdate = sousTypeContratRepository.findAll().size();
        sousTypeContrat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSousTypeContratMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sousTypeContrat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SousTypeContrat in the database
        List<SousTypeContrat> sousTypeContratList = sousTypeContratRepository.findAll();
        assertThat(sousTypeContratList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSousTypeContrat() throws Exception {
        // Initialize the database
        sousTypeContratRepository.saveAndFlush(sousTypeContrat);

        int databaseSizeBeforeDelete = sousTypeContratRepository.findAll().size();

        // Delete the sousTypeContrat
        restSousTypeContratMockMvc
            .perform(delete(ENTITY_API_URL_ID, sousTypeContrat.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SousTypeContrat> sousTypeContratList = sousTypeContratRepository.findAll();
        assertThat(sousTypeContratList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
