package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.PalierPlate;
import com.issa.payroll.repository.PalierPlateRepository;
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
 * Integration tests for the {@link PalierPlateResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PalierPlateResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final Double DEFAULT_EFFECTI_VALUE = 1D;
    private static final Double UPDATED_EFFECTI_VALUE = 2D;

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATEOP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATE_MODIF = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_MODIF = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_OP = "AAAAAAAAAA";
    private static final String UPDATED_OP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final String ENTITY_API_URL = "/api/palier-plates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PalierPlateRepository palierPlateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPalierPlateMockMvc;

    private PalierPlate palierPlate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PalierPlate createEntity(EntityManager em) {
        PalierPlate palierPlate = new PalierPlate()
            .code(DEFAULT_CODE)
            .libEn(DEFAULT_LIB_EN)
            .libAr(DEFAULT_LIB_AR)
            .annee(DEFAULT_ANNEE)
            .effectiValue(DEFAULT_EFFECTI_VALUE)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .dateModif(DEFAULT_DATE_MODIF)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED);
        return palierPlate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PalierPlate createUpdatedEntity(EntityManager em) {
        PalierPlate palierPlate = new PalierPlate()
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .annee(UPDATED_ANNEE)
            .effectiValue(UPDATED_EFFECTI_VALUE)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .dateModif(UPDATED_DATE_MODIF)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);
        return palierPlate;
    }

    @BeforeEach
    public void initTest() {
        palierPlate = createEntity(em);
    }

    @Test
    @Transactional
    void createPalierPlate() throws Exception {
        int databaseSizeBeforeCreate = palierPlateRepository.findAll().size();
        // Create the PalierPlate
        restPalierPlateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(palierPlate)))
            .andExpect(status().isCreated());

        // Validate the PalierPlate in the database
        List<PalierPlate> palierPlateList = palierPlateRepository.findAll();
        assertThat(palierPlateList).hasSize(databaseSizeBeforeCreate + 1);
        PalierPlate testPalierPlate = palierPlateList.get(palierPlateList.size() - 1);
        assertThat(testPalierPlate.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPalierPlate.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testPalierPlate.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testPalierPlate.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testPalierPlate.getEffectiValue()).isEqualTo(DEFAULT_EFFECTI_VALUE);
        assertThat(testPalierPlate.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testPalierPlate.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testPalierPlate.getDateModif()).isEqualTo(DEFAULT_DATE_MODIF);
        assertThat(testPalierPlate.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPalierPlate.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testPalierPlate.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createPalierPlateWithExistingId() throws Exception {
        // Create the PalierPlate with an existing ID
        palierPlate.setId(1L);

        int databaseSizeBeforeCreate = palierPlateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPalierPlateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(palierPlate)))
            .andExpect(status().isBadRequest());

        // Validate the PalierPlate in the database
        List<PalierPlate> palierPlateList = palierPlateRepository.findAll();
        assertThat(palierPlateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPalierPlates() throws Exception {
        // Initialize the database
        palierPlateRepository.saveAndFlush(palierPlate);

        // Get all the palierPlateList
        restPalierPlateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(palierPlate.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].effectiValue").value(hasItem(DEFAULT_EFFECTI_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].dateModif").value(hasItem(sameInstant(DEFAULT_DATE_MODIF))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getPalierPlate() throws Exception {
        // Initialize the database
        palierPlateRepository.saveAndFlush(palierPlate);

        // Get the palierPlate
        restPalierPlateMockMvc
            .perform(get(ENTITY_API_URL_ID, palierPlate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(palierPlate.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.effectiValue").value(DEFAULT_EFFECTI_VALUE.doubleValue()))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.dateModif").value(sameInstant(DEFAULT_DATE_MODIF)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPalierPlate() throws Exception {
        // Get the palierPlate
        restPalierPlateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPalierPlate() throws Exception {
        // Initialize the database
        palierPlateRepository.saveAndFlush(palierPlate);

        int databaseSizeBeforeUpdate = palierPlateRepository.findAll().size();

        // Update the palierPlate
        PalierPlate updatedPalierPlate = palierPlateRepository.findById(palierPlate.getId()).get();
        // Disconnect from session so that the updates on updatedPalierPlate are not directly saved in db
        em.detach(updatedPalierPlate);
        updatedPalierPlate
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .annee(UPDATED_ANNEE)
            .effectiValue(UPDATED_EFFECTI_VALUE)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .dateModif(UPDATED_DATE_MODIF)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restPalierPlateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPalierPlate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPalierPlate))
            )
            .andExpect(status().isOk());

        // Validate the PalierPlate in the database
        List<PalierPlate> palierPlateList = palierPlateRepository.findAll();
        assertThat(palierPlateList).hasSize(databaseSizeBeforeUpdate);
        PalierPlate testPalierPlate = palierPlateList.get(palierPlateList.size() - 1);
        assertThat(testPalierPlate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPalierPlate.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testPalierPlate.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testPalierPlate.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testPalierPlate.getEffectiValue()).isEqualTo(UPDATED_EFFECTI_VALUE);
        assertThat(testPalierPlate.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPalierPlate.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testPalierPlate.getDateModif()).isEqualTo(UPDATED_DATE_MODIF);
        assertThat(testPalierPlate.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPalierPlate.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testPalierPlate.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingPalierPlate() throws Exception {
        int databaseSizeBeforeUpdate = palierPlateRepository.findAll().size();
        palierPlate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPalierPlateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, palierPlate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(palierPlate))
            )
            .andExpect(status().isBadRequest());

        // Validate the PalierPlate in the database
        List<PalierPlate> palierPlateList = palierPlateRepository.findAll();
        assertThat(palierPlateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPalierPlate() throws Exception {
        int databaseSizeBeforeUpdate = palierPlateRepository.findAll().size();
        palierPlate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalierPlateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(palierPlate))
            )
            .andExpect(status().isBadRequest());

        // Validate the PalierPlate in the database
        List<PalierPlate> palierPlateList = palierPlateRepository.findAll();
        assertThat(palierPlateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPalierPlate() throws Exception {
        int databaseSizeBeforeUpdate = palierPlateRepository.findAll().size();
        palierPlate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalierPlateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(palierPlate)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PalierPlate in the database
        List<PalierPlate> palierPlateList = palierPlateRepository.findAll();
        assertThat(palierPlateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePalierPlateWithPatch() throws Exception {
        // Initialize the database
        palierPlateRepository.saveAndFlush(palierPlate);

        int databaseSizeBeforeUpdate = palierPlateRepository.findAll().size();

        // Update the palierPlate using partial update
        PalierPlate partialUpdatedPalierPlate = new PalierPlate();
        partialUpdatedPalierPlate.setId(palierPlate.getId());

        partialUpdatedPalierPlate
            .code(UPDATED_CODE)
            .annee(UPDATED_ANNEE)
            .effectiValue(UPDATED_EFFECTI_VALUE)
            .util(UPDATED_UTIL)
            .dateModif(UPDATED_DATE_MODIF)
            .isDeleted(UPDATED_IS_DELETED);

        restPalierPlateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPalierPlate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPalierPlate))
            )
            .andExpect(status().isOk());

        // Validate the PalierPlate in the database
        List<PalierPlate> palierPlateList = palierPlateRepository.findAll();
        assertThat(palierPlateList).hasSize(databaseSizeBeforeUpdate);
        PalierPlate testPalierPlate = palierPlateList.get(palierPlateList.size() - 1);
        assertThat(testPalierPlate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPalierPlate.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testPalierPlate.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testPalierPlate.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testPalierPlate.getEffectiValue()).isEqualTo(UPDATED_EFFECTI_VALUE);
        assertThat(testPalierPlate.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPalierPlate.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testPalierPlate.getDateModif()).isEqualTo(UPDATED_DATE_MODIF);
        assertThat(testPalierPlate.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPalierPlate.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testPalierPlate.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdatePalierPlateWithPatch() throws Exception {
        // Initialize the database
        palierPlateRepository.saveAndFlush(palierPlate);

        int databaseSizeBeforeUpdate = palierPlateRepository.findAll().size();

        // Update the palierPlate using partial update
        PalierPlate partialUpdatedPalierPlate = new PalierPlate();
        partialUpdatedPalierPlate.setId(palierPlate.getId());

        partialUpdatedPalierPlate
            .code(UPDATED_CODE)
            .libEn(UPDATED_LIB_EN)
            .libAr(UPDATED_LIB_AR)
            .annee(UPDATED_ANNEE)
            .effectiValue(UPDATED_EFFECTI_VALUE)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .dateModif(UPDATED_DATE_MODIF)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restPalierPlateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPalierPlate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPalierPlate))
            )
            .andExpect(status().isOk());

        // Validate the PalierPlate in the database
        List<PalierPlate> palierPlateList = palierPlateRepository.findAll();
        assertThat(palierPlateList).hasSize(databaseSizeBeforeUpdate);
        PalierPlate testPalierPlate = palierPlateList.get(palierPlateList.size() - 1);
        assertThat(testPalierPlate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPalierPlate.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testPalierPlate.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testPalierPlate.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testPalierPlate.getEffectiValue()).isEqualTo(UPDATED_EFFECTI_VALUE);
        assertThat(testPalierPlate.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPalierPlate.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testPalierPlate.getDateModif()).isEqualTo(UPDATED_DATE_MODIF);
        assertThat(testPalierPlate.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPalierPlate.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testPalierPlate.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingPalierPlate() throws Exception {
        int databaseSizeBeforeUpdate = palierPlateRepository.findAll().size();
        palierPlate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPalierPlateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, palierPlate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(palierPlate))
            )
            .andExpect(status().isBadRequest());

        // Validate the PalierPlate in the database
        List<PalierPlate> palierPlateList = palierPlateRepository.findAll();
        assertThat(palierPlateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPalierPlate() throws Exception {
        int databaseSizeBeforeUpdate = palierPlateRepository.findAll().size();
        palierPlate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalierPlateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(palierPlate))
            )
            .andExpect(status().isBadRequest());

        // Validate the PalierPlate in the database
        List<PalierPlate> palierPlateList = palierPlateRepository.findAll();
        assertThat(palierPlateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPalierPlate() throws Exception {
        int databaseSizeBeforeUpdate = palierPlateRepository.findAll().size();
        palierPlate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPalierPlateMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(palierPlate))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PalierPlate in the database
        List<PalierPlate> palierPlateList = palierPlateRepository.findAll();
        assertThat(palierPlateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePalierPlate() throws Exception {
        // Initialize the database
        palierPlateRepository.saveAndFlush(palierPlate);

        int databaseSizeBeforeDelete = palierPlateRepository.findAll().size();

        // Delete the palierPlate
        restPalierPlateMockMvc
            .perform(delete(ENTITY_API_URL_ID, palierPlate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PalierPlate> palierPlateList = palierPlateRepository.findAll();
        assertThat(palierPlateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
