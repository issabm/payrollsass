package com.issa.payroll.web.rest;

import com.issa.payroll.domain.PalierCondition;
import com.issa.payroll.repository.PalierConditionRepository;
import com.issa.payroll.service.PalierConditionService;
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
 * REST controller for managing {@link com.issa.payroll.domain.PalierCondition}.
 */
@RestController
@RequestMapping("/api")
public class PalierConditionResource {

    private final Logger log = LoggerFactory.getLogger(PalierConditionResource.class);

    private static final String ENTITY_NAME = "palierCondition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PalierConditionService palierConditionService;

    private final PalierConditionRepository palierConditionRepository;

    public PalierConditionResource(PalierConditionService palierConditionService, PalierConditionRepository palierConditionRepository) {
        this.palierConditionService = palierConditionService;
        this.palierConditionRepository = palierConditionRepository;
    }

    /**
     * {@code POST  /palier-conditions} : Create a new palierCondition.
     *
     * @param palierCondition the palierCondition to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new palierCondition, or with status {@code 400 (Bad Request)} if the palierCondition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/palier-conditions")
    public ResponseEntity<PalierCondition> createPalierCondition(@RequestBody PalierCondition palierCondition) throws URISyntaxException {
        log.debug("REST request to save PalierCondition : {}", palierCondition);
        if (palierCondition.getId() != null) {
            throw new BadRequestAlertException("A new palierCondition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PalierCondition result = palierConditionService.save(palierCondition);
        return ResponseEntity
            .created(new URI("/api/palier-conditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /palier-conditions/:id} : Updates an existing palierCondition.
     *
     * @param id the id of the palierCondition to save.
     * @param palierCondition the palierCondition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated palierCondition,
     * or with status {@code 400 (Bad Request)} if the palierCondition is not valid,
     * or with status {@code 500 (Internal Server Error)} if the palierCondition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/palier-conditions/{id}")
    public ResponseEntity<PalierCondition> updatePalierCondition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PalierCondition palierCondition
    ) throws URISyntaxException {
        log.debug("REST request to update PalierCondition : {}, {}", id, palierCondition);
        if (palierCondition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, palierCondition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!palierConditionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PalierCondition result = palierConditionService.save(palierCondition);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, palierCondition.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /palier-conditions/:id} : Partial updates given fields of an existing palierCondition, field will ignore if it is null
     *
     * @param id the id of the palierCondition to save.
     * @param palierCondition the palierCondition to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated palierCondition,
     * or with status {@code 400 (Bad Request)} if the palierCondition is not valid,
     * or with status {@code 404 (Not Found)} if the palierCondition is not found,
     * or with status {@code 500 (Internal Server Error)} if the palierCondition couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/palier-conditions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PalierCondition> partialUpdatePalierCondition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PalierCondition palierCondition
    ) throws URISyntaxException {
        log.debug("REST request to partial update PalierCondition partially : {}, {}", id, palierCondition);
        if (palierCondition.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, palierCondition.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!palierConditionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PalierCondition> result = palierConditionService.partialUpdate(palierCondition);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, palierCondition.getId().toString())
        );
    }

    /**
     * {@code GET  /palier-conditions} : get all the palierConditions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of palierConditions in body.
     */
    @GetMapping("/palier-conditions")
    public ResponseEntity<List<PalierCondition>> getAllPalierConditions(Pageable pageable) {
        log.debug("REST request to get a page of PalierConditions");
        Page<PalierCondition> page = palierConditionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /palier-conditions/:id} : get the "id" palierCondition.
     *
     * @param id the id of the palierCondition to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the palierCondition, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/palier-conditions/{id}")
    public ResponseEntity<PalierCondition> getPalierCondition(@PathVariable Long id) {
        log.debug("REST request to get PalierCondition : {}", id);
        Optional<PalierCondition> palierCondition = palierConditionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(palierCondition);
    }

    /**
     * {@code DELETE  /palier-conditions/:id} : delete the "id" palierCondition.
     *
     * @param id the id of the palierCondition to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/palier-conditions/{id}")
    public ResponseEntity<Void> deletePalierCondition(@PathVariable Long id) {
        log.debug("REST request to delete PalierCondition : {}", id);
        palierConditionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
