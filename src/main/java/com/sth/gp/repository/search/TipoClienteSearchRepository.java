package com.sth.gp.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.sth.gp.domain.TipoCliente;

public interface TipoClienteSearchRepository extends ElasticsearchRepository<TipoCliente, Long> {

}
