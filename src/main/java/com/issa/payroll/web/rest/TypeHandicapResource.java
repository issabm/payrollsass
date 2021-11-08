package com.issa.payroll.web.rest;

import com.issa.payroll.domain.TypeHandicap;
import com.issa.payroll.repository.TypeHandicapRepository;
import com.issa.payroll.service.TypeHandicapService;
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
 * REST controller for managing {@link com.issa.payroll.domain.TypeHandicap}.
 */
@RestController
@RequestMapping("/api")
public class TypeHandicapResource {

    private final Logger log = LoggerFactory.getLogger(TypeHandicapResource.class);

    private static final String ENTITY_NAME = "typeHandicap";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeHandicapService typeHandicapService;

    private final TypeHandicapRepository typeHandicapRepository;

    public TypeHandicapResource(TypeHandicapService typeHandicapService, TypeHandicapRepository typeHandicapRepository) {
        this.typeHandicapService = typeHandicapService;
        this.typeHandicapRepository = typeHandicapRepository;
    }

    /**
     * {@code POST  /type-handicaps} : Create a new typeHandicap.
     *
     * @param typeHandicap the typeHandicap to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeHandicap, or with status {@code 400 (Bad Request)} if the typeHandicap has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-handicaps")
    public ResponseEntity<TypeHandicap> createTypeHandicap(@RequestBody TypeHandicap typeHandicap) throws URISyntaxException {
        log.debug("REST request to save TypeHandicap : {}", typeHandicap);
        if (typeHandicap.getId() != null) {
            throw new BadRequestAlertException("A new typeHandicap cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeHandicap result = typeHandicapService.save(typeHandicap);
        return ResponseEntity
            .created(new URI("/api/type-handicaps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-handicaps/:id} : Updates an existing typeHandicap.
     *
     * @param id the id of the typeHandicap to save.
     * @param typeHandicap the typeHandicap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeHandicap,
     * or with status {@code 400 (Bad Request)} if the typeHandicap is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeHandicap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-handicaps/{id}")
    public ResponseEntity<TypeHandicap> updateTypeHandicap(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeHandicap typeHandicap
    ) throws URISyntaxException {
        log.debug("REST request to update TypeHandicap : {}, {}", id, typeHandicap);
        if (typeHandicap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeHandicap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeHandicapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TypeHandicap result = typeHandicapService.save(typeHandicap);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeHandicap.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /type-handicaps/:id} : Partial updates given fields of an existing typeHandicap, field will ignore if it is null
     *
     * @param id the id of the typeHandicap to save.
     * @param typeHandicap the typeHandicap to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeHandicap,
     * or with status {@code 400 (Bad Request)} if the typeHandicap is not valid,
     * or with status {@code 404 (Not Found)} if the typeHandicap is not found,
     * or with status {@code 500 (Internal Server Error)} if the typeHandicap couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/type-handicaps/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TypeHandicap> partialUpdateTypeHandicap(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TypeHandicap typeHandicap
    ) throws URISyntaxException {
        log.debug("REST request to partial update TypeHandicap partially : {}, {}", id, typeHandicap);
        if (typeHandicap.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeHandicap.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!typeHandicapRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TypeHandicap> result = typeHandicapService.partialUpdate(typeHandicap);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeHandicap.getId().toString())
        );
    }

    /**
     * {@code GET  /type-handicaps} : get all the typeHandicaps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeHandicaps in body.
     */
    @GetMapping("/type-handicaps")
    public ResponseEntity<List<TypeHandicap>> getAllTypeHandicaps(Pageable pageable) {
        log.debug("REST request to get a page of TypeHandicaps");
        Page<TypeHandicap> page = typeHandicapService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-handicaps/:id} : get the "id" typeHandicap.
     *
     * @param id the id of the typeHandicap to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeHandicap, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-handicaps/{id}")
    public ResponseEntity<TypeHandicap> getTypeHandicap(@PathVariable Long id) {
        log.debug("REST request to get TypeHandicap : {}", id);
        Optional<TypeHandicap> typeHandicap = typeHandicapService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeHandicap);
    }

    /**
     * {@code DELETE  /type-handicaps/:id} : delete the "id" typeHandicap.
     *
     * @param id the id of the typeHandicap to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-handicaps/{id}")
    public ResponseEntity<Void> deleteTypeHandicap(@PathVariable Long id) {
        log.debug("REST request to delete TypeHandicap : {}", id);
        typeHandicapService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
