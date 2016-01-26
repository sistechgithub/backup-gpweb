package com.sth.gp.repository.search;

import com.sth.gp.domain.Pessoa_juridica;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Pessoa_juridica entity.
 */
public interface Pessoa_juridicaSearchRepository extends ElasticsearchRepository<Pessoa_juridica, Long> {
}
