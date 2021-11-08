package com.issa.payroll.web.rest;

import com.issa.payroll.domain.ModeCalcul;
import com.issa.payroll.repository.ModeCalculRepository;
import com.issa.payroll.service.ModeCalculService;
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
 * REST controller for managing {@link com.issa.payroll.domain.ModeCalcul}.
 */
@RestController
@RequestMapping("/api")
public class ModeCalculResource {

    private final Logger log = LoggerFactory.getLogger(ModeCalculResource.class);

    private static final String ENTITY_NAME = "modeCalcul";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ModeCalculService modeCalculService;

    private final ModeCalculRepository modeCalculRepository;

    public ModeCalculResource(ModeCalculService modeCalculService, ModeCalculRepository modeCalculRepository) {
        this.modeCalculService = modeCalculService;
        this.modeCalculRepository = modeCalculRepository;
    }

    /**
     * {@code POST  /mode-calculs} : Create a new modeCalcul.
     *
     * @param modeCalcul the modeCalcul to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new modeCalcul, or with status {@code 400 (Bad Request)} if the modeCalcul has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mode-calculs")
    public ResponseEntity<ModeCalcul> createModeCalcul(@RequestBody ModeCalcul modeCalcul) throws URISyntaxException {
        log.debug("REST request to save ModeCalcul : {}", modeCalcul);
        if (modeCalcul.getId() != null) {
            throw new BadRequestAlertException("A new modeCalcul cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ModeCalcul result = modeCalculService.save(modeCalcul);
        return ResponseEntity
            .created(new URI("/api/mode-calculs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mode-calculs/:id} : Updates an existing modeCalcul.
     *
     * @param id the id of the modeCalcul to save.
     * @param modeCalcul the modeCalcul to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modeCalcul,
     * or with status {@code 400 (Bad Request)} if the modeCalcul is not valid,
     * or with status {@code 500 (Internal Server Error)} if the modeCalcul couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mode-calculs/{id}")
    public ResponseEntity<ModeCalcul> updateModeCalcul(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ModeCalcul modeCalcul
    ) throws URISyntaxException {
        log.debug("REST request to update ModeCalcul : {}, {}", id, modeCalcul);
        if (modeCalcul.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modeCalcul.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modeCalculRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ModeCalcul result = modeCalculService.save(modeCalcul);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modeCalcul.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mode-calculs/:id} : Partial updates given fields of an existing modeCalcul, field will ignore if it is null
     *
     * @param id the id of the modeCalcul to save.
     * @param modeCalcul the modeCalcul to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated modeCalcul,
     * or with status {@code 400 (Bad Request)} if the modeCalcul is not valid,
     * or with status {@code 404 (Not Found)} if the modeCalcul is not found,
     * or with status {@code 500 (Internal Server Error)} if the modeCalcul couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mode-calculs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ModeCalcul> partialUpdateModeCalcul(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ModeCalcul modeCalcul
    ) throws URISyntaxException {
        log.debug("REST request to partial update ModeCalcul partially : {}, {}", id, modeCalcul);
        if (modeCalcul.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, modeCalcul.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!modeCalculRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ModeCalcul> result = modeCalculService.partialUpdate(modeCalcul);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, modeCalcul.getId().toString())
        );
    }

    /**
     * {@code GET  /mode-calculs} : get all the modeCalculs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of modeCalculs in body.
     */
    @GetMapping("/mode-calculs")
    public ResponseEntity<List<ModeCalcul>> getAllModeCalculs(Pageable pageable) {
        log.debug("REST request to get a page of ModeCalculs");
        Page<ModeCalcul> page = modeCalculService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mode-calculs/:id} : get the "id" modeCalcul.
     *
     * @param id the id of the modeCalcul to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the modeCalcul, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mode-calculs/{id}")
    public ResponseEntity<ModeCalcul> getModeCalcul(@PathVariable Long id) {
        log.debug("REST request to get ModeCalcul : {}", id);
        Optional<ModeCalcul> modeCalcul = modeCalculService.findOne(id);
        return ResponseUtil.wrapOrNotFound(modeCalcul);
    }

    /**
     * {@code DELETE  /mode-calculs/:id} : delete the "id" modeCalcul.
     *
     * @param id the id of the modeCalcul to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mode-calculs/{id}")
    public ResponseEntity<Void> deleteModeCalcul(@PathVariable Long id) {
        log.debug("REST request to delete ModeCalcul : {}", id);
        modeCalculService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
