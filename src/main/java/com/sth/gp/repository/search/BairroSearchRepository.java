package com.sth.gp.repository.search;

import com.sth.gp.domain.Bairro;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Bairro entity.
 */
public interface BairroSearchRepository extends ElasticsearchRepository<Bairro, Long> {
}
