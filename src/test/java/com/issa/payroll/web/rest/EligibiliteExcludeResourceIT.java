package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.EligibiliteExclude;
import com.issa.payroll.repository.EligibiliteExcludeRepository;
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
 * Integration tests for the {@link EligibiliteExcludeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EligibiliteExcludeResourceIT {

    private static final Integer DEFAULT_ANNEE_BEGIN = 1;
    private static final Integer UPDATED_ANNEE_BEGIN = 2;

    private static final Integer DEFAULT_MOIS_BEGIN = 1;
    private static final Integer UPDATED_MOIS_BEGIN = 2;

    private static final Integer DEFAULT_ANNEE_END = 1;
    private static final Integer UPDATED_ANNEE_END = 2;

    private static final Integer DEFAULT_MOIS_END = 1;
    private static final Integer UPDATED_MOIS_END = 2;

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_FR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_FR = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final Double DEFAULT_VAL_PAYROLL = 1D;
    private static final Double UPDATED_VAL_PAYROLL = 2D;

    private static final Integer DEFAULT_NB_DAYS_LEAVE = 1;
    private static final Integer UPDATED_NB_DAYS_LEAVE = 2;

    private static final Double DEFAULT_POUR_VAL_PAYROLL = 1D;
    private static final Double UPDATED_POUR_VAL_PAYROLL = 2D;

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

    private static final String ENTITY_API_URL = "/api/eligibilite-excludes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EligibiliteExcludeRepository eligibiliteExcludeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEligibiliteExcludeMockMvc;

    private EligibiliteExclude eligibiliteExclude;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EligibiliteExclude createEntity(EntityManager em) {
        EligibiliteExclude eligibiliteExclude = new EligibiliteExclude()
            .anneeBegin(DEFAULT_ANNEE_BEGIN)
            .moisBegin(DEFAULT_MOIS_BEGIN)
            .anneeEnd(DEFAULT_ANNEE_END)
            .moisEnd(DEFAULT_MOIS_END)
            .matricule(DEFAULT_MATRICULE)
            .code(DEFAULT_CODE)
            .libEn(DEFAULT_LIB_EN)
            .libAr(DEFAULT_LIB_AR)
            .libFr(DEFAULT_LIB_FR)
            .annee(DEFAULT_ANNEE)
            .valPayroll(DEFAULT_VAL_PAYROLL)
            .nbDaysLeave(DEFAULT_NB_DAYS_LEAVE)
            .pourValPayroll(DEFAULT_POUR_VAL_PAYROLL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .op(DEFAULT_OP)
            .util(DEFAULT_UTIL)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return eligibiliteExclude;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EligibiliteExclude createUpdatedEntity(EntityManager em) {
        EligibiliteExclude eligibiliteExclude = new EligibiliteExclude()
            .anneeBegin(UPDATED_ANNEE_BEGIN)
            .moisBegin(UPDATED_MOIS_BEGIN)
            .anneeEnd(UPDATED_ANNEE_END)
            .moisEnd(UPDATED_MOIS_END)
            .matricule(UPDATED_MATRICULE)
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .annee(UPDATED_ANNEE)
            .valPayroll(UPDATED_VAL_PAYROLL)
            .nbDaysLeave(UPDATED_NB_DAYS_LEAVE)
            .pourValPayroll(UPDATED_POUR_VAL_PAYROLL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return eligibiliteExclude;
    }

    @BeforeEach
    public void initTest() {
        eligibiliteExclude = createEntity(em);
    }

    @Test
    @Transactional
    void createEligibiliteExclude() throws Exception {
        int databaseSizeBeforeCreate = eligibiliteExcludeRepository.findAll().size();
        // Create the EligibiliteExclude
        restEligibiliteExcludeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibiliteExclude))
            )
            .andExpect(status().isCreated());

        // Validate the EligibiliteExclude in the database
        List<EligibiliteExclude> eligibiliteExcludeList = eligibiliteExcludeRepository.findAll();
        assertThat(eligibiliteExcludeList).hasSize(databaseSizeBeforeCreate + 1);
        EligibiliteExclude testEligibiliteExclude = eligibiliteExcludeList.get(eligibiliteExcludeList.size() - 1);
        assertThat(testEligibiliteExclude.getAnneeBegin()).isEqualTo(DEFAULT_ANNEE_BEGIN);
        assertThat(testEligibiliteExclude.getMoisBegin()).isEqualTo(DEFAULT_MOIS_BEGIN);
        assertThat(testEligibiliteExclude.getAnneeEnd()).isEqualTo(DEFAULT_ANNEE_END);
        assertThat(testEligibiliteExclude.getMoisEnd()).isEqualTo(DEFAULT_MOIS_END);
        assertThat(testEligibiliteExclude.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testEligibiliteExclude.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEligibiliteExclude.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testEligibiliteExclude.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testEligibiliteExclude.getLibFr()).isEqualTo(DEFAULT_LIB_FR);
        assertThat(testEligibiliteExclude.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testEligibiliteExclude.getValPayroll()).isEqualTo(DEFAULT_VAL_PAYROLL);
        assertThat(testEligibiliteExclude.getNbDaysLeave()).isEqualTo(DEFAULT_NB_DAYS_LEAVE);
        assertThat(testEligibiliteExclude.getPourValPayroll()).isEqualTo(DEFAULT_POUR_VAL_PAYROLL);
        assertThat(testEligibiliteExclude.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testEligibiliteExclude.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testEligibiliteExclude.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEligibiliteExclude.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testEligibiliteExclude.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testEligibiliteExclude.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testEligibiliteExclude.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEligibiliteExclude.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createEligibiliteExcludeWithExistingId() throws Exception {
        // Create the EligibiliteExclude with an existing ID
        eligibiliteExclude.setId(1L);

        int databaseSizeBeforeCreate = eligibiliteExcludeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEligibiliteExcludeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibiliteExclude))
            )
            .andExpect(status().isBadRequest());

        // Validate the EligibiliteExclude in the database
        List<EligibiliteExclude> eligibiliteExcludeList = eligibiliteExcludeRepository.findAll();
        assertThat(eligibiliteExcludeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEligibiliteExcludes() throws Exception {
        // Initialize the database
        eligibiliteExcludeRepository.saveAndFlush(eligibiliteExclude);

        // Get all the eligibiliteExcludeList
        restEligibiliteExcludeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eligibiliteExclude.getId().intValue())))
            .andExpect(jsonPath("$.[*].anneeBegin").value(hasItem(DEFAULT_ANNEE_BEGIN)))
            .andExpect(jsonPath("$.[*].moisBegin").value(hasItem(DEFAULT_MOIS_BEGIN)))
            .andExpect(jsonPath("$.[*].anneeEnd").value(hasItem(DEFAULT_ANNEE_END)))
            .andExpect(jsonPath("$.[*].moisEnd").value(hasItem(DEFAULT_MOIS_END)))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].libFr").value(hasItem(DEFAULT_LIB_FR)))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].valPayroll").value(hasItem(DEFAULT_VAL_PAYROLL.doubleValue())))
            .andExpect(jsonPath("$.[*].nbDaysLeave").value(hasItem(DEFAULT_NB_DAYS_LEAVE)))
            .andExpect(jsonPath("$.[*].pourValPayroll").value(hasItem(DEFAULT_POUR_VAL_PAYROLL.doubleValue())))
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
    void getEligibiliteExclude() throws Exception {
        // Initialize the database
        eligibiliteExcludeRepository.saveAndFlush(eligibiliteExclude);

        // Get the eligibiliteExclude
        restEligibiliteExcludeMockMvc
            .perform(get(ENTITY_API_URL_ID, eligibiliteExclude.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eligibiliteExclude.getId().intValue()))
            .andExpect(jsonPath("$.anneeBegin").value(DEFAULT_ANNEE_BEGIN))
            .andExpect(jsonPath("$.moisBegin").value(DEFAULT_MOIS_BEGIN))
            .andExpect(jsonPath("$.anneeEnd").value(DEFAULT_ANNEE_END))
            .andExpect(jsonPath("$.moisEnd").value(DEFAULT_MOIS_END))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.libFr").value(DEFAULT_LIB_FR))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.valPayroll").value(DEFAULT_VAL_PAYROLL.doubleValue()))
            .andExpect(jsonPath("$.nbDaysLeave").value(DEFAULT_NB_DAYS_LEAVE))
            .andExpect(jsonPath("$.pourValPayroll").value(DEFAULT_POUR_VAL_PAYROLL.doubleValue()))
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
    void getNonExistingEligibiliteExclude() throws Exception {
        // Get the eligibiliteExclude
        restEligibiliteExcludeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEligibiliteExclude() throws Exception {
        // Initialize the database
        eligibiliteExcludeRepository.saveAndFlush(eligibiliteExclude);

        int databaseSizeBeforeUpdate = eligibiliteExcludeRepository.findAll().size();

        // Update the eligibiliteExclude
        EligibiliteExclude updatedEligibiliteExclude = eligibiliteExcludeRepository.findById(eligibiliteExclude.getId()).get();
        // Disconnect from session so that the updates on updatedEligibiliteExclude are not directly saved in db
        em.detach(updatedEligibiliteExclude);
        updatedEligibiliteExclude
            .anneeBegin(UPDATED_ANNEE_BEGIN)
            .moisBegin(UPDATED_MOIS_BEGIN)
            .anneeEnd(UPDATED_ANNEE_END)
            .moisEnd(UPDATED_MOIS_END)
            .matricule(UPDATED_MATRICULE)
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .annee(UPDATED_ANNEE)
            .valPayroll(UPDATED_VAL_PAYROLL)
            .nbDaysLeave(UPDATED_NB_DAYS_LEAVE)
            .pourValPayroll(UPDATED_POUR_VAL_PAYROLL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restEligibiliteExcludeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEligibiliteExclude.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEligibiliteExclude))
            )
            .andExpect(status().isOk());

        // Validate the EligibiliteExclude in the database
        List<EligibiliteExclude> eligibiliteExcludeList = eligibiliteExcludeRepository.findAll();
        assertThat(eligibiliteExcludeList).hasSize(databaseSizeBeforeUpdate);
        EligibiliteExclude testEligibiliteExclude = eligibiliteExcludeList.get(eligibiliteExcludeList.size() - 1);
        assertThat(testEligibiliteExclude.getAnneeBegin()).isEqualTo(UPDATED_ANNEE_BEGIN);
        assertThat(testEligibiliteExclude.getMoisBegin()).isEqualTo(UPDATED_MOIS_BEGIN);
        assertThat(testEligibiliteExclude.getAnneeEnd()).isEqualTo(UPDATED_ANNEE_END);
        assertThat(testEligibiliteExclude.getMoisEnd()).isEqualTo(UPDATED_MOIS_END);
        assertThat(testEligibiliteExclude.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEligibiliteExclude.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEligibiliteExclude.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testEligibiliteExclude.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testEligibiliteExclude.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testEligibiliteExclude.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testEligibiliteExclude.getValPayroll()).isEqualTo(UPDATED_VAL_PAYROLL);
        assertThat(testEligibiliteExclude.getNbDaysLeave()).isEqualTo(UPDATED_NB_DAYS_LEAVE);
        assertThat(testEligibiliteExclude.getPourValPayroll()).isEqualTo(UPDATED_POUR_VAL_PAYROLL);
        assertThat(testEligibiliteExclude.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEligibiliteExclude.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEligibiliteExclude.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEligibiliteExclude.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEligibiliteExclude.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEligibiliteExclude.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEligibiliteExclude.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEligibiliteExclude.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingEligibiliteExclude() throws Exception {
        int databaseSizeBeforeUpdate = eligibiliteExcludeRepository.findAll().size();
        eligibiliteExclude.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEligibiliteExcludeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eligibiliteExclude.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eligibiliteExclude))
            )
            .andExpect(status().isBadRequest());

        // Validate the EligibiliteExclude in the database
        List<EligibiliteExclude> eligibiliteExcludeList = eligibiliteExcludeRepository.findAll();
        assertThat(eligibiliteExcludeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEligibiliteExclude() throws Exception {
        int databaseSizeBeforeUpdate = eligibiliteExcludeRepository.findAll().size();
        eligibiliteExclude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibiliteExcludeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eligibiliteExclude))
            )
            .andExpect(status().isBadRequest());

        // Validate the EligibiliteExclude in the database
        List<EligibiliteExclude> eligibiliteExcludeList = eligibiliteExcludeRepository.findAll();
        assertThat(eligibiliteExcludeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEligibiliteExclude() throws Exception {
        int databaseSizeBeforeUpdate = eligibiliteExcludeRepository.findAll().size();
        eligibiliteExclude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibiliteExcludeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibiliteExclude))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EligibiliteExclude in the database
        List<EligibiliteExclude> eligibiliteExcludeList = eligibiliteExcludeRepository.findAll();
        assertThat(eligibiliteExcludeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEligibiliteExcludeWithPatch() throws Exception {
        // Initialize the database
        eligibiliteExcludeRepository.saveAndFlush(eligibiliteExclude);

        int databaseSizeBeforeUpdate = eligibiliteExcludeRepository.findAll().size();

        // Update the eligibiliteExclude using partial update
        EligibiliteExclude partialUpdatedEligibiliteExclude = new EligibiliteExclude();
        partialUpdatedEligibiliteExclude.setId(eligibiliteExclude.getId());

        partialUpdatedEligibiliteExclude
            .anneeBegin(UPDATED_ANNEE_BEGIN)
            .moisBegin(UPDATED_MOIS_BEGIN)
            .anneeEnd(UPDATED_ANNEE_END)
            .moisEnd(UPDATED_MOIS_END)
            .matricule(UPDATED_MATRICULE)
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .annee(UPDATED_ANNEE)
            .nbDaysLeave(UPDATED_NB_DAYS_LEAVE)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restEligibiliteExcludeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEligibiliteExclude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEligibiliteExclude))
            )
            .andExpect(status().isOk());

        // Validate the EligibiliteExclude in the database
        List<EligibiliteExclude> eligibiliteExcludeList = eligibiliteExcludeRepository.findAll();
        assertThat(eligibiliteExcludeList).hasSize(databaseSizeBeforeUpdate);
        EligibiliteExclude testEligibiliteExclude = eligibiliteExcludeList.get(eligibiliteExcludeList.size() - 1);
        assertThat(testEligibiliteExclude.getAnneeBegin()).isEqualTo(UPDATED_ANNEE_BEGIN);
        assertThat(testEligibiliteExclude.getMoisBegin()).isEqualTo(UPDATED_MOIS_BEGIN);
        assertThat(testEligibiliteExclude.getAnneeEnd()).isEqualTo(UPDATED_ANNEE_END);
        assertThat(testEligibiliteExclude.getMoisEnd()).isEqualTo(UPDATED_MOIS_END);
        assertThat(testEligibiliteExclude.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEligibiliteExclude.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEligibiliteExclude.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testEligibiliteExclude.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testEligibiliteExclude.getLibFr()).isEqualTo(DEFAULT_LIB_FR);
        assertThat(testEligibiliteExclude.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testEligibiliteExclude.getValPayroll()).isEqualTo(DEFAULT_VAL_PAYROLL);
        assertThat(testEligibiliteExclude.getNbDaysLeave()).isEqualTo(UPDATED_NB_DAYS_LEAVE);
        assertThat(testEligibiliteExclude.getPourValPayroll()).isEqualTo(DEFAULT_POUR_VAL_PAYROLL);
        assertThat(testEligibiliteExclude.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEligibiliteExclude.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEligibiliteExclude.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEligibiliteExclude.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEligibiliteExclude.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEligibiliteExclude.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEligibiliteExclude.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEligibiliteExclude.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateEligibiliteExcludeWithPatch() throws Exception {
        // Initialize the database
        eligibiliteExcludeRepository.saveAndFlush(eligibiliteExclude);

        int databaseSizeBeforeUpdate = eligibiliteExcludeRepository.findAll().size();

        // Update the eligibiliteExclude using partial update
        EligibiliteExclude partialUpdatedEligibiliteExclude = new EligibiliteExclude();
        partialUpdatedEligibiliteExclude.setId(eligibiliteExclude.getId());

        partialUpdatedEligibiliteExclude
            .anneeBegin(UPDATED_ANNEE_BEGIN)
            .moisBegin(UPDATED_MOIS_BEGIN)
            .anneeEnd(UPDATED_ANNEE_END)
            .moisEnd(UPDATED_MOIS_END)
            .matricule(UPDATED_MATRICULE)
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .annee(UPDATED_ANNEE)
            .valPayroll(UPDATED_VAL_PAYROLL)
            .nbDaysLeave(UPDATED_NB_DAYS_LEAVE)
            .pourValPayroll(UPDATED_POUR_VAL_PAYROLL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restEligibiliteExcludeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEligibiliteExclude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEligibiliteExclude))
            )
            .andExpect(status().isOk());

        // Validate the EligibiliteExclude in the database
        List<EligibiliteExclude> eligibiliteExcludeList = eligibiliteExcludeRepository.findAll();
        assertThat(eligibiliteExcludeList).hasSize(databaseSizeBeforeUpdate);
        EligibiliteExclude testEligibiliteExclude = eligibiliteExcludeList.get(eligibiliteExcludeList.size() - 1);
        assertThat(testEligibiliteExclude.getAnneeBegin()).isEqualTo(UPDATED_ANNEE_BEGIN);
        assertThat(testEligibiliteExclude.getMoisBegin()).isEqualTo(UPDATED_MOIS_BEGIN);
        assertThat(testEligibiliteExclude.getAnneeEnd()).isEqualTo(UPDATED_ANNEE_END);
        assertThat(testEligibiliteExclude.getMoisEnd()).isEqualTo(UPDATED_MOIS_END);
        assertThat(testEligibiliteExclude.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEligibiliteExclude.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEligibiliteExclude.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testEligibiliteExclude.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testEligibiliteExclude.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testEligibiliteExclude.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testEligibiliteExclude.getValPayroll()).isEqualTo(UPDATED_VAL_PAYROLL);
        assertThat(testEligibiliteExclude.getNbDaysLeave()).isEqualTo(UPDATED_NB_DAYS_LEAVE);
        assertThat(testEligibiliteExclude.getPourValPayroll()).isEqualTo(UPDATED_POUR_VAL_PAYROLL);
        assertThat(testEligibiliteExclude.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEligibiliteExclude.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEligibiliteExclude.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEligibiliteExclude.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEligibiliteExclude.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEligibiliteExclude.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEligibiliteExclude.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEligibiliteExclude.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingEligibiliteExclude() throws Exception {
        int databaseSizeBeforeUpdate = eligibiliteExcludeRepository.findAll().size();
        eligibiliteExclude.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEligibiliteExcludeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eligibiliteExclude.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eligibiliteExclude))
            )
            .andExpect(status().isBadRequest());

        // Validate the EligibiliteExclude in the database
        List<EligibiliteExclude> eligibiliteExcludeList = eligibiliteExcludeRepository.findAll();
        assertThat(eligibiliteExcludeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEligibiliteExclude() throws Exception {
        int databaseSizeBeforeUpdate = eligibiliteExcludeRepository.findAll().size();
        eligibiliteExclude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibiliteExcludeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eligibiliteExclude))
            )
            .andExpect(status().isBadRequest());

        // Validate the EligibiliteExclude in the database
        List<EligibiliteExclude> eligibiliteExcludeList = eligibiliteExcludeRepository.findAll();
        assertThat(eligibiliteExcludeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEligibiliteExclude() throws Exception {
        int databaseSizeBeforeUpdate = eligibiliteExcludeRepository.findAll().size();
        eligibiliteExclude.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibiliteExcludeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eligibiliteExclude))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EligibiliteExclude in the database
        List<EligibiliteExclude> eligibiliteExcludeList = eligibiliteExcludeRepository.findAll();
        assertThat(eligibiliteExcludeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEligibiliteExclude() throws Exception {
        // Initialize the database
        eligibiliteExcludeRepository.saveAndFlush(eligibiliteExclude);

        int databaseSizeBeforeDelete = eligibiliteExcludeRepository.findAll().size();

        // Delete the eligibiliteExclude
        restEligibiliteExcludeMockMvc
            .perform(delete(ENTITY_API_URL_ID, eligibiliteExclude.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EligibiliteExclude> eligibiliteExcludeList = eligibiliteExcludeRepository.findAll();
        assertThat(eligibiliteExcludeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
