package com.issa.payroll.web.rest;

import com.issa.payroll.domain.NatureMvtPaie;
import com.issa.payroll.repository.NatureMvtPaieRepository;
import com.issa.payroll.service.NatureMvtPaieService;
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
 * REST controller for managing {@link com.issa.payroll.domain.NatureMvtPaie}.
 */
@RestController
@RequestMapping("/api")
public class NatureMvtPaieResource {

    private final Logger log = LoggerFactory.getLogger(NatureMvtPaieResource.class);

    private static final String ENTITY_NAME = "natureMvtPaie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NatureMvtPaieService natureMvtPaieService;

    private final NatureMvtPaieRepository natureMvtPaieRepository;

    public NatureMvtPaieResource(NatureMvtPaieService natureMvtPaieService, NatureMvtPaieRepository natureMvtPaieRepository) {
        this.natureMvtPaieService = natureMvtPaieService;
        this.natureMvtPaieRepository = natureMvtPaieRepository;
    }

    /**
     * {@code POST  /nature-mvt-paies} : Create a new natureMvtPaie.
     *
     * @param natureMvtPaie the natureMvtPaie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new natureMvtPaie, or with status {@code 400 (Bad Request)} if the natureMvtPaie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nature-mvt-paies")
    public ResponseEntity<NatureMvtPaie> createNatureMvtPaie(@RequestBody NatureMvtPaie natureMvtPaie) throws URISyntaxException {
        log.debug("REST request to save NatureMvtPaie : {}", natureMvtPaie);
        if (natureMvtPaie.getId() != null) {
            throw new BadRequestAlertException("A new natureMvtPaie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NatureMvtPaie result = natureMvtPaieService.save(natureMvtPaie);
        return ResponseEntity
            .created(new URI("/api/nature-mvt-paies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nature-mvt-paies/:id} : Updates an existing natureMvtPaie.
     *
     * @param id the id of the natureMvtPaie to save.
     * @param natureMvtPaie the natureMvtPaie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureMvtPaie,
     * or with status {@code 400 (Bad Request)} if the natureMvtPaie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the natureMvtPaie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nature-mvt-paies/{id}")
    public ResponseEntity<NatureMvtPaie> updateNatureMvtPaie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureMvtPaie natureMvtPaie
    ) throws URISyntaxException {
        log.debug("REST request to update NatureMvtPaie : {}, {}", id, natureMvtPaie);
        if (natureMvtPaie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureMvtPaie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureMvtPaieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NatureMvtPaie result = natureMvtPaieService.save(natureMvtPaie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureMvtPaie.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nature-mvt-paies/:id} : Partial updates given fields of an existing natureMvtPaie, field will ignore if it is null
     *
     * @param id the id of the natureMvtPaie to save.
     * @param natureMvtPaie the natureMvtPaie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureMvtPaie,
     * or with status {@code 400 (Bad Request)} if the natureMvtPaie is not valid,
     * or with status {@code 404 (Not Found)} if the natureMvtPaie is not found,
     * or with status {@code 500 (Internal Server Error)} if the natureMvtPaie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nature-mvt-paies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NatureMvtPaie> partialUpdateNatureMvtPaie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureMvtPaie natureMvtPaie
    ) throws URISyntaxException {
        log.debug("REST request to partial update NatureMvtPaie partially : {}, {}", id, natureMvtPaie);
        if (natureMvtPaie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureMvtPaie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureMvtPaieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NatureMvtPaie> result = natureMvtPaieService.partialUpdate(natureMvtPaie);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureMvtPaie.getId().toString())
        );
    }

    /**
     * {@code GET  /nature-mvt-paies} : get all the natureMvtPaies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of natureMvtPaies in body.
     */
    @GetMapping("/nature-mvt-paies")
    public ResponseEntity<List<NatureMvtPaie>> getAllNatureMvtPaies(Pageable pageable) {
        log.debug("REST request to get a page of NatureMvtPaies");
        Page<NatureMvtPaie> page = natureMvtPaieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nature-mvt-paies/:id} : get the "id" natureMvtPaie.
     *
     * @param id the id of the natureMvtPaie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the natureMvtPaie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nature-mvt-paies/{id}")
    public ResponseEntity<NatureMvtPaie> getNatureMvtPaie(@PathVariable Long id) {
        log.debug("REST request to get NatureMvtPaie : {}", id);
        Optional<NatureMvtPaie> natureMvtPaie = natureMvtPaieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(natureMvtPaie);
    }

    /**
     * {@code DELETE  /nature-mvt-paies/:id} : delete the "id" natureMvtPaie.
     *
     * @param id the id of the natureMvtPaie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nature-mvt-paies/{id}")
    public ResponseEntity<Void> deleteNatureMvtPaie(@PathVariable Long id) {
        log.debug("REST request to delete NatureMvtPaie : {}", id);
        natureMvtPaieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
