package com.sth.gp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sth.gp.domain.Fabricante;

/**
 * Spring Data JPA repository for the Fabricante entity.
 */
public interface FabricanteRepository extends JpaRepository<Fabricante,Long> {

	/*
	 * Methods used on search
	*/
	
	//Id
	Page<Fabricante> findById(Long id, Pageable pageable);
	
	//Name
	Page<Fabricante> findByNomeStartingWithOrderByNomeAsc(String descricao, Pageable pageable);
	
	//City
	@Query("SELECT f FROM Fabricante f where f.logradouro.bairro.cidade.nome like :cidade% order by f.logradouro.bairro.cidade.nome") 		
	Page<Fabricante> findByCidadeStartingWithOrderByCidadeAsc(@Param("cidade") String descricao, Pageable pageable);
	
	//Fone
	@Query("SELECT f FROM Fabricante f where f.telefone like :fone% order by f.telefone") 		
	Page<Fabricante> findByFoneStartingWithOrderByFoneAsc(@Param("fone") String descricao, Pageable pageable);
	
	//Inactive
	@Query("SELECT f FROM Fabricante f where f.inativo = :inativo order by f.inativo") 		
	Page<Fabricante> findByInativoStartingWithOrderByInativoAsc(@Param("inativo") Boolean value, Pageable pageable);
}