package com.issa.payroll.web.rest;

import com.issa.payroll.domain.SousTypeContrat;
import com.issa.payroll.repository.SousTypeContratRepository;
import com.issa.payroll.service.SousTypeContratService;
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
 * REST controller for managing {@link com.issa.payroll.domain.SousTypeContrat}.
 */
@RestController
@RequestMapping("/api")
public class SousTypeContratResource {

    private final Logger log = LoggerFactory.getLogger(SousTypeContratResource.class);

    private static final String ENTITY_NAME = "sousTypeContrat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SousTypeContratService sousTypeContratService;

    private final SousTypeContratRepository sousTypeContratRepository;

    public SousTypeContratResource(SousTypeContratService sousTypeContratService, SousTypeContratRepository sousTypeContratRepository) {
        this.sousTypeContratService = sousTypeContratService;
        this.sousTypeContratRepository = sousTypeContratRepository;
    }

    /**
     * {@code POST  /sous-type-contrats} : Create a new sousTypeContrat.
     *
     * @param sousTypeContrat the sousTypeContrat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sousTypeContrat, or with status {@code 400 (Bad Request)} if the sousTypeContrat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sous-type-contrats")
    public ResponseEntity<SousTypeContrat> createSousTypeContrat(@RequestBody SousTypeContrat sousTypeContrat) throws URISyntaxException {
        log.debug("REST request to save SousTypeContrat : {}", sousTypeContrat);
        if (sousTypeContrat.getId() != null) {
            throw new BadRequestAlertException("A new sousTypeContrat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SousTypeContrat result = sousTypeContratService.save(sousTypeContrat);
        return ResponseEntity
            .created(new URI("/api/sous-type-contrats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sous-type-contrats/:id} : Updates an existing sousTypeContrat.
     *
     * @param id the id of the sousTypeContrat to save.
     * @param sousTypeContrat the sousTypeContrat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sousTypeContrat,
     * or with status {@code 400 (Bad Request)} if the sousTypeContrat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sousTypeContrat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sous-type-contrats/{id}")
    public ResponseEntity<SousTypeContrat> updateSousTypeContrat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SousTypeContrat sousTypeContrat
    ) throws URISyntaxException {
        log.debug("REST request to update SousTypeContrat : {}, {}", id, sousTypeContrat);
        if (sousTypeContrat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sousTypeContrat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sousTypeContratRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SousTypeContrat result = sousTypeContratService.save(sousTypeContrat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sousTypeContrat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sous-type-contrats/:id} : Partial updates given fields of an existing sousTypeContrat, field will ignore if it is null
     *
     * @param id the id of the sousTypeContrat to save.
     * @param sousTypeContrat the sousTypeContrat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sousTypeContrat,
     * or with status {@code 400 (Bad Request)} if the sousTypeContrat is not valid,
     * or with status {@code 404 (Not Found)} if the sousTypeContrat is not found,
     * or with status {@code 500 (Internal Server Error)} if the sousTypeContrat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sous-type-contrats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SousTypeContrat> partialUpdateSousTypeContrat(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SousTypeContrat sousTypeContrat
    ) throws URISyntaxException {
        log.debug("REST request to partial update SousTypeContrat partially : {}, {}", id, sousTypeContrat);
        if (sousTypeContrat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sousTypeContrat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sousTypeContratRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SousTypeContrat> result = sousTypeContratService.partialUpdate(sousTypeContrat);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sousTypeContrat.getId().toString())
        );
    }

    /**
     * {@code GET  /sous-type-contrats} : get all the sousTypeContrats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sousTypeContrats in body.
     */
    @GetMapping("/sous-type-contrats")
    public ResponseEntity<List<SousTypeContrat>> getAllSousTypeContrats(Pageable pageable) {
        log.debug("REST request to get a page of SousTypeContrats");
        Page<SousTypeContrat> page = sousTypeContratService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sous-type-contrats/:id} : get the "id" sousTypeContrat.
     *
     * @param id the id of the sousTypeContrat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sousTypeContrat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sous-type-contrats/{id}")
    public ResponseEntity<SousTypeContrat> getSousTypeContrat(@PathVariable Long id) {
        log.debug("REST request to get SousTypeContrat : {}", id);
        Optional<SousTypeContrat> sousTypeContrat = sousTypeContratService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sousTypeContrat);
    }

    /**
     * {@code DELETE  /sous-type-contrats/:id} : delete the "id" sousTypeContrat.
     *
     * @param id the id of the sousTypeContrat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sous-type-contrats/{id}")
    public ResponseEntity<Void> deleteSousTypeContrat(@PathVariable Long id) {
        log.debug("REST request to delete SousTypeContrat : {}", id);
        sousTypeContratService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
