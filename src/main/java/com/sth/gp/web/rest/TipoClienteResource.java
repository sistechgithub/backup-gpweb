package com.sth.gp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sth.gp.domain.TipoCliente;
import com.sth.gp.domain.Logradouro;
import com.sth.gp.domain.TipoCliente;
import com.sth.gp.repository.TipoClienteRepository;
import com.sth.gp.repository.LogradouroRepository;
import com.sth.gp.repository.TipoClienteRepository;
import com.sth.gp.repository.search.TipoClienteSearchRepository;
import com.sth.gp.repository.search.TipoClienteSearchRepository;
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
 * REST controller for managing TipoCliente.
 */
@RestController
@RequestMapping("/api")
public class TipoClienteResource {

    private final Logger log = LoggerFactory.getLogger(TipoClienteResource.class);

    @Inject
    private TipoClienteRepository tipoClienteRepository;

    @Inject
    private TipoClienteSearchRepository tipoClienteSearchRepository;

    

    /**
     * POST  /tipoClientes -> Create a new tipoCliente.
     */
    @RequestMapping(value = "/tipoClientes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoCliente> createTipoCliente(@Valid @RequestBody TipoCliente tipoCliente) throws URISyntaxException {
        log.debug("REST request to save TipoCliente : {}", tipoCliente);
        if (tipoCliente.getId() != null) {
            return ResponseEntity.badRequest().header("Falha", "Um novo TipoCliente não pode já ter um Código").body(null);
        }
        TipoCliente result = tipoClienteRepository.save(tipoCliente);
        tipoClienteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tipoClientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tipoCliente", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipoClientes -> Updates an existing tipoCliente.
     */
    @RequestMapping(value = "/tipoClientes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoCliente> updateTipoCliente(@Valid @RequestBody TipoCliente tipoCliente) throws URISyntaxException {
        log.debug("REST request to update TipoCliente : {}", tipoCliente);
        if (tipoCliente.getId() == null) {
            return createTipoCliente(tipoCliente);
        }
        TipoCliente result = tipoClienteRepository.save(tipoCliente);
        tipoClienteSearchRepository.save(tipoCliente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tipoCliente", tipoCliente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipoClientes -> get all the tipoClientes.
     */
    @RequestMapping(value = "/tipoClientes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TipoCliente>> getAllTipoClientes(Pageable pageable)
        throws URISyntaxException {
        Page<TipoCliente> page = tipoClienteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipoClientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
 
    /**
     * GET  /tipoClientes/:id -> get the "id" tipoCliente.
     */
    @RequestMapping(value = "/tipoClientes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TipoCliente> getTipoCliente(@PathVariable Long id) {
        log.debug("REST request to get TipoCliente : {}", id);
        return Optional.ofNullable(tipoClienteRepository.findOne(id))
            .map(tipoCliente -> new ResponseEntity<>(
                tipoCliente,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipoClientes/:id -> delete the "id" tipoCliente.
     */
    @RequestMapping(value = "/tipoClientes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTipoCliente(@PathVariable Long id) {
        log.debug("REST request to delete TipoCliente : {}", id);
        tipoClienteRepository.delete(id);
        tipoClienteSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tipoCliente", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tipoClientes/:query -> search for the tipoCliente corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/tipoCliente/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TipoCliente> searchTipoClientes(@PathVariable String query) {
        return StreamSupport
            .stream(tipoClienteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
