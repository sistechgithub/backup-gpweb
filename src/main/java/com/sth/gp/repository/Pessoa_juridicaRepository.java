package com.sth.gp.repository;

import com.sth.gp.domain.PessoaJuridica;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pessoa_juridica entity.
 */
public interface Pessoa_juridicaRepository extends JpaRepository<PessoaJuridica,Long> {

}
