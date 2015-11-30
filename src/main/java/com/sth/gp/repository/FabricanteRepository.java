package com.sth.gp.repository;

import com.sth.gp.domain.Fabricante;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Fabricante entity.
 */
public interface FabricanteRepository extends JpaRepository<Fabricante,Long> {

}
