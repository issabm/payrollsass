package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.PlateInfo;
import com.issa.payroll.repository.PlateInfoRepository;
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
 * Integration tests for the {@link PlateInfoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PlateInfoResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB = "AAAAAAAAAA";
    private static final String UPDATED_LIB = "BBBBBBBBBB";

    private static final Double DEFAULT_VAL_TAKEN = 1D;
    private static final Double UPDATED_VAL_TAKEN = 2D;

    private static final LocalDate DEFAULT_DATE_SITUATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SITUATION = LocalDate.now(ZoneId.systemDefault());

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

    private static final String ENTITY_API_URL = "/api/plate-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlateInfoRepository plateInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlateInfoMockMvc;

    private PlateInfo plateInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlateInfo createEntity(EntityManager em) {
        PlateInfo plateInfo = new PlateInfo()
            .code(DEFAULT_CODE)
            .lib(DEFAULT_LIB)
            .valTaken(DEFAULT_VAL_TAKEN)
            .dateSituation(DEFAULT_DATE_SITUATION)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .op(DEFAULT_OP)
            .util(DEFAULT_UTIL)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return plateInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlateInfo createUpdatedEntity(EntityManager em) {
        PlateInfo plateInfo = new PlateInfo()
            .code(UPDATED_CODE)
            .lib(UPDATED_LIB)
            .valTaken(UPDATED_VAL_TAKEN)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return plateInfo;
    }

    @BeforeEach
    public void initTest() {
        plateInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createPlateInfo() throws Exception {
        int databaseSizeBeforeCreate = plateInfoRepository.findAll().size();
        // Create the PlateInfo
        restPlateInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plateInfo)))
            .andExpect(status().isCreated());

        // Validate the PlateInfo in the database
        List<PlateInfo> plateInfoList = plateInfoRepository.findAll();
        assertThat(plateInfoList).hasSize(databaseSizeBeforeCreate + 1);
        PlateInfo testPlateInfo = plateInfoList.get(plateInfoList.size() - 1);
        assertThat(testPlateInfo.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPlateInfo.getLib()).isEqualTo(DEFAULT_LIB);
        assertThat(testPlateInfo.getValTaken()).isEqualTo(DEFAULT_VAL_TAKEN);
        assertThat(testPlateInfo.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testPlateInfo.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testPlateInfo.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPlateInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPlateInfo.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testPlateInfo.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testPlateInfo.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testPlateInfo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPlateInfo.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createPlateInfoWithExistingId() throws Exception {
        // Create the PlateInfo with an existing ID
        plateInfo.setId(1L);

        int databaseSizeBeforeCreate = plateInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlateInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plateInfo)))
            .andExpect(status().isBadRequest());

        // Validate the PlateInfo in the database
        List<PlateInfo> plateInfoList = plateInfoRepository.findAll();
        assertThat(plateInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlateInfos() throws Exception {
        // Initialize the database
        plateInfoRepository.saveAndFlush(plateInfo);

        // Get all the plateInfoList
        restPlateInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plateInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].lib").value(hasItem(DEFAULT_LIB)))
            .andExpect(jsonPath("$.[*].valTaken").value(hasItem(DEFAULT_VAL_TAKEN.doubleValue())))
            .andExpect(jsonPath("$.[*].dateSituation").value(hasItem(DEFAULT_DATE_SITUATION.toString())))
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
    void getPlateInfo() throws Exception {
        // Initialize the database
        plateInfoRepository.saveAndFlush(plateInfo);

        // Get the plateInfo
        restPlateInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, plateInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plateInfo.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.lib").value(DEFAULT_LIB))
            .andExpect(jsonPath("$.valTaken").value(DEFAULT_VAL_TAKEN.doubleValue()))
            .andExpect(jsonPath("$.dateSituation").value(DEFAULT_DATE_SITUATION.toString()))
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
    void getNonExistingPlateInfo() throws Exception {
        // Get the plateInfo
        restPlateInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlateInfo() throws Exception {
        // Initialize the database
        plateInfoRepository.saveAndFlush(plateInfo);

        int databaseSizeBeforeUpdate = plateInfoRepository.findAll().size();

        // Update the plateInfo
        PlateInfo updatedPlateInfo = plateInfoRepository.findById(plateInfo.getId()).get();
        // Disconnect from session so that the updates on updatedPlateInfo are not directly saved in db
        em.detach(updatedPlateInfo);
        updatedPlateInfo
            .code(UPDATED_CODE)
            .lib(UPDATED_LIB)
            .valTaken(UPDATED_VAL_TAKEN)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restPlateInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlateInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlateInfo))
            )
            .andExpect(status().isOk());

        // Validate the PlateInfo in the database
        List<PlateInfo> plateInfoList = plateInfoRepository.findAll();
        assertThat(plateInfoList).hasSize(databaseSizeBeforeUpdate);
        PlateInfo testPlateInfo = plateInfoList.get(plateInfoList.size() - 1);
        assertThat(testPlateInfo.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPlateInfo.getLib()).isEqualTo(UPDATED_LIB);
        assertThat(testPlateInfo.getValTaken()).isEqualTo(UPDATED_VAL_TAKEN);
        assertThat(testPlateInfo.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testPlateInfo.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testPlateInfo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPlateInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPlateInfo.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testPlateInfo.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPlateInfo.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testPlateInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPlateInfo.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPlateInfo() throws Exception {
        int databaseSizeBeforeUpdate = plateInfoRepository.findAll().size();
        plateInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlateInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plateInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plateInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlateInfo in the database
        List<PlateInfo> plateInfoList = plateInfoRepository.findAll();
        assertThat(plateInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlateInfo() throws Exception {
        int databaseSizeBeforeUpdate = plateInfoRepository.findAll().size();
        plateInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlateInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plateInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlateInfo in the database
        List<PlateInfo> plateInfoList = plateInfoRepository.findAll();
        assertThat(plateInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlateInfo() throws Exception {
        int databaseSizeBeforeUpdate = plateInfoRepository.findAll().size();
        plateInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlateInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plateInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlateInfo in the database
        List<PlateInfo> plateInfoList = plateInfoRepository.findAll();
        assertThat(plateInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlateInfoWithPatch() throws Exception {
        // Initialize the database
        plateInfoRepository.saveAndFlush(plateInfo);

        int databaseSizeBeforeUpdate = plateInfoRepository.findAll().size();

        // Update the plateInfo using partial update
        PlateInfo partialUpdatedPlateInfo = new PlateInfo();
        partialUpdatedPlateInfo.setId(plateInfo.getId());

        partialUpdatedPlateInfo
            .valTaken(UPDATED_VAL_TAKEN)
            .dateSituation(UPDATED_DATE_SITUATION)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restPlateInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlateInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlateInfo))
            )
            .andExpect(status().isOk());

        // Validate the PlateInfo in the database
        List<PlateInfo> plateInfoList = plateInfoRepository.findAll();
        assertThat(plateInfoList).hasSize(databaseSizeBeforeUpdate);
        PlateInfo testPlateInfo = plateInfoList.get(plateInfoList.size() - 1);
        assertThat(testPlateInfo.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPlateInfo.getLib()).isEqualTo(DEFAULT_LIB);
        assertThat(testPlateInfo.getValTaken()).isEqualTo(UPDATED_VAL_TAKEN);
        assertThat(testPlateInfo.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testPlateInfo.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testPlateInfo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPlateInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPlateInfo.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testPlateInfo.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testPlateInfo.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testPlateInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPlateInfo.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePlateInfoWithPatch() throws Exception {
        // Initialize the database
        plateInfoRepository.saveAndFlush(plateInfo);

        int databaseSizeBeforeUpdate = plateInfoRepository.findAll().size();

        // Update the plateInfo using partial update
        PlateInfo partialUpdatedPlateInfo = new PlateInfo();
        partialUpdatedPlateInfo.setId(plateInfo.getId());

        partialUpdatedPlateInfo
            .code(UPDATED_CODE)
            .lib(UPDATED_LIB)
            .valTaken(UPDATED_VAL_TAKEN)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restPlateInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlateInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlateInfo))
            )
            .andExpect(status().isOk());

        // Validate the PlateInfo in the database
        List<PlateInfo> plateInfoList = plateInfoRepository.findAll();
        assertThat(plateInfoList).hasSize(databaseSizeBeforeUpdate);
        PlateInfo testPlateInfo = plateInfoList.get(plateInfoList.size() - 1);
        assertThat(testPlateInfo.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPlateInfo.getLib()).isEqualTo(UPDATED_LIB);
        assertThat(testPlateInfo.getValTaken()).isEqualTo(UPDATED_VAL_TAKEN);
        assertThat(testPlateInfo.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testPlateInfo.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testPlateInfo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPlateInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPlateInfo.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testPlateInfo.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPlateInfo.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testPlateInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPlateInfo.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPlateInfo() throws Exception {
        int databaseSizeBeforeUpdate = plateInfoRepository.findAll().size();
        plateInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlateInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plateInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plateInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlateInfo in the database
        List<PlateInfo> plateInfoList = plateInfoRepository.findAll();
        assertThat(plateInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlateInfo() throws Exception {
        int databaseSizeBeforeUpdate = plateInfoRepository.findAll().size();
        plateInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlateInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plateInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlateInfo in the database
        List<PlateInfo> plateInfoList = plateInfoRepository.findAll();
        assertThat(plateInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlateInfo() throws Exception {
        int databaseSizeBeforeUpdate = plateInfoRepository.findAll().size();
        plateInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlateInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plateInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlateInfo in the database
        List<PlateInfo> plateInfoList = plateInfoRepository.findAll();
        assertThat(plateInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlateInfo() throws Exception {
        // Initialize the database
        plateInfoRepository.saveAndFlush(plateInfo);

        int databaseSizeBeforeDelete = plateInfoRepository.findAll().size();

        // Delete the plateInfo
        restPlateInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, plateInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlateInfo> plateInfoList = plateInfoRepository.findAll();
        assertThat(plateInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
