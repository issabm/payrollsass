package com.issa.payroll.web.rest;

import com.issa.payroll.domain.TargetEligible;
import com.issa.payroll.repository.TargetEligibleRepository;
import com.issa.payroll.service.TargetEligibleService;
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
 * REST controller for managing {@link com.issa.payroll.domain.TargetEligible}.
 */
@RestController
@RequestMapping("/api")
public class TargetEligibleResource {

    private final Logger log = LoggerFactory.getLogger(TargetEligibleResource.class);

    private static final String ENTITY_NAME = "targetEligible";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TargetEligibleService targetEligibleService;

    private final TargetEligibleRepository targetEligibleRepository;

    public TargetEligibleResource(TargetEligibleService targetEligibleService, TargetEligibleRepository targetEligibleRepository) {
        this.targetEligibleService = targetEligibleService;
        this.targetEligibleRepository = targetEligibleRepository;
    }

    /**
     * {@code POST  /target-eligibles} : Create a new targetEligible.
     *
     * @param targetEligible the targetEligible to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new targetEligible, or with status {@code 400 (Bad Request)} if the targetEligible has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/target-eligibles")
    public ResponseEntity<TargetEligible> createTargetEligible(@RequestBody TargetEligible targetEligible) throws URISyntaxException {
        log.debug("REST request to save TargetEligible : {}", targetEligible);
        if (targetEligible.getId() != null) {
            throw new BadRequestAlertException("A new targetEligible cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TargetEligible result = targetEligibleService.save(targetEligible);
        return ResponseEntity
            .created(new URI("/api/target-eligibles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /target-eligibles/:id} : Updates an existing targetEligible.
     *
     * @param id the id of the targetEligible to save.
     * @param targetEligible the targetEligible to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated targetEligible,
     * or with status {@code 400 (Bad Request)} if the targetEligible is not valid,
     * or with status {@code 500 (Internal Server Error)} if the targetEligible couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/target-eligibles/{id}")
    public ResponseEntity<TargetEligible> updateTargetEligible(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TargetEligible targetEligible
    ) throws URISyntaxException {
        log.debug("REST request to update TargetEligible : {}, {}", id, targetEligible);
        if (targetEligible.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, targetEligible.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!targetEligibleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TargetEligible result = targetEligibleService.save(targetEligible);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, targetEligible.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /target-eligibles/:id} : Partial updates given fields of an existing targetEligible, field will ignore if it is null
     *
     * @param id the id of the targetEligible to save.
     * @param targetEligible the targetEligible to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated targetEligible,
     * or with status {@code 400 (Bad Request)} if the targetEligible is not valid,
     * or with status {@code 404 (Not Found)} if the targetEligible is not found,
     * or with status {@code 500 (Internal Server Error)} if the targetEligible couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/target-eligibles/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TargetEligible> partialUpdateTargetEligible(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TargetEligible targetEligible
    ) throws URISyntaxException {
        log.debug("REST request to partial update TargetEligible partially : {}, {}", id, targetEligible);
        if (targetEligible.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, targetEligible.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!targetEligibleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TargetEligible> result = targetEligibleService.partialUpdate(targetEligible);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, targetEligible.getId().toString())
        );
    }

    /**
     * {@code GET  /target-eligibles} : get all the targetEligibles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of targetEligibles in body.
     */
    @GetMapping("/target-eligibles")
    public ResponseEntity<List<TargetEligible>> getAllTargetEligibles(Pageable pageable) {
        log.debug("REST request to get a page of TargetEligibles");
        Page<TargetEligible> page = targetEligibleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /target-eligibles/:id} : get the "id" targetEligible.
     *
     * @param id the id of the targetEligible to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the targetEligible, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/target-eligibles/{id}")
    public ResponseEntity<TargetEligible> getTargetEligible(@PathVariable Long id) {
        log.debug("REST request to get TargetEligible : {}", id);
        Optional<TargetEligible> targetEligible = targetEligibleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(targetEligible);
    }

    /**
     * {@code DELETE  /target-eligibles/:id} : delete the "id" targetEligible.
     *
     * @param id the id of the targetEligible to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/target-eligibles/{id}")
    public ResponseEntity<Void> deleteTargetEligible(@PathVariable Long id) {
        log.debug("REST request to delete TargetEligible : {}", id);
        targetEligibleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
