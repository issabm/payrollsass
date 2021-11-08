package com.issa.payroll.web.rest;

import com.issa.payroll.domain.TypeIdentite;
import com.issa.payroll.repository.TypeIdentiteRepository;
import com.issa.payroll.service.TypeIdentiteService;
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
 * REST controller for managing {@link com.issa.payroll.domain.TypeIdentite}.
 */
@RestController
@RequestMapping("/api")
public class TypeIdentiteResource {

    private final Logger log = LoggerFactory.getLogger(TypeIdentiteResource.class);

    private static final String ENTITY_NAME = "typeIdentite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeIdentiteService typeIdentiteService;

    private final TypeIdentiteRepository typeIdentiteRepository;

    public TypeIdentiteResource(TypeIdentiteService typeIdentiteService, TypeIdentiteRepository typeIdentiteRepository) {
        this.typeIdentiteService = typeIdentiteService;
        this.typeIdentiteRepository = typeIdentiteRepository;
    }

    /**
     * {@code POST  /type-identites} : Create a new typeIdentite.
     *
     * @param typeIdentite the typeIdentite to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeIdentite, or with status {@code 400 (Bad Request)} if the typeIdentite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-identites")
    public ResponseEntity<TypeIdentite> createTypeIdentite(@RequestBody TypeIdentite typeIdentite) throws URISyntaxException {
        log.debug("REST request to save TypeIdentite : {}", typeIdentite);
        if (typeIdentite.getId() != null) {
            throw new BadRequestAlertException("A new typeIdentite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeIdentite result = typeIdentiteService.save(typeIdentite);
        return ResponseEntity
            .created(new URI("/api/type-identites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-identites/:id} : Updates an existing typeIdentite.
     *
     * @param id the id of the typeIdentite to save.
     * @param typeIdentite the typeIdentite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeIdentite,
     * or with status {@code 400 (Bad Request)} if the typeIdentite is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeIdentite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-identites/{id}")
    public ResponseEntity<TypeIdentite> updateTypeIdentite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeIdentite typeIdentite
    ) throws URISyntaxException {
        log.debug("REST request to update TypeIdentite : {}, {}", id, typeIdentite);
        if (typeIdentite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeIdentite.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeIdentiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeIdentite result = typeIdentiteService.save(typeIdentite);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeIdentite.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-identites/:id} : Partial updates given fields of an existing typeIdentite, field will ignore if it is null
     *
     * @param id the id of the typeIdentite to save.
     * @param typeIdentite the typeIdentite to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeIdentite,
     * or with status {@code 400 (Bad Request)} if the typeIdentite is not valid,
     * or with status {@code 404 (Not Found)} if the typeIdentite is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeIdentite couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-identites/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeIdentite> partialUpdateTypeIdentite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeIdentite typeIdentite
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeIdentite partially : {}, {}", id, typeIdentite);
        if (typeIdentite.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeIdentite.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeIdentiteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeIdentite> result = typeIdentiteService.partialUpdate(typeIdentite);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeIdentite.getId().toString())
        );
    }

    /**
     * {@code GET  /type-identites} : get all the typeIdentites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeIdentites in body.
     */
    @GetMapping("/type-identites")
    public ResponseEntity<List<TypeIdentite>> getAllTypeIdentites(Pageable pageable) {
        log.debug("REST request to get a page of TypeIdentites");
        Page<TypeIdentite> page = typeIdentiteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-identites/:id} : get the "id" typeIdentite.
     *
     * @param id the id of the typeIdentite to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeIdentite, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-identites/{id}")
    public ResponseEntity<TypeIdentite> getTypeIdentite(@PathVariable Long id) {
        log.debug("REST request to get TypeIdentite : {}", id);
        Optional<TypeIdentite> typeIdentite = typeIdentiteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeIdentite);
    }

    /**
     * {@code DELETE  /type-identites/:id} : delete the "id" typeIdentite.
     *
     * @param id the id of the typeIdentite to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-identites/{id}")
    public ResponseEntity<Void> deleteTypeIdentite(@PathVariable Long id) {
        log.debug("REST request to delete TypeIdentite : {}", id);
        typeIdentiteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
