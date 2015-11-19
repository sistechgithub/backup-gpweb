package com.sth.gp.repository.search;

import com.sth.gp.domain.Subgrupo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Subgrupo entity.
 */
public interface SubgrupoSearchRepository extends ElasticsearchRepository<Subgrupo, Long> {
}
