package com.issa.payroll.web.rest;

import com.issa.payroll.domain.MouvementPaie;
import com.issa.payroll.repository.MouvementPaieRepository;
import com.issa.payroll.service.MouvementPaieService;
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
 * REST controller for managing {@link com.issa.payroll.domain.MouvementPaie}.
 */
@RestController
@RequestMapping("/api")
public class MouvementPaieResource {

    private final Logger log = LoggerFactory.getLogger(MouvementPaieResource.class);

    private static final String ENTITY_NAME = "mouvementPaie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MouvementPaieService mouvementPaieService;

    private final MouvementPaieRepository mouvementPaieRepository;

    public MouvementPaieResource(MouvementPaieService mouvementPaieService, MouvementPaieRepository mouvementPaieRepository) {
        this.mouvementPaieService = mouvementPaieService;
        this.mouvementPaieRepository = mouvementPaieRepository;
    }

    /**
     * {@code POST  /mouvement-paies} : Create a new mouvementPaie.
     *
     * @param mouvementPaie the mouvementPaie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mouvementPaie, or with status {@code 400 (Bad Request)} if the mouvementPaie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mouvement-paies")
    public ResponseEntity<MouvementPaie> createMouvementPaie(@RequestBody MouvementPaie mouvementPaie) throws URISyntaxException {
        log.debug("REST request to save MouvementPaie : {}", mouvementPaie);
        if (mouvementPaie.getId() != null) {
            throw new BadRequestAlertException("A new mouvementPaie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MouvementPaie result = mouvementPaieService.save(mouvementPaie);
        return ResponseEntity
            .created(new URI("/api/mouvement-paies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mouvement-paies/:id} : Updates an existing mouvementPaie.
     *
     * @param id the id of the mouvementPaie to save.
     * @param mouvementPaie the mouvementPaie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mouvementPaie,
     * or with status {@code 400 (Bad Request)} if the mouvementPaie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mouvementPaie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mouvement-paies/{id}")
    public ResponseEntity<MouvementPaie> updateMouvementPaie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MouvementPaie mouvementPaie
    ) throws URISyntaxException {
        log.debug("REST request to update MouvementPaie : {}, {}", id, mouvementPaie);
        if (mouvementPaie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mouvementPaie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mouvementPaieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MouvementPaie result = mouvementPaieService.save(mouvementPaie);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mouvementPaie.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mouvement-paies/:id} : Partial updates given fields of an existing mouvementPaie, field will ignore if it is null
     *
     * @param id the id of the mouvementPaie to save.
     * @param mouvementPaie the mouvementPaie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mouvementPaie,
     * or with status {@code 400 (Bad Request)} if the mouvementPaie is not valid,
     * or with status {@code 404 (Not Found)} if the mouvementPaie is not found,
     * or with status {@code 500 (Internal Server Error)} if the mouvementPaie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mouvement-paies/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MouvementPaie> partialUpdateMouvementPaie(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MouvementPaie mouvementPaie
    ) throws URISyntaxException {
        log.debug("REST request to partial update MouvementPaie partially : {}, {}", id, mouvementPaie);
        if (mouvementPaie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mouvementPaie.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mouvementPaieRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MouvementPaie> result = mouvementPaieService.partialUpdate(mouvementPaie);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mouvementPaie.getId().toString())
        );
    }

    /**
     * {@code GET  /mouvement-paies} : get all the mouvementPaies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mouvementPaies in body.
     */
    @GetMapping("/mouvement-paies")
    public ResponseEntity<List<MouvementPaie>> getAllMouvementPaies(Pageable pageable) {
        log.debug("REST request to get a page of MouvementPaies");
        Page<MouvementPaie> page = mouvementPaieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mouvement-paies/:id} : get the "id" mouvementPaie.
     *
     * @param id the id of the mouvementPaie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mouvementPaie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mouvement-paies/{id}")
    public ResponseEntity<MouvementPaie> getMouvementPaie(@PathVariable Long id) {
        log.debug("REST request to get MouvementPaie : {}", id);
        Optional<MouvementPaie> mouvementPaie = mouvementPaieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mouvementPaie);
    }

    /**
     * {@code DELETE  /mouvement-paies/:id} : delete the "id" mouvementPaie.
     *
     * @param id the id of the mouvementPaie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mouvement-paies/{id}")
    public ResponseEntity<Void> deleteMouvementPaie(@PathVariable Long id) {
        log.debug("REST request to delete MouvementPaie : {}", id);
        mouvementPaieService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
