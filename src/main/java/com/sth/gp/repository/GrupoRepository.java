package com.sth.gp.repository;

import com.sth.gp.domain.Grupo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Grupo entity.
 */
public interface GrupoRepository extends JpaRepository<Grupo,Long> {

}
