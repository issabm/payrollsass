package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Emploi;
import com.issa.payroll.repository.EmploiRepository;
import com.issa.payroll.service.EmploiService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Emploi}.
 */
@RestController
@RequestMapping("/api")
public class EmploiResource {

    private final Logger log = LoggerFactory.getLogger(EmploiResource.class);

    private static final String ENTITY_NAME = "emploi";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmploiService emploiService;

    private final EmploiRepository emploiRepository;

    public EmploiResource(EmploiService emploiService, EmploiRepository emploiRepository) {
        this.emploiService = emploiService;
        this.emploiRepository = emploiRepository;
    }

    /**
     * {@code POST  /emplois} : Create a new emploi.
     *
     * @param emploi the emploi to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new emploi, or with status {@code 400 (Bad Request)} if the emploi has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/emplois")
    public ResponseEntity<Emploi> createEmploi(@RequestBody Emploi emploi) throws URISyntaxException {
        log.debug("REST request to save Emploi : {}", emploi);
        if (emploi.getId() != null) {
            throw new BadRequestAlertException("A new emploi cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Emploi result = emploiService.save(emploi);
        return ResponseEntity
            .created(new URI("/api/emplois/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /emplois/:id} : Updates an existing emploi.
     *
     * @param id the id of the emploi to save.
     * @param emploi the emploi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emploi,
     * or with status {@code 400 (Bad Request)} if the emploi is not valid,
     * or with status {@code 500 (Internal Server Error)} if the emploi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/emplois/{id}")
    public ResponseEntity<Emploi> updateEmploi(@PathVariable(value = "id", required = false) final Long id, @RequestBody Emploi emploi)
        throws URISyntaxException {
        log.debug("REST request to update Emploi : {}, {}", id, emploi);
        if (emploi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emploi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emploiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Emploi result = emploiService.save(emploi);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emploi.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /emplois/:id} : Partial updates given fields of an existing emploi, field will ignore if it is null
     *
     * @param id the id of the emploi to save.
     * @param emploi the emploi to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated emploi,
     * or with status {@code 400 (Bad Request)} if the emploi is not valid,
     * or with status {@code 404 (Not Found)} if the emploi is not found,
     * or with status {@code 500 (Internal Server Error)} if the emploi couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/emplois/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Emploi> partialUpdateEmploi(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Emploi emploi
    ) throws URISyntaxException {
        log.debug("REST request to partial update Emploi partially : {}, {}", id, emploi);
        if (emploi.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, emploi.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!emploiRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Emploi> result = emploiService.partialUpdate(emploi);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, emploi.getId().toString())
        );
    }

    /**
     * {@code GET  /emplois} : get all the emplois.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of emplois in body.
     */
    @GetMapping("/emplois")
    public ResponseEntity<List<Emploi>> getAllEmplois(Pageable pageable) {
        log.debug("REST request to get a page of Emplois");
        Page<Emploi> page = emploiService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /emplois/:id} : get the "id" emploi.
     *
     * @param id the id of the emploi to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the emploi, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/emplois/{id}")
    public ResponseEntity<Emploi> getEmploi(@PathVariable Long id) {
        log.debug("REST request to get Emploi : {}", id);
        Optional<Emploi> emploi = emploiService.findOne(id);
        return ResponseUtil.wrapOrNotFound(emploi);
    }

    /**
     * {@code DELETE  /emplois/:id} : delete the "id" emploi.
     *
     * @param id the id of the emploi to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/emplois/{id}")
    public ResponseEntity<Void> deleteEmploi(@PathVariable Long id) {
        log.debug("REST request to delete Emploi : {}", id);
        emploiService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
