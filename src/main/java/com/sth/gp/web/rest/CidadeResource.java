package com.sth.gp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sth.gp.domain.Cidade;
import com.sth.gp.repository.CidadeRepository;
import com.sth.gp.repository.search.CidadeSearchRepository;
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
 * REST controller for managing Cidade.
 */
@RestController
@RequestMapping("/api")
public class CidadeResource {

    private final Logger log = LoggerFactory.getLogger(CidadeResource.class);

    @Inject
    private CidadeRepository cidadeRepository;

    @Inject
    private CidadeSearchRepository cidadeSearchRepository;

    /**
     * POST  /cidades -> Create a new cidade.
     */
    @RequestMapping(value = "/cidades",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cidade> createCidade(@Valid @RequestBody Cidade cidade) throws URISyntaxException {
        log.debug("REST request to save Cidade : {}", cidade);
        if (cidade.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cidade cannot already have an ID").body(null);
        }
        Cidade result = cidadeRepository.save(cidade);
        cidadeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cidade", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cidades -> Updates an existing cidade.
     */
    @RequestMapping(value = "/cidades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cidade> updateCidade(@Valid @RequestBody Cidade cidade) throws URISyntaxException {
        log.debug("REST request to update Cidade : {}", cidade);
        if (cidade.getId() == null) {
            return createCidade(cidade);
        }
        Cidade result = cidadeRepository.save(cidade);
        cidadeSearchRepository.save(cidade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cidade", cidade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cidades -> get all the cidades.
     */
    @RequestMapping(value = "/cidades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cidade>> getAllCidades(Pageable pageable)
        throws URISyntaxException {
        Page<Cidade> page = cidadeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cidades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cidades/:id -> get the "id" cidade.
     */
    @RequestMapping(value = "/cidades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cidade> getCidade(@PathVariable Long id) {
        log.debug("REST request to get Cidade : {}", id);
        return Optional.ofNullable(cidadeRepository.findOne(id))
            .map(cidade -> new ResponseEntity<>(
                cidade,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cidades/:id -> delete the "id" cidade.
     */
    @RequestMapping(value = "/cidades/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCidade(@PathVariable Long id) {
        log.debug("REST request to delete Cidade : {}", id);
        cidadeRepository.delete(id);
        cidadeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cidade", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cidades/:query -> search for the cidade corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cidades/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Cidade> searchCidades(@PathVariable String query) {
        return StreamSupport
            .stream(cidadeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
