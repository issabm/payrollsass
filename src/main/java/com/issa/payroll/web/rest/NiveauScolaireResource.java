package com.issa.payroll.web.rest;

import com.issa.payroll.domain.NiveauScolaire;
import com.issa.payroll.repository.NiveauScolaireRepository;
import com.issa.payroll.service.NiveauScolaireService;
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
 * REST controller for managing {@link com.issa.payroll.domain.NiveauScolaire}.
 */
@RestController
@RequestMapping("/api")
public class NiveauScolaireResource {

    private final Logger log = LoggerFactory.getLogger(NiveauScolaireResource.class);

    private static final String ENTITY_NAME = "niveauScolaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NiveauScolaireService niveauScolaireService;

    private final NiveauScolaireRepository niveauScolaireRepository;

    public NiveauScolaireResource(NiveauScolaireService niveauScolaireService, NiveauScolaireRepository niveauScolaireRepository) {
        this.niveauScolaireService = niveauScolaireService;
        this.niveauScolaireRepository = niveauScolaireRepository;
    }

    /**
     * {@code POST  /niveau-scolaires} : Create a new niveauScolaire.
     *
     * @param niveauScolaire the niveauScolaire to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new niveauScolaire, or with status {@code 400 (Bad Request)} if the niveauScolaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/niveau-scolaires")
    public ResponseEntity<NiveauScolaire> createNiveauScolaire(@RequestBody NiveauScolaire niveauScolaire) throws URISyntaxException {
        log.debug("REST request to save NiveauScolaire : {}", niveauScolaire);
        if (niveauScolaire.getId() != null) {
            throw new BadRequestAlertException("A new niveauScolaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NiveauScolaire result = niveauScolaireService.save(niveauScolaire);
        return ResponseEntity
            .created(new URI("/api/niveau-scolaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /niveau-scolaires/:id} : Updates an existing niveauScolaire.
     *
     * @param id the id of the niveauScolaire to save.
     * @param niveauScolaire the niveauScolaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated niveauScolaire,
     * or with status {@code 400 (Bad Request)} if the niveauScolaire is not valid,
     * or with status {@code 500 (Internal Server Error)} if the niveauScolaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/niveau-scolaires/{id}")
    public ResponseEntity<NiveauScolaire> updateNiveauScolaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NiveauScolaire niveauScolaire
    ) throws URISyntaxException {
        log.debug("REST request to update NiveauScolaire : {}, {}", id, niveauScolaire);
        if (niveauScolaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, niveauScolaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!niveauScolaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NiveauScolaire result = niveauScolaireService.save(niveauScolaire);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, niveauScolaire.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /niveau-scolaires/:id} : Partial updates given fields of an existing niveauScolaire, field will ignore if it is null
     *
     * @param id the id of the niveauScolaire to save.
     * @param niveauScolaire the niveauScolaire to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated niveauScolaire,
     * or with status {@code 400 (Bad Request)} if the niveauScolaire is not valid,
     * or with status {@code 404 (Not Found)} if the niveauScolaire is not found,
     * or with status {@code 500 (Internal Server Error)} if the niveauScolaire couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/niveau-scolaires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NiveauScolaire> partialUpdateNiveauScolaire(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NiveauScolaire niveauScolaire
    ) throws URISyntaxException {
        log.debug("REST request to partial update NiveauScolaire partially : {}, {}", id, niveauScolaire);
        if (niveauScolaire.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, niveauScolaire.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!niveauScolaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NiveauScolaire> result = niveauScolaireService.partialUpdate(niveauScolaire);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, niveauScolaire.getId().toString())
        );
    }

    /**
     * {@code GET  /niveau-scolaires} : get all the niveauScolaires.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of niveauScolaires in body.
     */
    @GetMapping("/niveau-scolaires")
    public ResponseEntity<List<NiveauScolaire>> getAllNiveauScolaires(Pageable pageable) {
        log.debug("REST request to get a page of NiveauScolaires");
        Page<NiveauScolaire> page = niveauScolaireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /niveau-scolaires/:id} : get the "id" niveauScolaire.
     *
     * @param id the id of the niveauScolaire to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the niveauScolaire, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/niveau-scolaires/{id}")
    public ResponseEntity<NiveauScolaire> getNiveauScolaire(@PathVariable Long id) {
        log.debug("REST request to get NiveauScolaire : {}", id);
        Optional<NiveauScolaire> niveauScolaire = niveauScolaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(niveauScolaire);
    }

    /**
     * {@code DELETE  /niveau-scolaires/:id} : delete the "id" niveauScolaire.
     *
     * @param id the id of the niveauScolaire to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/niveau-scolaires/{id}")
    public ResponseEntity<Void> deleteNiveauScolaire(@PathVariable Long id) {
        log.debug("REST request to delete NiveauScolaire : {}", id);
        niveauScolaireService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
