package com.sth.gp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sth.gp.domain.PessoaFisica;
import com.sth.gp.repository.PessoaFisicaRepository;
import com.sth.gp.repository.search.PessoaFisicaSearchRepository;
import com.sth.gp.web.rest.util.HeaderUtil;
import com.sth.gp.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PessoaFisica.
 */
@RestController
@RequestMapping("/api")
public class PessoaFisicaResource {

    private final Logger log = LoggerFactory.getLogger(PessoaFisicaResource.class);

    @Inject
    private PessoaFisicaRepository pessoaFisicaRepository;

    @Inject
    private PessoaFisicaSearchRepository pessoaFisicaSearchRepository;

    /**
     * POST  /pessoaFisicas -> Create a new pessoaFisica.
     */
    @RequestMapping(value = "/pessoaFisicas",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PessoaFisica> create(@RequestBody PessoaFisica pessoaFisica) throws URISyntaxException {
        log.debug("REST request to save PessoaFisica : {}", pessoaFisica);
        if (pessoaFisica.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pessoaFisica cannot already have an ID").body(null);
        }
        PessoaFisica result = pessoaFisicaRepository.save(pessoaFisica);
        pessoaFisicaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pessoaFisicas/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("pessoaFisica", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT  /pessoaFisicas -> Updates an existing pessoaFisica.
     */
    @RequestMapping(value = "/pessoaFisicas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PessoaFisica> update(@RequestBody PessoaFisica pessoaFisica) throws URISyntaxException {
        log.debug("REST request to update PessoaFisica : {}", pessoaFisica);
        if (pessoaFisica.getId() == null) {
            return create(pessoaFisica);
        }
        PessoaFisica result = pessoaFisicaRepository.save(pessoaFisica);
        pessoaFisicaSearchRepository.save(pessoaFisica);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("pessoaFisica", pessoaFisica.getId().toString()))
                .body(result);
    }

    /**
     * GET  /pessoaFisicas -> get all the pessoaFisicas.
     */
    @RequestMapping(value = "/pessoaFisicas",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PessoaFisica>> getAll(@RequestParam(value = "page" , required = false) Integer offset,
                                  @RequestParam(value = "per_page", required = false) Integer limit)
        throws URISyntaxException {
        Page<PessoaFisica> page = pessoaFisicaRepository.findAll(PaginationUtil.generatePageRequest(offset, limit));
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pessoaFisicas", offset, limit);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pessoaFisicas/:id -> get the "id" pessoaFisica.
     */
    @RequestMapping(value = "/pessoaFisicas/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PessoaFisica> get(@PathVariable Long id) {
        log.debug("REST request to get PessoaFisica : {}", id);
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findOne(id);
        if (pessoaFisica == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pessoaFisica, HttpStatus.OK);
    }

    /**
     * DELETE  /pessoaFisicas/:id -> delete the "id" pessoaFisica.
     */
    @RequestMapping(value = "/pessoaFisicas/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete PessoaFisica : {}", id);
        pessoaFisicaRepository.delete(id);
        pessoaFisicaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pessoaFisica", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pessoaFisicas/:query -> search for the pessoaFisica corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pessoaFisicas/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PessoaFisica> search(@PathVariable String query) {
        return StreamSupport
            .stream(pessoaFisicaSearchRepository.search(queryString(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
