package com.sth.gp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sth.gp.domain.Fabricante;
import com.sth.gp.domain.Logradouro;
import com.sth.gp.repository.FabricanteRepository;
import com.sth.gp.repository.LogradouroRepository;
import com.sth.gp.repository.search.FabricanteSearchRepository;
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
 * REST controller for managing Fabricante.
 */
@RestController
@RequestMapping("/api")
public class FabricanteResource {

    private final Logger log = LoggerFactory.getLogger(FabricanteResource.class);

    @Inject
    private FabricanteRepository fabricanteRepository;

    @Inject
    private FabricanteSearchRepository fabricanteSearchRepository;

    @Inject
    private LogradouroRepository logradouroRepository;
    

    /**
     * POST  /fabricantes -> Create a new fabricante.
     */
    @RequestMapping(value = "/fabricantes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fabricante> createFabricante(@Valid @RequestBody Fabricante fabricante) throws URISyntaxException {
        log.debug("REST request to save Fabricante : {}", fabricante);
        if (fabricante.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new fabricante cannot already have an ID").body(null);
        }
        Fabricante result = fabricanteRepository.save(fabricante);
        fabricanteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/fabricantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fabricante", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fabricantes -> Updates an existing fabricante.
     */
    @RequestMapping(value = "/fabricantes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fabricante> updateFabricante(@Valid @RequestBody Fabricante fabricante) throws URISyntaxException {
        log.debug("REST request to update Fabricante : {}", fabricante);
        if (fabricante.getId() == null) {
            return createFabricante(fabricante);
        }
        Fabricante result = fabricanteRepository.save(fabricante);
        fabricanteSearchRepository.save(fabricante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fabricante", fabricante.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fabricantes -> get all the fabricantes.
     */
    @RequestMapping(value = "/fabricantes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Fabricante>> getAllFabricantes(Pageable pageable)
        throws URISyntaxException {
        Page<Fabricante> page = fabricanteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fabricantes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    /**
     * GET  /fabricantes/cep/:cep -> get the "cep" logradouro.
     */
    @RequestMapping(value = "/fabricantes/cep/{cep}",
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
     * GET  /fabricantes/:id -> get the "id" fabricante.
     */
    @RequestMapping(value = "/fabricantes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fabricante> getFabricante(@PathVariable Long id) {
        log.debug("REST request to get Fabricante : {}", id);
        return Optional.ofNullable(fabricanteRepository.findOne(id))
            .map(fabricante -> new ResponseEntity<>(
                fabricante,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fabricantes/:id -> delete the "id" fabricante.
     */
    @RequestMapping(value = "/fabricantes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFabricante(@PathVariable Long id) {
        log.debug("REST request to delete Fabricante : {}", id);
        fabricanteRepository.delete(id);
        fabricanteSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fabricante", id.toString())).build();
    }

    /**
     * SEARCH  /_search/fabricantes/:query -> search for the fabricante corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/fabricantes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Fabricante> searchFabricantes(@PathVariable String query) {
        return StreamSupport
            .stream(fabricanteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
