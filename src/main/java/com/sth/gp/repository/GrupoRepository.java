package com.sth.gp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sth.gp.domain.Grupo;

/**
 * Spring Data JPA repository for the Grupo entity.
 */
public interface GrupoRepository extends JpaRepository<Grupo,Long> {
	
	Page<Grupo> findById(Long id, Pageable pageable);
	Page<Grupo> findByNmGrupoStartingWithOrderByNmGrupoAsc(String descricao, Pageable pageable);
		
}