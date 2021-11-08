package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Entreprise;
import com.issa.payroll.repository.EntrepriseRepository;
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
 * Integration tests for the {@link EntrepriseResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EntrepriseResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MAT_FISCAL = "AAAAAAAAAA";
    private static final String UPDATED_MAT_FISCAL = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRE_COMMERCE = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRE_COMMERCE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COMMERCE_AR = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COMMERCE_AR = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_COMMERCE_EN = "AAAAAAAAAA";
    private static final String UPDATED_NOM_COMMERCE_EN = "BBBBBBBBBB";

    private static final String DEFAULT_RAISON_SOCIAL_AR = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_SOCIAL_AR = "BBBBBBBBBB";

    private static final String DEFAULT_RAISON_SOCIAL_EN = "AAAAAAAAAA";
    private static final String UPDATED_RAISON_SOCIAL_EN = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESSE_AR = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSE_AR = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESSE_EN = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSE_EN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/entreprises";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntrepriseMockMvc;

    private Entreprise entreprise;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entreprise createEntity(EntityManager em) {
        Entreprise entreprise = new Entreprise()
            .code(DEFAULT_CODE)
            .matFiscal(DEFAULT_MAT_FISCAL)
            .registreCommerce(DEFAULT_REGISTRE_COMMERCE)
            .nomCommerceAr(DEFAULT_NOM_COMMERCE_AR)
            .nomCommerceEn(DEFAULT_NOM_COMMERCE_EN)
            .raisonSocialAr(DEFAULT_RAISON_SOCIAL_AR)
            .raisonSocialEn(DEFAULT_RAISON_SOCIAL_EN)
            .addresseAr(DEFAULT_ADDRESSE_AR)
            .addresseEn(DEFAULT_ADDRESSE_EN)
            .codePostal(DEFAULT_CODE_POSTAL)
            .tel(DEFAULT_TEL)
            .email(DEFAULT_EMAIL)
            .fax(DEFAULT_FAX)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY);
        return entreprise;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entreprise createUpdatedEntity(EntityManager em) {
        Entreprise entreprise = new Entreprise()
            .code(UPDATED_CODE)
            .matFiscal(UPDATED_MAT_FISCAL)
            .registreCommerce(UPDATED_REGISTRE_COMMERCE)
            .nomCommerceAr(UPDATED_NOM_COMMERCE_AR)
            .nomCommerceEn(UPDATED_NOM_COMMERCE_EN)
            .raisonSocialAr(UPDATED_RAISON_SOCIAL_AR)
            .raisonSocialEn(UPDATED_RAISON_SOCIAL_EN)
            .addresseAr(UPDATED_ADDRESSE_AR)
            .addresseEn(UPDATED_ADDRESSE_EN)
            .codePostal(UPDATED_CODE_POSTAL)
            .tel(UPDATED_TEL)
            .email(UPDATED_EMAIL)
            .fax(UPDATED_FAX)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY);
        return entreprise;
    }

    @BeforeEach
    public void initTest() {
        entreprise = createEntity(em);
    }

    @Test
    @Transactional
    void createEntreprise() throws Exception {
        int databaseSizeBeforeCreate = entrepriseRepository.findAll().size();
        // Create the Entreprise
        restEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entreprise)))
            .andExpect(status().isCreated());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeCreate + 1);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testEntreprise.getMatFiscal()).isEqualTo(DEFAULT_MAT_FISCAL);
        assertThat(testEntreprise.getRegistreCommerce()).isEqualTo(DEFAULT_REGISTRE_COMMERCE);
        assertThat(testEntreprise.getNomCommerceAr()).isEqualTo(DEFAULT_NOM_COMMERCE_AR);
        assertThat(testEntreprise.getNomCommerceEn()).isEqualTo(DEFAULT_NOM_COMMERCE_EN);
        assertThat(testEntreprise.getRaisonSocialAr()).isEqualTo(DEFAULT_RAISON_SOCIAL_AR);
        assertThat(testEntreprise.getRaisonSocialEn()).isEqualTo(DEFAULT_RAISON_SOCIAL_EN);
        assertThat(testEntreprise.getAddresseAr()).isEqualTo(DEFAULT_ADDRESSE_AR);
        assertThat(testEntreprise.getAddresseEn()).isEqualTo(DEFAULT_ADDRESSE_EN);
        assertThat(testEntreprise.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testEntreprise.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testEntreprise.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEntreprise.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testEntreprise.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testEntreprise.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testEntreprise.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createEntrepriseWithExistingId() throws Exception {
        // Create the Entreprise with an existing ID
        entreprise.setId(1L);

        int databaseSizeBeforeCreate = entrepriseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntrepriseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entreprise)))
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEntreprises() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        // Get all the entrepriseList
        restEntrepriseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entreprise.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].matFiscal").value(hasItem(DEFAULT_MAT_FISCAL)))
            .andExpect(jsonPath("$.[*].registreCommerce").value(hasItem(DEFAULT_REGISTRE_COMMERCE)))
            .andExpect(jsonPath("$.[*].nomCommerceAr").value(hasItem(DEFAULT_NOM_COMMERCE_AR)))
            .andExpect(jsonPath("$.[*].nomCommerceEn").value(hasItem(DEFAULT_NOM_COMMERCE_EN)))
            .andExpect(jsonPath("$.[*].raisonSocialAr").value(hasItem(DEFAULT_RAISON_SOCIAL_AR)))
            .andExpect(jsonPath("$.[*].raisonSocialEn").value(hasItem(DEFAULT_RAISON_SOCIAL_EN)))
            .andExpect(jsonPath("$.[*].addresseAr").value(hasItem(DEFAULT_ADDRESSE_AR)))
            .andExpect(jsonPath("$.[*].addresseEn").value(hasItem(DEFAULT_ADDRESSE_EN)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].dateop").value(hasItem(sameInstant(DEFAULT_DATEOP))))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        // Get the entreprise
        restEntrepriseMockMvc
            .perform(get(ENTITY_API_URL_ID, entreprise.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entreprise.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.matFiscal").value(DEFAULT_MAT_FISCAL))
            .andExpect(jsonPath("$.registreCommerce").value(DEFAULT_REGISTRE_COMMERCE))
            .andExpect(jsonPath("$.nomCommerceAr").value(DEFAULT_NOM_COMMERCE_AR))
            .andExpect(jsonPath("$.nomCommerceEn").value(DEFAULT_NOM_COMMERCE_EN))
            .andExpect(jsonPath("$.raisonSocialAr").value(DEFAULT_RAISON_SOCIAL_AR))
            .andExpect(jsonPath("$.raisonSocialEn").value(DEFAULT_RAISON_SOCIAL_EN))
            .andExpect(jsonPath("$.addresseAr").value(DEFAULT_ADDRESSE_AR))
            .andExpect(jsonPath("$.addresseEn").value(DEFAULT_ADDRESSE_EN))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.dateop").value(sameInstant(DEFAULT_DATEOP)))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getNonExistingEntreprise() throws Exception {
        // Get the entreprise
        restEntrepriseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise
        Entreprise updatedEntreprise = entrepriseRepository.findById(entreprise.getId()).get();
        // Disconnect from session so that the updates on updatedEntreprise are not directly saved in db
        em.detach(updatedEntreprise);
        updatedEntreprise
            .code(UPDATED_CODE)
            .matFiscal(UPDATED_MAT_FISCAL)
            .registreCommerce(UPDATED_REGISTRE_COMMERCE)
            .nomCommerceAr(UPDATED_NOM_COMMERCE_AR)
            .nomCommerceEn(UPDATED_NOM_COMMERCE_EN)
            .raisonSocialAr(UPDATED_RAISON_SOCIAL_AR)
            .raisonSocialEn(UPDATED_RAISON_SOCIAL_EN)
            .addresseAr(UPDATED_ADDRESSE_AR)
            .addresseEn(UPDATED_ADDRESSE_EN)
            .codePostal(UPDATED_CODE_POSTAL)
            .tel(UPDATED_TEL)
            .email(UPDATED_EMAIL)
            .fax(UPDATED_FAX)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEntreprise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEntreprise))
            )
            .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEntreprise.getMatFiscal()).isEqualTo(UPDATED_MAT_FISCAL);
        assertThat(testEntreprise.getRegistreCommerce()).isEqualTo(UPDATED_REGISTRE_COMMERCE);
        assertThat(testEntreprise.getNomCommerceAr()).isEqualTo(UPDATED_NOM_COMMERCE_AR);
        assertThat(testEntreprise.getNomCommerceEn()).isEqualTo(UPDATED_NOM_COMMERCE_EN);
        assertThat(testEntreprise.getRaisonSocialAr()).isEqualTo(UPDATED_RAISON_SOCIAL_AR);
        assertThat(testEntreprise.getRaisonSocialEn()).isEqualTo(UPDATED_RAISON_SOCIAL_EN);
        assertThat(testEntreprise.getAddresseAr()).isEqualTo(UPDATED_ADDRESSE_AR);
        assertThat(testEntreprise.getAddresseEn()).isEqualTo(UPDATED_ADDRESSE_EN);
        assertThat(testEntreprise.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testEntreprise.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testEntreprise.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEntreprise.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testEntreprise.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEntreprise.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEntreprise.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entreprise.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entreprise)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntrepriseWithPatch() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise using partial update
        Entreprise partialUpdatedEntreprise = new Entreprise();
        partialUpdatedEntreprise.setId(entreprise.getId());

        partialUpdatedEntreprise
            .code(UPDATED_CODE)
            .matFiscal(UPDATED_MAT_FISCAL)
            .registreCommerce(UPDATED_REGISTRE_COMMERCE)
            .raisonSocialEn(UPDATED_RAISON_SOCIAL_EN)
            .codePostal(UPDATED_CODE_POSTAL)
            .tel(UPDATED_TEL)
            .email(UPDATED_EMAIL)
            .fax(UPDATED_FAX)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntreprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntreprise))
            )
            .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEntreprise.getMatFiscal()).isEqualTo(UPDATED_MAT_FISCAL);
        assertThat(testEntreprise.getRegistreCommerce()).isEqualTo(UPDATED_REGISTRE_COMMERCE);
        assertThat(testEntreprise.getNomCommerceAr()).isEqualTo(DEFAULT_NOM_COMMERCE_AR);
        assertThat(testEntreprise.getNomCommerceEn()).isEqualTo(DEFAULT_NOM_COMMERCE_EN);
        assertThat(testEntreprise.getRaisonSocialAr()).isEqualTo(DEFAULT_RAISON_SOCIAL_AR);
        assertThat(testEntreprise.getRaisonSocialEn()).isEqualTo(UPDATED_RAISON_SOCIAL_EN);
        assertThat(testEntreprise.getAddresseAr()).isEqualTo(DEFAULT_ADDRESSE_AR);
        assertThat(testEntreprise.getAddresseEn()).isEqualTo(DEFAULT_ADDRESSE_EN);
        assertThat(testEntreprise.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testEntreprise.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testEntreprise.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEntreprise.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testEntreprise.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testEntreprise.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testEntreprise.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateEntrepriseWithPatch() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();

        // Update the entreprise using partial update
        Entreprise partialUpdatedEntreprise = new Entreprise();
        partialUpdatedEntreprise.setId(entreprise.getId());

        partialUpdatedEntreprise
            .code(UPDATED_CODE)
            .matFiscal(UPDATED_MAT_FISCAL)
            .registreCommerce(UPDATED_REGISTRE_COMMERCE)
            .nomCommerceAr(UPDATED_NOM_COMMERCE_AR)
            .nomCommerceEn(UPDATED_NOM_COMMERCE_EN)
            .raisonSocialAr(UPDATED_RAISON_SOCIAL_AR)
            .raisonSocialEn(UPDATED_RAISON_SOCIAL_EN)
            .addresseAr(UPDATED_ADDRESSE_AR)
            .addresseEn(UPDATED_ADDRESSE_EN)
            .codePostal(UPDATED_CODE_POSTAL)
            .tel(UPDATED_TEL)
            .email(UPDATED_EMAIL)
            .fax(UPDATED_FAX)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY);

        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntreprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntreprise))
            )
            .andExpect(status().isOk());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
        Entreprise testEntreprise = entrepriseList.get(entrepriseList.size() - 1);
        assertThat(testEntreprise.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testEntreprise.getMatFiscal()).isEqualTo(UPDATED_MAT_FISCAL);
        assertThat(testEntreprise.getRegistreCommerce()).isEqualTo(UPDATED_REGISTRE_COMMERCE);
        assertThat(testEntreprise.getNomCommerceAr()).isEqualTo(UPDATED_NOM_COMMERCE_AR);
        assertThat(testEntreprise.getNomCommerceEn()).isEqualTo(UPDATED_NOM_COMMERCE_EN);
        assertThat(testEntreprise.getRaisonSocialAr()).isEqualTo(UPDATED_RAISON_SOCIAL_AR);
        assertThat(testEntreprise.getRaisonSocialEn()).isEqualTo(UPDATED_RAISON_SOCIAL_EN);
        assertThat(testEntreprise.getAddresseAr()).isEqualTo(UPDATED_ADDRESSE_AR);
        assertThat(testEntreprise.getAddresseEn()).isEqualTo(UPDATED_ADDRESSE_EN);
        assertThat(testEntreprise.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testEntreprise.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testEntreprise.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEntreprise.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testEntreprise.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testEntreprise.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testEntreprise.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entreprise.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntreprise() throws Exception {
        int databaseSizeBeforeUpdate = entrepriseRepository.findAll().size();
        entreprise.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrepriseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(entreprise))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entreprise in the database
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntreprise() throws Exception {
        // Initialize the database
        entrepriseRepository.saveAndFlush(entreprise);

        int databaseSizeBeforeDelete = entrepriseRepository.findAll().size();

        // Delete the entreprise
        restEntrepriseMockMvc
            .perform(delete(ENTITY_API_URL_ID, entreprise.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entreprise> entrepriseList = entrepriseRepository.findAll();
        assertThat(entrepriseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
