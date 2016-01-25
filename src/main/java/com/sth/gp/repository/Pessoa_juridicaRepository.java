package com.sth.gp.repository;

import com.sth.gp.domain.Pessoa_juridica;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pessoa_juridica entity.
 */
public interface Pessoa_juridicaRepository extends JpaRepository<Pessoa_juridica,Long> {

}
