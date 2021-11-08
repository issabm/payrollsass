package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.DemandeCalculPaie;
import com.issa.payroll.repository.DemandeCalculPaieRepository;
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
 * Integration tests for the {@link DemandeCalculPaieResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DemandeCalculPaieResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB = "AAAAAAAAAA";
    private static final String UPDATED_LIB = "BBBBBBBBBB";

    private static final Integer DEFAULT_ANNEE = 1;
    private static final Integer UPDATED_ANNEE = 2;

    private static final Integer DEFAULT_MOIS = 1;
    private static final Integer UPDATED_MOIS = 2;

    private static final LocalDate DEFAULT_DATE_CALCUL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CALCUL = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_VALID = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_VALID = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_PAYROLL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_PAYROLL = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_TOTAL_NET = 1D;
    private static final Double UPDATED_TOTAL_NET = 2D;

    private static final Double DEFAULT_TOTAL_NET_DEVISE = 1D;
    private static final Double UPDATED_TOTAL_NET_DEVISE = 2D;

    private static final Double DEFAULT_TAUX_CHANGE = 1D;
    private static final Double UPDATED_TAUX_CHANGE = 2D;

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

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_MODIFIED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MODIFIED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/demande-calcul-paies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DemandeCalculPaieRepository demandeCalculPaieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDemandeCalculPaieMockMvc;

    private DemandeCalculPaie demandeCalculPaie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeCalculPaie createEntity(EntityManager em) {
        DemandeCalculPaie demandeCalculPaie = new DemandeCalculPaie()
            .code(DEFAULT_CODE)
            .lib(DEFAULT_LIB)
            .annee(DEFAULT_ANNEE)
            .mois(DEFAULT_MOIS)
            .dateCalcul(DEFAULT_DATE_CALCUL)
            .dateValid(DEFAULT_DATE_VALID)
            .datePayroll(DEFAULT_DATE_PAYROLL)
            .totalNet(DEFAULT_TOTAL_NET)
            .totalNetDevise(DEFAULT_TOTAL_NET_DEVISE)
            .tauxChange(DEFAULT_TAUX_CHANGE)
            .calculBy(DEFAULT_CALCUL_BY)
            .effectBy(DEFAULT_EFFECT_BY)
            .dateSituation(DEFAULT_DATE_SITUATION)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .op(DEFAULT_OP)
            .util(DEFAULT_UTIL)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return demandeCalculPaie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DemandeCalculPaie createUpdatedEntity(EntityManager em) {
        DemandeCalculPaie demandeCalculPaie = new DemandeCalculPaie()
            .code(UPDATED_CODE)
            .lib(UPDATED_LIB)
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .dateCalcul(UPDATED_DATE_CALCUL)
            .dateValid(UPDATED_DATE_VALID)
            .datePayroll(UPDATED_DATE_PAYROLL)
            .totalNet(UPDATED_TOTAL_NET)
            .totalNetDevise(UPDATED_TOTAL_NET_DEVISE)
            .tauxChange(UPDATED_TAUX_CHANGE)
            .calculBy(UPDATED_CALCUL_BY)
            .effectBy(UPDATED_EFFECT_BY)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return demandeCalculPaie;
    }

    @BeforeEach
    public void initTest() {
        demandeCalculPaie = createEntity(em);
    }

    @Test
    @Transactional
    void createDemandeCalculPaie() throws Exception {
        int databaseSizeBeforeCreate = demandeCalculPaieRepository.findAll().size();
        // Create the DemandeCalculPaie
        restDemandeCalculPaieMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeCalculPaie))
            )
            .andExpect(status().isCreated());

        // Validate the DemandeCalculPaie in the database
        List<DemandeCalculPaie> demandeCalculPaieList = demandeCalculPaieRepository.findAll();
        assertThat(demandeCalculPaieList).hasSize(databaseSizeBeforeCreate + 1);
        DemandeCalculPaie testDemandeCalculPaie = demandeCalculPaieList.get(demandeCalculPaieList.size() - 1);
        assertThat(testDemandeCalculPaie.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDemandeCalculPaie.getLib()).isEqualTo(DEFAULT_LIB);
        assertThat(testDemandeCalculPaie.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testDemandeCalculPaie.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testDemandeCalculPaie.getDateCalcul()).isEqualTo(DEFAULT_DATE_CALCUL);
        assertThat(testDemandeCalculPaie.getDateValid()).isEqualTo(DEFAULT_DATE_VALID);
        assertThat(testDemandeCalculPaie.getDatePayroll()).isEqualTo(DEFAULT_DATE_PAYROLL);
        assertThat(testDemandeCalculPaie.getTotalNet()).isEqualTo(DEFAULT_TOTAL_NET);
        assertThat(testDemandeCalculPaie.getTotalNetDevise()).isEqualTo(DEFAULT_TOTAL_NET_DEVISE);
        assertThat(testDemandeCalculPaie.getTauxChange()).isEqualTo(DEFAULT_TAUX_CHANGE);
        assertThat(testDemandeCalculPaie.getCalculBy()).isEqualTo(DEFAULT_CALCUL_BY);
        assertThat(testDemandeCalculPaie.getEffectBy()).isEqualTo(DEFAULT_EFFECT_BY);
        assertThat(testDemandeCalculPaie.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testDemandeCalculPaie.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testDemandeCalculPaie.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testDemandeCalculPaie.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDemandeCalculPaie.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testDemandeCalculPaie.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testDemandeCalculPaie.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testDemandeCalculPaie.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDemandeCalculPaie.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createDemandeCalculPaieWithExistingId() throws Exception {
        // Create the DemandeCalculPaie with an existing ID
        demandeCalculPaie.setId(1L);

        int databaseSizeBeforeCreate = demandeCalculPaieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDemandeCalculPaieMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeCalculPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeCalculPaie in the database
        List<DemandeCalculPaie> demandeCalculPaieList = demandeCalculPaieRepository.findAll();
        assertThat(demandeCalculPaieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDemandeCalculPaies() throws Exception {
        // Initialize the database
        demandeCalculPaieRepository.saveAndFlush(demandeCalculPaie);

        // Get all the demandeCalculPaieList
        restDemandeCalculPaieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(demandeCalculPaie.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].lib").value(hasItem(DEFAULT_LIB)))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE)))
            .andExpect(jsonPath("$.[*].mois").value(hasItem(DEFAULT_MOIS)))
            .andExpect(jsonPath("$.[*].dateCalcul").value(hasItem(DEFAULT_DATE_CALCUL.toString())))
            .andExpect(jsonPath("$.[*].dateValid").value(hasItem(DEFAULT_DATE_VALID.toString())))
            .andExpect(jsonPath("$.[*].datePayroll").value(hasItem(DEFAULT_DATE_PAYROLL.toString())))
            .andExpect(jsonPath("$.[*].totalNet").value(hasItem(DEFAULT_TOTAL_NET.doubleValue())))
            .andExpect(jsonPath("$.[*].totalNetDevise").value(hasItem(DEFAULT_TOTAL_NET_DEVISE.doubleValue())))
            .andExpect(jsonPath("$.[*].tauxChange").value(hasItem(DEFAULT_TAUX_CHANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].calculBy").value(hasItem(DEFAULT_CALCUL_BY)))
            .andExpect(jsonPath("$.[*].effectBy").value(hasItem(DEFAULT_EFFECT_BY)))
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
    void getDemandeCalculPaie() throws Exception {
        // Initialize the database
        demandeCalculPaieRepository.saveAndFlush(demandeCalculPaie);

        // Get the demandeCalculPaie
        restDemandeCalculPaieMockMvc
            .perform(get(ENTITY_API_URL_ID, demandeCalculPaie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(demandeCalculPaie.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.lib").value(DEFAULT_LIB))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE))
            .andExpect(jsonPath("$.mois").value(DEFAULT_MOIS))
            .andExpect(jsonPath("$.dateCalcul").value(DEFAULT_DATE_CALCUL.toString()))
            .andExpect(jsonPath("$.dateValid").value(DEFAULT_DATE_VALID.toString()))
            .andExpect(jsonPath("$.datePayroll").value(DEFAULT_DATE_PAYROLL.toString()))
            .andExpect(jsonPath("$.totalNet").value(DEFAULT_TOTAL_NET.doubleValue()))
            .andExpect(jsonPath("$.totalNetDevise").value(DEFAULT_TOTAL_NET_DEVISE.doubleValue()))
            .andExpect(jsonPath("$.tauxChange").value(DEFAULT_TAUX_CHANGE.doubleValue()))
            .andExpect(jsonPath("$.calculBy").value(DEFAULT_CALCUL_BY))
            .andExpect(jsonPath("$.effectBy").value(DEFAULT_EFFECT_BY))
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
    void getNonExistingDemandeCalculPaie() throws Exception {
        // Get the demandeCalculPaie
        restDemandeCalculPaieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDemandeCalculPaie() throws Exception {
        // Initialize the database
        demandeCalculPaieRepository.saveAndFlush(demandeCalculPaie);

        int databaseSizeBeforeUpdate = demandeCalculPaieRepository.findAll().size();

        // Update the demandeCalculPaie
        DemandeCalculPaie updatedDemandeCalculPaie = demandeCalculPaieRepository.findById(demandeCalculPaie.getId()).get();
        // Disconnect from session so that the updates on updatedDemandeCalculPaie are not directly saved in db
        em.detach(updatedDemandeCalculPaie);
        updatedDemandeCalculPaie
            .code(UPDATED_CODE)
            .lib(UPDATED_LIB)
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .dateCalcul(UPDATED_DATE_CALCUL)
            .dateValid(UPDATED_DATE_VALID)
            .datePayroll(UPDATED_DATE_PAYROLL)
            .totalNet(UPDATED_TOTAL_NET)
            .totalNetDevise(UPDATED_TOTAL_NET_DEVISE)
            .tauxChange(UPDATED_TAUX_CHANGE)
            .calculBy(UPDATED_CALCUL_BY)
            .effectBy(UPDATED_EFFECT_BY)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restDemandeCalculPaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedDemandeCalculPaie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedDemandeCalculPaie))
            )
            .andExpect(status().isOk());

        // Validate the DemandeCalculPaie in the database
        List<DemandeCalculPaie> demandeCalculPaieList = demandeCalculPaieRepository.findAll();
        assertThat(demandeCalculPaieList).hasSize(databaseSizeBeforeUpdate);
        DemandeCalculPaie testDemandeCalculPaie = demandeCalculPaieList.get(demandeCalculPaieList.size() - 1);
        assertThat(testDemandeCalculPaie.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDemandeCalculPaie.getLib()).isEqualTo(UPDATED_LIB);
        assertThat(testDemandeCalculPaie.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testDemandeCalculPaie.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testDemandeCalculPaie.getDateCalcul()).isEqualTo(UPDATED_DATE_CALCUL);
        assertThat(testDemandeCalculPaie.getDateValid()).isEqualTo(UPDATED_DATE_VALID);
        assertThat(testDemandeCalculPaie.getDatePayroll()).isEqualTo(UPDATED_DATE_PAYROLL);
        assertThat(testDemandeCalculPaie.getTotalNet()).isEqualTo(UPDATED_TOTAL_NET);
        assertThat(testDemandeCalculPaie.getTotalNetDevise()).isEqualTo(UPDATED_TOTAL_NET_DEVISE);
        assertThat(testDemandeCalculPaie.getTauxChange()).isEqualTo(UPDATED_TAUX_CHANGE);
        assertThat(testDemandeCalculPaie.getCalculBy()).isEqualTo(UPDATED_CALCUL_BY);
        assertThat(testDemandeCalculPaie.getEffectBy()).isEqualTo(UPDATED_EFFECT_BY);
        assertThat(testDemandeCalculPaie.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testDemandeCalculPaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testDemandeCalculPaie.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDemandeCalculPaie.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDemandeCalculPaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testDemandeCalculPaie.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testDemandeCalculPaie.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDemandeCalculPaie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDemandeCalculPaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingDemandeCalculPaie() throws Exception {
        int databaseSizeBeforeUpdate = demandeCalculPaieRepository.findAll().size();
        demandeCalculPaie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeCalculPaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, demandeCalculPaie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeCalculPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeCalculPaie in the database
        List<DemandeCalculPaie> demandeCalculPaieList = demandeCalculPaieRepository.findAll();
        assertThat(demandeCalculPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDemandeCalculPaie() throws Exception {
        int databaseSizeBeforeUpdate = demandeCalculPaieRepository.findAll().size();
        demandeCalculPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeCalculPaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(demandeCalculPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeCalculPaie in the database
        List<DemandeCalculPaie> demandeCalculPaieList = demandeCalculPaieRepository.findAll();
        assertThat(demandeCalculPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDemandeCalculPaie() throws Exception {
        int databaseSizeBeforeUpdate = demandeCalculPaieRepository.findAll().size();
        demandeCalculPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeCalculPaieMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(demandeCalculPaie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeCalculPaie in the database
        List<DemandeCalculPaie> demandeCalculPaieList = demandeCalculPaieRepository.findAll();
        assertThat(demandeCalculPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDemandeCalculPaieWithPatch() throws Exception {
        // Initialize the database
        demandeCalculPaieRepository.saveAndFlush(demandeCalculPaie);

        int databaseSizeBeforeUpdate = demandeCalculPaieRepository.findAll().size();

        // Update the demandeCalculPaie using partial update
        DemandeCalculPaie partialUpdatedDemandeCalculPaie = new DemandeCalculPaie();
        partialUpdatedDemandeCalculPaie.setId(demandeCalculPaie.getId());

        partialUpdatedDemandeCalculPaie
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .datePayroll(UPDATED_DATE_PAYROLL)
            .totalNet(UPDATED_TOTAL_NET)
            .calculBy(UPDATED_CALCUL_BY)
            .effectBy(UPDATED_EFFECT_BY)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restDemandeCalculPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeCalculPaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeCalculPaie))
            )
            .andExpect(status().isOk());

        // Validate the DemandeCalculPaie in the database
        List<DemandeCalculPaie> demandeCalculPaieList = demandeCalculPaieRepository.findAll();
        assertThat(demandeCalculPaieList).hasSize(databaseSizeBeforeUpdate);
        DemandeCalculPaie testDemandeCalculPaie = demandeCalculPaieList.get(demandeCalculPaieList.size() - 1);
        assertThat(testDemandeCalculPaie.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDemandeCalculPaie.getLib()).isEqualTo(DEFAULT_LIB);
        assertThat(testDemandeCalculPaie.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testDemandeCalculPaie.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testDemandeCalculPaie.getDateCalcul()).isEqualTo(DEFAULT_DATE_CALCUL);
        assertThat(testDemandeCalculPaie.getDateValid()).isEqualTo(DEFAULT_DATE_VALID);
        assertThat(testDemandeCalculPaie.getDatePayroll()).isEqualTo(UPDATED_DATE_PAYROLL);
        assertThat(testDemandeCalculPaie.getTotalNet()).isEqualTo(UPDATED_TOTAL_NET);
        assertThat(testDemandeCalculPaie.getTotalNetDevise()).isEqualTo(DEFAULT_TOTAL_NET_DEVISE);
        assertThat(testDemandeCalculPaie.getTauxChange()).isEqualTo(DEFAULT_TAUX_CHANGE);
        assertThat(testDemandeCalculPaie.getCalculBy()).isEqualTo(UPDATED_CALCUL_BY);
        assertThat(testDemandeCalculPaie.getEffectBy()).isEqualTo(UPDATED_EFFECT_BY);
        assertThat(testDemandeCalculPaie.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testDemandeCalculPaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testDemandeCalculPaie.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDemandeCalculPaie.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDemandeCalculPaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testDemandeCalculPaie.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testDemandeCalculPaie.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDemandeCalculPaie.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDemandeCalculPaie.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateDemandeCalculPaieWithPatch() throws Exception {
        // Initialize the database
        demandeCalculPaieRepository.saveAndFlush(demandeCalculPaie);

        int databaseSizeBeforeUpdate = demandeCalculPaieRepository.findAll().size();

        // Update the demandeCalculPaie using partial update
        DemandeCalculPaie partialUpdatedDemandeCalculPaie = new DemandeCalculPaie();
        partialUpdatedDemandeCalculPaie.setId(demandeCalculPaie.getId());

        partialUpdatedDemandeCalculPaie
            .code(UPDATED_CODE)
            .lib(UPDATED_LIB)
            .annee(UPDATED_ANNEE)
            .mois(UPDATED_MOIS)
            .dateCalcul(UPDATED_DATE_CALCUL)
            .dateValid(UPDATED_DATE_VALID)
            .datePayroll(UPDATED_DATE_PAYROLL)
            .totalNet(UPDATED_TOTAL_NET)
            .totalNetDevise(UPDATED_TOTAL_NET_DEVISE)
            .tauxChange(UPDATED_TAUX_CHANGE)
            .calculBy(UPDATED_CALCUL_BY)
            .effectBy(UPDATED_EFFECT_BY)
            .dateSituation(UPDATED_DATE_SITUATION)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .util(UPDATED_UTIL)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restDemandeCalculPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDemandeCalculPaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDemandeCalculPaie))
            )
            .andExpect(status().isOk());

        // Validate the DemandeCalculPaie in the database
        List<DemandeCalculPaie> demandeCalculPaieList = demandeCalculPaieRepository.findAll();
        assertThat(demandeCalculPaieList).hasSize(databaseSizeBeforeUpdate);
        DemandeCalculPaie testDemandeCalculPaie = demandeCalculPaieList.get(demandeCalculPaieList.size() - 1);
        assertThat(testDemandeCalculPaie.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDemandeCalculPaie.getLib()).isEqualTo(UPDATED_LIB);
        assertThat(testDemandeCalculPaie.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testDemandeCalculPaie.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testDemandeCalculPaie.getDateCalcul()).isEqualTo(UPDATED_DATE_CALCUL);
        assertThat(testDemandeCalculPaie.getDateValid()).isEqualTo(UPDATED_DATE_VALID);
        assertThat(testDemandeCalculPaie.getDatePayroll()).isEqualTo(UPDATED_DATE_PAYROLL);
        assertThat(testDemandeCalculPaie.getTotalNet()).isEqualTo(UPDATED_TOTAL_NET);
        assertThat(testDemandeCalculPaie.getTotalNetDevise()).isEqualTo(UPDATED_TOTAL_NET_DEVISE);
        assertThat(testDemandeCalculPaie.getTauxChange()).isEqualTo(UPDATED_TAUX_CHANGE);
        assertThat(testDemandeCalculPaie.getCalculBy()).isEqualTo(UPDATED_CALCUL_BY);
        assertThat(testDemandeCalculPaie.getEffectBy()).isEqualTo(UPDATED_EFFECT_BY);
        assertThat(testDemandeCalculPaie.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testDemandeCalculPaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testDemandeCalculPaie.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testDemandeCalculPaie.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDemandeCalculPaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testDemandeCalculPaie.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testDemandeCalculPaie.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDemandeCalculPaie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDemandeCalculPaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingDemandeCalculPaie() throws Exception {
        int databaseSizeBeforeUpdate = demandeCalculPaieRepository.findAll().size();
        demandeCalculPaie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDemandeCalculPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, demandeCalculPaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeCalculPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeCalculPaie in the database
        List<DemandeCalculPaie> demandeCalculPaieList = demandeCalculPaieRepository.findAll();
        assertThat(demandeCalculPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDemandeCalculPaie() throws Exception {
        int databaseSizeBeforeUpdate = demandeCalculPaieRepository.findAll().size();
        demandeCalculPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeCalculPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeCalculPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the DemandeCalculPaie in the database
        List<DemandeCalculPaie> demandeCalculPaieList = demandeCalculPaieRepository.findAll();
        assertThat(demandeCalculPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDemandeCalculPaie() throws Exception {
        int databaseSizeBeforeUpdate = demandeCalculPaieRepository.findAll().size();
        demandeCalculPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDemandeCalculPaieMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(demandeCalculPaie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DemandeCalculPaie in the database
        List<DemandeCalculPaie> demandeCalculPaieList = demandeCalculPaieRepository.findAll();
        assertThat(demandeCalculPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDemandeCalculPaie() throws Exception {
        // Initialize the database
        demandeCalculPaieRepository.saveAndFlush(demandeCalculPaie);

        int databaseSizeBeforeDelete = demandeCalculPaieRepository.findAll().size();

        // Delete the demandeCalculPaie
        restDemandeCalculPaieMockMvc
            .perform(delete(ENTITY_API_URL_ID, demandeCalculPaie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DemandeCalculPaie> demandeCalculPaieList = demandeCalculPaieRepository.findAll();
        assertThat(demandeCalculPaieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
