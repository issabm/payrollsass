package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Rebrique;
import com.issa.payroll.repository.RebriqueRepository;
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
 * Integration tests for the {@link RebriqueResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class RebriqueResourceIT {

    private static final Integer DEFAULT_PRIORITE = 1;
    private static final Integer UPDATED_PRIORITE = 2;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_FR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_FR = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IN_TAX = false;
    private static final Boolean UPDATED_IN_TAX = true;

    private static final Double DEFAULT_MIN_VALUE = 1D;
    private static final Double UPDATED_MIN_VALUE = 2D;

    private static final Double DEFAULT_MAX_VALUE = 1D;
    private static final Double UPDATED_MAX_VALUE = 2D;

    private static final LocalDate DEFAULT_DATE_SITUATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_SITUATION = LocalDate.now(ZoneId.systemDefault());

    private static final ZonedDateTime DEFAULT_DATEOP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATEOP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

    private static final String DEFAULT_OP = "AAAAAAAAAA";
    private static final String UPDATED_OP = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/rebriques";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RebriqueRepository rebriqueRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRebriqueMockMvc;

    private Rebrique rebrique;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rebrique createEntity(EntityManager em) {
        Rebrique rebrique = new Rebrique()
            .priorite(DEFAULT_PRIORITE)
            .code(DEFAULT_CODE)
            .libAr(DEFAULT_LIB_AR)
            .libFr(DEFAULT_LIB_FR)
            .libEn(DEFAULT_LIB_EN)
            .inTax(DEFAULT_IN_TAX)
            .minValue(DEFAULT_MIN_VALUE)
            .maxValue(DEFAULT_MAX_VALUE)
            .dateSituation(DEFAULT_DATE_SITUATION)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .util(DEFAULT_UTIL)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return rebrique;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Rebrique createUpdatedEntity(EntityManager em) {
        Rebrique rebrique = new Rebrique()
            .priorite(UPDATED_PRIORITE)
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .libEn(UPDATED_LIB_EN)
            .inTax(UPDATED_IN_TAX)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .util(UPDATED_UTIL)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return rebrique;
    }

    @BeforeEach
    public void initTest() {
        rebrique = createEntity(em);
    }

    @Test
    @Transactional
    void createRebrique() throws Exception {
        int databaseSizeBeforeCreate = rebriqueRepository.findAll().size();
        // Create the Rebrique
        restRebriqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rebrique)))
            .andExpect(status().isCreated());

        // Validate the Rebrique in the database
        List<Rebrique> rebriqueList = rebriqueRepository.findAll();
        assertThat(rebriqueList).hasSize(databaseSizeBeforeCreate + 1);
        Rebrique testRebrique = rebriqueList.get(rebriqueList.size() - 1);
        assertThat(testRebrique.getPriorite()).isEqualTo(DEFAULT_PRIORITE);
        assertThat(testRebrique.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRebrique.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testRebrique.getLibFr()).isEqualTo(DEFAULT_LIB_FR);
        assertThat(testRebrique.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testRebrique.getInTax()).isEqualTo(DEFAULT_IN_TAX);
        assertThat(testRebrique.getMinValue()).isEqualTo(DEFAULT_MIN_VALUE);
        assertThat(testRebrique.getMaxValue()).isEqualTo(DEFAULT_MAX_VALUE);
        assertThat(testRebrique.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testRebrique.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testRebrique.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testRebrique.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRebrique.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testRebrique.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testRebrique.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testRebrique.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testRebrique.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createRebriqueWithExistingId() throws Exception {
        // Create the Rebrique with an existing ID
        rebrique.setId(1L);

        int databaseSizeBeforeCreate = rebriqueRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRebriqueMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rebrique)))
            .andExpect(status().isBadRequest());

        // Validate the Rebrique in the database
        List<Rebrique> rebriqueList = rebriqueRepository.findAll();
        assertThat(rebriqueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRebriques() throws Exception {
        // Initialize the database
        rebriqueRepository.saveAndFlush(rebrique);

        // Get all the rebriqueList
        restRebriqueMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rebrique.getId().intValue())))
            .andExpect(jsonPath("$.[*].priorite").value(hasItem(DEFAULT_PRIORITE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].libFr").value(hasItem(DEFAULT_LIB_FR)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].inTax").value(hasItem(DEFAULT_IN_TAX.booleanValue())))
            .andExpect(jsonPath("$.[*].minValue").value(hasItem(DEFAULT_MIN_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxValue").value(hasItem(DEFAULT_MAX_VALUE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateSituation").value(hasItem(DEFAULT_DATE_SITUATION.toString())))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    void getRebrique() throws Exception {
        // Initialize the database
        rebriqueRepository.saveAndFlush(rebrique);

        // Get the rebrique
        restRebriqueMockMvc
            .perform(get(ENTITY_API_URL_ID, rebrique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rebrique.getId().intValue()))
            .andExpect(jsonPath("$.priorite").value(DEFAULT_PRIORITE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.libFr").value(DEFAULT_LIB_FR))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.inTax").value(DEFAULT_IN_TAX.booleanValue()))
            .andExpect(jsonPath("$.minValue").value(DEFAULT_MIN_VALUE.doubleValue()))
            .andExpect(jsonPath("$.maxValue").value(DEFAULT_MAX_VALUE.doubleValue()))
            .andExpect(jsonPath("$.dateSituation").value(DEFAULT_DATE_SITUATION.toString()))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingRebrique() throws Exception {
        // Get the rebrique
        restRebriqueMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRebrique() throws Exception {
        // Initialize the database
        rebriqueRepository.saveAndFlush(rebrique);

        int databaseSizeBeforeUpdate = rebriqueRepository.findAll().size();

        // Update the rebrique
        Rebrique updatedRebrique = rebriqueRepository.findById(rebrique.getId()).get();
        // Disconnect from session so that the updates on updatedRebrique are not directly saved in db
        em.detach(updatedRebrique);
        updatedRebrique
            .priorite(UPDATED_PRIORITE)
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .libEn(UPDATED_LIB_EN)
            .inTax(UPDATED_IN_TAX)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .util(UPDATED_UTIL)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restRebriqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedRebrique.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedRebrique))
            )
            .andExpect(status().isOk());

        // Validate the Rebrique in the database
        List<Rebrique> rebriqueList = rebriqueRepository.findAll();
        assertThat(rebriqueList).hasSize(databaseSizeBeforeUpdate);
        Rebrique testRebrique = rebriqueList.get(rebriqueList.size() - 1);
        assertThat(testRebrique.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testRebrique.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRebrique.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testRebrique.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testRebrique.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testRebrique.getInTax()).isEqualTo(UPDATED_IN_TAX);
        assertThat(testRebrique.getMinValue()).isEqualTo(UPDATED_MIN_VALUE);
        assertThat(testRebrique.getMaxValue()).isEqualTo(UPDATED_MAX_VALUE);
        assertThat(testRebrique.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testRebrique.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testRebrique.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testRebrique.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRebrique.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testRebrique.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testRebrique.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testRebrique.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRebrique.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingRebrique() throws Exception {
        int databaseSizeBeforeUpdate = rebriqueRepository.findAll().size();
        rebrique.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRebriqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rebrique.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rebrique))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rebrique in the database
        List<Rebrique> rebriqueList = rebriqueRepository.findAll();
        assertThat(rebriqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRebrique() throws Exception {
        int databaseSizeBeforeUpdate = rebriqueRepository.findAll().size();
        rebrique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRebriqueMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rebrique))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rebrique in the database
        List<Rebrique> rebriqueList = rebriqueRepository.findAll();
        assertThat(rebriqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRebrique() throws Exception {
        int databaseSizeBeforeUpdate = rebriqueRepository.findAll().size();
        rebrique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRebriqueMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rebrique)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rebrique in the database
        List<Rebrique> rebriqueList = rebriqueRepository.findAll();
        assertThat(rebriqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRebriqueWithPatch() throws Exception {
        // Initialize the database
        rebriqueRepository.saveAndFlush(rebrique);

        int databaseSizeBeforeUpdate = rebriqueRepository.findAll().size();

        // Update the rebrique using partial update
        Rebrique partialUpdatedRebrique = new Rebrique();
        partialUpdatedRebrique.setId(rebrique.getId());

        partialUpdatedRebrique
            .priorite(UPDATED_PRIORITE)
            .minValue(UPDATED_MIN_VALUE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE);

        restRebriqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRebrique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRebrique))
            )
            .andExpect(status().isOk());

        // Validate the Rebrique in the database
        List<Rebrique> rebriqueList = rebriqueRepository.findAll();
        assertThat(rebriqueList).hasSize(databaseSizeBeforeUpdate);
        Rebrique testRebrique = rebriqueList.get(rebriqueList.size() - 1);
        assertThat(testRebrique.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testRebrique.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRebrique.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testRebrique.getLibFr()).isEqualTo(DEFAULT_LIB_FR);
        assertThat(testRebrique.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testRebrique.getInTax()).isEqualTo(DEFAULT_IN_TAX);
        assertThat(testRebrique.getMinValue()).isEqualTo(UPDATED_MIN_VALUE);
        assertThat(testRebrique.getMaxValue()).isEqualTo(DEFAULT_MAX_VALUE);
        assertThat(testRebrique.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testRebrique.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testRebrique.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testRebrique.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testRebrique.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testRebrique.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testRebrique.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testRebrique.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRebrique.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateRebriqueWithPatch() throws Exception {
        // Initialize the database
        rebriqueRepository.saveAndFlush(rebrique);

        int databaseSizeBeforeUpdate = rebriqueRepository.findAll().size();

        // Update the rebrique using partial update
        Rebrique partialUpdatedRebrique = new Rebrique();
        partialUpdatedRebrique.setId(rebrique.getId());

        partialUpdatedRebrique
            .priorite(UPDATED_PRIORITE)
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .libEn(UPDATED_LIB_EN)
            .inTax(UPDATED_IN_TAX)
            .minValue(UPDATED_MIN_VALUE)
            .maxValue(UPDATED_MAX_VALUE)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .util(UPDATED_UTIL)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restRebriqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRebrique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRebrique))
            )
            .andExpect(status().isOk());

        // Validate the Rebrique in the database
        List<Rebrique> rebriqueList = rebriqueRepository.findAll();
        assertThat(rebriqueList).hasSize(databaseSizeBeforeUpdate);
        Rebrique testRebrique = rebriqueList.get(rebriqueList.size() - 1);
        assertThat(testRebrique.getPriorite()).isEqualTo(UPDATED_PRIORITE);
        assertThat(testRebrique.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRebrique.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testRebrique.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testRebrique.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testRebrique.getInTax()).isEqualTo(UPDATED_IN_TAX);
        assertThat(testRebrique.getMinValue()).isEqualTo(UPDATED_MIN_VALUE);
        assertThat(testRebrique.getMaxValue()).isEqualTo(UPDATED_MAX_VALUE);
        assertThat(testRebrique.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testRebrique.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testRebrique.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testRebrique.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testRebrique.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testRebrique.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testRebrique.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testRebrique.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testRebrique.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingRebrique() throws Exception {
        int databaseSizeBeforeUpdate = rebriqueRepository.findAll().size();
        rebrique.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRebriqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rebrique.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rebrique))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rebrique in the database
        List<Rebrique> rebriqueList = rebriqueRepository.findAll();
        assertThat(rebriqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRebrique() throws Exception {
        int databaseSizeBeforeUpdate = rebriqueRepository.findAll().size();
        rebrique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRebriqueMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rebrique))
            )
            .andExpect(status().isBadRequest());

        // Validate the Rebrique in the database
        List<Rebrique> rebriqueList = rebriqueRepository.findAll();
        assertThat(rebriqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRebrique() throws Exception {
        int databaseSizeBeforeUpdate = rebriqueRepository.findAll().size();
        rebrique.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRebriqueMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(rebrique)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Rebrique in the database
        List<Rebrique> rebriqueList = rebriqueRepository.findAll();
        assertThat(rebriqueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRebrique() throws Exception {
        // Initialize the database
        rebriqueRepository.saveAndFlush(rebrique);

        int databaseSizeBeforeDelete = rebriqueRepository.findAll().size();

        // Delete the rebrique
        restRebriqueMockMvc
            .perform(delete(ENTITY_API_URL_ID, rebrique.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Rebrique> rebriqueList = rebriqueRepository.findAll();
        assertThat(rebriqueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
