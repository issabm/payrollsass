package com.issa.payroll.web.rest;

import com.issa.payroll.domain.Conjoint;
import com.issa.payroll.repository.ConjointRepository;
import com.issa.payroll.service.ConjointService;
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
 * REST controller for managing {@link com.issa.payroll.domain.Conjoint}.
 */
@RestController
@RequestMapping("/api")
public class ConjointResource {

    private final Logger log = LoggerFactory.getLogger(ConjointResource.class);

    private static final String ENTITY_NAME = "conjoint";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConjointService conjointService;

    private final ConjointRepository conjointRepository;

    public ConjointResource(ConjointService conjointService, ConjointRepository conjointRepository) {
        this.conjointService = conjointService;
        this.conjointRepository = conjointRepository;
    }

    /**
     * {@code POST  /conjoints} : Create a new conjoint.
     *
     * @param conjoint the conjoint to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conjoint, or with status {@code 400 (Bad Request)} if the conjoint has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conjoints")
    public ResponseEntity<Conjoint> createConjoint(@RequestBody Conjoint conjoint) throws URISyntaxException {
        log.debug("REST request to save Conjoint : {}", conjoint);
        if (conjoint.getId() != null) {
            throw new BadRequestAlertException("A new conjoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Conjoint result = conjointService.save(conjoint);
        return ResponseEntity
            .created(new URI("/api/conjoints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conjoints/:id} : Updates an existing conjoint.
     *
     * @param id the id of the conjoint to save.
     * @param conjoint the conjoint to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conjoint,
     * or with status {@code 400 (Bad Request)} if the conjoint is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conjoint couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conjoints/{id}")
    public ResponseEntity<Conjoint> updateConjoint(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Conjoint conjoint
    ) throws URISyntaxException {
        log.debug("REST request to update Conjoint : {}, {}", id, conjoint);
        if (conjoint.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conjoint.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conjointRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Conjoint result = conjointService.save(conjoint);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conjoint.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /conjoints/:id} : Partial updates given fields of an existing conjoint, field will ignore if it is null
     *
     * @param id the id of the conjoint to save.
     * @param conjoint the conjoint to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conjoint,
     * or with status {@code 400 (Bad Request)} if the conjoint is not valid,
     * or with status {@code 404 (Not Found)} if the conjoint is not found,
     * or with status {@code 500 (Internal Server Error)} if the conjoint couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/conjoints/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Conjoint> partialUpdateConjoint(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Conjoint conjoint
    ) throws URISyntaxException {
        log.debug("REST request to partial update Conjoint partially : {}, {}", id, conjoint);
        if (conjoint.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conjoint.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conjointRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Conjoint> result = conjointService.partialUpdate(conjoint);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conjoint.getId().toString())
        );
    }

    /**
     * {@code GET  /conjoints} : get all the conjoints.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conjoints in body.
     */
    @GetMapping("/conjoints")
    public ResponseEntity<List<Conjoint>> getAllConjoints(Pageable pageable) {
        log.debug("REST request to get a page of Conjoints");
        Page<Conjoint> page = conjointService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /conjoints/:id} : get the "id" conjoint.
     *
     * @param id the id of the conjoint to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conjoint, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conjoints/{id}")
    public ResponseEntity<Conjoint> getConjoint(@PathVariable Long id) {
        log.debug("REST request to get Conjoint : {}", id);
        Optional<Conjoint> conjoint = conjointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conjoint);
    }

    /**
     * {@code DELETE  /conjoints/:id} : delete the "id" conjoint.
     *
     * @param id the id of the conjoint to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conjoints/{id}")
    public ResponseEntity<Void> deleteConjoint(@PathVariable Long id) {
        log.debug("REST request to delete Conjoint : {}", id);
        conjointService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
