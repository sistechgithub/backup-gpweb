package com.sth.gp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sth.gp.domain.Pessoa_fisica;
import com.sth.gp.repository.Pessoa_fisicaRepository;
import com.sth.gp.repository.search.Pessoa_fisicaSearchRepository;
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
 * REST controller for managing Pessoa_fisica.
 */
@RestController
@RequestMapping("/api")
public class Pessoa_fisicaResource {

    private final Logger log = LoggerFactory.getLogger(Pessoa_fisicaResource.class);

    @Inject
    private Pessoa_fisicaRepository pessoa_fisicaRepository;

    @Inject
    private Pessoa_fisicaSearchRepository pessoa_fisicaSearchRepository;

    /**
     * POST  /pessoa_fisicas -> Create a new pessoa_fisica.
     */
    @RequestMapping(value = "/pessoa_fisicas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pessoa_fisica> createPessoa_fisica(@Valid @RequestBody Pessoa_fisica pessoa_fisica) throws URISyntaxException {
        log.debug("REST request to save Pessoa_fisica : {}", pessoa_fisica);
        if (pessoa_fisica.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pessoa_fisica cannot already have an ID").body(null);
        }
        Pessoa_fisica result = pessoa_fisicaRepository.save(pessoa_fisica);
        pessoa_fisicaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pessoa_fisicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pessoa_fisica", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pessoa_fisicas -> Updates an existing pessoa_fisica.
     */
    @RequestMapping(value = "/pessoa_fisicas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pessoa_fisica> updatePessoa_fisica(@Valid @RequestBody Pessoa_fisica pessoa_fisica) throws URISyntaxException {
        log.debug("REST request to update Pessoa_fisica : {}", pessoa_fisica);
        if (pessoa_fisica.getId() == null) {
            return createPessoa_fisica(pessoa_fisica);
        }
        Pessoa_fisica result = pessoa_fisicaRepository.save(pessoa_fisica);
        pessoa_fisicaSearchRepository.save(pessoa_fisica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pessoa_fisica", pessoa_fisica.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pessoa_fisicas -> get all the pessoa_fisicas.
     */
    @RequestMapping(value = "/pessoa_fisicas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Pessoa_fisica>> getAllPessoa_fisicas(Pageable pageable)
        throws URISyntaxException {
        Page<Pessoa_fisica> page = pessoa_fisicaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pessoa_fisicas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pessoa_fisicas/:id -> get the "id" pessoa_fisica.
     */
    @RequestMapping(value = "/pessoa_fisicas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pessoa_fisica> getPessoa_fisica(@PathVariable Long id) {
        log.debug("REST request to get Pessoa_fisica : {}", id);
        return Optional.ofNullable(pessoa_fisicaRepository.findOne(id))
            .map(pessoa_fisica -> new ResponseEntity<>(
                pessoa_fisica,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pessoa_fisicas/:id -> delete the "id" pessoa_fisica.
     */
    @RequestMapping(value = "/pessoa_fisicas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePessoa_fisica(@PathVariable Long id) {
        log.debug("REST request to delete Pessoa_fisica : {}", id);
        pessoa_fisicaRepository.delete(id);
        pessoa_fisicaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pessoa_fisica", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pessoa_fisicas/:query -> search for the pessoa_fisica corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pessoa_fisicas/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Pessoa_fisica> searchPessoa_fisicas(@PathVariable String query) {
        return StreamSupport
            .stream(pessoa_fisicaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
