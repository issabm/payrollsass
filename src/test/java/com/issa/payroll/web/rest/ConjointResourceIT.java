package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Conjoint;
import com.issa.payroll.repository.ConjointRepository;
import java.time.Instant;
import java.time.LocalDate;
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
 * Integration tests for the {@link ConjointResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ConjointResourceIT {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_AR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_AR = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_AR = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_EN = "AAAAAAAAAA";
    private static final String UPDATED_NOM_EN = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_EN = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_EN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISS = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DOES_WORK = false;
    private static final Boolean UPDATED_DOES_WORK = true;

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

    private static final String ENTITY_API_URL = "/api/conjoints";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConjointRepository conjointRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConjointMockMvc;

    private Conjoint conjoint;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conjoint createEntity(EntityManager em) {
        Conjoint conjoint = new Conjoint()
            .matricule(DEFAULT_MATRICULE)
            .nomAr(DEFAULT_NOM_AR)
            .prenomAr(DEFAULT_PRENOM_AR)
            .nomEn(DEFAULT_NOM_EN)
            .prenomEn(DEFAULT_PRENOM_EN)
            .dateNaiss(DEFAULT_DATE_NAISS)
            .doesWork(DEFAULT_DOES_WORK)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED);
        return conjoint;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conjoint createUpdatedEntity(EntityManager em) {
        Conjoint conjoint = new Conjoint()
            .matricule(UPDATED_MATRICULE)
            .nomAr(UPDATED_NOM_AR)
            .prenomAr(UPDATED_PRENOM_AR)
            .nomEn(UPDATED_NOM_EN)
            .prenomEn(UPDATED_PRENOM_EN)
            .dateNaiss(UPDATED_DATE_NAISS)
            .doesWork(UPDATED_DOES_WORK)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);
        return conjoint;
    }

    @BeforeEach
    public void initTest() {
        conjoint = createEntity(em);
    }

    @Test
    @Transactional
    void createConjoint() throws Exception {
        int databaseSizeBeforeCreate = conjointRepository.findAll().size();
        // Create the Conjoint
        restConjointMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conjoint)))
            .andExpect(status().isCreated());

        // Validate the Conjoint in the database
        List<Conjoint> conjointList = conjointRepository.findAll();
        assertThat(conjointList).hasSize(databaseSizeBeforeCreate + 1);
        Conjoint testConjoint = conjointList.get(conjointList.size() - 1);
        assertThat(testConjoint.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testConjoint.getNomAr()).isEqualTo(DEFAULT_NOM_AR);
        assertThat(testConjoint.getPrenomAr()).isEqualTo(DEFAULT_PRENOM_AR);
        assertThat(testConjoint.getNomEn()).isEqualTo(DEFAULT_NOM_EN);
        assertThat(testConjoint.getPrenomEn()).isEqualTo(DEFAULT_PRENOM_EN);
        assertThat(testConjoint.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testConjoint.getDoesWork()).isEqualTo(DEFAULT_DOES_WORK);
        assertThat(testConjoint.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testConjoint.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testConjoint.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testConjoint.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testConjoint.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createConjointWithExistingId() throws Exception {
        // Create the Conjoint with an existing ID
        conjoint.setId(1L);

        int databaseSizeBeforeCreate = conjointRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConjointMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conjoint)))
            .andExpect(status().isBadRequest());

        // Validate the Conjoint in the database
        List<Conjoint> conjointList = conjointRepository.findAll();
        assertThat(conjointList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllConjoints() throws Exception {
        // Initialize the database
        conjointRepository.saveAndFlush(conjoint);

        // Get all the conjointList
        restConjointMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conjoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].nomAr").value(hasItem(DEFAULT_NOM_AR)))
            .andExpect(jsonPath("$.[*].prenomAr").value(hasItem(DEFAULT_PRENOM_AR)))
            .andExpect(jsonPath("$.[*].nomEn").value(hasItem(DEFAULT_NOM_EN)))
            .andExpect(jsonPath("$.[*].prenomEn").value(hasItem(DEFAULT_PRENOM_EN)))
            .andExpect(jsonPath("$.[*].dateNaiss").value(hasItem(DEFAULT_DATE_NAISS.toString())))
            .andExpect(jsonPath("$.[*].doesWork").value(hasItem(DEFAULT_DOES_WORK.booleanValue())))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getConjoint() throws Exception {
        // Initialize the database
        conjointRepository.saveAndFlush(conjoint);

        // Get the conjoint
        restConjointMockMvc
            .perform(get(ENTITY_API_URL_ID, conjoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conjoint.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.nomAr").value(DEFAULT_NOM_AR))
            .andExpect(jsonPath("$.prenomAr").value(DEFAULT_PRENOM_AR))
            .andExpect(jsonPath("$.nomEn").value(DEFAULT_NOM_EN))
            .andExpect(jsonPath("$.prenomEn").value(DEFAULT_PRENOM_EN))
            .andExpect(jsonPath("$.dateNaiss").value(DEFAULT_DATE_NAISS.toString()))
            .andExpect(jsonPath("$.doesWork").value(DEFAULT_DOES_WORK.booleanValue()))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingConjoint() throws Exception {
        // Get the conjoint
        restConjointMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConjoint() throws Exception {
        // Initialize the database
        conjointRepository.saveAndFlush(conjoint);

        int databaseSizeBeforeUpdate = conjointRepository.findAll().size();

        // Update the conjoint
        Conjoint updatedConjoint = conjointRepository.findById(conjoint.getId()).get();
        // Disconnect from session so that the updates on updatedConjoint are not directly saved in db
        em.detach(updatedConjoint);
        updatedConjoint
            .matricule(UPDATED_MATRICULE)
            .nomAr(UPDATED_NOM_AR)
            .prenomAr(UPDATED_PRENOM_AR)
            .nomEn(UPDATED_NOM_EN)
            .prenomEn(UPDATED_PRENOM_EN)
            .dateNaiss(UPDATED_DATE_NAISS)
            .doesWork(UPDATED_DOES_WORK)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restConjointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedConjoint.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedConjoint))
            )
            .andExpect(status().isOk());

        // Validate the Conjoint in the database
        List<Conjoint> conjointList = conjointRepository.findAll();
        assertThat(conjointList).hasSize(databaseSizeBeforeUpdate);
        Conjoint testConjoint = conjointList.get(conjointList.size() - 1);
        assertThat(testConjoint.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testConjoint.getNomAr()).isEqualTo(UPDATED_NOM_AR);
        assertThat(testConjoint.getPrenomAr()).isEqualTo(UPDATED_PRENOM_AR);
        assertThat(testConjoint.getNomEn()).isEqualTo(UPDATED_NOM_EN);
        assertThat(testConjoint.getPrenomEn()).isEqualTo(UPDATED_PRENOM_EN);
        assertThat(testConjoint.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testConjoint.getDoesWork()).isEqualTo(UPDATED_DOES_WORK);
        assertThat(testConjoint.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testConjoint.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testConjoint.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testConjoint.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testConjoint.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingConjoint() throws Exception {
        int databaseSizeBeforeUpdate = conjointRepository.findAll().size();
        conjoint.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConjointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conjoint.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conjoint))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conjoint in the database
        List<Conjoint> conjointList = conjointRepository.findAll();
        assertThat(conjointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConjoint() throws Exception {
        int databaseSizeBeforeUpdate = conjointRepository.findAll().size();
        conjoint.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConjointMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conjoint))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conjoint in the database
        List<Conjoint> conjointList = conjointRepository.findAll();
        assertThat(conjointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConjoint() throws Exception {
        int databaseSizeBeforeUpdate = conjointRepository.findAll().size();
        conjoint.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConjointMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conjoint)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conjoint in the database
        List<Conjoint> conjointList = conjointRepository.findAll();
        assertThat(conjointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConjointWithPatch() throws Exception {
        // Initialize the database
        conjointRepository.saveAndFlush(conjoint);

        int databaseSizeBeforeUpdate = conjointRepository.findAll().size();

        // Update the conjoint using partial update
        Conjoint partialUpdatedConjoint = new Conjoint();
        partialUpdatedConjoint.setId(conjoint.getId());

        partialUpdatedConjoint.util(UPDATED_UTIL).modifiedBy(UPDATED_MODIFIED_BY).op(UPDATED_OP).isDeleted(UPDATED_IS_DELETED);

        restConjointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConjoint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConjoint))
            )
            .andExpect(status().isOk());

        // Validate the Conjoint in the database
        List<Conjoint> conjointList = conjointRepository.findAll();
        assertThat(conjointList).hasSize(databaseSizeBeforeUpdate);
        Conjoint testConjoint = conjointList.get(conjointList.size() - 1);
        assertThat(testConjoint.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testConjoint.getNomAr()).isEqualTo(DEFAULT_NOM_AR);
        assertThat(testConjoint.getPrenomAr()).isEqualTo(DEFAULT_PRENOM_AR);
        assertThat(testConjoint.getNomEn()).isEqualTo(DEFAULT_NOM_EN);
        assertThat(testConjoint.getPrenomEn()).isEqualTo(DEFAULT_PRENOM_EN);
        assertThat(testConjoint.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testConjoint.getDoesWork()).isEqualTo(DEFAULT_DOES_WORK);
        assertThat(testConjoint.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testConjoint.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testConjoint.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testConjoint.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testConjoint.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateConjointWithPatch() throws Exception {
        // Initialize the database
        conjointRepository.saveAndFlush(conjoint);

        int databaseSizeBeforeUpdate = conjointRepository.findAll().size();

        // Update the conjoint using partial update
        Conjoint partialUpdatedConjoint = new Conjoint();
        partialUpdatedConjoint.setId(conjoint.getId());

        partialUpdatedConjoint
            .matricule(UPDATED_MATRICULE)
            .nomAr(UPDATED_NOM_AR)
            .prenomAr(UPDATED_PRENOM_AR)
            .nomEn(UPDATED_NOM_EN)
            .prenomEn(UPDATED_PRENOM_EN)
            .dateNaiss(UPDATED_DATE_NAISS)
            .doesWork(UPDATED_DOES_WORK)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restConjointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConjoint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConjoint))
            )
            .andExpect(status().isOk());

        // Validate the Conjoint in the database
        List<Conjoint> conjointList = conjointRepository.findAll();
        assertThat(conjointList).hasSize(databaseSizeBeforeUpdate);
        Conjoint testConjoint = conjointList.get(conjointList.size() - 1);
        assertThat(testConjoint.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testConjoint.getNomAr()).isEqualTo(UPDATED_NOM_AR);
        assertThat(testConjoint.getPrenomAr()).isEqualTo(UPDATED_PRENOM_AR);
        assertThat(testConjoint.getNomEn()).isEqualTo(UPDATED_NOM_EN);
        assertThat(testConjoint.getPrenomEn()).isEqualTo(UPDATED_PRENOM_EN);
        assertThat(testConjoint.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testConjoint.getDoesWork()).isEqualTo(UPDATED_DOES_WORK);
        assertThat(testConjoint.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testConjoint.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testConjoint.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testConjoint.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testConjoint.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingConjoint() throws Exception {
        int databaseSizeBeforeUpdate = conjointRepository.findAll().size();
        conjoint.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConjointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, conjoint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conjoint))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conjoint in the database
        List<Conjoint> conjointList = conjointRepository.findAll();
        assertThat(conjointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConjoint() throws Exception {
        int databaseSizeBeforeUpdate = conjointRepository.findAll().size();
        conjoint.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConjointMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conjoint))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conjoint in the database
        List<Conjoint> conjointList = conjointRepository.findAll();
        assertThat(conjointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConjoint() throws Exception {
        int databaseSizeBeforeUpdate = conjointRepository.findAll().size();
        conjoint.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConjointMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(conjoint)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conjoint in the database
        List<Conjoint> conjointList = conjointRepository.findAll();
        assertThat(conjointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConjoint() throws Exception {
        // Initialize the database
        conjointRepository.saveAndFlush(conjoint);

        int databaseSizeBeforeDelete = conjointRepository.findAll().size();

        // Delete the conjoint
        restConjointMockMvc
            .perform(delete(ENTITY_API_URL_ID, conjoint.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conjoint> conjointList = conjointRepository.findAll();
        assertThat(conjointList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
