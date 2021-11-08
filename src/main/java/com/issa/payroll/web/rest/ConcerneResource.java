package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Concerne;
import com.issa.payroll.repository.ConcerneRepository;
import com.issa.payroll.service.ConcerneService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Concerne}.
 */
@RestController
@RequestMapping("/api")
public class ConcerneResource {

    private final Logger log = LoggerFactory.getLogger(ConcerneResource.class);

    private static final String ENTITY_NAME = "concerne";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConcerneService concerneService;

    private final ConcerneRepository concerneRepository;

    public ConcerneResource(ConcerneService concerneService, ConcerneRepository concerneRepository) {
        this.concerneService = concerneService;
        this.concerneRepository = concerneRepository;
    }

    /**
     * {@code POST  /concernes} : Create a new concerne.
     *
     * @param concerne the concerne to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new concerne, or with status {@code 400 (Bad Request)} if the concerne has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concernes")
    public ResponseEntity<Concerne> createConcerne(@RequestBody Concerne concerne) throws URISyntaxException {
        log.debug("REST request to save Concerne : {}", concerne);
        if (concerne.getId() != null) {
            throw new BadRequestAlertException("A new concerne cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Concerne result = concerneService.save(concerne);
        return ResponseEntity
            .created(new URI("/api/concernes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concernes/:id} : Updates an existing concerne.
     *
     * @param id the id of the concerne to save.
     * @param concerne the concerne to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concerne,
     * or with status {@code 400 (Bad Request)} if the concerne is not valid,
     * or with status {@code 500 (Internal Server Error)} if the concerne couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concernes/{id}")
    public ResponseEntity<Concerne> updateConcerne(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Concerne concerne
    ) throws URISyntaxException {
        log.debug("REST request to update Concerne : {}, {}", id, concerne);
        if (concerne.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concerne.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!concerneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Concerne result = concerneService.save(concerne);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concerne.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /concernes/:id} : Partial updates given fields of an existing concerne, field will ignore if it is null
     *
     * @param id the id of the concerne to save.
     * @param concerne the concerne to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated concerne,
     * or with status {@code 400 (Bad Request)} if the concerne is not valid,
     * or with status {@code 404 (Not Found)} if the concerne is not found,
     * or with status {@code 500 (Internal Server Error)} if the concerne couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/concernes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Concerne> partialUpdateConcerne(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Concerne concerne
    ) throws URISyntaxException {
        log.debug("REST request to partial update Concerne partially : {}, {}", id, concerne);
        if (concerne.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, concerne.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!concerneRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Concerne> result = concerneService.partialUpdate(concerne);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, concerne.getId().toString())
        );
    }

    /**
     * {@code GET  /concernes} : get all the concernes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of concernes in body.
     */
    @GetMapping("/concernes")
    public ResponseEntity<List<Concerne>> getAllConcernes(Pageable pageable) {
        log.debug("REST request to get a page of Concernes");
        Page<Concerne> page = concerneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /concernes/:id} : get the "id" concerne.
     *
     * @param id the id of the concerne to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the concerne, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concernes/{id}")
    public ResponseEntity<Concerne> getConcerne(@PathVariable Long id) {
        log.debug("REST request to get Concerne : {}", id);
        Optional<Concerne> concerne = concerneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(concerne);
    }

    /**
     * {@code DELETE  /concernes/:id} : delete the "id" concerne.
     *
     * @param id the id of the concerne to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concernes/{id}")
    public ResponseEntity<Void> deleteConcerne(@PathVariable Long id) {
        log.debug("REST request to delete Concerne : {}", id);
        concerneService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
