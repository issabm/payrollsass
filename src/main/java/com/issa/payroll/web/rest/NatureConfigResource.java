package com.issa.payroll.web.rest;

import com.issa.payroll.domain.NatureConfig;
import com.issa.payroll.repository.NatureConfigRepository;
import com.issa.payroll.service.NatureConfigService;
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
 * REST controller for managing {@link com.issa.payroll.domain.NatureConfig}.
 */
@RestController
@RequestMapping("/api")
public class NatureConfigResource {

    private final Logger log = LoggerFactory.getLogger(NatureConfigResource.class);

    private static final String ENTITY_NAME = "natureConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NatureConfigService natureConfigService;

    private final NatureConfigRepository natureConfigRepository;

    public NatureConfigResource(NatureConfigService natureConfigService, NatureConfigRepository natureConfigRepository) {
        this.natureConfigService = natureConfigService;
        this.natureConfigRepository = natureConfigRepository;
    }

    /**
     * {@code POST  /nature-configs} : Create a new natureConfig.
     *
     * @param natureConfig the natureConfig to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new natureConfig, or with status {@code 400 (Bad Request)} if the natureConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nature-configs")
    public ResponseEntity<NatureConfig> createNatureConfig(@RequestBody NatureConfig natureConfig) throws URISyntaxException {
        log.debug("REST request to save NatureConfig : {}", natureConfig);
        if (natureConfig.getId() != null) {
            throw new BadRequestAlertException("A new natureConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NatureConfig result = natureConfigService.save(natureConfig);
        return ResponseEntity
            .created(new URI("/api/nature-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nature-configs/:id} : Updates an existing natureConfig.
     *
     * @param id the id of the natureConfig to save.
     * @param natureConfig the natureConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureConfig,
     * or with status {@code 400 (Bad Request)} if the natureConfig is not valid,
     * or with status {@code 500 (Internal Server Error)} if the natureConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nature-configs/{id}")
    public ResponseEntity<NatureConfig> updateNatureConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureConfig natureConfig
    ) throws URISyntaxException {
        log.debug("REST request to update NatureConfig : {}, {}", id, natureConfig);
        if (natureConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NatureConfig result = natureConfigService.save(natureConfig);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureConfig.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nature-configs/:id} : Partial updates given fields of an existing natureConfig, field will ignore if it is null
     *
     * @param id the id of the natureConfig to save.
     * @param natureConfig the natureConfig to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureConfig,
     * or with status {@code 400 (Bad Request)} if the natureConfig is not valid,
     * or with status {@code 404 (Not Found)} if the natureConfig is not found,
     * or with status {@code 500 (Internal Server Error)} if the natureConfig couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nature-configs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NatureConfig> partialUpdateNatureConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureConfig natureConfig
    ) throws URISyntaxException {
        log.debug("REST request to partial update NatureConfig partially : {}, {}", id, natureConfig);
        if (natureConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureConfig.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureConfigRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NatureConfig> result = natureConfigService.partialUpdate(natureConfig);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureConfig.getId().toString())
        );
    }

    /**
     * {@code GET  /nature-configs} : get all the natureConfigs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of natureConfigs in body.
     */
    @GetMapping("/nature-configs")
    public ResponseEntity<List<NatureConfig>> getAllNatureConfigs(Pageable pageable) {
        log.debug("REST request to get a page of NatureConfigs");
        Page<NatureConfig> page = natureConfigService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nature-configs/:id} : get the "id" natureConfig.
     *
     * @param id the id of the natureConfig to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the natureConfig, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nature-configs/{id}")
    public ResponseEntity<NatureConfig> getNatureConfig(@PathVariable Long id) {
        log.debug("REST request to get NatureConfig : {}", id);
        Optional<NatureConfig> natureConfig = natureConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(natureConfig);
    }

    /**
     * {@code DELETE  /nature-configs/:id} : delete the "id" natureConfig.
     *
     * @param id the id of the natureConfig to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nature-configs/{id}")
    public ResponseEntity<Void> deleteNatureConfig(@PathVariable Long id) {
        log.debug("REST request to delete NatureConfig : {}", id);
        natureConfigService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
