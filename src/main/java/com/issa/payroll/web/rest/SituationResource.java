package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Situation;
import com.issa.payroll.repository.SituationRepository;
import com.issa.payroll.service.SituationService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Situation}.
 */
@RestController
@RequestMapping("/api")
public class SituationResource {

    private final Logger log = LoggerFactory.getLogger(SituationResource.class);

    private static final String ENTITY_NAME = "situation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SituationService situationService;

    private final SituationRepository situationRepository;

    public SituationResource(SituationService situationService, SituationRepository situationRepository) {
        this.situationService = situationService;
        this.situationRepository = situationRepository;
    }

    /**
     * {@code POST  /situations} : Create a new situation.
     *
     * @param situation the situation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new situation, or with status {@code 400 (Bad Request)} if the situation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/situations")
    public ResponseEntity<Situation> createSituation(@RequestBody Situation situation) throws URISyntaxException {
        log.debug("REST request to save Situation : {}", situation);
        if (situation.getId() != null) {
            throw new BadRequestAlertException("A new situation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Situation result = situationService.save(situation);
        return ResponseEntity
            .created(new URI("/api/situations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /situations/:id} : Updates an existing situation.
     *
     * @param id the id of the situation to save.
     * @param situation the situation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situation,
     * or with status {@code 400 (Bad Request)} if the situation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the situation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/situations/{id}")
    public ResponseEntity<Situation> updateSituation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Situation situation
    ) throws URISyntaxException {
        log.debug("REST request to update Situation : {}, {}", id, situation);
        if (situation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, situation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!situationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Situation result = situationService.save(situation);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, situation.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /situations/:id} : Partial updates given fields of an existing situation, field will ignore if it is null
     *
     * @param id the id of the situation to save.
     * @param situation the situation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situation,
     * or with status {@code 400 (Bad Request)} if the situation is not valid,
     * or with status {@code 404 (Not Found)} if the situation is not found,
     * or with status {@code 500 (Internal Server Error)} if the situation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/situations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Situation> partialUpdateSituation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Situation situation
    ) throws URISyntaxException {
        log.debug("REST request to partial update Situation partially : {}, {}", id, situation);
        if (situation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, situation.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!situationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Situation> result = situationService.partialUpdate(situation);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, situation.getId().toString())
        );
    }

    /**
     * {@code GET  /situations} : get all the situations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of situations in body.
     */
    @GetMapping("/situations")
    public ResponseEntity<List<Situation>> getAllSituations(Pageable pageable) {
        log.debug("REST request to get a page of Situations");
        Page<Situation> page = situationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /situations/:id} : get the "id" situation.
     *
     * @param id the id of the situation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the situation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/situations/{id}")
    public ResponseEntity<Situation> getSituation(@PathVariable Long id) {
        log.debug("REST request to get Situation : {}", id);
        Optional<Situation> situation = situationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(situation);
    }

    /**
     * {@code DELETE  /situations/:id} : delete the "id" situation.
     *
     * @param id the id of the situation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/situations/{id}")
    public ResponseEntity<Void> deleteSituation(@PathVariable Long id) {
        log.debug("REST request to delete Situation : {}", id);
        situationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
