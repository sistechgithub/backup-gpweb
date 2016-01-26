package com.sth.gp.repository;

import com.sth.gp.domain.Pessoa_fisica;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pessoa_fisica entity.
 */
public interface Pessoa_fisicaRepository extends JpaRepository<Pessoa_fisica,Long> {

}
