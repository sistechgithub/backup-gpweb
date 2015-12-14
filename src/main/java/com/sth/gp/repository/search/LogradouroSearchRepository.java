package com.sth.gp.repository.search;

import com.sth.gp.domain.Logradouro;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Logradouro entity.
 */
public interface LogradouroSearchRepository extends ElasticsearchRepository<Logradouro, Long> {
}
