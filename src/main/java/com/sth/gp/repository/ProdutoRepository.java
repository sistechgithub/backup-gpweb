package com.sth.gp.repository;

import com.sth.gp.domain.Produto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Produto entity.
 */
public interface ProdutoRepository extends JpaRepository<Produto,Long> {

}
