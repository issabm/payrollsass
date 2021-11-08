package com.issa.payroll.web.rest;

import com.issa.payroll.domain.SoldeAbsencePaie;
import com.issa.payroll.repository.SoldeAbsencePaieRepository;
import com.issa.payroll.service.SoldeAbsencePaieService;
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
 * REST controller for managing {@link com.issa.payroll.domain.SoldeAbsencePaie}.
 */
@RestController
@RequestMapping("/api")
public class SoldeAbsencePaieResource {

    private final Logger log = LoggerFactory.getLogger(SoldeAbsencePaieResource.class);

    private static final String ENTITY_NAME = "soldeAbsencePaie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SoldeAbsencePaieService soldeAbsencePaieService;

    private final SoldeAbsencePaieRepository soldeAbsencePaieRepository;

    public SoldeAbsencePaieResource(
        SoldeAbsencePaieService soldeAbsencePaieService,
        SoldeAbsencePaieRepository soldeAbsencePaieRepository
    ) {
        this.soldeAbsencePaieService = soldeAbsencePaieService;
        this.soldeAbsencePaieRepository = soldeAbsencePaieRepository;
    }

    /**
     * {@code POST  /solde-absence-paies} : Create a new soldeAbsencePaie.
     *
     * @param soldeAbsencePaie the soldeAbsencePaie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new soldeAbsencePaie, or with status {@code 400 (Bad Request)} if the soldeAbsencePaie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/solde-absence-paies")
    public ResponseEntity<SoldeAbsencePaie> createSoldeAbsencePaie(@RequestBody SoldeAbsencePaie soldeAbsencePaie)
        throws URISyntaxException {
        log.debug("REST request to save SoldeAbsencePaie : {}", soldeAbsencePaie);
        if (soldeAbsencePaie.getId() != null) {
            throw new BadRequestAlertException("A new soldeAbsencePaie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SoldeAbsencePaie result = soldeAbsencePaieService.save(soldeAbsencePaie);
        return ResponseEntity
            .created(new URI("/api/solde-absence-paies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /solde-absence-paies/:id} : Updates an existing soldeAbsencePaie.
     *
     * @param id the id of the soldeAbsencePaie to save.
     * @param soldeAbsencePaie the soldeAbsencePaie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soldeAbsencePaie,
     * or with status {@code 400 (Bad Request)} if the soldeAbsencePaie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the soldeAbsencePaie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/solde-absence-paies/{id}")
    public ResponseEntity<SoldeAbsencePaie> updateSoldeAbsencePaie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SoldeAbsencePaie soldeAbsencePaie
    ) throws URISyntaxException {
        log.debug("REST request to update SoldeAbsencePaie : {}, {}", id, soldeAbsencePaie);
        if (soldeAbsencePaie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soldeAbsencePaie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soldeAbsencePaieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SoldeAbsencePaie result = soldeAbsencePaieService.save(soldeAbsencePaie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, soldeAbsencePaie.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /solde-absence-paies/:id} : Partial updates given fields of an existing soldeAbsencePaie, field will ignore if it is null
     *
     * @param id the id of the soldeAbsencePaie to save.
     * @param soldeAbsencePaie the soldeAbsencePaie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated soldeAbsencePaie,
     * or with status {@code 400 (Bad Request)} if the soldeAbsencePaie is not valid,
     * or with status {@code 404 (Not Found)} if the soldeAbsencePaie is not found,
     * or with status {@code 500 (Internal Server Error)} if the soldeAbsencePaie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/solde-absence-paies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SoldeAbsencePaie> partialUpdateSoldeAbsencePaie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SoldeAbsencePaie soldeAbsencePaie
    ) throws URISyntaxException {
        log.debug("REST request to partial update SoldeAbsencePaie partially : {}, {}", id, soldeAbsencePaie);
        if (soldeAbsencePaie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, soldeAbsencePaie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!soldeAbsencePaieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SoldeAbsencePaie> result = soldeAbsencePaieService.partialUpdate(soldeAbsencePaie);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, soldeAbsencePaie.getId().toString())
        );
    }

    /**
     * {@code GET  /solde-absence-paies} : get all the soldeAbsencePaies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of soldeAbsencePaies in body.
     */
    @GetMapping("/solde-absence-paies")
    public ResponseEntity<List<SoldeAbsencePaie>> getAllSoldeAbsencePaies(Pageable pageable) {
        log.debug("REST request to get a page of SoldeAbsencePaies");
        Page<SoldeAbsencePaie> page = soldeAbsencePaieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /solde-absence-paies/:id} : get the "id" soldeAbsencePaie.
     *
     * @param id the id of the soldeAbsencePaie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the soldeAbsencePaie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/solde-absence-paies/{id}")
    public ResponseEntity<SoldeAbsencePaie> getSoldeAbsencePaie(@PathVariable Long id) {
        log.debug("REST request to get SoldeAbsencePaie : {}", id);
        Optional<SoldeAbsencePaie> soldeAbsencePaie = soldeAbsencePaieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(soldeAbsencePaie);
    }

    /**
     * {@code DELETE  /solde-absence-paies/:id} : delete the "id" soldeAbsencePaie.
     *
     * @param id the id of the soldeAbsencePaie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/solde-absence-paies/{id}")
    public ResponseEntity<Void> deleteSoldeAbsencePaie(@PathVariable Long id) {
        log.debug("REST request to delete SoldeAbsencePaie : {}", id);
        soldeAbsencePaieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
