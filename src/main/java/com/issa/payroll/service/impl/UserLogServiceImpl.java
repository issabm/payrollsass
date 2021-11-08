package com.issa.payroll.service.impl;

import com.issa.payroll.domain.UserLog;
import com.issa.payroll.repository.UserLogRepository;
import com.issa.payroll.service.UserLogService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserLog}.
 */
@Service
@Transactional
public class UserLogServiceImpl implements UserLogService {

    private final Logger log = LoggerFactory.getLogger(UserLogServiceImpl.class);

    private final UserLogRepository userLogRepository;

    public UserLogServiceImpl(UserLogRepository userLogRepository) {
        this.userLogRepository = userLogRepository;
    }

    @Override
    public UserLog save(UserLog userLog) {
        log.debug("Request to save UserLog : {}", userLog);
        return userLogRepository.save(userLog);
    }

    @Override
    public Optional<UserLog> partialUpdate(UserLog userLog) {
        log.debug("Request to partially update UserLog : {}", userLog);

        return userLogRepository
            .findById(userLog.getId())
            .map(existingUserLog -> {
                if (userLog.getOp() != null) {
                    existingUserLog.setOp(userLog.getOp());
                }
                if (userLog.getUtil() != null) {
                    existingUserLog.setUtil(userLog.getUtil());
                }
                if (userLog.getDateOp() != null) {
                    existingUserLog.setDateOp(userLog.getDateOp());
                }

                return existingUserLog;
            })
            .map(userLogRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserLog> findAll(Pageable pageable) {
        log.debug("Request to get all UserLogs");
        return userLogRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserLog> findOne(Long id) {
        log.debug("Request to get UserLog : {}", id);
        return userLogRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserLog : {}", id);
        userLogRepository.deleteById(id);
    }
}
