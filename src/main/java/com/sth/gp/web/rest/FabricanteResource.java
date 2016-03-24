package com.sth.gp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.sth.gp.domain.Fabricante;
import com.sth.gp.domain.Logradouro;
import com.sth.gp.repository.FabricanteRepository;
import com.sth.gp.repository.LogradouroRepository;
import com.sth.gp.repository.search.FabricanteSearchRepository;
import com.sth.gp.web.rest.util.HeaderUtil;
import com.sth.gp.web.rest.util.PaginationUtil;

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
            return ResponseEntity.badRequest().header("Falha", "Um novo Fabricante não pode já ter um Código").body(null);
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
    public ResponseEntity<List<Fabricante>> searchFabricantes(@PathVariable String query, Pageable pageable) 
    		throws URISyntaxException {
       
    	try{    		
	    	String[] parameters = query.split(",");
	    	Page<Fabricante> page; 
	    	
	    	if(parameters[0].equals("codigo")){    		
	    		page = fabricanteRepository.findById(Long.parseLong(parameters[1]), pageable);
	    	}else if(parameters[0].equals("descricao")){
	    		page = fabricanteRepository.findByNomeStartingWithOrderByNomeAsc(parameters[1], pageable);
	    	}else if(parameters[0].equals("cidade")){
	    		page = fabricanteRepository.findByCidadeStartingWithOrderByCidadeAsc(parameters[1], pageable);
	    	}else if(parameters[0].equals("fone")){	    		
	    		page = fabricanteRepository.findByFoneStartingWithOrderByFoneAsc(parameters[1], pageable);	    	
	    	}else{  
	    		//find when is inative	    		
	    		page = fabricanteRepository.findByInativoStartingWithOrderByInativoAsc(parameters[1].startsWith("S")?true:false, pageable);
	    	};
	    	
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/_search/fabricantes");
	        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	        
    	}catch(Exception e){
    		log.error(e.getMessage());
    		return ResponseEntity.badRequest().header("Falha", e.getMessage()).body(null);
    	}
    }
}
