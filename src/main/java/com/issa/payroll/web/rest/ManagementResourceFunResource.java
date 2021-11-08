package com.issa.payroll.web.rest;

import com.issa.payroll.domain.ManagementResourceFun;
import com.issa.payroll.repository.ManagementResourceFunRepository;
import com.issa.payroll.service.ManagementResourceFunService;
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
 * REST controller for managing {@link com.issa.payroll.domain.ManagementResourceFun}.
 */
@RestController
@RequestMapping("/api")
public class ManagementResourceFunResource {

    private final Logger log = LoggerFactory.getLogger(ManagementResourceFunResource.class);

    private static final String ENTITY_NAME = "managementResourceFun";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ManagementResourceFunService managementResourceFunService;

    private final ManagementResourceFunRepository managementResourceFunRepository;

    public ManagementResourceFunResource(
        ManagementResourceFunService managementResourceFunService,
        ManagementResourceFunRepository managementResourceFunRepository
    ) {
        this.managementResourceFunService = managementResourceFunService;
        this.managementResourceFunRepository = managementResourceFunRepository;
    }

    /**
     * {@code POST  /management-resource-funs} : Create a new managementResourceFun.
     *
     * @param managementResourceFun the managementResourceFun to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new managementResourceFun, or with status {@code 400 (Bad Request)} if the managementResourceFun has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/management-resource-funs")
    public ResponseEntity<ManagementResourceFun> createManagementResourceFun(@RequestBody ManagementResourceFun managementResourceFun)
        throws URISyntaxException {
        log.debug("REST request to save ManagementResourceFun : {}", managementResourceFun);
        if (managementResourceFun.getId() != null) {
            throw new BadRequestAlertException("A new managementResourceFun cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ManagementResourceFun result = managementResourceFunService.save(managementResourceFun);
        return ResponseEntity
            .created(new URI("/api/management-resource-funs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /management-resource-funs/:id} : Updates an existing managementResourceFun.
     *
     * @param id the id of the managementResourceFun to save.
     * @param managementResourceFun the managementResourceFun to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated managementResourceFun,
     * or with status {@code 400 (Bad Request)} if the managementResourceFun is not valid,
     * or with status {@code 500 (Internal Server Error)} if the managementResourceFun couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/management-resource-funs/{id}")
    public ResponseEntity<ManagementResourceFun> updateManagementResourceFun(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManagementResourceFun managementResourceFun
    ) throws URISyntaxException {
        log.debug("REST request to update ManagementResourceFun : {}, {}", id, managementResourceFun);
        if (managementResourceFun.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, managementResourceFun.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!managementResourceFunRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ManagementResourceFun result = managementResourceFunService.save(managementResourceFun);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, managementResourceFun.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /management-resource-funs/:id} : Partial updates given fields of an existing managementResourceFun, field will ignore if it is null
     *
     * @param id the id of the managementResourceFun to save.
     * @param managementResourceFun the managementResourceFun to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated managementResourceFun,
     * or with status {@code 400 (Bad Request)} if the managementResourceFun is not valid,
     * or with status {@code 404 (Not Found)} if the managementResourceFun is not found,
     * or with status {@code 500 (Internal Server Error)} if the managementResourceFun couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/management-resource-funs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ManagementResourceFun> partialUpdateManagementResourceFun(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ManagementResourceFun managementResourceFun
    ) throws URISyntaxException {
        log.debug("REST request to partial update ManagementResourceFun partially : {}, {}", id, managementResourceFun);
        if (managementResourceFun.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, managementResourceFun.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!managementResourceFunRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ManagementResourceFun> result = managementResourceFunService.partialUpdate(managementResourceFun);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, managementResourceFun.getId().toString())
        );
    }

    /**
     * {@code GET  /management-resource-funs} : get all the managementResourceFuns.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of managementResourceFuns in body.
     */
    @GetMapping("/management-resource-funs")
    public ResponseEntity<List<ManagementResourceFun>> getAllManagementResourceFuns(Pageable pageable) {
        log.debug("REST request to get a page of ManagementResourceFuns");
        Page<ManagementResourceFun> page = managementResourceFunService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /management-resource-funs/:id} : get the "id" managementResourceFun.
     *
     * @param id the id of the managementResourceFun to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the managementResourceFun, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/management-resource-funs/{id}")
    public ResponseEntity<ManagementResourceFun> getManagementResourceFun(@PathVariable Long id) {
        log.debug("REST request to get ManagementResourceFun : {}", id);
        Optional<ManagementResourceFun> managementResourceFun = managementResourceFunService.findOne(id);
        return ResponseUtil.wrapOrNotFound(managementResourceFun);
    }

    /**
     * {@code DELETE  /management-resource-funs/:id} : delete the "id" managementResourceFun.
     *
     * @param id the id of the managementResourceFun to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/management-resource-funs/{id}")
    public ResponseEntity<Void> deleteManagementResourceFun(@PathVariable Long id) {
        log.debug("REST request to delete ManagementResourceFun : {}", id);
        managementResourceFunService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
