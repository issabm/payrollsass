package com.issa.payroll.web.rest;

import com.issa.payroll.domain.NatureAbsence;
import com.issa.payroll.repository.NatureAbsenceRepository;
import com.issa.payroll.service.NatureAbsenceService;
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
 * REST controller for managing {@link com.issa.payroll.domain.NatureAbsence}.
 */
@RestController
@RequestMapping("/api")
public class NatureAbsenceResource {

    private final Logger log = LoggerFactory.getLogger(NatureAbsenceResource.class);

    private static final String ENTITY_NAME = "natureAbsence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NatureAbsenceService natureAbsenceService;

    private final NatureAbsenceRepository natureAbsenceRepository;

    public NatureAbsenceResource(NatureAbsenceService natureAbsenceService, NatureAbsenceRepository natureAbsenceRepository) {
        this.natureAbsenceService = natureAbsenceService;
        this.natureAbsenceRepository = natureAbsenceRepository;
    }

    /**
     * {@code POST  /nature-absences} : Create a new natureAbsence.
     *
     * @param natureAbsence the natureAbsence to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new natureAbsence, or with status {@code 400 (Bad Request)} if the natureAbsence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nature-absences")
    public ResponseEntity<NatureAbsence> createNatureAbsence(@RequestBody NatureAbsence natureAbsence) throws URISyntaxException {
        log.debug("REST request to save NatureAbsence : {}", natureAbsence);
        if (natureAbsence.getId() != null) {
            throw new BadRequestAlertException("A new natureAbsence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NatureAbsence result = natureAbsenceService.save(natureAbsence);
        return ResponseEntity
            .created(new URI("/api/nature-absences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nature-absences/:id} : Updates an existing natureAbsence.
     *
     * @param id the id of the natureAbsence to save.
     * @param natureAbsence the natureAbsence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureAbsence,
     * or with status {@code 400 (Bad Request)} if the natureAbsence is not valid,
     * or with status {@code 500 (Internal Server Error)} if the natureAbsence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nature-absences/{id}")
    public ResponseEntity<NatureAbsence> updateNatureAbsence(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureAbsence natureAbsence
    ) throws URISyntaxException {
        log.debug("REST request to update NatureAbsence : {}, {}", id, natureAbsence);
        if (natureAbsence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureAbsence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureAbsenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NatureAbsence result = natureAbsenceService.save(natureAbsence);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureAbsence.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nature-absences/:id} : Partial updates given fields of an existing natureAbsence, field will ignore if it is null
     *
     * @param id the id of the natureAbsence to save.
     * @param natureAbsence the natureAbsence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureAbsence,
     * or with status {@code 400 (Bad Request)} if the natureAbsence is not valid,
     * or with status {@code 404 (Not Found)} if the natureAbsence is not found,
     * or with status {@code 500 (Internal Server Error)} if the natureAbsence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nature-absences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NatureAbsence> partialUpdateNatureAbsence(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureAbsence natureAbsence
    ) throws URISyntaxException {
        log.debug("REST request to partial update NatureAbsence partially : {}, {}", id, natureAbsence);
        if (natureAbsence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureAbsence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureAbsenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NatureAbsence> result = natureAbsenceService.partialUpdate(natureAbsence);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureAbsence.getId().toString())
        );
    }

    /**
     * {@code GET  /nature-absences} : get all the natureAbsences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of natureAbsences in body.
     */
    @GetMapping("/nature-absences")
    public ResponseEntity<List<NatureAbsence>> getAllNatureAbsences(Pageable pageable) {
        log.debug("REST request to get a page of NatureAbsences");
        Page<NatureAbsence> page = natureAbsenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nature-absences/:id} : get the "id" natureAbsence.
     *
     * @param id the id of the natureAbsence to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the natureAbsence, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nature-absences/{id}")
    public ResponseEntity<NatureAbsence> getNatureAbsence(@PathVariable Long id) {
        log.debug("REST request to get NatureAbsence : {}", id);
        Optional<NatureAbsence> natureAbsence = natureAbsenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(natureAbsence);
    }

    /**
     * {@code DELETE  /nature-absences/:id} : delete the "id" natureAbsence.
     *
     * @param id the id of the natureAbsence to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nature-absences/{id}")
    public ResponseEntity<Void> deleteNatureAbsence(@PathVariable Long id) {
        log.debug("REST request to delete NatureAbsence : {}", id);
        natureAbsenceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
