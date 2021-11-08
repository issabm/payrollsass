package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Employe;
import com.issa.payroll.repository.EmployeRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link EmployeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EmployeResourceIT {

    private static final String DEFAULT_MATRICULE = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_AR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_JF_AR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_JF_AR = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_AR = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_EN = "AAAAAAAAAA";
    private static final String UPDATED_NOM_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_JF_EN = "AAAAAAAAAA";
    private static final String UPDATED_NOM_JF_EN = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM_EN = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PERE_AR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PERE_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PERE_EN = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PERE_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_MERE_AR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MERE_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_MERE_EN = "AAAAAAAAAA";
    private static final String UPDATED_NOM_MERE_EN = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_GP_AR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_GP_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_GP_EN = "AAAAAAAAAA";
    private static final String UPDATED_NOM_GP_EN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_NAISS = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISS = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RIB = "AAAAAAAAAA";
    private static final String UPDATED_RIB = "BBBBBBBBBB";

    private static final String DEFAULT_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_BANQUE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_RIB = "AAAAAAAAAA";
    private static final String UPDATED_DATE_RIB = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_BANQUE = "AAAAAAAAAA";
    private static final String UPDATED_DATE_BANQUE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMG = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMG = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMG_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMG_CONTENT_TYPE = "image/png";

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

    private static final String ENTITY_API_URL = "/api/employes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeMockMvc;

    private Employe employe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employe createEntity(EntityManager em) {
        Employe employe = new Employe()
            .matricule(DEFAULT_MATRICULE)
            .nomAr(DEFAULT_NOM_AR)
            .nomJfAr(DEFAULT_NOM_JF_AR)
            .prenomAr(DEFAULT_PRENOM_AR)
            .nomEn(DEFAULT_NOM_EN)
            .nomJfEn(DEFAULT_NOM_JF_EN)
            .prenomEn(DEFAULT_PRENOM_EN)
            .nomPereAr(DEFAULT_NOM_PERE_AR)
            .nomPereEn(DEFAULT_NOM_PERE_EN)
            .nomMereAr(DEFAULT_NOM_MERE_AR)
            .nomMereEn(DEFAULT_NOM_MERE_EN)
            .nomGpAr(DEFAULT_NOM_GP_AR)
            .nomGpEn(DEFAULT_NOM_GP_EN)
            .dateNaiss(DEFAULT_DATE_NAISS)
            .rib(DEFAULT_RIB)
            .banque(DEFAULT_BANQUE)
            .dateRib(DEFAULT_DATE_RIB)
            .dateBanque(DEFAULT_DATE_BANQUE)
            .img(DEFAULT_IMG)
            .imgContentType(DEFAULT_IMG_CONTENT_TYPE)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .dateModif(DEFAULT_DATE_MODIF)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED);
        return employe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employe createUpdatedEntity(EntityManager em) {
        Employe employe = new Employe()
            .matricule(UPDATED_MATRICULE)
            .nomAr(UPDATED_NOM_AR)
            .nomJfAr(UPDATED_NOM_JF_AR)
            .prenomAr(UPDATED_PRENOM_AR)
            .nomEn(UPDATED_NOM_EN)
            .nomJfEn(UPDATED_NOM_JF_EN)
            .prenomEn(UPDATED_PRENOM_EN)
            .nomPereAr(UPDATED_NOM_PERE_AR)
            .nomPereEn(UPDATED_NOM_PERE_EN)
            .nomMereAr(UPDATED_NOM_MERE_AR)
            .nomMereEn(UPDATED_NOM_MERE_EN)
            .nomGpAr(UPDATED_NOM_GP_AR)
            .nomGpEn(UPDATED_NOM_GP_EN)
            .dateNaiss(UPDATED_DATE_NAISS)
            .rib(UPDATED_RIB)
            .banque(UPDATED_BANQUE)
            .dateRib(UPDATED_DATE_RIB)
            .dateBanque(UPDATED_DATE_BANQUE)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .dateModif(UPDATED_DATE_MODIF)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);
        return employe;
    }

    @BeforeEach
    public void initTest() {
        employe = createEntity(em);
    }

    @Test
    @Transactional
    void createEmploye() throws Exception {
        int databaseSizeBeforeCreate = employeRepository.findAll().size();
        // Create the Employe
        restEmployeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employe)))
            .andExpect(status().isCreated());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeCreate + 1);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testEmploye.getNomAr()).isEqualTo(DEFAULT_NOM_AR);
        assertThat(testEmploye.getNomJfAr()).isEqualTo(DEFAULT_NOM_JF_AR);
        assertThat(testEmploye.getPrenomAr()).isEqualTo(DEFAULT_PRENOM_AR);
        assertThat(testEmploye.getNomEn()).isEqualTo(DEFAULT_NOM_EN);
        assertThat(testEmploye.getNomJfEn()).isEqualTo(DEFAULT_NOM_JF_EN);
        assertThat(testEmploye.getPrenomEn()).isEqualTo(DEFAULT_PRENOM_EN);
        assertThat(testEmploye.getNomPereAr()).isEqualTo(DEFAULT_NOM_PERE_AR);
        assertThat(testEmploye.getNomPereEn()).isEqualTo(DEFAULT_NOM_PERE_EN);
        assertThat(testEmploye.getNomMereAr()).isEqualTo(DEFAULT_NOM_MERE_AR);
        assertThat(testEmploye.getNomMereEn()).isEqualTo(DEFAULT_NOM_MERE_EN);
        assertThat(testEmploye.getNomGpAr()).isEqualTo(DEFAULT_NOM_GP_AR);
        assertThat(testEmploye.getNomGpEn()).isEqualTo(DEFAULT_NOM_GP_EN);
        assertThat(testEmploye.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testEmploye.getRib()).isEqualTo(DEFAULT_RIB);
        assertThat(testEmploye.getBanque()).isEqualTo(DEFAULT_BANQUE);
        assertThat(testEmploye.getDateRib()).isEqualTo(DEFAULT_DATE_RIB);
        assertThat(testEmploye.getDateBanque()).isEqualTo(DEFAULT_DATE_BANQUE);
        assertThat(testEmploye.getImg()).isEqualTo(DEFAULT_IMG);
        assertThat(testEmploye.getImgContentType()).isEqualTo(DEFAULT_IMG_CONTENT_TYPE);
        assertThat(testEmploye.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testEmploye.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testEmploye.getDateModif()).isEqualTo(DEFAULT_DATE_MODIF);
        assertThat(testEmploye.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testEmploye.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testEmploye.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createEmployeWithExistingId() throws Exception {
        // Create the Employe with an existing ID
        employe.setId(1L);

        int databaseSizeBeforeCreate = employeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employe)))
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployes() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get all the employeList
        restEmployeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employe.getId().intValue())))
            .andExpect(jsonPath("$.[*].matricule").value(hasItem(DEFAULT_MATRICULE)))
            .andExpect(jsonPath("$.[*].nomAr").value(hasItem(DEFAULT_NOM_AR)))
            .andExpect(jsonPath("$.[*].nomJfAr").value(hasItem(DEFAULT_NOM_JF_AR)))
            .andExpect(jsonPath("$.[*].prenomAr").value(hasItem(DEFAULT_PRENOM_AR)))
            .andExpect(jsonPath("$.[*].nomEn").value(hasItem(DEFAULT_NOM_EN)))
            .andExpect(jsonPath("$.[*].nomJfEn").value(hasItem(DEFAULT_NOM_JF_EN)))
            .andExpect(jsonPath("$.[*].prenomEn").value(hasItem(DEFAULT_PRENOM_EN)))
            .andExpect(jsonPath("$.[*].nomPereAr").value(hasItem(DEFAULT_NOM_PERE_AR)))
            .andExpect(jsonPath("$.[*].nomPereEn").value(hasItem(DEFAULT_NOM_PERE_EN)))
            .andExpect(jsonPath("$.[*].nomMereAr").value(hasItem(DEFAULT_NOM_MERE_AR)))
            .andExpect(jsonPath("$.[*].nomMereEn").value(hasItem(DEFAULT_NOM_MERE_EN)))
            .andExpect(jsonPath("$.[*].nomGpAr").value(hasItem(DEFAULT_NOM_GP_AR)))
            .andExpect(jsonPath("$.[*].nomGpEn").value(hasItem(DEFAULT_NOM_GP_EN)))
            .andExpect(jsonPath("$.[*].dateNaiss").value(hasItem(DEFAULT_DATE_NAISS.toString())))
            .andExpect(jsonPath("$.[*].rib").value(hasItem(DEFAULT_RIB)))
            .andExpect(jsonPath("$.[*].banque").value(hasItem(DEFAULT_BANQUE)))
            .andExpect(jsonPath("$.[*].dateRib").value(hasItem(DEFAULT_DATE_RIB)))
            .andExpect(jsonPath("$.[*].dateBanque").value(hasItem(DEFAULT_DATE_BANQUE)))
            .andExpect(jsonPath("$.[*].imgContentType").value(hasItem(DEFAULT_IMG_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].img").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMG))))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].dateModif").value(hasItem(sameInstant(DEFAULT_DATE_MODIF))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())));
    }

    @Test
    @Transactional
    void getEmploye() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        // Get the employe
        restEmployeMockMvc
            .perform(get(ENTITY_API_URL_ID, employe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employe.getId().intValue()))
            .andExpect(jsonPath("$.matricule").value(DEFAULT_MATRICULE))
            .andExpect(jsonPath("$.nomAr").value(DEFAULT_NOM_AR))
            .andExpect(jsonPath("$.nomJfAr").value(DEFAULT_NOM_JF_AR))
            .andExpect(jsonPath("$.prenomAr").value(DEFAULT_PRENOM_AR))
            .andExpect(jsonPath("$.nomEn").value(DEFAULT_NOM_EN))
            .andExpect(jsonPath("$.nomJfEn").value(DEFAULT_NOM_JF_EN))
            .andExpect(jsonPath("$.prenomEn").value(DEFAULT_PRENOM_EN))
            .andExpect(jsonPath("$.nomPereAr").value(DEFAULT_NOM_PERE_AR))
            .andExpect(jsonPath("$.nomPereEn").value(DEFAULT_NOM_PERE_EN))
            .andExpect(jsonPath("$.nomMereAr").value(DEFAULT_NOM_MERE_AR))
            .andExpect(jsonPath("$.nomMereEn").value(DEFAULT_NOM_MERE_EN))
            .andExpect(jsonPath("$.nomGpAr").value(DEFAULT_NOM_GP_AR))
            .andExpect(jsonPath("$.nomGpEn").value(DEFAULT_NOM_GP_EN))
            .andExpect(jsonPath("$.dateNaiss").value(DEFAULT_DATE_NAISS.toString()))
            .andExpect(jsonPath("$.rib").value(DEFAULT_RIB))
            .andExpect(jsonPath("$.banque").value(DEFAULT_BANQUE))
            .andExpect(jsonPath("$.dateRib").value(DEFAULT_DATE_RIB))
            .andExpect(jsonPath("$.dateBanque").value(DEFAULT_DATE_BANQUE))
            .andExpect(jsonPath("$.imgContentType").value(DEFAULT_IMG_CONTENT_TYPE))
            .andExpect(jsonPath("$.img").value(Base64Utils.encodeToString(DEFAULT_IMG)))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.dateModif").value(sameInstant(DEFAULT_DATE_MODIF)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingEmploye() throws Exception {
        // Get the employe
        restEmployeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmploye() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        int databaseSizeBeforeUpdate = employeRepository.findAll().size();

        // Update the employe
        Employe updatedEmploye = employeRepository.findById(employe.getId()).get();
        // Disconnect from session so that the updates on updatedEmploye are not directly saved in db
        em.detach(updatedEmploye);
        updatedEmploye
            .matricule(UPDATED_MATRICULE)
            .nomAr(UPDATED_NOM_AR)
            .nomJfAr(UPDATED_NOM_JF_AR)
            .prenomAr(UPDATED_PRENOM_AR)
            .nomEn(UPDATED_NOM_EN)
            .nomJfEn(UPDATED_NOM_JF_EN)
            .prenomEn(UPDATED_PRENOM_EN)
            .nomPereAr(UPDATED_NOM_PERE_AR)
            .nomPereEn(UPDATED_NOM_PERE_EN)
            .nomMereAr(UPDATED_NOM_MERE_AR)
            .nomMereEn(UPDATED_NOM_MERE_EN)
            .nomGpAr(UPDATED_NOM_GP_AR)
            .nomGpEn(UPDATED_NOM_GP_EN)
            .dateNaiss(UPDATED_DATE_NAISS)
            .rib(UPDATED_RIB)
            .banque(UPDATED_BANQUE)
            .dateRib(UPDATED_DATE_RIB)
            .dateBanque(UPDATED_DATE_BANQUE)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .dateModif(UPDATED_DATE_MODIF)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restEmployeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEmploye.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEmploye))
            )
            .andExpect(status().isOk());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEmploye.getNomAr()).isEqualTo(UPDATED_NOM_AR);
        assertThat(testEmploye.getNomJfAr()).isEqualTo(UPDATED_NOM_JF_AR);
        assertThat(testEmploye.getPrenomAr()).isEqualTo(UPDATED_PRENOM_AR);
        assertThat(testEmploye.getNomEn()).isEqualTo(UPDATED_NOM_EN);
        assertThat(testEmploye.getNomJfEn()).isEqualTo(UPDATED_NOM_JF_EN);
        assertThat(testEmploye.getPrenomEn()).isEqualTo(UPDATED_PRENOM_EN);
        assertThat(testEmploye.getNomPereAr()).isEqualTo(UPDATED_NOM_PERE_AR);
        assertThat(testEmploye.getNomPereEn()).isEqualTo(UPDATED_NOM_PERE_EN);
        assertThat(testEmploye.getNomMereAr()).isEqualTo(UPDATED_NOM_MERE_AR);
        assertThat(testEmploye.getNomMereEn()).isEqualTo(UPDATED_NOM_MERE_EN);
        assertThat(testEmploye.getNomGpAr()).isEqualTo(UPDATED_NOM_GP_AR);
        assertThat(testEmploye.getNomGpEn()).isEqualTo(UPDATED_NOM_GP_EN);
        assertThat(testEmploye.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testEmploye.getRib()).isEqualTo(UPDATED_RIB);
        assertThat(testEmploye.getBanque()).isEqualTo(UPDATED_BANQUE);
        assertThat(testEmploye.getDateRib()).isEqualTo(UPDATED_DATE_RIB);
        assertThat(testEmploye.getDateBanque()).isEqualTo(UPDATED_DATE_BANQUE);
        assertThat(testEmploye.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testEmploye.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testEmploye.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEmploye.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEmploye.getDateModif()).isEqualTo(UPDATED_DATE_MODIF);
        assertThat(testEmploye.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEmploye.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEmploye.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeWithPatch() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        int databaseSizeBeforeUpdate = employeRepository.findAll().size();

        // Update the employe using partial update
        Employe partialUpdatedEmploye = new Employe();
        partialUpdatedEmploye.setId(employe.getId());

        partialUpdatedEmploye
            .prenomEn(UPDATED_PRENOM_EN)
            .nomPereAr(UPDATED_NOM_PERE_AR)
            .nomPereEn(UPDATED_NOM_PERE_EN)
            .nomGpAr(UPDATED_NOM_GP_AR)
            .nomGpEn(UPDATED_NOM_GP_EN)
            .dateRib(UPDATED_DATE_RIB)
            .dateBanque(UPDATED_DATE_BANQUE)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .dateModif(UPDATED_DATE_MODIF)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploye.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploye))
            )
            .andExpect(status().isOk());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getMatricule()).isEqualTo(DEFAULT_MATRICULE);
        assertThat(testEmploye.getNomAr()).isEqualTo(DEFAULT_NOM_AR);
        assertThat(testEmploye.getNomJfAr()).isEqualTo(DEFAULT_NOM_JF_AR);
        assertThat(testEmploye.getPrenomAr()).isEqualTo(DEFAULT_PRENOM_AR);
        assertThat(testEmploye.getNomEn()).isEqualTo(DEFAULT_NOM_EN);
        assertThat(testEmploye.getNomJfEn()).isEqualTo(DEFAULT_NOM_JF_EN);
        assertThat(testEmploye.getPrenomEn()).isEqualTo(UPDATED_PRENOM_EN);
        assertThat(testEmploye.getNomPereAr()).isEqualTo(UPDATED_NOM_PERE_AR);
        assertThat(testEmploye.getNomPereEn()).isEqualTo(UPDATED_NOM_PERE_EN);
        assertThat(testEmploye.getNomMereAr()).isEqualTo(DEFAULT_NOM_MERE_AR);
        assertThat(testEmploye.getNomMereEn()).isEqualTo(DEFAULT_NOM_MERE_EN);
        assertThat(testEmploye.getNomGpAr()).isEqualTo(UPDATED_NOM_GP_AR);
        assertThat(testEmploye.getNomGpEn()).isEqualTo(UPDATED_NOM_GP_EN);
        assertThat(testEmploye.getDateNaiss()).isEqualTo(DEFAULT_DATE_NAISS);
        assertThat(testEmploye.getRib()).isEqualTo(DEFAULT_RIB);
        assertThat(testEmploye.getBanque()).isEqualTo(DEFAULT_BANQUE);
        assertThat(testEmploye.getDateRib()).isEqualTo(UPDATED_DATE_RIB);
        assertThat(testEmploye.getDateBanque()).isEqualTo(UPDATED_DATE_BANQUE);
        assertThat(testEmploye.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testEmploye.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testEmploye.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testEmploye.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testEmploye.getDateModif()).isEqualTo(UPDATED_DATE_MODIF);
        assertThat(testEmploye.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEmploye.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEmploye.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateEmployeWithPatch() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        int databaseSizeBeforeUpdate = employeRepository.findAll().size();

        // Update the employe using partial update
        Employe partialUpdatedEmploye = new Employe();
        partialUpdatedEmploye.setId(employe.getId());

        partialUpdatedEmploye
            .matricule(UPDATED_MATRICULE)
            .nomAr(UPDATED_NOM_AR)
            .nomJfAr(UPDATED_NOM_JF_AR)
            .prenomAr(UPDATED_PRENOM_AR)
            .nomEn(UPDATED_NOM_EN)
            .nomJfEn(UPDATED_NOM_JF_EN)
            .prenomEn(UPDATED_PRENOM_EN)
            .nomPereAr(UPDATED_NOM_PERE_AR)
            .nomPereEn(UPDATED_NOM_PERE_EN)
            .nomMereAr(UPDATED_NOM_MERE_AR)
            .nomMereEn(UPDATED_NOM_MERE_EN)
            .nomGpAr(UPDATED_NOM_GP_AR)
            .nomGpEn(UPDATED_NOM_GP_EN)
            .dateNaiss(UPDATED_DATE_NAISS)
            .rib(UPDATED_RIB)
            .banque(UPDATED_BANQUE)
            .dateRib(UPDATED_DATE_RIB)
            .dateBanque(UPDATED_DATE_BANQUE)
            .img(UPDATED_IMG)
            .imgContentType(UPDATED_IMG_CONTENT_TYPE)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .dateModif(UPDATED_DATE_MODIF)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmploye.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmploye))
            )
            .andExpect(status().isOk());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
        Employe testEmploye = employeList.get(employeList.size() - 1);
        assertThat(testEmploye.getMatricule()).isEqualTo(UPDATED_MATRICULE);
        assertThat(testEmploye.getNomAr()).isEqualTo(UPDATED_NOM_AR);
        assertThat(testEmploye.getNomJfAr()).isEqualTo(UPDATED_NOM_JF_AR);
        assertThat(testEmploye.getPrenomAr()).isEqualTo(UPDATED_PRENOM_AR);
        assertThat(testEmploye.getNomEn()).isEqualTo(UPDATED_NOM_EN);
        assertThat(testEmploye.getNomJfEn()).isEqualTo(UPDATED_NOM_JF_EN);
        assertThat(testEmploye.getPrenomEn()).isEqualTo(UPDATED_PRENOM_EN);
        assertThat(testEmploye.getNomPereAr()).isEqualTo(UPDATED_NOM_PERE_AR);
        assertThat(testEmploye.getNomPereEn()).isEqualTo(UPDATED_NOM_PERE_EN);
        assertThat(testEmploye.getNomMereAr()).isEqualTo(UPDATED_NOM_MERE_AR);
        assertThat(testEmploye.getNomMereEn()).isEqualTo(UPDATED_NOM_MERE_EN);
        assertThat(testEmploye.getNomGpAr()).isEqualTo(UPDATED_NOM_GP_AR);
        assertThat(testEmploye.getNomGpEn()).isEqualTo(UPDATED_NOM_GP_EN);
        assertThat(testEmploye.getDateNaiss()).isEqualTo(UPDATED_DATE_NAISS);
        assertThat(testEmploye.getRib()).isEqualTo(UPDATED_RIB);
        assertThat(testEmploye.getBanque()).isEqualTo(UPDATED_BANQUE);
        assertThat(testEmploye.getDateRib()).isEqualTo(UPDATED_DATE_RIB);
        assertThat(testEmploye.getDateBanque()).isEqualTo(UPDATED_DATE_BANQUE);
        assertThat(testEmploye.getImg()).isEqualTo(UPDATED_IMG);
        assertThat(testEmploye.getImgContentType()).isEqualTo(UPDATED_IMG_CONTENT_TYPE);
        assertThat(testEmploye.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEmploye.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEmploye.getDateModif()).isEqualTo(UPDATED_DATE_MODIF);
        assertThat(testEmploye.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testEmploye.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testEmploye.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmploye() throws Exception {
        int databaseSizeBeforeUpdate = employeRepository.findAll().size();
        employe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employe in the database
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmploye() throws Exception {
        // Initialize the database
        employeRepository.saveAndFlush(employe);

        int databaseSizeBeforeDelete = employeRepository.findAll().size();

        // Delete the employe
        restEmployeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employe> employeList = employeRepository.findAll();
        assertThat(employeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
