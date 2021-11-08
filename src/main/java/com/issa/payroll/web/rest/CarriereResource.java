package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Carriere;
import com.issa.payroll.repository.CarriereRepository;
import com.issa.payroll.service.CarriereService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Carriere}.
 */
@RestController
@RequestMapping("/api")
public class CarriereResource {

    private final Logger log = LoggerFactory.getLogger(CarriereResource.class);

    private static final String ENTITY_NAME = "carriere";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CarriereService carriereService;

    private final CarriereRepository carriereRepository;

    public CarriereResource(CarriereService carriereService, CarriereRepository carriereRepository) {
        this.carriereService = carriereService;
        this.carriereRepository = carriereRepository;
    }

    /**
     * {@code POST  /carrieres} : Create a new carriere.
     *
     * @param carriere the carriere to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new carriere, or with status {@code 400 (Bad Request)} if the carriere has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/carrieres")
    public ResponseEntity<Carriere> createCarriere(@RequestBody Carriere carriere) throws URISyntaxException {
        log.debug("REST request to save Carriere : {}", carriere);
        if (carriere.getId() != null) {
            throw new BadRequestAlertException("A new carriere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Carriere result = carriereService.save(carriere);
        return ResponseEntity
            .created(new URI("/api/carrieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /carrieres/:id} : Updates an existing carriere.
     *
     * @param id the id of the carriere to save.
     * @param carriere the carriere to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carriere,
     * or with status {@code 400 (Bad Request)} if the carriere is not valid,
     * or with status {@code 500 (Internal Server Error)} if the carriere couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/carrieres/{id}")
    public ResponseEntity<Carriere> updateCarriere(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Carriere carriere
    ) throws URISyntaxException {
        log.debug("REST request to update Carriere : {}, {}", id, carriere);
        if (carriere.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carriere.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carriereRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Carriere result = carriereService.save(carriere);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carriere.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /carrieres/:id} : Partial updates given fields of an existing carriere, field will ignore if it is null
     *
     * @param id the id of the carriere to save.
     * @param carriere the carriere to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated carriere,
     * or with status {@code 400 (Bad Request)} if the carriere is not valid,
     * or with status {@code 404 (Not Found)} if the carriere is not found,
     * or with status {@code 500 (Internal Server Error)} if the carriere couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/carrieres/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Carriere> partialUpdateCarriere(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Carriere carriere
    ) throws URISyntaxException {
        log.debug("REST request to partial update Carriere partially : {}, {}", id, carriere);
        if (carriere.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, carriere.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!carriereRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Carriere> result = carriereService.partialUpdate(carriere);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, carriere.getId().toString())
        );
    }

    /**
     * {@code GET  /carrieres} : get all the carrieres.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of carrieres in body.
     */
    @GetMapping("/carrieres")
    public ResponseEntity<List<Carriere>> getAllCarrieres(Pageable pageable) {
        log.debug("REST request to get a page of Carrieres");
        Page<Carriere> page = carriereService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /carrieres/:id} : get the "id" carriere.
     *
     * @param id the id of the carriere to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the carriere, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/carrieres/{id}")
    public ResponseEntity<Carriere> getCarriere(@PathVariable Long id) {
        log.debug("REST request to get Carriere : {}", id);
        Optional<Carriere> carriere = carriereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(carriere);
    }

    /**
     * {@code DELETE  /carrieres/:id} : delete the "id" carriere.
     *
     * @param id the id of the carriere to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/carrieres/{id}")
    public ResponseEntity<Void> deleteCarriere(@PathVariable Long id) {
        log.debug("REST request to delete Carriere : {}", id);
        carriereService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
