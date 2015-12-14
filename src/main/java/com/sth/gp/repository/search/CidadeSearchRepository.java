package com.sth.gp.repository.search;

import com.sth.gp.domain.Cidade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cidade entity.
 */
public interface CidadeSearchRepository extends ElasticsearchRepository<Cidade, Long> {
}
