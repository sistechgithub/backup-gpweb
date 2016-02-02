package com.sth.gp.repository;

import com.sth.gp.domain.PessoaFisica;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pessoa_fisica entity.
 */
public interface Pessoa_fisicaRepository extends JpaRepository<PessoaFisica,Long> {

}
