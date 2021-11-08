package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Eligibilite;
import com.issa.payroll.repository.EligibiliteRepository;
import com.issa.payroll.service.EligibiliteService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Eligibilite}.
 */
@RestController
@RequestMapping("/api")
public class EligibiliteResource {

    private final Logger log = LoggerFactory.getLogger(EligibiliteResource.class);

    private static final String ENTITY_NAME = "eligibilite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EligibiliteService eligibiliteService;

    private final EligibiliteRepository eligibiliteRepository;

    public EligibiliteResource(EligibiliteService eligibiliteService, EligibiliteRepository eligibiliteRepository) {
        this.eligibiliteService = eligibiliteService;
        this.eligibiliteRepository = eligibiliteRepository;
    }

    /**
     * {@code POST  /eligibilites} : Create a new eligibilite.
     *
     * @param eligibilite the eligibilite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eligibilite, or with status {@code 400 (Bad Request)} if the eligibilite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eligibilites")
    public ResponseEntity<Eligibilite> createEligibilite(@RequestBody Eligibilite eligibilite) throws URISyntaxException {
        log.debug("REST request to save Eligibilite : {}", eligibilite);
        if (eligibilite.getId() != null) {
            throw new BadRequestAlertException("A new eligibilite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Eligibilite result = eligibiliteService.save(eligibilite);
        return ResponseEntity
            .created(new URI("/api/eligibilites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eligibilites/:id} : Updates an existing eligibilite.
     *
     * @param id the id of the eligibilite to save.
     * @param eligibilite the eligibilite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eligibilite,
     * or with status {@code 400 (Bad Request)} if the eligibilite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eligibilite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eligibilites/{id}")
    public ResponseEntity<Eligibilite> updateEligibilite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Eligibilite eligibilite
    ) throws URISyntaxException {
        log.debug("REST request to update Eligibilite : {}, {}", id, eligibilite);
        if (eligibilite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eligibilite.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eligibiliteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Eligibilite result = eligibiliteService.save(eligibilite);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eligibilite.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /eligibilites/:id} : Partial updates given fields of an existing eligibilite, field will ignore if it is null
     *
     * @param id the id of the eligibilite to save.
     * @param eligibilite the eligibilite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eligibilite,
     * or with status {@code 400 (Bad Request)} if the eligibilite is not valid,
     * or with status {@code 404 (Not Found)} if the eligibilite is not found,
     * or with status {@code 500 (Internal Server Error)} if the eligibilite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/eligibilites/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Eligibilite> partialUpdateEligibilite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Eligibilite eligibilite
    ) throws URISyntaxException {
        log.debug("REST request to partial update Eligibilite partially : {}, {}", id, eligibilite);
        if (eligibilite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eligibilite.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eligibiliteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Eligibilite> result = eligibiliteService.partialUpdate(eligibilite);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eligibilite.getId().toString())
        );
    }

    /**
     * {@code GET  /eligibilites} : get all the eligibilites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eligibilites in body.
     */
    @GetMapping("/eligibilites")
    public ResponseEntity<List<Eligibilite>> getAllEligibilites(Pageable pageable) {
        log.debug("REST request to get a page of Eligibilites");
        Page<Eligibilite> page = eligibiliteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /eligibilites/:id} : get the "id" eligibilite.
     *
     * @param id the id of the eligibilite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eligibilite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eligibilites/{id}")
    public ResponseEntity<Eligibilite> getEligibilite(@PathVariable Long id) {
        log.debug("REST request to get Eligibilite : {}", id);
        Optional<Eligibilite> eligibilite = eligibiliteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eligibilite);
    }

    /**
     * {@code DELETE  /eligibilites/:id} : delete the "id" eligibilite.
     *
     * @param id the id of the eligibilite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eligibilites/{id}")
    public ResponseEntity<Void> deleteEligibilite(@PathVariable Long id) {
        log.debug("REST request to delete Eligibilite : {}", id);
        eligibiliteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
