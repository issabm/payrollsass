package com.issa.payroll.web.rest;

import com.issa.payroll.domain.EligibiliteExclude;
import com.issa.payroll.repository.EligibiliteExcludeRepository;
import com.issa.payroll.service.EligibiliteExcludeService;
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
 * REST controller for managing {@link com.issa.payroll.domain.EligibiliteExclude}.
 */
@RestController
@RequestMapping("/api")
public class EligibiliteExcludeResource {

    private final Logger log = LoggerFactory.getLogger(EligibiliteExcludeResource.class);

    private static final String ENTITY_NAME = "eligibiliteExclude";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EligibiliteExcludeService eligibiliteExcludeService;

    private final EligibiliteExcludeRepository eligibiliteExcludeRepository;

    public EligibiliteExcludeResource(
        EligibiliteExcludeService eligibiliteExcludeService,
        EligibiliteExcludeRepository eligibiliteExcludeRepository
    ) {
        this.eligibiliteExcludeService = eligibiliteExcludeService;
        this.eligibiliteExcludeRepository = eligibiliteExcludeRepository;
    }

    /**
     * {@code POST  /eligibilite-excludes} : Create a new eligibiliteExclude.
     *
     * @param eligibiliteExclude the eligibiliteExclude to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eligibiliteExclude, or with status {@code 400 (Bad Request)} if the eligibiliteExclude has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/eligibilite-excludes")
    public ResponseEntity<EligibiliteExclude> createEligibiliteExclude(@RequestBody EligibiliteExclude eligibiliteExclude)
        throws URISyntaxException {
        log.debug("REST request to save EligibiliteExclude : {}", eligibiliteExclude);
        if (eligibiliteExclude.getId() != null) {
            throw new BadRequestAlertException("A new eligibiliteExclude cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EligibiliteExclude result = eligibiliteExcludeService.save(eligibiliteExclude);
        return ResponseEntity
            .created(new URI("/api/eligibilite-excludes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /eligibilite-excludes/:id} : Updates an existing eligibiliteExclude.
     *
     * @param id the id of the eligibiliteExclude to save.
     * @param eligibiliteExclude the eligibiliteExclude to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eligibiliteExclude,
     * or with status {@code 400 (Bad Request)} if the eligibiliteExclude is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eligibiliteExclude couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/eligibilite-excludes/{id}")
    public ResponseEntity<EligibiliteExclude> updateEligibiliteExclude(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EligibiliteExclude eligibiliteExclude
    ) throws URISyntaxException {
        log.debug("REST request to update EligibiliteExclude : {}, {}", id, eligibiliteExclude);
        if (eligibiliteExclude.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eligibiliteExclude.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eligibiliteExcludeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EligibiliteExclude result = eligibiliteExcludeService.save(eligibiliteExclude);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eligibiliteExclude.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /eligibilite-excludes/:id} : Partial updates given fields of an existing eligibiliteExclude, field will ignore if it is null
     *
     * @param id the id of the eligibiliteExclude to save.
     * @param eligibiliteExclude the eligibiliteExclude to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eligibiliteExclude,
     * or with status {@code 400 (Bad Request)} if the eligibiliteExclude is not valid,
     * or with status {@code 404 (Not Found)} if the eligibiliteExclude is not found,
     * or with status {@code 500 (Internal Server Error)} if the eligibiliteExclude couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/eligibilite-excludes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EligibiliteExclude> partialUpdateEligibiliteExclude(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EligibiliteExclude eligibiliteExclude
    ) throws URISyntaxException {
        log.debug("REST request to partial update EligibiliteExclude partially : {}, {}", id, eligibiliteExclude);
        if (eligibiliteExclude.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eligibiliteExclude.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eligibiliteExcludeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EligibiliteExclude> result = eligibiliteExcludeService.partialUpdate(eligibiliteExclude);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eligibiliteExclude.getId().toString())
        );
    }

    /**
     * {@code GET  /eligibilite-excludes} : get all the eligibiliteExcludes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eligibiliteExcludes in body.
     */
    @GetMapping("/eligibilite-excludes")
    public ResponseEntity<List<EligibiliteExclude>> getAllEligibiliteExcludes(Pageable pageable) {
        log.debug("REST request to get a page of EligibiliteExcludes");
        Page<EligibiliteExclude> page = eligibiliteExcludeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /eligibilite-excludes/:id} : get the "id" eligibiliteExclude.
     *
     * @param id the id of the eligibiliteExclude to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eligibiliteExclude, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/eligibilite-excludes/{id}")
    public ResponseEntity<EligibiliteExclude> getEligibiliteExclude(@PathVariable Long id) {
        log.debug("REST request to get EligibiliteExclude : {}", id);
        Optional<EligibiliteExclude> eligibiliteExclude = eligibiliteExcludeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eligibiliteExclude);
    }

    /**
     * {@code DELETE  /eligibilite-excludes/:id} : delete the "id" eligibiliteExclude.
     *
     * @param id the id of the eligibiliteExclude to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/eligibilite-excludes/{id}")
    public ResponseEntity<Void> deleteEligibiliteExclude(@PathVariable Long id) {
        log.debug("REST request to delete EligibiliteExclude : {}", id);
        eligibiliteExcludeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
