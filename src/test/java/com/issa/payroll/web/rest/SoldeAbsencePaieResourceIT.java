package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.SoldeAbsencePaie;
import com.issa.payroll.repository.SoldeAbsencePaieRepository;
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
 * Integration tests for the {@link SoldeAbsencePaieResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SoldeAbsencePaieResourceIT {

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final Integer DEFAULT_MOIS = 1;
    private static final Integer UPDATED_MOIS = 2;

    private static final Integer DEFAULT_NB_DAYS = 1;
    private static final Integer UPDATED_NB_DAYS = 2;

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

    private static final String ENTITY_API_URL = "/api/solde-absence-paies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SoldeAbsencePaieRepository soldeAbsencePaieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSoldeAbsencePaieMockMvc;

    private SoldeAbsencePaie soldeAbsencePaie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoldeAbsencePaie createEntity(EntityManager em) {
        SoldeAbsencePaie soldeAbsencePaie = new SoldeAbsencePaie()
            .annee(DEFAULT_ANNEE)
            .mois(DEFAULT_MOIS)
            .nbDays(DEFAULT_NB_DAYS)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return soldeAbsencePaie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SoldeAbsencePaie createUpdatedEntity(EntityManager em) {
        SoldeAbsencePaie soldeAbsencePaie = new SoldeAbsencePaie()
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .nbDays(UPDATED_NB_DAYS)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return soldeAbsencePaie;
    }

    @BeforeEach
    public void initTest() {
        soldeAbsencePaie = createEntity(em);
    }

    @Test
    @Transactional
    void createSoldeAbsencePaie() throws Exception {
        int databaseSizeBeforeCreate = soldeAbsencePaieRepository.findAll().size();
        // Create the SoldeAbsencePaie
        restSoldeAbsencePaieMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldeAbsencePaie))
            )
            .andExpect(status().isCreated());

        // Validate the SoldeAbsencePaie in the database
        List<SoldeAbsencePaie> soldeAbsencePaieList = soldeAbsencePaieRepository.findAll();
        assertThat(soldeAbsencePaieList).hasSize(databaseSizeBeforeCreate + 1);
        SoldeAbsencePaie testSoldeAbsencePaie = soldeAbsencePaieList.get(soldeAbsencePaieList.size() - 1);
        assertThat(testSoldeAbsencePaie.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testSoldeAbsencePaie.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testSoldeAbsencePaie.getNbDays()).isEqualTo(DEFAULT_NB_DAYS);
        assertThat(testSoldeAbsencePaie.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testSoldeAbsencePaie.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testSoldeAbsencePaie.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSoldeAbsencePaie.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testSoldeAbsencePaie.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSoldeAbsencePaie.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSoldeAbsencePaie.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createSoldeAbsencePaieWithExistingId() throws Exception {
        // Create the SoldeAbsencePaie with an existing ID
        soldeAbsencePaie.setId(1L);

        int databaseSizeBeforeCreate = soldeAbsencePaieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSoldeAbsencePaieMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldeAbsencePaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsencePaie in the database
        List<SoldeAbsencePaie> soldeAbsencePaieList = soldeAbsencePaieRepository.findAll();
        assertThat(soldeAbsencePaieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSoldeAbsencePaies() throws Exception {
        // Initialize the database
        soldeAbsencePaieRepository.saveAndFlush(soldeAbsencePaie);

        // Get all the soldeAbsencePaieList
        restSoldeAbsencePaieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(soldeAbsencePaie.getId().intValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].mois").value(hasItem(DEFAULT_MOIS)))
            .andExpect(jsonPath("$.[*].nbDays").value(hasItem(DEFAULT_NB_DAYS)))
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
    void getSoldeAbsencePaie() throws Exception {
        // Initialize the database
        soldeAbsencePaieRepository.saveAndFlush(soldeAbsencePaie);

        // Get the soldeAbsencePaie
        restSoldeAbsencePaieMockMvc
            .perform(get(ENTITY_API_URL_ID, soldeAbsencePaie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(soldeAbsencePaie.getId().intValue()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.mois").value(DEFAULT_MOIS))
            .andExpect(jsonPath("$.nbDays").value(DEFAULT_NB_DAYS))
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
    void getNonExistingSoldeAbsencePaie() throws Exception {
        // Get the soldeAbsencePaie
        restSoldeAbsencePaieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSoldeAbsencePaie() throws Exception {
        // Initialize the database
        soldeAbsencePaieRepository.saveAndFlush(soldeAbsencePaie);

        int databaseSizeBeforeUpdate = soldeAbsencePaieRepository.findAll().size();

        // Update the soldeAbsencePaie
        SoldeAbsencePaie updatedSoldeAbsencePaie = soldeAbsencePaieRepository.findById(soldeAbsencePaie.getId()).get();
        // Disconnect from session so that the updates on updatedSoldeAbsencePaie are not directly saved in db
        em.detach(updatedSoldeAbsencePaie);
        updatedSoldeAbsencePaie
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .nbDays(UPDATED_NB_DAYS)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restSoldeAbsencePaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSoldeAbsencePaie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSoldeAbsencePaie))
            )
            .andExpect(status().isOk());

        // Validate the SoldeAbsencePaie in the database
        List<SoldeAbsencePaie> soldeAbsencePaieList = soldeAbsencePaieRepository.findAll();
        assertThat(soldeAbsencePaieList).hasSize(databaseSizeBeforeUpdate);
        SoldeAbsencePaie testSoldeAbsencePaie = soldeAbsencePaieList.get(soldeAbsencePaieList.size() - 1);
        assertThat(testSoldeAbsencePaie.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSoldeAbsencePaie.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testSoldeAbsencePaie.getNbDays()).isEqualTo(UPDATED_NB_DAYS);
        assertThat(testSoldeAbsencePaie.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSoldeAbsencePaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSoldeAbsencePaie.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSoldeAbsencePaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSoldeAbsencePaie.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSoldeAbsencePaie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSoldeAbsencePaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingSoldeAbsencePaie() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsencePaieRepository.findAll().size();
        soldeAbsencePaie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoldeAbsencePaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, soldeAbsencePaie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soldeAbsencePaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsencePaie in the database
        List<SoldeAbsencePaie> soldeAbsencePaieList = soldeAbsencePaieRepository.findAll();
        assertThat(soldeAbsencePaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSoldeAbsencePaie() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsencePaieRepository.findAll().size();
        soldeAbsencePaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldeAbsencePaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(soldeAbsencePaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsencePaie in the database
        List<SoldeAbsencePaie> soldeAbsencePaieList = soldeAbsencePaieRepository.findAll();
        assertThat(soldeAbsencePaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSoldeAbsencePaie() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsencePaieRepository.findAll().size();
        soldeAbsencePaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldeAbsencePaieMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(soldeAbsencePaie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SoldeAbsencePaie in the database
        List<SoldeAbsencePaie> soldeAbsencePaieList = soldeAbsencePaieRepository.findAll();
        assertThat(soldeAbsencePaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSoldeAbsencePaieWithPatch() throws Exception {
        // Initialize the database
        soldeAbsencePaieRepository.saveAndFlush(soldeAbsencePaie);

        int databaseSizeBeforeUpdate = soldeAbsencePaieRepository.findAll().size();

        // Update the soldeAbsencePaie using partial update
        SoldeAbsencePaie partialUpdatedSoldeAbsencePaie = new SoldeAbsencePaie();
        partialUpdatedSoldeAbsencePaie.setId(soldeAbsencePaie.getId());

        partialUpdatedSoldeAbsencePaie.annee(UPDATED_ANNEE).mois(UPDATED_MOIS).nbDays(UPDATED_NB_DAYS).dateop(UPDATED_DATEOP);

        restSoldeAbsencePaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoldeAbsencePaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoldeAbsencePaie))
            )
            .andExpect(status().isOk());

        // Validate the SoldeAbsencePaie in the database
        List<SoldeAbsencePaie> soldeAbsencePaieList = soldeAbsencePaieRepository.findAll();
        assertThat(soldeAbsencePaieList).hasSize(databaseSizeBeforeUpdate);
        SoldeAbsencePaie testSoldeAbsencePaie = soldeAbsencePaieList.get(soldeAbsencePaieList.size() - 1);
        assertThat(testSoldeAbsencePaie.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSoldeAbsencePaie.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testSoldeAbsencePaie.getNbDays()).isEqualTo(UPDATED_NB_DAYS);
        assertThat(testSoldeAbsencePaie.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testSoldeAbsencePaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSoldeAbsencePaie.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testSoldeAbsencePaie.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testSoldeAbsencePaie.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testSoldeAbsencePaie.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testSoldeAbsencePaie.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateSoldeAbsencePaieWithPatch() throws Exception {
        // Initialize the database
        soldeAbsencePaieRepository.saveAndFlush(soldeAbsencePaie);

        int databaseSizeBeforeUpdate = soldeAbsencePaieRepository.findAll().size();

        // Update the soldeAbsencePaie using partial update
        SoldeAbsencePaie partialUpdatedSoldeAbsencePaie = new SoldeAbsencePaie();
        partialUpdatedSoldeAbsencePaie.setId(soldeAbsencePaie.getId());

        partialUpdatedSoldeAbsencePaie
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .nbDays(UPDATED_NB_DAYS)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restSoldeAbsencePaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSoldeAbsencePaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSoldeAbsencePaie))
            )
            .andExpect(status().isOk());

        // Validate the SoldeAbsencePaie in the database
        List<SoldeAbsencePaie> soldeAbsencePaieList = soldeAbsencePaieRepository.findAll();
        assertThat(soldeAbsencePaieList).hasSize(databaseSizeBeforeUpdate);
        SoldeAbsencePaie testSoldeAbsencePaie = soldeAbsencePaieList.get(soldeAbsencePaieList.size() - 1);
        assertThat(testSoldeAbsencePaie.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testSoldeAbsencePaie.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testSoldeAbsencePaie.getNbDays()).isEqualTo(UPDATED_NB_DAYS);
        assertThat(testSoldeAbsencePaie.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testSoldeAbsencePaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testSoldeAbsencePaie.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testSoldeAbsencePaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testSoldeAbsencePaie.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testSoldeAbsencePaie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testSoldeAbsencePaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingSoldeAbsencePaie() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsencePaieRepository.findAll().size();
        soldeAbsencePaie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSoldeAbsencePaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, soldeAbsencePaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soldeAbsencePaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsencePaie in the database
        List<SoldeAbsencePaie> soldeAbsencePaieList = soldeAbsencePaieRepository.findAll();
        assertThat(soldeAbsencePaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSoldeAbsencePaie() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsencePaieRepository.findAll().size();
        soldeAbsencePaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldeAbsencePaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soldeAbsencePaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the SoldeAbsencePaie in the database
        List<SoldeAbsencePaie> soldeAbsencePaieList = soldeAbsencePaieRepository.findAll();
        assertThat(soldeAbsencePaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSoldeAbsencePaie() throws Exception {
        int databaseSizeBeforeUpdate = soldeAbsencePaieRepository.findAll().size();
        soldeAbsencePaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSoldeAbsencePaieMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(soldeAbsencePaie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SoldeAbsencePaie in the database
        List<SoldeAbsencePaie> soldeAbsencePaieList = soldeAbsencePaieRepository.findAll();
        assertThat(soldeAbsencePaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSoldeAbsencePaie() throws Exception {
        // Initialize the database
        soldeAbsencePaieRepository.saveAndFlush(soldeAbsencePaie);

        int databaseSizeBeforeDelete = soldeAbsencePaieRepository.findAll().size();

        // Delete the soldeAbsencePaie
        restSoldeAbsencePaieMockMvc
            .perform(delete(ENTITY_API_URL_ID, soldeAbsencePaie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SoldeAbsencePaie> soldeAbsencePaieList = soldeAbsencePaieRepository.findAll();
        assertThat(soldeAbsencePaieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
