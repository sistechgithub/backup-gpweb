package com.sth.gp.web.rest;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.sth.gp.domain.Prazo;
import com.sth.gp.repository.PrazoRepository;
import com.sth.gp.repository.search.PrazoSearchRepository;
import com.sth.gp.web.rest.util.HeaderUtil;
import com.sth.gp.web.rest.util.PaginationUtil;

/**
 * REST controller for managing Prazo.
 */
@RestController
@RequestMapping("/api")
public class PrazoResource {

    private final Logger log = LoggerFactory.getLogger(PrazoResource.class);

    @Inject
    private PrazoRepository prazoRepository;

    @Inject
    private PrazoSearchRepository prazoSearchRepository;

    

    /**
     * POST  /prazos -> Create a new tipoCliente.
     */
    @RequestMapping(value = "/prazos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Prazo> createPrazo(@Valid @RequestBody Prazo prazo) throws URISyntaxException {
        log.debug("REST request to save Prazo : {}", prazo);
        if (prazo.getId() != null) {
            return ResponseEntity.badRequest().header("Falha", "Um novo Prazo não pode já ter um Código").body(null);
        }
        Prazo result = prazoRepository.save(prazo);
        prazoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prazos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prazo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prazos -> Updates an existing prazo.
     */
    @RequestMapping(value = "/prazos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Prazo> updatePrazo(@Valid @RequestBody Prazo prazo) throws URISyntaxException {
        log.debug("REST request to update Prazo : {}", prazo);
        if (prazo.getId() == null) {
            return createPrazo(prazo);
        }
        System.out.println("s1");
        Prazo result = prazoRepository.save(prazo);
        System.out.println("s2");
        prazoSearchRepository.save(prazo);
        System.out.println("s3");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prazo", prazo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prazos -> get all the prazos.
     */
    @RequestMapping(value = "/prazos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Prazo>> getAllPrazos(Pageable pageable)
        throws URISyntaxException {
        Page<Prazo> page = prazoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prazos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
 
    /**
     * GET  /prazos/:id -> get the "id" prazo.
     */
    @RequestMapping(value = "/prazos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Prazo> getPrazo(@PathVariable Long id) {
        log.debug("REST request to get Prazo : {}", id);
        return Optional.ofNullable(prazoRepository.findOne(id))
            .map(tipoCliente -> new ResponseEntity<>(
                tipoCliente,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prazos/:id -> delete the "id" prazo.
     */
    @RequestMapping(value = "/prazos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrazo(@PathVariable Long id) {
        log.debug("REST request to delete Prazo : {}", id);
        prazoRepository.delete(id);
        prazoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prazo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prazos/:query -> search for the prazo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prazo/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Prazo> searchPrazos(@PathVariable String query) {
        return StreamSupport
            .stream(prazoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
