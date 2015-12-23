package com.sth.gp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sth.gp.domain.Logradouro;
import com.sth.gp.repository.LogradouroRepository;
import com.sth.gp.repository.search.LogradouroSearchRepository;
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
 * REST controller for managing Logradouro.
 */
@RestController
@RequestMapping("/api")
public class LogradouroResource {

    private final Logger log = LoggerFactory.getLogger(LogradouroResource.class);

    @Inject
    private LogradouroRepository logradouroRepository;

    @Inject
    private LogradouroSearchRepository logradouroSearchRepository;

    /**
     * POST  /logradouros -> Create a new logradouro.
     */
    @RequestMapping(value = "/logradouros",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Logradouro> createLogradouro(@Valid @RequestBody Logradouro logradouro) throws URISyntaxException {
        log.debug("REST request to save Logradouro : {}", logradouro);
        if (logradouro.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new logradouro cannot already have an ID").body(null);
        }
        
        Logradouro result = logradouroRepository.save(logradouro);
        logradouroSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityCreationAlert("logradouro", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /logradouros -> Updates an existing logradouro.
     */
    @RequestMapping(value = "/logradouros",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Logradouro> updateLogradouro(@Valid @RequestBody Logradouro logradouro) throws URISyntaxException {
        log.debug("REST request to update Logradouro : {}", logradouro);
        if (logradouro.getId() == null) {
            return createLogradouro(logradouro);
        }
        Logradouro result = logradouroRepository.save(logradouro);
        logradouroSearchRepository.save(logradouro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("logradouro", logradouro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /logradouros -> get all the logradouros.
     */
    @RequestMapping(value = "/logradouros",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Logradouro>> getAllLogradouros(Pageable pageable)
        throws URISyntaxException {
        Page<Logradouro> page = logradouroRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/logradouros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /logradouros/:id -> get the "id" logradouro.
     */
    @RequestMapping(value = "/logradouros/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Logradouro> getLogradouro(@PathVariable Long id) {
        log.debug("REST request to get Logradouro : {}", id);
        return Optional.ofNullable(logradouroRepository.findOne(id))
            .map(logradouro -> new ResponseEntity<>(
                logradouro,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    /**
     * GET  /logradouros/cep/:cep -> get the "cep" logradouro.
     */
    @RequestMapping(value = "/logradouros/cep/{cep}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Logradouro> getLogradouroByCep(@PathVariable String cep) {
        log.debug("REST request to get Logradouro : {}", cep);
        return Optional.ofNullable(logradouroRepository.findByCep(cep))
            .map(logradouro -> new ResponseEntity<>(
                logradouro,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /logradouros/:id -> delete the "id" logradouro.
     */
    @RequestMapping(value = "/logradouros/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLogradouro(@PathVariable Long id) {
        log.debug("REST request to delete Logradouro : {}", id);
        logradouroRepository.delete(id);
        logradouroSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("logradouro", id.toString())).build();
    }

    /**
     * SEARCH  /_search/logradouros/:query -> search for the logradouro corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/logradouros/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Logradouro> searchLogradouros(@PathVariable String query) {
        return StreamSupport
            .stream(logradouroSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
