package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Frequence;
import com.issa.payroll.repository.FrequenceRepository;
import com.issa.payroll.service.FrequenceService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Frequence}.
 */
@RestController
@RequestMapping("/api")
public class FrequenceResource {

    private final Logger log = LoggerFactory.getLogger(FrequenceResource.class);

    private static final String ENTITY_NAME = "frequence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FrequenceService frequenceService;

    private final FrequenceRepository frequenceRepository;

    public FrequenceResource(FrequenceService frequenceService, FrequenceRepository frequenceRepository) {
        this.frequenceService = frequenceService;
        this.frequenceRepository = frequenceRepository;
    }

    /**
     * {@code POST  /frequences} : Create a new frequence.
     *
     * @param frequence the frequence to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new frequence, or with status {@code 400 (Bad Request)} if the frequence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/frequences")
    public ResponseEntity<Frequence> createFrequence(@RequestBody Frequence frequence) throws URISyntaxException {
        log.debug("REST request to save Frequence : {}", frequence);
        if (frequence.getId() != null) {
            throw new BadRequestAlertException("A new frequence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Frequence result = frequenceService.save(frequence);
        return ResponseEntity
            .created(new URI("/api/frequences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /frequences/:id} : Updates an existing frequence.
     *
     * @param id the id of the frequence to save.
     * @param frequence the frequence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frequence,
     * or with status {@code 400 (Bad Request)} if the frequence is not valid,
     * or with status {@code 500 (Internal Server Error)} if the frequence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/frequences/{id}")
    public ResponseEntity<Frequence> updateFrequence(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Frequence frequence
    ) throws URISyntaxException {
        log.debug("REST request to update Frequence : {}, {}", id, frequence);
        if (frequence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, frequence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!frequenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Frequence result = frequenceService.save(frequence);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, frequence.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /frequences/:id} : Partial updates given fields of an existing frequence, field will ignore if it is null
     *
     * @param id the id of the frequence to save.
     * @param frequence the frequence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frequence,
     * or with status {@code 400 (Bad Request)} if the frequence is not valid,
     * or with status {@code 404 (Not Found)} if the frequence is not found,
     * or with status {@code 500 (Internal Server Error)} if the frequence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/frequences/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Frequence> partialUpdateFrequence(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Frequence frequence
    ) throws URISyntaxException {
        log.debug("REST request to partial update Frequence partially : {}, {}", id, frequence);
        if (frequence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, frequence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!frequenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Frequence> result = frequenceService.partialUpdate(frequence);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, frequence.getId().toString())
        );
    }

    /**
     * {@code GET  /frequences} : get all the frequences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of frequences in body.
     */
    @GetMapping("/frequences")
    public ResponseEntity<List<Frequence>> getAllFrequences(Pageable pageable) {
        log.debug("REST request to get a page of Frequences");
        Page<Frequence> page = frequenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /frequences/:id} : get the "id" frequence.
     *
     * @param id the id of the frequence to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the frequence, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/frequences/{id}")
    public ResponseEntity<Frequence> getFrequence(@PathVariable Long id) {
        log.debug("REST request to get Frequence : {}", id);
        Optional<Frequence> frequence = frequenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(frequence);
    }

    /**
     * {@code DELETE  /frequences/:id} : delete the "id" frequence.
     *
     * @param id the id of the frequence to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/frequences/{id}")
    public ResponseEntity<Void> deleteFrequence(@PathVariable Long id) {
        log.debug("REST request to delete Frequence : {}", id);
        frequenceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
