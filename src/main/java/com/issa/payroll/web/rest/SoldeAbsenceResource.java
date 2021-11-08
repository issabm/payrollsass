package com.issa.payroll.web.rest;

import com.issa.payroll.domain.SoldeAbsence;
import com.issa.payroll.repository.SoldeAbsenceRepository;
import com.issa.payroll.service.SoldeAbsenceService;
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
 * REST controller for managing {@link com.issa.payroll.domain.SoldeAbsence}.
 */
@RestController
@RequestMapping("/api")
public class SoldeAbsenceResource {

    private final Logger log = LoggerFactory.getLogger(SoldeAbsenceResource.class);

    private static final String ENTITY_NAME = "soldeAbsence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoldeAbsenceService soldeAbsenceService;

    private final SoldeAbsenceRepository soldeAbsenceRepository;

    public SoldeAbsenceResource(SoldeAbsenceService soldeAbsenceService, SoldeAbsenceRepository soldeAbsenceRepository) {
        this.soldeAbsenceService = soldeAbsenceService;
        this.soldeAbsenceRepository = soldeAbsenceRepository;
    }

    /**
     * {@code POST  /solde-absences} : Create a new soldeAbsence.
     *
     * @param soldeAbsence the soldeAbsence to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new soldeAbsence, or with status {@code 400 (Bad Request)} if the soldeAbsence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solde-absences")
    public ResponseEntity<SoldeAbsence> createSoldeAbsence(@RequestBody SoldeAbsence soldeAbsence) throws URISyntaxException {
        log.debug("REST request to save SoldeAbsence : {}", soldeAbsence);
        if (soldeAbsence.getId() != null) {
            throw new BadRequestAlertException("A new soldeAbsence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SoldeAbsence result = soldeAbsenceService.save(soldeAbsence);
        return ResponseEntity
            .created(new URI("/api/solde-absences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solde-absences/:id} : Updates an existing soldeAbsence.
     *
     * @param id the id of the soldeAbsence to save.
     * @param soldeAbsence the soldeAbsence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soldeAbsence,
     * or with status {@code 400 (Bad Request)} if the soldeAbsence is not valid,
     * or with status {@code 500 (Internal Server Error)} if the soldeAbsence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solde-absences/{id}")
    public ResponseEntity<SoldeAbsence> updateSoldeAbsence(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SoldeAbsence soldeAbsence
    ) throws URISyntaxException {
        log.debug("REST request to update SoldeAbsence : {}, {}", id, soldeAbsence);
        if (soldeAbsence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soldeAbsence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soldeAbsenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SoldeAbsence result = soldeAbsenceService.save(soldeAbsence);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, soldeAbsence.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /solde-absences/:id} : Partial updates given fields of an existing soldeAbsence, field will ignore if it is null
     *
     * @param id the id of the soldeAbsence to save.
     * @param soldeAbsence the soldeAbsence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soldeAbsence,
     * or with status {@code 400 (Bad Request)} if the soldeAbsence is not valid,
     * or with status {@code 404 (Not Found)} if the soldeAbsence is not found,
     * or with status {@code 500 (Internal Server Error)} if the soldeAbsence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/solde-absences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SoldeAbsence> partialUpdateSoldeAbsence(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SoldeAbsence soldeAbsence
    ) throws URISyntaxException {
        log.debug("REST request to partial update SoldeAbsence partially : {}, {}", id, soldeAbsence);
        if (soldeAbsence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soldeAbsence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soldeAbsenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SoldeAbsence> result = soldeAbsenceService.partialUpdate(soldeAbsence);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, soldeAbsence.getId().toString())
        );
    }

    /**
     * {@code GET  /solde-absences} : get all the soldeAbsences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soldeAbsences in body.
     */
    @GetMapping("/solde-absences")
    public ResponseEntity<List<SoldeAbsence>> getAllSoldeAbsences(Pageable pageable) {
        log.debug("REST request to get a page of SoldeAbsences");
        Page<SoldeAbsence> page = soldeAbsenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /solde-absences/:id} : get the "id" soldeAbsence.
     *
     * @param id the id of the soldeAbsence to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the soldeAbsence, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solde-absences/{id}")
    public ResponseEntity<SoldeAbsence> getSoldeAbsence(@PathVariable Long id) {
        log.debug("REST request to get SoldeAbsence : {}", id);
        Optional<SoldeAbsence> soldeAbsence = soldeAbsenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(soldeAbsence);
    }

    /**
     * {@code DELETE  /solde-absences/:id} : delete the "id" soldeAbsence.
     *
     * @param id the id of the soldeAbsence to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solde-absences/{id}")
    public ResponseEntity<Void> deleteSoldeAbsence(@PathVariable Long id) {
        log.debug("REST request to delete SoldeAbsence : {}", id);
        soldeAbsenceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
