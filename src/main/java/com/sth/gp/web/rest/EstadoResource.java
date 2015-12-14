package com.sth.gp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sth.gp.domain.Estado;
import com.sth.gp.repository.EstadoRepository;
import com.sth.gp.repository.search.EstadoSearchRepository;
import com.sth.gp.web.rest.util.HeaderUtil;
import com.sth.gp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Estado.
 */
@RestController
@RequestMapping("/api")
public class EstadoResource {

    private final Logger log = LoggerFactory.getLogger(EstadoResource.class);

    @Inject
    private EstadoRepository estadoRepository;

    @Inject
    private EstadoSearchRepository estadoSearchRepository;

    /**
     * POST  /estados -> Create a new estado.
     */
    @RequestMapping(value = "/estados",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Estado> createEstado(@Valid @RequestBody Estado estado) throws URISyntaxException {
        log.debug("REST request to save Estado : {}", estado);
        if (estado.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new estado cannot already have an ID").body(null);
        }
        Estado result = estadoRepository.save(estado);
        estadoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/estados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("estado", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /estados -> Updates an existing estado.
     */
    @RequestMapping(value = "/estados",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Estado> updateEstado(@Valid @RequestBody Estado estado) throws URISyntaxException {
        log.debug("REST request to update Estado : {}", estado);
        if (estado.getId() == null) {
            return createEstado(estado);
        }
        Estado result = estadoRepository.save(estado);
        estadoSearchRepository.save(estado);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("estado", estado.getId().toString()))
            .body(result);
    }

    /**
     * GET  /estados -> get all the estados.
     */
    @RequestMapping(value = "/estados",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Estado>> getAllEstados(Pageable pageable)
        throws URISyntaxException {
        Page<Estado> page = estadoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/estados");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /estados/:id -> get the "id" estado.
     */
    @RequestMapping(value = "/estados/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Estado> getEstado(@PathVariable Long id) {
        log.debug("REST request to get Estado : {}", id);
        return Optional.ofNullable(estadoRepository.findOne(id))
            .map(estado -> new ResponseEntity<>(
                estado,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /estados/:id -> delete the "id" estado.
     */
    @RequestMapping(value = "/estados/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEstado(@PathVariable Long id) {
        log.debug("REST request to delete Estado : {}", id);
        estadoRepository.delete(id);
        estadoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("estado", id.toString())).build();
    }

    /**
     * SEARCH  /_search/estados/:query -> search for the estado corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/estados/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Estado> searchEstados(@PathVariable String query) {
        return StreamSupport
            .stream(estadoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
