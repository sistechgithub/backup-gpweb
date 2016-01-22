package com.sth.gp.repository;

import com.sth.gp.domain.Bairro;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Bairro entity.
 */
public interface BairroRepository extends JpaRepository<Bairro,Long> {

	Bairro findOneByNomeAndCidadeNomeAndCidadeEstadoSigla(String bairro, String cidadeNome, String estado);
	
}
