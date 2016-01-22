package com.sth.gp.repository;

import com.sth.gp.domain.Estado;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Estado entity.
 */
public interface EstadoRepository extends JpaRepository<Estado,Long> {

}
