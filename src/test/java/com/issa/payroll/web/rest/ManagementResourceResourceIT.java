package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.ManagementResource;
import com.issa.payroll.repository.ManagementResourceRepository;
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
 * Integration tests for the {@link ManagementResourceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ManagementResourceResourceIT {

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/management-resources";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ManagementResourceRepository managementResourceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restManagementResourceMockMvc;

    private ManagementResource managementResource;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ManagementResource createEntity(EntityManager em) {
        ManagementResource managementResource = new ManagementResource()
            .util(DEFAULT_UTIL)
            .dateop(DEFAULT_DATEOP)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .op(DEFAULT_OP)
            .isDeleted(DEFAULT_IS_DELETED)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedDate(DEFAULT_MODIFIED_DATE);
        return managementResource;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ManagementResource createUpdatedEntity(EntityManager em) {
        ManagementResource managementResource = new ManagementResource()
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);
        return managementResource;
    }

    @BeforeEach
    public void initTest() {
        managementResource = createEntity(em);
    }

    @Test
    @Transactional
    void createManagementResource() throws Exception {
        int databaseSizeBeforeCreate = managementResourceRepository.findAll().size();
        // Create the ManagementResource
        restManagementResourceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(managementResource))
            )
            .andExpect(status().isCreated());

        // Validate the ManagementResource in the database
        List<ManagementResource> managementResourceList = managementResourceRepository.findAll();
        assertThat(managementResourceList).hasSize(databaseSizeBeforeCreate + 1);
        ManagementResource testManagementResource = managementResourceList.get(managementResourceList.size() - 1);
        assertThat(testManagementResource.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testManagementResource.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testManagementResource.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testManagementResource.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testManagementResource.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testManagementResource.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testManagementResource.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testManagementResource.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createManagementResourceWithExistingId() throws Exception {
        // Create the ManagementResource with an existing ID
        managementResource.setId(1L);

        int databaseSizeBeforeCreate = managementResourceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restManagementResourceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(managementResource))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResource in the database
        List<ManagementResource> managementResourceList = managementResourceRepository.findAll();
        assertThat(managementResourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllManagementResources() throws Exception {
        // Initialize the database
        managementResourceRepository.saveAndFlush(managementResource);

        // Get all the managementResourceList
        restManagementResourceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(managementResource.getId().intValue())))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
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
    void getManagementResource() throws Exception {
        // Initialize the database
        managementResourceRepository.saveAndFlush(managementResource);

        // Get the managementResource
        restManagementResourceMockMvc
            .perform(get(ENTITY_API_URL_ID, managementResource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(managementResource.getId().intValue()))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
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
    void getNonExistingManagementResource() throws Exception {
        // Get the managementResource
        restManagementResourceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewManagementResource() throws Exception {
        // Initialize the database
        managementResourceRepository.saveAndFlush(managementResource);

        int databaseSizeBeforeUpdate = managementResourceRepository.findAll().size();

        // Update the managementResource
        ManagementResource updatedManagementResource = managementResourceRepository.findById(managementResource.getId()).get();
        // Disconnect from session so that the updates on updatedManagementResource are not directly saved in db
        em.detach(updatedManagementResource);
        updatedManagementResource
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restManagementResourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedManagementResource.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedManagementResource))
            )
            .andExpect(status().isOk());

        // Validate the ManagementResource in the database
        List<ManagementResource> managementResourceList = managementResourceRepository.findAll();
        assertThat(managementResourceList).hasSize(databaseSizeBeforeUpdate);
        ManagementResource testManagementResource = managementResourceList.get(managementResourceList.size() - 1);
        assertThat(testManagementResource.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testManagementResource.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testManagementResource.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testManagementResource.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testManagementResource.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testManagementResource.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testManagementResource.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testManagementResource.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingManagementResource() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceRepository.findAll().size();
        managementResource.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManagementResourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, managementResource.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementResource))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResource in the database
        List<ManagementResource> managementResourceList = managementResourceRepository.findAll();
        assertThat(managementResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchManagementResource() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceRepository.findAll().size();
        managementResource.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementResourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(managementResource))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResource in the database
        List<ManagementResource> managementResourceList = managementResourceRepository.findAll();
        assertThat(managementResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamManagementResource() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceRepository.findAll().size();
        managementResource.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementResourceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(managementResource))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ManagementResource in the database
        List<ManagementResource> managementResourceList = managementResourceRepository.findAll();
        assertThat(managementResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateManagementResourceWithPatch() throws Exception {
        // Initialize the database
        managementResourceRepository.saveAndFlush(managementResource);

        int databaseSizeBeforeUpdate = managementResourceRepository.findAll().size();

        // Update the managementResource using partial update
        ManagementResource partialUpdatedManagementResource = new ManagementResource();
        partialUpdatedManagementResource.setId(managementResource.getId());

        partialUpdatedManagementResource
            .modifiedBy(UPDATED_MODIFIED_BY)
            .op(UPDATED_OP)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restManagementResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManagementResource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManagementResource))
            )
            .andExpect(status().isOk());

        // Validate the ManagementResource in the database
        List<ManagementResource> managementResourceList = managementResourceRepository.findAll();
        assertThat(managementResourceList).hasSize(databaseSizeBeforeUpdate);
        ManagementResource testManagementResource = managementResourceList.get(managementResourceList.size() - 1);
        assertThat(testManagementResource.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testManagementResource.getDateop()).isEqualTo(DEFAULT_DATEOP);
        assertThat(testManagementResource.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testManagementResource.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testManagementResource.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testManagementResource.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testManagementResource.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testManagementResource.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateManagementResourceWithPatch() throws Exception {
        // Initialize the database
        managementResourceRepository.saveAndFlush(managementResource);

        int databaseSizeBeforeUpdate = managementResourceRepository.findAll().size();

        // Update the managementResource using partial update
        ManagementResource partialUpdatedManagementResource = new ManagementResource();
        partialUpdatedManagementResource.setId(managementResource.getId());

        partialUpdatedManagementResource
            .util(UPDATED_UTIL)
            .dateop(UPDATED_DATEOP)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .op(UPDATED_OP)
            .isDeleted(UPDATED_IS_DELETED)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedDate(UPDATED_MODIFIED_DATE);

        restManagementResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManagementResource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedManagementResource))
            )
            .andExpect(status().isOk());

        // Validate the ManagementResource in the database
        List<ManagementResource> managementResourceList = managementResourceRepository.findAll();
        assertThat(managementResourceList).hasSize(databaseSizeBeforeUpdate);
        ManagementResource testManagementResource = managementResourceList.get(managementResourceList.size() - 1);
        assertThat(testManagementResource.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testManagementResource.getDateop()).isEqualTo(UPDATED_DATEOP);
        assertThat(testManagementResource.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testManagementResource.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testManagementResource.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testManagementResource.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testManagementResource.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testManagementResource.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingManagementResource() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceRepository.findAll().size();
        managementResource.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManagementResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, managementResource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(managementResource))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResource in the database
        List<ManagementResource> managementResourceList = managementResourceRepository.findAll();
        assertThat(managementResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchManagementResource() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceRepository.findAll().size();
        managementResource.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementResourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(managementResource))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManagementResource in the database
        List<ManagementResource> managementResourceList = managementResourceRepository.findAll();
        assertThat(managementResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamManagementResource() throws Exception {
        int databaseSizeBeforeUpdate = managementResourceRepository.findAll().size();
        managementResource.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManagementResourceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(managementResource))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ManagementResource in the database
        List<ManagementResource> managementResourceList = managementResourceRepository.findAll();
        assertThat(managementResourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteManagementResource() throws Exception {
        // Initialize the database
        managementResourceRepository.saveAndFlush(managementResource);

        int databaseSizeBeforeDelete = managementResourceRepository.findAll().size();

        // Delete the managementResource
        restManagementResourceMockMvc
            .perform(delete(ENTITY_API_URL_ID, managementResource.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ManagementResource> managementResourceList = managementResourceRepository.findAll();
        assertThat(managementResourceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
