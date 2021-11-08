package com.issa.payroll.web.rest;

import com.issa.payroll.domain.PayrollInfo;
import com.issa.payroll.repository.PayrollInfoRepository;
import com.issa.payroll.service.PayrollInfoService;
import com.issa.payroll.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.issa.payroll.domain.PayrollInfo}.
 */
@RestController
@RequestMapping("/api")
public class PayrollInfoResource {

    private final Logger log = LoggerFactory.getLogger(PayrollInfoResource.class);

    private static final String ENTITY_NAME = "payrollInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayrollInfoService payrollInfoService;

    private final PayrollInfoRepository payrollInfoRepository;

    public PayrollInfoResource(PayrollInfoService payrollInfoService, PayrollInfoRepository payrollInfoRepository) {
        this.payrollInfoService = payrollInfoService;
        this.payrollInfoRepository = payrollInfoRepository;
    }

    /**
     * {@code POST  /payroll-infos} : Create a new payrollInfo.
     *
     * @param payrollInfo the payrollInfo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payrollInfo, or with status {@code 400 (Bad Request)} if the payrollInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payroll-infos")
    public ResponseEntity<PayrollInfo> createPayrollInfo(@RequestBody PayrollInfo payrollInfo) throws URISyntaxException {
        log.debug("REST request to save PayrollInfo : {}", payrollInfo);
        if (payrollInfo.getId() != null) {
            throw new BadRequestAlertException("A new payrollInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PayrollInfo result = payrollInfoService.save(payrollInfo);
        return ResponseEntity
            .created(new URI("/api/payroll-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payroll-infos/:id} : Updates an existing payrollInfo.
     *
     * @param id the id of the payrollInfo to save.
     * @param payrollInfo the payrollInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payrollInfo,
     * or with status {@code 400 (Bad Request)} if the payrollInfo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payrollInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payroll-infos/{id}")
    public ResponseEntity<PayrollInfo> updatePayrollInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PayrollInfo payrollInfo
    ) throws URISyntaxException {
        log.debug("REST request to update PayrollInfo : {}, {}", id, payrollInfo);
        if (payrollInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payrollInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payrollInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PayrollInfo result = payrollInfoService.save(payrollInfo);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payrollInfo.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payroll-infos/:id} : Partial updates given fields of an existing payrollInfo, field will ignore if it is null
     *
     * @param id the id of the payrollInfo to save.
     * @param payrollInfo the payrollInfo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payrollInfo,
     * or with status {@code 400 (Bad Request)} if the payrollInfo is not valid,
     * or with status {@code 404 (Not Found)} if the payrollInfo is not found,
     * or with status {@code 500 (Internal Server Error)} if the payrollInfo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payroll-infos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PayrollInfo> partialUpdatePayrollInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PayrollInfo payrollInfo
    ) throws URISyntaxException {
        log.debug("REST request to partial update PayrollInfo partially : {}, {}", id, payrollInfo);
        if (payrollInfo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payrollInfo.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payrollInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PayrollInfo> result = payrollInfoService.partialUpdate(payrollInfo);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payrollInfo.getId().toString())
        );
    }

    /**
     * {@code GET  /payroll-infos} : get all the payrollInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payrollInfos in body.
     */
    @GetMapping("/payroll-infos")
    public ResponseEntity<List<PayrollInfo>> getAllPayrollInfos(Pageable pageable) {
        log.debug("REST request to get a page of PayrollInfos");
        Page<PayrollInfo> page = payrollInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payroll-infos/:id} : get the "id" payrollInfo.
     *
     * @param id the id of the payrollInfo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payrollInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payroll-infos/{id}")
    public ResponseEntity<PayrollInfo> getPayrollInfo(@PathVariable Long id) {
        log.debug("REST request to get PayrollInfo : {}", id);
        Optional<PayrollInfo> payrollInfo = payrollInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(payrollInfo);
    }

    /**
     * {@code DELETE  /payroll-infos/:id} : delete the "id" payrollInfo.
     *
     * @param id the id of the payrollInfo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payroll-infos/{id}")
    public ResponseEntity<Void> deletePayrollInfo(@PathVariable Long id) {
        log.debug("REST request to delete PayrollInfo : {}", id);
        payrollInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
