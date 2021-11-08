package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Sexe;
import com.issa.payroll.repository.SexeRepository;
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
 * Integration tests for the {@link SexeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SexeResourceIT {

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

    private static final String ENTITY_API_URL = "/api/sexes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SexeRepository sexeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSexeMockMvc;

    private Sexe sexe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sexe createEntity(EntityManager em) {
        Sexe sexe = new Sexe()
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
        return sexe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sexe createUpdatedEntity(EntityManager em) {
        Sexe sexe = new Sexe()
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
        return sexe;
    }

    @BeforeEach
    public void initTest() {
        sexe = createEntity(em);
    }

    @Test
    @Transactional
    void createSexe() throws Exception {
        int databaseSizeBeforeCreate = sexeRepository.findAll().size();
        // Create the Sexe
        restSexeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sexe)))
            .andExpect(status().isCreated());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeCreate + 1);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testSexe.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testSexe.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testSexe.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testSexe.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testSexe.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSexe.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testSexe.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSexe.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSexe.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSexeWithExistingId() throws Exception {
        // Create the Sexe with an existing ID
        sexe.setId(1L);

        int databaseSizeBeforeCreate = sexeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSexeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sexe)))
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSexes() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get all the sexeList
        restSexeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sexe.getId().intValue())))
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
    void getSexe() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        // Get the sexe
        restSexeMockMvc
            .perform(get(ENTITY_API_URL_ID, sexe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sexe.getId().intValue()))
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
    void getNonExistingSexe() throws Exception {
        // Get the sexe
        restSexeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSexe() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();

        // Update the sexe
        Sexe updatedSexe = sexeRepository.findById(sexe.getId()).get();
        // Disconnect from session so that the updates on updatedSexe are not directly saved in db
        em.detach(updatedSexe);
        updatedSexe
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

        restSexeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSexe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSexe))
            )
            .andExpect(status().isOk());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSexe.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testSexe.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testSexe.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSexe.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSexe.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSexe.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSexe.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSexe.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSexe.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sexe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sexe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sexe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sexe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSexeWithPatch() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();

        // Update the sexe using partial update
        Sexe partialUpdatedSexe = new Sexe();
        partialUpdatedSexe.setId(sexe.getId());

        partialUpdatedSexe
            .code(UPDATED_CODE)
            .dateop(UPDATED_DATEOP)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restSexeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSexe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSexe))
            )
            .andExpect(status().isOk());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSexe.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testSexe.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testSexe.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testSexe.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSexe.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSexe.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSexe.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSexe.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSexe.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSexeWithPatch() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();

        // Update the sexe using partial update
        Sexe partialUpdatedSexe = new Sexe();
        partialUpdatedSexe.setId(sexe.getId());

        partialUpdatedSexe
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

        restSexeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSexe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSexe))
            )
            .andExpect(status().isOk());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
        Sexe testSexe = sexeList.get(sexeList.size() - 1);
        assertThat(testSexe.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testSexe.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testSexe.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testSexe.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSexe.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSexe.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSexe.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSexe.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSexe.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSexe.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sexe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sexe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sexe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSexe() throws Exception {
        int databaseSizeBeforeUpdate = sexeRepository.findAll().size();
        sexe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSexeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sexe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sexe in the database
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSexe() throws Exception {
        // Initialize the database
        sexeRepository.saveAndFlush(sexe);

        int databaseSizeBeforeDelete = sexeRepository.findAll().size();

        // Delete the sexe
        restSexeMockMvc
            .perform(delete(ENTITY_API_URL_ID, sexe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sexe> sexeList = sexeRepository.findAll();
        assertThat(sexeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
