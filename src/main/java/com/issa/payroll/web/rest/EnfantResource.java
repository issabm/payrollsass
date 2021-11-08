package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Enfant;
import com.issa.payroll.repository.EnfantRepository;
import com.issa.payroll.service.EnfantService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Enfant}.
 */
@RestController
@RequestMapping("/api")
public class EnfantResource {

    private final Logger log = LoggerFactory.getLogger(EnfantResource.class);

    private static final String ENTITY_NAME = "enfant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EnfantService enfantService;

    private final EnfantRepository enfantRepository;

    public EnfantResource(EnfantService enfantService, EnfantRepository enfantRepository) {
        this.enfantService = enfantService;
        this.enfantRepository = enfantRepository;
    }

    /**
     * {@code POST  /enfants} : Create a new enfant.
     *
     * @param enfant the enfant to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new enfant, or with status {@code 400 (Bad Request)} if the enfant has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/enfants")
    public ResponseEntity<Enfant> createEnfant(@RequestBody Enfant enfant) throws URISyntaxException {
        log.debug("REST request to save Enfant : {}", enfant);
        if (enfant.getId() != null) {
            throw new BadRequestAlertException("A new enfant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Enfant result = enfantService.save(enfant);
        return ResponseEntity
            .created(new URI("/api/enfants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /enfants/:id} : Updates an existing enfant.
     *
     * @param id the id of the enfant to save.
     * @param enfant the enfant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enfant,
     * or with status {@code 400 (Bad Request)} if the enfant is not valid,
     * or with status {@code 500 (Internal Server Error)} if the enfant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/enfants/{id}")
    public ResponseEntity<Enfant> updateEnfant(@PathVariable(value = "id", required = false) final Long id, @RequestBody Enfant enfant)
        throws URISyntaxException {
        log.debug("REST request to update Enfant : {}, {}", id, enfant);
        if (enfant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enfant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enfantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Enfant result = enfantService.save(enfant);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enfant.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /enfants/:id} : Partial updates given fields of an existing enfant, field will ignore if it is null
     *
     * @param id the id of the enfant to save.
     * @param enfant the enfant to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated enfant,
     * or with status {@code 400 (Bad Request)} if the enfant is not valid,
     * or with status {@code 404 (Not Found)} if the enfant is not found,
     * or with status {@code 500 (Internal Server Error)} if the enfant couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/enfants/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Enfant> partialUpdateEnfant(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Enfant enfant
    ) throws URISyntaxException {
        log.debug("REST request to partial update Enfant partially : {}, {}", id, enfant);
        if (enfant.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, enfant.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!enfantRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Enfant> result = enfantService.partialUpdate(enfant);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, enfant.getId().toString())
        );
    }

    /**
     * {@code GET  /enfants} : get all the enfants.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of enfants in body.
     */
    @GetMapping("/enfants")
    public ResponseEntity<List<Enfant>> getAllEnfants(Pageable pageable) {
        log.debug("REST request to get a page of Enfants");
        Page<Enfant> page = enfantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /enfants/:id} : get the "id" enfant.
     *
     * @param id the id of the enfant to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the enfant, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/enfants/{id}")
    public ResponseEntity<Enfant> getEnfant(@PathVariable Long id) {
        log.debug("REST request to get Enfant : {}", id);
        Optional<Enfant> enfant = enfantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enfant);
    }

    /**
     * {@code DELETE  /enfants/:id} : delete the "id" enfant.
     *
     * @param id the id of the enfant to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/enfants/{id}")
    public ResponseEntity<Void> deleteEnfant(@PathVariable Long id) {
        log.debug("REST request to delete Enfant : {}", id);
        enfantService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
