package com.sth.gp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sth.gp.domain.Produto;
import com.sth.gp.repository.ProdutoRepository;
import com.sth.gp.repository.search.ProdutoSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Produto.
 */
@RestController
@RequestMapping("/api")
public class ProdutoResource {

    private final Logger log = LoggerFactory.getLogger(ProdutoResource.class);

    @Inject
    private ProdutoRepository produtoRepository;

    @Inject
    private ProdutoSearchRepository produtoSearchRepository;

    /**
     * POST  /produtos -> Create a new produto.
     */
    @RequestMapping(value = "/produtos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) throws URISyntaxException {
        log.debug("REST request to save Produto : {}", produto);
        if (produto.getId() != null) {
            return ResponseEntity.badRequest().header("Falha", "Um novo Produto não pode já ter um Código").body(null);
        }
        Produto result = produtoRepository.save(produto);
        produtoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/produtos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("produto", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produtos -> Updates an existing produto.
     */
    @RequestMapping(value = "/produtos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Produto> updateProduto(@RequestBody Produto produto) throws URISyntaxException {
        log.debug("REST request to update Produto : {}", produto);
        if (produto.getId() == null) {
            return createProduto(produto);
        }
        Produto result = produtoRepository.save(produto);
        produtoSearchRepository.save(produto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("produto", produto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produtos -> get all the produtos.
     */
    @RequestMapping(value = "/produtos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Produto>> getAllProdutos(Pageable pageable)
        throws URISyntaxException {
        Page<Produto> page = produtoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/produtos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /produtos/:id -> get the "id" produto.
     */
    @RequestMapping(value = "/produtos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Produto> getProduto(@PathVariable Long id) {
        log.debug("REST request to get Produto : {}", id);
        return Optional.ofNullable(produtoRepository.findOne(id))
            .map(produto -> new ResponseEntity<>(
                produto,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /produtos/:id -> delete the "id" produto.
     */
    @RequestMapping(value = "/produtos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        log.debug("REST request to delete Produto : {}", id);
        produtoRepository.delete(id);
        produtoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("produto", id.toString())).build();
    }

    /**
     * SEARCH  /_search/produtos/:query -> search for the produto corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/produtos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Produto> searchProdutos(@PathVariable String query) {
        return StreamSupport
            .stream(produtoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
