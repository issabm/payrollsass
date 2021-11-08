package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.SoldeAbsence;
import com.issa.payroll.repository.SoldeAbsenceRepository;
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
 * Integration tests for the {@link SoldeAbsenceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SoldeAbsenceResourceIT {

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final Integer DEFAULT_NB_DAYS_RIGHT = 1;
    private static final Integer UPDATED_NB_DAYS_RIGHT = 2;

    private static final Integer DEFAULT_NB_DAYS_CONSUMED = 1;
    private static final Integer UPDATED_NB_DAYS_CONSUMED = 2;

    private static final Integer DEFAULT_NB_DAYS_UNAVAILBLE = 1;
    private static final Integer UPDATED_NB_DAYS_UNAVAILBLE = 2;

    private static final Integer DEFAULT_NB_DAYS_AVAILBLE = 1;
    private static final Integer UPDATED_NB_DAYS_AVAILBLE = 2;

    private static final Integer DEFAULT_NB_DAYS_LEFT = 1;
    private static final Integer UPDATED_NB_DAYS_LEFT = 2;

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

    private static final String ENTITY_API_URL = "/api/solde-absences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SoldeAbsenceRepository soldeAbsenceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoldeAbsenceMockMvc;

    private SoldeAbsence soldeAbsence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoldeAbsence createEntity(EntityManager em) {
        SoldeAbsence soldeAbsence = new SoldeAbsence()
            .annee(DEFAULT_ANNEE)
            .nbDaysRight(DEFAULT_NB_DAYS_RIGHT)
            .nbDaysConsumed(DEFAULT_NB_DAYS_CONSUMED)
            .nbDaysUnavailble(DEFAULT_NB_DAYS_UNAVAILBLE)
            .nbDaysAvailble(DEFAULT_NB_DAYS_AVAILBLE)
            .nbDaysLeft(DEFAULT_NB_DAYS_LEFT)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return soldeAbsence;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoldeAbsence createUpdatedEntity(EntityManager em) {
        SoldeAbsence soldeAbsence = new SoldeAbsence()
            .annee(UPDATED_ANNEE)
            .nbDaysRight(UPDATED_NB_DAYS_RIGHT)
            .nbDaysConsumed(UPDATED_NB_DAYS_CONSUMED)
            .nbDaysUnavailble(UPDATED_NB_DAYS_UNAVAILBLE)
            .nbDaysAvailble(UPDATED_NB_DAYS_AVAILBLE)
            .nbDaysLeft(UPDATED_NB_DAYS_LEFT)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return soldeAbsence;
    }

    @BeforeEach
    public void initTest() {
        soldeAbsence = createEntity(em);
    }

    @Test
    @Transactional
    void createSoldeAbsence() throws Exception {
        int databaseSizeBeforeCreate = soldeAbsenceRepository.findAll().size();
        // Create the SoldeAbsence
        restSoldeAbsenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldeAbsence)))
            .andExpect(status().isCreated());

        // Validate the SoldeAbsence in the database
        List<SoldeAbsence> soldeAbsenceList = soldeAbsenceRepository.findAll();
        assertThat(soldeAbsenceList).hasSize(databaseSizeBeforeCreate + 1);
        SoldeAbsence testSoldeAbsence = soldeAbsenceList.get(soldeAbsenceList.size() - 1);
        assertThat(testSoldeAbsence.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testSoldeAbsence.getNbDaysRight()).isEqualTo(DEFAULT_NB_DAYS_RIGHT);
        assertThat(testSoldeAbsence.getNbDaysConsumed()).isEqualTo(DEFAULT_NB_DAYS_CONSUMED);
        assertThat(testSoldeAbsence.getNbDaysUnavailble()).isEqualTo(DEFAULT_NB_DAYS_UNAVAILBLE);
        assertThat(testSoldeAbsence.getNbDaysAvailble()).isEqualTo(DEFAULT_NB_DAYS_AVAILBLE);
        assertThat(testSoldeAbsence.getNbDaysLeft()).isEqualTo(DEFAULT_NB_DAYS_LEFT);
        assertThat(testSoldeAbsence.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testSoldeAbsence.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testSoldeAbsence.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSoldeAbsence.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testSoldeAbsence.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSoldeAbsence.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSoldeAbsence.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSoldeAbsenceWithExistingId() throws Exception {
        // Create the SoldeAbsence with an existing ID
        soldeAbsence.setId(1L);

        int databaseSizeBeforeCreate = soldeAbsenceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoldeAbsenceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldeAbsence)))
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsence in the database
        List<SoldeAbsence> soldeAbsenceList = soldeAbsenceRepository.findAll();
        assertThat(soldeAbsenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSoldeAbsences() throws Exception {
        // Initialize the database
        soldeAbsenceRepository.saveAndFlush(soldeAbsence);

        // Get all the soldeAbsenceList
        restSoldeAbsenceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soldeAbsence.getId().intValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].nbDaysRight").value(hasItem(DEFAULT_NB_DAYS_RIGHT)))
            .andExpect(jsonPath("$.[*].nbDaysConsumed").value(hasItem(DEFAULT_NB_DAYS_CONSUMED)))
            .andExpect(jsonPath("$.[*].nbDaysUnavailble").value(hasItem(DEFAULT_NB_DAYS_UNAVAILBLE)))
            .andExpect(jsonPath("$.[*].nbDaysAvailble").value(hasItem(DEFAULT_NB_DAYS_AVAILBLE)))
            .andExpect(jsonPath("$.[*].nbDaysLeft").value(hasItem(DEFAULT_NB_DAYS_LEFT)))
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
    void getSoldeAbsence() throws Exception {
        // Initialize the database
        soldeAbsenceRepository.saveAndFlush(soldeAbsence);

        // Get the soldeAbsence
        restSoldeAbsenceMockMvc
            .perform(get(ENTITY_API_URL_ID, soldeAbsence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soldeAbsence.getId().intValue()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.nbDaysRight").value(DEFAULT_NB_DAYS_RIGHT))
            .andExpect(jsonPath("$.nbDaysConsumed").value(DEFAULT_NB_DAYS_CONSUMED))
            .andExpect(jsonPath("$.nbDaysUnavailble").value(DEFAULT_NB_DAYS_UNAVAILBLE))
            .andExpect(jsonPath("$.nbDaysAvailble").value(DEFAULT_NB_DAYS_AVAILBLE))
            .andExpect(jsonPath("$.nbDaysLeft").value(DEFAULT_NB_DAYS_LEFT))
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
    void getNonExistingSoldeAbsence() throws Exception {
        // Get the soldeAbsence
        restSoldeAbsenceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSoldeAbsence() throws Exception {
        // Initialize the database
        soldeAbsenceRepository.saveAndFlush(soldeAbsence);

        int databaseSizeBeforeUpdate = soldeAbsenceRepository.findAll().size();

        // Update the soldeAbsence
        SoldeAbsence updatedSoldeAbsence = soldeAbsenceRepository.findById(soldeAbsence.getId()).get();
        // Disconnect from session so that the updates on updatedSoldeAbsence are not directly saved in db
        em.detach(updatedSoldeAbsence);
        updatedSoldeAbsence
            .annee(UPDATED_ANNEE)
            .nbDaysRight(UPDATED_NB_DAYS_RIGHT)
            .nbDaysConsumed(UPDATED_NB_DAYS_CONSUMED)
            .nbDaysUnavailble(UPDATED_NB_DAYS_UNAVAILBLE)
            .nbDaysAvailble(UPDATED_NB_DAYS_AVAILBLE)
            .nbDaysLeft(UPDATED_NB_DAYS_LEFT)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restSoldeAbsenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSoldeAbsence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSoldeAbsence))
            )
            .andExpect(status().isOk());

        // Validate the SoldeAbsence in the database
        List<SoldeAbsence> soldeAbsenceList = soldeAbsenceRepository.findAll();
        assertThat(soldeAbsenceList).hasSize(databaseSizeBeforeUpdate);
        SoldeAbsence testSoldeAbsence = soldeAbsenceList.get(soldeAbsenceList.size() - 1);
        assertThat(testSoldeAbsence.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSoldeAbsence.getNbDaysRight()).isEqualTo(UPDATED_NB_DAYS_RIGHT);
        assertThat(testSoldeAbsence.getNbDaysConsumed()).isEqualTo(UPDATED_NB_DAYS_CONSUMED);
        assertThat(testSoldeAbsence.getNbDaysUnavailble()).isEqualTo(UPDATED_NB_DAYS_UNAVAILBLE);
        assertThat(testSoldeAbsence.getNbDaysAvailble()).isEqualTo(UPDATED_NB_DAYS_AVAILBLE);
        assertThat(testSoldeAbsence.getNbDaysLeft()).isEqualTo(UPDATED_NB_DAYS_LEFT);
        assertThat(testSoldeAbsence.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSoldeAbsence.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSoldeAbsence.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSoldeAbsence.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSoldeAbsence.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSoldeAbsence.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSoldeAbsence.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSoldeAbsence() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsenceRepository.findAll().size();
        soldeAbsence.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoldeAbsenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soldeAbsence.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soldeAbsence))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsence in the database
        List<SoldeAbsence> soldeAbsenceList = soldeAbsenceRepository.findAll();
        assertThat(soldeAbsenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoldeAbsence() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsenceRepository.findAll().size();
        soldeAbsence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldeAbsenceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soldeAbsence))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsence in the database
        List<SoldeAbsence> soldeAbsenceList = soldeAbsenceRepository.findAll();
        assertThat(soldeAbsenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoldeAbsence() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsenceRepository.findAll().size();
        soldeAbsence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldeAbsenceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldeAbsence)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SoldeAbsence in the database
        List<SoldeAbsence> soldeAbsenceList = soldeAbsenceRepository.findAll();
        assertThat(soldeAbsenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoldeAbsenceWithPatch() throws Exception {
        // Initialize the database
        soldeAbsenceRepository.saveAndFlush(soldeAbsence);

        int databaseSizeBeforeUpdate = soldeAbsenceRepository.findAll().size();

        // Update the soldeAbsence using partial update
        SoldeAbsence partialUpdatedSoldeAbsence = new SoldeAbsence();
        partialUpdatedSoldeAbsence.setId(soldeAbsence.getId());

        partialUpdatedSoldeAbsence.op(UPDATED_OP);

        restSoldeAbsenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoldeAbsence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoldeAbsence))
            )
            .andExpect(status().isOk());

        // Validate the SoldeAbsence in the database
        List<SoldeAbsence> soldeAbsenceList = soldeAbsenceRepository.findAll();
        assertThat(soldeAbsenceList).hasSize(databaseSizeBeforeUpdate);
        SoldeAbsence testSoldeAbsence = soldeAbsenceList.get(soldeAbsenceList.size() - 1);
        assertThat(testSoldeAbsence.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testSoldeAbsence.getNbDaysRight()).isEqualTo(DEFAULT_NB_DAYS_RIGHT);
        assertThat(testSoldeAbsence.getNbDaysConsumed()).isEqualTo(DEFAULT_NB_DAYS_CONSUMED);
        assertThat(testSoldeAbsence.getNbDaysUnavailble()).isEqualTo(DEFAULT_NB_DAYS_UNAVAILBLE);
        assertThat(testSoldeAbsence.getNbDaysAvailble()).isEqualTo(DEFAULT_NB_DAYS_AVAILBLE);
        assertThat(testSoldeAbsence.getNbDaysLeft()).isEqualTo(DEFAULT_NB_DAYS_LEFT);
        assertThat(testSoldeAbsence.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testSoldeAbsence.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testSoldeAbsence.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSoldeAbsence.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSoldeAbsence.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSoldeAbsence.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSoldeAbsence.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSoldeAbsenceWithPatch() throws Exception {
        // Initialize the database
        soldeAbsenceRepository.saveAndFlush(soldeAbsence);

        int databaseSizeBeforeUpdate = soldeAbsenceRepository.findAll().size();

        // Update the soldeAbsence using partial update
        SoldeAbsence partialUpdatedSoldeAbsence = new SoldeAbsence();
        partialUpdatedSoldeAbsence.setId(soldeAbsence.getId());

        partialUpdatedSoldeAbsence
            .annee(UPDATED_ANNEE)
            .nbDaysRight(UPDATED_NB_DAYS_RIGHT)
            .nbDaysConsumed(UPDATED_NB_DAYS_CONSUMED)
            .nbDaysUnavailble(UPDATED_NB_DAYS_UNAVAILBLE)
            .nbDaysAvailble(UPDATED_NB_DAYS_AVAILBLE)
            .nbDaysLeft(UPDATED_NB_DAYS_LEFT)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restSoldeAbsenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoldeAbsence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoldeAbsence))
            )
            .andExpect(status().isOk());

        // Validate the SoldeAbsence in the database
        List<SoldeAbsence> soldeAbsenceList = soldeAbsenceRepository.findAll();
        assertThat(soldeAbsenceList).hasSize(databaseSizeBeforeUpdate);
        SoldeAbsence testSoldeAbsence = soldeAbsenceList.get(soldeAbsenceList.size() - 1);
        assertThat(testSoldeAbsence.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSoldeAbsence.getNbDaysRight()).isEqualTo(UPDATED_NB_DAYS_RIGHT);
        assertThat(testSoldeAbsence.getNbDaysConsumed()).isEqualTo(UPDATED_NB_DAYS_CONSUMED);
        assertThat(testSoldeAbsence.getNbDaysUnavailble()).isEqualTo(UPDATED_NB_DAYS_UNAVAILBLE);
        assertThat(testSoldeAbsence.getNbDaysAvailble()).isEqualTo(UPDATED_NB_DAYS_AVAILBLE);
        assertThat(testSoldeAbsence.getNbDaysLeft()).isEqualTo(UPDATED_NB_DAYS_LEFT);
        assertThat(testSoldeAbsence.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSoldeAbsence.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSoldeAbsence.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSoldeAbsence.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSoldeAbsence.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSoldeAbsence.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSoldeAbsence.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSoldeAbsence() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsenceRepository.findAll().size();
        soldeAbsence.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoldeAbsenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soldeAbsence.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soldeAbsence))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsence in the database
        List<SoldeAbsence> soldeAbsenceList = soldeAbsenceRepository.findAll();
        assertThat(soldeAbsenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoldeAbsence() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsenceRepository.findAll().size();
        soldeAbsence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldeAbsenceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soldeAbsence))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsence in the database
        List<SoldeAbsence> soldeAbsenceList = soldeAbsenceRepository.findAll();
        assertThat(soldeAbsenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoldeAbsence() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsenceRepository.findAll().size();
        soldeAbsence.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldeAbsenceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(soldeAbsence))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SoldeAbsence in the database
        List<SoldeAbsence> soldeAbsenceList = soldeAbsenceRepository.findAll();
        assertThat(soldeAbsenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoldeAbsence() throws Exception {
        // Initialize the database
        soldeAbsenceRepository.saveAndFlush(soldeAbsence);

        int databaseSizeBeforeDelete = soldeAbsenceRepository.findAll().size();

        // Delete the soldeAbsence
        restSoldeAbsenceMockMvc
            .perform(delete(ENTITY_API_URL_ID, soldeAbsence.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SoldeAbsence> soldeAbsenceList = soldeAbsenceRepository.findAll();
        assertThat(soldeAbsenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
