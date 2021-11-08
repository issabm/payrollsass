package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Identite;
import com.issa.payroll.repository.IdentiteRepository;
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
 * Integration tests for the {@link IdentiteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class IdentiteResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_ISSUED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ISSUED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PLACE_ISSED = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_ISSED = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_VLD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_VLD = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/identites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IdentiteRepository identiteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIdentiteMockMvc;

    private Identite identite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Identite createEntity(EntityManager em) {
        Identite identite = new Identite()
            .code(DEFAULT_CODE)
            .dateIssued(DEFAULT_DATE_ISSUED)
            .placeIssed(DEFAULT_PLACE_ISSED)
            .dateVld(DEFAULT_DATE_VLD)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return identite;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Identite createUpdatedEntity(EntityManager em) {
        Identite identite = new Identite()
            .code(UPDATED_CODE)
            .dateIssued(UPDATED_DATE_ISSUED)
            .placeIssed(UPDATED_PLACE_ISSED)
            .dateVld(UPDATED_DATE_VLD)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return identite;
    }

    @BeforeEach
    public void initTest() {
        identite = createEntity(em);
    }

    @Test
    @Transactional
    void createIdentite() throws Exception {
        int databaseSizeBeforeCreate = identiteRepository.findAll().size();
        // Create the Identite
        restIdentiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(identite)))
            .andExpect(status().isCreated());

        // Validate the Identite in the database
        List<Identite> identiteList = identiteRepository.findAll();
        assertThat(identiteList).hasSize(databaseSizeBeforeCreate + 1);
        Identite testIdentite = identiteList.get(identiteList.size() - 1);
        assertThat(testIdentite.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testIdentite.getDateIssued()).isEqualTo(DEFAULT_DATE_ISSUED);
        assertThat(testIdentite.getPlaceIssed()).isEqualTo(DEFAULT_PLACE_ISSED);
        assertThat(testIdentite.getDateVld()).isEqualTo(DEFAULT_DATE_VLD);
        assertThat(testIdentite.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testIdentite.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testIdentite.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testIdentite.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testIdentite.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testIdentite.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testIdentite.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createIdentiteWithExistingId() throws Exception {
        // Create the Identite with an existing ID
        identite.setId(1L);

        int databaseSizeBeforeCreate = identiteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIdentiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(identite)))
            .andExpect(status().isBadRequest());

        // Validate the Identite in the database
        List<Identite> identiteList = identiteRepository.findAll();
        assertThat(identiteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllIdentites() throws Exception {
        // Initialize the database
        identiteRepository.saveAndFlush(identite);

        // Get all the identiteList
        restIdentiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(identite.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].dateIssued").value(hasItem(DEFAULT_DATE_ISSUED.toString())))
            .andExpect(jsonPath("$.[*].placeIssed").value(hasItem(DEFAULT_PLACE_ISSED)))
            .andExpect(jsonPath("$.[*].dateVld").value(hasItem(DEFAULT_DATE_VLD.toString())))
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
    void getIdentite() throws Exception {
        // Initialize the database
        identiteRepository.saveAndFlush(identite);

        // Get the identite
        restIdentiteMockMvc
            .perform(get(ENTITY_API_URL_ID, identite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(identite.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.dateIssued").value(DEFAULT_DATE_ISSUED.toString()))
            .andExpect(jsonPath("$.placeIssed").value(DEFAULT_PLACE_ISSED))
            .andExpect(jsonPath("$.dateVld").value(DEFAULT_DATE_VLD.toString()))
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
    void getNonExistingIdentite() throws Exception {
        // Get the identite
        restIdentiteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewIdentite() throws Exception {
        // Initialize the database
        identiteRepository.saveAndFlush(identite);

        int databaseSizeBeforeUpdate = identiteRepository.findAll().size();

        // Update the identite
        Identite updatedIdentite = identiteRepository.findById(identite.getId()).get();
        // Disconnect from session so that the updates on updatedIdentite are not directly saved in db
        em.detach(updatedIdentite);
        updatedIdentite
            .code(UPDATED_CODE)
            .dateIssued(UPDATED_DATE_ISSUED)
            .placeIssed(UPDATED_PLACE_ISSED)
            .dateVld(UPDATED_DATE_VLD)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restIdentiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedIdentite.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedIdentite))
            )
            .andExpect(status().isOk());

        // Validate the Identite in the database
        List<Identite> identiteList = identiteRepository.findAll();
        assertThat(identiteList).hasSize(databaseSizeBeforeUpdate);
        Identite testIdentite = identiteList.get(identiteList.size() - 1);
        assertThat(testIdentite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIdentite.getDateIssued()).isEqualTo(UPDATED_DATE_ISSUED);
        assertThat(testIdentite.getPlaceIssed()).isEqualTo(UPDATED_PLACE_ISSED);
        assertThat(testIdentite.getDateVld()).isEqualTo(UPDATED_DATE_VLD);
        assertThat(testIdentite.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testIdentite.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testIdentite.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testIdentite.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testIdentite.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testIdentite.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testIdentite.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingIdentite() throws Exception {
        int databaseSizeBeforeUpdate = identiteRepository.findAll().size();
        identite.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, identite.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(identite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Identite in the database
        List<Identite> identiteList = identiteRepository.findAll();
        assertThat(identiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIdentite() throws Exception {
        int databaseSizeBeforeUpdate = identiteRepository.findAll().size();
        identite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(identite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Identite in the database
        List<Identite> identiteList = identiteRepository.findAll();
        assertThat(identiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIdentite() throws Exception {
        int databaseSizeBeforeUpdate = identiteRepository.findAll().size();
        identite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentiteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(identite)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Identite in the database
        List<Identite> identiteList = identiteRepository.findAll();
        assertThat(identiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIdentiteWithPatch() throws Exception {
        // Initialize the database
        identiteRepository.saveAndFlush(identite);

        int databaseSizeBeforeUpdate = identiteRepository.findAll().size();

        // Update the identite using partial update
        Identite partialUpdatedIdentite = new Identite();
        partialUpdatedIdentite.setId(identite.getId());

        partialUpdatedIdentite
            .placeIssed(UPDATED_PLACE_ISSED)
            .dateVld(UPDATED_DATE_VLD)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .isDeleted(UPDATED_IS_DELETED);

        restIdentiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdentite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIdentite))
            )
            .andExpect(status().isOk());

        // Validate the Identite in the database
        List<Identite> identiteList = identiteRepository.findAll();
        assertThat(identiteList).hasSize(databaseSizeBeforeUpdate);
        Identite testIdentite = identiteList.get(identiteList.size() - 1);
        assertThat(testIdentite.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testIdentite.getDateIssued()).isEqualTo(DEFAULT_DATE_ISSUED);
        assertThat(testIdentite.getPlaceIssed()).isEqualTo(UPDATED_PLACE_ISSED);
        assertThat(testIdentite.getDateVld()).isEqualTo(UPDATED_DATE_VLD);
        assertThat(testIdentite.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testIdentite.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testIdentite.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testIdentite.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testIdentite.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testIdentite.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testIdentite.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateIdentiteWithPatch() throws Exception {
        // Initialize the database
        identiteRepository.saveAndFlush(identite);

        int databaseSizeBeforeUpdate = identiteRepository.findAll().size();

        // Update the identite using partial update
        Identite partialUpdatedIdentite = new Identite();
        partialUpdatedIdentite.setId(identite.getId());

        partialUpdatedIdentite
            .code(UPDATED_CODE)
            .dateIssued(UPDATED_DATE_ISSUED)
            .placeIssed(UPDATED_PLACE_ISSED)
            .dateVld(UPDATED_DATE_VLD)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restIdentiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIdentite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIdentite))
            )
            .andExpect(status().isOk());

        // Validate the Identite in the database
        List<Identite> identiteList = identiteRepository.findAll();
        assertThat(identiteList).hasSize(databaseSizeBeforeUpdate);
        Identite testIdentite = identiteList.get(identiteList.size() - 1);
        assertThat(testIdentite.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIdentite.getDateIssued()).isEqualTo(UPDATED_DATE_ISSUED);
        assertThat(testIdentite.getPlaceIssed()).isEqualTo(UPDATED_PLACE_ISSED);
        assertThat(testIdentite.getDateVld()).isEqualTo(UPDATED_DATE_VLD);
        assertThat(testIdentite.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testIdentite.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testIdentite.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testIdentite.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testIdentite.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testIdentite.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testIdentite.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingIdentite() throws Exception {
        int databaseSizeBeforeUpdate = identiteRepository.findAll().size();
        identite.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIdentiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, identite.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(identite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Identite in the database
        List<Identite> identiteList = identiteRepository.findAll();
        assertThat(identiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIdentite() throws Exception {
        int databaseSizeBeforeUpdate = identiteRepository.findAll().size();
        identite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(identite))
            )
            .andExpect(status().isBadRequest());

        // Validate the Identite in the database
        List<Identite> identiteList = identiteRepository.findAll();
        assertThat(identiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIdentite() throws Exception {
        int databaseSizeBeforeUpdate = identiteRepository.findAll().size();
        identite.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIdentiteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(identite)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Identite in the database
        List<Identite> identiteList = identiteRepository.findAll();
        assertThat(identiteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIdentite() throws Exception {
        // Initialize the database
        identiteRepository.saveAndFlush(identite);

        int databaseSizeBeforeDelete = identiteRepository.findAll().size();

        // Delete the identite
        restIdentiteMockMvc
            .perform(delete(ENTITY_API_URL_ID, identite.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Identite> identiteList = identiteRepository.findAll();
        assertThat(identiteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
