package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.Groupe;
import com.issa.payroll.repository.GroupeRepository;
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
 * Integration tests for the {@link GroupeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class GroupeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_AR = "AAAAAAAAAA";
    private static final String UPDATED_LIB_AR = "BBBBBBBBBB";

    private static final String DEFAULT_LIB_EN = "AAAAAAAAAA";
    private static final String UPDATED_LIB_EN = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESSE_AR = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSE_AR = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESSE_EN = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESSE_EN = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/groupes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GroupeRepository groupeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGroupeMockMvc;

    private Groupe groupe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Groupe createEntity(EntityManager em) {
        Groupe groupe = new Groupe()
            .code(DEFAULT_CODE)
            .libAr(DEFAULT_LIB_AR)
            .libEn(DEFAULT_LIB_EN)
            .addresseAr(DEFAULT_ADDRESSE_AR)
            .addresseEn(DEFAULT_ADDRESSE_EN)
            .tel(DEFAULT_TEL)
            .email(DEFAULT_EMAIL)
            .fax(DEFAULT_FAX)
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED);
        return groupe;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Groupe createUpdatedEntity(EntityManager em) {
        Groupe groupe = new Groupe()
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .addresseAr(UPDATED_ADDRESSE_AR)
            .addresseEn(UPDATED_ADDRESSE_EN)
            .tel(UPDATED_TEL)
            .email(UPDATED_EMAIL)
            .fax(UPDATED_FAX)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);
        return groupe;
    }

    @BeforeEach
    public void initTest() {
        groupe = createEntity(em);
    }

    @Test
    @Transactional
    void createGroupe() throws Exception {
        int databaseSizeBeforeCreate = groupeRepository.findAll().size();
        // Create the Groupe
        restGroupeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupe)))
            .andExpect(status().isCreated());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeCreate + 1);
        Groupe testGroupe = groupeList.get(groupeList.size() - 1);
        assertThat(testGroupe.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGroupe.getLibAr()).isEqualTo(DEFAULT_LIB_AR);
        assertThat(testGroupe.getLibEn()).isEqualTo(DEFAULT_LIB_EN);
        assertThat(testGroupe.getAddresseAr()).isEqualTo(DEFAULT_ADDRESSE_AR);
        assertThat(testGroupe.getAddresseEn()).isEqualTo(DEFAULT_ADDRESSE_EN);
        assertThat(testGroupe.getTel()).isEqualTo(DEFAULT_TEL);
        assertThat(testGroupe.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testGroupe.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testGroupe.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testGroupe.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testGroupe.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testGroupe.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testGroupe.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createGroupeWithExistingId() throws Exception {
        // Create the Groupe with an existing ID
        groupe.setId(1L);

        int databaseSizeBeforeCreate = groupeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGroupeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupe)))
            .andExpect(status().isBadRequest());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllGroupes() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        // Get all the groupeList
        restGroupeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(groupe.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libAr").value(hasItem(DEFAULT_LIB_AR)))
            .andExpect(jsonPath("$.[*].libEn").value(hasItem(DEFAULT_LIB_EN)))
            .andExpect(jsonPath("$.[*].addresseAr").value(hasItem(DEFAULT_ADDRESSE_AR)))
            .andExpect(jsonPath("$.[*].addresseEn").value(hasItem(DEFAULT_ADDRESSE_EN)))
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
    void getGroupe() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        // Get the groupe
        restGroupeMockMvc
            .perform(get(ENTITY_API_URL_ID, groupe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(groupe.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libAr").value(DEFAULT_LIB_AR))
            .andExpect(jsonPath("$.libEn").value(DEFAULT_LIB_EN))
            .andExpect(jsonPath("$.addresseAr").value(DEFAULT_ADDRESSE_AR))
            .andExpect(jsonPath("$.addresseEn").value(DEFAULT_ADDRESSE_EN))
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
    void getNonExistingGroupe() throws Exception {
        // Get the groupe
        restGroupeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGroupe() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        int databaseSizeBeforeUpdate = groupeRepository.findAll().size();

        // Update the groupe
        Groupe updatedGroupe = groupeRepository.findById(groupe.getId()).get();
        // Disconnect from session so that the updates on updatedGroupe are not directly saved in db
        em.detach(updatedGroupe);
        updatedGroupe
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .addresseAr(UPDATED_ADDRESSE_AR)
            .addresseEn(UPDATED_ADDRESSE_EN)
            .tel(UPDATED_TEL)
            .email(UPDATED_EMAIL)
            .fax(UPDATED_FAX)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restGroupeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedGroupe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedGroupe))
            )
            .andExpect(status().isOk());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeUpdate);
        Groupe testGroupe = groupeList.get(groupeList.size() - 1);
        assertThat(testGroupe.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGroupe.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testGroupe.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testGroupe.getAddresseAr()).isEqualTo(UPDATED_ADDRESSE_AR);
        assertThat(testGroupe.getAddresseEn()).isEqualTo(UPDATED_ADDRESSE_EN);
        assertThat(testGroupe.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testGroupe.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGroupe.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testGroupe.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testGroupe.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testGroupe.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testGroupe.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testGroupe.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingGroupe() throws Exception {
        int databaseSizeBeforeUpdate = groupeRepository.findAll().size();
        groupe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, groupe.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGroupe() throws Exception {
        int databaseSizeBeforeUpdate = groupeRepository.findAll().size();
        groupe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(groupe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGroupe() throws Exception {
        int databaseSizeBeforeUpdate = groupeRepository.findAll().size();
        groupe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(groupe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGroupeWithPatch() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        int databaseSizeBeforeUpdate = groupeRepository.findAll().size();

        // Update the groupe using partial update
        Groupe partialUpdatedGroupe = new Groupe();
        partialUpdatedGroupe.setId(groupe.getId());

        partialUpdatedGroupe
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .addresseEn(UPDATED_ADDRESSE_EN)
            .tel(UPDATED_TEL)
            .email(UPDATED_EMAIL)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restGroupeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroupe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroupe))
            )
            .andExpect(status().isOk());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeUpdate);
        Groupe testGroupe = groupeList.get(groupeList.size() - 1);
        assertThat(testGroupe.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGroupe.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testGroupe.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testGroupe.getAddresseAr()).isEqualTo(DEFAULT_ADDRESSE_AR);
        assertThat(testGroupe.getAddresseEn()).isEqualTo(UPDATED_ADDRESSE_EN);
        assertThat(testGroupe.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testGroupe.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGroupe.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testGroupe.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testGroupe.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testGroupe.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testGroupe.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testGroupe.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdateGroupeWithPatch() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        int databaseSizeBeforeUpdate = groupeRepository.findAll().size();

        // Update the groupe using partial update
        Groupe partialUpdatedGroupe = new Groupe();
        partialUpdatedGroupe.setId(groupe.getId());

        partialUpdatedGroupe
            .code(UPDATED_CODE)
            .libAr(UPDATED_LIB_AR)
            .libEn(UPDATED_LIB_EN)
            .addresseAr(UPDATED_ADDRESSE_AR)
            .addresseEn(UPDATED_ADDRESSE_EN)
            .tel(UPDATED_TEL)
            .email(UPDATED_EMAIL)
            .fax(UPDATED_FAX)
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED);

        restGroupeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGroupe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGroupe))
            )
            .andExpect(status().isOk());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeUpdate);
        Groupe testGroupe = groupeList.get(groupeList.size() - 1);
        assertThat(testGroupe.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGroupe.getLibAr()).isEqualTo(UPDATED_LIB_AR);
        assertThat(testGroupe.getLibEn()).isEqualTo(UPDATED_LIB_EN);
        assertThat(testGroupe.getAddresseAr()).isEqualTo(UPDATED_ADDRESSE_AR);
        assertThat(testGroupe.getAddresseEn()).isEqualTo(UPDATED_ADDRESSE_EN);
        assertThat(testGroupe.getTel()).isEqualTo(UPDATED_TEL);
        assertThat(testGroupe.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testGroupe.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testGroupe.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testGroupe.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testGroupe.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testGroupe.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testGroupe.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingGroupe() throws Exception {
        int databaseSizeBeforeUpdate = groupeRepository.findAll().size();
        groupe.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGroupeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, groupe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGroupe() throws Exception {
        int databaseSizeBeforeUpdate = groupeRepository.findAll().size();
        groupe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(groupe))
            )
            .andExpect(status().isBadRequest());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGroupe() throws Exception {
        int databaseSizeBeforeUpdate = groupeRepository.findAll().size();
        groupe.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGroupeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(groupe)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Groupe in the database
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGroupe() throws Exception {
        // Initialize the database
        groupeRepository.saveAndFlush(groupe);

        int databaseSizeBeforeDelete = groupeRepository.findAll().size();

        // Delete the groupe
        restGroupeMockMvc
            .perform(delete(ENTITY_API_URL_ID, groupe.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Groupe> groupeList = groupeRepository.findAll();
        assertThat(groupeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
