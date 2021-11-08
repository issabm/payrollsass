package com.issa.payroll.web.rest;

import com.issa.payroll.domain.PalierPlate;
import com.issa.payroll.repository.PalierPlateRepository;
import com.issa.payroll.service.PalierPlateService;
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
 * REST controller for managing {@link com.issa.payroll.domain.PalierPlate}.
 */
@RestController
@RequestMapping("/api")
public class PalierPlateResource {

    private final Logger log = LoggerFactory.getLogger(PalierPlateResource.class);

    private static final String ENTITY_NAME = "palierPlate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PalierPlateService palierPlateService;

    private final PalierPlateRepository palierPlateRepository;

    public PalierPlateResource(PalierPlateService palierPlateService, PalierPlateRepository palierPlateRepository) {
        this.palierPlateService = palierPlateService;
        this.palierPlateRepository = palierPlateRepository;
    }

    /**
     * {@code POST  /palier-plates} : Create a new palierPlate.
     *
     * @param palierPlate the palierPlate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new palierPlate, or with status {@code 400 (Bad Request)} if the palierPlate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/palier-plates")
    public ResponseEntity<PalierPlate> createPalierPlate(@RequestBody PalierPlate palierPlate) throws URISyntaxException {
        log.debug("REST request to save PalierPlate : {}", palierPlate);
        if (palierPlate.getId() != null) {
            throw new BadRequestAlertException("A new palierPlate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PalierPlate result = palierPlateService.save(palierPlate);
        return ResponseEntity
            .created(new URI("/api/palier-plates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /palier-plates/:id} : Updates an existing palierPlate.
     *
     * @param id the id of the palierPlate to save.
     * @param palierPlate the palierPlate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated palierPlate,
     * or with status {@code 400 (Bad Request)} if the palierPlate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the palierPlate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/palier-plates/{id}")
    public ResponseEntity<PalierPlate> updatePalierPlate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PalierPlate palierPlate
    ) throws URISyntaxException {
        log.debug("REST request to update PalierPlate : {}, {}", id, palierPlate);
        if (palierPlate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, palierPlate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!palierPlateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PalierPlate result = palierPlateService.save(palierPlate);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, palierPlate.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /palier-plates/:id} : Partial updates given fields of an existing palierPlate, field will ignore if it is null
     *
     * @param id the id of the palierPlate to save.
     * @param palierPlate the palierPlate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated palierPlate,
     * or with status {@code 400 (Bad Request)} if the palierPlate is not valid,
     * or with status {@code 404 (Not Found)} if the palierPlate is not found,
     * or with status {@code 500 (Internal Server Error)} if the palierPlate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/palier-plates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PalierPlate> partialUpdatePalierPlate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PalierPlate palierPlate
    ) throws URISyntaxException {
        log.debug("REST request to partial update PalierPlate partially : {}, {}", id, palierPlate);
        if (palierPlate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, palierPlate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!palierPlateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PalierPlate> result = palierPlateService.partialUpdate(palierPlate);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, palierPlate.getId().toString())
        );
    }

    /**
     * {@code GET  /palier-plates} : get all the palierPlates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of palierPlates in body.
     */
    @GetMapping("/palier-plates")
    public ResponseEntity<List<PalierPlate>> getAllPalierPlates(Pageable pageable) {
        log.debug("REST request to get a page of PalierPlates");
        Page<PalierPlate> page = palierPlateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /palier-plates/:id} : get the "id" palierPlate.
     *
     * @param id the id of the palierPlate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the palierPlate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/palier-plates/{id}")
    public ResponseEntity<PalierPlate> getPalierPlate(@PathVariable Long id) {
        log.debug("REST request to get PalierPlate : {}", id);
        Optional<PalierPlate> palierPlate = palierPlateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(palierPlate);
    }

    /**
     * {@code DELETE  /palier-plates/:id} : delete the "id" palierPlate.
     *
     * @param id the id of the palierPlate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/palier-plates/{id}")
    public ResponseEntity<Void> deletePalierPlate(@PathVariable Long id) {
        log.debug("REST request to delete PalierPlate : {}", id);
        palierPlateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
