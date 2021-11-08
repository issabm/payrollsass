package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Affiliation;
import com.issa.payroll.repository.AffiliationRepository;
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
 * Integration tests for the {@link AffiliationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AffiliationResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/affiliations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AffiliationRepository affiliationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAffiliationMockMvc;

    private Affiliation affiliation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Affiliation createEntity(EntityManager em) {
        Affiliation affiliation = new Affiliation()
            .code(DEFAULT_CODE)
            .libAr(DEFAULT_LIB_AR)
            .libEn(DEFAULT_LIB_EN)
            .tel(DEFAULT_TEL)
            .email(DEFAULT_EMAIL)
            .fax(DEFAULT_FAX)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED);
        return affiliation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Affiliation createUpdatedEntity(EntityManager em) {
        Affiliation affiliation = new Affiliation()
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .tel(UPDATED_TEL)
            .email(UPDATED_EMAIL)
            .fax(UPDATED_FAX)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);
        return affiliation;
    }

    @BeforeEach
    public void initTest() {
        affiliation = createEntity(em);
    }

    @Test
    @Transactional
    void createAffiliation() throws Exception {
        int databaseSizeBeforeCreate = affiliationRepository.findAll().size();
        // Create the Affiliation
        restAffiliationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliation)))
            .andExpect(status().isCreated());

        // Validate the Affiliation in the database
        List<Affiliation> affiliationList = affiliationRepository.findAll();
        assertThat(affiliationList).hasSize(databaseSizeBeforeCreate + 1);
        Affiliation testAffiliation = affiliationList.get(affiliationList.size() - 1);
        assertThat(testAffiliation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAffiliation.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testAffiliation.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testAffiliation.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testAffiliation.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAffiliation.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testAffiliation.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testAffiliation.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testAffiliation.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testAffiliation.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testAffiliation.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createAffiliationWithExistingId() throws Exception {
        // Create the Affiliation with an existing ID
        affiliation.setId(1L);

        int databaseSizeBeforeCreate = affiliationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAffiliationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliation)))
            .andExpect(status().isBadRequest());

        // Validate the Affiliation in the database
        List<Affiliation> affiliationList = affiliationRepository.findAll();
        assertThat(affiliationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAffiliations() throws Exception {
        // Initialize the database
        affiliationRepository.saveAndFlush(affiliation);

        // Get all the affiliationList
        restAffiliationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(affiliation.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getAffiliation() throws Exception {
        // Initialize the database
        affiliationRepository.saveAndFlush(affiliation);

        // Get the affiliation
        restAffiliationMockMvc
            .perform(get(ENTITY_API_URL_ID, affiliation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(affiliation.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAffiliation() throws Exception {
        // Get the affiliation
        restAffiliationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAffiliation() throws Exception {
        // Initialize the database
        affiliationRepository.saveAndFlush(affiliation);

        int databaseSizeBeforeUpdate = affiliationRepository.findAll().size();

        // Update the affiliation
        Affiliation updatedAffiliation = affiliationRepository.findById(affiliation.getId()).get();
        // Disconnect from session so that the updates on updatedAffiliation are not directly saved in db
        em.detach(updatedAffiliation);
        updatedAffiliation
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .tel(UPDATED_TEL)
            .email(UPDATED_EMAIL)
            .fax(UPDATED_FAX)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restAffiliationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAffiliation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAffiliation))
            )
            .andExpect(status().isOk());

        // Validate the Affiliation in the database
        List<Affiliation> affiliationList = affiliationRepository.findAll();
        assertThat(affiliationList).hasSize(databaseSizeBeforeUpdate);
        Affiliation testAffiliation = affiliationList.get(affiliationList.size() - 1);
        assertThat(testAffiliation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAffiliation.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testAffiliation.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testAffiliation.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testAffiliation.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAffiliation.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testAffiliation.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testAffiliation.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testAffiliation.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAffiliation.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testAffiliation.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingAffiliation() throws Exception {
        int databaseSizeBeforeUpdate = affiliationRepository.findAll().size();
        affiliation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffiliationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, affiliation.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(affiliation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affiliation in the database
        List<Affiliation> affiliationList = affiliationRepository.findAll();
        assertThat(affiliationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAffiliation() throws Exception {
        int databaseSizeBeforeUpdate = affiliationRepository.findAll().size();
        affiliation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(affiliation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affiliation in the database
        List<Affiliation> affiliationList = affiliationRepository.findAll();
        assertThat(affiliationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAffiliation() throws Exception {
        int databaseSizeBeforeUpdate = affiliationRepository.findAll().size();
        affiliation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(affiliation)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Affiliation in the database
        List<Affiliation> affiliationList = affiliationRepository.findAll();
        assertThat(affiliationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAffiliationWithPatch() throws Exception {
        // Initialize the database
        affiliationRepository.saveAndFlush(affiliation);

        int databaseSizeBeforeUpdate = affiliationRepository.findAll().size();

        // Update the affiliation using partial update
        Affiliation partialUpdatedAffiliation = new Affiliation();
        partialUpdatedAffiliation.setId(affiliation.getId());

        partialUpdatedAffiliation.fax(UPDATED_FAX).op(UPDATED_OP);

        restAffiliationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAffiliation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAffiliation))
            )
            .andExpect(status().isOk());

        // Validate the Affiliation in the database
        List<Affiliation> affiliationList = affiliationRepository.findAll();
        assertThat(affiliationList).hasSize(databaseSizeBeforeUpdate);
        Affiliation testAffiliation = affiliationList.get(affiliationList.size() - 1);
        assertThat(testAffiliation.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAffiliation.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testAffiliation.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testAffiliation.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testAffiliation.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testAffiliation.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testAffiliation.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testAffiliation.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testAffiliation.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testAffiliation.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testAffiliation.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateAffiliationWithPatch() throws Exception {
        // Initialize the database
        affiliationRepository.saveAndFlush(affiliation);

        int databaseSizeBeforeUpdate = affiliationRepository.findAll().size();

        // Update the affiliation using partial update
        Affiliation partialUpdatedAffiliation = new Affiliation();
        partialUpdatedAffiliation.setId(affiliation.getId());

        partialUpdatedAffiliation
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .tel(UPDATED_TEL)
            .email(UPDATED_EMAIL)
            .fax(UPDATED_FAX)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restAffiliationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAffiliation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAffiliation))
            )
            .andExpect(status().isOk());

        // Validate the Affiliation in the database
        List<Affiliation> affiliationList = affiliationRepository.findAll();
        assertThat(affiliationList).hasSize(databaseSizeBeforeUpdate);
        Affiliation testAffiliation = affiliationList.get(affiliationList.size() - 1);
        assertThat(testAffiliation.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAffiliation.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testAffiliation.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testAffiliation.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testAffiliation.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testAffiliation.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testAffiliation.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testAffiliation.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testAffiliation.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testAffiliation.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testAffiliation.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingAffiliation() throws Exception {
        int databaseSizeBeforeUpdate = affiliationRepository.findAll().size();
        affiliation.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAffiliationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, affiliation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(affiliation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affiliation in the database
        List<Affiliation> affiliationList = affiliationRepository.findAll();
        assertThat(affiliationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAffiliation() throws Exception {
        int databaseSizeBeforeUpdate = affiliationRepository.findAll().size();
        affiliation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(affiliation))
            )
            .andExpect(status().isBadRequest());

        // Validate the Affiliation in the database
        List<Affiliation> affiliationList = affiliationRepository.findAll();
        assertThat(affiliationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAffiliation() throws Exception {
        int databaseSizeBeforeUpdate = affiliationRepository.findAll().size();
        affiliation.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAffiliationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(affiliation))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Affiliation in the database
        List<Affiliation> affiliationList = affiliationRepository.findAll();
        assertThat(affiliationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAffiliation() throws Exception {
        // Initialize the database
        affiliationRepository.saveAndFlush(affiliation);

        int databaseSizeBeforeDelete = affiliationRepository.findAll().size();

        // Delete the affiliation
        restAffiliationMockMvc
            .perform(delete(ENTITY_API_URL_ID, affiliation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Affiliation> affiliationList = affiliationRepository.findAll();
        assertThat(affiliationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
