package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Plate;
import com.issa.payroll.repository.PlateRepository;
import com.issa.payroll.service.PlateService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Plate}.
 */
@RestController
@RequestMapping("/api")
public class PlateResource {

    private final Logger log = LoggerFactory.getLogger(PlateResource.class);

    private static final String ENTITY_NAME = "plate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlateService plateService;

    private final PlateRepository plateRepository;

    public PlateResource(PlateService plateService, PlateRepository plateRepository) {
        this.plateService = plateService;
        this.plateRepository = plateRepository;
    }

    /**
     * {@code POST  /plates} : Create a new plate.
     *
     * @param plate the plate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plate, or with status {@code 400 (Bad Request)} if the plate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plates")
    public ResponseEntity<Plate> createPlate(@RequestBody Plate plate) throws URISyntaxException {
        log.debug("REST request to save Plate : {}", plate);
        if (plate.getId() != null) {
            throw new BadRequestAlertException("A new plate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Plate result = plateService.save(plate);
        return ResponseEntity
            .created(new URI("/api/plates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /plates/:id} : Updates an existing plate.
     *
     * @param id the id of the plate to save.
     * @param plate the plate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plate,
     * or with status {@code 400 (Bad Request)} if the plate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plates/{id}")
    public ResponseEntity<Plate> updatePlate(@PathVariable(value = "id", required = false) final Long id, @RequestBody Plate plate)
        throws URISyntaxException {
        log.debug("REST request to update Plate : {}, {}", id, plate);
        if (plate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Plate result = plateService.save(plate);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plate.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /plates/:id} : Partial updates given fields of an existing plate, field will ignore if it is null
     *
     * @param id the id of the plate to save.
     * @param plate the plate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plate,
     * or with status {@code 400 (Bad Request)} if the plate is not valid,
     * or with status {@code 404 (Not Found)} if the plate is not found,
     * or with status {@code 500 (Internal Server Error)} if the plate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Plate> partialUpdatePlate(@PathVariable(value = "id", required = false) final Long id, @RequestBody Plate plate)
        throws URISyntaxException {
        log.debug("REST request to partial update Plate partially : {}, {}", id, plate);
        if (plate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plate.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Plate> result = plateService.partialUpdate(plate);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, plate.getId().toString())
        );
    }

    /**
     * {@code GET  /plates} : get all the plates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plates in body.
     */
    @GetMapping("/plates")
    public ResponseEntity<List<Plate>> getAllPlates(Pageable pageable) {
        log.debug("REST request to get a page of Plates");
        Page<Plate> page = plateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plates/:id} : get the "id" plate.
     *
     * @param id the id of the plate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plates/{id}")
    public ResponseEntity<Plate> getPlate(@PathVariable Long id) {
        log.debug("REST request to get Plate : {}", id);
        Optional<Plate> plate = plateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plate);
    }

    /**
     * {@code DELETE  /plates/:id} : delete the "id" plate.
     *
     * @param id the id of the plate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plates/{id}")
    public ResponseEntity<Void> deletePlate(@PathVariable Long id) {
        log.debug("REST request to delete Plate : {}", id);
        plateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
