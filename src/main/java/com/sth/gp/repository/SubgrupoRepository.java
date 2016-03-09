package com.sth.gp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sth.gp.domain.Grupo;
import com.sth.gp.domain.Subgrupo;

/**
 * Spring Data JPA repository for the Subgrupo entity.
 */
public interface SubgrupoRepository extends JpaRepository<Subgrupo,Long> {

	Page<Subgrupo> findById(Long id, Pageable pageable);
	Page<Subgrupo> findByNmSubGrupoStartingWithOrderByNmSubGrupoAsc(String descricao, Pageable pageable);
	
}
