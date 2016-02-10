package com.sth.gp.repository.search;

import com.sth.gp.domain.Produto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Produto entity.
 */
public interface ProdutoSearchRepository extends ElasticsearchRepository<Produto, Long> {
}
