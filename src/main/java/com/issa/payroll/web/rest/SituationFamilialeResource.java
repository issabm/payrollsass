package com.issa.payroll.web.rest;

import com.issa.payroll.domain.SituationFamiliale;
import com.issa.payroll.repository.SituationFamilialeRepository;
import com.issa.payroll.service.SituationFamilialeService;
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
 * REST controller for managing {@link com.issa.payroll.domain.SituationFamiliale}.
 */
@RestController
@RequestMapping("/api")
public class SituationFamilialeResource {

    private final Logger log = LoggerFactory.getLogger(SituationFamilialeResource.class);

    private static final String ENTITY_NAME = "situationFamiliale";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SituationFamilialeService situationFamilialeService;

    private final SituationFamilialeRepository situationFamilialeRepository;

    public SituationFamilialeResource(
        SituationFamilialeService situationFamilialeService,
        SituationFamilialeRepository situationFamilialeRepository
    ) {
        this.situationFamilialeService = situationFamilialeService;
        this.situationFamilialeRepository = situationFamilialeRepository;
    }

    /**
     * {@code POST  /situation-familiales} : Create a new situationFamiliale.
     *
     * @param situationFamiliale the situationFamiliale to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new situationFamiliale, or with status {@code 400 (Bad Request)} if the situationFamiliale has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/situation-familiales")
    public ResponseEntity<SituationFamiliale> createSituationFamiliale(@RequestBody SituationFamiliale situationFamiliale)
        throws URISyntaxException {
        log.debug("REST request to save SituationFamiliale : {}", situationFamiliale);
        if (situationFamiliale.getId() != null) {
            throw new BadRequestAlertException("A new situationFamiliale cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SituationFamiliale result = situationFamilialeService.save(situationFamiliale);
        return ResponseEntity
            .created(new URI("/api/situation-familiales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /situation-familiales/:id} : Updates an existing situationFamiliale.
     *
     * @param id the id of the situationFamiliale to save.
     * @param situationFamiliale the situationFamiliale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situationFamiliale,
     * or with status {@code 400 (Bad Request)} if the situationFamiliale is not valid,
     * or with status {@code 500 (Internal Server Error)} if the situationFamiliale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/situation-familiales/{id}")
    public ResponseEntity<SituationFamiliale> updateSituationFamiliale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SituationFamiliale situationFamiliale
    ) throws URISyntaxException {
        log.debug("REST request to update SituationFamiliale : {}, {}", id, situationFamiliale);
        if (situationFamiliale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, situationFamiliale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!situationFamilialeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SituationFamiliale result = situationFamilialeService.save(situationFamiliale);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, situationFamiliale.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /situation-familiales/:id} : Partial updates given fields of an existing situationFamiliale, field will ignore if it is null
     *
     * @param id the id of the situationFamiliale to save.
     * @param situationFamiliale the situationFamiliale to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated situationFamiliale,
     * or with status {@code 400 (Bad Request)} if the situationFamiliale is not valid,
     * or with status {@code 404 (Not Found)} if the situationFamiliale is not found,
     * or with status {@code 500 (Internal Server Error)} if the situationFamiliale couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/situation-familiales/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SituationFamiliale> partialUpdateSituationFamiliale(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SituationFamiliale situationFamiliale
    ) throws URISyntaxException {
        log.debug("REST request to partial update SituationFamiliale partially : {}, {}", id, situationFamiliale);
        if (situationFamiliale.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, situationFamiliale.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!situationFamilialeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SituationFamiliale> result = situationFamilialeService.partialUpdate(situationFamiliale);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, situationFamiliale.getId().toString())
        );
    }

    /**
     * {@code GET  /situation-familiales} : get all the situationFamiliales.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of situationFamiliales in body.
     */
    @GetMapping("/situation-familiales")
    public ResponseEntity<List<SituationFamiliale>> getAllSituationFamiliales(Pageable pageable) {
        log.debug("REST request to get a page of SituationFamiliales");
        Page<SituationFamiliale> page = situationFamilialeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /situation-familiales/:id} : get the "id" situationFamiliale.
     *
     * @param id the id of the situationFamiliale to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the situationFamiliale, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/situation-familiales/{id}")
    public ResponseEntity<SituationFamiliale> getSituationFamiliale(@PathVariable Long id) {
        log.debug("REST request to get SituationFamiliale : {}", id);
        Optional<SituationFamiliale> situationFamiliale = situationFamilialeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(situationFamiliale);
    }

    /**
     * {@code DELETE  /situation-familiales/:id} : delete the "id" situationFamiliale.
     *
     * @param id the id of the situationFamiliale to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/situation-familiales/{id}")
    public ResponseEntity<Void> deleteSituationFamiliale(@PathVariable Long id) {
        log.debug("REST request to delete SituationFamiliale : {}", id);
        situationFamilialeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
