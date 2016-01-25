package com.sth.gp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sth.gp.domain.Bairro;
import com.sth.gp.repository.BairroRepository;
import com.sth.gp.repository.search.BairroSearchRepository;
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
 * REST controller for managing Bairro.
 */
@RestController
@RequestMapping("/api")
public class BairroResource {

    private final Logger log = LoggerFactory.getLogger(BairroResource.class);

    @Inject
    private BairroRepository bairroRepository;

    @Inject
    private BairroSearchRepository bairroSearchRepository;

    
    @RequestMapping(value = "/bairros/getbycidadeestado/{nome}/{cidade}/{estado}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
        @Timed
        public ResponseEntity<Bairro> getBairroByNomeCidadeEstado(@PathVariable String nome, @PathVariable String cidade, @PathVariable String estado) {
            log.debug("REST request to get Bairro : {} {} {}", nome, cidade, estado);
            
            nome = nome.toUpperCase();
            cidade = cidade.toUpperCase();
            estado = estado.toUpperCase();
            
            return Optional.ofNullable(bairroRepository.
            		findOneByNomeAndCidadeNomeAndCidadeEstadoSigla(nome, cidade, estado))
                .map(bairro -> new ResponseEntity<>(
                    bairro,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    
    
    /**
     * POST  /bairros -> Create a new bairro.
     */
    @RequestMapping(value = "/bairros",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bairro> createBairro(@Valid @RequestBody Bairro bairro) throws URISyntaxException {
        log.debug("REST request to save Bairro : {}", bairro);
        if (bairro.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bairro cannot already have an ID").body(null);
        }
        Bairro result = bairroRepository.save(bairro);
        bairroSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bairros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bairro", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bairros -> Updates an existing bairro.
     */
    @RequestMapping(value = "/bairros",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bairro> updateBairro(@Valid @RequestBody Bairro bairro) throws URISyntaxException {
        log.debug("REST request to update Bairro : {}", bairro);
        if (bairro.getId() == null) {
            return createBairro(bairro);
        }
        Bairro result = bairroRepository.save(bairro);
        bairroSearchRepository.save(bairro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bairro", bairro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bairros -> get all the bairros.
     */
    @RequestMapping(value = "/bairros",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Bairro>> getAllBairros(Pageable pageable)
        throws URISyntaxException {
        Page<Bairro> page = bairroRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bairros");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bairros/:id -> get the "id" bairro.
     */
    @RequestMapping(value = "/bairros/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Bairro> getBairro(@PathVariable Long id) {
        log.debug("REST request to get Bairro : {}", id);
        return Optional.ofNullable(bairroRepository.findOne(id))
            .map(bairro -> new ResponseEntity<>(
                bairro,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bairros/:id -> delete the "id" bairro.
     */
    @RequestMapping(value = "/bairros/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBairro(@PathVariable Long id) {
        log.debug("REST request to delete Bairro : {}", id);
        bairroRepository.delete(id);
        bairroSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bairro", id.toString())).build();
    }

    /**
     * SEARCH  /_search/bairros/:query -> search for the bairro corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/bairros/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Bairro> searchBairros(@PathVariable String query) {
        return StreamSupport
            .stream(bairroSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
