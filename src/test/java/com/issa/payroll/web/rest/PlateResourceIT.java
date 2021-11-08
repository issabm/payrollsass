package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Plate;
import com.issa.payroll.repository.PlateRepository;
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
 * Integration tests for the {@link PlateResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PlateResourceIT {

    private static final Integer DEFAULT_PRIORITE = 1;
    private static final Integer UPDATED_PRIORITE = 2;

    private static final Double DEFAULT_VALUE_CALCUL = 1D;
    private static final Double UPDATED_VALUE_CALCUL = 2D;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/plates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlateRepository plateRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlateMockMvc;

    private Plate plate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plate createEntity(EntityManager em) {
        Plate plate = new Plate()
            .priorite(DEFAULT_PRIORITE)
            .valueCalcul(DEFAULT_VALUE_CALCUL)
            .code(DEFAULT_CODE)
            .libAr(DEFAULT_LIB_AR)
            .libEn(DEFAULT_LIB_EN)
            .dateop(DEFAULT_DATEOP)
            .util(DEFAULT_UTIL)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return plate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plate createUpdatedEntity(EntityManager em) {
        Plate plate = new Plate()
            .priorite(UPDATED_PRIORITE)
            .valueCalcul(UPDATED_VALUE_CALCUL)
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return plate;
    }

    @BeforeEach
    public void initTest() {
        plate = createEntity(em);
    }

    @Test
    @Transactional
    void createPlate() throws Exception {
        int databaseSizeBeforeCreate = plateRepository.findAll().size();
        // Create the Plate
        restPlateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plate)))
            .andExpect(status().isCreated());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeCreate + 1);
        Plate testPlate = plateList.get(plateList.size() - 1);
        assertThat(testPlate.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
        assertThat(testPlate.getValueCalcul()).isEqualTo(DEFAULT_VALUE_CALCUL);
        assertThat(testPlate.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPlate.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testPlate.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testPlate.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testPlate.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testPlate.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPlate.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testPlate.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testPlate.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPlate.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createPlateWithExistingId() throws Exception {
        // Create the Plate with an existing ID
        plate.setId(1L);

        int databaseSizeBeforeCreate = plateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plate)))
            .andExpect(status().isBadRequest());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlates() throws Exception {
        // Initialize the database
        plateRepository.saveAndFlush(plate);

        // Get all the plateList
        restPlateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plate.getId().intValue())))
            .andExpect(jsonPath("$.[*].priorite").value(hasItem(DEFAULT_PRIORITE)))
            .andExpect(jsonPath("$.[*].valueCalcul").value(hasItem(DEFAULT_VALUE_CALCUL.doubleValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
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
    void getPlate() throws Exception {
        // Initialize the database
        plateRepository.saveAndFlush(plate);

        // Get the plate
        restPlateMockMvc
            .perform(get(ENTITY_API_URL_ID, plate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plate.getId().intValue()))
            .andExpect(jsonPath("$.priorite").value(DEFAULT_PRIORITE))
            .andExpect(jsonPath("$.valueCalcul").value(DEFAULT_VALUE_CALCUL.doubleValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
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
    void getNonExistingPlate() throws Exception {
        // Get the plate
        restPlateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlate() throws Exception {
        // Initialize the database
        plateRepository.saveAndFlush(plate);

        int databaseSizeBeforeUpdate = plateRepository.findAll().size();

        // Update the plate
        Plate updatedPlate = plateRepository.findById(plate.getId()).get();
        // Disconnect from session so that the updates on updatedPlate are not directly saved in db
        em.detach(updatedPlate);
        updatedPlate
            .priorite(UPDATED_PRIORITE)
            .valueCalcul(UPDATED_VALUE_CALCUL)
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restPlateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlate))
            )
            .andExpect(status().isOk());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
        Plate testPlate = plateList.get(plateList.size() - 1);
        assertThat(testPlate.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testPlate.getValueCalcul()).isEqualTo(UPDATED_VALUE_CALCUL);
        assertThat(testPlate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPlate.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testPlate.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testPlate.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testPlate.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPlate.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPlate.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testPlate.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testPlate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPlate.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPlate() throws Exception {
        int databaseSizeBeforeUpdate = plateRepository.findAll().size();
        plate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plate.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlate() throws Exception {
        int databaseSizeBeforeUpdate = plateRepository.findAll().size();
        plate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlate() throws Exception {
        int databaseSizeBeforeUpdate = plateRepository.findAll().size();
        plate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plate)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlateWithPatch() throws Exception {
        // Initialize the database
        plateRepository.saveAndFlush(plate);

        int databaseSizeBeforeUpdate = plateRepository.findAll().size();

        // Update the plate using partial update
        Plate partialUpdatedPlate = new Plate();
        partialUpdatedPlate.setId(plate.getId());

        partialUpdatedPlate
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restPlateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlate))
            )
            .andExpect(status().isOk());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
        Plate testPlate = plateList.get(plateList.size() - 1);
        assertThat(testPlate.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
        assertThat(testPlate.getValueCalcul()).isEqualTo(DEFAULT_VALUE_CALCUL);
        assertThat(testPlate.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPlate.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testPlate.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testPlate.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testPlate.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPlate.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPlate.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testPlate.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testPlate.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPlate.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePlateWithPatch() throws Exception {
        // Initialize the database
        plateRepository.saveAndFlush(plate);

        int databaseSizeBeforeUpdate = plateRepository.findAll().size();

        // Update the plate using partial update
        Plate partialUpdatedPlate = new Plate();
        partialUpdatedPlate.setId(plate.getId());

        partialUpdatedPlate
            .priorite(UPDATED_PRIORITE)
            .valueCalcul(UPDATED_VALUE_CALCUL)
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .dateop(UPDATED_DATEOP)
            .util(UPDATED_UTIL)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restPlateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlate))
            )
            .andExpect(status().isOk());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
        Plate testPlate = plateList.get(plateList.size() - 1);
        assertThat(testPlate.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testPlate.getValueCalcul()).isEqualTo(UPDATED_VALUE_CALCUL);
        assertThat(testPlate.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPlate.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testPlate.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testPlate.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testPlate.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPlate.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPlate.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testPlate.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testPlate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPlate.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPlate() throws Exception {
        int databaseSizeBeforeUpdate = plateRepository.findAll().size();
        plate.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlate() throws Exception {
        int databaseSizeBeforeUpdate = plateRepository.findAll().size();
        plate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plate))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlate() throws Exception {
        int databaseSizeBeforeUpdate = plateRepository.findAll().size();
        plate.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlateMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plate)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlate() throws Exception {
        // Initialize the database
        plateRepository.saveAndFlush(plate);

        int databaseSizeBeforeDelete = plateRepository.findAll().size();

        // Delete the plate
        restPlateMockMvc
            .perform(delete(ENTITY_API_URL_ID, plate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
