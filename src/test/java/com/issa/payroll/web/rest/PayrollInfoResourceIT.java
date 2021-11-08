package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.PayrollInfo;
import com.issa.payroll.repository.PayrollInfoRepository;
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
 * Integration tests for the {@link PayrollInfoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PayrollInfoResourceIT {

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_FR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_FR = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_ER = "AAAAAAAAAA";
    private static final String UPDATED_LIB_ER = "BBBBBBBBBB";

    private static final Double DEFAULT_VALUE_REBRIQUE = 1D;
    private static final Double UPDATED_VALUE_REBRIQUE = 2D;

    private static final Double DEFAULT_VALUE_REB_DEVISE = 1D;
    private static final Double UPDATED_VALUE_REB_DEVISE = 2D;

    private static final Double DEFAULT_TAUX_CHANGE = 1D;
    private static final Double UPDATED_TAUX_CHANGE = 2D;

    private static final LocalDate DEFAULT_DATE_CALCUL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CALCUL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_EFFECT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_EFFECT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CALCUL_BY = "AAAAAAAAAA";
    private static final String UPDATED_CALCUL_BY = "BBBBBBBBBB";

    private static final String DEFAULT_EFFECT_BY = "AAAAAAAAAA";
    private static final String UPDATED_EFFECT_BY = "BBBBBBBBBB";

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

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/payroll-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PayrollInfoRepository payrollInfoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPayrollInfoMockMvc;

    private PayrollInfo payrollInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayrollInfo createEntity(EntityManager em) {
        PayrollInfo payrollInfo = new PayrollInfo()
            .util(DEFAULT_UTIL)
            .code(DEFAULT_CODE)
            .libAr(DEFAULT_LIB_AR)
            .libFr(DEFAULT_LIB_FR)
            .libEr(DEFAULT_LIB_ER)
            .valueRebrique(DEFAULT_VALUE_REBRIQUE)
            .valueRebDevise(DEFAULT_VALUE_REB_DEVISE)
            .tauxChange(DEFAULT_TAUX_CHANGE)
            .dateCalcul(DEFAULT_DATE_CALCUL)
            .dateEffect(DEFAULT_DATE_EFFECT)
            .calculBy(DEFAULT_CALCUL_BY)
            .effectBy(DEFAULT_EFFECT_BY)
            .dateSituation(DEFAULT_DATE_SITUATION)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return payrollInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PayrollInfo createUpdatedEntity(EntityManager em) {
        PayrollInfo payrollInfo = new PayrollInfo()
            .util(UPDATED_UTIL)
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .libEr(UPDATED_LIB_ER)
            .valueRebrique(UPDATED_VALUE_REBRIQUE)
            .valueRebDevise(UPDATED_VALUE_REB_DEVISE)
            .tauxChange(UPDATED_TAUX_CHANGE)
            .dateCalcul(UPDATED_DATE_CALCUL)
            .dateEffect(UPDATED_DATE_EFFECT)
            .calculBy(UPDATED_CALCUL_BY)
            .effectBy(UPDATED_EFFECT_BY)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return payrollInfo;
    }

    @BeforeEach
    public void initTest() {
        payrollInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createPayrollInfo() throws Exception {
        int databaseSizeBeforeCreate = payrollInfoRepository.findAll().size();
        // Create the PayrollInfo
        restPayrollInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payrollInfo)))
            .andExpect(status().isCreated());

        // Validate the PayrollInfo in the database
        List<PayrollInfo> payrollInfoList = payrollInfoRepository.findAll();
        assertThat(payrollInfoList).hasSize(databaseSizeBeforeCreate + 1);
        PayrollInfo testPayrollInfo = payrollInfoList.get(payrollInfoList.size() - 1);
        assertThat(testPayrollInfo.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testPayrollInfo.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPayrollInfo.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testPayrollInfo.getLibFr()).isEqualTo(DEFAULT_LIB_FR);
        assertThat(testPayrollInfo.getLibEr()).isEqualTo(DEFAULT_LIB_ER);
        assertThat(testPayrollInfo.getValueRebrique()).isEqualTo(DEFAULT_VALUE_REBRIQUE);
        assertThat(testPayrollInfo.getValueRebDevise()).isEqualTo(DEFAULT_VALUE_REB_DEVISE);
        assertThat(testPayrollInfo.getTauxChange()).isEqualTo(DEFAULT_TAUX_CHANGE);
        assertThat(testPayrollInfo.getDateCalcul()).isEqualTo(DEFAULT_DATE_CALCUL);
        assertThat(testPayrollInfo.getDateEffect()).isEqualTo(DEFAULT_DATE_EFFECT);
        assertThat(testPayrollInfo.getCalculBy()).isEqualTo(DEFAULT_CALCUL_BY);
        assertThat(testPayrollInfo.getEffectBy()).isEqualTo(DEFAULT_EFFECT_BY);
        assertThat(testPayrollInfo.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testPayrollInfo.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testPayrollInfo.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testPayrollInfo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPayrollInfo.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testPayrollInfo.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testPayrollInfo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPayrollInfo.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createPayrollInfoWithExistingId() throws Exception {
        // Create the PayrollInfo with an existing ID
        payrollInfo.setId(1L);

        int databaseSizeBeforeCreate = payrollInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPayrollInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payrollInfo)))
            .andExpect(status().isBadRequest());

        // Validate the PayrollInfo in the database
        List<PayrollInfo> payrollInfoList = payrollInfoRepository.findAll();
        assertThat(payrollInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPayrollInfos() throws Exception {
        // Initialize the database
        payrollInfoRepository.saveAndFlush(payrollInfo);

        // Get all the payrollInfoList
        restPayrollInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payrollInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].libFr").value(hasItem(DEFAULT_LIB_FR)))
            .andExpect(jsonPath("$.[*].libEr").value(hasItem(DEFAULT_LIB_ER)))
            .andExpect(jsonPath("$.[*].valueRebrique").value(hasItem(DEFAULT_VALUE_REBRIQUE.doubleValue())))
            .andExpect(jsonPath("$.[*].valueRebDevise").value(hasItem(DEFAULT_VALUE_REB_DEVISE.doubleValue())))
            .andExpect(jsonPath("$.[*].tauxChange").value(hasItem(DEFAULT_TAUX_CHANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCalcul").value(hasItem(DEFAULT_DATE_CALCUL.toString())))
            .andExpect(jsonPath("$.[*].dateEffect").value(hasItem(DEFAULT_DATE_EFFECT.toString())))
            .andExpect(jsonPath("$.[*].calculBy").value(hasItem(DEFAULT_CALCUL_BY)))
            .andExpect(jsonPath("$.[*].effectBy").value(hasItem(DEFAULT_EFFECT_BY)))
            .andExpect(jsonPath("$.[*].dateSituation").value(hasItem(DEFAULT_DATE_SITUATION.toString())))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(sameInstant(DEFAULT_MODIFIED_DATE))));
    }

    @Test
    @Transactional
    void getPayrollInfo() throws Exception {
        // Initialize the database
        payrollInfoRepository.saveAndFlush(payrollInfo);

        // Get the payrollInfo
        restPayrollInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, payrollInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payrollInfo.getId().intValue()))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.libFr").value(DEFAULT_LIB_FR))
            .andExpect(jsonPath("$.libEr").value(DEFAULT_LIB_ER))
            .andExpect(jsonPath("$.valueRebrique").value(DEFAULT_VALUE_REBRIQUE.doubleValue()))
            .andExpect(jsonPath("$.valueRebDevise").value(DEFAULT_VALUE_REB_DEVISE.doubleValue()))
            .andExpect(jsonPath("$.tauxChange").value(DEFAULT_TAUX_CHANGE.doubleValue()))
            .andExpect(jsonPath("$.dateCalcul").value(DEFAULT_DATE_CALCUL.toString()))
            .andExpect(jsonPath("$.dateEffect").value(DEFAULT_DATE_EFFECT.toString()))
            .andExpect(jsonPath("$.calculBy").value(DEFAULT_CALCUL_BY))
            .andExpect(jsonPath("$.effectBy").value(DEFAULT_EFFECT_BY))
            .andExpect(jsonPath("$.dateSituation").value(DEFAULT_DATE_SITUATION.toString()))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.modifiedDate").value(sameInstant(DEFAULT_MODIFIED_DATE)));
    }

    @Test
    @Transactional
    void getNonExistingPayrollInfo() throws Exception {
        // Get the payrollInfo
        restPayrollInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPayrollInfo() throws Exception {
        // Initialize the database
        payrollInfoRepository.saveAndFlush(payrollInfo);

        int databaseSizeBeforeUpdate = payrollInfoRepository.findAll().size();

        // Update the payrollInfo
        PayrollInfo updatedPayrollInfo = payrollInfoRepository.findById(payrollInfo.getId()).get();
        // Disconnect from session so that the updates on updatedPayrollInfo are not directly saved in db
        em.detach(updatedPayrollInfo);
        updatedPayrollInfo
            .util(UPDATED_UTIL)
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .libEr(UPDATED_LIB_ER)
            .valueRebrique(UPDATED_VALUE_REBRIQUE)
            .valueRebDevise(UPDATED_VALUE_REB_DEVISE)
            .tauxChange(UPDATED_TAUX_CHANGE)
            .dateCalcul(UPDATED_DATE_CALCUL)
            .dateEffect(UPDATED_DATE_EFFECT)
            .calculBy(UPDATED_CALCUL_BY)
            .effectBy(UPDATED_EFFECT_BY)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restPayrollInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPayrollInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPayrollInfo))
            )
            .andExpect(status().isOk());

        // Validate the PayrollInfo in the database
        List<PayrollInfo> payrollInfoList = payrollInfoRepository.findAll();
        assertThat(payrollInfoList).hasSize(databaseSizeBeforeUpdate);
        PayrollInfo testPayrollInfo = payrollInfoList.get(payrollInfoList.size() - 1);
        assertThat(testPayrollInfo.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPayrollInfo.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPayrollInfo.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testPayrollInfo.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testPayrollInfo.getLibEr()).isEqualTo(UPDATED_LIB_ER);
        assertThat(testPayrollInfo.getValueRebrique()).isEqualTo(UPDATED_VALUE_REBRIQUE);
        assertThat(testPayrollInfo.getValueRebDevise()).isEqualTo(UPDATED_VALUE_REB_DEVISE);
        assertThat(testPayrollInfo.getTauxChange()).isEqualTo(UPDATED_TAUX_CHANGE);
        assertThat(testPayrollInfo.getDateCalcul()).isEqualTo(UPDATED_DATE_CALCUL);
        assertThat(testPayrollInfo.getDateEffect()).isEqualTo(UPDATED_DATE_EFFECT);
        assertThat(testPayrollInfo.getCalculBy()).isEqualTo(UPDATED_CALCUL_BY);
        assertThat(testPayrollInfo.getEffectBy()).isEqualTo(UPDATED_EFFECT_BY);
        assertThat(testPayrollInfo.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testPayrollInfo.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testPayrollInfo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPayrollInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPayrollInfo.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testPayrollInfo.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testPayrollInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPayrollInfo.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPayrollInfo() throws Exception {
        int databaseSizeBeforeUpdate = payrollInfoRepository.findAll().size();
        payrollInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayrollInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, payrollInfo.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollInfo in the database
        List<PayrollInfo> payrollInfoList = payrollInfoRepository.findAll();
        assertThat(payrollInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayrollInfo() throws Exception {
        int databaseSizeBeforeUpdate = payrollInfoRepository.findAll().size();
        payrollInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(payrollInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollInfo in the database
        List<PayrollInfo> payrollInfoList = payrollInfoRepository.findAll();
        assertThat(payrollInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayrollInfo() throws Exception {
        int databaseSizeBeforeUpdate = payrollInfoRepository.findAll().size();
        payrollInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(payrollInfo)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayrollInfo in the database
        List<PayrollInfo> payrollInfoList = payrollInfoRepository.findAll();
        assertThat(payrollInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePayrollInfoWithPatch() throws Exception {
        // Initialize the database
        payrollInfoRepository.saveAndFlush(payrollInfo);

        int databaseSizeBeforeUpdate = payrollInfoRepository.findAll().size();

        // Update the payrollInfo using partial update
        PayrollInfo partialUpdatedPayrollInfo = new PayrollInfo();
        partialUpdatedPayrollInfo.setId(payrollInfo.getId());

        partialUpdatedPayrollInfo
            .util(UPDATED_UTIL)
            .valueRebDevise(UPDATED_VALUE_REB_DEVISE)
            .dateCalcul(UPDATED_DATE_CALCUL)
            .dateEffect(UPDATED_DATE_EFFECT)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY);

        restPayrollInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayrollInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayrollInfo))
            )
            .andExpect(status().isOk());

        // Validate the PayrollInfo in the database
        List<PayrollInfo> payrollInfoList = payrollInfoRepository.findAll();
        assertThat(payrollInfoList).hasSize(databaseSizeBeforeUpdate);
        PayrollInfo testPayrollInfo = payrollInfoList.get(payrollInfoList.size() - 1);
        assertThat(testPayrollInfo.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPayrollInfo.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPayrollInfo.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testPayrollInfo.getLibFr()).isEqualTo(DEFAULT_LIB_FR);
        assertThat(testPayrollInfo.getLibEr()).isEqualTo(DEFAULT_LIB_ER);
        assertThat(testPayrollInfo.getValueRebrique()).isEqualTo(DEFAULT_VALUE_REBRIQUE);
        assertThat(testPayrollInfo.getValueRebDevise()).isEqualTo(UPDATED_VALUE_REB_DEVISE);
        assertThat(testPayrollInfo.getTauxChange()).isEqualTo(DEFAULT_TAUX_CHANGE);
        assertThat(testPayrollInfo.getDateCalcul()).isEqualTo(UPDATED_DATE_CALCUL);
        assertThat(testPayrollInfo.getDateEffect()).isEqualTo(UPDATED_DATE_EFFECT);
        assertThat(testPayrollInfo.getCalculBy()).isEqualTo(DEFAULT_CALCUL_BY);
        assertThat(testPayrollInfo.getEffectBy()).isEqualTo(DEFAULT_EFFECT_BY);
        assertThat(testPayrollInfo.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testPayrollInfo.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testPayrollInfo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPayrollInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPayrollInfo.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testPayrollInfo.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testPayrollInfo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPayrollInfo.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePayrollInfoWithPatch() throws Exception {
        // Initialize the database
        payrollInfoRepository.saveAndFlush(payrollInfo);

        int databaseSizeBeforeUpdate = payrollInfoRepository.findAll().size();

        // Update the payrollInfo using partial update
        PayrollInfo partialUpdatedPayrollInfo = new PayrollInfo();
        partialUpdatedPayrollInfo.setId(payrollInfo.getId());

        partialUpdatedPayrollInfo
            .util(UPDATED_UTIL)
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libFr(UPDATED_LIB_FR)
            .libEr(UPDATED_LIB_ER)
            .valueRebrique(UPDATED_VALUE_REBRIQUE)
            .valueRebDevise(UPDATED_VALUE_REB_DEVISE)
            .tauxChange(UPDATED_TAUX_CHANGE)
            .dateCalcul(UPDATED_DATE_CALCUL)
            .dateEffect(UPDATED_DATE_EFFECT)
            .calculBy(UPDATED_CALCUL_BY)
            .effectBy(UPDATED_EFFECT_BY)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restPayrollInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayrollInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayrollInfo))
            )
            .andExpect(status().isOk());

        // Validate the PayrollInfo in the database
        List<PayrollInfo> payrollInfoList = payrollInfoRepository.findAll();
        assertThat(payrollInfoList).hasSize(databaseSizeBeforeUpdate);
        PayrollInfo testPayrollInfo = payrollInfoList.get(payrollInfoList.size() - 1);
        assertThat(testPayrollInfo.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testPayrollInfo.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPayrollInfo.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testPayrollInfo.getLibFr()).isEqualTo(UPDATED_LIB_FR);
        assertThat(testPayrollInfo.getLibEr()).isEqualTo(UPDATED_LIB_ER);
        assertThat(testPayrollInfo.getValueRebrique()).isEqualTo(UPDATED_VALUE_REBRIQUE);
        assertThat(testPayrollInfo.getValueRebDevise()).isEqualTo(UPDATED_VALUE_REB_DEVISE);
        assertThat(testPayrollInfo.getTauxChange()).isEqualTo(UPDATED_TAUX_CHANGE);
        assertThat(testPayrollInfo.getDateCalcul()).isEqualTo(UPDATED_DATE_CALCUL);
        assertThat(testPayrollInfo.getDateEffect()).isEqualTo(UPDATED_DATE_EFFECT);
        assertThat(testPayrollInfo.getCalculBy()).isEqualTo(UPDATED_CALCUL_BY);
        assertThat(testPayrollInfo.getEffectBy()).isEqualTo(UPDATED_EFFECT_BY);
        assertThat(testPayrollInfo.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testPayrollInfo.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testPayrollInfo.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testPayrollInfo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPayrollInfo.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testPayrollInfo.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testPayrollInfo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPayrollInfo.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPayrollInfo() throws Exception {
        int databaseSizeBeforeUpdate = payrollInfoRepository.findAll().size();
        payrollInfo.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPayrollInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, payrollInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payrollInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollInfo in the database
        List<PayrollInfo> payrollInfoList = payrollInfoRepository.findAll();
        assertThat(payrollInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayrollInfo() throws Exception {
        int databaseSizeBeforeUpdate = payrollInfoRepository.findAll().size();
        payrollInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(payrollInfo))
            )
            .andExpect(status().isBadRequest());

        // Validate the PayrollInfo in the database
        List<PayrollInfo> payrollInfoList = payrollInfoRepository.findAll();
        assertThat(payrollInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayrollInfo() throws Exception {
        int databaseSizeBeforeUpdate = payrollInfoRepository.findAll().size();
        payrollInfo.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPayrollInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(payrollInfo))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PayrollInfo in the database
        List<PayrollInfo> payrollInfoList = payrollInfoRepository.findAll();
        assertThat(payrollInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayrollInfo() throws Exception {
        // Initialize the database
        payrollInfoRepository.saveAndFlush(payrollInfo);

        int databaseSizeBeforeDelete = payrollInfoRepository.findAll().size();

        // Delete the payrollInfo
        restPayrollInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, payrollInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PayrollInfo> payrollInfoList = payrollInfoRepository.findAll();
        assertThat(payrollInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
