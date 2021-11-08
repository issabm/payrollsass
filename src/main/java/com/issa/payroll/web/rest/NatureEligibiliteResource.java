package com.issa.payroll.web.rest;

import com.issa.payroll.domain.NatureEligibilite;
import com.issa.payroll.repository.NatureEligibiliteRepository;
import com.issa.payroll.service.NatureEligibiliteService;
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
 * REST controller for managing {@link com.issa.payroll.domain.NatureEligibilite}.
 */
@RestController
@RequestMapping("/api")
public class NatureEligibiliteResource {

    private final Logger log = LoggerFactory.getLogger(NatureEligibiliteResource.class);

    private static final String ENTITY_NAME = "natureEligibilite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NatureEligibiliteService natureEligibiliteService;

    private final NatureEligibiliteRepository natureEligibiliteRepository;

    public NatureEligibiliteResource(
        NatureEligibiliteService natureEligibiliteService,
        NatureEligibiliteRepository natureEligibiliteRepository
    ) {
        this.natureEligibiliteService = natureEligibiliteService;
        this.natureEligibiliteRepository = natureEligibiliteRepository;
    }

    /**
     * {@code POST  /nature-eligibilites} : Create a new natureEligibilite.
     *
     * @param natureEligibilite the natureEligibilite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new natureEligibilite, or with status {@code 400 (Bad Request)} if the natureEligibilite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nature-eligibilites")
    public ResponseEntity<NatureEligibilite> createNatureEligibilite(@RequestBody NatureEligibilite natureEligibilite)
        throws URISyntaxException {
        log.debug("REST request to save NatureEligibilite : {}", natureEligibilite);
        if (natureEligibilite.getId() != null) {
            throw new BadRequestAlertException("A new natureEligibilite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NatureEligibilite result = natureEligibiliteService.save(natureEligibilite);
        return ResponseEntity
            .created(new URI("/api/nature-eligibilites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nature-eligibilites/:id} : Updates an existing natureEligibilite.
     *
     * @param id the id of the natureEligibilite to save.
     * @param natureEligibilite the natureEligibilite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureEligibilite,
     * or with status {@code 400 (Bad Request)} if the natureEligibilite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the natureEligibilite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nature-eligibilites/{id}")
    public ResponseEntity<NatureEligibilite> updateNatureEligibilite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureEligibilite natureEligibilite
    ) throws URISyntaxException {
        log.debug("REST request to update NatureEligibilite : {}, {}", id, natureEligibilite);
        if (natureEligibilite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureEligibilite.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureEligibiliteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NatureEligibilite result = natureEligibiliteService.save(natureEligibilite);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureEligibilite.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nature-eligibilites/:id} : Partial updates given fields of an existing natureEligibilite, field will ignore if it is null
     *
     * @param id the id of the natureEligibilite to save.
     * @param natureEligibilite the natureEligibilite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureEligibilite,
     * or with status {@code 400 (Bad Request)} if the natureEligibilite is not valid,
     * or with status {@code 404 (Not Found)} if the natureEligibilite is not found,
     * or with status {@code 500 (Internal Server Error)} if the natureEligibilite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nature-eligibilites/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NatureEligibilite> partialUpdateNatureEligibilite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureEligibilite natureEligibilite
    ) throws URISyntaxException {
        log.debug("REST request to partial update NatureEligibilite partially : {}, {}", id, natureEligibilite);
        if (natureEligibilite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureEligibilite.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureEligibiliteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NatureEligibilite> result = natureEligibiliteService.partialUpdate(natureEligibilite);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureEligibilite.getId().toString())
        );
    }

    /**
     * {@code GET  /nature-eligibilites} : get all the natureEligibilites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of natureEligibilites in body.
     */
    @GetMapping("/nature-eligibilites")
    public ResponseEntity<List<NatureEligibilite>> getAllNatureEligibilites(Pageable pageable) {
        log.debug("REST request to get a page of NatureEligibilites");
        Page<NatureEligibilite> page = natureEligibiliteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nature-eligibilites/:id} : get the "id" natureEligibilite.
     *
     * @param id the id of the natureEligibilite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the natureEligibilite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nature-eligibilites/{id}")
    public ResponseEntity<NatureEligibilite> getNatureEligibilite(@PathVariable Long id) {
        log.debug("REST request to get NatureEligibilite : {}", id);
        Optional<NatureEligibilite> natureEligibilite = natureEligibiliteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(natureEligibilite);
    }

    /**
     * {@code DELETE  /nature-eligibilites/:id} : delete the "id" natureEligibilite.
     *
     * @param id the id of the natureEligibilite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nature-eligibilites/{id}")
    public ResponseEntity<Void> deleteNatureEligibilite(@PathVariable Long id) {
        log.debug("REST request to delete NatureEligibilite : {}", id);
        natureEligibiliteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
