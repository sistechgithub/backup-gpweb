package com.sth.gp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sth.gp.domain.TipoCliente;

public interface TipoClienteRepository extends JpaRepository<TipoCliente, Long>{
	
	TipoCliente findOneWithPrazosById(Long id);

}
