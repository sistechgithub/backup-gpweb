package com.sth.gp.repository;

import com.sth.gp.domain.PessoaFisica;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PessoaFisica entity.
 */
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica,Long> {

}
