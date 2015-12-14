package com.sth.gp.repository;

import com.sth.gp.domain.Cidade;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Cidade entity.
 */
public interface CidadeRepository extends JpaRepository<Cidade,Long> {

}
