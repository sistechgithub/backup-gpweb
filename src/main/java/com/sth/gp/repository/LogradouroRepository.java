package com.sth.gp.repository;

import com.sth.gp.domain.Logradouro;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.lang.String;

/**
 * Spring Data JPA repository for the Logradouro entity.
 */
public interface LogradouroRepository extends JpaRepository<Logradouro,Long> {

	Logradouro findByCdCep(String cd_cep);
	
}
