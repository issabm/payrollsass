package com.issa.payroll.web.rest;

import com.issa.payroll.domain.EntityAdhesion;
import com.issa.payroll.repository.EntityAdhesionRepository;
import com.issa.payroll.service.EntityAdhesionService;
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
 * REST controller for managing {@link com.issa.payroll.domain.EntityAdhesion}.
 */
@RestController
@RequestMapping("/api")
public class EntityAdhesionResource {

    private final Logger log = LoggerFactory.getLogger(EntityAdhesionResource.class);

    private static final String ENTITY_NAME = "entityAdhesion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntityAdhesionService entityAdhesionService;

    private final EntityAdhesionRepository entityAdhesionRepository;

    public EntityAdhesionResource(EntityAdhesionService entityAdhesionService, EntityAdhesionRepository entityAdhesionRepository) {
        this.entityAdhesionService = entityAdhesionService;
        this.entityAdhesionRepository = entityAdhesionRepository;
    }

    /**
     * {@code POST  /entity-adhesions} : Create a new entityAdhesion.
     *
     * @param entityAdhesion the entityAdhesion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entityAdhesion, or with status {@code 400 (Bad Request)} if the entityAdhesion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entity-adhesions")
    public ResponseEntity<EntityAdhesion> createEntityAdhesion(@RequestBody EntityAdhesion entityAdhesion) throws URISyntaxException {
        log.debug("REST request to save EntityAdhesion : {}", entityAdhesion);
        if (entityAdhesion.getId() != null) {
            throw new BadRequestAlertException("A new entityAdhesion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntityAdhesion result = entityAdhesionService.save(entityAdhesion);
        return ResponseEntity
            .created(new URI("/api/entity-adhesions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entity-adhesions/:id} : Updates an existing entityAdhesion.
     *
     * @param id the id of the entityAdhesion to save.
     * @param entityAdhesion the entityAdhesion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entityAdhesion,
     * or with status {@code 400 (Bad Request)} if the entityAdhesion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entityAdhesion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entity-adhesions/{id}")
    public ResponseEntity<EntityAdhesion> updateEntityAdhesion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EntityAdhesion entityAdhesion
    ) throws URISyntaxException {
        log.debug("REST request to update EntityAdhesion : {}, {}", id, entityAdhesion);
        if (entityAdhesion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entityAdhesion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entityAdhesionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EntityAdhesion result = entityAdhesionService.save(entityAdhesion);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entityAdhesion.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entity-adhesions/:id} : Partial updates given fields of an existing entityAdhesion, field will ignore if it is null
     *
     * @param id the id of the entityAdhesion to save.
     * @param entityAdhesion the entityAdhesion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entityAdhesion,
     * or with status {@code 400 (Bad Request)} if the entityAdhesion is not valid,
     * or with status {@code 404 (Not Found)} if the entityAdhesion is not found,
     * or with status {@code 500 (Internal Server Error)} if the entityAdhesion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/entity-adhesions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EntityAdhesion> partialUpdateEntityAdhesion(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EntityAdhesion entityAdhesion
    ) throws URISyntaxException {
        log.debug("REST request to partial update EntityAdhesion partially : {}, {}", id, entityAdhesion);
        if (entityAdhesion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entityAdhesion.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entityAdhesionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntityAdhesion> result = entityAdhesionService.partialUpdate(entityAdhesion);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entityAdhesion.getId().toString())
        );
    }

    /**
     * {@code GET  /entity-adhesions} : get all the entityAdhesions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entityAdhesions in body.
     */
    @GetMapping("/entity-adhesions")
    public ResponseEntity<List<EntityAdhesion>> getAllEntityAdhesions(Pageable pageable) {
        log.debug("REST request to get a page of EntityAdhesions");
        Page<EntityAdhesion> page = entityAdhesionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entity-adhesions/:id} : get the "id" entityAdhesion.
     *
     * @param id the id of the entityAdhesion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entityAdhesion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entity-adhesions/{id}")
    public ResponseEntity<EntityAdhesion> getEntityAdhesion(@PathVariable Long id) {
        log.debug("REST request to get EntityAdhesion : {}", id);
        Optional<EntityAdhesion> entityAdhesion = entityAdhesionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entityAdhesion);
    }

    /**
     * {@code DELETE  /entity-adhesions/:id} : delete the "id" entityAdhesion.
     *
     * @param id the id of the entityAdhesion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entity-adhesions/{id}")
    public ResponseEntity<Void> deleteEntityAdhesion(@PathVariable Long id) {
        log.debug("REST request to delete EntityAdhesion : {}", id);
        entityAdhesionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
