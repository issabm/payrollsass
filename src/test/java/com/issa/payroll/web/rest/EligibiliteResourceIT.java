package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Eligibilite;
import com.issa.payroll.repository.EligibiliteRepository;
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
 * Integration tests for the {@link EligibiliteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EligibiliteResourceIT {

    private static final Integer DEFAULT_PRIORITE = 1;
    private static final Integer UPDATED_PRIORITE = 2;

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final Integer DEFAULT_MOIS = 1;
    private static final Integer UPDATED_MOIS = 2;

    private static final Integer DEFAULT_NB_ENT = 1;
    private static final Integer UPDATED_NB_ENT = 2;

    private static final Integer DEFAULT_AGE_ENT = 1;
    private static final Integer UPDATED_AGE_ENT = 2;

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

    private static final String ENTITY_API_URL = "/api/eligibilites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EligibiliteRepository eligibiliteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEligibiliteMockMvc;

    private Eligibilite eligibilite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eligibilite createEntity(EntityManager em) {
        Eligibilite eligibilite = new Eligibilite()
            .priorite(DEFAULT_PRIORITE)
            .annee(DEFAULT_ANNEE)
            .mois(DEFAULT_MOIS)
            .nbEnt(DEFAULT_NB_ENT)
            .ageEnt(DEFAULT_AGE_ENT)
            .matricule(DEFAULT_MATRICULE)
            .code(DEFAULT_CODE)
            .libEn(DEFAULT_LIB_EN)
            .libAr(DEFAULT_LIB_AR)
            .libFr(DEFAULT_LIB_FR)
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
        return eligibilite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Eligibilite createUpdatedEntity(EntityManager em) {
        Eligibilite eligibilite = new Eligibilite()
            .priorite(UPDATED_PRIORITE)
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .nbEnt(UPDATED_NB_ENT)
            .ageEnt(UPDATED_AGE_ENT)
            .matricule(UPDATED_MATRICULE)
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
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
        return eligibilite;
    }

    @BeforeEach
    public void initTest() {
        eligibilite = createEntity(em);
    }

    @Test
    @Transactional
    void createEligibilite() throws Exception {
        int databaseSizeBeforeCreate = eligibiliteRepository.findAll().size();
        // Create the Eligibilite
        restEligibiliteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibilite)))
            .andExpect(status().isCreated());

        // Validate the Eligibilite in the database
        List<Eligibilite> eligibiliteList = eligibiliteRepository.findAll();
        assertThat(eligibiliteList).hasSize(databaseSizeBeforeCreate + 1);
        Eligibilite testEligibilite = eligibiliteList.get(eligibiliteList.size() - 1);
        assertThat(testEligibilite.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
        assertThat(testEligibilite.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testEligibilite.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testEligibilite.getNbEnt()).isEqualTo(DEFAULT_NB_ENT);
        assertThat(testEligibilite.getAgeEnt()).isEqualTo(DEFAULT_AGE_ENT);
        assertThat(testEligibilite.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testEligibilite.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEligibilite.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testEligibilite.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testEligibilite.getLibFr()).isEqualTo(DEFAULT_LIB_FR);
        assertThat(testEligibilite.getValPayroll()).isEqualTo(DEFAULT_VAL_PAYROLL);
        assertThat(testEligibilite.getNbDaysLeave()).isEqualTo(DEFAULT_NB_DAYS_LEAVE);
        assertThat(testEligibilite.getPourValPayroll()).isEqualTo(DEFAULT_POUR_VAL_PAYROLL);
        assertThat(testEligibilite.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testEligibilite.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testEligibilite.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEligibilite.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testEligibilite.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testEligibilite.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testEligibilite.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEligibilite.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createEligibiliteWithExistingId() throws Exception {
        // Create the Eligibilite with an existing ID
        eligibilite.setId(1L);

        int databaseSizeBeforeCreate = eligibiliteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEligibiliteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibilite)))
            .andExpect(status().isBadRequest());

        // Validate the Eligibilite in the database
        List<Eligibilite> eligibiliteList = eligibiliteRepository.findAll();
        assertThat(eligibiliteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEligibilites() throws Exception {
        // Initialize the database
        eligibiliteRepository.saveAndFlush(eligibilite);

        // Get all the eligibiliteList
        restEligibiliteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eligibilite.getId().intValue())))
            .andExpect(jsonPath("$.[*].priorite").value(hasItem(DEFAULT_PRIORITE)))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].mois").value(hasItem(DEFAULT_MOIS)))
            .andExpect(jsonPath("$.[*].nbEnt").value(hasItem(DEFAULT_NB_ENT)))
            .andExpect(jsonPath("$.[*].ageEnt").value(hasItem(DEFAULT_AGE_ENT)))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].libFr").value(hasItem(DEFAULT_LIB_FR)))
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
    void getEligibilite() throws Exception {
        // Initialize the database
        eligibiliteRepository.saveAndFlush(eligibilite);

        // Get the eligibilite
        restEligibiliteMockMvc
            .perform(get(ENTITY_API_URL_ID, eligibilite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eligibilite.getId().intValue()))
            .andExpect(jsonPath("$.priorite").value(DEFAULT_PRIORITE))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.mois").value(DEFAULT_MOIS))
            .andExpect(jsonPath("$.nbEnt").value(DEFAULT_NB_ENT))
            .andExpect(jsonPath("$.ageEnt").value(DEFAULT_AGE_ENT))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.libFr").value(DEFAULT_LIB_FR))
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
    void getNonExistingEligibilite() throws Exception {
        // Get the eligibilite
        restEligibiliteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEligibilite() throws Exception {
        // Initialize the database
        eligibiliteRepository.saveAndFlush(eligibilite);

        int databaseSizeBeforeUpdate = eligibiliteRepository.findAll().size();

        // Update the eligibilite
        Eligibilite updatedEligibilite = eligibiliteRepository.findById(eligibilite.getId()).get();
        // Disconnect from session so that the updates on updatedEligibilite are not directly saved in db
        em.detach(updatedEligibilite);
        updatedEligibilite
            .priorite(UPDATED_PRIORITE)
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .nbEnt(UPDATED_NB_ENT)
            .ageEnt(UPDATED_AGE_ENT)
            .matricule(UPDATED_MATRICULE)
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
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

        restEligibiliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEligibilite.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEligibilite))
            )
            .andExpect(status().isOk());

        // Validate the Eligibilite in the database
        List<Eligibilite> eligibiliteList = eligibiliteRepository.findAll();
        assertThat(eligibiliteList).hasSize(databaseSizeBeforeUpdate);
        Eligibilite testEligibilite = eligibiliteList.get(eligibiliteList.size() - 1);
        assertThat(testEligibilite.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testEligibilite.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testEligibilite.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testEligibilite.getNbEnt()).isEqualTo(UPDATED_NB_ENT);
        assertThat(testEligibilite.getAgeEnt()).isEqualTo(UPDATED_AGE_ENT);
        assertThat(testEligibilite.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEligibilite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEligibilite.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testEligibilite.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testEligibilite.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testEligibilite.getValPayroll()).isEqualTo(UPDATED_VAL_PAYROLL);
        assertThat(testEligibilite.getNbDaysLeave()).isEqualTo(UPDATED_NB_DAYS_LEAVE);
        assertThat(testEligibilite.getPourValPayroll()).isEqualTo(UPDATED_POUR_VAL_PAYROLL);
        assertThat(testEligibilite.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEligibilite.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEligibilite.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEligibilite.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEligibilite.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEligibilite.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEligibilite.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEligibilite.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingEligibilite() throws Exception {
        int databaseSizeBeforeUpdate = eligibiliteRepository.findAll().size();
        eligibilite.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEligibiliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eligibilite.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eligibilite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eligibilite in the database
        List<Eligibilite> eligibiliteList = eligibiliteRepository.findAll();
        assertThat(eligibiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEligibilite() throws Exception {
        int databaseSizeBeforeUpdate = eligibiliteRepository.findAll().size();
        eligibilite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibiliteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eligibilite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eligibilite in the database
        List<Eligibilite> eligibiliteList = eligibiliteRepository.findAll();
        assertThat(eligibiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEligibilite() throws Exception {
        int databaseSizeBeforeUpdate = eligibiliteRepository.findAll().size();
        eligibilite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibiliteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eligibilite)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eligibilite in the database
        List<Eligibilite> eligibiliteList = eligibiliteRepository.findAll();
        assertThat(eligibiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEligibiliteWithPatch() throws Exception {
        // Initialize the database
        eligibiliteRepository.saveAndFlush(eligibilite);

        int databaseSizeBeforeUpdate = eligibiliteRepository.findAll().size();

        // Update the eligibilite using partial update
        Eligibilite partialUpdatedEligibilite = new Eligibilite();
        partialUpdatedEligibilite.setId(eligibilite.getId());

        partialUpdatedEligibilite
            .nbEnt(UPDATED_NB_ENT)
            .ageEnt(UPDATED_AGE_ENT)
            .matricule(UPDATED_MATRICULE)
            .code(UPDATED_CODE)
            .libFr(UPDATED_LIB_FR)
            .valPayroll(UPDATED_VAL_PAYROLL)
            .dateop(UPDATED_DATEOP)
            .createdBy(UPDATED_CREATED_BY)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED);

        restEligibiliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEligibilite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEligibilite))
            )
            .andExpect(status().isOk());

        // Validate the Eligibilite in the database
        List<Eligibilite> eligibiliteList = eligibiliteRepository.findAll();
        assertThat(eligibiliteList).hasSize(databaseSizeBeforeUpdate);
        Eligibilite testEligibilite = eligibiliteList.get(eligibiliteList.size() - 1);
        assertThat(testEligibilite.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
        assertThat(testEligibilite.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testEligibilite.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testEligibilite.getNbEnt()).isEqualTo(UPDATED_NB_ENT);
        assertThat(testEligibilite.getAgeEnt()).isEqualTo(UPDATED_AGE_ENT);
        assertThat(testEligibilite.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEligibilite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEligibilite.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testEligibilite.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testEligibilite.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testEligibilite.getValPayroll()).isEqualTo(UPDATED_VAL_PAYROLL);
        assertThat(testEligibilite.getNbDaysLeave()).isEqualTo(DEFAULT_NB_DAYS_LEAVE);
        assertThat(testEligibilite.getPourValPayroll()).isEqualTo(DEFAULT_POUR_VAL_PAYROLL);
        assertThat(testEligibilite.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEligibilite.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testEligibilite.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEligibilite.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testEligibilite.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEligibilite.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEligibilite.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testEligibilite.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateEligibiliteWithPatch() throws Exception {
        // Initialize the database
        eligibiliteRepository.saveAndFlush(eligibilite);

        int databaseSizeBeforeUpdate = eligibiliteRepository.findAll().size();

        // Update the eligibilite using partial update
        Eligibilite partialUpdatedEligibilite = new Eligibilite();
        partialUpdatedEligibilite.setId(eligibilite.getId());

        partialUpdatedEligibilite
            .priorite(UPDATED_PRIORITE)
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .nbEnt(UPDATED_NB_ENT)
            .ageEnt(UPDATED_AGE_ENT)
            .matricule(UPDATED_MATRICULE)
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
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

        restEligibiliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEligibilite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEligibilite))
            )
            .andExpect(status().isOk());

        // Validate the Eligibilite in the database
        List<Eligibilite> eligibiliteList = eligibiliteRepository.findAll();
        assertThat(eligibiliteList).hasSize(databaseSizeBeforeUpdate);
        Eligibilite testEligibilite = eligibiliteList.get(eligibiliteList.size() - 1);
        assertThat(testEligibilite.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testEligibilite.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testEligibilite.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testEligibilite.getNbEnt()).isEqualTo(UPDATED_NB_ENT);
        assertThat(testEligibilite.getAgeEnt()).isEqualTo(UPDATED_AGE_ENT);
        assertThat(testEligibilite.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEligibilite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEligibilite.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testEligibilite.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testEligibilite.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testEligibilite.getValPayroll()).isEqualTo(UPDATED_VAL_PAYROLL);
        assertThat(testEligibilite.getNbDaysLeave()).isEqualTo(UPDATED_NB_DAYS_LEAVE);
        assertThat(testEligibilite.getPourValPayroll()).isEqualTo(UPDATED_POUR_VAL_PAYROLL);
        assertThat(testEligibilite.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEligibilite.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEligibilite.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEligibilite.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEligibilite.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEligibilite.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEligibilite.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testEligibilite.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingEligibilite() throws Exception {
        int databaseSizeBeforeUpdate = eligibiliteRepository.findAll().size();
        eligibilite.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEligibiliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eligibilite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eligibilite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eligibilite in the database
        List<Eligibilite> eligibiliteList = eligibiliteRepository.findAll();
        assertThat(eligibiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEligibilite() throws Exception {
        int databaseSizeBeforeUpdate = eligibiliteRepository.findAll().size();
        eligibilite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibiliteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eligibilite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Eligibilite in the database
        List<Eligibilite> eligibiliteList = eligibiliteRepository.findAll();
        assertThat(eligibiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEligibilite() throws Exception {
        int databaseSizeBeforeUpdate = eligibiliteRepository.findAll().size();
        eligibilite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEligibiliteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eligibilite))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Eligibilite in the database
        List<Eligibilite> eligibiliteList = eligibiliteRepository.findAll();
        assertThat(eligibiliteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEligibilite() throws Exception {
        // Initialize the database
        eligibiliteRepository.saveAndFlush(eligibilite);

        int databaseSizeBeforeDelete = eligibiliteRepository.findAll().size();

        // Delete the eligibilite
        restEligibiliteMockMvc
            .perform(delete(ENTITY_API_URL_ID, eligibilite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Eligibilite> eligibiliteList = eligibiliteRepository.findAll();
        assertThat(eligibiliteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
