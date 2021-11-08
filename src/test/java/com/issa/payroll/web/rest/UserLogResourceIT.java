package com.issa.payroll.web.rest;

import static com.issa.payroll.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.issa.payroll.IntegrationTest;
import com.issa.payroll.domain.UserLog;
import com.issa.payroll.repository.UserLogRepository;
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
 * Integration tests for the {@link UserLogResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class UserLogResourceIT {

    private static final String DEFAULT_OP = "AAAAAAAAAA";
    private static final String UPDATED_OP = "BBBBBBBBBB";

    private static final String DEFAULT_UTIL = "AAAAAAAAAA";
    private static final String UPDATED_UTIL = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_OP = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_OP = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/user-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserLogRepository userLogRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserLogMockMvc;

    private UserLog userLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserLog createEntity(EntityManager em) {
        UserLog userLog = new UserLog().op(DEFAULT_OP).util(DEFAULT_UTIL).dateOp(DEFAULT_DATE_OP);
        return userLog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserLog createUpdatedEntity(EntityManager em) {
        UserLog userLog = new UserLog().op(UPDATED_OP).util(UPDATED_UTIL).dateOp(UPDATED_DATE_OP);
        return userLog;
    }

    @BeforeEach
    public void initTest() {
        userLog = createEntity(em);
    }

    @Test
    @Transactional
    void createUserLog() throws Exception {
        int databaseSizeBeforeCreate = userLogRepository.findAll().size();
        // Create the UserLog
        restUserLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userLog)))
            .andExpect(status().isCreated());

        // Validate the UserLog in the database
        List<UserLog> userLogList = userLogRepository.findAll();
        assertThat(userLogList).hasSize(databaseSizeBeforeCreate + 1);
        UserLog testUserLog = userLogList.get(userLogList.size() - 1);
        assertThat(testUserLog.getOp()).isEqualTo(DEFAULT_OP);
        assertThat(testUserLog.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testUserLog.getDateOp()).isEqualTo(DEFAULT_DATE_OP);
    }

    @Test
    @Transactional
    void createUserLogWithExistingId() throws Exception {
        // Create the UserLog with an existing ID
        userLog.setId(1L);

        int databaseSizeBeforeCreate = userLogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userLog)))
            .andExpect(status().isBadRequest());

        // Validate the UserLog in the database
        List<UserLog> userLogList = userLogRepository.findAll();
        assertThat(userLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUserLogs() throws Exception {
        // Initialize the database
        userLogRepository.saveAndFlush(userLog);

        // Get all the userLogList
        restUserLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].op").value(hasItem(DEFAULT_OP)))
            .andExpect(jsonPath("$.[*].util").value(hasItem(DEFAULT_UTIL)))
            .andExpect(jsonPath("$.[*].dateOp").value(hasItem(sameInstant(DEFAULT_DATE_OP))));
    }

    @Test
    @Transactional
    void getUserLog() throws Exception {
        // Initialize the database
        userLogRepository.saveAndFlush(userLog);

        // Get the userLog
        restUserLogMockMvc
            .perform(get(ENTITY_API_URL_ID, userLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userLog.getId().intValue()))
            .andExpect(jsonPath("$.op").value(DEFAULT_OP))
            .andExpect(jsonPath("$.util").value(DEFAULT_UTIL))
            .andExpect(jsonPath("$.dateOp").value(sameInstant(DEFAULT_DATE_OP)));
    }

    @Test
    @Transactional
    void getNonExistingUserLog() throws Exception {
        // Get the userLog
        restUserLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserLog() throws Exception {
        // Initialize the database
        userLogRepository.saveAndFlush(userLog);

        int databaseSizeBeforeUpdate = userLogRepository.findAll().size();

        // Update the userLog
        UserLog updatedUserLog = userLogRepository.findById(userLog.getId()).get();
        // Disconnect from session so that the updates on updatedUserLog are not directly saved in db
        em.detach(updatedUserLog);
        updatedUserLog.op(UPDATED_OP).util(UPDATED_UTIL).dateOp(UPDATED_DATE_OP);

        restUserLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserLog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserLog))
            )
            .andExpect(status().isOk());

        // Validate the UserLog in the database
        List<UserLog> userLogList = userLogRepository.findAll();
        assertThat(userLogList).hasSize(databaseSizeBeforeUpdate);
        UserLog testUserLog = userLogList.get(userLogList.size() - 1);
        assertThat(testUserLog.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testUserLog.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testUserLog.getDateOp()).isEqualTo(UPDATED_DATE_OP);
    }

    @Test
    @Transactional
    void putNonExistingUserLog() throws Exception {
        int databaseSizeBeforeUpdate = userLogRepository.findAll().size();
        userLog.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userLog.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserLog in the database
        List<UserLog> userLogList = userLogRepository.findAll();
        assertThat(userLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserLog() throws Exception {
        int databaseSizeBeforeUpdate = userLogRepository.findAll().size();
        userLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserLog in the database
        List<UserLog> userLogList = userLogRepository.findAll();
        assertThat(userLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserLog() throws Exception {
        int databaseSizeBeforeUpdate = userLogRepository.findAll().size();
        userLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserLogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userLog)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserLog in the database
        List<UserLog> userLogList = userLogRepository.findAll();
        assertThat(userLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserLogWithPatch() throws Exception {
        // Initialize the database
        userLogRepository.saveAndFlush(userLog);

        int databaseSizeBeforeUpdate = userLogRepository.findAll().size();

        // Update the userLog using partial update
        UserLog partialUpdatedUserLog = new UserLog();
        partialUpdatedUserLog.setId(userLog.getId());

        partialUpdatedUserLog.op(UPDATED_OP).dateOp(UPDATED_DATE_OP);

        restUserLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserLog))
            )
            .andExpect(status().isOk());

        // Validate the UserLog in the database
        List<UserLog> userLogList = userLogRepository.findAll();
        assertThat(userLogList).hasSize(databaseSizeBeforeUpdate);
        UserLog testUserLog = userLogList.get(userLogList.size() - 1);
        assertThat(testUserLog.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testUserLog.getUtil()).isEqualTo(DEFAULT_UTIL);
        assertThat(testUserLog.getDateOp()).isEqualTo(UPDATED_DATE_OP);
    }

    @Test
    @Transactional
    void fullUpdateUserLogWithPatch() throws Exception {
        // Initialize the database
        userLogRepository.saveAndFlush(userLog);

        int databaseSizeBeforeUpdate = userLogRepository.findAll().size();

        // Update the userLog using partial update
        UserLog partialUpdatedUserLog = new UserLog();
        partialUpdatedUserLog.setId(userLog.getId());

        partialUpdatedUserLog.op(UPDATED_OP).util(UPDATED_UTIL).dateOp(UPDATED_DATE_OP);

        restUserLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserLog))
            )
            .andExpect(status().isOk());

        // Validate the UserLog in the database
        List<UserLog> userLogList = userLogRepository.findAll();
        assertThat(userLogList).hasSize(databaseSizeBeforeUpdate);
        UserLog testUserLog = userLogList.get(userLogList.size() - 1);
        assertThat(testUserLog.getOp()).isEqualTo(UPDATED_OP);
        assertThat(testUserLog.getUtil()).isEqualTo(UPDATED_UTIL);
        assertThat(testUserLog.getDateOp()).isEqualTo(UPDATED_DATE_OP);
    }

    @Test
    @Transactional
    void patchNonExistingUserLog() throws Exception {
        int databaseSizeBeforeUpdate = userLogRepository.findAll().size();
        userLog.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserLog in the database
        List<UserLog> userLogList = userLogRepository.findAll();
        assertThat(userLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserLog() throws Exception {
        int databaseSizeBeforeUpdate = userLogRepository.findAll().size();
        userLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userLog))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserLog in the database
        List<UserLog> userLogList = userLogRepository.findAll();
        assertThat(userLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserLog() throws Exception {
        int databaseSizeBeforeUpdate = userLogRepository.findAll().size();
        userLog.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserLogMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userLog)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserLog in the database
        List<UserLog> userLogList = userLogRepository.findAll();
        assertThat(userLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserLog() throws Exception {
        // Initialize the database
        userLogRepository.saveAndFlush(userLog);

        int databaseSizeBeforeDelete = userLogRepository.findAll().size();

        // Delete the userLog
        restUserLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, userLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserLog> userLogList = userLogRepository.findAll();
        assertThat(userLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
