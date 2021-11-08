package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Payroll;
import com.issa.payroll.repository.PayrollRepository;
import com.issa.payroll.service.PayrollService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Payroll}.
 */
@RestController
@RequestMapping("/api")
public class PayrollResource {

    private final Logger log = LoggerFactory.getLogger(PayrollResource.class);

    private static final String ENTITY_NAME = "payroll";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PayrollService payrollService;

    private final PayrollRepository payrollRepository;

    public PayrollResource(PayrollService payrollService, PayrollRepository payrollRepository) {
        this.payrollService = payrollService;
        this.payrollRepository = payrollRepository;
    }

    /**
     * {@code POST  /payrolls} : Create a new payroll.
     *
     * @param payroll the payroll to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new payroll, or with status {@code 400 (Bad Request)} if the payroll has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payrolls")
    public ResponseEntity<Payroll> createPayroll(@RequestBody Payroll payroll) throws URISyntaxException {
        log.debug("REST request to save Payroll : {}", payroll);
        if (payroll.getId() != null) {
            throw new BadRequestAlertException("A new payroll cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Payroll result = payrollService.save(payroll);
        return ResponseEntity
            .created(new URI("/api/payrolls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payrolls/:id} : Updates an existing payroll.
     *
     * @param id the id of the payroll to save.
     * @param payroll the payroll to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payroll,
     * or with status {@code 400 (Bad Request)} if the payroll is not valid,
     * or with status {@code 500 (Internal Server Error)} if the payroll couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payrolls/{id}")
    public ResponseEntity<Payroll> updatePayroll(@PathVariable(value = "id", required = false) final Long id, @RequestBody Payroll payroll)
        throws URISyntaxException {
        log.debug("REST request to update Payroll : {}, {}", id, payroll);
        if (payroll.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payroll.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payrollRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Payroll result = payrollService.save(payroll);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payroll.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payrolls/:id} : Partial updates given fields of an existing payroll, field will ignore if it is null
     *
     * @param id the id of the payroll to save.
     * @param payroll the payroll to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated payroll,
     * or with status {@code 400 (Bad Request)} if the payroll is not valid,
     * or with status {@code 404 (Not Found)} if the payroll is not found,
     * or with status {@code 500 (Internal Server Error)} if the payroll couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payrolls/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Payroll> partialUpdatePayroll(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Payroll payroll
    ) throws URISyntaxException {
        log.debug("REST request to partial update Payroll partially : {}, {}", id, payroll);
        if (payroll.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, payroll.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!payrollRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Payroll> result = payrollService.partialUpdate(payroll);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, payroll.getId().toString())
        );
    }

    /**
     * {@code GET  /payrolls} : get all the payrolls.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of payrolls in body.
     */
    @GetMapping("/payrolls")
    public ResponseEntity<List<Payroll>> getAllPayrolls(Pageable pageable) {
        log.debug("REST request to get a page of Payrolls");
        Page<Payroll> page = payrollService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /payrolls/:id} : get the "id" payroll.
     *
     * @param id the id of the payroll to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the payroll, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payrolls/{id}")
    public ResponseEntity<Payroll> getPayroll(@PathVariable Long id) {
        log.debug("REST request to get Payroll : {}", id);
        Optional<Payroll> payroll = payrollService.findOne(id);
        return ResponseUtil.wrapOrNotFound(payroll);
    }

    /**
     * {@code DELETE  /payrolls/:id} : delete the "id" payroll.
     *
     * @param id the id of the payroll to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payrolls/{id}")
    public ResponseEntity<Void> deletePayroll(@PathVariable Long id) {
        log.debug("REST request to delete Payroll : {}", id);
        payrollService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
