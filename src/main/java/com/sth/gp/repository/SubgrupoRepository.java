package com.sth.gp.repository;

import com.sth.gp.domain.Subgrupo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Subgrupo entity.
 */
public interface SubgrupoRepository extends JpaRepository<Subgrupo,Long> {

}
