package com.sth.gp.repository.search;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.sth.gp.domain.Prazo;

public interface PrazoSearchRepository extends ElasticsearchRepository<Prazo, Long> {

}
