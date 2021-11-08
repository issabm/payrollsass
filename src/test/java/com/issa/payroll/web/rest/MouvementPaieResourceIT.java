package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.MouvementPaie;
import com.issa.payroll.repository.MouvementPaieRepository;
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
 * Integration tests for the {@link MouvementPaieResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class MouvementPaieResourceIT {

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

    private static final String ENTITY_API_URL = "/api/mouvement-paies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MouvementPaieRepository mouvementPaieRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMouvementPaieMockMvc;

    private MouvementPaie mouvementPaie;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MouvementPaie createEntity(EntityManager em) {
        MouvementPaie mouvementPaie = new MouvementPaie()
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
        return mouvementPaie;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MouvementPaie createUpdatedEntity(EntityManager em) {
        MouvementPaie mouvementPaie = new MouvementPaie()
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
        return mouvementPaie;
    }

    @BeforeEach
    public void initTest() {
        mouvementPaie = createEntity(em);
    }

    @Test
    @Transactional
    void createMouvementPaie() throws Exception {
        int databaseSizeBeforeCreate = mouvementPaieRepository.findAll().size();
        // Create the MouvementPaie
        restMouvementPaieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mouvementPaie)))
            .andExpect(status().isCreated());

        // Validate the MouvementPaie in the database
        List<MouvementPaie> mouvementPaieList = mouvementPaieRepository.findAll();
        assertThat(mouvementPaieList).hasSize(databaseSizeBeforeCreate + 1);
        MouvementPaie testMouvementPaie = mouvementPaieList.get(mouvementPaieList.size() - 1);
        assertThat(testMouvementPaie.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMouvementPaie.getLib()).isEqualTo(DEFAULT_LIB);
        assertThat(testMouvementPaie.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testMouvementPaie.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testMouvementPaie.getDateCalcul()).isEqualTo(DEFAULT_DATE_CALCUL);
        assertThat(testMouvementPaie.getDateValid()).isEqualTo(DEFAULT_DATE_VALID);
        assertThat(testMouvementPaie.getDatePayroll()).isEqualTo(DEFAULT_DATE_PAYROLL);
        assertThat(testMouvementPaie.getTotalNet()).isEqualTo(DEFAULT_TOTAL_NET);
        assertThat(testMouvementPaie.getTotalNetDevise()).isEqualTo(DEFAULT_TOTAL_NET_DEVISE);
        assertThat(testMouvementPaie.getTauxChange()).isEqualTo(DEFAULT_TAUX_CHANGE);
        assertThat(testMouvementPaie.getCalculBy()).isEqualTo(DEFAULT_CALCUL_BY);
        assertThat(testMouvementPaie.getEffectBy()).isEqualTo(DEFAULT_EFFECT_BY);
        assertThat(testMouvementPaie.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testMouvementPaie.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testMouvementPaie.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMouvementPaie.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMouvementPaie.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testMouvementPaie.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testMouvementPaie.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMouvementPaie.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMouvementPaie.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createMouvementPaieWithExistingId() throws Exception {
        // Create the MouvementPaie with an existing ID
        mouvementPaie.setId(1L);

        int databaseSizeBeforeCreate = mouvementPaieRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMouvementPaieMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mouvementPaie)))
            .andExpect(status().isBadRequest());

        // Validate the MouvementPaie in the database
        List<MouvementPaie> mouvementPaieList = mouvementPaieRepository.findAll();
        assertThat(mouvementPaieList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMouvementPaies() throws Exception {
        // Initialize the database
        mouvementPaieRepository.saveAndFlush(mouvementPaie);

        // Get all the mouvementPaieList
        restMouvementPaieMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mouvementPaie.getId().intValue())))
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
    void getMouvementPaie() throws Exception {
        // Initialize the database
        mouvementPaieRepository.saveAndFlush(mouvementPaie);

        // Get the mouvementPaie
        restMouvementPaieMockMvc
            .perform(get(ENTITY_API_URL_ID, mouvementPaie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mouvementPaie.getId().intValue()))
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
    void getNonExistingMouvementPaie() throws Exception {
        // Get the mouvementPaie
        restMouvementPaieMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMouvementPaie() throws Exception {
        // Initialize the database
        mouvementPaieRepository.saveAndFlush(mouvementPaie);

        int databaseSizeBeforeUpdate = mouvementPaieRepository.findAll().size();

        // Update the mouvementPaie
        MouvementPaie updatedMouvementPaie = mouvementPaieRepository.findById(mouvementPaie.getId()).get();
        // Disconnect from session so that the updates on updatedMouvementPaie are not directly saved in db
        em.detach(updatedMouvementPaie);
        updatedMouvementPaie
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

        restMouvementPaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMouvementPaie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMouvementPaie))
            )
            .andExpect(status().isOk());

        // Validate the MouvementPaie in the database
        List<MouvementPaie> mouvementPaieList = mouvementPaieRepository.findAll();
        assertThat(mouvementPaieList).hasSize(databaseSizeBeforeUpdate);
        MouvementPaie testMouvementPaie = mouvementPaieList.get(mouvementPaieList.size() - 1);
        assertThat(testMouvementPaie.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMouvementPaie.getLib()).isEqualTo(UPDATED_LIB);
        assertThat(testMouvementPaie.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testMouvementPaie.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testMouvementPaie.getDateCalcul()).isEqualTo(UPDATED_DATE_CALCUL);
        assertThat(testMouvementPaie.getDateValid()).isEqualTo(UPDATED_DATE_VALID);
        assertThat(testMouvementPaie.getDatePayroll()).isEqualTo(UPDATED_DATE_PAYROLL);
        assertThat(testMouvementPaie.getTotalNet()).isEqualTo(UPDATED_TOTAL_NET);
        assertThat(testMouvementPaie.getTotalNetDevise()).isEqualTo(UPDATED_TOTAL_NET_DEVISE);
        assertThat(testMouvementPaie.getTauxChange()).isEqualTo(UPDATED_TAUX_CHANGE);
        assertThat(testMouvementPaie.getCalculBy()).isEqualTo(UPDATED_CALCUL_BY);
        assertThat(testMouvementPaie.getEffectBy()).isEqualTo(UPDATED_EFFECT_BY);
        assertThat(testMouvementPaie.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testMouvementPaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testMouvementPaie.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMouvementPaie.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMouvementPaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testMouvementPaie.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testMouvementPaie.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMouvementPaie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMouvementPaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingMouvementPaie() throws Exception {
        int databaseSizeBeforeUpdate = mouvementPaieRepository.findAll().size();
        mouvementPaie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMouvementPaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mouvementPaie.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mouvementPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the MouvementPaie in the database
        List<MouvementPaie> mouvementPaieList = mouvementPaieRepository.findAll();
        assertThat(mouvementPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMouvementPaie() throws Exception {
        int databaseSizeBeforeUpdate = mouvementPaieRepository.findAll().size();
        mouvementPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMouvementPaieMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mouvementPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the MouvementPaie in the database
        List<MouvementPaie> mouvementPaieList = mouvementPaieRepository.findAll();
        assertThat(mouvementPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMouvementPaie() throws Exception {
        int databaseSizeBeforeUpdate = mouvementPaieRepository.findAll().size();
        mouvementPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMouvementPaieMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mouvementPaie)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MouvementPaie in the database
        List<MouvementPaie> mouvementPaieList = mouvementPaieRepository.findAll();
        assertThat(mouvementPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMouvementPaieWithPatch() throws Exception {
        // Initialize the database
        mouvementPaieRepository.saveAndFlush(mouvementPaie);

        int databaseSizeBeforeUpdate = mouvementPaieRepository.findAll().size();

        // Update the mouvementPaie using partial update
        MouvementPaie partialUpdatedMouvementPaie = new MouvementPaie();
        partialUpdatedMouvementPaie.setId(mouvementPaie.getId());

        partialUpdatedMouvementPaie
            .code(UPDATED_CODE)
            .lib(UPDATED_LIB)
            .annee(UPDATED_ANNEE)
            .dateCalcul(UPDATED_DATE_CALCUL)
            .dateValid(UPDATED_DATE_VALID)
            .datePayroll(UPDATED_DATE_PAYROLL)
            .tauxChange(UPDATED_TAUX_CHANGE)
            .calculBy(UPDATED_CALCUL_BY)
            .createdBy(UPDATED_CREATED_BY)
            .util(UPDATED_UTIL)
            .createdDate(UPDATED_CREATED_DATE);

        restMouvementPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMouvementPaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMouvementPaie))
            )
            .andExpect(status().isOk());

        // Validate the MouvementPaie in the database
        List<MouvementPaie> mouvementPaieList = mouvementPaieRepository.findAll();
        assertThat(mouvementPaieList).hasSize(databaseSizeBeforeUpdate);
        MouvementPaie testMouvementPaie = mouvementPaieList.get(mouvementPaieList.size() - 1);
        assertThat(testMouvementPaie.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMouvementPaie.getLib()).isEqualTo(UPDATED_LIB);
        assertThat(testMouvementPaie.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testMouvementPaie.getMois()).isEqualTo(DEFAULT_MOIS);
        assertThat(testMouvementPaie.getDateCalcul()).isEqualTo(UPDATED_DATE_CALCUL);
        assertThat(testMouvementPaie.getDateValid()).isEqualTo(UPDATED_DATE_VALID);
        assertThat(testMouvementPaie.getDatePayroll()).isEqualTo(UPDATED_DATE_PAYROLL);
        assertThat(testMouvementPaie.getTotalNet()).isEqualTo(DEFAULT_TOTAL_NET);
        assertThat(testMouvementPaie.getTotalNetDevise()).isEqualTo(DEFAULT_TOTAL_NET_DEVISE);
        assertThat(testMouvementPaie.getTauxChange()).isEqualTo(UPDATED_TAUX_CHANGE);
        assertThat(testMouvementPaie.getCalculBy()).isEqualTo(UPDATED_CALCUL_BY);
        assertThat(testMouvementPaie.getEffectBy()).isEqualTo(DEFAULT_EFFECT_BY);
        assertThat(testMouvementPaie.getDateSituation()).isEqualTo(DEFAULT_DATE_SITUATION);
        assertThat(testMouvementPaie.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testMouvementPaie.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMouvementPaie.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMouvementPaie.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testMouvementPaie.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testMouvementPaie.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testMouvementPaie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMouvementPaie.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateMouvementPaieWithPatch() throws Exception {
        // Initialize the database
        mouvementPaieRepository.saveAndFlush(mouvementPaie);

        int databaseSizeBeforeUpdate = mouvementPaieRepository.findAll().size();

        // Update the mouvementPaie using partial update
        MouvementPaie partialUpdatedMouvementPaie = new MouvementPaie();
        partialUpdatedMouvementPaie.setId(mouvementPaie.getId());

        partialUpdatedMouvementPaie
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

        restMouvementPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMouvementPaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMouvementPaie))
            )
            .andExpect(status().isOk());

        // Validate the MouvementPaie in the database
        List<MouvementPaie> mouvementPaieList = mouvementPaieRepository.findAll();
        assertThat(mouvementPaieList).hasSize(databaseSizeBeforeUpdate);
        MouvementPaie testMouvementPaie = mouvementPaieList.get(mouvementPaieList.size() - 1);
        assertThat(testMouvementPaie.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMouvementPaie.getLib()).isEqualTo(UPDATED_LIB);
        assertThat(testMouvementPaie.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testMouvementPaie.getMois()).isEqualTo(UPDATED_MOIS);
        assertThat(testMouvementPaie.getDateCalcul()).isEqualTo(UPDATED_DATE_CALCUL);
        assertThat(testMouvementPaie.getDateValid()).isEqualTo(UPDATED_DATE_VALID);
        assertThat(testMouvementPaie.getDatePayroll()).isEqualTo(UPDATED_DATE_PAYROLL);
        assertThat(testMouvementPaie.getTotalNet()).isEqualTo(UPDATED_TOTAL_NET);
        assertThat(testMouvementPaie.getTotalNetDevise()).isEqualTo(UPDATED_TOTAL_NET_DEVISE);
        assertThat(testMouvementPaie.getTauxChange()).isEqualTo(UPDATED_TAUX_CHANGE);
        assertThat(testMouvementPaie.getCalculBy()).isEqualTo(UPDATED_CALCUL_BY);
        assertThat(testMouvementPaie.getEffectBy()).isEqualTo(UPDATED_EFFECT_BY);
        assertThat(testMouvementPaie.getDateSituation()).isEqualTo(UPDATED_DATE_SITUATION);
        assertThat(testMouvementPaie.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testMouvementPaie.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMouvementPaie.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMouvementPaie.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testMouvementPaie.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testMouvementPaie.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testMouvementPaie.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMouvementPaie.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingMouvementPaie() throws Exception {
        int databaseSizeBeforeUpdate = mouvementPaieRepository.findAll().size();
        mouvementPaie.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMouvementPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mouvementPaie.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mouvementPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the MouvementPaie in the database
        List<MouvementPaie> mouvementPaieList = mouvementPaieRepository.findAll();
        assertThat(mouvementPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMouvementPaie() throws Exception {
        int databaseSizeBeforeUpdate = mouvementPaieRepository.findAll().size();
        mouvementPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMouvementPaieMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mouvementPaie))
            )
            .andExpect(status().isBadRequest());

        // Validate the MouvementPaie in the database
        List<MouvementPaie> mouvementPaieList = mouvementPaieRepository.findAll();
        assertThat(mouvementPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMouvementPaie() throws Exception {
        int databaseSizeBeforeUpdate = mouvementPaieRepository.findAll().size();
        mouvementPaie.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMouvementPaieMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(mouvementPaie))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MouvementPaie in the database
        List<MouvementPaie> mouvementPaieList = mouvementPaieRepository.findAll();
        assertThat(mouvementPaieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMouvementPaie() throws Exception {
        // Initialize the database
        mouvementPaieRepository.saveAndFlush(mouvementPaie);

        int databaseSizeBeforeDelete = mouvementPaieRepository.findAll().size();

        // Delete the mouvementPaie
        restMouvementPaieMockMvc
            .perform(delete(ENTITY_API_URL_ID, mouvementPaie.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MouvementPaie> mouvementPaieList = mouvementPaieRepository.findAll();
        assertThat(mouvementPaieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
