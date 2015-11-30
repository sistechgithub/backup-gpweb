package com.sth.gp.repository.search;

import com.sth.gp.domain.Fabricante;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Fabricante entity.
 */
public interface FabricanteSearchRepository extends ElasticsearchRepository<Fabricante, Long> {
}
