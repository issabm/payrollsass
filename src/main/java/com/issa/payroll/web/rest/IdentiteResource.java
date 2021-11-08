package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Identite;
import com.issa.payroll.repository.IdentiteRepository;
import com.issa.payroll.service.IdentiteService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Identite}.
 */
@RestController
@RequestMapping("/api")
public class IdentiteResource {

    private final Logger log = LoggerFactory.getLogger(IdentiteResource.class);

    private static final String ENTITY_NAME = "identite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IdentiteService identiteService;

    private final IdentiteRepository identiteRepository;

    public IdentiteResource(IdentiteService identiteService, IdentiteRepository identiteRepository) {
        this.identiteService = identiteService;
        this.identiteRepository = identiteRepository;
    }

    /**
     * {@code POST  /identites} : Create a new identite.
     *
     * @param identite the identite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new identite, or with status {@code 400 (Bad Request)} if the identite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/identites")
    public ResponseEntity<Identite> createIdentite(@RequestBody Identite identite) throws URISyntaxException {
        log.debug("REST request to save Identite : {}", identite);
        if (identite.getId() != null) {
            throw new BadRequestAlertException("A new identite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Identite result = identiteService.save(identite);
        return ResponseEntity
            .created(new URI("/api/identites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /identites/:id} : Updates an existing identite.
     *
     * @param id the id of the identite to save.
     * @param identite the identite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated identite,
     * or with status {@code 400 (Bad Request)} if the identite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the identite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/identites/{id}")
    public ResponseEntity<Identite> updateIdentite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Identite identite
    ) throws URISyntaxException {
        log.debug("REST request to update Identite : {}, {}", id, identite);
        if (identite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, identite.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!identiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Identite result = identiteService.save(identite);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, identite.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /identites/:id} : Partial updates given fields of an existing identite, field will ignore if it is null
     *
     * @param id the id of the identite to save.
     * @param identite the identite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated identite,
     * or with status {@code 400 (Bad Request)} if the identite is not valid,
     * or with status {@code 404 (Not Found)} if the identite is not found,
     * or with status {@code 500 (Internal Server Error)} if the identite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/identites/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Identite> partialUpdateIdentite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Identite identite
    ) throws URISyntaxException {
        log.debug("REST request to partial update Identite partially : {}, {}", id, identite);
        if (identite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, identite.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!identiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Identite> result = identiteService.partialUpdate(identite);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, identite.getId().toString())
        );
    }

    /**
     * {@code GET  /identites} : get all the identites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of identites in body.
     */
    @GetMapping("/identites")
    public ResponseEntity<List<Identite>> getAllIdentites(Pageable pageable) {
        log.debug("REST request to get a page of Identites");
        Page<Identite> page = identiteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /identites/:id} : get the "id" identite.
     *
     * @param id the id of the identite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the identite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/identites/{id}")
    public ResponseEntity<Identite> getIdentite(@PathVariable Long id) {
        log.debug("REST request to get Identite : {}", id);
        Optional<Identite> identite = identiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(identite);
    }

    /**
     * {@code DELETE  /identites/:id} : delete the "id" identite.
     *
     * @param id the id of the identite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/identites/{id}")
    public ResponseEntity<Void> deleteIdentite(@PathVariable Long id) {
        log.debug("REST request to delete Identite : {}", id);
        identiteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
