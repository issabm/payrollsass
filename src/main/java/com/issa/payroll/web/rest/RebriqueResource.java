package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Rebrique;
import com.issa.payroll.repository.RebriqueRepository;
import com.issa.payroll.service.RebriqueService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Rebrique}.
 */
@RestController
@RequestMapping("/api")
public class RebriqueResource {

    private final Logger log = LoggerFactory.getLogger(RebriqueResource.class);

    private static final String ENTITY_NAME = "rebrique";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RebriqueService rebriqueService;

    private final RebriqueRepository rebriqueRepository;

    public RebriqueResource(RebriqueService rebriqueService, RebriqueRepository rebriqueRepository) {
        this.rebriqueService = rebriqueService;
        this.rebriqueRepository = rebriqueRepository;
    }

    /**
     * {@code POST  /rebriques} : Create a new rebrique.
     *
     * @param rebrique the rebrique to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rebrique, or with status {@code 400 (Bad Request)} if the rebrique has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rebriques")
    public ResponseEntity<Rebrique> createRebrique(@RequestBody Rebrique rebrique) throws URISyntaxException {
        log.debug("REST request to save Rebrique : {}", rebrique);
        if (rebrique.getId() != null) {
            throw new BadRequestAlertException("A new rebrique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rebrique result = rebriqueService.save(rebrique);
        return ResponseEntity
            .created(new URI("/api/rebriques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rebriques/:id} : Updates an existing rebrique.
     *
     * @param id the id of the rebrique to save.
     * @param rebrique the rebrique to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rebrique,
     * or with status {@code 400 (Bad Request)} if the rebrique is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rebrique couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rebriques/{id}")
    public ResponseEntity<Rebrique> updateRebrique(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Rebrique rebrique
    ) throws URISyntaxException {
        log.debug("REST request to update Rebrique : {}, {}", id, rebrique);
        if (rebrique.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rebrique.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rebriqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Rebrique result = rebriqueService.save(rebrique);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rebrique.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /rebriques/:id} : Partial updates given fields of an existing rebrique, field will ignore if it is null
     *
     * @param id the id of the rebrique to save.
     * @param rebrique the rebrique to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rebrique,
     * or with status {@code 400 (Bad Request)} if the rebrique is not valid,
     * or with status {@code 404 (Not Found)} if the rebrique is not found,
     * or with status {@code 500 (Internal Server Error)} if the rebrique couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/rebriques/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Rebrique> partialUpdateRebrique(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Rebrique rebrique
    ) throws URISyntaxException {
        log.debug("REST request to partial update Rebrique partially : {}, {}", id, rebrique);
        if (rebrique.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rebrique.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rebriqueRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Rebrique> result = rebriqueService.partialUpdate(rebrique);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rebrique.getId().toString())
        );
    }

    /**
     * {@code GET  /rebriques} : get all the rebriques.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rebriques in body.
     */
    @GetMapping("/rebriques")
    public ResponseEntity<List<Rebrique>> getAllRebriques(Pageable pageable) {
        log.debug("REST request to get a page of Rebriques");
        Page<Rebrique> page = rebriqueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rebriques/:id} : get the "id" rebrique.
     *
     * @param id the id of the rebrique to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rebrique, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rebriques/{id}")
    public ResponseEntity<Rebrique> getRebrique(@PathVariable Long id) {
        log.debug("REST request to get Rebrique : {}", id);
        Optional<Rebrique> rebrique = rebriqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rebrique);
    }

    /**
     * {@code DELETE  /rebriques/:id} : delete the "id" rebrique.
     *
     * @param id the id of the rebrique to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rebriques/{id}")
    public ResponseEntity<Void> deleteRebrique(@PathVariable Long id) {
        log.debug("REST request to delete Rebrique : {}", id);
        rebriqueService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
