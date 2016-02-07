package com.sth.gp.repository.search;

import com.sth.gp.domain.Pessoa;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Pessoa entity.
 */
public interface PessoaSearchRepository extends ElasticsearchRepository<Pessoa, Long> {
}
