package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Famille;
import com.issa.payroll.repository.FamilleRepository;
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
 * Integration tests for the {@link FamilleResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FamilleResourceIT {

    private static final LocalDate DEFAULT_DATE_SITUATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SITUATION = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_DATEOP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/familles";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FamilleRepository familleRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFamilleMockMvc;

    private Famille famille;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Famille createEntity(EntityManager em) {
        Famille famille = new Famille()
            .dateSituation(DEFAULT_DATE_SITUATION)
            .dateop(DEFAULT_DATEOP)
            .util(DEFAULT_UTIL)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return famille;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Famille createUpdatedEntity(EntityManager em) {
        Famille famille = new Famille()
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return famille;
    }

    @BeforeEach
    public void initTest() {
        famille = createEntity(em);
    }

    @Test
    @Transactional
    void createFamille() throws Exception {
        int databaseSizeBeforeCreate = familleRepository.findAll().size();
        // Create the Famille
        restFamilleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(famille)))
            .andExpect(status().isCreated());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeCreate + 1);
        Famille testFamille = familleList.get(familleList.size() - 1);
        assertThat(testFamille.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testFamille.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testFamille.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testFamille.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testFamille.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testFamille.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testFamille.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFamille.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createFamilleWithExistingId() throws Exception {
        // Create the Famille with an existing ID
        famille.setId(1L);

        int databaseSizeBeforeCreate = familleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFamilleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(famille)))
            .andExpect(status().isBadRequest());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFamilles() throws Exception {
        // Initialize the database
        familleRepository.saveAndFlush(famille);

        // Get all the familleList
        restFamilleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(famille.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateSituation").value(hasItem(DEFAULT_DATE_SITUATION.toString())))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    void getFamille() throws Exception {
        // Initialize the database
        familleRepository.saveAndFlush(famille);

        // Get the famille
        restFamilleMockMvc
            .perform(get(ENTITY_API_URL_ID, famille.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(famille.getId().intValue()))
            .andExpect(jsonPath("$.dateSituation").value(DEFAULT_DATE_SITUATION.toString()))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingFamille() throws Exception {
        // Get the famille
        restFamilleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFamille() throws Exception {
        // Initialize the database
        familleRepository.saveAndFlush(famille);

        int databaseSizeBeforeUpdate = familleRepository.findAll().size();

        // Update the famille
        Famille updatedFamille = familleRepository.findById(famille.getId()).get();
        // Disconnect from session so that the updates on updatedFamille are not directly saved in db
        em.detach(updatedFamille);
        updatedFamille
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restFamilleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFamille.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFamille))
            )
            .andExpect(status().isOk());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeUpdate);
        Famille testFamille = familleList.get(familleList.size() - 1);
        assertThat(testFamille.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testFamille.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testFamille.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testFamille.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFamille.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testFamille.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testFamille.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFamille.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingFamille() throws Exception {
        int databaseSizeBeforeUpdate = familleRepository.findAll().size();
        famille.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, famille.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(famille))
            )
            .andExpect(status().isBadRequest());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFamille() throws Exception {
        int databaseSizeBeforeUpdate = familleRepository.findAll().size();
        famille.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(famille))
            )
            .andExpect(status().isBadRequest());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFamille() throws Exception {
        int databaseSizeBeforeUpdate = familleRepository.findAll().size();
        famille.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(famille)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFamilleWithPatch() throws Exception {
        // Initialize the database
        familleRepository.saveAndFlush(famille);

        int databaseSizeBeforeUpdate = familleRepository.findAll().size();

        // Update the famille using partial update
        Famille partialUpdatedFamille = new Famille();
        partialUpdatedFamille.setId(famille.getId());

        partialUpdatedFamille
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restFamilleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFamille.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFamille))
            )
            .andExpect(status().isOk());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeUpdate);
        Famille testFamille = familleList.get(familleList.size() - 1);
        assertThat(testFamille.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testFamille.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testFamille.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testFamille.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFamille.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testFamille.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testFamille.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFamille.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateFamilleWithPatch() throws Exception {
        // Initialize the database
        familleRepository.saveAndFlush(famille);

        int databaseSizeBeforeUpdate = familleRepository.findAll().size();

        // Update the famille using partial update
        Famille partialUpdatedFamille = new Famille();
        partialUpdatedFamille.setId(famille.getId());

        partialUpdatedFamille
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restFamilleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFamille.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFamille))
            )
            .andExpect(status().isOk());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeUpdate);
        Famille testFamille = familleList.get(familleList.size() - 1);
        assertThat(testFamille.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testFamille.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testFamille.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testFamille.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testFamille.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testFamille.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testFamille.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFamille.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingFamille() throws Exception {
        int databaseSizeBeforeUpdate = familleRepository.findAll().size();
        famille.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFamilleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, famille.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(famille))
            )
            .andExpect(status().isBadRequest());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFamille() throws Exception {
        int databaseSizeBeforeUpdate = familleRepository.findAll().size();
        famille.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(famille))
            )
            .andExpect(status().isBadRequest());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFamille() throws Exception {
        int databaseSizeBeforeUpdate = familleRepository.findAll().size();
        famille.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFamilleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(famille)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Famille in the database
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFamille() throws Exception {
        // Initialize the database
        familleRepository.saveAndFlush(famille);

        int databaseSizeBeforeDelete = familleRepository.findAll().size();

        // Delete the famille
        restFamilleMockMvc
            .perform(delete(ENTITY_API_URL_ID, famille.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Famille> familleList = familleRepository.findAll();
        assertThat(familleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
