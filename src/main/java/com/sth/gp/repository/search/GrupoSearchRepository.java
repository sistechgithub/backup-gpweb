package com.sth.gp.repository.search;

import com.sth.gp.domain.Grupo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Grupo entity.
 */
public interface GrupoSearchRepository extends ElasticsearchRepository<Grupo, Long> {
}
