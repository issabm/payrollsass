package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Carriere;
import com.issa.payroll.repository.CarriereRepository;
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
 * Integration tests for the {@link CarriereResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CarriereResourceIT {

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_FIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_FIN = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_EMPLOI = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EMPLOI = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_ECHLON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ECHLON = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_CATEGORIE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CATEGORIE = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/carrieres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CarriereRepository carriereRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarriereMockMvc;

    private Carriere carriere;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carriere createEntity(EntityManager em) {
        Carriere carriere = new Carriere()
            .dateDebut(DEFAULT_DATE_DEBUT)
            .dateFin(DEFAULT_DATE_FIN)
            .dateEmploi(DEFAULT_DATE_EMPLOI)
            .dateEchlon(DEFAULT_DATE_ECHLON)
            .dateCategorie(DEFAULT_DATE_CATEGORIE)
            .dateop(DEFAULT_DATEOP)
            .util(DEFAULT_UTIL)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return carriere;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Carriere createUpdatedEntity(EntityManager em) {
        Carriere carriere = new Carriere()
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .dateEmploi(UPDATED_DATE_EMPLOI)
            .dateEchlon(UPDATED_DATE_ECHLON)
            .dateCategorie(UPDATED_DATE_CATEGORIE)
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return carriere;
    }

    @BeforeEach
    public void initTest() {
        carriere = createEntity(em);
    }

    @Test
    @Transactional
    void createCarriere() throws Exception {
        int databaseSizeBeforeCreate = carriereRepository.findAll().size();
        // Create the Carriere
        restCarriereMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carriere)))
            .andExpect(status().isCreated());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeCreate + 1);
        Carriere testCarriere = carriereList.get(carriereList.size() - 1);
        assertThat(testCarriere.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testCarriere.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testCarriere.getDateEmploi()).isEqualTo(DEFAULT_DATE_EMPLOI);
        assertThat(testCarriere.getDateEchlon()).isEqualTo(DEFAULT_DATE_ECHLON);
        assertThat(testCarriere.getDateCategorie()).isEqualTo(DEFAULT_DATE_CATEGORIE);
        assertThat(testCarriere.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testCarriere.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testCarriere.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testCarriere.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testCarriere.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testCarriere.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCarriere.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createCarriereWithExistingId() throws Exception {
        // Create the Carriere with an existing ID
        carriere.setId(1L);

        int databaseSizeBeforeCreate = carriereRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarriereMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carriere)))
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCarrieres() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        // Get all the carriereList
        restCarriereMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carriere.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].dateFin").value(hasItem(DEFAULT_DATE_FIN.toString())))
            .andExpect(jsonPath("$.[*].dateEmploi").value(hasItem(DEFAULT_DATE_EMPLOI.toString())))
            .andExpect(jsonPath("$.[*].dateEchlon").value(hasItem(DEFAULT_DATE_ECHLON.toString())))
            .andExpect(jsonPath("$.[*].dateCategorie").value(hasItem(DEFAULT_DATE_CATEGORIE.toString())))
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
    void getCarriere() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        // Get the carriere
        restCarriereMockMvc
            .perform(get(ENTITY_API_URL_ID, carriere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carriere.getId().intValue()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.dateFin").value(DEFAULT_DATE_FIN.toString()))
            .andExpect(jsonPath("$.dateEmploi").value(DEFAULT_DATE_EMPLOI.toString()))
            .andExpect(jsonPath("$.dateEchlon").value(DEFAULT_DATE_ECHLON.toString()))
            .andExpect(jsonPath("$.dateCategorie").value(DEFAULT_DATE_CATEGORIE.toString()))
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
    void getNonExistingCarriere() throws Exception {
        // Get the carriere
        restCarriereMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCarriere() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();

        // Update the carriere
        Carriere updatedCarriere = carriereRepository.findById(carriere.getId()).get();
        // Disconnect from session so that the updates on updatedCarriere are not directly saved in db
        em.detach(updatedCarriere);
        updatedCarriere
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .dateEmploi(UPDATED_DATE_EMPLOI)
            .dateEchlon(UPDATED_DATE_ECHLON)
            .dateCategorie(UPDATED_DATE_CATEGORIE)
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restCarriereMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCarriere.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCarriere))
            )
            .andExpect(status().isOk());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
        Carriere testCarriere = carriereList.get(carriereList.size() - 1);
        assertThat(testCarriere.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testCarriere.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testCarriere.getDateEmploi()).isEqualTo(UPDATED_DATE_EMPLOI);
        assertThat(testCarriere.getDateEchlon()).isEqualTo(UPDATED_DATE_ECHLON);
        assertThat(testCarriere.getDateCategorie()).isEqualTo(UPDATED_DATE_CATEGORIE);
        assertThat(testCarriere.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testCarriere.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testCarriere.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testCarriere.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testCarriere.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testCarriere.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCarriere.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carriere.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carriere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carriere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carriere)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarriereWithPatch() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();

        // Update the carriere using partial update
        Carriere partialUpdatedCarriere = new Carriere();
        partialUpdatedCarriere.setId(carriere.getId());

        partialUpdatedCarriere
            .dateEchlon(UPDATED_DATE_ECHLON)
            .dateCategorie(UPDATED_DATE_CATEGORIE)
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED);

        restCarriereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarriere.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarriere))
            )
            .andExpect(status().isOk());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
        Carriere testCarriere = carriereList.get(carriereList.size() - 1);
        assertThat(testCarriere.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testCarriere.getDateFin()).isEqualTo(DEFAULT_DATE_FIN);
        assertThat(testCarriere.getDateEmploi()).isEqualTo(DEFAULT_DATE_EMPLOI);
        assertThat(testCarriere.getDateEchlon()).isEqualTo(UPDATED_DATE_ECHLON);
        assertThat(testCarriere.getDateCategorie()).isEqualTo(UPDATED_DATE_CATEGORIE);
        assertThat(testCarriere.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testCarriere.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testCarriere.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testCarriere.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testCarriere.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testCarriere.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCarriere.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateCarriereWithPatch() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();

        // Update the carriere using partial update
        Carriere partialUpdatedCarriere = new Carriere();
        partialUpdatedCarriere.setId(carriere.getId());

        partialUpdatedCarriere
            .dateDebut(UPDATED_DATE_DEBUT)
            .dateFin(UPDATED_DATE_FIN)
            .dateEmploi(UPDATED_DATE_EMPLOI)
            .dateEchlon(UPDATED_DATE_ECHLON)
            .dateCategorie(UPDATED_DATE_CATEGORIE)
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restCarriereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarriere.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarriere))
            )
            .andExpect(status().isOk());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
        Carriere testCarriere = carriereList.get(carriereList.size() - 1);
        assertThat(testCarriere.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testCarriere.getDateFin()).isEqualTo(UPDATED_DATE_FIN);
        assertThat(testCarriere.getDateEmploi()).isEqualTo(UPDATED_DATE_EMPLOI);
        assertThat(testCarriere.getDateEchlon()).isEqualTo(UPDATED_DATE_ECHLON);
        assertThat(testCarriere.getDateCategorie()).isEqualTo(UPDATED_DATE_CATEGORIE);
        assertThat(testCarriere.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testCarriere.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testCarriere.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testCarriere.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testCarriere.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testCarriere.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCarriere.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carriere.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carriere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carriere))
            )
            .andExpect(status().isBadRequest());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarriere() throws Exception {
        int databaseSizeBeforeUpdate = carriereRepository.findAll().size();
        carriere.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarriereMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(carriere)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Carriere in the database
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarriere() throws Exception {
        // Initialize the database
        carriereRepository.saveAndFlush(carriere);

        int databaseSizeBeforeDelete = carriereRepository.findAll().size();

        // Delete the carriere
        restCarriereMockMvc
            .perform(delete(ENTITY_API_URL_ID, carriere.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Carriere> carriereList = carriereRepository.findAll();
        assertThat(carriereList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
