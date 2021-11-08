package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Echlon;
import com.issa.payroll.repository.EchlonRepository;
import com.issa.payroll.service.EchlonService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Echlon}.
 */
@RestController
@RequestMapping("/api")
public class EchlonResource {

    private final Logger log = LoggerFactory.getLogger(EchlonResource.class);

    private static final String ENTITY_NAME = "echlon";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EchlonService echlonService;

    private final EchlonRepository echlonRepository;

    public EchlonResource(EchlonService echlonService, EchlonRepository echlonRepository) {
        this.echlonService = echlonService;
        this.echlonRepository = echlonRepository;
    }

    /**
     * {@code POST  /echlons} : Create a new echlon.
     *
     * @param echlon the echlon to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new echlon, or with status {@code 400 (Bad Request)} if the echlon has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/echlons")
    public ResponseEntity<Echlon> createEchlon(@RequestBody Echlon echlon) throws URISyntaxException {
        log.debug("REST request to save Echlon : {}", echlon);
        if (echlon.getId() != null) {
            throw new BadRequestAlertException("A new echlon cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Echlon result = echlonService.save(echlon);
        return ResponseEntity
            .created(new URI("/api/echlons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /echlons/:id} : Updates an existing echlon.
     *
     * @param id the id of the echlon to save.
     * @param echlon the echlon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated echlon,
     * or with status {@code 400 (Bad Request)} if the echlon is not valid,
     * or with status {@code 500 (Internal Server Error)} if the echlon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/echlons/{id}")
    public ResponseEntity<Echlon> updateEchlon(@PathVariable(value = "id", required = false) final Long id, @RequestBody Echlon echlon)
        throws URISyntaxException {
        log.debug("REST request to update Echlon : {}, {}", id, echlon);
        if (echlon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, echlon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!echlonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Echlon result = echlonService.save(echlon);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, echlon.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /echlons/:id} : Partial updates given fields of an existing echlon, field will ignore if it is null
     *
     * @param id the id of the echlon to save.
     * @param echlon the echlon to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated echlon,
     * or with status {@code 400 (Bad Request)} if the echlon is not valid,
     * or with status {@code 404 (Not Found)} if the echlon is not found,
     * or with status {@code 500 (Internal Server Error)} if the echlon couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/echlons/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Echlon> partialUpdateEchlon(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Echlon echlon
    ) throws URISyntaxException {
        log.debug("REST request to partial update Echlon partially : {}, {}", id, echlon);
        if (echlon.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, echlon.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!echlonRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Echlon> result = echlonService.partialUpdate(echlon);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, echlon.getId().toString())
        );
    }

    /**
     * {@code GET  /echlons} : get all the echlons.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of echlons in body.
     */
    @GetMapping("/echlons")
    public ResponseEntity<List<Echlon>> getAllEchlons(Pageable pageable) {
        log.debug("REST request to get a page of Echlons");
        Page<Echlon> page = echlonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /echlons/:id} : get the "id" echlon.
     *
     * @param id the id of the echlon to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the echlon, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/echlons/{id}")
    public ResponseEntity<Echlon> getEchlon(@PathVariable Long id) {
        log.debug("REST request to get Echlon : {}", id);
        Optional<Echlon> echlon = echlonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(echlon);
    }

    /**
     * {@code DELETE  /echlons/:id} : delete the "id" echlon.
     *
     * @param id the id of the echlon to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/echlons/{id}")
    public ResponseEntity<Void> deleteEchlon(@PathVariable Long id) {
        log.debug("REST request to delete Echlon : {}", id);
        echlonService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
