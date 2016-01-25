package com.sth.gp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sth.gp.domain.Pessoa;
import com.sth.gp.repository.PessoaRepository;
import com.sth.gp.repository.search.PessoaSearchRepository;
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
 * REST controller for managing Pessoa.
 */
@RestController
@RequestMapping("/api")
public class PessoaResource {

    private final Logger log = LoggerFactory.getLogger(PessoaResource.class);

    @Inject
    private PessoaRepository pessoaRepository;

    @Inject
    private PessoaSearchRepository pessoaSearchRepository;

    /**
     * POST  /pessoas -> Create a new pessoa.
     */
    @RequestMapping(value = "/pessoas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pessoa> createPessoa(@Valid @RequestBody Pessoa pessoa) throws URISyntaxException {
        log.debug("REST request to save Pessoa : {}", pessoa);
        if (pessoa.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pessoa cannot already have an ID").body(null);
        }
        Pessoa result = pessoaRepository.save(pessoa);
        pessoaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pessoas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pessoa", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pessoas -> Updates an existing pessoa.
     */
    @RequestMapping(value = "/pessoas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pessoa> updatePessoa(@Valid @RequestBody Pessoa pessoa) throws URISyntaxException {
        log.debug("REST request to update Pessoa : {}", pessoa);
        if (pessoa.getId() == null) {
            return createPessoa(pessoa);
        }
        Pessoa result = pessoaRepository.save(pessoa);
        pessoaSearchRepository.save(pessoa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pessoa", pessoa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pessoas -> get all the pessoas.
     */
    @RequestMapping(value = "/pessoas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Pessoa>> getAllPessoas(Pageable pageable)
        throws URISyntaxException {
        Page<Pessoa> page = pessoaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pessoas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pessoas/:id -> get the "id" pessoa.
     */
    @RequestMapping(value = "/pessoas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pessoa> getPessoa(@PathVariable Long id) {
        log.debug("REST request to get Pessoa : {}", id);
        return Optional.ofNullable(pessoaRepository.findOne(id))
            .map(pessoa -> new ResponseEntity<>(
                pessoa,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pessoas/:id -> delete the "id" pessoa.
     */
    @RequestMapping(value = "/pessoas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        log.debug("REST request to delete Pessoa : {}", id);
        pessoaRepository.delete(id);
        pessoaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pessoa", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pessoas/:query -> search for the pessoa corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pessoas/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Pessoa> searchPessoas(@PathVariable String query) {
        return StreamSupport
            .stream(pessoaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
