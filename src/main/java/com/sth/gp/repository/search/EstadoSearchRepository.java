package com.sth.gp.repository.search;

import com.sth.gp.domain.Estado;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Estado entity.
 */
public interface EstadoSearchRepository extends ElasticsearchRepository<Estado, Long> {
}
