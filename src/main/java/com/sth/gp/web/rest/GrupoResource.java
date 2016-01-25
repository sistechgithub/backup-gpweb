package com.sth.gp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sth.gp.domain.Grupo;
import com.sth.gp.repository.GrupoRepository;
import com.sth.gp.repository.search.GrupoSearchRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Grupo.
 */
@RestController
@RequestMapping("/api")
public class GrupoResource {

    private final Logger log = LoggerFactory.getLogger(GrupoResource.class);

    @Inject
    private GrupoRepository grupoRepository;

    @Inject
    private GrupoSearchRepository grupoSearchRepository;

    /**
     * POST  /grupos -> Create a new grupo.
     */
    @RequestMapping(value = "/grupos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Grupo> createGrupo(@RequestBody Grupo grupo) throws URISyntaxException {
        log.debug("REST request to save Grupo : {}", grupo);
        if (grupo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new grupo cannot already have an ID").body(null);
        }        
        
        grupo.setDataOperacao(LocalDate.now()); //Always use the operation date from server
        
        Grupo result = grupoRepository.save(grupo);
        grupoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/grupos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("grupo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /grupos -> Updates an existing grupo.
     */
    @RequestMapping(value = "/grupos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Grupo> updateGrupo(@RequestBody Grupo grupo) throws URISyntaxException {
        log.debug("REST request to update Grupo : {}", grupo);
        if (grupo.getId() == null) {
            return createGrupo(grupo);
        }
        
        grupo.setDataOperacao(LocalDate.now()); //Always use the operation date from server
        
        Grupo result = grupoRepository.save(grupo);
        grupoSearchRepository.save(grupo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("grupo", grupo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /grupos -> get all the grupos.
     */
    @RequestMapping(value = "/grupos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Grupo>> getAllGrupos(Pageable pageable)
        throws URISyntaxException {
        Page<Grupo> page = grupoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/grupos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /grupos/:id -> get the "id" grupo.
     */
    @RequestMapping(value = "/grupos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Grupo> getGrupo(@PathVariable Long id) {
        log.debug("REST request to get Grupo : {}", id);
        return Optional.ofNullable(grupoRepository.findOne(id))
            .map(grupo -> new ResponseEntity<>(
                grupo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /grupos/:id -> delete the "id" grupo.
     */
    @RequestMapping(value = "/grupos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGrupo(@PathVariable Long id) {
        log.debug("REST request to delete Grupo : {}", id);
        grupoRepository.delete(id);
        grupoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("grupo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/grupos/:query -> search for the grupo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/grupos/{query}",
    		
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Grupo> searchGrupos(@PathVariable String query) {
        return StreamSupport
            .stream(grupoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
