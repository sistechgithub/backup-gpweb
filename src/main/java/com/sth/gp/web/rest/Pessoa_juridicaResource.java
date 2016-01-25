package com.sth.gp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sth.gp.domain.Pessoa_juridica;
import com.sth.gp.repository.Pessoa_juridicaRepository;
import com.sth.gp.repository.search.Pessoa_juridicaSearchRepository;
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
 * REST controller for managing Pessoa_juridica.
 */
@RestController
@RequestMapping("/api")
public class Pessoa_juridicaResource {

    private final Logger log = LoggerFactory.getLogger(Pessoa_juridicaResource.class);

    @Inject
    private Pessoa_juridicaRepository pessoa_juridicaRepository;

    @Inject
    private Pessoa_juridicaSearchRepository pessoa_juridicaSearchRepository;

    /**
     * POST  /pessoa_juridicas -> Create a new pessoa_juridica.
     */
    @RequestMapping(value = "/pessoa_juridicas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pessoa_juridica> createPessoa_juridica(@Valid @RequestBody Pessoa_juridica pessoa_juridica) throws URISyntaxException {
        log.debug("REST request to save Pessoa_juridica : {}", pessoa_juridica);
        if (pessoa_juridica.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pessoa_juridica cannot already have an ID").body(null);
        }
        Pessoa_juridica result = pessoa_juridicaRepository.save(pessoa_juridica);
        pessoa_juridicaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pessoa_juridicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pessoa_juridica", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pessoa_juridicas -> Updates an existing pessoa_juridica.
     */
    @RequestMapping(value = "/pessoa_juridicas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pessoa_juridica> updatePessoa_juridica(@Valid @RequestBody Pessoa_juridica pessoa_juridica) throws URISyntaxException {
        log.debug("REST request to update Pessoa_juridica : {}", pessoa_juridica);
        if (pessoa_juridica.getId() == null) {
            return createPessoa_juridica(pessoa_juridica);
        }
        Pessoa_juridica result = pessoa_juridicaRepository.save(pessoa_juridica);
        pessoa_juridicaSearchRepository.save(pessoa_juridica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pessoa_juridica", pessoa_juridica.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pessoa_juridicas -> get all the pessoa_juridicas.
     */
    @RequestMapping(value = "/pessoa_juridicas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Pessoa_juridica>> getAllPessoa_juridicas(Pageable pageable)
        throws URISyntaxException {
        Page<Pessoa_juridica> page = pessoa_juridicaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pessoa_juridicas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pessoa_juridicas/:id -> get the "id" pessoa_juridica.
     */
    @RequestMapping(value = "/pessoa_juridicas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pessoa_juridica> getPessoa_juridica(@PathVariable Long id) {
        log.debug("REST request to get Pessoa_juridica : {}", id);
        return Optional.ofNullable(pessoa_juridicaRepository.findOne(id))
            .map(pessoa_juridica -> new ResponseEntity<>(
                pessoa_juridica,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pessoa_juridicas/:id -> delete the "id" pessoa_juridica.
     */
    @RequestMapping(value = "/pessoa_juridicas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePessoa_juridica(@PathVariable Long id) {
        log.debug("REST request to delete Pessoa_juridica : {}", id);
        pessoa_juridicaRepository.delete(id);
        pessoa_juridicaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pessoa_juridica", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pessoa_juridicas/:query -> search for the pessoa_juridica corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pessoa_juridicas/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Pessoa_juridica> searchPessoa_juridicas(@PathVariable String query) {
        return StreamSupport
            .stream(pessoa_juridicaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
