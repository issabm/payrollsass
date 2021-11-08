package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Sens;
import com.issa.payroll.repository.SensRepository;
import com.issa.payroll.service.SensService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Sens}.
 */
@RestController
@RequestMapping("/api")
public class SensResource {

    private final Logger log = LoggerFactory.getLogger(SensResource.class);

    private static final String ENTITY_NAME = "sens";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SensService sensService;

    private final SensRepository sensRepository;

    public SensResource(SensService sensService, SensRepository sensRepository) {
        this.sensService = sensService;
        this.sensRepository = sensRepository;
    }

    /**
     * {@code POST  /sens} : Create a new sens.
     *
     * @param sens the sens to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sens, or with status {@code 400 (Bad Request)} if the sens has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sens")
    public ResponseEntity<Sens> createSens(@RequestBody Sens sens) throws URISyntaxException {
        log.debug("REST request to save Sens : {}", sens);
        if (sens.getId() != null) {
            throw new BadRequestAlertException("A new sens cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sens result = sensService.save(sens);
        return ResponseEntity
            .created(new URI("/api/sens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sens/:id} : Updates an existing sens.
     *
     * @param id the id of the sens to save.
     * @param sens the sens to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sens,
     * or with status {@code 400 (Bad Request)} if the sens is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sens couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sens/{id}")
    public ResponseEntity<Sens> updateSens(@PathVariable(value = "id", required = false) final Long id, @RequestBody Sens sens)
        throws URISyntaxException {
        log.debug("REST request to update Sens : {}, {}", id, sens);
        if (sens.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sens.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sensRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Sens result = sensService.save(sens);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sens.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sens/:id} : Partial updates given fields of an existing sens, field will ignore if it is null
     *
     * @param id the id of the sens to save.
     * @param sens the sens to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sens,
     * or with status {@code 400 (Bad Request)} if the sens is not valid,
     * or with status {@code 404 (Not Found)} if the sens is not found,
     * or with status {@code 500 (Internal Server Error)} if the sens couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sens/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Sens> partialUpdateSens(@PathVariable(value = "id", required = false) final Long id, @RequestBody Sens sens)
        throws URISyntaxException {
        log.debug("REST request to partial update Sens partially : {}, {}", id, sens);
        if (sens.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, sens.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!sensRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Sens> result = sensService.partialUpdate(sens);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sens.getId().toString())
        );
    }

    /**
     * {@code GET  /sens} : get all the sens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sens in body.
     */
    @GetMapping("/sens")
    public ResponseEntity<List<Sens>> getAllSens(Pageable pageable) {
        log.debug("REST request to get a page of Sens");
        Page<Sens> page = sensService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sens/:id} : get the "id" sens.
     *
     * @param id the id of the sens to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sens, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sens/{id}")
    public ResponseEntity<Sens> getSens(@PathVariable Long id) {
        log.debug("REST request to get Sens : {}", id);
        Optional<Sens> sens = sensService.findOne(id);
        return ResponseUtil.wrapOrNotFound(sens);
    }

    /**
     * {@code DELETE  /sens/:id} : delete the "id" sens.
     *
     * @param id the id of the sens to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sens/{id}")
    public ResponseEntity<Void> deleteSens(@PathVariable Long id) {
        log.debug("REST request to delete Sens : {}", id);
        sensService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
