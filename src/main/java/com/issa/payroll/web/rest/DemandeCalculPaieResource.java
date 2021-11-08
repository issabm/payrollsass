package com.issa.payroll.web.rest;

import com.issa.payroll.domain.DemandeCalculPaie;
import com.issa.payroll.repository.DemandeCalculPaieRepository;
import com.issa.payroll.service.DemandeCalculPaieService;
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
 * REST controller for managing {@link com.issa.payroll.domain.DemandeCalculPaie}.
 */
@RestController
@RequestMapping("/api")
public class DemandeCalculPaieResource {

    private final Logger log = LoggerFactory.getLogger(DemandeCalculPaieResource.class);

    private static final String ENTITY_NAME = "demandeCalculPaie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DemandeCalculPaieService demandeCalculPaieService;

    private final DemandeCalculPaieRepository demandeCalculPaieRepository;

    public DemandeCalculPaieResource(
        DemandeCalculPaieService demandeCalculPaieService,
        DemandeCalculPaieRepository demandeCalculPaieRepository
    ) {
        this.demandeCalculPaieService = demandeCalculPaieService;
        this.demandeCalculPaieRepository = demandeCalculPaieRepository;
    }

    /**
     * {@code POST  /demande-calcul-paies} : Create a new demandeCalculPaie.
     *
     * @param demandeCalculPaie the demandeCalculPaie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new demandeCalculPaie, or with status {@code 400 (Bad Request)} if the demandeCalculPaie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/demande-calcul-paies")
    public ResponseEntity<DemandeCalculPaie> createDemandeCalculPaie(@RequestBody DemandeCalculPaie demandeCalculPaie)
        throws URISyntaxException {
        log.debug("REST request to save DemandeCalculPaie : {}", demandeCalculPaie);
        if (demandeCalculPaie.getId() != null) {
            throw new BadRequestAlertException("A new demandeCalculPaie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DemandeCalculPaie result = demandeCalculPaieService.save(demandeCalculPaie);
        return ResponseEntity
            .created(new URI("/api/demande-calcul-paies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /demande-calcul-paies/:id} : Updates an existing demandeCalculPaie.
     *
     * @param id the id of the demandeCalculPaie to save.
     * @param demandeCalculPaie the demandeCalculPaie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeCalculPaie,
     * or with status {@code 400 (Bad Request)} if the demandeCalculPaie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the demandeCalculPaie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/demande-calcul-paies/{id}")
    public ResponseEntity<DemandeCalculPaie> updateDemandeCalculPaie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandeCalculPaie demandeCalculPaie
    ) throws URISyntaxException {
        log.debug("REST request to update DemandeCalculPaie : {}, {}", id, demandeCalculPaie);
        if (demandeCalculPaie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeCalculPaie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeCalculPaieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DemandeCalculPaie result = demandeCalculPaieService.save(demandeCalculPaie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeCalculPaie.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /demande-calcul-paies/:id} : Partial updates given fields of an existing demandeCalculPaie, field will ignore if it is null
     *
     * @param id the id of the demandeCalculPaie to save.
     * @param demandeCalculPaie the demandeCalculPaie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated demandeCalculPaie,
     * or with status {@code 400 (Bad Request)} if the demandeCalculPaie is not valid,
     * or with status {@code 404 (Not Found)} if the demandeCalculPaie is not found,
     * or with status {@code 500 (Internal Server Error)} if the demandeCalculPaie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/demande-calcul-paies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DemandeCalculPaie> partialUpdateDemandeCalculPaie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DemandeCalculPaie demandeCalculPaie
    ) throws URISyntaxException {
        log.debug("REST request to partial update DemandeCalculPaie partially : {}, {}", id, demandeCalculPaie);
        if (demandeCalculPaie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, demandeCalculPaie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!demandeCalculPaieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DemandeCalculPaie> result = demandeCalculPaieService.partialUpdate(demandeCalculPaie);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, demandeCalculPaie.getId().toString())
        );
    }

    /**
     * {@code GET  /demande-calcul-paies} : get all the demandeCalculPaies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of demandeCalculPaies in body.
     */
    @GetMapping("/demande-calcul-paies")
    public ResponseEntity<List<DemandeCalculPaie>> getAllDemandeCalculPaies(Pageable pageable) {
        log.debug("REST request to get a page of DemandeCalculPaies");
        Page<DemandeCalculPaie> page = demandeCalculPaieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /demande-calcul-paies/:id} : get the "id" demandeCalculPaie.
     *
     * @param id the id of the demandeCalculPaie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the demandeCalculPaie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/demande-calcul-paies/{id}")
    public ResponseEntity<DemandeCalculPaie> getDemandeCalculPaie(@PathVariable Long id) {
        log.debug("REST request to get DemandeCalculPaie : {}", id);
        Optional<DemandeCalculPaie> demandeCalculPaie = demandeCalculPaieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(demandeCalculPaie);
    }

    /**
     * {@code DELETE  /demande-calcul-paies/:id} : delete the "id" demandeCalculPaie.
     *
     * @param id the id of the demandeCalculPaie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/demande-calcul-paies/{id}")
    public ResponseEntity<Void> deleteDemandeCalculPaie(@PathVariable Long id) {
        log.debug("REST request to delete DemandeCalculPaie : {}", id);
        demandeCalculPaieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
