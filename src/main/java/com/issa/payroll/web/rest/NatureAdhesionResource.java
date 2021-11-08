package com.issa.payroll.web.rest;

import com.issa.payroll.domain.NatureAdhesion;
import com.issa.payroll.repository.NatureAdhesionRepository;
import com.issa.payroll.service.NatureAdhesionService;
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
 * REST controller for managing {@link com.issa.payroll.domain.NatureAdhesion}.
 */
@RestController
@RequestMapping("/api")
public class NatureAdhesionResource {

    private final Logger log = LoggerFactory.getLogger(NatureAdhesionResource.class);

    private static final String ENTITY_NAME = "natureAdhesion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NatureAdhesionService natureAdhesionService;

    private final NatureAdhesionRepository natureAdhesionRepository;

    public NatureAdhesionResource(NatureAdhesionService natureAdhesionService, NatureAdhesionRepository natureAdhesionRepository) {
        this.natureAdhesionService = natureAdhesionService;
        this.natureAdhesionRepository = natureAdhesionRepository;
    }

    /**
     * {@code POST  /nature-adhesions} : Create a new natureAdhesion.
     *
     * @param natureAdhesion the natureAdhesion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new natureAdhesion, or with status {@code 400 (Bad Request)} if the natureAdhesion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nature-adhesions")
    public ResponseEntity<NatureAdhesion> createNatureAdhesion(@RequestBody NatureAdhesion natureAdhesion) throws URISyntaxException {
        log.debug("REST request to save NatureAdhesion : {}", natureAdhesion);
        if (natureAdhesion.getId() != null) {
            throw new BadRequestAlertException("A new natureAdhesion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NatureAdhesion result = natureAdhesionService.save(natureAdhesion);
        return ResponseEntity
            .created(new URI("/api/nature-adhesions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nature-adhesions/:id} : Updates an existing natureAdhesion.
     *
     * @param id the id of the natureAdhesion to save.
     * @param natureAdhesion the natureAdhesion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureAdhesion,
     * or with status {@code 400 (Bad Request)} if the natureAdhesion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the natureAdhesion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nature-adhesions/{id}")
    public ResponseEntity<NatureAdhesion> updateNatureAdhesion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureAdhesion natureAdhesion
    ) throws URISyntaxException {
        log.debug("REST request to update NatureAdhesion : {}, {}", id, natureAdhesion);
        if (natureAdhesion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureAdhesion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureAdhesionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NatureAdhesion result = natureAdhesionService.save(natureAdhesion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureAdhesion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nature-adhesions/:id} : Partial updates given fields of an existing natureAdhesion, field will ignore if it is null
     *
     * @param id the id of the natureAdhesion to save.
     * @param natureAdhesion the natureAdhesion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureAdhesion,
     * or with status {@code 400 (Bad Request)} if the natureAdhesion is not valid,
     * or with status {@code 404 (Not Found)} if the natureAdhesion is not found,
     * or with status {@code 500 (Internal Server Error)} if the natureAdhesion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nature-adhesions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NatureAdhesion> partialUpdateNatureAdhesion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureAdhesion natureAdhesion
    ) throws URISyntaxException {
        log.debug("REST request to partial update NatureAdhesion partially : {}, {}", id, natureAdhesion);
        if (natureAdhesion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureAdhesion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureAdhesionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NatureAdhesion> result = natureAdhesionService.partialUpdate(natureAdhesion);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureAdhesion.getId().toString())
        );
    }

    /**
     * {@code GET  /nature-adhesions} : get all the natureAdhesions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of natureAdhesions in body.
     */
    @GetMapping("/nature-adhesions")
    public ResponseEntity<List<NatureAdhesion>> getAllNatureAdhesions(Pageable pageable) {
        log.debug("REST request to get a page of NatureAdhesions");
        Page<NatureAdhesion> page = natureAdhesionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nature-adhesions/:id} : get the "id" natureAdhesion.
     *
     * @param id the id of the natureAdhesion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the natureAdhesion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nature-adhesions/{id}")
    public ResponseEntity<NatureAdhesion> getNatureAdhesion(@PathVariable Long id) {
        log.debug("REST request to get NatureAdhesion : {}", id);
        Optional<NatureAdhesion> natureAdhesion = natureAdhesionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(natureAdhesion);
    }

    /**
     * {@code DELETE  /nature-adhesions/:id} : delete the "id" natureAdhesion.
     *
     * @param id the id of the natureAdhesion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nature-adhesions/{id}")
    public ResponseEntity<Void> deleteNatureAdhesion(@PathVariable Long id) {
        log.debug("REST request to delete NatureAdhesion : {}", id);
        natureAdhesionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
