package com.sth.gp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sth.gp.domain.Prazo;

public interface PrazoRepository extends JpaRepository<Prazo, Long> {

	Page<Prazo> findById(Long id, Pageable pageable);
	Page<Prazo> findByNomeStartingWithOrderByNomeAsc(String descricao, Pageable pageable);
	
	//Parcel
	@Query("SELECT p FROM Prazo p where p.qntParcelas = :parcela order by p.nome")
	Page<Prazo> findByParcelaStartingWithOrderByNomeAsc(@Param("parcela") Integer parcela, Pageable pageable);
}