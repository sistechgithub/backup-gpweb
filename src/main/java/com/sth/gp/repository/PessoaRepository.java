package com.sth.gp.repository;

import com.sth.gp.domain.Pessoa;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pessoa entity.
 */
public interface PessoaRepository extends JpaRepository<Pessoa,Long> {

}
