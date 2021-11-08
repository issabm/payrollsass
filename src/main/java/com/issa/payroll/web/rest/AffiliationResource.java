package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Affiliation;
import com.issa.payroll.repository.AffiliationRepository;
import com.issa.payroll.service.AffiliationService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Affiliation}.
 */
@RestController
@RequestMapping("/api")
public class AffiliationResource {

    private final Logger log = LoggerFactory.getLogger(AffiliationResource.class);

    private static final String ENTITY_NAME = "affiliation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AffiliationService affiliationService;

    private final AffiliationRepository affiliationRepository;

    public AffiliationResource(AffiliationService affiliationService, AffiliationRepository affiliationRepository) {
        this.affiliationService = affiliationService;
        this.affiliationRepository = affiliationRepository;
    }

    /**
     * {@code POST  /affiliations} : Create a new affiliation.
     *
     * @param affiliation the affiliation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new affiliation, or with status {@code 400 (Bad Request)} if the affiliation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/affiliations")
    public ResponseEntity<Affiliation> createAffiliation(@RequestBody Affiliation affiliation) throws URISyntaxException {
        log.debug("REST request to save Affiliation : {}", affiliation);
        if (affiliation.getId() != null) {
            throw new BadRequestAlertException("A new affiliation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Affiliation result = affiliationService.save(affiliation);
        return ResponseEntity
            .created(new URI("/api/affiliations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /affiliations/:id} : Updates an existing affiliation.
     *
     * @param id the id of the affiliation to save.
     * @param affiliation the affiliation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated affiliation,
     * or with status {@code 400 (Bad Request)} if the affiliation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the affiliation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/affiliations/{id}")
    public ResponseEntity<Affiliation> updateAffiliation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Affiliation affiliation
    ) throws URISyntaxException {
        log.debug("REST request to update Affiliation : {}, {}", id, affiliation);
        if (affiliation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, affiliation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!affiliationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Affiliation result = affiliationService.save(affiliation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, affiliation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /affiliations/:id} : Partial updates given fields of an existing affiliation, field will ignore if it is null
     *
     * @param id the id of the affiliation to save.
     * @param affiliation the affiliation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated affiliation,
     * or with status {@code 400 (Bad Request)} if the affiliation is not valid,
     * or with status {@code 404 (Not Found)} if the affiliation is not found,
     * or with status {@code 500 (Internal Server Error)} if the affiliation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/affiliations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Affiliation> partialUpdateAffiliation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Affiliation affiliation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Affiliation partially : {}, {}", id, affiliation);
        if (affiliation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, affiliation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!affiliationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Affiliation> result = affiliationService.partialUpdate(affiliation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, affiliation.getId().toString())
        );
    }

    /**
     * {@code GET  /affiliations} : get all the affiliations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of affiliations in body.
     */
    @GetMapping("/affiliations")
    public ResponseEntity<List<Affiliation>> getAllAffiliations(Pageable pageable) {
        log.debug("REST request to get a page of Affiliations");
        Page<Affiliation> page = affiliationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /affiliations/:id} : get the "id" affiliation.
     *
     * @param id the id of the affiliation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the affiliation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/affiliations/{id}")
    public ResponseEntity<Affiliation> getAffiliation(@PathVariable Long id) {
        log.debug("REST request to get Affiliation : {}", id);
        Optional<Affiliation> affiliation = affiliationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(affiliation);
    }

    /**
     * {@code DELETE  /affiliations/:id} : delete the "id" affiliation.
     *
     * @param id the id of the affiliation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/affiliations/{id}")
    public ResponseEntity<Void> deleteAffiliation(@PathVariable Long id) {
        log.debug("REST request to delete Affiliation : {}", id);
        affiliationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
