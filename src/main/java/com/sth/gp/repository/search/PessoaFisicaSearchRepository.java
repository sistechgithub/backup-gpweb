package com.sth.gp.repository.search;

import com.sth.gp.domain.PessoaFisica;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PessoaFisica entity.
 */
public interface PessoaFisicaSearchRepository extends ElasticsearchRepository<PessoaFisica, Long> {
}
